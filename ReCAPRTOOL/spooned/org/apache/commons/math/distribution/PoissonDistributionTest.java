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
package org.apache.commons.math.distribution;



/**
 * <code>PoissonDistributionTest</code>
 *
 * @version $Revision$ $Date$
 */
public class PoissonDistributionTest extends org.apache.commons.math.distribution.IntegerDistributionAbstractTest {

    /**
     * Poisson parameter value for the test distribution.
     */
    private static final double DEFAULT_TEST_POISSON_PARAMETER = 4.0;

    /**
     * Constructor.
     *
     * @param name
     * 		
     */     public PoissonDistributionTest(java.lang.String name) {         super(name);
        setTolerance(1.0E-12);
    }

    /**
     * Creates the default discrete distribution instance to use in tests.
     */
    @java.lang.Override
    public org.apache.commons.math.distribution.IntegerDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.PoissonDistributionImpl(org.apache.commons.math.distribution.PoissonDistributionTest.DEFAULT_TEST_POISSON_PARAMETER);
    }

    /**
     * Creates the default probability density test input values.
     */
    @java.lang.Override
    public int[] makeDensityTestPoints() {
        return new int[]{ -1, 0, 1, 2, 3, 4, 5, 10, 20 };
    }

    /**
     * Creates the default probability density test expected values.
     * These and all other test values are generated by R, version 1.8.1
     */
    @java.lang.Override
    public double[] makeDensityTestValues() {
        return new double[]{ 0.0, 0.0183156388887, 0.073262555555, 
        0.14652511111, 0.195366814813, 0.195366814813, 
        0.156293451851, 0.00529247667642, 8.27746364655E-9 };
    }

    /**
     * Creates the default cumulative probability density test input values.
     */
    @java.lang.Override
    public int[] makeCumulativeTestPoints() {
        return new int[]{ -1, 0, 1, 2, 3, 4, 5, 10, 20 };
    }

    /**
     * Creates the default cumulative probability density test expected values.
     */
    @java.lang.Override
    public double[] makeCumulativeTestValues() {
        return new double[]{ 0.0, 0.0183156388887, 0.0915781944437, 
        0.238103305554, 0.433470120367, 0.62883693518, 
        0.78513038703, 0.99716023388, 0.999999998077 };
    }

    /**
     * Creates the default inverse cumulative probability test input values.
     * Increased 3rd and 7th values slightly as computed cumulative
     * probabilities for corresponding values exceeds the target value (still
     * within tolerance).
     */
    @java.lang.Override
    public double[] makeInverseCumulativeTestPoints() {
        return new double[]{ 0.0, 0.018315638889, 0.0915781944437, 
        0.238103305554, 0.433470120367, 0.62883693518, 
        0.78513038704, 0.99716023388, 0.999999998077 };
    }

    /**
     * Creates the default inverse cumulative probability density test expected values.
     */
    @java.lang.Override
    public int[] makeInverseCumulativeTestValues() {
        return new int[]{ -1, 0, 1, 2, 3, 4, 5, 10, 20 };
    }

    /**
     * Test the normal approximation of the Poisson distribution by
     * calculating P(90 &le; X &le; 110) for X = Po(100) and
     * P(9900 &le; X &le; 10200) for X  = Po(10000)
     */
    public void testNormalApproximateProbability() throws java.lang.Exception {
        org.apache.commons.math.distribution.PoissonDistribution dist = new org.apache.commons.math.distribution.PoissonDistributionImpl(100);
        double result = (dist.normalApproximateProbability(110)) - 
        (dist.normalApproximateProbability(89));
        junit.framework.Assert.assertEquals(0.706281887248, result, 1.0E-10);
        dist.setMean(10000);
        result = (dist.normalApproximateProbability(10200)) - 
        (dist.normalApproximateProbability(9899));
        junit.framework.Assert.assertEquals(0.820070051552, result, 1.0E-10);
    }

    /**
     * Test the degenerate cases of a 0.0 and 1.0 inverse cumulative probability.
     *
     * @throws Exception
     * 		
     */     public void testDegenerateInverseCumulativeProbability() throws java.lang.Exception {         org.apache.commons.math.distribution.PoissonDistribution dist = new org.apache.commons.math.distribution.PoissonDistributionImpl(org.apache.commons.math.distribution.PoissonDistributionTest.DEFAULT_TEST_POISSON_PARAMETER);
        junit.framework.Assert.assertEquals(java.lang.Integer.MAX_VALUE, dist.inverseCumulativeProbability(1.0));
        junit.framework.Assert.assertEquals((-1), dist.inverseCumulativeProbability(0.0));
    }

    public void testMean() {
        org.apache.commons.math.distribution.PoissonDistribution dist = new org.apache.commons.math.distribution.PoissonDistributionImpl(org.apache.commons.math.distribution.PoissonDistributionTest.DEFAULT_TEST_POISSON_PARAMETER);
        try {
            dist.setMean((-1));
            junit.framework.Assert.fail("negative mean.  IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
        }

        dist.setMean(10.0);
        junit.framework.Assert.assertEquals(10.0, dist.getMean(), 0.0);
    }

    public void testLargeMeanCumulativeProbability() {
        org.apache.commons.math.distribution.PoissonDistribution dist = new org.apache.commons.math.distribution.PoissonDistributionImpl(1.0);
        double mean = 1.0;
        while (mean <= 1.0E7) {
            dist.setMean(mean);

            double x = mean * 2.0;
            double dx = x / 10.0;
            double p = java.lang.Double.NaN;
            double sigma = java.lang.Math.sqrt(mean);
            while (x >= 0) {
                try {
                    p = dist.cumulativeProbability(x);
                    junit.framework.Assert.assertFalse(((("NaN cumulative probability returned for mean = " + 
                    mean) + " x = ") + x), java.lang.Double.isNaN(p));
                    if (x > (mean - (2 * sigma))) {
                        junit.framework.Assert.assertTrue(((("Zero cum probaility returned for mean = " + 
                        mean) + " x = ") + x), (p > 0));
                    }
                } catch (org.apache.commons.math.MathException ex) {
                    junit.framework.Assert.fail(((((("mean of " + mean) + " and x of ") + x) + " caused ") + (ex.getMessage())));
                }
                x -= dx;
            } 

            mean *= 10.0;
        } 
    }

    /**
     * JIRA: MATH-282
     */
    public void testCumulativeProbabilitySpecial() throws java.lang.Exception {
        org.apache.commons.math.distribution.PoissonDistribution dist = new org.apache.commons.math.distribution.PoissonDistributionImpl(1.0);
        dist.setMean(9120);
        checkProbability(dist, 9075);
        checkProbability(dist, 9102);
        dist.setMean(5058);
        checkProbability(dist, 5044);
        dist.setMean(6986);
        checkProbability(dist, 6950);
    }

    private void checkProbability(org.apache.commons.math.distribution.PoissonDistribution dist, double x) throws java.lang.Exception {
        double p = dist.cumulativeProbability(x);
        junit.framework.Assert.assertFalse(((("NaN cumulative probability returned for mean = " + 
        (dist.getMean())) + " x = ") + x), java.lang.Double.isNaN(p));
        junit.framework.Assert.assertTrue(((("Zero cum probability returned for mean = " + 
        (dist.getMean())) + " x = ") + x), (p > 0));
    }

    public void testLargeMeanInverseCumulativeProbability() throws java.lang.Exception {
        org.apache.commons.math.distribution.PoissonDistribution dist = new org.apache.commons.math.distribution.PoissonDistributionImpl(1.0);
        double mean = 1.0;
        while (mean <= 100000.0) {             // Extended test value: 1E7.  Reduced to limit run time.
            dist.setMean(mean);
            double p = 0.1;
            double dp = p;
            while (p < 0.99) {
                double ret = java.lang.Double.NaN;
                try {
                    ret = dist.inverseCumulativeProbability(p);
                    // Verify that returned value satisties definition
                    junit.framework.Assert.assertTrue((p >= (dist.cumulativeProbability(ret))));
                    junit.framework.Assert.assertTrue((p < (dist.cumulativeProbability((ret + 1)))));
                } catch (org.apache.commons.math.MathException ex) {
                    junit.framework.Assert.fail(((((("mean of " + mean) + " and p of ") + p) + " caused ") + (ex.getMessage())));
                }
                p += dp;
            } 
            mean *= 10.0;
        } 
    }
}