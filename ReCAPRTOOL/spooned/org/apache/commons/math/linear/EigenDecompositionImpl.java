/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math.linear;







/**
 * Calculates the eigen decomposition of a real <strong>symmetric</strong>
 * matrix.
 * <p>
 * The eigen decomposition of matrix A is a set of two matrices: V and D such
 * that A = V D V<sup>T</sup>. A, V and D are all m &times; m matrices.
 * </p>
 * <p>
 * As of 2.0, this class supports only <strong>symmetric</strong> matrices, and
 * hence computes only real realEigenvalues. This implies the D matrix returned
 * by {@link #getD()} is always diagonal and the imaginary values returned
 * {@link #getImagEigenvalue(int)} and {@link #getImagEigenvalues()} are always
 * null.
 * </p>
 * <p>
 * When called with a {@link RealMatrix} argument, this implementation only uses
 * the upper part of the matrix, the part below the diagonal is not accessed at
 * all.
 * </p>
 * <p>
 * This implementation is based on the paper by A. Drubrulle, R.S. Martin and
 * J.H. Wilkinson 'The Implicit QL Algorithm' in Wilksinson and Reinsch (1971)
 * Handbook for automatic computation, vol. 2, Linear algebra, Springer-Verlag,
 * New-York
 * </p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class EigenDecompositionImpl implements org.apache.commons.math.linear.EigenDecomposition {

    /**
     * Maximum number of iterations accepted in the implicit QL transformation
     */     private byte maxIter = 30;
    /**
     * Main diagonal of the tridiagonal matrix.
     */     private double[] main;
    /**
     * Secondary diagonal of the tridiagonal matrix.
     */     private double[] secondary;
    /**
     * Transformer to tridiagonal (may be null if matrix is already
     * tridiagonal).
     */
    private org.apache.commons.math.linear.TriDiagonalTransformer transformer;

    /**
     * Real part of the realEigenvalues.
     */     private double[] realEigenvalues;
    /**
     * Imaginary part of the realEigenvalues.
     */     private double[] imagEigenvalues;
    /**
     * Eigenvectors.
     */     private org.apache.commons.math.linear.ArrayRealVector[] eigenvectors;
    /**
     * Cached value of V.
     */     private org.apache.commons.math.linear.RealMatrix cachedV;
    /**
     * Cached value of D.
     */     private org.apache.commons.math.linear.RealMatrix cachedD;
    /**
     * Cached value of Vt.
     */     private org.apache.commons.math.linear.RealMatrix cachedVt;
    /**
     * Calculates the eigen decomposition of the given symmetric matrix.
     *
     * @param matrix
     * 		The <strong>symmetric</strong> matrix to decompose.
     * @param splitTolerance
     * 		dummy parameter, present for backward compatibility only.
     * @exception InvalidMatrixException (wrapping a
     * {@link org.apache.commons.math.ConvergenceException} if algorithm
     * fails to converge
     */     public EigenDecompositionImpl(final org.apache.commons.math.linear.RealMatrix matrix, final double splitTolerance) throws org.apache.commons.math.linear.InvalidMatrixException {         if (isSymmetric(matrix)) {
            transformToTridiagonal(matrix);
            findEigenVectors(transformer.getQ().getData());
        }else {
            // as of 2.0, non-symmetric matrices (i.e. complex eigenvalues) are
            // NOT supported
            // see issue https://issues.apache.org/jira/browse/MATH-235
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.ASSYMETRIC_EIGEN_NOT_SUPPORTED);
        }
    }

    /**
     * Calculates the eigen decomposition of the symmetric tridiagonal
     * matrix.  The Householder matrix is assumed to be the identity matrix.
     *
     * @param main
     * 		Main diagonal of the symmetric triadiagonal form
     * @param secondary
     * 		Secondary of the tridiagonal form
     * @param splitTolerance
     * 		dummy parameter, present for backward compatibility only.
     * @exception InvalidMatrixException (wrapping a
     * {@link org.apache.commons.math.ConvergenceException} if algorithm
     * fails to converge
     */     public EigenDecompositionImpl(final double[] main, final double[] secondary, final double splitTolerance) throws org.apache.commons.math.linear.InvalidMatrixException {         this.main = main.clone();
        this.secondary = secondary.clone();
        transformer = null;
        final int size = main.length;
        double[][] z = new double[size][size];
        for (int i = 0; i < size; i++) {
            z[i][i] = 1.0;
        }
        findEigenVectors(z);
    }

    /**
     * Check if a matrix is symmetric.
     *
     * @param matrix
     * 		matrix to check
     * @return true if matrix is symmetric
     */     private boolean isSymmetric(final org.apache.commons.math.linear.RealMatrix matrix) {
        final int rows = matrix.getRowDimension();
        final int columns = matrix.getColumnDimension();
        final double eps = ((10 * rows) * columns) * (org.apache.commons.math.util.MathUtils.EPSILON);
        for (int i = 0; i < rows; ++i) {
            for (int j = i + 1; j < columns; ++j) {
                final double mij = matrix.getEntry(i, j);
                final double mji = matrix.getEntry(j, i);
                if ((java.lang.Math.abs((mij - mji))) > ((java.lang.Math.max(java.lang.Math.abs(mij), java.lang.Math.abs(
                mji))) * eps)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealMatrix getV() throws org.apache.commons.math.linear.InvalidMatrixException {
        if ((cachedV) == null) {
            final int m = eigenvectors.length;
            cachedV = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(m, m);
            for (int k = 0; k < m; ++k) {
                cachedV.setColumnVector(k, eigenvectors[k]);
            }
        }
        // return the cached matrix
        return cachedV;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealMatrix getD() throws org.apache.commons.math.linear.InvalidMatrixException {         if ((cachedD) == null) {
            // cache the matrix for subsequent calls
            cachedD = org.apache.commons.math.linear.MatrixUtils.createRealDiagonalMatrix(realEigenvalues);
        }
        return cachedD;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealMatrix getVT() throws org.apache.commons.math.linear.InvalidMatrixException {
        if ((cachedVt) == null) {
            final int m = eigenvectors.length;
            cachedVt = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(m, m);
            for (int k = 0; k < m; ++k) {
                cachedVt.setRowVector(k, eigenvectors[k]);
            }

        }

        // return the cached matrix
        return cachedVt;
    }

    /**
     * {@inheritDoc}
     */     public double[] getRealEigenvalues() throws org.apache.commons.math.linear.InvalidMatrixException {         return realEigenvalues.clone();
    }

    /**
     * {@inheritDoc}
     */     public double getRealEigenvalue(final int i) throws java.lang.ArrayIndexOutOfBoundsException, org.apache.commons.math.linear.InvalidMatrixException {
        return realEigenvalues[i];
    }

    /**
     * {@inheritDoc}
     */     public double[] getImagEigenvalues() throws org.apache.commons.math.linear.InvalidMatrixException {         return imagEigenvalues.clone();
    }

    /**
     * {@inheritDoc}
     */     public double getImagEigenvalue(final int i) throws java.lang.ArrayIndexOutOfBoundsException, org.apache.commons.math.linear.InvalidMatrixException {
        return imagEigenvalues[i];
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector getEigenvector(final int i) throws java.lang.ArrayIndexOutOfBoundsException, org.apache.commons.math.linear.InvalidMatrixException {
        return eigenvectors[i].copy();
    }

    /**
     * Return the determinant of the matrix
     *
     * @return determinant of the matrix
     */     public double getDeterminant() {
        double determinant = 1;
        for (double lambda : realEigenvalues) {
            determinant *= lambda;
        }
        return determinant;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.DecompositionSolver getSolver() {         return new org.apache.commons.math.linear.EigenDecompositionImpl.Solver(realEigenvalues, imagEigenvalues, eigenvectors);
    }

    /**
     * Specialized solver.
     */     private static class Solver implements org.apache.commons.math.linear.DecompositionSolver {
        /**
         * Real part of the realEigenvalues.
         */         private double[] realEigenvalues;
        /**
         * Imaginary part of the realEigenvalues.
         */         private double[] imagEigenvalues;
        /**
         * Eigenvectors.
         */         private final org.apache.commons.math.linear.ArrayRealVector[] eigenvectors;
        /**
         * Build a solver from decomposed matrix.
         *
         * @param realEigenvalues
         * 		real parts of the eigenvalues
         * @param imagEigenvalues
         * 		imaginary parts of the eigenvalues
         * @param eigenvectors
         * 		eigenvectors
         */         private Solver(final double[] realEigenvalues, final 
        double[] imagEigenvalues, final 
        org.apache.commons.math.linear.ArrayRealVector[] eigenvectors) {
            this.realEigenvalues = realEigenvalues;
            this.imagEigenvalues = imagEigenvalues;
            this.eigenvectors = eigenvectors;
        }

        /**
         * Solve the linear equation A &times; X = B for symmetric matrices A.
         * <p>
         * This method only find exact linear solutions, i.e. solutions for
         * which ||A &times; X - B|| is exactly 0.
         * </p>
         *
         * @param b
         * 		right-hand side of the equation A &times; X = B
         * @return a vector X that minimizes the two norm of A &times; X - B
         * @exception IllegalArgumentException
         * if matrices dimensions don't match
         * @exception InvalidMatrixException
         * if decomposed matrix is singular
         */         public double[] solve(final double[] b) throws 
        java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {

            if (!(isNonSingular())) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }

            final int m = realEigenvalues.length;
            if ((b.length) != m) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
                b.length, m);
            }

            final double[] bp = new double[m];
            for (int i = 0; i < m; ++i) {
                final org.apache.commons.math.linear.ArrayRealVector v = eigenvectors[i];
                final double[] vData = v.getDataRef();
                final double s = (v.dotProduct(b)) / (realEigenvalues[i]);
                for (int j = 0; j < m; ++j) {
                    bp[j] += s * (vData[j]);
                }
            }

            return bp;

        }

        /**
         * Solve the linear equation A &times; X = B for symmetric matrices A.
         * <p>
         * This method only find exact linear solutions, i.e. solutions for
         * which ||A &times; X - B|| is exactly 0.
         * </p>
         *
         * @param b
         * 		right-hand side of the equation A &times; X = B
         * @return a vector X that minimizes the two norm of A &times; X - B
         * @exception IllegalArgumentException
         * if matrices dimensions don't match
         * @exception InvalidMatrixException
         * if decomposed matrix is singular
         */         public org.apache.commons.math.linear.RealVector solve(final org.apache.commons.math.linear.RealVector b) throws 
        java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {

            if (!(isNonSingular())) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }

            final int m = realEigenvalues.length;
            if ((b.getDimension()) != m) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, b.getDimension(), 
                m);
            }

            final double[] bp = new double[m];
            for (int i = 0; i < m; ++i) {
                final org.apache.commons.math.linear.ArrayRealVector v = eigenvectors[i];
                final double[] vData = v.getDataRef();
                final double s = (v.dotProduct(b)) / (realEigenvalues[i]);
                for (int j = 0; j < m; ++j) {
                    bp[j] += s * (vData[j]);
                }
            }

            return new org.apache.commons.math.linear.ArrayRealVector(bp, false);

        }

        /**
         * Solve the linear equation A &times; X = B for symmetric matrices A.
         * <p>
         * This method only find exact linear solutions, i.e. solutions for
         * which ||A &times; X - B|| is exactly 0.
         * </p>
         *
         * @param b
         * 		right-hand side of the equation A &times; X = B
         * @return a matrix X that minimizes the two norm of A &times; X - B
         * @exception IllegalArgumentException
         * if matrices dimensions don't match
         * @exception InvalidMatrixException
         * if decomposed matrix is singular
         */         public org.apache.commons.math.linear.RealMatrix solve(final org.apache.commons.math.linear.RealMatrix b) throws 
        java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {

            if (!(isNonSingular())) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }

            final int m = realEigenvalues.length;
            if ((b.getRowDimension()) != m) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(

                org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
                b.getRowDimension(), b.getColumnDimension(), m, 
                "n");
            }

            final int nColB = b.getColumnDimension();
            final double[][] bp = new double[m][nColB];
            for (int k = 0; k < nColB; ++k) {
                for (int i = 0; i < m; ++i) {
                    final org.apache.commons.math.linear.ArrayRealVector v = eigenvectors[i];
                    final double[] vData = v.getDataRef();
                    double s = 0;
                    for (int j = 0; j < m; ++j) {
                        s += (v.getEntry(j)) * (b.getEntry(j, k));
                    }
                    s /= realEigenvalues[i];
                    for (int j = 0; j < m; ++j) {
                        bp[j][k] += s * (vData[j]);
                    }
                }
            }

            return org.apache.commons.math.linear.MatrixUtils.createRealMatrix(bp);

        }

        /**
         * Check if the decomposed matrix is non-singular.
         *
         * @return true if the decomposed matrix is non-singular
         */         public boolean isNonSingular() {
            for (int i = 0; i < (realEigenvalues.length); ++i) {
                if (((realEigenvalues[i]) == 0) && ((imagEigenvalues[i]) == 0)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Get the inverse of the decomposed matrix.
         *
         * @return inverse matrix
         * @throws InvalidMatrixException
         * 		if decomposed matrix is singular
         */         public org.apache.commons.math.linear.RealMatrix getInverse() throws org.apache.commons.math.linear.InvalidMatrixException {

            if (!(isNonSingular())) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }

            final int m = realEigenvalues.length;
            final double[][] invData = new double[m][m];

            for (int i = 0; i < m; ++i) {
                final double[] invI = invData[i];
                for (int j = 0; j < m; ++j) {
                    double invIJ = 0;
                    for (int k = 0; k < m; ++k) {
                        final double[] vK = eigenvectors[k].getDataRef();
                        invIJ += ((vK[i]) * (vK[j])) / (realEigenvalues[k]);
                    }
                    invI[j] = invIJ;
                }
            }
            return org.apache.commons.math.linear.MatrixUtils.createRealMatrix(invData);

        }

    }

    /**
     * Transform matrix to tridiagonal.
     *
     * @param matrix
     * 		matrix to transform
     */     private void transformToTridiagonal(final org.apache.commons.math.linear.RealMatrix matrix) {

        // transform the matrix to tridiagonal
        transformer = new org.apache.commons.math.linear.TriDiagonalTransformer(matrix);
        main = transformer.getMainDiagonalRef();
        secondary = transformer.getSecondaryDiagonalRef();

    }

    /**
     * Find eigenvalues and eigenvectors (Dubrulle et al., 1971)
     *
     * @param householderMatrix
     * 		Householder matrix of the transformation
     * 		to tri-diagonal form.
     */     private void findEigenVectors(double[][] householderMatrix) {
        double[][] z = householderMatrix.clone();
        final int n = main.length;
        realEigenvalues = new double[n];
        imagEigenvalues = new double[n];
        double[] e = new double[n];
        for (int i = 0; i < (n - 1); i++) {
            realEigenvalues[i] = main[i];
            e[i] = secondary[i];
        }
        realEigenvalues[(n - 1)] = main[(n - 1)];
        e[(n - 1)] = 0.0;

        // Determine the largest main and secondary value in absolute term.
        double maxAbsoluteValue = 0.0;
        for (int i = 0; i < n; i++) {
            if ((java.lang.Math.abs(realEigenvalues[i])) > maxAbsoluteValue) {
                maxAbsoluteValue = java.lang.Math.abs(realEigenvalues[i]);
            }
            if ((java.lang.Math.abs(e[i])) > maxAbsoluteValue) {
                maxAbsoluteValue = java.lang.Math.abs(e[i]);
            }
        }
        // Make null any main and secondary value too small to be significant
        if (maxAbsoluteValue != 0.0) {
            for (int i = 0; i < n; i++) {
                if ((java.lang.Math.abs(realEigenvalues[i])) <= ((org.apache.commons.math.util.MathUtils.EPSILON) * maxAbsoluteValue)) {
                    realEigenvalues[i] = 0.0;
                }
                if ((java.lang.Math.abs(e[i])) <= ((org.apache.commons.math.util.MathUtils.EPSILON) * maxAbsoluteValue)) {
                    e[i] = 0.0;
                }
            }
        }

        for (int j = 0; j < n; j++) {
            int its = 0;
            int m;
            do {
                for (m = j; m < (n - 1); m++) {
                    double delta = (java.lang.Math.abs(realEigenvalues[m])) + (java.lang.Math.abs(realEigenvalues[(m + 1)]));
                    if (((java.lang.Math.abs(e[m])) + delta) == delta) {
                        break;
                    }
                }
                if (m != j) {
                    if (its == (maxIter))
                        throw new org.apache.commons.math.linear.InvalidMatrixException(
                        new org.apache.commons.math.MaxIterationsExceededException(maxIter));
                    its++;
                    double q = ((realEigenvalues[(j + 1)]) - (realEigenvalues[j])) / (2 * (e[j]));
                    double t = java.lang.Math.sqrt((1 + (q * q)));
                    if (q < 0.0) {
                        q = ((realEigenvalues[m]) - (realEigenvalues[j])) + ((e[j]) / (q - t));
                    }else {
                        q = ((realEigenvalues[m]) - (realEigenvalues[j])) + ((e[j]) / (q + t));
                    }
                    double u = 0.0;
                    double s = 1.0;
                    double c = 1.0;
                    int i;
                    for (i = m - 1; i >= j; i--) {
                        double p = s * (e[i]);
                        double h = c * (e[i]);
                        if ((java.lang.Math.abs(p)) >= (java.lang.Math.abs(q))) {
                            c = q / p;
                            t = java.lang.Math.sqrt(((c * c) + 1.0));
                            e[(i + 1)] = p * t;
                            s = 1.0 / t;
                            c = c * s;
                        }else {
                            s = p / q;
                            t = java.lang.Math.sqrt(((s * s) + 1.0));
                            e[(i + 1)] = q * t;
                            c = 1.0 / t;
                            s = s * c;
                        }
                        if ((e[(i + 1)]) == 0.0) {
                            realEigenvalues[(i + 1)] -= u;
                            e[m] = 0.0;
                            break;
                        }
                        q = (realEigenvalues[(i + 1)]) - u;
                        t = (((realEigenvalues[i]) - q) * s) + ((2.0 * c) * h);
                        u = s * t;
                        realEigenvalues[(i + 1)] = q + u;
                        q = (c * t) - h;
                        for (int ia = 0; ia < n; ia++) {
                            p = z[ia][(i + 1)];
                            z[ia][(i + 1)] = (s * (z[ia][i])) + (c * p);
                            z[ia][i] = (c * (z[ia][i])) - (s * p);
                        }
                    }
                    if (((e[(i + 1)]) == 0.0) && (i >= j))
                        continue;
                    realEigenvalues[j] -= u;
                    e[j] = q;
                    e[m] = 0.0;
                }
            } while (m != j );
        }

        // Sort the eigen values (and vectors) in increase order
        for (int i = 0; i < n; i++) {
            int k = i;
            double p = realEigenvalues[i];
            for (int j = i + 1; j < n; j++) {
                if ((realEigenvalues[j]) > p) {
                    k = j;
                    p = realEigenvalues[j];
                }
            }
            if (k != i) {
                realEigenvalues[k] = realEigenvalues[i];
                realEigenvalues[i] = p;
                for (int j = 0; j < n; j++) {
                    p = z[j][i];
                    z[j][i] = z[j][k];
                    z[j][k] = p;
                }
            }
        }

        // Determine the largest eigen value in absolute term.
        maxAbsoluteValue = 0.0;
        for (int i = 0; i < n; i++) {
            if ((java.lang.Math.abs(realEigenvalues[i])) > maxAbsoluteValue) {
                maxAbsoluteValue = java.lang.Math.abs(realEigenvalues[i]);
            }
        }
        // Make null any eigen value too small to be significant
        if (maxAbsoluteValue != 0.0) {
            for (int i = 0; i < n; i++) {
                if ((java.lang.Math.abs(realEigenvalues[i])) < ((org.apache.commons.math.util.MathUtils.EPSILON) * maxAbsoluteValue)) {
                    realEigenvalues[i] = 0.0;
                }
            }
        }
        eigenvectors = new org.apache.commons.math.linear.ArrayRealVector[n];
        double[] tmp = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmp[j] = z[j][i];
            }
            eigenvectors[i] = new org.apache.commons.math.linear.ArrayRealVector(tmp);
        }
    }
}