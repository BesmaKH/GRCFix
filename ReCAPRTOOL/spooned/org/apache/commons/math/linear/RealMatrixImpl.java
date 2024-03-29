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
 * Implementation of RealMatrix using a double[][] array to store entries and
 * <a href="http://www.math.gatech.edu/~bourbaki/math2601/Web-notes/2num.pdf">
 * LU decomposition</a> to support linear system
 * solution and inverse.
 * <p>
 * The LU decomposition is performed as needed, to support the following operations: <ul>
 * <li>solve</li>
 * <li>isSingular</li>
 * <li>getDeterminant</li>
 * <li>inverse</li> </ul></p>
 * <p>
 * <strong>Usage notes</strong>:<br>
 * <ul><li>
 * The LU decomposition is cached and reused on subsequent calls.
 * If data are modified via references to the underlying array obtained using
 * <code>getDataRef()</code>, then the stored LU decomposition will not be
 * discarded.  In this case, you need to explicitly invoke
 * <code>LUDecompose()</code> to recompute the decomposition
 * before using any of the methods above.</li>
 * <li>
 * As specified in the {@link RealMatrix} interface, matrix element indexing
 * is 0-based -- e.g., <code>getEntry(0, 0)</code>
 * returns the element in the first row, first column of the matrix.</li></ul>
 * </p>
 *
 * @version $Revision$ $Date$
 * @deprecated as of 2.0 replaced by {@link Array2DRowRealMatrix}
 */
@java.lang.Deprecated
public class RealMatrixImpl extends org.apache.commons.math.linear.AbstractRealMatrix implements java.io.Serializable {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -1067294169172445528L;
    /**
     * Entries of the matrix
     */     protected double[][] data;
    /**
     * Creates a matrix with no data
     */
    public RealMatrixImpl() {
    }

    /**
     * Create a new RealMatrix with the supplied row and column dimensions.
     *
     * @param rowDimension
     * 		the number of rows in the new matrix
     * @param columnDimension
     * 		the number of columns in the new matrix
     * @throws IllegalArgumentException
     * 		if row or column dimension is not
     * 		positive
     */     public RealMatrixImpl(final int rowDimension, final int columnDimension) throws java.lang.IllegalArgumentException {         super(rowDimension, columnDimension);
        data = new double[rowDimension][columnDimension];
    }

    /**
     * Create a new RealMatrix using the input array as the underlying
     * data array.
     * <p>The input array is copied, not referenced. This constructor has
     * the same effect as calling {@link #RealMatrixImpl(double[][], boolean)}
     * with the second argument set to <code>true</code>.</p>
     *
     * @param d
     * 		data for new matrix
     * @throws IllegalArgumentException
     * 		if <code>d</code> is not rectangular
     * 		(not all rows have the same length) or empty
     * @throws NullPointerException
     * 		if <code>d</code> is null
     * @see #RealMatrixImpl(double[][], boolean)
     */     public RealMatrixImpl(final double[][] d) throws java.lang.IllegalArgumentException, java.lang.NullPointerException {         copyIn(d);
    }

