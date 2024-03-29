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
package org.apache.commons.math.stat.descriptive.moment;









/**
 * Returns the <a href="http://www.xycoon.com/geometric_mean.htm">
 * geometric mean </a> of the available values.
 * <p>
 * Uses a {@link SumOfLogs} instance to compute sum of logs and returns
 * <code> exp( 1/n  (sum of logs) ).</code>  Therefore, </p>
 * <ul>
 * <li>If any of values are < 0, the result is <code>NaN.</code></li>
 * <li>If all values are non-negative and less than
 * <code>Double.POSITIVE_INFINITY</code>,  but at least one value is 0, the
 * result is <code>0.</code></li>
 * <li>If both <code>Double.POSITIVE_INFINITY</code> and
 * <code>Double.NEGATIVE_INFINITY</code> are among the values, the result is
 * <code>NaN.</code></li>
 * </ul> </p>
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.</p>
 *
 * @version $Revision$ $Date$
 */

public class GeometricMean extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -8178734905303459453L;
    /**
     * Wrapped SumOfLogs instance
     */     private org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumOfLogs;
    /**
     * Create a GeometricMean instance
     */
    public GeometricMean() {
        sumOfLogs = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    }

    /**
     * Copy constructor, creates a new {@code GeometricMean} identical
     * to the {@code original}
     *
     * @param original
     * 		the {@code GeometricMean} instance to copy
     */     public GeometricMean(org.apache.commons.math.stat.descriptive.moment.GeometricMean original) {
        super();
        org.apache.commons.math.stat.descriptive.moment.GeometricMean.copy(original, this);
    }

    /**
     * Create a GeometricMean instance using the given SumOfLogs instance
     *
     * @param sumOfLogs
     * 		sum of logs instance to use for computation
     */     public GeometricMean(org.apache.commons.math.stat.descriptive.summary.SumOfLogs sumOfLogs) {         this.sumOfLogs = sumOfLogs;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.moment.GeometricMean copy() {
        org.apache.commons.math.stat.descriptive.moment.GeometricMean result = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        org.apache.commons.math.stat.descriptive.moment.GeometricMean.copy(this, result);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void increment(final double d) {
        sumOfLogs.increment(d);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double getResult() {
        if ((sumOfLogs.getN()) > 0) {
            return java.lang.Math.exp(((sumOfLogs.getResult()) / (sumOfLogs.getN())));
        }else {
            return java.lang.Double.NaN;
        }
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void clear() {
        sumOfLogs.clear();
    }

    /**
     * Returns the geometric mean of the entries in the specified portion
     * of the input array.
     * <p>
     * See {@link GeometricMean} for details on the computing algorithm.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		input array containing the values
     * @param begin
     * 		first array element to include
     * @param length
     * 		the number of elements to include
     * @return the geometric mean or Double.NaN if length = 0 or
     * any of the values are &lt;= 0.
     * @throws IllegalArgumentException
     * 		if the input array is null or the array
     * 		index parameters are not valid
     */     @java.lang.Override     public double evaluate(final double[] values, final int begin, final int length) {         return java.lang.Math.exp(
        ((sumOfLogs.evaluate(values, begin, length)) / length));
    }

    /**
     * {@inheritDoc}
     */
    public long getN() {
        return sumOfLogs.getN();
    }

    /**
     * <p>Sets the implementation for the sum of logs.</p>
     * <p>This method must be activated before any data has been added - i.e.,
     * before {@link #increment(double) increment} has been used to add data;
     * otherwise an IllegalStateException will be thrown.</p>
     *
     * @param sumLogImpl
     * 		the StorelessUnivariateStatistic instance to use
     * 		for computing the log sum
     * @throws IllegalStateException
     * 		if data has already been added
     * 		(i.e if n > 0)
     */     public void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumLogImpl) {
        checkEmpty();
        this.sumOfLogs = sumLogImpl;
    }

    /**
     * Returns the currently configured sum of logs implementation
     *
     * @return the StorelessUnivariateStatistic implementing the log sum
     */
    public org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumLogImpl() {
        return sumOfLogs;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source
     * 		GeometricMean to copy
     * @param dest
     * 		GeometricMean to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.moment.GeometricMean source, org.apache.commons.math.stat.descriptive.moment.GeometricMean dest) {         dest.sumOfLogs = source.sumOfLogs.copy();}


    /**
     * Throws IllegalStateException if n > 0.
     */
    private void checkEmpty() {
        if ((getN()) > 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
            org.apache.commons.math.util.LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, 
            getN());
        }
    }

}