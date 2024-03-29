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
 * Computes a statistic related to the Second Central Moment.  Specifically,
 * what is computed is the sum of squared deviations from the sample mean.
 * <p>
 * The following recursive updating formula is used:</p>
 * <p>
 * Let <ul>
 * <li> dev = (current obs - previous mean) </li>
 * <li> n = number of observations (including current obs) </li>
 * </ul>
 * Then</p>
 * <p>
 * new value = old value + dev^2 * (n -1) / n.</p>
 * <p>
 * Returns <code>Double.NaN</code> if no data values have been added and
 * returns <code>0</code> if there is just one value in the data set.</p>
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.</p>
 *
 * @version $Revision$ $Date$
 */
public class SecondMoment extends org.apache.commons.math.stat.descriptive.moment.FirstMoment implements java.io.Serializable {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 3942403127395076445L;
    /**
     * second moment of values that have been added
     */     protected double m2;
    /**
     * Create a SecondMoment instance
     */
    public SecondMoment() {
        super();
        m2 = java.lang.Double.NaN;
    }

    /**
     * Copy constructor, creates a new {@code SecondMoment} identical
     * to the {@code original}
     *
     * @param original
     * 		the {@code SecondMoment} instance to copy
     */     public SecondMoment(org.apache.commons.math.stat.descriptive.moment.SecondMoment original) {
        super(original);
        this.m2 = original.m2;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void increment(final double d) {
        if ((n) < 1) {
            m1 = m2 = 0.0;
        }
        super.increment(d);
        m2 += ((((double) (n)) - 1) * (dev)) * (nDev);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public void clear() {
        super.clear();
        m2 = java.lang.Double.NaN;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double getResult() {
        return m2;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.moment.SecondMoment copy() {
        org.apache.commons.math.stat.descriptive.moment.SecondMoment result = new org.apache.commons.math.stat.descriptive.moment.SecondMoment();
        org.apache.commons.math.stat.descriptive.moment.SecondMoment.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source
     * 		SecondMoment to copy
     * @param dest
     * 		SecondMoment to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.moment.SecondMoment source, org.apache.commons.math.stat.descriptive.moment.SecondMoment dest) {         org.apache.commons.math.stat.descriptive.moment.FirstMoment.copy(source, dest);         dest.m2 = source.m2;
    }

}