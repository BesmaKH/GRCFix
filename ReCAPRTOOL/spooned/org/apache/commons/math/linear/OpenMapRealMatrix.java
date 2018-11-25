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
 * Sparse matrix implementation based on an open addressed map.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public class OpenMapRealMatrix extends org.apache.commons.math.linear.AbstractRealMatrix implements java.io.Serializable , org.apache.commons.math.linear.SparseRealMatrix {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = -5962461716457143437L;
    /**
     * Number of rows of the matrix.
     */     private final int rows;
    /**
     * Number of columns of the matrix.
     */     private final int columns;
    /**
     * Storage for (sparse) matrix elements.
     */     private final org.apache.commons.math.util.OpenIntToDoubleHashMap entries;
    /**
     * Build a sparse matrix with the supplied row and column dimensions.
     *
     * @param rowDimension
     * 		number of rows of the matrix
     * @param columnDimension
     * 		number of columns of the matrix
     */     public OpenMapRealMatrix(int rowDimension, int columnDimension) {         super(rowDimension, columnDimension);         this.rows = rowDimension;
        this.columns = columnDimension;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0);
    }

    /**
     * Build a matrix by copying another one.
     *
     * @param matrix
     * 		matrix to copy
     */     public OpenMapRealMatrix(org.apache.commons.math.linear.OpenMapRealMatrix matrix) {         this.rows = matrix.rows;
        this.columns = matrix.columns;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(matrix.entries);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealMatrix copy() {
        return new org.apache.commons.math.linear.OpenMapRealMatrix(this);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealMatrix createMatrix(int rowDimension, int columnDimension) throws 
    java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.OpenMapRealMatrix(rowDimension, columnDimension);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getColumnDimension() {
        return columns;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealMatrix add(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return add(((org.apache.commons.math.linear.OpenMapRealMatrix) (m)));
        } catch (java.lang.ClassCastException cce) {
            return ((org.apache.commons.math.linear.OpenMapRealMatrix) (super.add(m)));
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
     */     public org.apache.commons.math.linear.OpenMapRealMatrix add(org.apache.commons.math.linear.OpenMapRealMatrix m) throws java.lang.IllegalArgumentException {
        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, m);

        final org.apache.commons.math.linear.OpenMapRealMatrix out = new org.apache.commons.math.linear.OpenMapRealMatrix(this);
        for (org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator = m.entries.iterator(); iterator.hasNext();) {
            iterator.advance();
            final int row = (iterator.key()) / (columns);
            final int col = (iterator.key()) - (row * (columns));
            out.setEntry(row, col, ((getEntry(row, col)) + (iterator.value())));
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealMatrix subtract(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return subtract(((org.apache.commons.math.linear.OpenMapRealMatrix) (m)));
        } catch (java.lang.ClassCastException cce) {
            return ((org.apache.commons.math.linear.OpenMapRealMatrix) (super.subtract(m)));
        }
    }

    /**
     * Compute this minus <code>m</code>.
     *
     * @param m
     * 		matrix to be subtracted
     * @return this - m
     * @throws 
     * 		IllegalArgumentException if m is not the same size as this
     */     public org.apache.commons.math.linear.OpenMapRealMatrix subtract(org.apache.commons.math.linear.OpenMapRealMatrix m) throws java.lang.IllegalArgumentException {
        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, m);

        final org.apache.commons.math.linear.OpenMapRealMatrix out = new org.apache.commons.math.linear.OpenMapRealMatrix(this);
        for (org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator = m.entries.iterator(); iterator.hasNext();) {
            iterator.advance();
            final int row = (iterator.key()) / (columns);
            final int col = (iterator.key()) - (row * (columns));
            out.setEntry(row, col, ((getEntry(row, col)) - (iterator.value())));
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix multiply(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return multiply(((org.apache.commons.math.linear.OpenMapRealMatrix) (m)));
        } catch (java.lang.ClassCastException cce) {

            // safety check
            org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, m);

            final int outCols = m.getColumnDimension();
            final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, outCols);
            for (org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator = entries.iterator(); iterator.hasNext();) {
                iterator.advance();
                final double value = iterator.value();
                final int key = iterator.key();
                final int i = key / (columns);
                final int k = key % (columns);
                for (int j = 0; j < outCols; ++j) {
                    out.addToEntry(i, j, (value * (m.getEntry(k, j))));
                }
            }

            return out;

        }
    }

    /**
     * Returns the result of postmultiplying this by m.
     *
     * @param m
     * 		matrix to postmultiply by
     * @return this * m
     * @throws 
     * 		IllegalArgumentException
     * 		if columnDimension(this) != rowDimension(m)
     */     public org.apache.commons.math.linear.OpenMapRealMatrix multiply(org.apache.commons.math.linear.OpenMapRealMatrix m) throws java.lang.IllegalArgumentException {
        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, m);

        final int outCols = m.getColumnDimension();
        org.apache.commons.math.linear.OpenMapRealMatrix out = new org.apache.commons.math.linear.OpenMapRealMatrix(rows, outCols);
        for (org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator = entries.iterator(); iterator.hasNext();) {
            iterator.advance();
            final double value = iterator.value();
            final int key = iterator.key();
            final int i = key / (columns);
            final int k = key % (columns);
            for (int j = 0; j < outCols; ++j) {
                final int rightKey = m.computeKey(k, j);
                if (m.entries.containsKey(rightKey)) {
                    final int outKey = out.computeKey(i, j);
                    final double outValue = 
                    (out.entries.get(outKey)) + (value * (m.entries.get(rightKey)));
                    if (outValue == 0.0) {
                        out.entries.remove(outKey);
                    }else {
                        out.entries.put(outKey, outValue);
                    }
                }
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getEntry(int row, int column) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        return entries.get(computeKey(row, column));
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getRowDimension() {
        return rows;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setEntry(int row, int column, double value) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        if (value == 0.0) {
            entries.remove(computeKey(row, column));
        }else {
            entries.put(computeKey(row, column), value);
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void addToEntry(int row, int column, double increment) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final int key = computeKey(row, column);
        final double value = (entries.get(key)) + increment;
        if (value == 0.0) {
            entries.remove(key);
        }else {
            entries.put(key, value);
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void multiplyEntry(int row, int column, double factor) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final int key = computeKey(row, column);
        final double value = (entries.get(key)) * factor;
        if (value == 0.0) {
            entries.remove(key);
        }else {
            entries.put(key, value);
        }
    }

    /**
     * Compute the key to access a matrix element
     *
     * @param row
     * 		row index of the matrix element
     * @param column
     * 		column index of the matrix element
     * @return key within the map to access the matrix element
     */     private int computeKey(int row, int column) {         return (row * (columns)) + column;}


}