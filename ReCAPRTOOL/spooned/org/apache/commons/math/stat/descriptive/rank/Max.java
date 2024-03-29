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
package org.apache.commons.math.stat.descriptive.rank;





/**
 * Returns the maximum of the available values.
 * <p>
 * <ul>
 * <li>The result is <code>NaN</code> iff all values are <code>NaN</code>
 * (i.e. <code>NaN</code> values have no impact on the value of the statistic).</li>
 * <li>If any of the values equals <code>Double.POSITIVE_INFINITY</code>,
 * the result is <code>Double.POSITIVE_INFINITY.</code></li>
 * </ul></p>
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.</p>
 *
 * @version $Revision$ $Date$
 */
public class Max extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -5593383832225844641L;
    /**
     * Number of values that have been added
     */     private long n;
    /**
     * Current value of the statistic
     */     private double value;
    /**
     * Create a Max instance
     */
    public Max() {
        n = 0;
        value = java.lang.Double.NaN;
    }

    /**
     * Copy constructor, creates a new {@code Max} identical
     * to the {@code original}
     *
     * @param original
     * 		the {@code Max} instance to copy
     */     public Max(org.apache.commons.math.stat.descriptive.rank.Max original) {
        org.apache.commons.math.stat.descriptive.rank.Max.copy(original, this);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void increment(final double d) {
        if ((d > (value)) || (java.lang.Double.isNaN(value))) {
            value = d;
        }
        (n)++;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void clear() {
        value = java.lang.Double.NaN;
        n = 0;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double getResult() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public long getN() {
        return n;
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
     */     @java.lang.Override     public double evaluate(final double[] values, final int begin, final int length) {         double max = java.lang.Double.NaN;         if (test(values, begin, length)) {
            max = values[begin];
            for (int i = begin; i < (begin + length); i++) {
                if (!(java.lang.Double.isNaN(values[i]))) {
                    max = (max > (values[i])) ? max : values[i];
                }
            }
        }
        return max;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.rank.Max copy() {
        org.apache.commons.math.stat.descriptive.rank.Max result = new org.apache.commons.math.stat.descriptive.rank.Max();
        org.apache.commons.math.stat.descriptive.rank.Max.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source
     * 		Max to copy
     * @param dest
     * 		Max to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.rank.Max source, org.apache.commons.math.stat.descriptive.rank.Max dest) {         dest.n = source.n;         dest.value = source.value;
    }
}