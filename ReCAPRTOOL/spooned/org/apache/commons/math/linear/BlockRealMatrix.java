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
 * Cache-friendly implementation of RealMatrix using a flat arrays to store
 * square blocks of the matrix.
 * <p>
 * This implementation is specially designed to be cache-friendly. Square blocks are
 * stored as small arrays and allow efficient traversal of data both in row major direction
 * and columns major direction, one block at a time. This greatly increases performances
 * for algorithms that use crossed directions loops like multiplication or transposition.
 * </p>
 * <p>
 * The size of square blocks is a static parameter. It may be tuned according to the cache
 * size of the target computer processor. As a rule of thumbs, it should be the largest
 * value that allows three blocks to be simultaneously cached (this is necessary for example
 * for matrix multiplication). The default value is to use 52x52 blocks which is well suited
 * for processors with 64k L1 cache (one block holds 2704 values or 21632 bytes). This value
 * could be lowered to 36x36 for processors with 32k L1 cache.
 * </p>
 * <p>
 * The regular blocks represent {@link #BLOCK_SIZE} x {@link #BLOCK_SIZE} squares. Blocks
 * at right hand side and bottom side which may be smaller to fit matrix dimensions. The square
 * blocks are flattened in row major order in single dimension arrays which are therefore
 * {@link #BLOCK_SIZE}<sup>2</sup> elements long for regular blocks. The blocks are themselves
 * organized in row major order.
 * </p>
 * <p>
 * As an example, for a block size of 52x52, a 100x60 matrix would be stored in 4 blocks.
 * Block 0 would be a double[2704] array holding the upper left 52x52 square, block 1 would be
 * a double[416] array holding the upper right 52x8 rectangle, block 2 would be a double[2496]
 * array holding the lower left 48x52 rectangle and block 3 would be a double[384] array
 * holding the lower right 48x8 rectangle.
 * </p>
 * <p>
 * The layout complexity overhead versus simple mapping of matrices to java
 * arrays is negligible for small matrices (about 1%). The gain from cache efficiency leads
 * to up to 3-fold improvements for matrices of moderate to large size.
 * </p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class BlockRealMatrix extends org.apache.commons.math.linear.AbstractRealMatrix implements java.io.Serializable {

    /**
     * Block size.
     */     public static final int BLOCK_SIZE = 52;
    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 4991895511313664478L;
    /**
     * Blocks of matrix entries.
     */     private final double[][] blocks;
    /**
     * Number of rows of the matrix.
     */     private final int rows;
    /**
     * Number of columns of the matrix.
     */     private final int columns;
    /**
     * Number of block rows of the matrix.
     */     private final int blockRows;
    /**
     * Number of block columns of the matrix.
     */     private final int blockColumns;
    /**
     * Create a new matrix with the supplied row and column dimensions.
     *
     * @param rows
     * 		the number of rows in the new matrix
     * @param columns
     * 		the number of columns in the new matrix
     * @throws IllegalArgumentException
     * 		if row or column dimension is not
     * 		positive
     */     public BlockRealMatrix(final int rows, final int columns) throws java.lang.IllegalArgumentException {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;

        // number of blocks
        blockRows = ((rows + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        blockColumns = ((columns + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);

        // allocate storage blocks, taking care of smaller ones at right and bottom
        blocks = org.apache.commons.math.linear.BlockRealMatrix.createBlocksLayout(rows, columns);

    }

    /**
     * Create a new dense matrix copying entries from raw layout data.
     * <p>The input array <em>must</em> already be in raw layout.</p>
     * <p>Calling this constructor is equivalent to call:
     * <pre>matrix = new BlockRealMatrix(rawData.length, rawData[0].length,
     *                                   toBlocksLayout(rawData), false);</pre>
     * </p>
     *
     * @param rawData
     * 		data for new matrix, in raw layout
     * @exception IllegalArgumentException if <code>blockData</code> shape is
     * inconsistent with block layout
     * @see #BlockRealMatrix(int, int, double[][], boolean)
     */     public BlockRealMatrix(final double[][] rawData) throws 
    java.lang.IllegalArgumentException {
        this(rawData.length, rawData[0].length, org.apache.commons.math.linear.BlockRealMatrix.toBlocksLayout(rawData), false);
    }

    /**
     * Create a new dense matrix copying entries from block layout data.
     * <p>The input array <em>must</em> already be in blocks layout.</p>
     *
     * @param rows
     * 		the number of rows in the new matrix
     * @param columns
     * 		the number of columns in the new matrix
     * @param blockData
     * 		data for new matrix
     * @param copyArray
     * 		if true, the input array will be copied, otherwise
     * 		it will be referenced
     * @exception IllegalArgumentException if <code>blockData</code> shape is
     * inconsistent with block layout
     * @see #createBlocksLayout(int, int)
     * @see #toBlocksLayout(double[][])
     * @see #BlockRealMatrix(double[][])
     */     public BlockRealMatrix(final int rows, final int columns, final double[][] blockData, final boolean copyArray) throws java.lang.IllegalArgumentException {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;

        // number of blocks
        blockRows = ((rows + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        blockColumns = ((columns + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);

        if (copyArray) {
            // allocate storage blocks, taking care of smaller ones at right and bottom
            blocks = new double[(blockRows) * (blockColumns)][];
        }else {
            // reference existing array
            blocks = blockData;
        }

        int index = 0;
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            for (int jBlock = 0; jBlock < (blockColumns); ++jBlock , ++index) {
                if ((blockData[index].length) != (iHeight * (blockWidth(jBlock)))) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.WRONG_BLOCK_LENGTH, 
                    blockData[index].length, (iHeight * (blockWidth(jBlock))));
                }
                if (copyArray) {
                    blocks[index] = blockData[index].clone();
                }
            }
        }

    }

    /**
     * Convert a data array from raw layout to blocks layout.
     * <p>
     * Raw layout is the straightforward layout where element at row i and
     * column j is in array element <code>rawData[i][j]</code>. Blocks layout
     * is the layout used in {@link BlockRealMatrix} instances, where the matrix
     * is split in square blocks (except at right and bottom side where blocks may
     * be rectangular to fit matrix size) and each block is stored in a flattened
     * one-dimensional array.
     * </p>
     * <p>
     * This method creates an array in blocks layout from an input array in raw layout.
     * It can be used to provide the array argument of the {@link
     * #BlockRealMatrix(int, int, double[][], boolean)} constructor.
     * </p>
     *
     * @param rawData
     * 		data array in raw layout
     * @return a new data array containing the same entries but in blocks layout
     * @exception IllegalArgumentException if <code>rawData</code> is not rectangular
     * (not all rows have the same length)
     * @see #createBlocksLayout(int, int)
     * @see #BlockRealMatrix(int, int, double[][], boolean)
     */     public static double[][] toBlocksLayout(final double[][] rawData) throws java.lang.IllegalArgumentException {

        final int rows = rawData.length;
        final int columns = rawData[0].length;
        final int blockRows = ((rows + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int blockColumns = ((columns + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);

        // safety checks
        for (int i = 0; i < (rawData.length); ++i) {
            final int length = rawData[i].length;
            if (length != columns) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, 
                columns, length);
            }
        }

        // convert array
        final double[][] blocks = new double[blockRows * blockColumns][];
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < blockRows; ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            final int iHeight = pEnd - pStart;
            for (int jBlock = 0; jBlock < blockColumns; ++jBlock) {
                final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                final int jWidth = qEnd - qStart;

                // allocate new block
                final double[] block = new double[iHeight * jWidth];
                blocks[blockIndex] = block;

                // copy data
                int index = 0;
                for (int p = pStart; p < pEnd; ++p) {
                    java.lang.System.arraycopy(rawData[p], qStart, block, index, jWidth);
                    index += jWidth;
                }

                ++blockIndex;

            }
        }

        return blocks;

    }

    /**
     * Create a data array in blocks layout.
     * <p>
     * This method can be used to create the array argument of the {@link
     * #BlockRealMatrix(int, int, double[][], boolean)} constructor.
     * </p>
     *
     * @param rows
     * 		the number of rows in the new matrix
     * @param columns
     * 		the number of columns in the new matrix
     * @return a new data array in blocks layout
     * @see #toBlocksLayout(double[][])
     * @see #BlockRealMatrix(int, int, double[][], boolean)
     */     public static double[][] createBlocksLayout(final int rows, final int columns) {         final int blockRows = ((rows + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int blockColumns = ((columns + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) - 1) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);

        final double[][] blocks = new double[blockRows * blockColumns][];
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < blockRows; ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            final int iHeight = pEnd - pStart;
            for (int jBlock = 0; jBlock < blockColumns; ++jBlock) {
                final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                final int jWidth = qEnd - qStart;
                blocks[blockIndex] = new double[iHeight * jWidth];
                ++blockIndex;
            }
        }

        return blocks;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix createMatrix(final int rowDimension, final int columnDimension) throws 
    java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.BlockRealMatrix(rowDimension, columnDimension);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix copy() {

        // create an empty matrix
        org.apache.commons.math.linear.BlockRealMatrix copied = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

        // copy the blocks
        for (int i = 0; i < (blocks.length); ++i) {
            java.lang.System.arraycopy(blocks[i], 0, copied.blocks[i], 0, blocks[i].length);
        }

        return copied;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix add(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return add(((org.apache.commons.math.linear.BlockRealMatrix) (m)));
        } catch (java.lang.ClassCastException cce) {

            // safety check
            org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, m);

            final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

            // perform addition block-wise, to ensure good cache behavior
            int blockIndex = 0;
            for (int iBlock = 0; iBlock < (out.blockRows); ++iBlock) {
                for (int jBlock = 0; jBlock < (out.blockColumns); ++jBlock) {

                    // perform addition on the current block
                    final double[] outBlock = out.blocks[blockIndex];
                    final double[] tBlock = blocks[blockIndex];
                    final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
                    final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; ++p) {
                        for (int q = qStart; q < qEnd; ++q) {
                            outBlock[k] = (tBlock[k]) + (m.getEntry(p, q));
                            ++k;
                        }
                    }

                    // go to next block
                    ++blockIndex;

                }
            }

            return out;

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
     */     public org.apache.commons.math.linear.BlockRealMatrix add(final org.apache.commons.math.linear.BlockRealMatrix m) throws java.lang.IllegalArgumentException {

        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, m);

        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

        // perform addition block-wise, to ensure good cache behavior
        for (int blockIndex = 0; blockIndex < (out.blocks.length); ++blockIndex) {
            final double[] outBlock = out.blocks[blockIndex];
            final double[] tBlock = blocks[blockIndex];
            final double[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < (outBlock.length); ++k) {
                outBlock[k] = (tBlock[k]) + (mBlock[k]);
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix subtract(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return subtract(((org.apache.commons.math.linear.BlockRealMatrix) (m)));
        } catch (java.lang.ClassCastException cce) {

            // safety check
            org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, m);

            final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

            // perform subtraction block-wise, to ensure good cache behavior
            int blockIndex = 0;
            for (int iBlock = 0; iBlock < (out.blockRows); ++iBlock) {
                for (int jBlock = 0; jBlock < (out.blockColumns); ++jBlock) {

                    // perform subtraction on the current block
                    final double[] outBlock = out.blocks[blockIndex];
                    final double[] tBlock = blocks[blockIndex];
                    final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
                    final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; ++p) {
                        for (int q = qStart; q < qEnd; ++q) {
                            outBlock[k] = (tBlock[k]) - (m.getEntry(p, q));
                            ++k;
                        }
                    }

                    // go to next block
                    ++blockIndex;

                }
            }

            return out;

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
     */     public org.apache.commons.math.linear.BlockRealMatrix subtract(final org.apache.commons.math.linear.BlockRealMatrix m) throws java.lang.IllegalArgumentException {

        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, m);

        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

        // perform subtraction block-wise, to ensure good cache behavior
        for (int blockIndex = 0; blockIndex < (out.blocks.length); ++blockIndex) {
            final double[] outBlock = out.blocks[blockIndex];
            final double[] tBlock = blocks[blockIndex];
            final double[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < (outBlock.length); ++k) {
                outBlock[k] = (tBlock[k]) - (mBlock[k]);
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix scalarAdd(final double d) throws 
    java.lang.IllegalArgumentException {

        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

        // perform subtraction block-wise, to ensure good cache behavior
        for (int blockIndex = 0; blockIndex < (out.blocks.length); ++blockIndex) {
            final double[] outBlock = out.blocks[blockIndex];
            final double[] tBlock = blocks[blockIndex];
            for (int k = 0; k < (outBlock.length); ++k) {
                outBlock[k] = (tBlock[k]) + d;
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix scalarMultiply(final double d) throws 
    java.lang.IllegalArgumentException {

        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, columns);

        // perform subtraction block-wise, to ensure good cache behavior
        for (int blockIndex = 0; blockIndex < (out.blocks.length); ++blockIndex) {
            final double[] outBlock = out.blocks[blockIndex];
            final double[] tBlock = blocks[blockIndex];
            for (int k = 0; k < (outBlock.length); ++k) {
                outBlock[k] = (tBlock[k]) * d;
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix multiply(final org.apache.commons.math.linear.RealMatrix m) throws 
    java.lang.IllegalArgumentException {
        try {
            return multiply(((org.apache.commons.math.linear.BlockRealMatrix) (m)));
        } catch (java.lang.ClassCastException cce) {

            // safety check
            org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, m);

            final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, m.getColumnDimension());

            // perform multiplication block-wise, to ensure good cache behavior
            int blockIndex = 0;
            for (int iBlock = 0; iBlock < (out.blockRows); ++iBlock) {

                final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);

                for (int jBlock = 0; jBlock < (out.blockColumns); ++jBlock) {

                    final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), m.getColumnDimension());

                    // select current block
                    final double[] outBlock = out.blocks[blockIndex];

                    // perform multiplication on current block
                    for (int kBlock = 0; kBlock < (blockColumns); ++kBlock) {
                        final int kWidth = blockWidth(kBlock);
                        final double[] tBlock = blocks[((iBlock * (blockColumns)) + kBlock)];
                        final int rStart = kBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                        int k = 0;
                        for (int p = pStart; p < pEnd; ++p) {
                            final int lStart = (p - pStart) * kWidth;
                            final int lEnd = lStart + kWidth;
                            for (int q = qStart; q < qEnd; ++q) {
                                double sum = 0;
                                int r = rStart;
                                for (int l = lStart; l < lEnd; ++l) {
                                    sum += (tBlock[l]) * (m.getEntry(r, q));
                                    ++r;
                                }
                                outBlock[k] += sum;
                                ++k;
                            }
                        }
                    }

                    // go to next block
                    ++blockIndex;

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
     */     public org.apache.commons.math.linear.BlockRealMatrix multiply(org.apache.commons.math.linear.BlockRealMatrix m) throws java.lang.IllegalArgumentException {
        // safety check
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, m);

        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, m.columns);

        // perform multiplication block-wise, to ensure good cache behavior
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < (out.blockRows); ++iBlock) {

            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);

            for (int jBlock = 0; jBlock < (out.blockColumns); ++jBlock) {
                final int jWidth = out.blockWidth(jBlock);
                final int jWidth2 = jWidth + jWidth;
                final int jWidth3 = jWidth2 + jWidth;
                final int jWidth4 = jWidth3 + jWidth;

                // select current block
                final double[] outBlock = out.blocks[blockIndex];

                // perform multiplication on current block
                for (int kBlock = 0; kBlock < (blockColumns); ++kBlock) {
                    final int kWidth = blockWidth(kBlock);
                    final double[] tBlock = blocks[((iBlock * (blockColumns)) + kBlock)];
                    final double[] mBlock = m.blocks[((kBlock * (m.blockColumns)) + jBlock)];
                    int k = 0;
                    for (int p = pStart; p < pEnd; ++p) {
                        final int lStart = (p - pStart) * kWidth;
                        final int lEnd = lStart + kWidth;
                        for (int nStart = 0; nStart < jWidth; ++nStart) {
                            double sum = 0;
                            int l = lStart;
                            int n = nStart;
                            while (l < (lEnd - 3)) {
                                sum += ((((tBlock[l]) * (mBlock[n])) + 
                                ((tBlock[(l + 1)]) * (mBlock[(n + jWidth)]))) + 
                                ((tBlock[(l + 2)]) * (mBlock[(n + jWidth2)]))) + 
                                ((tBlock[(l + 3)]) * (mBlock[(n + jWidth3)]));
                                l += 4;
                                n += jWidth4;
                            } 
                            while (l < lEnd) {
                                sum += (tBlock[(l++)]) * (mBlock[n]);
                                n += jWidth;
                            } 
                            outBlock[k] += sum;
                            ++k;
                        }
                    }
                }

                // go to next block
                ++blockIndex;

            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[][] getData() {

        final double[][] data = new double[getRowDimension()][getColumnDimension()];
        final int lastColumns = (columns) - (((blockColumns) - 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));

        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            int regularPos = 0;
            int lastPos = 0;
            for (int p = pStart; p < pEnd; ++p) {
                final double[] dataP = data[p];
                int blockIndex = iBlock * (blockColumns);
                int dataPos = 0;
                for (int jBlock = 0; jBlock < ((blockColumns) - 1); ++jBlock) {
                    java.lang.System.arraycopy(blocks[(blockIndex++)], regularPos, dataP, dataPos, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    dataPos += org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE;
                }
                java.lang.System.arraycopy(blocks[blockIndex], lastPos, dataP, dataPos, lastColumns);
                regularPos += org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE;
                lastPos += lastColumns;
            }
        }

        return data;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getNorm() {
        final double[] colSums = new double[org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE];
        double maxColSum = 0;
        for (int jBlock = 0; jBlock < (blockColumns); jBlock++) {
            final int jWidth = blockWidth(jBlock);
            java.util.Arrays.fill(colSums, 0, jWidth, 0.0);
            for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
                final int iHeight = blockHeight(iBlock);
                final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                for (int j = 0; j < jWidth; ++j) {
                    double sum = 0;
                    for (int i = 0; i < iHeight; ++i) {
                        sum += java.lang.Math.abs(block[((i * jWidth) + j)]);
                    }
                    colSums[j] += sum;
                }
            }
            for (int j = 0; j < jWidth; ++j) {
                maxColSum = java.lang.Math.max(maxColSum, colSums[j]);
            }
        }
        return maxColSum;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getFrobeniusNorm() {
        double sum2 = 0;
        for (int blockIndex = 0; blockIndex < (blocks.length); ++blockIndex) {
            for (final double entry : blocks[blockIndex]) {
                sum2 += entry * entry;
            }
        }
        return java.lang.Math.sqrt(sum2);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix getSubMatrix(final int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        // safety checks
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);

        // create the output matrix
        final org.apache.commons.math.linear.BlockRealMatrix out = 
        new org.apache.commons.math.linear.BlockRealMatrix(((endRow - startRow) + 1), ((endColumn - startColumn) + 1));

        // compute blocks shifts
        final int blockStartRow = startRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int rowsShift = startRow % (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int blockStartColumn = startColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int columnsShift = startColumn % (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);

        // perform extraction block-wise, to ensure good cache behavior
        int pBlock = blockStartRow;
        for (int iBlock = 0; iBlock < (out.blockRows); ++iBlock) {
            final int iHeight = out.blockHeight(iBlock);
            int qBlock = blockStartColumn;
            for (int jBlock = 0; jBlock < (out.blockColumns); ++jBlock) {
                final int jWidth = out.blockWidth(jBlock);

                // handle one block of the output matrix
                final int outIndex = (iBlock * (out.blockColumns)) + jBlock;
                final double[] outBlock = out.blocks[outIndex];
                final int index = (pBlock * (blockColumns)) + qBlock;
                final int width = blockWidth(qBlock);

                final int heightExcess = (iHeight + rowsShift) - (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int widthExcess = (jWidth + columnsShift) - (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                if (heightExcess > 0) {
                    // the submatrix block spans on two blocks rows from the original matrix
                    if (widthExcess > 0) {
                        // the submatrix block spans on two blocks columns from the original matrix
                        final int width2 = blockWidth((qBlock + 1));
                        copyBlockPart(blocks[index], width, 
                        rowsShift, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE, 
                        columnsShift, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE, 
                        outBlock, jWidth, 0, 0);
                        copyBlockPart(blocks[(index + 1)], width2, 
                        rowsShift, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE, 
                        0, widthExcess, 
                        outBlock, jWidth, 0, (jWidth - widthExcess));
                        copyBlockPart(blocks[(index + (blockColumns))], width, 
                        0, heightExcess, 
                        columnsShift, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE, 
                        outBlock, jWidth, (iHeight - heightExcess), 0);
                        copyBlockPart(blocks[((index + (blockColumns)) + 1)], width2, 
                        0, heightExcess, 
                        0, widthExcess, 
                        outBlock, jWidth, (iHeight - heightExcess), (jWidth - widthExcess));
                    }else {
                        // the submatrix block spans on one block column from the original matrix
                        copyBlockPart(blocks[index], width, 
                        rowsShift, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE, 
                        columnsShift, (jWidth + columnsShift), 
                        outBlock, jWidth, 0, 0);
                        copyBlockPart(blocks[(index + (blockColumns))], width, 
                        0, heightExcess, 
                        columnsShift, (jWidth + columnsShift), 
                        outBlock, jWidth, (iHeight - heightExcess), 0);
                    }
                }else {
                    // the submatrix block spans on one block row from the original matrix
                    if (widthExcess > 0) {
                        // the submatrix block spans on two blocks columns from the original matrix
                        final int width2 = blockWidth((qBlock + 1));
                        copyBlockPart(blocks[index], width, 
                        rowsShift, (iHeight + rowsShift), 
                        columnsShift, org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE, 
                        outBlock, jWidth, 0, 0);
                        copyBlockPart(blocks[(index + 1)], width2, 
                        rowsShift, (iHeight + rowsShift), 
                        0, widthExcess, 
                        outBlock, jWidth, 0, (jWidth - widthExcess));
                    }else {
                        // the submatrix block spans on one block column from the original matrix
                        copyBlockPart(blocks[index], width, 
                        rowsShift, (iHeight + rowsShift), 
                        columnsShift, (jWidth + columnsShift), 
                        outBlock, jWidth, 0, 0);
                    }
                }

                ++qBlock;

            }

            ++pBlock;

        }

        return out;

    }

    /**
     * Copy a part of a block into another one
     * <p>This method can be called only when the specified part fits in both
     * blocks, no verification is done here.</p>
     *
     * @param srcBlock
     * 		source block
     * @param srcWidth
     * 		source block width ({@link #BLOCK_SIZE} or smaller)
     * @param srcStartRow
     * 		start row in the source block
     * @param srcEndRow
     * 		end row (exclusive) in the source block
     * @param srcStartColumn
     * 		start column in the source block
     * @param srcEndColumn
     * 		end column (exclusive) in the source block
     * @param dstBlock
     * 		destination block
     * @param dstWidth
     * 		destination block width ({@link #BLOCK_SIZE} or smaller)
     * @param dstStartRow
     * 		start row in the destination block
     * @param dstStartColumn
     * 		start column in the destination block
     */     private void copyBlockPart(final double[] srcBlock, final int srcWidth, final int srcStartRow, final int srcEndRow, final int srcStartColumn, final int srcEndColumn, final double[] dstBlock, final int dstWidth, final int dstStartRow, final int dstStartColumn) {         final int length = srcEndColumn - srcStartColumn;         int srcPos = (srcStartRow * srcWidth) + srcStartColumn;         int dstPos = (dstStartRow * dstWidth) + dstStartColumn;         for (int srcRow = srcStartRow; srcRow < srcEndRow; ++srcRow) {             java.lang.System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);             srcPos += srcWidth;
            dstPos += dstWidth;
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setSubMatrix(final double[][] subMatrix, final int row, final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        // safety checks
        final int refLength = subMatrix[0].length;
        if (refLength < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.AT_LEAST_ONE_COLUMN);
        }
        final int endRow = (row + (subMatrix.length)) - 1;
        final int endColumn = (column + refLength) - 1;
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, row, endRow, column, endColumn);
        for (final double[] subRow : subMatrix) {
            if ((subRow.length) != refLength) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, 
                refLength, subRow.length);
            }
        }

        // compute blocks bounds
        final int blockStartRow = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int blockEndRow = (endRow + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int blockStartColumn = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int blockEndColumn = (endColumn + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);

        // perform copy block-wise, to ensure good cache behavior
        for (int iBlock = blockStartRow; iBlock < blockEndRow; ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            final int firstRow = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int iStart = java.lang.Math.max(row, firstRow);
            final int iEnd = java.lang.Math.min((endRow + 1), (firstRow + iHeight));

            for (int jBlock = blockStartColumn; jBlock < blockEndColumn; ++jBlock) {
                final int jWidth = blockWidth(jBlock);
                final int firstColumn = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int jStart = java.lang.Math.max(column, firstColumn);
                final int jEnd = java.lang.Math.min((endColumn + 1), (firstColumn + jWidth));
                final int jLength = jEnd - jStart;

                // handle one block, row by row
                final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                for (int i = iStart; i < iEnd; ++i) {
                    java.lang.System.arraycopy(subMatrix[(i - row)], (jStart - column), 
                    block, (((i - firstRow) * jWidth) + (jStart - firstColumn)), 
                    jLength);
                }

            }
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix getRowMatrix(final int row) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(1, columns);

        // perform copy block-wise, to ensure good cache behavior
        final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int iRow = row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        int outBlockIndex = 0;
        int outIndex = 0;
        double[] outBlock = out.blocks[outBlockIndex];
        for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
            final int jWidth = blockWidth(jBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            final int available = (outBlock.length) - outIndex;
            if (jWidth > available) {
                java.lang.System.arraycopy(block, (iRow * jWidth), outBlock, outIndex, available);
                outBlock = out.blocks[(++outBlockIndex)];
                java.lang.System.arraycopy(block, (iRow * jWidth), outBlock, 0, (jWidth - available));
                outIndex = jWidth - available;
            }else {
                java.lang.System.arraycopy(block, (iRow * jWidth), outBlock, outIndex, jWidth);
                outIndex += jWidth;
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setRowMatrix(final int row, final org.apache.commons.math.linear.RealMatrix matrix) throws 
    org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {
        try {
            setRowMatrix(row, ((org.apache.commons.math.linear.BlockRealMatrix) (matrix)));
        } catch (java.lang.ClassCastException cce) {
            super.setRowMatrix(row, matrix);
        }
    }

    /**
     * Sets the entries in row number <code>row</code>
     * as a row matrix.  Row indices start at 0.
     *
     * @param row
     * 		the row to be set
     * @param matrix
     * 		row matrix (must have one row and the same number of columns
     * 		as the instance)
     * @throws MatrixIndexException
     * 		if the specified row index is invalid
     * @throws InvalidMatrixException
     * 		if the matrix dimensions do not match one
     * 		instance row
     */     public void setRowMatrix(final int row, final org.apache.commons.math.linear.BlockRealMatrix matrix) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {         org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        final int nCols = getColumnDimension();
        if (((matrix.getRowDimension()) != 1) || 
        ((matrix.getColumnDimension()) != nCols)) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            matrix.getRowDimension(), matrix.getColumnDimension(), 
            1, nCols);
        }

        // perform copy block-wise, to ensure good cache behavior
        final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int iRow = row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        int mBlockIndex = 0;
        int mIndex = 0;
        double[] mBlock = matrix.blocks[mBlockIndex];
        for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
            final int jWidth = blockWidth(jBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            final int available = (mBlock.length) - mIndex;
            if (jWidth > available) {
                java.lang.System.arraycopy(mBlock, mIndex, block, (iRow * jWidth), available);
                mBlock = matrix.blocks[(++mBlockIndex)];
                java.lang.System.arraycopy(mBlock, 0, block, (iRow * jWidth), (jWidth - available));
                mIndex = jWidth - available;
            }else {
                java.lang.System.arraycopy(mBlock, mIndex, block, (iRow * jWidth), jWidth);
                mIndex += jWidth;
            }
        }

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix getColumnMatrix(final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(rows, 1);

        // perform copy block-wise, to ensure good cache behavior
        final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int jColumn = column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        final int jWidth = blockWidth(jBlock);
        int outBlockIndex = 0;
        int outIndex = 0;
        double[] outBlock = out.blocks[outBlockIndex];
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            for (int i = 0; i < iHeight; ++i) {
                if (outIndex >= (outBlock.length)) {
                    outBlock = out.blocks[(++outBlockIndex)];
                    outIndex = 0;
                }
                outBlock[(outIndex++)] = block[((i * jWidth) + jColumn)];
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setColumnMatrix(final int column, final org.apache.commons.math.linear.RealMatrix matrix) throws 
    org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {
        try {
            setColumnMatrix(column, ((org.apache.commons.math.linear.BlockRealMatrix) (matrix)));
        } catch (java.lang.ClassCastException cce) {
            super.setColumnMatrix(column, matrix);
        }
    }

    /**
     * Sets the entries in column number <code>column</code>
     * as a column matrix.  Column indices start at 0.
     *
     * @param column
     * 		the column to be set
     * @param matrix
     * 		column matrix (must have one column and the same number of rows
     * 		as the instance)
     * @throws MatrixIndexException
     * 		if the specified column index is invalid
     * @throws InvalidMatrixException
     * 		if the matrix dimensions do not match one
     * 		instance column
     */     void setColumnMatrix(final int column, final org.apache.commons.math.linear.BlockRealMatrix matrix) throws org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {         org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final int nRows = getRowDimension();
        if (((matrix.getRowDimension()) != nRows) || 
        ((matrix.getColumnDimension()) != 1)) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            matrix.getRowDimension(), matrix.getColumnDimension(), 
            nRows, 1);
        }

        // perform copy block-wise, to ensure good cache behavior
        final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int jColumn = column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        final int jWidth = blockWidth(jBlock);
        int mBlockIndex = 0;
        int mIndex = 0;
        double[] mBlock = matrix.blocks[mBlockIndex];
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            for (int i = 0; i < iHeight; ++i) {
                if (mIndex >= (mBlock.length)) {
                    mBlock = matrix.blocks[(++mBlockIndex)];
                    mIndex = 0;
                }
                block[((i * jWidth) + jColumn)] = mBlock[(mIndex++)];
            }
        }

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealVector getRowVector(final int row) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        final double[] outData = new double[columns];

        // perform copy block-wise, to ensure good cache behavior
        final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int iRow = row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        int outIndex = 0;
        for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
            final int jWidth = blockWidth(jBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            java.lang.System.arraycopy(block, (iRow * jWidth), outData, outIndex, jWidth);
            outIndex += jWidth;
        }

        return new org.apache.commons.math.linear.ArrayRealVector(outData, false);

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setRowVector(final int row, final org.apache.commons.math.linear.RealVector vector) throws 
    org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {
        try {
            setRow(row, ((org.apache.commons.math.linear.ArrayRealVector) (vector)).getDataRef());
        } catch (java.lang.ClassCastException cce) {
            super.setRowVector(row, vector);
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealVector getColumnVector(final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final double[] outData = new double[rows];

        // perform copy block-wise, to ensure good cache behavior
        final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int jColumn = column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        final int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            for (int i = 0; i < iHeight; ++i) {
                outData[(outIndex++)] = block[((i * jWidth) + jColumn)];
            }
        }

        return new org.apache.commons.math.linear.ArrayRealVector(outData, false);

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setColumnVector(final int column, final org.apache.commons.math.linear.RealVector vector) throws 
    org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {
        try {
            setColumn(column, ((org.apache.commons.math.linear.ArrayRealVector) (vector)).getDataRef());
        } catch (java.lang.ClassCastException cce) {
            super.setColumnVector(column, vector);
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] getRow(final int row) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        final double[] out = new double[columns];

        // perform copy block-wise, to ensure good cache behavior
        final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int iRow = row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        int outIndex = 0;
        for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
            final int jWidth = blockWidth(jBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            java.lang.System.arraycopy(block, (iRow * jWidth), out, outIndex, jWidth);
            outIndex += jWidth;
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setRow(final int row, final double[] array) throws 
    org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, row);
        final int nCols = getColumnDimension();
        if ((array.length) != nCols) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            1, array.length, 1, nCols);
        }

        // perform copy block-wise, to ensure good cache behavior
        final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int iRow = row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        int outIndex = 0;
        for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
            final int jWidth = blockWidth(jBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            java.lang.System.arraycopy(array, outIndex, block, (iRow * jWidth), jWidth);
            outIndex += jWidth;
        }

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] getColumn(final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final double[] out = new double[rows];

        // perform copy block-wise, to ensure good cache behavior
        final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int jColumn = column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        final int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            for (int i = 0; i < iHeight; ++i) {
                out[(outIndex++)] = block[((i * jWidth) + jColumn)];
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setColumn(final int column, final double[] array) throws 
    org.apache.commons.math.linear.InvalidMatrixException, org.apache.commons.math.linear.MatrixIndexException {

        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, column);
        final int nRows = getRowDimension();
        if ((array.length) != nRows) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 
            array.length, 1, nRows, 1);
        }

        // perform copy block-wise, to ensure good cache behavior
        final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
        final int jColumn = column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE));
        final int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int iHeight = blockHeight(iBlock);
            final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
            for (int i = 0; i < iHeight; ++i) {
                block[((i * jWidth) + jColumn)] = array[(outIndex++)];
            }
        }

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getEntry(final int row, final int column) throws 
    org.apache.commons.math.linear.MatrixIndexException {
        try {
            final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int k = ((row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))) * (blockWidth(jBlock))) + 
            (column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)));
            return blocks[((iBlock * (blockColumns)) + jBlock)][k];
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
            final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int k = ((row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))) * (blockWidth(jBlock))) + 
            (column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)));
            blocks[((iBlock * (blockColumns)) + jBlock)][k] = value;
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
            final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int k = ((row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))) * (blockWidth(jBlock))) + 
            (column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)));
            blocks[((iBlock * (blockColumns)) + jBlock)][k] += increment;
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
            final int iBlock = row / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int jBlock = column / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int k = ((row - (iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))) * (blockWidth(jBlock))) + 
            (column - (jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)));
            blocks[((iBlock * (blockColumns)) + jBlock)][k] *= factor;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            org.apache.commons.math.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, 
            row, column, getRowDimension(), getColumnDimension());
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.BlockRealMatrix transpose() {

        final int nRows = getRowDimension();
        final int nCols = getColumnDimension();
        final org.apache.commons.math.linear.BlockRealMatrix out = new org.apache.commons.math.linear.BlockRealMatrix(nCols, nRows);

        // perform transpose block-wise, to ensure good cache behavior
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < (blockColumns); ++iBlock) {
            for (int jBlock = 0; jBlock < (blockRows); ++jBlock) {

                // transpose current block
                final double[] outBlock = out.blocks[blockIndex];
                final double[] tBlock = blocks[((jBlock * (blockColumns)) + iBlock)];
                final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
                int k = 0;
                for (int p = pStart; p < pEnd; ++p) {
                    final int lInc = pEnd - pStart;
                    int l = p - pStart;
                    for (int q = qStart; q < qEnd; ++q) {
                        outBlock[k] = tBlock[l];
                        ++k;
                        l += lInc;
                    }
                }

                // go to next block
                ++blockIndex;

            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getRowDimension() {
        return rows;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getColumnDimension() {
        return columns;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] operate(final double[] v) throws 
    java.lang.IllegalArgumentException {

        if ((v.length) != (columns)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            v.length, columns);
        }
        final double[] out = new double[rows];

        // perform multiplication block-wise, to ensure good cache behavior
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
                final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                int k = 0;
                for (int p = pStart; p < pEnd; ++p) {
                    double sum = 0;
                    int q = qStart;
                    while (q < (qEnd - 3)) {
                        sum += ((((block[k]) * (v[q])) + 
                        ((block[(k + 1)]) * (v[(q + 1)]))) + 
                        ((block[(k + 2)]) * (v[(q + 2)]))) + 
                        ((block[(k + 3)]) * (v[(q + 3)]));
                        k += 4;
                        q += 4;
                    } 
                    while (q < qEnd) {
                        sum += (block[(k++)]) * (v[(q++)]);
                    } 
                    out[p] += sum;
                }
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] preMultiply(final double[] v) throws 
    java.lang.IllegalArgumentException {

        if ((v.length) != (rows)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            v.length, rows);
        }
        final double[] out = new double[columns];

        // perform multiplication block-wise, to ensure good cache behavior
        for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
            final int jWidth = blockWidth(jBlock);
            final int jWidth2 = jWidth + jWidth;
            final int jWidth3 = jWidth2 + jWidth;
            final int jWidth4 = jWidth3 + jWidth;
            final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
            for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
                final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
                for (int q = qStart; q < qEnd; ++q) {
                    int k = q - qStart;
                    double sum = 0;
                    int p = pStart;
                    while (p < (pEnd - 3)) {
                        sum += ((((block[k]) * (v[p])) + 
                        ((block[(k + jWidth)]) * (v[(p + 1)]))) + 
                        ((block[(k + jWidth2)]) * (v[(p + 2)]))) + 
                        ((block[(k + jWidth3)]) * (v[(p + 3)]));
                        k += jWidth4;
                        p += 4;
                    } 
                    while (p < pEnd) {
                        sum += (block[k]) * (v[(p++)]);
                        k += jWidth;
                    } 
                    out[q] += sum;
                }
            }
        }

        return out;

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInRowOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        visitor.start(rows, columns, 0, ((rows) - 1), 0, ((columns) - 1));
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            for (int p = pStart; p < pEnd; ++p) {
                for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
                    final int jWidth = blockWidth(jBlock);
                    final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                    final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                    int k = (p - pStart) * jWidth;
                    for (int q = qStart; q < qEnd; ++q) {
                        block[k] = visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInRowOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        visitor.start(rows, columns, 0, ((rows) - 1), 0, ((columns) - 1));
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            for (int p = pStart; p < pEnd; ++p) {
                for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
                    final int jWidth = blockWidth(jBlock);
                    final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                    final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                    int k = (p - pStart) * jWidth;
                    for (int q = qStart; q < qEnd; ++q) {
                        visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
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
        visitor.start(rows, columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); iBlock < (1 + (endRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++iBlock) {
            final int p0 = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pStart = java.lang.Math.max(startRow, p0);
            final int pEnd = java.lang.Math.min(((iBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endRow));
            for (int p = pStart; p < pEnd; ++p) {
                for (int jBlock = startColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); jBlock < (1 + (endColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++jBlock) {
                    final int jWidth = blockWidth(jBlock);
                    final int q0 = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qStart = java.lang.Math.max(startColumn, q0);
                    final int qEnd = java.lang.Math.min(((jBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endColumn));
                    final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; ++q) {
                        block[k] = visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
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
        visitor.start(rows, columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); iBlock < (1 + (endRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++iBlock) {
            final int p0 = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pStart = java.lang.Math.max(startRow, p0);
            final int pEnd = java.lang.Math.min(((iBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endRow));
            for (int p = pStart; p < pEnd; ++p) {
                for (int jBlock = startColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); jBlock < (1 + (endColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++jBlock) {
                    final int jWidth = blockWidth(jBlock);
                    final int q0 = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                    final int qStart = java.lang.Math.max(startColumn, q0);
                    final int qEnd = java.lang.Math.min(((jBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endColumn));
                    final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; ++q) {
                        visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInOptimizedOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        visitor.start(rows, columns, 0, ((rows) - 1), 0, ((columns) - 1));
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
                final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                final double[] block = blocks[blockIndex];
                int k = 0;
                for (int p = pStart; p < pEnd; ++p) {
                    for (int q = qStart; q < qEnd; ++q) {
                        block[k] = visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
                ++blockIndex;
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInOptimizedOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor) throws 
    org.apache.commons.math.linear.MatrixVisitorException {
        visitor.start(rows, columns, 0, ((rows) - 1), 0, ((columns) - 1));
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < (blockRows); ++iBlock) {
            final int pStart = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pEnd = java.lang.Math.min((pStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), rows);
            for (int jBlock = 0; jBlock < (blockColumns); ++jBlock) {
                final int qStart = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qEnd = java.lang.Math.min((qStart + (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), columns);
                final double[] block = blocks[blockIndex];
                int k = 0;
                for (int p = pStart; p < pEnd; ++p) {
                    for (int q = qStart; q < qEnd; ++q) {
                        visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
                ++blockIndex;
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInOptimizedOrder(final org.apache.commons.math.linear.RealMatrixChangingVisitor visitor, final 
    int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        visitor.start(rows, columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); iBlock < (1 + (endRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++iBlock) {
            final int p0 = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pStart = java.lang.Math.max(startRow, p0);
            final int pEnd = java.lang.Math.min(((iBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endRow));
            for (int jBlock = startColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); jBlock < (1 + (endColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++jBlock) {
                final int jWidth = blockWidth(jBlock);
                final int q0 = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qStart = java.lang.Math.max(startColumn, q0);
                final int qEnd = java.lang.Math.min(((jBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endColumn));
                final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                for (int p = pStart; p < pEnd; ++p) {
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; ++q) {
                        block[k] = visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
            }
        }
        return visitor.end();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double walkInOptimizedOrder(final org.apache.commons.math.linear.RealMatrixPreservingVisitor visitor, final 
    int startRow, final int endRow, final 
    int startColumn, final int endColumn) throws 
    org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        visitor.start(rows, columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); iBlock < (1 + (endRow / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++iBlock) {
            final int p0 = iBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
            final int pStart = java.lang.Math.max(startRow, p0);
            final int pEnd = java.lang.Math.min(((iBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endRow));
            for (int jBlock = startColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE); jBlock < (1 + (endColumn / (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE))); ++jBlock) {
                final int jWidth = blockWidth(jBlock);
                final int q0 = jBlock * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE);
                final int qStart = java.lang.Math.max(startColumn, q0);
                final int qEnd = java.lang.Math.min(((jBlock + 1) * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)), (1 + endColumn));
                final double[] block = blocks[((iBlock * (blockColumns)) + jBlock)];
                for (int p = pStart; p < pEnd; ++p) {
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; ++q) {
                        visitor.visit(p, q, block[k]);
                        ++k;
                    }
                }
            }
        }
        return visitor.end();
    }

    /**
     * Get the height of a block.
     *
     * @param blockRow
     * 		row index (in block sense) of the block
     * @return height (number of rows) of the block
     */     private int blockHeight(final int blockRow) {         return blockRow == ((blockRows) - 1) ? (rows) - (blockRow * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) : org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE;
    }

    /**
     * Get the width of a block.
     *
     * @param blockColumn
     * 		column index (in block sense) of the block
     * @return width (number of columns) of the block
     */     private int blockWidth(final int blockColumn) {         return blockColumn == ((blockColumns) - 1) ? (columns) - (blockColumn * (org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE)) : org.apache.commons.math.linear.BlockRealMatrix.BLOCK_SIZE;
    }

}