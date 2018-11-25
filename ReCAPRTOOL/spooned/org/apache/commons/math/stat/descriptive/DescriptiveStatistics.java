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
 * Maintains a dataset of values of a single variable and computes descriptive
 * statistics based on stored data. The {@link #getWindowSize() windowSize}
 * property sets a limit on the number of values that can be stored in the
 * dataset.  The default value, INFINITE_WINDOW, puts no limit on the size of
 * the dataset.  This value should be used with caution, as the backing store
 * will grow without bound in this case.  For very large datasets,
 * {@link SummaryStatistics}, which does not store the dataset, should be used
 * instead of this class. If <code>windowSize</code> is not INFINITE_WINDOW and
 * more values are added than can be stored in the dataset, new values are
 * added in a "rolling" manner, with new values replacing the "oldest" values
 * in the dataset.
 *
 * <p>Note: this class is not threadsafe.  Use
 * {@link SynchronizedDescriptiveStatistics} if concurrent access from multiple
 * threads is required.</p>
 *
 * @version $Revision$ $Date$
 */
public class DescriptiveStatistics implements java.io.Serializable , org.apache.commons.math.stat.descriptive.StatisticalSummary {

    /**
     * Represents an infinite window size.  When the {@link #getWindowSize()}
     * returns this value, there is no limit to the number of data values
     * that can be stored in the dataset.
     */
    public static final int INFINITE_WINDOW = -1;

    /**
     * Serialization UID
     */     private static final long serialVersionUID = 4133067267405273064L;
    /**
     * Name of the setQuantile method.
     */     private static final java.lang.String SET_QUANTILE_METHOD_NAME = "setQuantile";
    /**
     * hold the window size *
     */     protected int windowSize = org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW;
    /**
     * Stored data values
     */
    protected org.apache.commons.math.util.ResizableDoubleArray eDA = new org.apache.commons.math.util.ResizableDoubleArray();

    /**
     * Mean statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic meanImpl = new org.apache.commons.math.stat.descriptive.moment.Mean();
    /**
     * Geometric mean statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic geometricMeanImpl = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
    /**
     * Kurtosis statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic kurtosisImpl = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
    /**
     * Maximum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic maxImpl = new org.apache.commons.math.stat.descriptive.rank.Max();
    /**
     * Minimum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic minImpl = new org.apache.commons.math.stat.descriptive.rank.Min();
    /**
     * Percentile statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic percentileImpl = new org.apache.commons.math.stat.descriptive.rank.Percentile();
    /**
     * Skewness statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic skewnessImpl = new org.apache.commons.math.stat.descriptive.moment.Skewness();
    /**
     * Variance statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic varianceImpl = new org.apache.commons.math.stat.descriptive.moment.Variance();
    /**
     * Sum of squares statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic sumsqImpl = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
    /**
     * Sum statistic implementation - can be reset by setter.
     */     private org.apache.commons.math.stat.descriptive.UnivariateStatistic sumImpl = new org.apache.commons.math.stat.descriptive.summary.Sum();
    /**
     * Construct a DescriptiveStatistics instance with an infinite window
     */
    public DescriptiveStatistics() {
    }

    /**
     * Construct a DescriptiveStatistics instance with the specified window
     *
     * @param window
     * 		the window size.
     */     public DescriptiveStatistics(int window) {
        setWindowSize(window);
    }

    /**
     * Copy constructor.  Construct a new DescriptiveStatistics instance that
     * is a copy of original.
     *
     * @param original
     * 		DescriptiveStatistics instance to copy
     */     public DescriptiveStatistics(org.apache.commons.math.stat.descriptive.DescriptiveStatistics original) {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics.copy(original, this);
    }

    /**
     * Adds the value to the dataset. If the dataset is at the maximum size
     * (i.e., the number of stored elements equals the currently configured
     * windowSize), the first (oldest) element in the dataset is discarded
     * to make room for the new value.
     *
     * @param v
     * 		the value to be added
     */     public void addValue(double v) {
        if ((windowSize) != (org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW)) {
            if ((getN()) == (windowSize)) {
                eDA.addElementRolling(v);
            }else                 if ((getN()) < (windowSize)) {
                    eDA.addElement(v);
                }
        }else {
            eDA.addElement(v);
        }
    }

    /**
     * Removes the most recent value from the dataset.
     */
    public void removeMostRecentValue() {
        eDA.discardMostRecentElements(1);
    }

    /**
     * Replaces the most recently stored value with the given value.
     * There must be at least one element stored to call this method.
     *
     * @param v
     * 		the value to replace the most recent stored value
     * @return replaced value
     */     public double replaceMostRecentValue(double v) {
        return eDA.substituteMostRecentElement(v);
    }

    /**
     * Returns the <a href="http://www.xycoon.com/arithmetic_mean.htm">
     * arithmetic mean </a> of the available values
     *
     * @return The mean or Double.NaN if no values have been added.
     */     public double getMean() {
        return apply(meanImpl);
    }

    /**
     * Returns the <a href="http://www.xycoon.com/geometric_mean.htm">
     * geometric mean </a> of the available values
     *
     * @return The geometricMean, Double.NaN if no values have been added,
     * or if the product of the available values is less than or equal to 0.
     */     public double getGeometricMean() {
        return apply(geometricMeanImpl);
    }

    /**
     * Returns the variance of the available values.
     *
     * @return The variance, Double.NaN if no values have been added
     * or 0.0 for a single value set.
     */     public double getVariance() {
        return apply(varianceImpl);
    }

    /**
     * Returns the standard deviation of the available values.
     *
     * @return The standard deviation, Double.NaN if no values have been added
     * or 0.0 for a single value set.
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
     * Returns the skewness of the available values. Skewness is a
     * measure of the asymmetry of a given distribution.
     *
     * @return The skewness, Double.NaN if no values have been added
     * or 0.0 for a value set &lt;=2.
     */     public double getSkewness() {
        return apply(skewnessImpl);
    }

    /**
     * Returns the Kurtosis of the available values. Kurtosis is a
     * measure of the "peakedness" of a distribution
     *
     * @return The kurtosis, Double.NaN if no values have been added, or 0.0
     * for a value set &lt;=3.
     */     public double getKurtosis() {
        return apply(kurtosisImpl);
    }

    /**
     * Returns the maximum of the available values
     *
     * @return The max or Double.NaN if no values have been added.
     */     public double getMax() {
        return apply(maxImpl);
    }

    /**
     * Returns the minimum of the available values
     *
     * @return The min or Double.NaN if no values have been added.
     */     public double getMin() {
        return apply(minImpl);
    }

    /**
     * Returns the number of available values
     *
     * @return The number of available values
     */     public long getN() {
        return eDA.getNumElements();
    }

    /**
     * Returns the sum of the values that have been added to Univariate.
     *
     * @return The sum or Double.NaN if no values have been added
     */     public double getSum() {
        return apply(sumImpl);
    }

    /**
     * Returns the sum of the squares of the available values.
     *
     * @return The sum of the squares or Double.NaN if no
     * values have been added.
     */     public double getSumsq() {
        return apply(sumsqImpl);
    }

    /**
     * Resets all statistics and storage
     */
    public void clear() {
        eDA.clear();
    }


    /**
     * Returns the maximum number of values that can be stored in the
     * dataset, or INFINITE_WINDOW (-1) if there is no limit.
     *
     * @return The current window size or -1 if its Infinite.
     */
    public int getWindowSize() {
        return windowSize;
    }

    /**
     * WindowSize controls the number of values which contribute
     * to the reported statistics.  For example, if
     * windowSize is set to 3 and the values {1,2,3,4,5}
     * have been added <strong> in that order</strong>
     * then the <i>available values</i> are {3,4,5} and all
     * reported statistics will be based on these values
     *
     * @param windowSize
     * 		sets the size of the window.
     */     public void setWindowSize(int windowSize) {         if (windowSize < 1) {
            if (windowSize != (org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW)) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.NOT_POSITIVE_WINDOW_SIZE, windowSize);
            }
        }

        this.windowSize = windowSize;

        // We need to check to see if we need to discard elements
        // from the front of the array.  If the windowSize is less than
        // the current number of elements.
        if ((windowSize != (org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW)) && (windowSize < (eDA.getNumElements()))) {
            eDA.discardFrontElements(((eDA.getNumElements()) - windowSize));
        }
    }

    /**
     * Returns the current set of values in an array of double primitives.
     * The order of addition is preserved.  The returned array is a fresh
     * copy of the underlying data -- i.e., it is not a reference to the
     * stored data.
     *
     * @return returns the current set of numbers in the order in which they
     * were added to this set
     */
    public double[] getValues() {
        return eDA.getElements();
    }

    /**
     * Returns the current set of values in an array of double primitives,
     * sorted in ascending order.  The returned array is a fresh
     * copy of the underlying data -- i.e., it is not a reference to the
     * stored data.
     *
     * @return returns the current set of
     * numbers sorted in ascending order
     */     public double[] getSortedValues() {
        double[] sort = getValues();
        java.util.Arrays.sort(sort);
        return sort;
    }

    /**
     * Returns the element at the specified index
     *
     * @param index
     * 		The Index of the element
     * @return return the element at the specified index
     */     public double getElement(int index) {         return eDA.getElement(index);
    }

    /**
     * Returns an estimate for the pth percentile of the stored values.
     * <p>
     * The implementation provided here follows the first estimation procedure presented
     * <a href="http://www.itl.nist.gov/div898/handbook/prc/section2/prc252.htm">here.</a>
     * </p><p>
     * <strong>Preconditions</strong>:<ul>
     * <li><code>0 &lt; p &le; 100</code> (otherwise an
     * <code>IllegalArgumentException</code> is thrown)</li>
     * <li>at least one value must be stored (returns <code>Double.NaN
     *     </code> otherwise)</li>
     * </ul></p>
     *
     * @param p
     * 		the requested percentile (scaled from 0 - 100)
     * @return An estimate for the pth percentile of the stored data
     * @throws IllegalStateException
     * 		if percentile implementation has been
     * 		overridden and the supplied implementation does not support setQuantile
     * 		values
     */     public double getPercentile(double p) {         if ((percentileImpl) instanceof org.apache.commons.math.stat.descriptive.rank.Percentile) {
            ((org.apache.commons.math.stat.descriptive.rank.Percentile) (percentileImpl)).setQuantile(p);
        }else {
            try {
                percentileImpl.getClass().getMethod(org.apache.commons.math.stat.descriptive.DescriptiveStatistics.SET_QUANTILE_METHOD_NAME, 
                new java.lang.Class[]{ java.lang.Double.TYPE }).invoke(percentileImpl, 
                new java.lang.Object[]{ java.lang.Double.valueOf(p) });
            } catch (java.lang.NoSuchMethodException e1) {                 // Setter guard should prevent
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, 
                percentileImpl.getClass().getName(), org.apache.commons.math.stat.descriptive.DescriptiveStatistics.SET_QUANTILE_METHOD_NAME);
            } catch (java.lang.IllegalAccessException e2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, 
                org.apache.commons.math.stat.descriptive.DescriptiveStatistics.SET_QUANTILE_METHOD_NAME, percentileImpl.getClass().getName());
            } catch (java.lang.reflect.InvocationTargetException e3) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(e3.getCause());
            }
        }
        return apply(percentileImpl);
    }

    /**
     * Generates a text report displaying univariate statistics from values
     * that have been added.  Each statistic is displayed on a separate
     * line.
     *
     * @return String with line feeds displaying statistics
     */
    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuffer outBuffer = new java.lang.StringBuffer();
        java.lang.String endl = "\n";
        outBuffer.append("DescriptiveStatistics:").append(endl);
        outBuffer.append("n: ").append(getN()).append(endl);
        outBuffer.append("min: ").append(getMin()).append(endl);
        outBuffer.append("max: ").append(getMax()).append(endl);
        outBuffer.append("mean: ").append(getMean()).append(endl);
        outBuffer.append("std dev: ").append(getStandardDeviation()).append(
        endl);
        outBuffer.append("median: ").append(getPercentile(50)).append(endl);
        outBuffer.append("skewness: ").append(getSkewness()).append(endl);
        outBuffer.append("kurtosis: ").append(getKurtosis()).append(endl);
        return outBuffer.toString();
    }

    /**
     * Apply the given statistic to the data associated with this set of statistics.
     *
     * @param stat
     * 		the statistic to apply
     * @return the computed value of the statistic.
     */     public double apply(org.apache.commons.math.stat.descriptive.UnivariateStatistic stat) {         return stat.evaluate(eDA.getInternalValues(), eDA.start(), eDA.getNumElements());
    }

    // Implementation getters and setter

    /**
     * Returns the currently configured mean implementation.
     *
     * @return the UnivariateStatistic implementing the mean
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getMeanImpl() {
        return meanImpl;
    }

    /**
     * <p>Sets the implementation for the mean.</p>
     *
     * @param meanImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the mean
     * @since 1.2
     */     public synchronized void setMeanImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic meanImpl) {
        this.meanImpl = meanImpl;
    }

    /**
     * Returns the currently configured geometric mean implementation.
     *
     * @return the UnivariateStatistic implementing the geometric mean
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getGeometricMeanImpl() {
        return geometricMeanImpl;
    }

    /**
     * <p>Sets the implementation for the gemoetric mean.</p>
     *
     * @param geometricMeanImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the geometric mean
     * @since 1.2
     */     public synchronized void setGeometricMeanImpl(
    org.apache.commons.math.stat.descriptive.UnivariateStatistic geometricMeanImpl) {
        this.geometricMeanImpl = geometricMeanImpl;
    }

    /**
     * Returns the currently configured kurtosis implementation.
     *
     * @return the UnivariateStatistic implementing the kurtosis
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getKurtosisImpl() {
        return kurtosisImpl;
    }

    /**
     * <p>Sets the implementation for the kurtosis.</p>
     *
     * @param kurtosisImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the kurtosis
     * @since 1.2
     */     public synchronized void setKurtosisImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic kurtosisImpl) {
        this.kurtosisImpl = kurtosisImpl;
    }

    /**
     * Returns the currently configured maximum implementation.
     *
     * @return the UnivariateStatistic implementing the maximum
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getMaxImpl() {
        return maxImpl;
    }

    /**
     * <p>Sets the implementation for the maximum.</p>
     *
     * @param maxImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the maximum
     * @since 1.2
     */     public synchronized void setMaxImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic maxImpl) {
        this.maxImpl = maxImpl;
    }

    /**
     * Returns the currently configured minimum implementation.
     *
     * @return the UnivariateStatistic implementing the minimum
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getMinImpl() {
        return minImpl;
    }

    /**
     * <p>Sets the implementation for the minimum.</p>
     *
     * @param minImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the minimum
     * @since 1.2
     */     public synchronized void setMinImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic minImpl) {
        this.minImpl = minImpl;
    }

    /**
     * Returns the currently configured percentile implementation.
     *
     * @return the UnivariateStatistic implementing the percentile
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getPercentileImpl() {
        return percentileImpl;
    }

    /**
     * Sets the implementation to be used by {@link #getPercentile(double)}.
     * The supplied <code>UnivariateStatistic</code> must provide a
     * <code>setQuantile(double)</code> method; otherwise
     * <code>IllegalArgumentException</code> is thrown.
     *
     * @param percentileImpl
     * 		the percentileImpl to set
     * @throws IllegalArgumentException
     * 		if the supplied implementation does not
     * 		provide a <code>setQuantile</code> method
     * @since 1.2
     */     public synchronized void setPercentileImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic percentileImpl) {
        try {
            percentileImpl.getClass().getMethod(org.apache.commons.math.stat.descriptive.DescriptiveStatistics.SET_QUANTILE_METHOD_NAME, 
            new java.lang.Class[]{ java.lang.Double.TYPE }).invoke(percentileImpl, 
            new java.lang.Object[]{ java.lang.Double.valueOf(50.0) });
        } catch (java.lang.NoSuchMethodException e1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, 
            percentileImpl.getClass().getName(), org.apache.commons.math.stat.descriptive.DescriptiveStatistics.SET_QUANTILE_METHOD_NAME);
        } catch (java.lang.IllegalAccessException e2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, 
            org.apache.commons.math.stat.descriptive.DescriptiveStatistics.SET_QUANTILE_METHOD_NAME, percentileImpl.getClass().getName());
        } catch (java.lang.reflect.InvocationTargetException e3) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(e3.getCause());
        }
        this.percentileImpl = percentileImpl;
    }

    /**
     * Returns the currently configured skewness implementation.
     *
     * @return the UnivariateStatistic implementing the skewness
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getSkewnessImpl() {
        return skewnessImpl;
    }

    /**
     * <p>Sets the implementation for the skewness.</p>
     *
     * @param skewnessImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the skewness
     * @since 1.2
     */     public synchronized void setSkewnessImpl(
    org.apache.commons.math.stat.descriptive.UnivariateStatistic skewnessImpl) {
        this.skewnessImpl = skewnessImpl;
    }

    /**
     * Returns the currently configured variance implementation.
     *
     * @return the UnivariateStatistic implementing the variance
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getVarianceImpl() {
        return varianceImpl;
    }

    /**
     * <p>Sets the implementation for the variance.</p>
     *
     * @param varianceImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the variance
     * @since 1.2
     */     public synchronized void setVarianceImpl(
    org.apache.commons.math.stat.descriptive.UnivariateStatistic varianceImpl) {
        this.varianceImpl = varianceImpl;
    }

    /**
     * Returns the currently configured sum of squares implementation.
     *
     * @return the UnivariateStatistic implementing the sum of squares
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getSumsqImpl() {
        return sumsqImpl;
    }

    /**
     * <p>Sets the implementation for the sum of squares.</p>
     *
     * @param sumsqImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the sum of squares
     * @since 1.2
     */     public synchronized void setSumsqImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic sumsqImpl) {
        this.sumsqImpl = sumsqImpl;
    }

    /**
     * Returns the currently configured sum implementation.
     *
     * @return the UnivariateStatistic implementing the sum
     * @since 1.2
     */
    public synchronized org.apache.commons.math.stat.descriptive.UnivariateStatistic getSumImpl() {
        return sumImpl;
    }

    /**
     * <p>Sets the implementation for the sum.</p>
     *
     * @param sumImpl
     * 		the UnivariateStatistic instance to use
     * 		for computing the sum
     * @since 1.2
     */     public synchronized void setSumImpl(org.apache.commons.math.stat.descriptive.UnivariateStatistic sumImpl) {
        this.sumImpl = sumImpl;
    }

    /**
     * Returns a copy of this DescriptiveStatistics instance with the same internal state.
     *
     * @return a copy of this
     */
    public org.apache.commons.math.stat.descriptive.DescriptiveStatistics copy() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics result = new org.apache.commons.math.stat.descriptive.DescriptiveStatistics();
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source
     * 		DescriptiveStatistics to copy
     * @param dest
     * 		DescriptiveStatistics to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.DescriptiveStatistics source, org.apache.commons.math.stat.descriptive.DescriptiveStatistics dest) {         // Copy data and window size         dest.eDA = source.eDA.copy();
        dest.windowSize = source.windowSize;

        // Copy implementations
        dest.maxImpl = source.maxImpl.copy();
        dest.meanImpl = source.meanImpl.copy();
        dest.minImpl = source.minImpl.copy();
        dest.sumImpl = source.sumImpl.copy();
        dest.varianceImpl = source.varianceImpl.copy();
        dest.sumsqImpl = source.sumsqImpl.copy();
        dest.geometricMeanImpl = source.geometricMeanImpl.copy();
        dest.kurtosisImpl = source.kurtosisImpl;
        dest.skewnessImpl = source.skewnessImpl;
        dest.percentileImpl = source.percentileImpl;
    }
}