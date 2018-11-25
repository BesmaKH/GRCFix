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
 * Implementation of
 * {@link org.apache.commons.math.stat.descriptive.DescriptiveStatistics} that
 * is safe to use in a multithreaded environment.  Multiple threads can safely
 * operate on a single instance without causing runtime exceptions due to race
 * conditions.  In effect, this implementation makes modification and access
 * methods atomic operations for a single instance.  That is to say, as one
 * thread is computing a statistic from the instance, no other thread can modify
 * the instance nor compute another statistic.
 *
 * @since 1.2
 * @version $Revision$ $Date$
 */
public class SynchronizedDescriptiveStatistics extends org.apache.commons.math.stat.descriptive.DescriptiveStatistics {

    /**
     * Serialization UID
     */     private static final long serialVersionUID = 1L;
    /**
     * Construct an instance with infinite window
     */
    public SynchronizedDescriptiveStatistics() {
        this(org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW);
    }

    /**
     * Construct an instance with finite window
     *
     * @param window
     * 		the finite window size.
     */     public SynchronizedDescriptiveStatistics(int window) {         super(window);
    }

    /**
     * A copy constructor. Creates a deep-copy of the {@code original}.
     *
     * @param original
     * 		the {@code SynchronizedDescriptiveStatistics} instance to copy
     */     public SynchronizedDescriptiveStatistics(org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics original) {
        org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics.copy(original, this);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void addValue(double v) {
        super.addValue(v);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double apply(org.apache.commons.math.stat.descriptive.UnivariateStatistic stat) {
        return super.apply(stat);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void clear() {
        super.clear();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getElement(int index) {
        return super.getElement(index);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized long getN() {
        return super.getN();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getStandardDeviation() {
        return super.getStandardDeviation();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double[] getValues() {
        return super.getValues();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized int getWindowSize() {
        return super.getWindowSize();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setWindowSize(int windowSize) {
        super.setWindowSize(windowSize);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized java.lang.String toString() {
        return super.toString();
    }

    /**
     * Returns a copy of this SynchronizedDescriptiveStatistics instance with the
     * same internal state.
     *
     * @return a copy of this
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics copy() {
        org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics result = 
        new org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics();
        org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     * <p>Acquires synchronization lock on source, then dest before copying.</p>
     *
     * @param source
     * 		SynchronizedDescriptiveStatistics to copy
     * @param dest
     * 		SynchronizedDescriptiveStatistics to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics source, org.apache.commons.math.stat.descriptive.SynchronizedDescriptiveStatistics dest) {         synchronized(source) {
            synchronized(dest) {
                org.apache.commons.math.stat.descriptive.DescriptiveStatistics.copy(source, dest);
            }
        }
    }
}