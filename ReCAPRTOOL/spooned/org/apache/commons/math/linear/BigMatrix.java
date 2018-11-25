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
 * Interface defining a real-valued matrix with basic algebraic operations, using
 * BigDecimal representations for the entries.
 * <p>
 * Matrix element indexing is 0-based -- e.g., <code>getEntry(0, 0)</code>
 * returns the element in the first row, first column of the matrix.</p>
 *
 * @version $Revision$ $Date$
 * @deprecated as of 2.0, replaced by {@link FieldMatrix} with a {@link
 * org.apache.commons.math.util.BigReal} parameter
 */
@java.lang.Deprecated
public interface BigMatrix extends org.apache.commons.math.linear.AnyMatrix {

    /**
     * Returns a (deep) copy of this.
     *
     * @return matrix copy
     */
    org.apache.commons.math.linear.BigMatrix copy();

    /**
     * Compute the sum of this and m.
     *
     * @param m
     * 		matrix to be added
     * @return this + m
     * @exception IllegalArgumentException if m is not the same size as this
     */     org.apache.commons.math.linear.BigMatrix add(org.apache.commons.math.linear.BigMatrix m) throws java.lang.IllegalArgumentException;

    /**
     * Compute this minus m.
     *
     * @param m
     * 		matrix to be subtracted
     * @return this + m
     * @exception IllegalArgumentException if m is not the same size as this
     */     org.apache.commons.math.linear.BigMatrix subtract(org.apache.commons.math.linear.BigMatrix m) throws java.lang.IllegalArgumentException;

    /**
     * Returns the result of adding d to each entry of this.
     *
     * @param d
     * 		value to be added to each entry
     * @return d + this
     */     org.apache.commons.math.linear.BigMatrix scalarAdd(java.math.BigDecimal d);

    /**
     * Returns the result multiplying each entry of this by d.
     *
     * @param d
     * 		value to multiply all entries by
     * @return d * this
     */     org.apache.commons.math.linear.BigMatrix scalarMultiply(java.math.BigDecimal d);

    /**
     * Returns the result of postmultiplying this by m.
     *
     * @param m
     * 		matrix to postmultiply by
     * @return this * m
     * @throws 
     * 		IllegalArgumentException
     * 		if columnDimension(this) != rowDimension(m)
     */     org.apache.commons.math.linear.BigMatrix multiply(org.apache.commons.math.linear.BigMatrix m) throws java.lang.IllegalArgumentException;
    /**
     * Returns the result premultiplying this by <code>m</code>.
     *
     * @param m
     * 		matrix to premultiply by
     * @return m * this
     * @throws 
     * 		IllegalArgumentException
     * 		if rowDimension(this) != columnDimension(m)
     */     org.apache.commons.math.linear.BigMatrix preMultiply(org.apache.commons.math.linear.BigMatrix m) throws java.lang.IllegalArgumentException;     /**
     * Returns matrix entries as a two-dimensional array.
     *
     * @return 2-dimensional array of entries
     */
    java.math.BigDecimal[][] getData();

    /**
     * Returns matrix entries as a two-dimensional array.
     *
     * @return 2-dimensional array of entries
     */
    double[][] getDataAsDoubleArray();

    /**
     * *
     * Gets the rounding mode
     *
     * @return the rounding mode
     */     int getRoundingMode();
    /**
     * Returns the <a href="http://mathworld.wolfram.com/MaximumAbsoluteRowSumNorm.html">
     * maximum absolute row sum norm</a> of the matrix.
     *
     * @return norm
     */
    java.math.BigDecimal getNorm();

