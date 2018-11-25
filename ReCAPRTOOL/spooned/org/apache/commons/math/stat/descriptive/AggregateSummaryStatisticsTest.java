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
 * Test cases for {@link AggregateSummaryStatistics}
 */

public class AggregateSummaryStatisticsTest extends junit.framework.TestCase {

    /**
     * Tests the standard aggregation behavior
     */
    public void testAggregation() {
        org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics aggregate = new org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics();
        org.apache.commons.math.stat.descriptive.SummaryStatistics setOneStats = aggregate.createContributingStatistics();
        org.apache.commons.math.stat.descriptive.SummaryStatistics setTwoStats = aggregate.createContributingStatistics();

        junit.framework.Assert.assertNotNull("The set one contributing stats are null", setOneStats);
        junit.framework.Assert.assertNotNull("The set two contributing stats are null", setTwoStats);
        junit.framework.Assert.assertNotSame("Contributing stats objects are the same", setOneStats, setTwoStats);

        setOneStats.addValue(2);
        setOneStats.addValue(3);
        setOneStats.addValue(5);
        setOneStats.addValue(7);
        setOneStats.addValue(11);
        junit.framework.Assert.assertEquals("Wrong number of set one values", 5, setOneStats.getN());
        junit.framework.Assert.assertEquals("Wrong sum of set one values", 28.0, setOneStats.getSum());

        setTwoStats.addValue(2);
        setTwoStats.addValue(4);
        setTwoStats.addValue(8);
        junit.framework.Assert.assertEquals("Wrong number of set two values", 3, setTwoStats.getN());
        junit.framework.Assert.assertEquals("Wrong sum of set two values", 14.0, setTwoStats.getSum());

        junit.framework.Assert.assertEquals("Wrong number of aggregate values", 8, aggregate.getN());
        junit.framework.Assert.assertEquals("Wrong aggregate sum", 42.0, aggregate.getSum());
    }

    /**
     * Verify that aggregating over a partition gives the same results
     * as direct computation.
     *
     *  1) Randomly generate a dataset of 10-100 values
     *     from [-100, 100]
     *  2) Divide the dataset it into 2-5 partitions
     *  3) Create an AggregateSummaryStatistic and ContributingStatistics
     *     for each partition
     *  4) Compare results from the AggregateSummaryStatistic with values
     *     returned by a single SummaryStatistics instance that is provided
     *     the full dataset
     */
    public void testAggregationConsistency() throws java.lang.Exception {

        // Generate a random sample and random partition
        double[] totalSample = generateSample();
        double[][] subSamples = generatePartition(totalSample);
        int nSamples = subSamples.length;

        // Create aggregator and total stats for comparison
        org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics aggregate = new org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics();
        org.apache.commons.math.stat.descriptive.SummaryStatistics totalStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();

        // Create array of component stats
        org.apache.commons.math.stat.descriptive.SummaryStatistics[] componentStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics[nSamples];

        for (int i = 0; i < nSamples; i++) {

            // Make componentStats[i] a contributing statistic to aggregate
            componentStats[i] = aggregate.createContributingStatistics();

            // Add values from subsample
            for (int j = 0; j < (subSamples[i].length); j++) {
                componentStats[i].addValue(subSamples[i][j]);
            }
        }

        // Compute totalStats directly
        for (int i = 0; i < (totalSample.length); i++) {
            totalStats.addValue(totalSample[i]);
        }

        /* Compare statistics in totalStats with aggregate.
        Note that guaranteed success of this comparison depends on the
        fact that <aggregate> gets values in exactly the same order
        as <totalStats>.
         */
        junit.framework.Assert.

        assertEquals(totalStats.getSummary(), aggregate.getSummary());

    }

