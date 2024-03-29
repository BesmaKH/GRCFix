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
 * {@link org.apache.commons.math.stat.descriptive.SummaryStatistics} that
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
public class SynchronizedSummaryStatistics extends org.apache.commons.math.stat.descriptive.SummaryStatistics {

    /**
     * Serialization UID
     */     private static final long serialVersionUID = 1909861009042253704L;
    /**
     * Construct a SynchronizedSummaryStatistics instance
     */
    public SynchronizedSummaryStatistics() {
        super();
    }

    /**
     * A copy constructor. Creates a deep-copy of the {@code original}.
     *
     * @param original
     * 		the {@code SynchronizedSummaryStatistics} instance to copy
     */     public SynchronizedSummaryStatistics(org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics original) {
        org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics.copy(original, this);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StatisticalSummary getSummary() {
        return super.getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void addValue(double value) {
        super.addValue(value);
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
    public synchronized double getSum() {
        return super.getSum();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getSumsq() {
        return super.getSumsq();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getMean() {
        return super.getMean();
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
    public synchronized double getVariance() {
        return super.getVariance();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getMax() {
        return super.getMax();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getMin() {
        return super.getMin();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized double getGeometricMean() {
        return super.getGeometricMean();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized java.lang.String toString() {
        return super.toString();
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
    public synchronized boolean equals(java.lang.Object object) {
        return super.equals(object);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumImpl() {
        return super.getSumImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setSumImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumImpl) {
        super.setSumImpl(sumImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumsqImpl() {
        return super.getSumsqImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setSumsqImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumsqImpl) {
        super.setSumsqImpl(sumsqImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMinImpl() {
        return super.getMinImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setMinImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic minImpl) {
        super.setMinImpl(minImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMaxImpl() {
        return super.getMaxImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setMaxImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic maxImpl) {
        super.setMaxImpl(maxImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getSumLogImpl() {
        return super.getSumLogImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setSumLogImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic sumLogImpl) {
        super.setSumLogImpl(sumLogImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getGeoMeanImpl() {
        return super.getGeoMeanImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setGeoMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic geoMeanImpl) {
        super.setGeoMeanImpl(geoMeanImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getMeanImpl() {
        return super.getMeanImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setMeanImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic meanImpl) {
        super.setMeanImpl(meanImpl);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic getVarianceImpl() {
        return super.getVarianceImpl();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public synchronized void setVarianceImpl(org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic varianceImpl) {
        super.setVarianceImpl(varianceImpl);
    }

    /**
     * Returns a copy of this SynchronizedSummaryStatistics instance with the
     * same internal state.
     *
     * @return a copy of this
     */
    @java.lang.Override
    public synchronized org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics copy() {
        org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics result = 
        new org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics();
        org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics.copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     * <p>Acquires synchronization lock on source, then dest before copying.</p>
     *
     * @param source
     * 		SynchronizedSummaryStatistics to copy
     * @param dest
     * 		SynchronizedSummaryStatistics to copy to
     * @throws NullPointerException
     * 		if either source or dest is null
     */     public static void copy(org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics source, org.apache.commons.math.stat.descriptive.SynchronizedSummaryStatistics dest) {         synchronized(source) {
            synchronized(dest) {
                org.apache.commons.math.stat.descriptive.SummaryStatistics.copy(source, dest);
            }
        }
    }

}