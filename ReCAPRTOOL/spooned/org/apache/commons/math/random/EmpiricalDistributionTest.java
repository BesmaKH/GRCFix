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
package org.apache.commons.math.random;












/**
 * Test cases for the EmpiricalDistribution class
 *
 * @version $Revision$ $Date$
 */

public final class EmpiricalDistributionTest extends org.apache.commons.math.RetryTestCase {

    protected org.apache.commons.math.random.EmpiricalDistribution empiricalDistribution = null;
    protected org.apache.commons.math.random.EmpiricalDistribution empiricalDistribution2 = null;
    protected java.io.File file = null;
    protected java.net.URL url = null;
    protected double[] dataArray = null;

    public EmpiricalDistributionTest(java.lang.String name) {
        super(name);
    }

    @java.lang.Override
    public void setUp() throws java.io.IOException {
        empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl(100);
        url = getClass().getResource("testData.txt");

        empiricalDistribution2 = new org.apache.commons.math.random.EmpiricalDistributionImpl(100);
        java.io.BufferedReader in = 
        new java.io.BufferedReader(new java.io.InputStreamReader(
        url.openStream()));
        java.lang.String str = null;
        java.util.ArrayList<java.lang.Double> list = new java.util.ArrayList<java.lang.Double>();
        while ((str = in.readLine()) != null) {
            list.add(java.lang.Double.valueOf(str));
        } 
        in.close();
        in = null;

        dataArray = new double[list.size()];
        int i = 0;
        for (java.lang.Double data : list) {
            dataArray[i] = data.doubleValue();
            i++;
        }
    }

    /**
     * Test EmpiricalDistrbution.load() using sample data file.<br>
     * Check that the sampleCount, mu and sigma match data in
     * the sample data file.
     */
    public void testLoad() throws java.lang.Exception {
        empiricalDistribution.load(url);
        // testData File has 10000 values, with mean ~ 5.0, std dev ~ 1
        // Make sure that loaded distribution matches this
        junit.framework.Assert.assertEquals(empiricalDistribution.getSampleStats().getN(), 1000, 1.0E-6);
        // TODO: replace with statistical tests
        junit.framework.Assert.assertEquals(
        empiricalDistribution.getSampleStats().getMean(), 
        5.069831575018909, 1.0E-6);
        junit.framework.Assert.assertEquals(
        empiricalDistribution.getSampleStats().getStandardDeviation(), 
        1.0173699343977738, 1.0E-6);
    }

    /**
     * Test EmpiricalDistrbution.load(double[]) using data taken from
     * sample data file.<br>
     * Check that the sampleCount, mu and sigma match data in
     * the sample data file.
     */
    public void testDoubleLoad() throws java.lang.Exception {
        empiricalDistribution2.load(dataArray);
        // testData File has 10000 values, with mean ~ 5.0, std dev ~ 1
        // Make sure that loaded distribution matches this
        junit.framework.Assert.assertEquals(empiricalDistribution2.getSampleStats().getN(), 1000, 1.0E-6);
        // TODO: replace with statistical tests
        junit.framework.Assert.assertEquals(
        empiricalDistribution2.getSampleStats().getMean(), 
        5.069831575018909, 1.0E-6);
        junit.framework.Assert.assertEquals(
        empiricalDistribution2.getSampleStats().getStandardDeviation(), 
        1.0173699343977738, 1.0E-6);

        double[] bounds = ((org.apache.commons.math.random.EmpiricalDistributionImpl) (empiricalDistribution2)).getGeneratorUpperBounds();
        junit.framework.Assert.assertEquals(bounds.length, 100);
        junit.framework.Assert.assertEquals(bounds[99], 1.0, 1.0E-11);

    }

    /**
     * Generate 1000 random values and make sure they look OK.<br>
     * Note that there is a non-zero (but very small) probability that
     * these tests will fail even if the code is working as designed.
     */
    public void testNext() throws java.lang.Exception {
        tstGen(0.1);
        tstDoubleGen(0.1);
    }

