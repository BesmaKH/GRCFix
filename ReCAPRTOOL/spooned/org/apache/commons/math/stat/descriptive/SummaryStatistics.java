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
 * <p>
 * Computes summary statistics for a stream of data values added using the
 * {@link #addValue(double) addValue} method. The data values are not stored in
 * memory, so this class can be used to compute statistics for very large data
 * streams.
 * </p>
 * <p>
 * The {@link StorelessUnivariateStatistic} instances used to maintain summary
 * state and compute statistics are configurable via setters. For example, the
 * default implementation for the variance can be overridden by calling
 * {@link #setVarianceImpl(StorelessUnivariateStatistic)}. Actual parameters to
 * these methods must implement the {@link StorelessUnivariateStatistic}
 * interface and configuration must be completed before <code>addValue</code>
 * is called. No configuration is necessary to use the default, commons-math
 * provided implementations.
 * </p>
 * <p>
 * Note: This class is not thread-safe. Use
 * {@link SynchronizedSummaryStatistics} if concurrent access from multiple
 * threads is required.
 * </p>
 *
 * @version $Revision$ $Date$
 */ public class SummaryStatistics implements java.io.Serializable , org.apache.commons.math.stat.descriptive.StatisticalSummary {

    /**
     * Serialization UID
     */     private static final long serialVersionUID = -2021321786743555871L;
    /**
     * count of values that have been added
     */     protected long n = 0;
    /**
     * SecondMoment is used to compute the mean and variance
     */     protected org.apache.commons.math.stat.descriptive.moment.SecondMoment secondMoment = new org.apache.commons.math.stat.descriptive.moment.SecondMoment();
    /**
     * sum of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.summary.Sum sum = new org.apache.commons.math.stat.descriptive.summary.Sum();
    /**
     * sum of the square of each value that has been added
     */     protected org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumsq = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
    /**
     * min of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.rank.Min min = new org.apache.commons.math.stat.descriptive.rank.Min();
    /**
     * max of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.rank.Max max = new org.apache.commons.math.stat.descriptive.rank.Max();
    /**
     * sumLog of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumLog = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    /**
     * geoMean of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.moment.GeometricMean geoMean = new org.apache.commons.math.stat.descriptive.moment.GeometricMean(sumLog);
    /**
     * mean of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.moment.Mean mean = new org.apache.commons.math.stat.descriptive.moment.Mean();
    /**
     * variance of values that have been added
     */     protected org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
    /**
     * Sum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumImpl = sum;
    /**
     * Sum of squares statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumsqImpl = sumsq;
    /**
     * Minimum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic minImpl = min;
    /**
     * Maximum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic maxImpl = max;
    /**
     * Sum of log statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumLogImpl = sumLog;
    /**
     * Geometric mean statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic geoMeanImpl = geoMean;
    /**
     * Mean statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic meanImpl = mean;
    /**
     * Variance statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic varianceImpl = variance;
    /**
     * Construct a SummaryStatistics instance
     */
    public SummaryStatistics() {
    }

    /**
     * A copy constructor. Creates a deep-copy of the {@code original}.
     *
     * @param original
     * 		the {@code SummaryStatistics} instance to copy
     */     public SummaryStatistics(org.apache.commons.math.stat.descriptive.SummaryStatistics original) {
        org.apache.commons.math.stat.descriptive.SummaryStatistics.copy(original, this);
    }

    /**
     * Return a {@link StatisticalSummaryValues} instance reporting current
     * statistics.
     *
     * @return Current values of statistics
     */     public org.apache.commons.math.stat.descriptive.StatisticalSummary getSummary() {
        return new org.apache.commons.math.stat.descriptive.StatisticalSummaryValues(getMean(), getVariance(), getN(), 
        getMax(), getMin(), getSum());
    }

    /**
     * Add a value to the data
     *
     * @param value
     * 		the value to add
     */     public void addValue(double value) {         sumImpl.increment(value);
        sumsqImpl.increment(value);
        minImpl.increment(value);
        maxImpl.increment(value);
        sumLogImpl.increment(value);
        secondMoment.increment(value);
        // If mean, variance or geomean have been overridden,
        // need to increment these
        if (!((meanImpl) instanceof org.apache.commons.math.stat.descriptive.moment.Mean)) {
            meanImpl.increment(value);
        }
        if (!((varianceImpl) instanceof org.apache.commons.math.stat.descriptive.moment.Variance)) {
            varianceImpl.increment(value);
        }
        if (!((geoMeanImpl) instanceof org.apache.commons.math.stat.descriptive.moment.GeometricMean)) {
            geoMeanImpl.increment(value);
        }
        (n)++;
    }

    /**
     * Returns the number of available values
     *
     * @return The number of available values
     */     public long getN() {
        return n;
    }

    /**
     * Returns the sum of the values that have been added
     *
     * @return The sum or <code>Double.NaN</code> if no values have been added
     */     public double getSum() {
        return sumImpl.getResult();
    }

    /**
     * Returns the sum of the squares of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return The sum of squares
     */     public double getSumsq() {
        return sumsqImpl.getResult();
    }

    /**
     * Returns the mean of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the mean
     */     public double getMean() {
        if ((mean) == (meanImpl)) {
            return new org.apache.commons.math.stat.descriptive.moment.Mean(secondMoment).getResult();
        }else {
            return meanImpl.getResult();
        }
    }

    /**
     * Returns the standard deviation of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the standard deviation
     */     public double getStandardDeviation() {
        double stdDev = java.lang.Double.NaN;
        if ((getN()) > 0) {
            if ((getN()) > 1) {
                stdDev = java.lang.Math.sqrt(getVariance());
            }else {
                stdDev = 0.0;
            }
        }
        return stdDev;
    }

    /**
     * Returns the variance of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the variance
     */     public double getVariance() {
        if ((varianceImpl) == (variance)) {
            return new org.apache.commons.math.stat.descriptive.moment.Variance(secondMoment).getResult();
        }else {
            return varianceImpl.getResult();
        }
    }

    /**
     * Returns the maximum of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the maximum
     */     public double getMax() {
        return maxImpl.getResult();
    }

    /**
     * Returns the minimum of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the minimum
     */     public double getMin() {
        return minImpl.getResult();
    }

    /**
     * Returns the geometric mean of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the geometric mean
     */     public double getGeometricMean() {
        return geoMeanImpl.getResult();
    }

    /**
     * Returns the sum of the logs of the values that have been added.
     * <p>
     * Double.NaN is returned if no values have been added.
     * </p>
     *
     * @return the sum of logs
     * @since 1.2
     */     public double getSumOfLogs() {
        return sumLogImpl.getResult();
    }

    /**
     * Returns a statistic related to the Second Central Moment.  Specifically,
     * what is returned is the sum of squared deviations from the sample mean
     * among the values that have been added.
     * <p>
     * Returns <code>Double.NaN</code> if no data values have been added and
     * returns <code>0</code> if there is just one value in the data set.</p>
     * <p>
     *
     * @return second central moment statistic
     * @since 2.0
     */     public double getSecondMoment() {
        return secondMoment.getResult();
    }

    /**
     * Generates a text report displaying summary statistics from values that
     * have been added.
     *
     * @return String with line feeds displaying statistics
     * @since 1.2
     */     @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuffer outBuffer = new java.lang.StringBuffer();
        java.lang.String endl = "\n";
        outBuffer.append("SummaryStatistics:").append(endl);
        outBuffer.append("n: ").append(getN()).append(endl);
        outBuffer.append("min: ").append(getMin()).append(endl);
        outBuffer.append("max: ").append(getMax()).append(endl);
        outBuffer.append("mean: ").append(getMean()).append(endl);
        outBuffer.append("geometric mean: ").append(getGeometricMean()).append(
        endl);
        outBuffer.append("variance: ").append(getVariance()).append(endl);
        outBuffer.append("sum of squares: ").append(getSumsq()).append(endl);
        outBuffer.append("standard deviation: ").append(getStandardDeviation()).append(
        endl);
        outBuffer.append("sum of logs: ").append(getSumOfLogs()).append(endl);
        return outBuffer.toString();
    }

    /**
     * Resets all statistics and storage
     */
    public void clear() {
        this.n = 0;
        minImpl.clear();
        maxImpl.clear();
        sumImpl.clear();
        sumLogImpl.clear();
        sumsqImpl.clear();
        geoMeanImpl.clear();
        secondMoment.clear();
        if ((meanImpl) != (mean)) {
            meanImpl.clear();
        }
        if ((varianceImpl) != (variance)) {
            varianceImpl.clear();
        }
    }

    /**
     * Returns true iff <code>object</code> is a
     * <code>SummaryStatistics</code> instance and all statistics have the
     * same values as this.
     *
     * @param object
     * 		the object to test equality against.
     * @return true if object equals this
     */     @java.lang.Override     public boolean equals(java.lang.Object object) {
        if (object == (this)) {
            return true;
        }
        if ((object instanceof org.apache.commons.math.stat.descriptive.SummaryStatistics) == false) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.SummaryStatistics stat = ((org.apache.commons.math.stat.descriptive.SummaryStatistics) (object));
        return (((((((org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getGeometricMean(), getGeometricMean())) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getMax(), getMax()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getMean(), getMean()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getMin(), getMin()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getN(), getN()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getSum(), getSum()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getSumsq(), getSumsq()))) && 
        (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(stat.getVariance(), getVariance()));
    }

    /**
     * Returns hash code based on values of statistics
     *
     * @return hash code
     */     @java.lang.Override
    public int hashCode() {
        int result = 31 + (org.apache.commons.math.util.MathUtils.hash(getGeometricMean()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getGeometricMean()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getMax()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getMean()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getMin()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getN()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getSum()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getSumsq()));
        result = (result * 31) + (org.apache.commons.math.util.MathUtils.hash(getVariance()));
        return result;
    }

    // Getters and setters for statistics implementations
    /**
     * Returns the currently configured Sum implementation
     *
     * @return the StorelessUnivariateStatistic implementing the sum
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumImpl() {
        return sumImpl;
    }

    /**
     * <p>
     * Sets the implementation for the Sum.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param sumImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the Sum
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setSumImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumImpl) {         checkEmpty();         this.sumImpl = sumImpl;
    }

    /**
     * Returns the currently configured sum of squares implementation
     *
     * @return the StorelessUnivariateStatistic implementing the sum of squares
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumsqImpl() {
        return sumsqImpl;
    }

    /**
     * <p>
     * Sets the implementation for the sum of squares.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param sumsqImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the sum of squares
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setSumsqImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumsqImpl) {         checkEmpty();         this.sumsqImpl = sumsqImpl;
    }

    /**
     * Returns the currently configured minimum implementation
     *
     * @return the StorelessUnivariateStatistic implementing the minimum
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMinImpl() {
        return minImpl;
    }

    /**
     * <p>
     * Sets the implementation for the minimum.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param minImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the minimum
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setMinImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic minImpl) {         checkEmpty();         this.minImpl = minImpl;
    }

    /**
     * Returns the currently configured maximum implementation
     *
     * @return the StorelessUnivariateStatistic implementing the maximum
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMaxImpl() {
        return maxImpl;
    }

    /**
     * <p>
     * Sets the implementation for the maximum.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param maxImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the maximum
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setMaxImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic maxImpl) {         checkEmpty();         this.maxImpl = maxImpl;
    }

    /**
     * Returns the currently configured sum of logs implementation
     *
     * @return the StorelessUnivariateStatistic implementing the log sum
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumLogImpl() {
        return sumLogImpl;
    }

    /**
     * <p>
     * Sets the implementation for the sum of logs.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param sumLogImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the log sum
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumLogImpl) {         checkEmpty();         this.sumLogImpl = sumLogImpl;
        geoMean.setSumLogImpl(sumLogImpl);
    }

    /**
     * Returns the currently configured geometric mean implementation
     *
     * @return the StorelessUnivariateStatistic implementing the geometric mean
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getGeoMeanImpl() {
        return geoMeanImpl;
    }

    /**
     * <p>
     * Sets the implementation for the geometric mean.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param geoMeanImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the geometric mean
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setGeoMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic geoMeanImpl) {         checkEmpty();         this.geoMeanImpl = geoMeanImpl;
    }

    /**
     * Returns the currently configured mean implementation
     *
     * @return the StorelessUnivariateStatistic implementing the mean
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMeanImpl() {
        return meanImpl;
    }

    /**
     * <p>
     * Sets the implementation for the mean.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param meanImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the mean
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic meanImpl) {         checkEmpty();         this.meanImpl = meanImpl;
    }

    /**
     * Returns the currently configured variance implementation
     *
     * @return the StorelessUnivariateStatistic implementing the variance
     * @since 1.2
     */     public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getVarianceImpl() {
        return varianceImpl;
    }

    /**
     * <p>
     * Sets the implementation for the variance.
     * </p>
     * <p>
     * This method must be activated before any data has been added - i.e.,
     * before {@link #addValue(double) addValue} has been used to add data;
     * otherwise an IllegalStateException will be thrown.
     * </p>
     *
     * @param varianceImpl
     * 		the StorelessUnivariateStatistic instance to use for
     * 		computing the variance
     * @throws IllegalStateException
     * 		if data has already been added (i.e if n >
     * 		0)
     * @since 1.2
     */     public void setVarianceImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic varianceImpl) {         checkEmpty();         this.varianceImpl = varianceImpl;
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
     * Returns a copy of this SummaryStatistics instance with the same internal state.
     *
     * @return a copy of this
     */
    public org.apache.commons.math.stat.descriptive.SummaryStatistics copy() {
        org.apache.commons.math.stat.descriptive.SummaryStatistics result = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        org.apache.commons.math.stat.descriptive.SummaryStatistics.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source
     * 		SummaryStatistics to copy
     * @param dest
     * 		SummaryStatistics to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.SummaryStatistics source, org.apache.commons.math.stat.descriptive.SummaryStatistics dest) {         dest.maxImpl = source.maxImpl.copy();         dest.meanImpl = source.meanImpl.copy();
        dest.minImpl = source.minImpl.copy();
        dest.sumImpl = source.sumImpl.copy();
        dest.varianceImpl = source.varianceImpl.copy();
        dest.sumLogImpl = source.sumLogImpl.copy();
        dest.sumsqImpl = source.sumsqImpl.copy();
        if ((source.getGeoMeanImpl()) instanceof org.apache.commons.math.stat.descriptive.moment.GeometricMean) {
            // Keep geoMeanImpl, sumLogImpl in synch
            dest.geoMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean(((org.apache.commons.math.stat.descriptive.summary.SumOfLogs) (dest.sumLogImpl)));
        }else {
            dest.geoMeanImpl = source.geoMeanImpl.copy();
        }
        org.apache.commons.math.stat.descriptive.moment.SecondMoment.copy(source.secondMoment, dest.secondMoment);
        dest.n = source.n;

        // Make sure that if stat == statImpl in source, same
        // holds in dest; otherwise copy stat
        if ((source.geoMean) == (source.geoMeanImpl)) {
            dest.geoMean = ((org.apache.commons.math.stat.descriptive.moment.GeometricMean) (dest.geoMeanImpl));
        }else {
            org.apache.commons.math.stat.descriptive.moment.GeometricMean.copy(source.geoMean, dest.geoMean);
        }
        if ((source.max) == (source.maxImpl)) {
            dest.max = ((org.apache.commons.math.stat.descriptive.rank.Max) (dest.maxImpl));
        }else {
            org.apache.commons.math.stat.descriptive.rank.Max.copy(source.max, dest.max);
        }
        if ((source.mean) == (source.meanImpl)) {
            dest.mean = ((org.apache.commons.math.stat.descriptive.moment.Mean) (dest.meanImpl));
        }else {
            org.apache.commons.math.stat.descriptive.moment.Mean.copy(source.mean, dest.mean);
        }
        if ((source.min) == (source.minImpl)) {
            dest.min = ((org.apache.commons.math.stat.descriptive.rank.Min) (dest.minImpl));
        }else {
            org.apache.commons.math.stat.descriptive.rank.Min.copy(source.min, dest.min);
        }
        if ((source.sum) == (source.sumImpl)) {
            dest.sum = ((org.apache.commons.math.stat.descriptive.summary.Sum) (dest.sumImpl));
        }else {
            org.apache.commons.math.stat.descriptive.summary.Sum.copy(source.sum, dest.sum);
        }
        if ((source.variance) == (source.varianceImpl)) {
            dest.variance = ((org.apache.commons.math.stat.descriptive.moment.Variance) (dest.varianceImpl));
        }else {
            org.apache.commons.math.stat.descriptive.moment.Variance.copy(source.variance, dest.variance);
        }
        if ((source.sumLog) == (source.sumLogImpl)) {
            dest.sumLog = ((org.apache.commons.math.stat.descriptive.summary.SumOfLogs) (dest.sumLogImpl));
        }else {
            org.apache.commons.math.stat.descriptive.summary.SumOfLogs.copy(source.sumLog, dest.sumLog);
        }
        if ((source.sumsq) == (source.sumsqImpl)) {
            dest.sumsq = ((org.apache.commons.math.stat.descriptive.summary.SumOfSquares) (dest.sumsqImpl));
        }else {
            org.apache.commons.math.stat.descriptive.summary.SumOfSquares.copy(source.sumsq, dest.sumsq);
        }
    }
}