    /**
     * Gets a submatrix. Rows and columns are indicated
     * counting from 0 to n-1.
     *
     * @param startRow
     * 		Initial row index
     * @param endRow
     * 		Final row index
     * @param startColumn
     * 		Initial column index
     * @param endColumn
     * 		Final column index
     * @return The subMatrix containing the data of the
     * specified rows and columns
     * @exception MatrixIndexException  if the indices are not valid
     */     org.apache.commons.math.linear.BigMatrix getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws org.apache.commons.math.linear.MatrixIndexException;     /**
     * Gets a submatrix. Rows and columns are indicated
     * counting from 0 to n-1.
     *
     * @param selectedRows
     * 		Array of row indices.
     * @param selectedColumns
     * 		Array of column indices.
     * @return The subMatrix containing the data in the
     * specified rows and columns
     * @exception MatrixIndexException if row or column selections are not valid
     */     org.apache.commons.math.linear.BigMatrix getSubMatrix(int[] selectedRows, int[] selectedColumns) throws org.apache.commons.math.linear.MatrixIndexException;

    /**
     * Returns the entries in row number <code>row</code>
     * as a row matrix.  Row indices start at 0.
     *
     * @param row
     * 		the row to be fetched
     * @return row matrix
     * @throws MatrixIndexException
     * 		if the specified row index is invalid
     */     org.apache.commons.math.linear.BigMatrix getRowMatrix(int row) throws org.apache.commons.math.linear.MatrixIndexException;
    /**
     * Returns the entries in column number <code>column</code>
     * as a column matrix.  Column indices start at 0.
     *
     * @param column
     * 		the column to be fetched
     * @return column matrix
     * @throws MatrixIndexException
     * 		if the specified column index is invalid
     */     org.apache.commons.math.linear.BigMatrix getColumnMatrix(int column) throws org.apache.commons.math.linear.MatrixIndexException;
    /**
     * Returns the entries in row number <code>row</code> as an array.
     * <p>
     * Row indices start at 0.  A <code>MatrixIndexException</code> is thrown
     * unless <code>0 <= row < rowDimension.</code></p>
     *
     * @param row
     * 		the row to be fetched
     * @return array of entries in the row
     * @throws MatrixIndexException
     * 		if the specified row index is not valid
     */     java.math.BigDecimal[] getRow(int row) throws org.apache.commons.math.linear.MatrixIndexException;
    /**
     * Returns the entries in row number <code>row</code> as an array
     * of double values.
     * <p>
     * Row indices start at 0.  A <code>MatrixIndexException</code> is thrown
     * unless <code>0 <= row < rowDimension.</code></p>
     *
     * @param row
     * 		the row to be fetched
     * @return array of entries in the row
     * @throws MatrixIndexException
     * 		if the specified row index is not valid
     */     double[] getRowAsDoubleArray(int row) throws org.apache.commons.math.linear.MatrixIndexException;
    /**
     * Returns the entries in column number <code>col</code> as an array.
     * <p>
     * Column indices start at 0.  A <code>MatrixIndexException</code> is thrown
     * unless <code>0 <= column < columnDimension.</code></p>
     *
     * @param col
     * 		the column to be fetched
     * @return array of entries in the column
     * @throws MatrixIndexException
     * 		if the specified column index is not valid
     */     java.math.BigDecimal[] getColumn(int col) throws org.apache.commons.math.linear.MatrixIndexException;
    /**
     * Returns the entries in column number <code>col</code> as an array
     * of double values.
     * <p>
     * Column indices start at 0.  A <code>MatrixIndexException</code> is thrown
     * unless <code>0 <= column < columnDimension.</code></p>
     *
     * @param col
     * 		the column to be fetched
     * @return array of entries in the column
     * @throws MatrixIndexException
     * 		if the specified column index is not valid
     */     double[] getColumnAsDoubleArray(int col) throws org.apache.commons.math.linear.MatrixIndexException;
    /**
     * Returns the entry in the specified row and column.
     * <p>
     * Row and column indices start at 0 and must satisfy
     * <ul>
     * <li><code>0 <= row < rowDimension</code></li>
     * <li><code> 0 <= column < columnDimension</code></li>
     * </ul>
     * otherwise a <code>MatrixIndexException</code> is thrown.</p>
     *
     * @param row
     * 		row location of entry to be fetched
     * @param column
     * 		column location of entry to be fetched
     * @return matrix entry in row,column
     * @throws MatrixIndexException
     * 		if the row or column index is not valid
     */     java.math.BigDecimal getEntry(int row, int column) throws org.apache.commons.math.linear.MatrixIndexException;     /**
     * Returns the entry in the specified row and column as a double.
     * <p>
     * Row and column indices start at 0 and must satisfy
     * <ul>
     * <li><code>0 <= row < rowDimension</code></li>
     * <li><code> 0 <= column < columnDimension</code></li>
     * </ul>
     * otherwise a <code>MatrixIndexException</code> is thrown.</p>
     *
     * @param row
     * 		row location of entry to be fetched
     * @param column
     * 		column location of entry to be fetched
     * @return matrix entry in row,column
     * @throws MatrixIndexException
     * 		if the row or column index is not valid
     */     double getEntryAsDouble(int row, int column) throws org.apache.commons.math.linear.MatrixIndexException;     /**
     * Returns the transpose of this matrix.
     *
     * @return transpose matrix
     */
    org.apache.commons.math.linear.BigMatrix transpose();

