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
 * Test cases for the ValueServer class.
 *
 * @version $Revision$ $Date$
 */

public final class ValueServerTest extends org.apache.commons.math.RetryTestCase {

    private org.apache.commons.math.random.ValueServer vs = new org.apache.commons.math.random.ValueServer();

    public ValueServerTest(java.lang.String name) {
        super(name);
    }

    @java.lang.Override
    public void setUp() {
        vs.setMode(org.apache.commons.math.random.ValueServer.DIGEST_MODE);
        try {
            java.net.URL url = getClass().getResource("testData.txt");
            vs.setValuesFileURL(url);
        } catch (java.lang.Exception ex) {
            junit.framework.Assert.fail("malformed test URL");
        }
    }

    /**
     * Generate 1000 random values and make sure they look OK.<br>
     * Note that there is a non-zero (but very small) probability that
     * these tests will fail even if the code is working as designed.
     */
    public void testNextDigest() throws java.lang.Exception {
        double next = 0.0;
        double tolerance = 0.1;
        vs.computeDistribution();
        junit.framework.Assert.assertTrue("empirical distribution property", 
        ((vs.getEmpiricalDistribution()) != null));
        org.apache.commons.math.stat.descriptive.SummaryStatistics stats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 1; i < 1000; i++) {
            next = vs.getNext();
            stats.addValue(next);
        }
        junit.framework.Assert.assertEquals("mean", 5.069831575018909, stats.getMean(), tolerance);
        junit.framework.Assert.assertEquals(
        "std dev", 1.0173699343977738, stats.getStandardDeviation(), 
        tolerance);

        vs.computeDistribution(500);
        stats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
        for (int i = 1; i < 1000; i++) {
            next = vs.getNext();
            stats.addValue(next);
        }
        junit.framework.Assert.assertEquals("mean", 5.069831575018909, stats.getMean(), tolerance);
        junit.framework.Assert.assertEquals(
        "std dev", 1.0173699343977738, stats.getStandardDeviation(), 
        tolerance);

    }

    /**
     * Make sure exception thrown if digest getNext is attempted
     * before loading empiricalDistribution.
     */
    public void testNextDigestFail() throws java.lang.Exception {
        try {
            vs.getNext();
            junit.framework.Assert.fail("Expecting IllegalStateException");
        } catch (java.lang.IllegalStateException ex) {}
    }

    public void testEmptyReplayFile() {
        try {
            java.net.URL url = getClass().getResource("emptyFile.txt");
            vs.setMode(org.apache.commons.math.random.ValueServer.REPLAY_MODE);
            vs.setValuesFileURL(url);
            vs.getNext();
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (java.io.EOFException eof) {
            // expected behavior
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    public void testEmptyDigestFile() {
        try {
            java.net.URL url = getClass().getResource("emptyFile.txt");
            vs.setMode(org.apache.commons.math.random.ValueServer.DIGEST_MODE);
            vs.setValuesFileURL(url);
            vs.computeDistribution();
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (java.io.EOFException eof) {
            // expected behavior
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    /**
     * Test ValueServer REPLAY_MODE using values in testData file.<br>
     * Check that the values 1,2,1001,1002 match data file values 1 and 2.
     * the sample data file.
     */
    public void testReplay() throws java.lang.Exception {
        double firstDataValue = 4.038625496201205;
        double secondDataValue = 3.6485326248346936;
        double tolerance = 1.0E-14;
        double compareValue = 0.0;
        vs.setMode(org.apache.commons.math.random.ValueServer.REPLAY_MODE);
        vs.resetReplayFile();
        compareValue = vs.getNext();
        junit.framework.Assert.assertEquals(compareValue, firstDataValue, tolerance);
        compareValue = vs.getNext();
        junit.framework.Assert.assertEquals(compareValue, secondDataValue, tolerance);
        for (int i = 3; i < 1001; i++) {
            compareValue = vs.getNext();
        }
        compareValue = vs.getNext();
        junit.framework.Assert.assertEquals(compareValue, firstDataValue, tolerance);
        compareValue = vs.getNext();
        junit.framework.Assert.assertEquals(compareValue, secondDataValue, tolerance);
        vs.closeReplayFile();
        // make sure no NPE
        vs.closeReplayFile();
    }

    /**
     * Test other ValueServer modes
     */
    public void testModes() throws java.lang.Exception {
        vs.setMode(org.apache.commons.math.random.ValueServer.CONSTANT_MODE);
        vs.setMu(0);
        junit.framework.Assert.assertEquals("constant mode test", vs.getMu(), vs.getNext(), java.lang.Double.MIN_VALUE);
        vs.setMode(org.apache.commons.math.random.ValueServer.UNIFORM_MODE);
        vs.setMu(2);
        double val = vs.getNext();
        junit.framework.Assert.assertTrue(((val > 0) && (val < 4)));
        vs.setSigma(1);
        vs.setMode(org.apache.commons.math.random.ValueServer.GAUSSIAN_MODE);
        val = vs.getNext();
        junit.framework.Assert.assertTrue("gaussian value close enough to mean", 
        (val < ((vs.getMu()) + (100 * (vs.getSigma())))));
        vs.setMode(org.apache.commons.math.random.ValueServer.EXPONENTIAL_MODE);
        val = vs.getNext();
        junit.framework.Assert.assertTrue((val > 0));
        try {
            vs.setMode(1000);
            vs.getNext();
            junit.framework.Assert.fail("bad mode, expecting IllegalStateException");
        } catch (java.lang.IllegalStateException ex) {
            // ignored
        }
    }

    /**
     * Test fill
     */
    public void testFill() throws java.lang.Exception {
        vs.setMode(org.apache.commons.math.random.ValueServer.CONSTANT_MODE);
        vs.setMu(2);
        double[] val = new double[5];
        vs.fill(val);
        for (int i = 0; i < 5; i++) {
            junit.framework.Assert.assertEquals("fill test in place", 2, val[i], java.lang.Double.MIN_VALUE);
        }
        double[] v2 = vs.fill(3);
        for (int i = 0; i < 3; i++) {
            junit.framework.Assert.assertEquals("fill test in place", 2, v2[i], java.lang.Double.MIN_VALUE);
        }
    }

    /**
     * Test getters to make Clover happy
     */
    public void testProperties() throws java.lang.Exception {
        vs.setMode(org.apache.commons.math.random.ValueServer.CONSTANT_MODE);
        junit.framework.Assert.assertEquals("mode test", org.apache.commons.math.random.ValueServer.CONSTANT_MODE, vs.getMode());
        vs.setValuesFileURL("http://www.apache.org");
        java.net.URL url = vs.getValuesFileURL();
        junit.framework.Assert.assertEquals("valuesFileURL test", "http://www.apache.org", url.toString());
    }

}