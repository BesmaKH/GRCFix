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
 * Basic implementation of {@link FieldMatrix} methods regardless of the underlying storage.
 * <p>All the methods implemented here use {@link #getEntry(int, int)} to access
 * matrix elements. Derived class can provide faster implementations. </p>
 *
 * @param <T>
 * 		the type of the field elements
 * @version $Revision$ $Date$
 * @since 2.0
 */ public abstract class AbstractFieldMatrix<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldMatrix<T> {

    /**
     * Field to which the elements belong.
     */     private final org.apache.commons.math.Field<T> field;
    /**
     * Constructor for use with Serializable
     */
    protected AbstractFieldMatrix() {
        field = null;
    }

    /**
     * Creates a matrix with no data
     *
     * @param field
     * 		field to which the elements belong
     */     protected AbstractFieldMatrix(final org.apache.commons.math.Field<T> field) {         this.field = field;
    }

    /**
     * Create a new FieldMatrix<T> with the supplied row and column dimensions.
     *
     * @param field
     * 		field to which the elements belong
     * @param rowDimension
     * 		the number of rows in the new matrix
     * @param columnDimension
     * 		the number of columns in the new matrix
     * @throws IllegalArgumentException
     * 		if row or column dimension is not positive
     */     protected AbstractFieldMatrix(final org.apache.commons.math.Field<T> field, final int rowDimension, final int columnDimension) throws java.lang.IllegalArgumentException {         if (rowDimension < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_DIMENSION, rowDimension, 1);
        }
        if (columnDimension < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_DIMENSION, columnDimension, 1);
        }
        this.field = field;
    }

    /**
     * Get the elements type from an array.
     *
     * @param <T>
     * 		the type of the field elements
     * @param d
     * 		data array
     * @return field to which array elements belong
     * @exception IllegalArgumentException if array is empty
     */     protected static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.Field<T> extractField(final T[][] d) throws java.lang.IllegalArgumentException {         if ((d.length) == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_ROW);
        }
        if ((d[0].length) == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_COLUMN);
        }
        return d[0][0].getField();
    }

    /**
     * Get the elements type from an array.
     *
     * @param <T>
     * 		the type of the field elements
     * @param d
     * 		data array
     * @return field to which array elements belong
     * @exception IllegalArgumentException if array is empty
     */     protected static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.Field<T> extractField(final T[] d) throws java.lang.IllegalArgumentException {         if ((d.length) == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_ROW);
        }
        return d[0].getField();
    }

    /**
     * Build an array of elements.
     * <p>
     * Complete arrays are filled with field.getZero()
     * </p>
     *
     * @param <T>
     * 		the type of the field elements
     * @param field
     * 		field to which array elements belong
     * @param rows
     * 		number of rows
     * @param columns
     * 		number of columns (may be negative to build partial
     * 		arrays in the same way <code>new Field[rows][]</code> works)
     * @return a new array
     */     @java.lang.SuppressWarnings("unchecked")     protected static <T extends org.apache.commons.math.FieldElement<T>> T[][] buildArray(final org.apache.commons.math.Field<T> field, final int rows, final int columns) {         if (columns < 0) {             T[] dummyRow = ((T[]) (java.lang.reflect.Array.newInstance(field.getZero().getClass(), 0)));
            return ((T[][]) (java.lang.reflect.Array.newInstance(dummyRow.getClass(), rows)));
        }
        T[][] array = 
        ((T[][]) (java.lang.reflect.Array.newInstance(field.getZero().getClass(), new int[]{ rows, columns })));
        for (int i = 0; i < (array.length); ++i) {
            java.util.Arrays.fill(array[i], field.getZero());
        }
        return array;
    }

    /**
     * Build an array of elements.
     * <p>
     * Arrays are filled with field.getZero()
     * </p>
     *
     * @param <T>
     * 		the type of the field elements
     * @param field
     * 		field to which array elements belong
     * @param length
     * 		of the array
     * @return a new array
     */     protected static <T extends org.apache.commons.math.FieldElement<T>> T[] buildArray(final org.apache.commons.math.Field<T> field, final int length) {         // OK because field must be correct class         @java.lang.SuppressWarnings("unchecked")         T[] array = ((T[]) (java.lang.reflect.Array.newInstance(field.getZero().getClass(), length)));         java.util.Arrays.fill(array, field.getZero());
        return array;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.Field<T> getField() {         return field;
    }

    /**
     * {@inheritDoc}
     */     public abstract org.apache.commons.math.linear.FieldMatrix<T> createMatrix(final int rowDimension, final int columnDimension) throws java.lang.IllegalArgumentException;

    /**
     * {@inheritDoc}
     */     public abstract org.apache.commons.math.linear.FieldMatrix<T> copy();
    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> add(org.apache.commons.math.linear.FieldMatrix<T> m) throws java.lang.IllegalArgumentException {
        // safety check
        checkAdditionCompatible(m);

        final int rowCount = getRowDimension();
        final int columnCount = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; ++row) {
            for (int col = 0; col < columnCount; ++col) {
                out.setEntry(row, col, getEntry(row, col).add(m.getEntry(row, col)));
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> subtract(final org.apache.commons.math.linear.FieldMatrix<T> m) throws java.lang.IllegalArgumentException {
        // safety check
        checkSubtractionCompatible(m);

        final int rowCount = getRowDimension();
        final int columnCount = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; ++row) {
            for (int col = 0; col < columnCount; ++col) {
                out.setEntry(row, col, getEntry(row, col).subtract(m.getEntry(row, col)));
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> scalarAdd(final T d) {
        final int rowCount = getRowDimension();
        final int columnCount = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; ++row) {
            for (int col = 0; col < columnCount; ++col) {
                out.setEntry(row, col, getEntry(row, col).add(d));
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> scalarMultiply(final T d) {
        final int rowCount = getRowDimension();
        final int columnCount = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; ++row) {
            for (int col = 0; col < columnCount; ++col) {
                out.setEntry(row, col, getEntry(row, col).multiply(d));
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> multiply(final org.apache.commons.math.linear.FieldMatrix<T> m) throws java.lang.IllegalArgumentException {

        // safety check
        checkMultiplicationCompatible(m);

        final int nRows = getRowDimension();
        final int nCols = m.getColumnDimension();
        final int nSum = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(nRows, nCols);
        for (int row = 0; row < nRows; ++row) {
            for (int col = 0; col < nCols; ++col) {
                T sum = field.getZero();
                for (int i = 0; i < nSum; ++i) {
                    sum = sum.add(getEntry(row, i).multiply(m.getEntry(i, col)));
                }
                out.setEntry(row, col, sum);
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> preMultiply(final org.apache.commons.math.linear.FieldMatrix<T> m) throws java.lang.IllegalArgumentException {
        return m.multiply(this);
    }

    /**
     * {@inheritDoc}
     */     public T[][] getData() {
        final T[][] data = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, getRowDimension(), getColumnDimension());

        for (int i = 0; i < (data.length); ++i) {
            final T[] dataI = data[i];
            for (int j = 0; j < (dataI.length); ++j) {
                dataI[j] = getEntry(i, j);
            }
        }

        return data;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> getSubMatrix(final int startRow, final int endRow, final int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);

        final org.apache.commons.math.linear.FieldMatrix<T> subMatrix = 
        createMatrix(((endRow - startRow) + 1), ((endColumn - startColumn) + 1));
        for (int i = startRow; i <= endRow; ++i) {
            for (int j = startColumn; j <= endColumn; ++j) {
                subMatrix.setEntry((i - startRow), (j - startColumn), getEntry(i, j));
            }
        }

        return subMatrix;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> getSubMatrix(final int[] selectedRows, final int[] selectedColumns) throws org.apache.commons.math.linear.MatrixIndexException {

        // safety checks
        checkSubMatrixIndex(selectedRows, selectedColumns);

        // copy entries
        final org.apache.commons.math.linear.FieldMatrix<T> subMatrix = 
        createMatrix(selectedRows.length, selectedColumns.length);
        subMatrix.walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixChangingVisitor<T>(field.getZero()) {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public T visit(final int row, final int column, final T value) {
                return getEntry(selectedRows[row], selectedColumns[column]);
            }

        });

        return subMatrix;

    }

    /**
     * {@inheritDoc}
     */     public void copySubMatrix(final int startRow, final int endRow, final int startColumn, final int endColumn, final 
    T[][] destination) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.linear.MatrixIndexException {

        // safety checks
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        final int rowsCount = (endRow + 1) - startRow;
        final int columnsCount = (endColumn + 1) - startColumn;
        if (((destination.length) < rowsCount) || ((destination[0].length) < columnsCount)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            destination.length, destination[0].length, 
            rowsCount, columnsCount);
        }

        // copy entries
        walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<T>(field.getZero()) {

            /**
             * Initial row index.
             */             private int startRow;
            /**
             * Initial column index.
             */             private int startColumn;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public void start(final int rows, final int columns, final 
            int startRow, final int endRow, final 
            int startColumn, final int endColumn) {
                this.startRow = startRow;
                this.startColumn = startColumn;
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public void visit(final int row, final int column, final T value) {
                destination[(row - (startRow))][(column - (startColumn))] = value;
            }

        }, startRow, endRow, startColumn, endColumn);

    }

    /**
     * {@inheritDoc}
     */     public void copySubMatrix(int[] selectedRows, int[] selectedColumns, T[][] destination) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.MatrixIndexException {

        // safety checks
        checkSubMatrixIndex(selectedRows, selectedColumns);
        if (((destination.length) < (selectedRows.length)) || 
        ((destination[0].length) < (selectedColumns.length))) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            destination.length, destination[0].length, 
            selectedRows.length, selectedColumns.length);
        }

        // copy entries
        for (int i = 0; i < (selectedRows.length); i++) {
            final T[] destinationI = destination[i];
            for (int j = 0; j < (selectedColumns.length); j++) {
                destinationI[j] = getEntry(selectedRows[i], selectedColumns[j]);
            }
        }

    }

    /**
     * {@inheritDoc}
     */     public void setSubMatrix(final T[][] subMatrix, final int row, final int column) throws org.apache.commons.math.linear.MatrixIndexException {

        final int nRows = subMatrix.length;
        if (nRows == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_ROW);
        }

        final int nCols = subMatrix[0].length;
        if (nCols == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_COLUMN);
        }

        for (int r = 1; r < nRows; ++r) {
            if ((subMatrix[r].length) != nCols) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, 
                nCols, subMatrix[r].length);
            }
        }

        checkRowIndex(row);
        checkColumnIndex(column);
        checkRowIndex(((nRows + row) - 1));
        checkColumnIndex(((nCols + column) - 1));

        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nCols; ++j) {
                setEntry((row + i), (column + j), subMatrix[i][j]);
            }
        }

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> getRowMatrix(final int row) throws org.apache.commons.math.linear.MatrixIndexException {

        checkRowIndex(row);
        final int nCols = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(1, nCols);
        for (int i = 0; i < nCols; ++i) {
            out.setEntry(0, i, getEntry(row, i));
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public void setRowMatrix(final int row, final org.apache.commons.math.linear.FieldMatrix<T> matrix) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        checkRowIndex(row);
        final int nCols = getColumnDimension();
        if (((matrix.getRowDimension()) != 1) || 
        ((matrix.getColumnDimension()) != nCols)) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
        }
        for (int i = 0; i < nCols; ++i) {
            setEntry(row, i, matrix.getEntry(0, i));
        }

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> getColumnMatrix(final int column) throws org.apache.commons.math.linear.MatrixIndexException {

        checkColumnIndex(column);
        final int nRows = getRowDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(nRows, 1);
        for (int i = 0; i < nRows; ++i) {
            out.setEntry(i, 0, getEntry(i, column));
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public void setColumnMatrix(final int column, final org.apache.commons.math.linear.FieldMatrix<T> matrix) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        checkColumnIndex(column);
        final int nRows = getRowDimension();
        if (((matrix.getRowDimension()) != nRows) || 
        ((matrix.getColumnDimension()) != 1)) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
        }
        for (int i = 0; i < nRows; ++i) {
            setEntry(i, column, matrix.getEntry(i, 0));
        }

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> getRowVector(final int row) throws org.apache.commons.math.linear.MatrixIndexException {
        return new org.apache.commons.math.linear.ArrayFieldVector<T>(getRow(row), false);
    }

    /**
     * {@inheritDoc}
     */     public void setRowVector(final int row, final org.apache.commons.math.linear.FieldVector<T> vector) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        checkRowIndex(row);
        final int nCols = getColumnDimension();
        if ((vector.getDimension()) != nCols) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            1, vector.getDimension(), 1, nCols);
        }
        for (int i = 0; i < nCols; ++i) {
            setEntry(row, i, vector.getEntry(i));
        }

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> getColumnVector(final int column) throws org.apache.commons.math.linear.MatrixIndexException {
        return new org.apache.commons.math.linear.ArrayFieldVector<T>(getColumn(column), false);
    }

    /**
     * {@inheritDoc}
     */     public void setColumnVector(final int column, final org.apache.commons.math.linear.FieldVector<T> vector) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        checkColumnIndex(column);
        final int nRows = getRowDimension();
        if ((vector.getDimension()) != nRows) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            vector.getDimension(), 1, nRows, 1);
        }
        for (int i = 0; i < nRows; ++i) {
            setEntry(i, column, vector.getEntry(i));
        }

    }

    /**
     * {@inheritDoc}
     */     public T[] getRow(final int row) throws org.apache.commons.math.linear.MatrixIndexException {

        checkRowIndex(row);
        final int nCols = getColumnDimension();
        final T[] out = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, nCols);
        for (int i = 0; i < nCols; ++i) {
            out[i] = getEntry(row, i);
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public void setRow(final int row, final T[] array) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        checkRowIndex(row);
        final int nCols = getColumnDimension();
        if ((array.length) != nCols) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            1, array.length, 1, nCols);
        }
        for (int i = 0; i < nCols; ++i) {
            setEntry(row, i, array[i]);
        }

    }

    /**
     * {@inheritDoc}
     */     public T[] getColumn(final int column) throws org.apache.commons.math.linear.MatrixIndexException {

        checkColumnIndex(column);
        final int nRows = getRowDimension();
        final T[] out = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, nRows);
        for (int i = 0; i < nRows; ++i) {
            out[i] = getEntry(i, column);
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public void setColumn(final int column, final T[] array) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        checkColumnIndex(column);
        final int nRows = getRowDimension();
        if ((array.length) != nRows) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            array.length, 1, nRows, 1);
        }
        for (int i = 0; i < nRows; ++i) {
            setEntry(i, column, array[i]);
        }

    }

    /**
     * {@inheritDoc}
     */     public abstract T getEntry(int row, int column) throws org.apache.commons.math.linear.MatrixIndexException;

    /**
     * {@inheritDoc}
     */     public abstract void setEntry(int row, int column, T value) throws org.apache.commons.math.linear.MatrixIndexException;

    /**
     * {@inheritDoc}
     */     public abstract void addToEntry(int row, int column, T increment) throws org.apache.commons.math.linear.MatrixIndexException;

    /**
     * {@inheritDoc}
     */     public abstract void multiplyEntry(int row, int column, T factor) throws org.apache.commons.math.linear.MatrixIndexException;

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> transpose() {
        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        final org.apache.commons.math.linear.FieldMatrix<T> out = createMatrix(nCols, nRows);
        walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<T>(field.getZero()) {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public void visit(final int row, final int column, final T value) {
                out.setEntry(column, row, value);
            }

        });

        return out;

    }

    /**
     * {@inheritDoc}
     */     public boolean isSquare() {         return (getColumnDimension()) == (getRowDimension());
    }

    /**
     * {@inheritDoc}
     */     public abstract int getRowDimension();
    /**
     * {@inheritDoc}
     */     public abstract int getColumnDimension();
    /**
     * {@inheritDoc}
     */     public T getTrace() throws org.apache.commons.math.linear.NonSquareMatrixException {
        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        if (nRows != nCols) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(nRows, nCols);
        }
        T trace = field.getZero();
        for (int i = 0; i < nRows; ++i) {
            trace = trace.add(getEntry(i, i));
        }
        return trace;
    }

    /**
     * {@inheritDoc}
     */     public T[] operate(final T[] v) throws java.lang.IllegalArgumentException {

        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        if ((v.length) != nCols) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            v.length, nCols);
        }

        final T[] out = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, nRows);
        for (int row = 0; row < nRows; ++row) {
            T sum = field.getZero();
            for (int i = 0; i < nCols; ++i) {
                sum = sum.add(getEntry(row, i).multiply(v[i]));
            }
            out[row] = sum;
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> operate(final org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {
        try {
            return new org.apache.commons.math.linear.ArrayFieldVector<T>(operate(((org.apache.commons.math.linear.ArrayFieldVector<T>) (v)).getDataRef()), false);
        } catch (java.lang.ClassCastException cce) {
            final int nRows = getRowDimension();
            final int nCols = getColumnDimension();
            if ((v.getDimension()) != nCols) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
                v.getDimension(), nCols);
            }

            final T[] out = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, nRows);
            for (int row = 0; row < nRows; ++row) {
                T sum = field.getZero();
                for (int i = 0; i < nCols; ++i) {
                    sum = sum.add(getEntry(row, i).multiply(v.getEntry(i)));
                }
                out[row] = sum;
            }

            return new org.apache.commons.math.linear.ArrayFieldVector<T>(out, false);
        }
    }

    /**
     * {@inheritDoc}
     */     public T[] preMultiply(final T[] v) throws java.lang.IllegalArgumentException {

        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        if ((v.length) != nRows) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            v.length, nRows);
        }

        final T[] out = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, nCols);
        for (int col = 0; col < nCols; ++col) {
            T sum = field.getZero();
            for (int i = 0; i < nRows; ++i) {
                sum = sum.add(getEntry(i, col).multiply(v[i]));
            }
            out[col] = sum;
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> preMultiply(final org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {
        try {
            return new org.apache.commons.math.linear.ArrayFieldVector<T>(preMultiply(((org.apache.commons.math.linear.ArrayFieldVector<T>) (v)).getDataRef()), false);
        } catch (java.lang.ClassCastException cce) {

            final int nRows = getRowDimension();
            final int nCols = getColumnDimension();
            if ((v.getDimension()) != nRows) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
                v.getDimension(), nRows);
            }

            final T[] out = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, nCols);
            for (int col = 0; col < nCols; ++col) {
                T sum = field.getZero();
                for (int i = 0; i < nRows; ++i) {
                    sum = sum.add(getEntry(i, col).multiply(v.getEntry(i)));
                }
                out[col] = sum;
            }

            return new org.apache.commons.math.linear.ArrayFieldVector<T>(out);

        }
    }

    /**
     * {@inheritDoc}
     */     public T walkInRowOrder(final org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> visitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                final T oldValue = getEntry(row, column);
                final T newValue = visitor.visit(row, column, oldValue);
                setEntry(row, column, newValue);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInRowOrder(final org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> visitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInRowOrder(final org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> visitor, final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int row = startRow; row <= endRow; ++row) {
            for (int column = startColumn; column <= endColumn; ++column) {
                final T oldValue = getEntry(row, column);
                final T newValue = visitor.visit(row, column, oldValue);
                setEntry(row, column, newValue);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInRowOrder(final org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> visitor, final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int row = startRow; row <= endRow; ++row) {
            for (int column = startColumn; column <= endColumn; ++column) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInColumnOrder(final org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> visitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int column = 0; column < columns; ++column) {
            for (int row = 0; row < rows; ++row) {
                final T oldValue = getEntry(row, column);
                final T newValue = visitor.visit(row, column, oldValue);
                setEntry(row, column, newValue);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInColumnOrder(final org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> visitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        final int rows = getRowDimension();
        final int columns = getColumnDimension();
        visitor.start(rows, columns, 0, (rows - 1), 0, (columns - 1));
        for (int column = 0; column < columns; ++column) {
            for (int row = 0; row < rows; ++row) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInColumnOrder(final org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> visitor, final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int column = startColumn; column <= endColumn; ++column) {
            for (int row = startRow; row <= endRow; ++row) {
                final T oldValue = getEntry(row, column);
                final T newValue = visitor.visit(row, column, oldValue);
                setEntry(row, column, newValue);
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInColumnOrder(final org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> visitor, final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), 
        startRow, endRow, startColumn, endColumn);
        for (int column = startColumn; column <= endColumn; ++column) {
            for (int row = startRow; row <= endRow; ++row) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     public T walkInOptimizedOrder(final org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> visitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(visitor);
    }

    /**
     * {@inheritDoc}
     */     public T walkInOptimizedOrder(final org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> visitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(visitor);
    }

    /**
     * {@inheritDoc}
     */     public T walkInOptimizedOrder(final org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> visitor, final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
    }

    /**
     * {@inheritDoc}
     */     public T walkInOptimizedOrder(final org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> visitor, final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
    }

    /**
     * Get a string representation for this matrix.
     *
     * @return a string representation for this matrix
     */     @java.lang.Override
    public java.lang.String toString() {
        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        final java.lang.StringBuffer res = new java.lang.StringBuffer();
        java.lang.String fullClassName = getClass().getName();
        java.lang.String shortClassName = fullClassName.substring(((fullClassName.lastIndexOf('.')) + 1));
        res.append(shortClassName).append("{");

        for (int i = 0; i < nRows; ++i) {
            if (i > 0) {
                res.append(",");
            }
            res.append("{");
            for (int j = 0; j < nCols; ++j) {
                if (j > 0) {
                    res.append(",");
                }
                res.append(getEntry(i, j));
            }
            res.append("}");
        }

        res.append("}");
        return res.toString();

    }

    /**
     * Returns true iff <code>object</code> is a
     * <code>FieldMatrix</code> instance with the same dimensions as this
     * and all corresponding matrix entries are equal.
     *
     * @param object
     * 		the object to test equality against.
     * @return true if object equals this
     */     @java.lang.Override
    public boolean equals(final java.lang.Object object) {
        if (object == (this)) {
            return true;
        }
        if ((object instanceof org.apache.commons.math.linear.FieldMatrix<?>) == false) {
            return false;
        }
        org.apache.commons.math.linear.FieldMatrix<?> m = ((org.apache.commons.math.linear.FieldMatrix<?>) (object));
        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        if (((m.getColumnDimension()) != nCols) || ((m.getRowDimension()) != nRows)) {
            return false;
        }
        for (int row = 0; row < nRows; ++row) {
            for (int col = 0; col < nCols; ++col) {
                if (!(getEntry(row, col).equals(m.getEntry(row, col)))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Computes a hashcode for the matrix.
     *
     * @return hashcode for matrix
     */
    @java.lang.Override
    public int hashCode() {
        int ret = 322562;
        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        ret = (ret * 31) + nRows;
        ret = (ret * 31) + nCols;
        for (int row = 0; row < nRows; ++row) {
            for (int col = 0; col < nCols; ++col) {
                ret = (ret * 31) + (((11 * (row + 1)) + (17 * (col + 1))) * (getEntry(row, col).hashCode()));
            }
        }
        return ret;
    }

    /**
     * Check if a row index is valid.
     *
     * @param row
     * 		row index to check
     * @exception MatrixIndexException if index is not valid
     */     protected void checkRowIndex(final int row) {         if ((row < 0) || (row >= (getRowDimension()))) {
            throw new org.apache.commons.math.linear.MatrixIndexException("row index {0} out of allowed range [{1}, {2}]", 
            row, 0, ((getRowDimension()) - 1));
        }
    }

    /**
     * Check if a column index is valid.
     *
     * @param column
     * 		column index to check
     * @exception MatrixIndexException if index is not valid
     */     protected void checkColumnIndex(final int column) throws org.apache.commons.math.linear.MatrixIndexException {
        if ((column < 0) || (column >= (getColumnDimension()))) {
            throw new org.apache.commons.math.linear.MatrixIndexException("column index {0} out of allowed range [{1}, {2}]", 
            column, 0, ((getColumnDimension()) - 1));
        }
    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param startRow
     * 		Initial row index
     * @param endRow
     * 		Final row index
     * @param startColumn
     * 		Initial column index
     * @param endColumn
     * 		Final column index
     * @exception MatrixIndexException  if the indices are not valid
     */     protected void checkSubMatrixIndex(final int startRow, final int endRow, final int startColumn, final int endColumn) {         checkRowIndex(startRow);         checkRowIndex(endRow);
        if (startRow > endRow) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.util.LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, 
            startRow, endRow);
        }

        checkColumnIndex(startColumn);
        checkColumnIndex(endColumn);
        if (startColumn > endColumn) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.util.LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, 
            startColumn, endColumn);
        }


    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param selectedRows
     * 		Array of row indices.
     * @param selectedColumns
     * 		Array of column indices.
     * @exception MatrixIndexException if row or column selections are not valid
     */     protected void checkSubMatrixIndex(final int[] selectedRows, final int[] selectedColumns) {         if (((selectedRows.length) * (selectedColumns.length)) == 0) {
            if ((selectedRows.length) == 0) {
                throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.util.LocalizedFormats.EMPTY_SELECTED_ROW_INDEX_ARRAY);
            }
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.util.LocalizedFormats.EMPTY_SELECTED_COLUMN_INDEX_ARRAY);
        }

        for (final int row : selectedRows) {
            checkRowIndex(row);
        }
        for (final int column : selectedColumns) {
            checkColumnIndex(column);
        }
    }

    /**
     * Check if a matrix is addition compatible with the instance
     *
     * @param m
     * 		matrix to check
     * @exception IllegalArgumentException if matrix is not addition compatible with instance
     */     protected void checkAdditionCompatible(final org.apache.commons.math.linear.FieldMatrix<T> m) {         if (((getRowDimension()) != (m.getRowDimension())) || 
        ((getColumnDimension()) != (m.getColumnDimension()))) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_ADDITION_COMPATIBLE_MATRICES, 
            getRowDimension(), getColumnDimension(), 
            m.getRowDimension(), m.getColumnDimension());
        }
    }

    /**
     * Check if a matrix is subtraction compatible with the instance
     *
     * @param m
     * 		matrix to check
     * @exception IllegalArgumentException if matrix is not subtraction compatible with instance
     */     protected void checkSubtractionCompatible(final org.apache.commons.math.linear.FieldMatrix<T> m) {         if (((getRowDimension()) != (m.getRowDimension())) || 
        ((getColumnDimension()) != (m.getColumnDimension()))) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_SUBTRACTION_COMPATIBLE_MATRICES, 
            getRowDimension(), getColumnDimension(), 
            m.getRowDimension(), m.getColumnDimension());
        }
    }

    /**
     * Check if a matrix is multiplication compatible with the instance
     *
     * @param m
     * 		matrix to check
     * @exception IllegalArgumentException if matrix is not multiplication compatible with instance
     */     protected void checkMultiplicationCompatible(final org.apache.commons.math.linear.FieldMatrix<T> m) {         if ((getColumnDimension()) != (m.getRowDimension())) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_MULTIPLICATION_COMPATIBLE_MATRICES, 
            getRowDimension(), getColumnDimension(), 
            m.getRowDimension(), m.getColumnDimension());
        }
    }

}