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
package org.apache.commons.math.stat;















/**
 * StatUtils provides static methods for computing statistics based on data
 * stored in double[] arrays.
 *
 * @version $Revision$ $Date$
 */
public final class StatUtils {

    /**
     * sum
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic SUM = new org.apache.commons.math.stat.descriptive.summary.Sum();
    /**
     * sumSq
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic SUM_OF_SQUARES = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
    /**
     * prod
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic PRODUCT = new org.apache.commons.math.stat.descriptive.summary.Product();
    /**
     * sumLog
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic SUM_OF_LOGS = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    /**
     * min
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic MIN = new org.apache.commons.math.stat.descriptive.rank.Min();
    /**
     * max
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic MAX = new org.apache.commons.math.stat.descriptive.rank.Max();
    /**
     * mean
     */     private static final org.apache.commons.math.stat.descriptive.UnivariateStatistic MEAN = new org.apache.commons.math.stat.descriptive.moment.Mean();
    /**
     * variance
     */     private static final org.apache.commons.math.stat.descriptive.moment.Variance VARIANCE = new org.apache.commons.math.stat.descriptive.moment.Variance();
    /**
     * percentile
     */     private static final org.apache.commons.math.stat.descriptive.rank.Percentile PERCENTILE = new org.apache.commons.math.stat.descriptive.rank.Percentile();
    /**
     * geometric mean
     */     private static final org.apache.commons.math.stat.descriptive.moment.GeometricMean GEOMETRIC_MEAN = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
    /**
     * Private Constructor
     */
    private StatUtils() {
    }

