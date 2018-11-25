/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.commons.math.stat.descriptive;








/**
 * Test cases for the DescriptiveStatistics class.
 *
 * @version $Revision$ $Date: 2007-08-16 15:36:33 -0500 (Thu, 16 Aug
 * 2007) $
 */
public class DescriptiveStatisticsTest extends junit.framework.TestCase {

    public DescriptiveStatisticsTest(java.lang.String name) {
        super(name);
    }

    protected org.apache.commons.math.stat.descriptive.DescriptiveStatistics createDescriptiveStatistics() {
        return new org.apache.commons.math.stat.descriptive.DescriptiveStatistics();
    }

    public void testSetterInjection() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics stats = createDescriptiveStatistics();
        stats.addValue(1);
        stats.addValue(3);
        junit.framework.Assert.assertEquals(2, stats.getMean(), 1.0E-10);
        // Now lets try some new math
        stats.setMeanImpl(new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.deepMean());
        junit.framework.Assert.assertEquals(42, stats.getMean(), 1.0E-10);
    }

    public void testCopy() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics stats = createDescriptiveStatistics();
        stats.addValue(1);
        stats.addValue(3);
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics copy = new org.apache.commons.math.stat.descriptive.DescriptiveStatistics(stats);
        junit.framework.Assert.assertEquals(2, copy.getMean(), 1.0E-10);
        // Now lets try some new math
        stats.setMeanImpl(new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.deepMean());
        copy = stats.copy();
        junit.framework.Assert.assertEquals(42, copy.getMean(), 1.0E-10);
    }

    public void testWindowSize() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics stats = createDescriptiveStatistics();
        stats.setWindowSize(300);
        for (int i = 0; i < 100; ++i) {
            stats.addValue((i + 1));
        }
        int refSum = (100 * 101) / 2;
        junit.framework.Assert.assertEquals((refSum / 100.0), stats.getMean(), 1.0E-10);
        junit.framework.Assert.assertEquals(300, stats.getWindowSize());
        try {
            stats.setWindowSize((-3));
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalArgumentException iae) {
            // expected
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail(("wrong exception caught: " + (e.getMessage())));
        }
        junit.framework.Assert.assertEquals(300, stats.getWindowSize());
        stats.setWindowSize(50);
        junit.framework.Assert.assertEquals(50, stats.getWindowSize());
        int refSum2 = refSum - ((50 * 51) / 2);
        junit.framework.Assert.assertEquals((refSum2 / 50.0), stats.getMean(), 1.0E-10);
    }

    public void testGetValues() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics stats = createDescriptiveStatistics();
        for (int i = 100; i > 0; --i) {
            stats.addValue(i);
        }
        int refSum = (100 * 101) / 2;
        junit.framework.Assert.assertEquals((refSum / 100.0), stats.getMean(), 1.0E-10);
        double[] v = stats.getValues();
        for (int i = 0; i < (v.length); ++i) {
            junit.framework.Assert.assertEquals((100.0 - i), v[i], 1.0E-10);
        }
        double[] s = stats.getSortedValues();
        for (int i = 0; i < (s.length); ++i) {
            junit.framework.Assert.assertEquals((i + 1.0), s[i], 1.0E-10);
        }
        junit.framework.Assert.assertEquals(12.0, stats.getElement(88), 1.0E-10);
    }

    public void testToString() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics stats = createDescriptiveStatistics();
        stats.addValue(1);
        stats.addValue(2);
        stats.addValue(3);
        java.util.Locale d = java.util.Locale.getDefault();
        java.util.Locale.setDefault(java.util.Locale.US);
        junit.framework.Assert.assertEquals(("DescriptiveStatistics:\n" + 
        ((((((("n: 3\n" + 
        "min: 1.0\n") + 
        "max: 3.0\n") + 
        "mean: 2.0\n") + 
        "std dev: 1.0\n") + 
        "median: 2.0\n") + 
        "skewness: 0.0\n") + 
        "kurtosis: NaN\n")), stats.toString());
        java.util.Locale.setDefault(d);
    }

    public void testShuffledStatistics() {
        // the purpose of this test is only to check the get/set methods
        // we are aware shuffling statistics like this is really not
        // something sensible to do in production ...
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics reference = createDescriptiveStatistics();
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics shuffled = createDescriptiveStatistics();

        org.apache.commons.math.stat.descriptive.UnivariateStatistic tmp = shuffled.getGeometricMeanImpl();
        shuffled.setGeometricMeanImpl(shuffled.getMeanImpl());
        shuffled.setMeanImpl(shuffled.getKurtosisImpl());
        shuffled.setKurtosisImpl(shuffled.getSkewnessImpl());
        shuffled.setSkewnessImpl(shuffled.getVarianceImpl());
        shuffled.setVarianceImpl(shuffled.getMaxImpl());
        shuffled.setMaxImpl(shuffled.getMinImpl());
        shuffled.setMinImpl(shuffled.getSumImpl());
        shuffled.setSumImpl(shuffled.getSumsqImpl());
        shuffled.setSumsqImpl(tmp);

        for (int i = 100; i > 0; --i) {
            reference.addValue(i);
            shuffled.addValue(i);
        }

        junit.framework.Assert.assertEquals(reference.getMean(), shuffled.getGeometricMean(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getKurtosis(), shuffled.getMean(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getSkewness(), shuffled.getKurtosis(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getVariance(), shuffled.getSkewness(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getMax(), shuffled.getVariance(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getMin(), shuffled.getMax(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getSum(), shuffled.getMin(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getSumsq(), shuffled.getSum(), 1.0E-10);
        junit.framework.Assert.assertEquals(reference.getGeometricMean(), shuffled.getSumsq(), 1.0E-10);

    }

    public void testPercentileSetter() throws java.lang.Exception {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics stats = createDescriptiveStatistics();
        stats.addValue(1);
        stats.addValue(2);
        stats.addValue(3);
        junit.framework.Assert.assertEquals(2, stats.getPercentile(50.0), 1.0E-10);

        // Inject wrapped Percentile impl
        stats.setPercentileImpl(new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.goodPercentile());
        junit.framework.Assert.assertEquals(2, stats.getPercentile(50.0), 1.0E-10);

        // Try "new math" impl
        stats.setPercentileImpl(new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.subPercentile());
        junit.framework.Assert.assertEquals(10.0, stats.getPercentile(10.0), 1.0E-10);

        // Try to set bad impl
        try {
            stats.setPercentileImpl(new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.badPercentile());
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

    public void test20090720() {
        org.apache.commons.math.stat.descriptive.DescriptiveStatistics descriptiveStatistics = new org.apache.commons.math.stat.descriptive.DescriptiveStatistics(100);
        for (int i = 0; i < 161; i++) {
            descriptiveStatistics.addValue(1.2);
        }
        descriptiveStatistics.clear();
        descriptiveStatistics.addValue(1.2);
        junit.framework.Assert.assertEquals(1, descriptiveStatistics.getN());
    }

    public void testRemoval() {

        final org.apache.commons.math.stat.descriptive.DescriptiveStatistics dstat = createDescriptiveStatistics();

        checkremoval(dstat, 1, 6.0, 0.0, java.lang.Double.NaN);
        checkremoval(dstat, 3, 5.0, 3.0, 4.5);
        checkremoval(dstat, 6, 3.5, 2.5, 3.0);
        checkremoval(dstat, 9, 3.5, 2.5, 3.0);
        checkremoval(dstat, org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW, 3.5, 2.5, 3.0);

    }

    public void checkremoval(org.apache.commons.math.stat.descriptive.DescriptiveStatistics dstat, int wsize, 
    double mean1, double mean2, double mean3) {

        dstat.setWindowSize(wsize);
        dstat.clear();

        for (int i = 1; i <= 6; ++i) {
            dstat.addValue(i);
        }

        junit.framework.Assert.assertTrue(org.apache.commons.math.util.MathUtils.equalsIncludingNaN(mean1, dstat.getMean()));
        dstat.replaceMostRecentValue(0);
        junit.framework.Assert.assertTrue(org.apache.commons.math.util.MathUtils.equalsIncludingNaN(mean2, dstat.getMean()));
        dstat.removeMostRecentValue();
        junit.framework.Assert.assertTrue(org.apache.commons.math.util.MathUtils.equalsIncludingNaN(mean3, dstat.getMean()));

    }

    // Test UnivariateStatistics impls for setter injection tests

    /**
     * A new way to compute the mean
     */
    static class deepMean implements org.apache.commons.math.stat.descriptive.UnivariateStatistic {

        public double evaluate(double[] values, int begin, int length) {
            return 42;
        }

        public double evaluate(double[] values) {
            return 42;
        }
        public org.apache.commons.math.stat.descriptive.UnivariateStatistic copy() {
            return new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.deepMean();
        }
    }

    /**
     * Test percentile implementation - wraps a Percentile
     */
    static class goodPercentile implements org.apache.commons.math.stat.descriptive.UnivariateStatistic {
        private org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        public void setQuantile(double quantile) {
            percentile.setQuantile(quantile);
        }
        public double evaluate(double[] values, int begin, int length) {
            return percentile.evaluate(values, begin, length);
        }
        public double evaluate(double[] values) {
            return percentile.evaluate(values);
        }
        public org.apache.commons.math.stat.descriptive.UnivariateStatistic copy() {
            org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.goodPercentile result = new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.goodPercentile();
            result.setQuantile(percentile.getQuantile());
            return result;
        }
    }

    /**
     * Test percentile subclass - another "new math" impl
     * Always returns currently set quantile
     */
    static class subPercentile extends org.apache.commons.math.stat.descriptive.rank.Percentile {
        @java.lang.Override
        public double evaluate(double[] values, int begin, int length) {
            return getQuantile();
        }
        @java.lang.Override
        public double evaluate(double[] values) {
            return getQuantile();
        }
        private static final long serialVersionUID = 8040701391045914979L;
        @java.lang.Override
        public org.apache.commons.math.stat.descriptive.rank.Percentile copy() {
            org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.subPercentile result = new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.subPercentile();
            return result;
        }
    }

    /**
     * "Bad" test percentile implementation - no setQuantile
     */
    static class badPercentile implements org.apache.commons.math.stat.descriptive.UnivariateStatistic {
        private org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        public double evaluate(double[] values, int begin, int length) {
            return percentile.evaluate(values, begin, length);
        }
        public double evaluate(double[] values) {
            return percentile.evaluate(values);
        }
        public org.apache.commons.math.stat.descriptive.UnivariateStatistic copy() {
            return new org.apache.commons.math.stat.descriptive.DescriptiveStatisticsTest.badPercentile();
        }
    }

}