    /**
     * Returns the inverse of this matrix.
     *
     * @return inverse matrix
     * @throws org.apache.commons.math.linear.InvalidMatrixException
     * 		if
     * 		this is not invertible
     */     org.apache.commons.math.linear.BigMatrix inverse() throws org.apache.commons.math.linear.InvalidMatrixException;

    /**
     * Returns the determinant of this matrix.
     *
     * @return determinant
     * @throws org.apache.commons.math.linear.InvalidMatrixException
     * 		if
     * 		matrix is not square
     */     java.math.BigDecimal getDeterminant() throws org.apache.commons.math.linear.InvalidMatrixException;

    /**
     * Returns the <a href="http://mathworld.wolfram.com/MatrixTrace.html">
     * trace</a> of the matrix (the sum of the elements on the main diagonal).
     *
     * @return trace
     */
    java.math.BigDecimal getTrace();

    /**
     * Returns the result of multiplying this by the vector <code>v</code>.
     *
     * @param v
     * 		the vector to operate on
     * @return this*v
     * @throws IllegalArgumentException
     * 		if columnDimension != v.size()
     */     java.math.BigDecimal[] operate(java.math.BigDecimal[] v) throws java.lang.IllegalArgumentException;
    /**
     * Returns the (row) vector result of premultiplying this by the vector <code>v</code>.
     *
     * @param v
     * 		the row vector to premultiply by
     * @return v*this
     * @throws IllegalArgumentException
     * 		if rowDimension != v.size()
     */     java.math.BigDecimal[] preMultiply(java.math.BigDecimal[] v) throws java.lang.IllegalArgumentException;
    /**
     * Returns the solution vector for a linear system with coefficient
     * matrix = this and constant vector = <code>b</code>.
     *
     * @param b
     * 		constant vector
     * @return vector of solution values to AX = b, where A is *this
     * @throws IllegalArgumentException
     * 		if this.rowDimension != b.length
     * @throws org.apache.commons.math.linear.InvalidMatrixException
     * 		if this matrix is not square or is singular
     */     java.math.BigDecimal[] solve(java.math.BigDecimal[] b) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;     /**
     * Returns a matrix of (column) solution vectors for linear systems with
     * coefficient matrix = this and constant vectors = columns of
     * <code>b</code>.
     *
     * @param b
     * 		matrix of constant vectors forming RHS of linear systems to
     * 		to solve
     * @return matrix of solution vectors
     * @throws IllegalArgumentException
     * 		if this.rowDimension != row dimension
     * @throws org.apache.commons.math.linear.InvalidMatrixException
     * 		if this matrix is not square or is singular
     */     org.apache.commons.math.linear.BigMatrix solve(org.apache.commons.math.linear.BigMatrix b) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;}