    /**
     * Returns the sum of the values in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the input array
     * is null.</p>
     *
     * @param values
     * 		array of values to sum
     * @return the sum of the values or <code>Double.NaN</code> if the array
     * is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double sum(final double[] values) {         return org.apache.commons.math.stat.StatUtils.SUM.evaluate(values);
    }

    /**
     * Returns the sum of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the sum of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double sum(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.SUM.evaluate(values, begin, length);}

    /**
     * Returns the sum of the squares of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		input array
     * @return the sum of the squared values or <code>Double.NaN</code> if the
     * array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double sumSq(final double[] values) {         return org.apache.commons.math.stat.StatUtils.SUM_OF_SQUARES.evaluate(values);
    }

    /**
     * Returns the sum of the squares of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the sum of the squares of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double sumSq(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.SUM_OF_SQUARES.evaluate(values, begin, length);}

    /**
     * Returns the product of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @return the product of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double product(final double[] values) {         return org.apache.commons.math.stat.StatUtils.PRODUCT.evaluate(values);
    }

    /**
     * Returns the product of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the product of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double product(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.PRODUCT.evaluate(values, begin, length);}

    /**
     * Returns the sum of the natural logs of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.summary.SumOfLogs}.
     * </p>
     *
     * @param values
     * 		the input array
     * @return the sum of the natural logs of the values or Double.NaN if
     * the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double sumLog(final double[] values) {         return org.apache.commons.math.stat.StatUtils.SUM_OF_LOGS.evaluate(values);
    }

    /**
     * Returns the sum of the natural logs of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.summary.SumOfLogs}.
     * </p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the sum of the natural logs of the values or Double.NaN if
     * length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double sumLog(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.SUM_OF_LOGS.evaluate(values, begin, length);}

    /**
     * Returns the arithmetic mean of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.Mean} for
     * details on the computing algorithm.</p>
     *
     * @param values
     * 		the input array
     * @return the mean of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double mean(final double[] values) {         return org.apache.commons.math.stat.StatUtils.MEAN.evaluate(values);
    }

    /**
     * Returns the arithmetic mean of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.Mean} for
     * details on the computing algorithm.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the mean of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double mean(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.MEAN.evaluate(values, begin, length);}

    /**
     * Returns the geometric mean of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.GeometricMean}
     * for details on the computing algorithm.</p>
     *
     * @param values
     * 		the input array
     * @return the geometric mean of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double geometricMean(final double[] values) {         return org.apache.commons.math.stat.StatUtils.GEOMETRIC_MEAN.evaluate(values);
    }

    /**
     * Returns the geometric mean of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.GeometricMean}
     * for details on the computing algorithm.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the geometric mean of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double geometricMean(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.GEOMETRIC_MEAN.evaluate(values, begin, length);}


    /**
     * Returns the variance of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.Variance} for
     * details on the computing algorithm.</p>
     * <p>
     * Returns 0 for a single-value (i.e. length = 1) sample.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @return the variance of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double variance(final double[] values) {         return org.apache.commons.math.stat.StatUtils.VARIANCE.evaluate(values);
    }

    /**
     * Returns the variance of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.Variance} for
     * details on the computing algorithm.</p>
     * <p>
     * Returns 0 for a single-value (i.e. length = 1) sample.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null or the
     * array index parameters are not valid.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the variance of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double variance(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.VARIANCE.evaluate(values, begin, length);}

    /**
     * Returns the variance of the entries in the specified portion of
     * the input array, using the precomputed mean value.  Returns
     * <code>Double.NaN</code> if the designated subarray is empty.
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.Variance} for
     * details on the computing algorithm.</p>
     * <p>
     * The formula used assumes that the supplied mean value is the arithmetic
     * mean of the sample data, not a known population parameter.  This method
     * is supplied only to save computation when the mean has already been
     * computed.</p>
     * <p>
     * Returns 0 for a single-value (i.e. length = 1) sample.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null or the
     * array index parameters are not valid.</p>
     *
     * @param values
     * 		the input array
     * @param mean
     * 		the precomputed mean value
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the variance of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double variance(final double[] values, final double mean, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.VARIANCE.evaluate(values, mean, begin, length);}
    /**
     * Returns the variance of the entries in the input array, using the
     * precomputed mean value.  Returns <code>Double.NaN</code> if the array
     * is empty.
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.moment.Variance} for
     * details on the computing algorithm.</p>
     * <p>
     * The formula used assumes that the supplied mean value is the arithmetic
     * mean of the sample data, not a known population parameter.  This method
     * is supplied only to save computation when the mean has already been
     * computed.</p>
     * <p>
     * Returns 0 for a single-value (i.e. length = 1) sample.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @param mean
     * 		the precomputed mean value
     * @return the variance of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double variance(final double[] values, final double mean) {         return org.apache.commons.math.stat.StatUtils.VARIANCE.evaluate(values, mean);}

    /**
     * Returns the maximum of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * <ul>
     * <li>The result is <code>NaN</code> iff all values are <code>NaN</code>
     * (i.e. <code>NaN</code> values have no impact on the value of the statistic).</li>
     * <li>If any of the values equals <code>Double.POSITIVE_INFINITY</code>,
     * the result is <code>Double.POSITIVE_INFINITY.</code></li>
     * </ul></p>
     *
     * @param values
     * 		the input array
     * @return the maximum of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double max(final double[] values) {         return org.apache.commons.math.stat.StatUtils.MAX.evaluate(values);
    }

    /**
     * Returns the maximum of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null or
     * the array index parameters are not valid.</p>
     * <p>
     * <ul>
     * <li>The result is <code>NaN</code> iff all values are <code>NaN</code>
     * (i.e. <code>NaN</code> values have no impact on the value of the statistic).</li>
     * <li>If any of the values equals <code>Double.POSITIVE_INFINITY</code>,
     * the result is <code>Double.POSITIVE_INFINITY.</code></li>
     * </ul></p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the maximum of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double max(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.MAX.evaluate(values, begin, length);}

    /**
     * Returns the minimum of the entries in the input array, or
     * <code>Double.NaN</code> if the array is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     * <p>
     * <ul>
     * <li>The result is <code>NaN</code> iff all values are <code>NaN</code>
     * (i.e. <code>NaN</code> values have no impact on the value of the statistic).</li>
     * <li>If any of the values equals <code>Double.NEGATIVE_INFINITY</code>,
     * the result is <code>Double.NEGATIVE_INFINITY.</code></li>
     * </ul> </p>
     *
     * @param values
     * 		the input array
     * @return the minimum of the values or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if the array is null
     */     public static double min(final double[] values) {         return org.apache.commons.math.stat.StatUtils.MIN.evaluate(values);
    }

    /**
     * Returns the minimum of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null or
     * the array index parameters are not valid.</p>
     * <p>
     * <ul>
     * <li>The result is <code>NaN</code> iff all values are <code>NaN</code>
     * (i.e. <code>NaN</code> values have no impact on the value of the statistic).</li>
     * <li>If any of the values equals <code>Double.NEGATIVE_INFINITY</code>,
     * the result is <code>Double.NEGATIVE_INFINITY.</code></li>
     * </ul></p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the minimum of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException
     * 		if the array is null or the array index
     * 		parameters are not valid
     */     public static double min(final double[] values, final int begin, final int length) {         return org.apache.commons.math.stat.StatUtils.MIN.evaluate(values, begin, length);}

