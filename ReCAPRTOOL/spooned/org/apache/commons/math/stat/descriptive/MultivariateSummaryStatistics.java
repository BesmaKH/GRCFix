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
package org.apache.commons.math.stat.descriptive;


















/**
 * <p>Computes summary statistics for a stream of n-tuples added using the
 * {@link #addValue(double[]) addValue} method. The data values are not stored
 * in memory, so this class can be used to compute statistics for very large
 * n-tuple streams.</p>
 *
 * <p>The {@link StorelessUnivariateStatistic} instances used to maintain
 * summary state and compute statistics are configurable via setters.
 * For example, the default implementation for the mean can be overridden by
 * calling {@link #setMeanImpl(StorelessUnivariateStatistic[])}. Actual
 * parameters to these methods must implement the
 * {@link StorelessUnivariateStatistic} interface and configuration must be
 * completed before <code>addValue</code> is called. No configuration is
 * necessary to use the default, commons-math provided implementations.</p>
 *
 * <p>To compute statistics for a stream of n-tuples, construct a
 * MultivariateStatistics instance with dimension n and then use
 * {@link #addValue(double[])} to add n-tuples. The <code>getXxx</code>
 * methods where Xxx is a statistic return an array of <code>double</code>
 * values, where for <code>i = 0,...,n-1</code> the i<sup>th</sup> array element is the
 * value of the given statistic for data range consisting of the i<sup>th</sup> element of
 * each of the input n-tuples.  For example, if <code>addValue</code> is called
 * with actual parameters {0, 1, 2}, then {3, 4, 5} and finally {6, 7, 8},
 * <code>getSum</code> will return a three-element array with values
 * {0+3+6, 1+4+7, 2+5+8}</p>
 *
 * <p>Note: This class is not thread-safe. Use
 * {@link SynchronizedMultivariateSummaryStatistics} if concurrent access from multiple
 * threads is required.</p>
 *
 * @since 1.2
 * @version $Revision$ $Date$
 */
