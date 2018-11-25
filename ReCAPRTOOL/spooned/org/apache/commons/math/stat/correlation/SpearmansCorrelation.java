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
 * <p>Spearman's rank correlation. This implementation performs a rank
 * transformation on the input data and then computes {@link PearsonsCorrelation}
 * on the ranked data.</p>
 *
 * <p>By default, ranks are computed using {@link NaturalRanking} with default
 * strategies for handling NaNs and ties in the data (NaNs maximal, ties averaged).
 * The ranking algorithm can be set using a constructor argument.</p>
 *
 * @since 2.0
 * @version $Revision$ $Date$
 */

public class SpearmansCorrelation {

    /**
     * Input data
     */     private final org.apache.commons.math.linear.RealMatrix data;
    /**
     * Ranking algorithm
     */     private final org.apache.commons.math.stat.ranking.RankingAlgorithm rankingAlgorithm;
    /**
     * Rank correlation
     */     private final org.apache.commons.math.stat.correlation.PearsonsCorrelation rankCorrelation;
    /**
     * Create a SpearmansCorrelation with the given input data matrix
     * and ranking algorithm.
     *
     * @param dataMatrix
     * 		matrix of data with columns representing
     * 		variables to correlate
     * @param rankingAlgorithm
     * 		ranking algorithm
     */     public SpearmansCorrelation(final org.apache.commons.math.linear.RealMatrix dataMatrix, final org.apache.commons.math.stat.ranking.RankingAlgorithm rankingAlgorithm) {         this.data = dataMatrix.copy();
        this.rankingAlgorithm = rankingAlgorithm;
        rankTransform(data);
        rankCorrelation = new org.apache.commons.math.stat.correlation.PearsonsCorrelation(data);
    }

    /**
     * Create a SpearmansCorrelation from the given data matrix.
     *
     * @param dataMatrix
     * 		matrix of data with columns representing
     * 		variables to correlate
     */     public SpearmansCorrelation(final org.apache.commons.math.linear.RealMatrix dataMatrix) {
        this(dataMatrix, new org.apache.commons.math.stat.ranking.NaturalRanking());
    }

    /**
     * Create a SpearmansCorrelation without data.
     */
    public SpearmansCorrelation() {
        data = null;
        this.rankingAlgorithm = new org.apache.commons.math.stat.ranking.NaturalRanking();
        rankCorrelation = null;
    }

    /**
     * Calculate the Spearman Rank Correlation Matrix.
     *
     * @return Spearman Rank Correlation Matrix
     */
    public org.apache.commons.math.linear.RealMatrix getCorrelationMatrix() {
        return rankCorrelation.getCorrelationMatrix();
    }

    /**
     * Returns a {@link PearsonsCorrelation} instance constructed from the
     * ranked input data. That is,
     * <code>new SpearmansCorrelation(matrix).getRankCorrelation()</code>
     * is equivalent to
     * <code>new PearsonsCorrelation(rankTransform(matrix))</code> where
     * <code>rankTransform(matrix)</code> is the result of applying the
     * configured <code>RankingAlgorithm</code> to each of the columns of
     * <code>matrix.</code>
     *
     * @return PearsonsCorrelation among ranked column data
     */
    public org.apache.commons.math.stat.correlation.PearsonsCorrelation getRankCorrelation() {
        return rankCorrelation;
    }

    /**
     * Computes the Spearman's rank correlation matrix for the columns of the
     * input matrix.
     *
     * @param matrix
     * 		matrix with columns representing variables to correlate
     * @return correlation matrix
     */     public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(org.apache.commons.math.linear.RealMatrix matrix) {
        org.apache.commons.math.linear.RealMatrix matrixCopy = matrix.copy();
        rankTransform(matrixCopy);
        return new org.apache.commons.math.stat.correlation.PearsonsCorrelation().computeCorrelationMatrix(matrixCopy);
    }

    /**
     * Computes the Spearman's rank correlation matrix for the columns of the
     * input rectangular array.  The columns of the array represent values
     * of variables to be correlated.
     *
     * @param matrix
     * 		matrix with columns representing variables to correlate
     * @return correlation matrix
     */     public org.apache.commons.math.linear.RealMatrix computeCorrelationMatrix(double[][] matrix) {
        return computeCorrelationMatrix(new org.apache.commons.math.linear.BlockRealMatrix(matrix));
    }

    /**
     * Computes the Spearman's rank correlation coefficient between the two arrays.
     *
     * </p>Throws IllegalArgumentException if the arrays do not have the same length
     * or their common length is less than 2</p>
     *
     * @param xArray
     * 		first data array
     * @param yArray
     * 		second data array
     * @return Returns Spearman's rank correlation coefficient for the two arrays
     * @throws 
     * 		IllegalArgumentException if the arrays lengths do not match or
     * 		there is insufficient data
     */     public double correlation(final double[] xArray, final double[] yArray) throws java.lang.IllegalArgumentException {         if ((xArray.length) != (yArray.length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, xArray.length, yArray.length);
        }else             if ((xArray.length) < 2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_DIMENSION, xArray.length, 2);
            }else {
                return new org.apache.commons.math.stat.correlation.PearsonsCorrelation().correlation(rankingAlgorithm.rank(xArray), 
                rankingAlgorithm.rank(yArray));
            }
    }

    /**
     * Applies rank transform to each of the columns of <code>matrix</code>
     * using the current <code>rankingAlgorithm</code>
     *
     * @param matrix
     * 		matrix to transform
     */     private void rankTransform(org.apache.commons.math.linear.RealMatrix matrix) {
        for (int i = 0; i < (matrix.getColumnDimension()); i++) {
            matrix.setColumn(i, rankingAlgorithm.rank(matrix.getColumn(i)));
        }
    }
}