    /**
     * Create a new RealMatrix using the input array as the underlying
     * data array.
     * <p>If an array is built specially in order to be embedded in a
     * RealMatrix and not used directly, the <code>copyArray</code> may be
     * set to <code>false</code. This will prevent the copying and improve
     * performance as no new array will be built and no data will be copied.</p>
     *
     * @param d
     * 		data for new matrix
     * @param copyArray
     * 		if true, the input array will be copied, otherwise
     * 		it will be referenced
     * @throws IllegalArgumentException
     * 		if <code>d</code> is not rectangular
     * 		(not all rows have the same length) or empty
     * @throws NullPointerException
     * 		if <code>d</code> is null
     * @see #RealMatrixImpl(double[][])
     */     public RealMatrixImpl(final double[][] d, final boolean copyArray) throws java.lang.IllegalArgumentException, java.lang.NullPointerException {         if (copyArray) {             copyIn(d);}else {
            if (d == null) {
                throw new java.lang.NullPointerException();
            }
            final int nRows = d.length;
            if (nRows == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_ROW);
            }
            final int nCols = d[0].length;
            if (nCols == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_COLUMN);
            }
            for (int r = 1; r < nRows; r++) {
                if ((d[r].length) != nCols) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, 
                    nCols, d[r].length);
                }
            }
            data = d;
        }
    }

    /**
     * Create a new (column) RealMatrix using <code>v</code> as the
     * data for the unique column of the <code>v.length x 1</code> matrix
     * created.
     * <p>The input array is copied, not referenced.</p>
     *
     * @param v
     * 		column vector holding data for new matrix
     */     public RealMatrixImpl(final double[] v) {
        final int nRows = v.length;
        data = new double[nRows][1];
        for (int row = 0; row < nRows; row++) {
            data[row][0] = v[row];
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix createMatrix(final int rowDimension, final int columnDimension) throws 
    java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.RealMatrixImpl(rowDimension, columnDimension);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix copy() {
        return new org.apache.commons.math.linear.RealMatrixImpl(copyOut(), false);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix add(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return add(((org.apache.commons.math.linear.RealMatrixImpl) (m)));
        } catch (java.lang.ClassCastException cce) {
            return super.add(m);
        }
    }

    /**
     * Compute the sum of this and <code>m</code>.
     *
     * @param m
     * 		matrix to be added
     * @return this + m
     * @throws 
     * 		IllegalArgumentException if m is not the same size as this
     */     public org.apache.commons.math.linear.RealMatrixImpl add(final org.apache.commons.math.linear.RealMatrixImpl m) throws java.lang.IllegalArgumentException {

        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, m);

        final int rowCount = getRowDimension();
        final int columnCount = getColumnDimension();
        final double[][] outData = new double[rowCount][columnCount];
        for (int row = 0; row < rowCount; row++) {
            final double[] dataRow = data[row];
            final double[] mRow = m.data[row];
            final double[] outDataRow = outData[row];
            for (int col = 0; col < columnCount; col++) {
                outDataRow[col] = (dataRow[col]) + (mRow[col]);
            }
        }

        return new org.apache.commons.math.linear.RealMatrixImpl(outData, false);

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix subtract(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return subtract(((org.apache.commons.math.linear.RealMatrixImpl) (m)));
        } catch (java.lang.ClassCastException cce) {
            return super.subtract(m);
        }
    }

    /**
     * Compute  this minus <code>m</code>.
     *
     * @param m
     * 		matrix to be subtracted
     * @return this + m
     * @throws 
     * 		IllegalArgumentException if m is not the same size as this
     */     public org.apache.commons.math.linear.RealMatrixImpl subtract(final org.apache.commons.math.linear.RealMatrixImpl m) throws java.lang.IllegalArgumentException {

        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, m);

        final int rowCount = getRowDimension();
        final int columnCount = getColumnDimension();
        final double[][] outData = new double[rowCount][columnCount];
        for (int row = 0; row < rowCount; row++) {
            final double[] dataRow = data[row];
            final double[] mRow = m.data[row];
            final double[] outDataRow = outData[row];
            for (int col = 0; col < columnCount; col++) {
                outDataRow[col] = (dataRow[col]) - (mRow[col]);
            }
        }

        return new org.apache.commons.math.linear.RealMatrixImpl(outData, false);

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix multiply(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return multiply(((org.apache.commons.math.linear.RealMatrixImpl) (m)));
        } catch (java.lang.ClassCastException cce) {
            return super.multiply(m);
        }
    }

    /**
     * Returns the result of postmultiplying this by <code>m</code>.
     *
     * @param m
     * 		matrix to postmultiply by
     * @return this*m
     * @throws 
     * 		IllegalArgumentException
     * 		if columnDimension(this) != rowDimension(m)
     */     public org.apache.commons.math.linear.RealMatrixImpl multiply(final org.apache.commons.math.linear.RealMatrixImpl m) throws java.lang.IllegalArgumentException {
        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, m);

        final int nRows = this.getRowDimension();
        final int nCols = m.getColumnDimension();
        final int nSum = this.getColumnDimension();
        final double[][] outData = new double[nRows][nCols];
        for (int row = 0; row < nRows; row++) {
            final double[] dataRow = data[row];
            final double[] outDataRow = outData[row];
            for (int col = 0; col < nCols; col++) {
                double sum = 0;
                for (int i = 0; i < nSum; i++) {
                    sum += (dataRow[i]) * (m.data[i][col]);
                }
                outDataRow[col] = sum;
            }
        }

        return new org.apache.commons.math.linear.RealMatrixImpl(outData, false);

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[][] getData() {
        return copyOut();
    }

    /**
     * Returns a reference to the underlying data array.
     * <p>
     * Does <strong>not</strong> make a fresh copy of the underlying data.</p>
     *
     * @return 2-dimensional array of entries
     */
    public double[][] getDataRef() {
        return data;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setSubMatrix(final double[][] subMatrix, final int row, final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        if ((data) == null) {
            if (row > 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
                org.apache.commons.math.util.LocalizedFormats.FIRST_ROWS_NOT_INITIALIZED_YET, 
                row);
            }
            if (column > 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
                org.apache.commons.math.util.LocalizedFormats.FIRST_COLUMNS_NOT_INITIALIZED_YET, 
                column);
            }
            final int nRows = subMatrix.length;
            if (nRows == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_ROW);
            }

            final int nCols = subMatrix[0].length;
            if (nCols == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_COLUMN);
            }
            data = new double[subMatrix.length][nCols];
            for (int i = 0; i < (data.length); ++i) {
                if ((subMatrix[i].length) != nCols) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, 
                    nCols, subMatrix[i].length);
                }
                java.lang.System.arraycopy(subMatrix[i], 0, data[(i + row)], column, nCols);
            }
        }else {
            super.setSubMatrix(subMatrix, row, column);
        }

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getEntry(final int row, final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        try {
            return data[row][column];
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            org.apache.commons.math.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, 
            row, column, getRowDimension(), getColumnDimension());
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setEntry(final int row, final int column, final double value) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        try {
            data[row][column] = value;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            org.apache.commons.math.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, 
            row, column, getRowDimension(), getColumnDimension());
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void addToEntry(final int row, final int column, final double increment) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        try {
            data[row][column] += increment;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            org.apache.commons.math.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, 
            row, column, getRowDimension(), getColumnDimension());
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void multiplyEntry(final int row, final int column, final double factor) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        try {
            data[row][column] *= factor;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            org.apache.commons.math.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, 
            row, column, getRowDimension(), getColumnDimension());
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getRowDimension() {
        return (data) == null ? 0 : data.length;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getColumnDimension() {
        return ((data) == null) || ((data[0]) == null) ? 0 : data[0].length;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] operate(final double[] v) throws 
    java.lang.IllegalArgumentException {
        final int nRows = this.getRowDimension();
        final int nCols = this.getColumnDimension();
        if ((v.length) != nCols) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            v.length, nCols);
        }
        final double[] out = new double[nRows];
        for (int row = 0; row < nRows; row++) {
            final double[] dataRow = data[row];
            double sum = 0;
            for (int i = 0; i < nCols; i++) {
                sum += (dataRow[i]) * (v[i]);
            }
            out[row] = sum;
        }
        return out;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] preMultiply(final double[] v) throws 
    java.lang.IllegalArgumentException {

        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        if ((v.length) != nRows) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            v.length, nRows);
        }

        final double[] out = new double[nCols];
        for (int col = 0; col < nCols; ++col) {
            double sum = 0;
            for (int i = 0; i < nRows; ++i) {
                sum += (data[i][col]) * (v[i]);
            }
            out[col] = sum;
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInRowOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int i = 0; i < rows; ++i) {
            final double[] rowI = data[i];
            for (int j = 0; j < columns; ++j) {
                rowI[j] = visitor.visit(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInRowOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int i = 0; i < rows; ++i) {
            final double[] rowI = data[i];
            for (int j = 0; j < columns; ++j) {
                visitor.visit(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInRowOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor, final 
    int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int i = startRow; i <= endRow; ++i) {
            final double[] rowI = data[i];
            for (int j = startColumn; j <= endColumn; ++j) {
                rowI[j] = visitor.visit(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInRowOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor, final 
    int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int i = startRow; i <= endRow; ++i) {
            final double[] rowI = data[i];
            for (int j = startColumn; j <= endColumn; ++j) {
                visitor.visit(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInColumnOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int j = 0; j < columns; ++j) {
            for (int i = 0; i < rows; ++i) {
                final double[] rowI = data[i];
                rowI[j] = visitor.visit(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInColumnOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int j = 0; j < columns; ++j) {
            for (int i = 0; i < rows; ++i) {
                visitor.visit(i, j, data[i][j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInColumnOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor, final 
    int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int j = startColumn; j <= endColumn; ++j) {
            for (int i = startRow; i <= endRow; ++i) {
                final double[] rowI = data[i];
                rowI[j] = visitor.visit(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInColumnOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor, final 
    int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int j = startColumn; j <= endColumn; ++j) {
            for (int i = startRow; i <= endRow; ++i) {
                visitor.visit(i, j, data[i][j]);
            }
        }
        return visitor.end();
    }

    /**
     * Returns a fresh copy of the underlying data array.
     *
     * @return a copy of the underlying data array.
     */
    private double[][] copyOut() {
        final int nRows = this.getRowDimension();
        final double[][] out = new double[nRows][this.getColumnDimension()];
        // can't copy 2-d array in one shot, otherwise get row references
        for (int i = 0; i < nRows; i++) {
            java.lang.System.arraycopy(data[i], 0, out[i], 0, data[i].length);
        }
        return out;
    }

    /**
     * Replaces data with a fresh copy of the input array.
     * <p>
     * Verifies that the input array is rectangular and non-empty.</p>
     *
     * @param in
     * 		data to copy in
     * @throws IllegalArgumentException
     * 		if input array is empty or not
     * 		rectangular
     * @throws NullPointerException
     * 		if input array is null
     */     private void copyIn(final double[][] in) {         setSubMatrix(in, 0, 0);}

}