    /**
     * Test aggregate function by randomly generating a dataset of 10-100 values
     * from [-100, 100], dividing it into 2-5 partitions, computing stats for each
     * partition and comparing the result of aggregate(...) applied to the collection
     * of per-partition SummaryStatistics with a single SummaryStatistics computed
     * over the full sample.
     *
     * @throws Exception
     * 		
     */     public void testAggregate() throws java.lang.Exception {

        // Generate a random sample and random partition
        double[] totalSample = generateSample();
        double[][] subSamples = generatePartition(totalSample);
        int nSamples = subSamples.length;

        // Compute combined stats directly
        org.apache.commons.math.stat.descriptive.SummaryStatistics totalStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 0; i < (totalSample.length); i++) {
            totalStats.addValue(totalSample[i]);
        }

        // Now compute subsample stats individually and aggregate
        org.apache.commons.math.stat.descriptive.SummaryStatistics[] subSampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics[nSamples];
        for (int i = 0; i < nSamples; i++) {
            subSampleStats[i] = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        }
        java.util.Collection<org.apache.commons.math.stat.descriptive.SummaryStatistics> aggregate = new java.util.ArrayList<org.apache.commons.math.stat.descriptive.SummaryStatistics>();
        for (int i = 0; i < nSamples; i++) {
            for (int j = 0; j < (subSamples[i].length); j++) {
                subSampleStats[i].addValue(subSamples[i][j]);
            }
            aggregate.add(subSampleStats[i]);
        }

