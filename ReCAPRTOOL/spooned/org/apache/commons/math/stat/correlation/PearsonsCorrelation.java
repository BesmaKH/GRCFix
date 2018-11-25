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
package org.apache.commons.math.stat.correlation;










/**
 * Computes Pearson's product-moment correlation coefficients for pairs of arrays
 * or columns of a matrix.
 *
 * <p>The constructors that take <code>RealMatrix</code> or
 * <code>double[][]</code> arguments generate correlation matrices.  The
 * columns of the input matrices are assumed to represent variable values.
 * Correlations are given by the formula</p>
 * <code>cor(X, Y) = &Sigma;[(x<sub>i</sub> - E(X))(y<sub>i</sub> - E(Y))] / [(n - 1)s(X)s(Y)]</code>
 * where <code>E(X)</code> is the mean of <code>X</code>, <code>E(Y)</code>
 * is the mean of the <code>Y</code> values and s(X), s(Y) are standard deviations.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public class PearsonsCorrelation {

    /**
     * correlation matrix
     */     private final org.apache.commons.math.linear.RealMatrix correlationMatrix;
    /**
     * number of observations
     */     private final int nObs;
    /**
     * Create a PearsonsCorrelation instance without data
     */
    public PearsonsCorrelation() {
        super();
        correlationMatrix = null;
        nObs = 0;
    }

    /**
     * Create a PearsonsCorrelation from a rectangular array
     * whose columns represent values of variables to be correlated.
     *
     * @param data
     * 		rectangular array with columns representing variables
     * @throws IllegalArgumentException
     * 		if the input data array is not
     * 		rectangular with at least two rows and two columns.
     */     public PearsonsCorrelation(double[][] data) {         this(new org.apache.commons.math.linear.BlockRealMatrix(data));
    }

    /**
     * Create a PearsonsCorrelation from a RealMatrix whose columns
     * represent variables to be correlated.
     *
     * @param matrix
     * 		matrix with columns representing variables to correlate
     */     public PearsonsCorrelation(org.apache.commons.math.linear.RealMatrix matrix) {
        checkSufficientData(matrix);
        nObs = matrix.getRowDimension();
        correlationMatrix = computeCorrelationMatrix(matrix);
    }

    /**
     * Create a PearsonsCorrelation from a {@link Covariance}.  The correlation
     * matrix is computed by scaling the Covariance's covariance matrix.
     * The Covariance instance must have been created from a data matrix with
     * columns representing variable values.
     *
     * @param covariance
     * 		Covariance instance
     */     public PearsonsCorrelation(org.apache.commons.math.stat.correlation.Covariance covariance) {
        org.apache.commons.math.linear.RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
        if (covarianceMatrix == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.NULL_COVARIANCE_MATRIX);
        }
        nObs = covariance.getN();
        correlationMatrix = covarianceToCorrelation(covarianceMatrix);
    }

    /**
     * Create a PearsonsCorrelation from a covariance matrix.  The correlation
     * matrix is computed by scaling the covariance matrix.
     *
     * @param covarianceMatrix
     * 		covariance matrix
     * @param numberOfObservations
     * 		the number of observations in the dataset used to compute
     * 		the covariance matrix
     */     public PearsonsCorrelation(org.apache.commons.math.linear.RealMatrix covarianceMatrix, int numberOfObservations) {         nObs = numberOfObservations;
        correlationMatrix = covarianceToCorrelation(covarianceMatrix);

    }

    /**
     * Returns the correlation matrix
     *
     * @return correlation matrix
     */
    public org.apache.commons.math.linear.RealMatrix getCorrelationMatrix() {
        return correlationMatrix;
    }

    /**
     * Returns a matrix of standard errors associated with the estimates
     * in the correlation matrix.<br/>
     * <code>getCorrelationStandardErrors().getEntry(i,j)</code> is the standard
     * error associated with <code>getCorrelationMatrix.getEntry(i,j)</code>
     * <p>The formula used to compute the standard error is <br/>
     * <code>SE<sub>r</sub> = ((1 - r<sup>2</sup>) / (n - 2))<sup>1/2</sup></code>
     * where <code>r</code> is the estimated correlation coefficient and
     * <code>n</code> is the number of observations in the source dataset.</p>
     *
     * @return matrix of correlation standard errors
     */
    public org.apache.commons.math.linear.RealMatrix getCorrelationStandardErrors() {
        int nVars = correlationMatrix.getColumnDimension();
        double[][] out = new double[nVars][nVars];
        for (int i = 0; i < nVars; i++) {
            for (int j = 0; j < nVars; j++) {
                double r = correlationMatrix.getEntry(i, j);
                out[i][j] = java.lang.Math.sqrt(((1 - (r * r)) / ((nObs) - 2)));
            }
        }
        return new org.apache.commons.math.linear.BlockRealMatrix(out);
    }

    /**
     * Returns a matrix of p-values associated with the (two-sided) null
     * hypothesis that the corresponding correlation coefficient is zero.
     * <p><code>getCorrelationPValues().getEntry(i,j)</code> is the probability
     * that a random variable distributed as <code>t<sub>n-2</sub></code> takes
     * a value with absolute value greater than or equal to <br>
     * <code>|r|((n - 2) / (1 - r<sup>2</sup>))<sup>1/2</sup></code></p>
     * <p>The values in the matrix are sometimes referred to as the
     * <i>significance</i> of the corresponding correlation coefficients.</p>
     *
     * @return matrix of p-values
     * @throws MathException
     * 		if an error occurs estimating probabilities
     */     public org.apache.commons.math.linear.RealMatrix getCorrelationPValues() throws org.apache.commons.math.MathException {
        org.apache.commons.math.distribution.TDistribution tDistribution = new org.apache.commons.math.distribution.TDistributionImpl(((nObs) - 2));
        int nVars = correlationMatrix.getColumnDimension();
        double[][] out = new double[nVars][nVars];
        for (int i = 0; i < nVars; i++) {
            for (int j = 0; j < nVars; j++) {
                if (i == j) {
                    out[i][j] = 0.0;
                }else {
                    double r = correlationMatrix.getEntry(i, j);
                    double t = java.lang.Math.abs((r * (java.lang.Math.sqrt((((nObs) - 2) / (1 - (r * r)))))));
                    out[i][j] = 2 * (tDistribution.cumulativeProbability((-t)));
                }
            }
        }
        return new org.apache.commons.math.linear.BlockRealMatrix(out);
    }


    /**
     * Computes the correlation matrix for the columns of the
     * input matrix.
     *
     * @param matrix
     * 		matrix with columns representing variables to correlate
     * @return correlation matrix
     */     public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(org.apache.commons.math.linear.RealMatrix matrix) {
        int nVars = matrix.getColumnDimension();
        org.apache.commons.math.linear.RealMatrix outMatrix = new org.apache.commons.math.linear.BlockRealMatrix(nVars, nVars);
        for (int i = 0; i < nVars; i++) {
            for (int j = 0; j < i; j++) {
                double corr = correlation(matrix.getColumn(i), matrix.getColumn(j));
                outMatrix.setEntry(i, j, corr);
                outMatrix.setEntry(j, i, corr);
            }
            outMatrix.setEntry(i, i, 1.0);
        }
        return outMatrix;
    }

    /**
     * Computes the correlation matrix for the columns of the
     * input rectangular array.  The colums of the array represent values
     * of variables to be correlated.
     *
     * @param data
     * 		matrix with columns representing variables to correlate
     * @return correlation matrix
     */     public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(double[][] data) {
        return computeCorrelationMatrix(new org.apache.commons.math.linear.BlockRealMatrix(data));
    }

    /**
     * Computes the Pearson's product-moment correlation coefficient between the two arrays.
     *
     * </p>Throws IllegalArgumentException if the arrays do not have the same length
     * or their common length is less than 2</p>
     *
     * @param xArray
     * 		first data array
     * @param yArray
     * 		second data array
     * @return Returns Pearson's correlation coefficient for the two arrays
     * @throws 
     * 		IllegalArgumentException if the arrays lengths do not match or
     * 		there is insufficient data
     */     public double correlation(final double[] xArray, final double[] yArray) throws java.lang.IllegalArgumentException {         org.apache.commons.math.stat.regression.SimpleRegression regression = new org.apache.commons.math.stat.regression.SimpleRegression();         if ((xArray.length) != (yArray.length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, xArray.length, yArray.length);
        }else             if ((xArray.length) < 2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_DIMENSION, xArray.length, 2);
            }else {
                for (int i = 0; i < (xArray.length); i++) {
                    regression.addData(xArray[i], yArray[i]);
                }
                return regression.getR();
            }
    }

    /**
     * Derives a correlation matrix from a covariance matrix.
     *
     * <p>Uses the formula <br/>
     * <code>r(X,Y) = cov(X,Y)/s(X)s(Y)</code> where
     * <code>r(&middot,&middot;)</code> is the correlation coefficient and
     * <code>s(&middot;)</code> means standard deviation.</p>
     *
     * @param covarianceMatrix
     * 		the covariance matrix
     * @return correlation matrix
     */     public org.apache.commons.math.linear.RealMatrix covarianceToCorrelation(org.apache.commons.math.linear.RealMatrix covarianceMatrix) {
        int nVars = covarianceMatrix.getColumnDimension();
        org.apache.commons.math.linear.RealMatrix outMatrix = new org.apache.commons.math.linear.BlockRealMatrix(nVars, nVars);
        for (int i = 0; i < nVars; i++) {
            double sigma = java.lang.Math.sqrt(covarianceMatrix.getEntry(i, i));
            outMatrix.setEntry(i, i, 1.0);
            for (int j = 0; j < i; j++) {
                double entry = (covarianceMatrix.getEntry(i, j)) / 
                (sigma * (java.lang.Math.sqrt(covarianceMatrix.getEntry(j, j))));
                outMatrix.setEntry(i, j, entry);
                outMatrix.setEntry(j, i, entry);
            }
        }
        return outMatrix;
    }

    /**
     * Throws IllegalArgumentException of the matrix does not have at least
     * two columns and two rows
     *
     * @param matrix
     * 		matrix to check for sufficiency
     */     private void checkSufficientData(final org.apache.commons.math.linear.RealMatrix matrix) {
        int nRows = matrix.getRowDimension();
        int nCols = matrix.getColumnDimension();
        if ((nRows < 2) || (nCols < 2)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS, 
            nRows, nCols);
        }
    }
}