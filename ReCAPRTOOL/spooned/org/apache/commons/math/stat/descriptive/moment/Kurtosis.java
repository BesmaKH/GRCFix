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
 * Computes the Kurtosis of the available values.
 * <p>
 * We use the following (unbiased) formula to define kurtosis:</p>
 *  <p>
 *  kurtosis = { [n(n+1) / (n -1)(n - 2)(n-3)] sum[(x_i - mean)^4] / std^4 } - [3(n-1)^2 / (n-2)(n-3)]
 *  </p><p>
 *  where n is the number of values, mean is the {@link Mean} and std is the
 * {@link StandardDeviation}</p>
 * <p>
 *  Note that this statistic is undefined for n < 4.  <code>Double.Nan</code>
 *  is returned when there is not sufficient data to compute the statistic.</p>
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.</p>
 *
 * @version $Revision$ $Date$
 */
public class Kurtosis extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 2784465764798260919L;
    /**
     * Fourth Moment on which this statistic is based
     */     protected org.apache.commons.math.stat.descriptive.moment.FourthMoment moment;
    /**
     * Determines whether or not this statistic can be incremented or cleared.
     * <p>
     * Statistics based on (constructed from) external moments cannot
     * be incremented or cleared.</p>
     */
    protected boolean incMoment;

    /**
     * Construct a Kurtosis
     */
    public Kurtosis() {
        incMoment = true;
        moment = new org.apache.commons.math.stat.descriptive.moment.FourthMoment();
    }

    /**
     * Construct a Kurtosis from an external moment
     *
     * @param m4
     * 		external Moment
     */     public Kurtosis(final org.apache.commons.math.stat.descriptive.moment.FourthMoment m4) {
        incMoment = false;
        this.moment = m4;
    }

    /**
     * Copy constructor, creates a new {@code Kurtosis} identical
     * to the {@code original}
     *
     * @param original
     * 		the {@code Kurtosis} instance to copy
     */     public Kurtosis(org.apache.commons.math.stat.descriptive.moment.Kurtosis original) {
        org.apache.commons.math.stat.descriptive.moment.Kurtosis.copy(original, this);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void increment(final double d) {
        if (incMoment) {
            moment.increment(d);
        }else {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
            org.apache.commons.math.util.LocalizedFormats.CANNOT_INCREMENT_STATISTIC_CONSTRUCTED_FROM_EXTERNAL_MOMENTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double getResult() {
        double kurtosis = java.lang.Double.NaN;
        if ((moment.getN()) > 3) {
            double variance = (moment.m2) / ((moment.n) - 1);
            if (((moment.n) <= 3) || (variance < 1.0E-19)) {
                kurtosis = 0.0;
            }else {
                double n = moment.n;
                kurtosis = 
                (((n * (n + 1)) * (moment.m4)) - 
                (((3 * (moment.m2)) * (moment.m2)) * (n - 1))) / 
                (((((n - 1) * (n - 2)) * (n - 3)) * variance) * variance);
            }
        }
        return kurtosis;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void clear() {
        if (incMoment) {
            moment.clear();
        }else {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
            org.apache.commons.math.util.LocalizedFormats.CANNOT_CLEAR_STATISTIC_CONSTRUCTED_FROM_EXTERNAL_MOMENTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    public long getN() {
        return moment.getN();
    }

    /* UnvariateStatistic Approach */

    /**
     * Returns the kurtosis of the entries in the specified portion of the
     * input array.
     * <p>
     * See {@link Kurtosis} for details on the computing algorithm.</p>
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values
     * 		the input array
     * @param begin
     * 		index of the first array element to include
     * @param length
     * 		the number of elements to include
     * @return the kurtosis of the values or Double.NaN if length is less than
     * 4
     * @throws IllegalArgumentException
     * 		if the input array is null or the array
     * 		index parameters are not valid
     */     @java.lang.Override     public double evaluate(final double[] values, final int begin, final int length) {         // Initialize the kurtosis         double kurt = java.lang.Double.NaN;

        if ((test(values, begin, length)) && (length > 3)) {

            // Compute the mean and standard deviation
            org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
            variance.incrementAll(values, begin, length);
            double mean = variance.moment.m1;
            double stdDev = java.lang.Math.sqrt(variance.getResult());

            // Sum the ^4 of the distance from the mean divided by the
            // standard deviation
            double accum3 = 0.0;
            for (int i = begin; i < (begin + length); i++) {
                accum3 += java.lang.Math.pow(((values[i]) - mean), 4.0);
            }
            accum3 /= java.lang.Math.pow(stdDev, 4.0);

            // Get N
            double n0 = length;

            double coefficientOne = 
            (n0 * (n0 + 1)) / (((n0 - 1) * (n0 - 2)) * (n0 - 3));
            double termTwo = 
            (3 * (java.lang.Math.pow((n0 - 1), 2.0))) / ((n0 - 2) * (n0 - 3));

            // Calculate kurtosis
            kurt = (coefficientOne * accum3) - termTwo;
        }
        return kurt;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.moment.Kurtosis copy() {
        org.apache.commons.math.stat.descriptive.moment.Kurtosis result = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        org.apache.commons.math.stat.descriptive.moment.Kurtosis.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source
     * 		Kurtosis to copy
     * @param dest
     * 		Kurtosis to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.moment.Kurtosis source, org.apache.commons.math.stat.descriptive.moment.Kurtosis dest) {         dest.moment = source.moment.copy();         dest.incMoment = source.incMoment;
    }

}