public class MultivariateSummaryStatistics implements 
java.io.Serializable , org.apache.commons.math.stat.descriptive.StatisticalMultivariateSummary {

    /**
     * Serialization UID
     */     private static final long serialVersionUID = 2271900808994826718L;
    /**
     * Dimension of the data.
     */     private int k;
    /**
     * Count of values that have been added
     */     private long n = 0;
    /**
     * Sum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumImpl;
    /**
     * Sum of squares statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumSqImpl;
    /**
     * Minimum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] minImpl;
    /**
     * Maximum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] maxImpl;
    /**
     * Sum of log statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumLogImpl;
    /**
     * Geometric mean statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] geoMeanImpl;
    /**
     * Mean statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] meanImpl;
    /**
     * Covariance statistic implementation - cannot be reset.
     */     private org.apache.commons.math.stat.descriptive.moment.VectorialCovariance covarianceImpl;
    /**
     * Construct a MultivariateSummaryStatistics instance
     *
     * @param k
     * 		dimension of the data
     * @param isCovarianceBiasCorrected
     * 		if true, the unbiased sample
     * 		covariance is computed, otherwise the biased population covariance
     * 		is computed
     */     public MultivariateSummaryStatistics(int k, boolean isCovarianceBiasCorrected) {         this.k = k;
        sumImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];
        sumSqImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];
        minImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];
        maxImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];
        sumLogImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];
        geoMeanImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];
        meanImpl = new org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[k];

        for (int i = 0; i < k; ++i) {
            sumImpl[i] = new org.apache.commons.math.stat.descriptive.summary.Sum();
            sumSqImpl[i] = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
            minImpl[i] = new org.apache.commons.math.stat.descriptive.rank.Min();
            maxImpl[i] = new org.apache.commons.math.stat.descriptive.rank.Max();
            sumLogImpl[i] = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
            geoMeanImpl[i] = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
            meanImpl[i] = new org.apache.commons.math.stat.descriptive.moment.Mean();
        }

        covarianceImpl = 
        new org.apache.commons.math.stat.descriptive.moment.VectorialCovariance(k, isCovarianceBiasCorrected);

    }

    /**
     * Add an n-tuple to the data
     *
     * @param value
     * 		the n-tuple to add
     * @throws DimensionMismatchException
     * 		if the length of the array
     * 		does not match the one used at construction
     */     public void addValue(double[] value) throws org.apache.commons.math.DimensionMismatchException {
        checkDimension(value.length);
        for (int i = 0; i < (k); ++i) {
            double v = value[i];
            sumImpl[i].increment(v);
            sumSqImpl[i].increment(v);
            minImpl[i].increment(v);
            maxImpl[i].increment(v);
            sumLogImpl[i].increment(v);
            geoMeanImpl[i].increment(v);
            meanImpl[i].increment(v);
        }
        covarianceImpl.increment(value);
        (n)++;
    }

    /**
     * Returns the dimension of the data
     *
     * @return The dimension of the data
     */     public int getDimension() {
        return k;
    }

    /**
     * Returns the number of available values
     *
     * @return The number of available values
     */     public long getN() {
        return n;
    }

    /**
     * Returns an array of the results of a statistic.
     *
     * @param stats
     * 		univariate statistic array
     * @return results array
     */     private double[] getResults(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] stats) {         double[] results = new double[stats.length];
        for (int i = 0; i < (results.length); ++i) {
            results[i] = stats[i].getResult();
        }
        return results;
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the sum of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component sums
     */
    public double[] getSum() {
        return getResults(sumImpl);
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the sum of squares of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component sums of squares
     */
    public double[] getSumSq() {
        return getResults(sumSqImpl);
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the sum of logs of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component log sums
     */
    public double[] getSumLog() {
        return getResults(sumLogImpl);
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the mean of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component means
     */
    public double[] getMean() {
        return getResults(meanImpl);
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the standard deviation of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component standard deviations
     */
    public double[] getStandardDeviation() {
        double[] stdDev = new double[k];
        if ((getN()) < 1) {
            java.util.Arrays.fill(stdDev, java.lang.Double.NaN);
        }else             if ((getN()) < 2) {
                java.util.Arrays.fill(stdDev, 0.0);
            }else {
                org.apache.commons.math.linear.RealMatrix matrix = covarianceImpl.getResult();
                for (int i = 0; i < (k); ++i) {
                    stdDev[i] = java.lang.Math.sqrt(matrix.getEntry(i, i));
                }
            }
        return stdDev;
    }

    /**
     * Returns the covariance matrix of the values that have been added.
     *
     * @return the covariance matrix
     */
    public org.apache.commons.math.linear.RealMatrix getCovariance() {
        return covarianceImpl.getResult();
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the maximum of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component maxima
     */
    public double[] getMax() {
        return getResults(maxImpl);
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the minimum of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component minima
     */
    public double[] getMin() {
        return getResults(minImpl);
    }

    /**
     * Returns an array whose i<sup>th</sup> entry is the geometric mean of the
     * i<sup>th</sup> entries of the arrays that have been added using
     * {@link #addValue(double[])}
     *
     * @return the array of component geometric means
     */
    public double[] getGeometricMean() {
        return getResults(geoMeanImpl);
    }

    /**
     * Generates a text report displaying
     * summary statistics from values that
     * have been added.
     *
     * @return String with line feeds displaying statistics
     */     @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuffer outBuffer = new java.lang.StringBuffer();
        outBuffer.append("MultivariateSummaryStatistics:\n");
        outBuffer.append((("n: " + (getN())) + "\n"));
        append(outBuffer, getMin(), "min: ", ", ", "\n");
        append(outBuffer, getMax(), "max: ", ", ", "\n");
        append(outBuffer, getMean(), "mean: ", ", ", "\n");
        append(outBuffer, getGeometricMean(), "geometric mean: ", ", ", "\n");
        append(outBuffer, getSumSq(), "sum of squares: ", ", ", "\n");
        append(outBuffer, getSumLog(), "sum of logarithms: ", ", ", "\n");
        append(outBuffer, getStandardDeviation(), "standard deviation: ", ", ", "\n");
        outBuffer.append((("covariance: " + (getCovariance().toString())) + "\n"));
        return outBuffer.toString();
    }

    /**
     * Append a text representation of an array to a buffer.
     *
     * @param buffer
     * 		buffer to fill
     * @param data
     * 		data array
     * @param prefix
     * 		text prefix
     * @param separator
     * 		elements separator
     * @param suffix
     * 		text suffix
     */     private void append(java.lang.StringBuffer buffer, double[] data, java.lang.String prefix, java.lang.String separator, java.lang.String suffix) {         buffer.append(prefix);         for (int i = 0; i < (data.length); ++i) {             if (i > 0) {                 buffer.append(separator);
            }
            buffer.append(data[i]);
        }
        buffer.append(suffix);
    }

    /**
     * Resets all statistics and storage
     */
    public void clear() {
        this.n = 0;
        for (int i = 0; i < (k); ++i) {
            minImpl[i].clear();
            maxImpl[i].clear();
            sumImpl[i].clear();
            sumLogImpl[i].clear();
            sumSqImpl[i].clear();
            geoMeanImpl[i].clear();
            meanImpl[i].clear();
        }
        covarianceImpl.clear();
    }

    /**
     * Returns true iff <code>object</code> is a <code>MultivariateSummaryStatistics</code>
     * instance and all statistics have the same values as this.
     *
     * @param object
     * 		the object to test equality against.
     * @return true if object equals this
     */     @java.lang.Override     public boolean equals(java.lang.Object object) {
        if (object == (this)) {
            return true;
        }
        if ((object instanceof org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics) == false) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics stat = ((org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics) (object));
        return ((((((((org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getGeometricMean(), getGeometricMean())) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getMax(), getMax()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getMean(), getMean()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getMin(), getMin()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getN(), getN()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getSum(), getSum()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getSumSq(), getSumSq()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getSumLog(), getSumLog()))) && 
        (stat.getCovariance().equals(getCovariance()));
    }

    /**
     * Returns hash code based on values of statistics
     *
     * @return hash code
     */
    @java.lang.Override
    public int hashCode() {
        int result = 31 + (org.apache.commons.math.util.MathUtils.hash(getGeometricMean()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getGeometricMean()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getMax()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getMean()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getMin()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getN()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getSum()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getSumSq()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getSumLog()));
        result = (result * 31) + (getCovariance().hashCode());
        return result;
    }

    // Getters and setters for statistics implementations
    /**
     * Sets statistics implementations.
     *
     * @param newImpl
     * 		new implementations for statistics
     * @param oldImpl
     * 		old implementations for statistics
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     private void setImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] newImpl, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] oldImpl) throws java.lang.IllegalStateException, org.apache.commons.math.DimensionMismatchException {         checkEmpty();         checkDimension(newImpl.length);
        java.lang.System.arraycopy(newImpl, 0, oldImpl, 0, newImpl.length);
    }

    /**
     * Returns the currently configured Sum implementation
     *
     * @return the StorelessUnivariateStatistic implementing the sum
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getSumImpl() {
        return sumImpl.clone();
    }

    /**
     * <p>Sets the implementation for the Sum.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param sumImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the Sum
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setSumImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(sumImpl, this.sumImpl);
    }

    /**
     * Returns the currently configured sum of squares implementation
     *
     * @return the StorelessUnivariateStatistic implementing the sum of squares
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getSumsqImpl() {
        return sumSqImpl.clone();
    }

    /**
     * <p>Sets the implementation for the sum of squares.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param sumsqImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the sum of squares
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setSumsqImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumsqImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(sumsqImpl, this.sumSqImpl);
    }

    /**
     * Returns the currently configured minimum implementation
     *
     * @return the StorelessUnivariateStatistic implementing the minimum
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getMinImpl() {
        return minImpl.clone();
    }

    /**
     * <p>Sets the implementation for the minimum.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param minImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the minimum
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setMinImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] minImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(minImpl, this.minImpl);
    }

    /**
     * Returns the currently configured maximum implementation
     *
     * @return the StorelessUnivariateStatistic implementing the maximum
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getMaxImpl() {
        return maxImpl.clone();
    }

    /**
     * <p>Sets the implementation for the maximum.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param maxImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the maximum
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setMaxImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] maxImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(maxImpl, this.maxImpl);
    }

    /**
     * Returns the currently configured sum of logs implementation
     *
     * @return the StorelessUnivariateStatistic implementing the log sum
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getSumLogImpl() {
        return sumLogImpl.clone();
    }

    /**
     * <p>Sets the implementation for the sum of logs.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param sumLogImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the log sum
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] sumLogImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(sumLogImpl, this.sumLogImpl);
    }

    /**
     * Returns the currently configured geometric mean implementation
     *
     * @return the StorelessUnivariateStatistic implementing the geometric mean
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getGeoMeanImpl() {
        return geoMeanImpl.clone();
    }

    /**
     * <p>Sets the implementation for the geometric mean.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param geoMeanImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the geometric mean
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setGeoMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] geoMeanImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(geoMeanImpl, this.geoMeanImpl);
    }

    /**
     * Returns the currently configured mean implementation
     *
     * @return the StorelessUnivariateStatistic implementing the mean
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] getMeanImpl() {
        return meanImpl.clone();
    }

    /**
     * <p>Sets the implementation for the mean.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double[]) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param meanImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the mean
     * @throws DimensionMismatchException
     * 		if the array dimension
     * 		does not match the one used at construction
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic[] meanImpl) throws org.apache.commons.math.DimensionMismatchException {         setImpl(meanImpl, this.meanImpl);
    }

    /**
     * Throws IllegalStateException if n > 0.
     */
    private void checkEmpty() {
        if ((n) > 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
            org.apache.commons.math.util.LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, 
            n);
        }
    }

    /**
     * Throws DimensionMismatchException if dimension != k.
     *
     * @param dimension
     * 		dimension to check
     * @throws DimensionMismatchException
     * 		if dimension != k
     */     private void checkDimension(int dimension) throws org.apache.commons.math.DimensionMismatchException {         if (dimension != (k)) {
            throw new org.apache.commons.math.DimensionMismatchException(dimension, k);
        }
    }

}