        // Compare values
        org.apache.commons.math.stat.descriptive.StatisticalSummary aggregatedStats = org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.aggregate(aggregate);
        org.apache.commons.math.stat.descriptive.AggregateSummaryStatisticsTest.assertEquals(totalStats.getSummary(), aggregatedStats, 1.0E-11);
    }


    public void testAggregateDegenerate() throws java.lang.Exception {
        double[] totalSample = new double[]{ 1, 2, 3, 4, 5 };
        double[][] subSamples = new double[][]{ new double[]{ 1 }, new double[]{ 2 }, new double[]{ 3 }, new double[]{ 4 }, new double[]{ 5 } };

        // Compute combined stats directly
        org.apache.commons.math.stat.descriptive.SummaryStatistics totalStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 0; i < (totalSample.length); i++) {
            totalStats.addValue(totalSample[i]);
        }

        // Now compute subsample stats individually and aggregate
        org.apache.commons.math.stat.descriptive.SummaryStatistics[] subSampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics[5];
        for (int i = 0; i < 5; i++) {
            subSampleStats[i] = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        }
        java.util.Collection<org.apache.commons.math.stat.descriptive.SummaryStatistics> aggregate = new java.util.ArrayList<org.apache.commons.math.stat.descriptive.SummaryStatistics>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < (subSamples[i].length); j++) {
                subSampleStats[i].addValue(subSamples[i][j]);
            }
            aggregate.add(subSampleStats[i]);
        }

        // Compare values
        org.apache.commons.math.stat.descriptive.StatisticalSummaryValues aggregatedStats = org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.aggregate(aggregate);
        org.apache.commons.math.stat.descriptive.AggregateSummaryStatisticsTest.assertEquals(totalStats.getSummary(), aggregatedStats, 1.0E-11);
    }

    public void testAggregateSpecialValues() throws java.lang.Exception {
        double[] totalSample = new double[]{ java.lang.Double.POSITIVE_INFINITY, 2, 3, java.lang.Double.NaN, 5 };
        double[][] subSamples = new double[][]{ new double[]{ java.lang.Double.POSITIVE_INFINITY, 2 }, new double[]{ 3 }, new double[]{ java.lang.Double.NaN }, new double[]{ 5 } };

        // Compute combined stats directly
        org.apache.commons.math.stat.descriptive.SummaryStatistics totalStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 0; i < (totalSample.length); i++) {
            totalStats.addValue(totalSample[i]);
        }

        // Now compute subsample stats individually and aggregate
        org.apache.commons.math.stat.descriptive.SummaryStatistics[] subSampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics[5];
        for (int i = 0; i < 4; i++) {
            subSampleStats[i] = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        }
        java.util.Collection<org.apache.commons.math.stat.descriptive.SummaryStatistics> aggregate = new java.util.ArrayList<org.apache.commons.math.stat.descriptive.SummaryStatistics>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < (subSamples[i].length); j++) {
                subSampleStats[i].addValue(subSamples[i][j]);
            }
            aggregate.add(subSampleStats[i]);
        }

        // Compare values
        org.apache.commons.math.stat.descriptive.StatisticalSummaryValues aggregatedStats = org.apache.commons.math.stat.descriptive.AggregateSummaryStatistics.aggregate(aggregate);
        org.apache.commons.math.stat.descriptive.AggregateSummaryStatisticsTest.assertEquals(totalStats.getSummary(), aggregatedStats, 1.0E-11);

    }

    /**
     * Verifies that a StatisticalSummary and a StatisticalSummaryValues are equal up
     * to delta, with NaNs, infinities returned in the same spots. For max, min, n, values
     * have to agree exactly, delta is used only for sum, mean, variance, std dev.
     */
    protected static void assertEquals(org.apache.commons.math.stat.descriptive.StatisticalSummary expected, org.apache.commons.math.stat.descriptive.StatisticalSummary observed, double delta) {
        org.apache.commons.math.TestUtils.assertEquals(expected.getMax(), observed.getMax(), 0);
        org.apache.commons.math.TestUtils.assertEquals(expected.getMin(), observed.getMin(), 0);
        junit.framework.Assert.assertEquals(expected.getN(), observed.getN());
        org.apache.commons.math.TestUtils.assertEquals(expected.getSum(), observed.getSum(), delta);
        org.apache.commons.math.TestUtils.assertEquals(expected.getMean(), observed.getMean(), delta);
        org.apache.commons.math.TestUtils.assertEquals(expected.getStandardDeviation(), observed.getStandardDeviation(), delta);
        org.apache.commons.math.TestUtils.assertEquals(expected.getVariance(), observed.getVariance(), delta);
    }


    /**
     * Generates a random sample of double values.
     * Sample size is random, between 10 and 100 and values are
     * uniformly distributed over [-100, 100].
     *
     * @return array of random double values
     */
    private double[] generateSample() {
        final org.apache.commons.math.random.RandomData randomData = new org.apache.commons.math.random.RandomDataImpl();
        final int sampleSize = randomData.nextInt(10, 100);
        double[] out = new double[sampleSize];
        for (int i = 0; i < (out.length); i++) {
            out[i] = randomData.nextUniform((-100), 100);
        }
        return out;
    }

    /**
     * Generates a partition of <sample> into up to 5 sequentially selected
     * subsamples with randomly selected partition points.
     *
     * @param sample
     * 		array to partition
     * @return rectangular array with rows = subsamples
     */     private double[][] generatePartition(double[] sample) {
        final int length = sample.length;
        final double[][] out = new double[5][];
        final org.apache.commons.math.random.RandomData randomData = new org.apache.commons.math.random.RandomDataImpl();
        int cur = 0;
        int offset = 0;
        int sampleCount = 0;
        for (int i = 0; i < 5; i++) {
            if ((cur == length) || (offset == length)) {
                break;
            }
            final int next = ((i == 4) || (cur == (length - 1))) ? length - 1 : randomData.nextInt(cur, (length - 1));
            final int subLength = (next - cur) + 1;
            out[i] = new double[subLength];
            java.lang.System.arraycopy(sample, offset, out[i], 0, subLength);
            cur = next + 1;
            sampleCount++;
            offset += subLength;
        }
        if (sampleCount < 5) {
            double[][] out2 = new double[sampleCount][];
            for (int j = 0; j < sampleCount; j++) {
                final int curSize = out[j].length;
                out2[j] = new double[curSize];
                java.lang.System.arraycopy(out[j], 0, out2[j], 0, curSize);
            }
            return out2;
        }else {
            return out;
        }
    }

}