    /**
     * Make sure exception thrown if digest getNext is attempted
     * before loading empiricalDistribution.
     */
    public void testNexFail() {
        try {
            empiricalDistribution.getNextValue();
            empiricalDistribution2.getNextValue();
            junit.framework.Assert.fail("Expecting IllegalStateException");
        } catch (java.lang.IllegalStateException ex) {
            // expected
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    /**
     * Make sure we can handle a grid size that is too fine
     */
    public void testGridTooFine() throws java.lang.Exception {
        empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl(1001);
        tstGen(0.1);
        empiricalDistribution2 = new org.apache.commons.math.random.EmpiricalDistributionImpl(1001);
        tstDoubleGen(0.1);
    }

    /**
     * How about too fat?
     */
    public void testGridTooFat() throws java.lang.Exception {
        empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl(1);
        tstGen(5);// ridiculous tolerance; but ridiculous grid size
        // really just checking to make sure we do not bomb
        empiricalDistribution2 = new org.apache.commons.math.random.EmpiricalDistributionImpl(1);
        tstDoubleGen(5);
    }

    /**
     * Test bin index overflow problem (BZ 36450)
     */
    public void testBinIndexOverflow() throws java.lang.Exception {
        double[] x = new double[]{ 9474.94326071674, 2080107.8865462579 };
        new org.apache.commons.math.random.EmpiricalDistributionImpl().load(x);
    }

    public void testSerialization() {
        // Empty
        org.apache.commons.math.random.EmpiricalDistribution dist = new org.apache.commons.math.random.EmpiricalDistributionImpl();
        org.apache.commons.math.random.EmpiricalDistribution dist2 = ((org.apache.commons.math.random.EmpiricalDistribution) (org.apache.commons.math.TestUtils.serializeAndRecover(dist)));
        verifySame(dist, dist2);

        // Loaded
        empiricalDistribution2.load(dataArray);
        dist2 = ((org.apache.commons.math.random.EmpiricalDistribution) (org.apache.commons.math.TestUtils.serializeAndRecover(empiricalDistribution2)));
        verifySame(empiricalDistribution2, dist2);
    }

    public void testLoadNullDoubleArray() {
        org.apache.commons.math.random.EmpiricalDistribution dist = new org.apache.commons.math.random.EmpiricalDistributionImpl();
        try {
            dist.load(((double[]) (null)));
            junit.framework.Assert.fail("load((double[]) null) expected NullPointerException");
        } catch (java.lang.NullPointerException e) {
            // expected
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    public void testLoadNullURL() throws java.lang.Exception {
        org.apache.commons.math.random.EmpiricalDistribution dist = new org.apache.commons.math.random.EmpiricalDistributionImpl();
        try {
            dist.load(((java.net.URL) (null)));
            junit.framework.Assert.fail("load((URL) null) expected NullPointerException");
        } catch (java.lang.NullPointerException e) {
            // expected
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    public void testLoadNullFile() throws java.lang.Exception {
        org.apache.commons.math.random.EmpiricalDistribution dist = new org.apache.commons.math.random.EmpiricalDistributionImpl();
        try {
            dist.load(((java.io.File) (null)));
            junit.framework.Assert.fail("load((File) null) expected NullPointerException");
        } catch (java.lang.NullPointerException e) {
            // expected
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    /**
     * MATH-298
     */
    public void testGetBinUpperBounds() {
        double[] testData = new double[]{ 0, 1, 1, 2, 3, 4, 4, 5, 6, 7, 8, 9, 10 };
        org.apache.commons.math.random.EmpiricalDistributionImpl dist = new org.apache.commons.math.random.EmpiricalDistributionImpl(5);
        dist.load(testData);
        double[] expectedBinUpperBounds = new double[]{ 2, 4, 6, 8, 10 };
        double[] expectedGeneratorUpperBounds = new double[]{ 4.0 / 13.0, 7.0 / 13.0, 9.0 / 13.0, 11.0 / 13.0, 1 };
        double tol = 1.0E-11;
        org.apache.commons.math.TestUtils.assertEquals(expectedBinUpperBounds, dist.getUpperBounds(), tol);
        org.apache.commons.math.TestUtils.assertEquals(expectedGeneratorUpperBounds, dist.getGeneratorUpperBounds(), tol);
    }

    private void verifySame(org.apache.commons.math.random.EmpiricalDistribution d1, org.apache.commons.math.random.EmpiricalDistribution d2) {
        junit.framework.Assert.assertEquals(d1.isLoaded(), d2.isLoaded());
        junit.framework.Assert.assertEquals(d1.getBinCount(), d2.getBinCount());
        junit.framework.Assert.assertEquals(d1.getSampleStats(), d2.getSampleStats());
        if (d1.isLoaded()) {
            for (int i = 0; i < (d1.getUpperBounds().length); i++) {
                junit.framework.Assert.assertEquals(d1.getUpperBounds()[i], d2.getUpperBounds()[i], 0);
            }
            junit.framework.Assert.assertEquals(d1.getBinStats(), d2.getBinStats());
        }
    }

    private void tstGen(double tolerance) throws java.lang.Exception {
        empiricalDistribution.load(url);
        org.apache.commons.math.stat.descriptive.SummaryStatistics stats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 1; i < 1000; i++) {
            stats.addValue(empiricalDistribution.getNextValue());
        }
        junit.framework.Assert.assertEquals("mean", stats.getMean(), 5.069831575018909, tolerance);
        junit.framework.Assert.assertEquals(
        "std dev", stats.getStandardDeviation(), 1.0173699343977738, tolerance);
    }

    private void tstDoubleGen(double tolerance) throws java.lang.Exception {
        empiricalDistribution2.load(dataArray);
        org.apache.commons.math.stat.descriptive.SummaryStatistics stats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 1; i < 1000; i++) {
            stats.addValue(empiricalDistribution2.getNextValue());
        }
        junit.framework.Assert.assertEquals("mean", stats.getMean(), 5.069831575018909, tolerance);
        junit.framework.Assert.assertEquals(
        "std dev", stats.getStandardDeviation(), 1.0173699343977738, tolerance);
    }
}