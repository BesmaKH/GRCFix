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
package org.apache.commons.math.stat.inference;



/**
 * Test cases for the ChiSquareTestImpl class.
 *
 * @version $Revision$ $Date$
 */

public class ChiSquareTestTest extends junit.framework.TestCase {

    protected org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest testStatistic = new org.apache.commons.math.stat.inference.ChiSquareTestImpl();

    public ChiSquareTestTest(java.lang.String name) {
        super(name);
    }

    public void testChiSquare() throws java.lang.Exception {

        // Target values computed using R version 1.8.1
        // Some assembly required ;-)
        // Use sum((obs - exp)^2/exp) for the chi-square statistic and
        // 1 - pchisq(sum((obs - exp)^2/exp), length(obs) - 1) for the p-value

        long[] observed = new long[]{ 10, 9, 11 };
        double[] expected = new double[]{ 10, 10, 10 };
        junit.framework.Assert.assertEquals("chi-square statistic", 0.2, testStatistic.chiSquare(expected, observed), 1.0E-11);
        junit.framework.Assert.assertEquals("chi-square p-value", 0.904837418036, testStatistic.chiSquareTest(expected, observed), 1.0E-10);

        long[] observed1 = new long[]{ 500, 623, 72, 70, 31 };
        double[] expected1 = new double[]{ 485, 541, 82, 61, 37 };
        junit.framework.Assert.assertEquals("chi-square test statistic", 9.023307936427388, testStatistic.chiSquare(expected1, observed1), 1.0E-10);
        junit.framework.Assert.assertEquals("chi-square p-value", 0.06051952647453607, testStatistic.chiSquareTest(expected1, observed1), 1.0E-9);
        junit.framework.Assert.assertTrue("chi-square test reject", testStatistic.chiSquareTest(expected1, observed1, 0.08));
        junit.framework.Assert.assertTrue("chi-square test accept", (!(testStatistic.chiSquareTest(expected1, observed1, 0.05))));

        try {
            testStatistic.chiSquareTest(expected1, observed1, 95);
            junit.framework.Assert.fail("alpha out of range, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        long[] tooShortObs = new long[]{ 0 };
        double[] tooShortEx = new double[]{ 1 };
        try {
            testStatistic.chiSquare(tooShortEx, tooShortObs);
            junit.framework.Assert.fail("arguments too short, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        // unmatched arrays
        long[] unMatchedObs = new long[]{ 0, 1, 2, 3 };
        double[] unMatchedEx = new double[]{ 1, 1, 2 };
        try {
            testStatistic.chiSquare(unMatchedEx, unMatchedObs);
            junit.framework.Assert.fail("arrays have different lengths, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        // 0 expected count
        expected[0] = 0;
        try {
            testStatistic.chiSquareTest(expected, observed, 0.01);
            junit.framework.Assert.fail("bad expected count, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        // negative observed count
        expected[0] = 1;
        observed[0] = -1;
        try {
            testStatistic.chiSquareTest(expected, observed, 0.01);
            junit.framework.Assert.fail("bad expected count, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

    }

    public void testChiSquareIndependence() throws java.lang.Exception {

        // Target values computed using R version 1.8.1

        long[][] counts = new long[][]{ new long[]{ 40, 22, 43 }, new long[]{ 91, 21, 28 }, new long[]{ 60, 10, 22 } };
        junit.framework.Assert.assertEquals("chi-square test statistic", 22.709027688, testStatistic.chiSquare(counts), 1.0E-9);
        junit.framework.Assert.assertEquals("chi-square p-value", 1.44751460134E-4, testStatistic.chiSquareTest(counts), 1.0E-9);
        junit.framework.Assert.assertTrue("chi-square test reject", testStatistic.chiSquareTest(counts, 2.0E-4));
        junit.framework.Assert.assertTrue("chi-square test accept", (!(testStatistic.chiSquareTest(counts, 1.0E-4))));

        long[][] counts2 = new long[][]{ new long[]{ 10, 15 }, new long[]{ 30, 40 }, new long[]{ 60, 90 } };
        junit.framework.Assert.assertEquals("chi-square test statistic", 0.168965517241, testStatistic.chiSquare(counts2), 1.0E-9);
        junit.framework.Assert.assertEquals("chi-square p-value", 0.918987499852, testStatistic.chiSquareTest(counts2), 1.0E-9);
        junit.framework.Assert.assertTrue("chi-square test accept", (!(testStatistic.chiSquareTest(counts2, 0.1))));

        // ragged input array
        long[][] counts3 = new long[][]{ new long[]{ 40, 22, 43 }, new long[]{ 91, 21, 28 }, new long[]{ 60, 10 } };
        try {
            testStatistic.chiSquare(counts3);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        // insufficient data
        long[][] counts4 = new long[][]{ new long[]{ 40, 22, 43 } };
        try {
            testStatistic.chiSquare(counts4);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        long[][] counts5 = new long[][]{ new long[]{ 40 }, new long[]{ 40 }, new long[]{ 30 }, new long[]{ 10 } };
        try {
            testStatistic.chiSquare(counts5);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        // negative counts
        long[][] counts6 = new long[][]{ new long[]{ 10, -2 }, new long[]{ 30, 40 }, new long[]{ 60, 90 } };
        try {
            testStatistic.chiSquare(counts6);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        // bad alpha
        try {
            testStatistic.chiSquareTest(counts, 0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

    public void testChiSquareLargeTestStatistic() throws java.lang.Exception {
        double[] exp = new double[]{ 
        3389119.5, 649136.6, 285745.4, 2.535736476E7, 1.129118978E7, 543628.0, 
        232921.0, 437665.75 };


        long[] obs = new long[]{ 
        2372383, 584222, 257170, 17750155, 7903832, 489265, 209628, 393899 };

        org.apache.commons.math.stat.inference.ChiSquareTestImpl csti = 
        new org.apache.commons.math.stat.inference.ChiSquareTestImpl();
        double cst = csti.chiSquareTest(exp, obs);
        junit.framework.Assert.assertEquals("chi-square p-value", 0.0, cst, 0.001);
        junit.framework.Assert.assertEquals("chi-square test statistic", 
        114875.90421929007, testStatistic.chiSquare(exp, obs), 1.0E-9);
    }

    /**
     * Contingency table containing zeros - PR # 32531
     */     public void testChiSquareZeroCount() throws java.lang.Exception {         // Target values computed using R version 1.8.1
        long[][] counts = new long[][]{ new long[]{ 40, 0, 4 }, new long[]{ 91, 1, 2 }, new long[]{ 60, 2, 0 } };
        junit.framework.Assert.assertEquals("chi-square test statistic", 9.67444662263, 
        testStatistic.chiSquare(counts), 1.0E-9);
        junit.framework.Assert.assertEquals("chi-square p-value", 0.0462835770603, 
        testStatistic.chiSquareTest(counts), 1.0E-9);
    }

    /**
     * Target values verified using DATAPLOT version 2006.3
     */     public void testChiSquareDataSetsComparisonEqualCounts() throws java.lang.Exception {
        long[] observed1 = new long[]{ 10, 12, 12, 10 };
        long[] observed2 = new long[]{ 5, 15, 14, 10 };
        junit.framework.Assert.assertEquals("chi-square p value", 0.541096, 
        testStatistic.chiSquareTestDataSetsComparison(
        observed1, observed2), 1.0E-6);
        junit.framework.Assert.assertEquals("chi-square test statistic", 2.153846, 
        testStatistic.chiSquareDataSetsComparison(
        observed1, observed2), 1.0E-6);
        junit.framework.Assert.assertFalse("chi-square test result", 
        testStatistic.chiSquareTestDataSetsComparison(
        observed1, observed2, 0.4));
    }

    /**
     * Target values verified using DATAPLOT version 2006.3
     */     public void testChiSquareDataSetsComparisonUnEqualCounts() throws java.lang.Exception {
        long[] observed1 = new long[]{ 10, 12, 12, 10, 15 };
        long[] observed2 = new long[]{ 15, 10, 10, 15, 5 };
        junit.framework.Assert.assertEquals("chi-square p value", 0.124115, 
        testStatistic.chiSquareTestDataSetsComparison(
        observed1, observed2), 1.0E-6);
        junit.framework.Assert.assertEquals("chi-square test statistic", 7.232189, 
        testStatistic.chiSquareDataSetsComparison(
        observed1, observed2), 1.0E-6);
        junit.framework.Assert.assertTrue("chi-square test result", 
        testStatistic.chiSquareTestDataSetsComparison(
        observed1, observed2, 0.13));
        junit.framework.Assert.assertFalse("chi-square test result", 
        testStatistic.chiSquareTestDataSetsComparison(
        observed1, observed2, 0.12));
    }

    public void testChiSquareDataSetsComparisonBadCounts() throws 
    java.lang.Exception {
        long[] observed1 = new long[]{ 10, -1, 12, 10, 15 };
        long[] observed2 = new long[]{ 15, 10, 10, 15, 5 };
        try {
            testStatistic.chiSquareTestDataSetsComparison(
            observed1, observed2);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - negative count");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        long[] observed3 = new long[]{ 10, 0, 12, 10, 15 };
        long[] observed4 = new long[]{ 15, 0, 10, 15, 5 };
        try {
            testStatistic.chiSquareTestDataSetsComparison(
            observed3, observed4);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - double 0's");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        long[] observed5 = new long[]{ 10, 10, 12, 10, 15 };
        long[] observed6 = new long[]{ 0, 0, 0, 0, 0 };
        try {
            testStatistic.chiSquareTestDataSetsComparison(
            observed5, observed6);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - vanishing counts");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }
}