    /**
     * Returns an estimate of the <code>p</code>th percentile of the values
     * in the <code>values</code> array.
     * <p>
     * <ul>
     * <li>Returns <code>Double.NaN</code> if <code>values</code> has length
     * <code>0</code></li></p>
     * <li>Returns (for any value of <code>p</code>) <code>values[0]</code>
     *  if <code>values</code> has length <code>1</code></li>
     * <li>Throws <code>IllegalArgumentException</code> if <code>values</code>
     * is null  or p is not a valid quantile value (p must be greater than 0
     * and less than or equal to 100)</li>
     * </ul></p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.rank.Percentile} for
     * a description of the percentile estimation algorithm used.</p>
     *
     * @param values
     * 		input array of values
     * @param p
     * 		the percentile value to compute
     * @return the percentile value or Double.NaN if the array is empty
     * @throws IllegalArgumentException
     * 		if <code>values</code> is null
     * 		or p is invalid
     */     public static double percentile(final double[] values, final double p) {         return org.apache.commons.math.stat.StatUtils.PERCENTILE.evaluate(values, p);}

    /**
     * Returns an estimate of the <code>p</code>th percentile of the values
     * in the <code>values</code> array, starting with the element in (0-based)
     * position <code>begin</code> in the array and including <code>length</code>
     * values.
     * <p>
     * <ul>
     * <li>Returns <code>Double.NaN</code> if <code>length = 0</code></li>
     * <li>Returns (for any value of <code>p</code>) <code>values[begin]</code>
     *  if <code>length = 1 </code></li>
     * <li>Throws <code>IllegalArgumentException</code> if <code>values</code>
     *  is null , <code>begin</code> or <code>length</code> is invalid, or
     * <code>p</code> is not a valid quantile value (p must be greater than 0
     * and less than or equal to 100)</li>
     * </ul></p>
     * <p>
     * See {@link org.apache.commons.math.stat.descriptive.rank.Percentile} for
     * a description of the percentile estimation algorithm used.</p>
     *
     * @param values
     * 		array of input values
     * @param p
     * 		the percentile to compute
     * @param begin
     * 		the first (0-based) element to include in the computation
     * @param length
     * 		the number of array elements to include
     * @return the percentile value
     * @throws IllegalArgumentException
     * 		if the parameters are not valid or the
     * 		input array is null
     */     public static double percentile(final double[] values, final int begin, final int length, final double p) {         return org.apache.commons.math.stat.StatUtils.PERCENTILE.evaluate(values, begin, length, p);}
    /**
     * Returns the sum of the (signed) differences between corresponding elements of the
     * input arrays -- i.e., sum(sample1[i] - sample2[i]).
     *
     * @param sample1
     * 		the first array
     * @param sample2
     * 		the second array
     * @return sum of paired differences
     * @throws IllegalArgumentException
     * 		if the arrays do not have the same
     * 		(positive) length
     */     public static double sumDifference(final double[] sample1, final double[] sample2) throws java.lang.IllegalArgumentException {         int n = sample1.length;
        if (n != (sample2.length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, n, sample2.length);
        }
        if (n < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_DIMENSION, sample2.length, 1);
        }
        double result = 0;
        for (int i = 0; i < n; i++) {
            result += (sample1[i]) - (sample2[i]);
        }
        return result;
    }

    /**
     * Returns the mean of the (signed) differences between corresponding elements of the
     * input arrays -- i.e., sum(sample1[i] - sample2[i]) / sample1.length.
     *
     * @param sample1
     * 		the first array
     * @param sample2
     * 		the second array
     * @return mean of paired differences
     * @throws IllegalArgumentException
     * 		if the arrays do not have the same
     * 		(positive) length
     */     public static double meanDifference(final double[] sample1, final double[] sample2) throws java.lang.IllegalArgumentException {         return (org.apache.commons.math.stat.StatUtils.sumDifference(sample1, sample2)) / (sample1.length);
    }

    /**
     * Returns the variance of the (signed) differences between corresponding elements of the
     * input arrays -- i.e., var(sample1[i] - sample2[i]).
     *
     * @param sample1
     * 		the first array
     * @param sample2
     * 		the second array
     * @param meanDifference
     * 		the mean difference between corresponding entries
     * @see #meanDifference(double[],double[])
     * @return variance of paired differences
     * @throws IllegalArgumentException
     * 		if the arrays do not have the same
     * 		length or their common length is less than 2.
     */     public static double varianceDifference(final double[] sample1, final double[] sample2, double meanDifference) throws java.lang.IllegalArgumentException {         double sum1 = 0.0;         double sum2 = 0.0;
        double diff = 0.0;
        int n = sample1.length;
        if (n != (sample2.length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, n, sample2.length);
        }
        if (n < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_DIMENSION, n, 2);
        }
        for (int i = 0; i < n; i++) {
            diff = (sample1[i]) - (sample2[i]);
            sum1 += (diff - meanDifference) * (diff - meanDifference);
            sum2 += diff - meanDifference;
        }
        return (sum1 - ((sum2 * sum2) / n)) / (n - 1);
    }

}