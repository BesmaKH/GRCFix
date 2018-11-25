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







public class SemiVarianceTest extends junit.framework.TestCase {

    public void testInsufficientData() {
        double[] nothing = null;
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance();
        try {
            sv.evaluate(nothing);
            junit.framework.Assert.fail("null is not a valid data array.");
        } catch (java.lang.IllegalArgumentException iae) {
        }

        try {
            sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE);
            sv.evaluate(nothing);
            junit.framework.Assert.fail("null is not a valid data array.");
        } catch (java.lang.IllegalArgumentException iae) {
        }
        nothing = new double[]{  };
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sv.evaluate(nothing)));
    }

    public void testSingleDown() {
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance();
        double[] values = new double[]{ 50.0 };
        double singletest = sv.evaluate(values);
        junit.framework.Assert.assertEquals(0.0, singletest, 0);
    }

    public void testSingleUp() {
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance(org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE);
        double[] values = new double[]{ 50.0 };
        double singletest = sv.evaluate(values);
        junit.framework.Assert.assertEquals(0.0, singletest, 0);
    }

    public void testSample() {
        final double[] values = new double[]{ -2.0, 2.0, 4.0, -2.0, 22.0, 11.0, 3.0, 14.0, 5.0 };
        final int length = values.length;
        final double mean = org.apache.commons.math.stat.StatUtils.mean(values);// 6.333...
        final org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance();// Default bias correction is true
        final double downsideSemiVariance = sv.evaluate(values);// Downside is the default
        junit.framework.Assert.assertEquals(((org.apache.commons.math.TestUtils.sumSquareDev(new double[]{ -2.0, 2.0, 4.0, -2.0, 3.0, 5.0 }, mean)) / (length - 1)), 
        downsideSemiVariance, 1.0E-14);

        sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE);
        final double upsideSemiVariance = sv.evaluate(values);
        junit.framework.Assert.assertEquals(((org.apache.commons.math.TestUtils.sumSquareDev(new double[]{ 22.0, 11.0, 14.0 }, mean)) / (length - 1)), 
        upsideSemiVariance, 1.0E-14);

        // Verify that upper + lower semivariance against the mean sum to variance
        junit.framework.Assert.assertEquals(org.apache.commons.math.stat.StatUtils.variance(values), (downsideSemiVariance + upsideSemiVariance), 1.0E-11);
    }

    public void testPopulation() {
        double[] values = new double[]{ -2.0, 2.0, 4.0, -2.0, 22.0, 11.0, 3.0, 14.0, 5.0 };
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance(false);

        double singletest = sv.evaluate(values);
        junit.framework.Assert.assertEquals(19.556, singletest, 0.01);

        sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE);
        singletest = sv.evaluate(values);
        junit.framework.Assert.assertEquals(36.222, singletest, 0.01);
    }

    public void testNonMeanCutoffs() {
        double[] values = new double[]{ -2.0, 2.0, 4.0, -2.0, 22.0, 11.0, 3.0, 14.0, 5.0 };
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance(false);// Turn off bias correction - use df = length

        double singletest = sv.evaluate(values, 1.0, org.apache.commons.math.stat.descriptive.moment.SemiVariance.DOWNSIDE_VARIANCE, false, 0, values.length);
        junit.framework.Assert.assertEquals(((org.apache.commons.math.TestUtils.sumSquareDev(new double[]{ -2.0, -2.0 }, 1.0)) / (values.length)), 
        singletest, 0.01);

        singletest = sv.evaluate(values, 3.0, org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE, false, 0, values.length);
        junit.framework.Assert.assertEquals(((org.apache.commons.math.TestUtils.sumSquareDev(new double[]{ 4.0, 22.0, 11.0, 14.0, 5.0 }, 3.0)) / (values.length)), singletest, 
        0.01);
    }

    /**
     * Check that the lower + upper semivariance against the mean sum to the
     * variance.
     */
    public void testVarianceDecompMeanCutoff() {
        double[] values = new double[]{ -2.0, 2.0, 4.0, -2.0, 22.0, 11.0, 3.0, 14.0, 5.0 };
        double variance = org.apache.commons.math.stat.StatUtils.variance(values);
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance(true);// Bias corrected
        sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.DOWNSIDE_VARIANCE);
        final double lower = sv.evaluate(values);
        sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE);
        final double upper = sv.evaluate(values);
        junit.framework.Assert.assertEquals(variance, (lower + upper), 1.0E-11);
    }

    /**
     * Check that upper and lower semivariances against a cutoff sum to the sum
     * of squared deviations of the full set of values against the cutoff
     * divided by df = length - 1 (assuming bias-corrected).
     */
    public void testVarianceDecompNonMeanCutoff() {
        double[] values = new double[]{ -2.0, 2.0, 4.0, -2.0, 22.0, 11.0, 3.0, 14.0, 5.0 };
        double target = 0;
        double totalSumOfSquares = org.apache.commons.math.TestUtils.sumSquareDev(values, target);
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance(true);// Bias corrected
        sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.DOWNSIDE_VARIANCE);
        double lower = sv.evaluate(values, target);
        sv.setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE);
        double upper = sv.evaluate(values, target);
        junit.framework.Assert.assertEquals((totalSumOfSquares / ((values.length) - 1)), (lower + upper), 1.0E-11);
    }

    public void testNoVariance() {
        final double[] values = new double[]{ 100.0, 100.0, 100.0, 100.0 };
        org.apache.commons.math.stat.descriptive.moment.SemiVariance sv = new org.apache.commons.math.stat.descriptive.moment.SemiVariance();
        junit.framework.Assert.assertEquals(0, sv.evaluate(values), 1.0E-11);
        junit.framework.Assert.assertEquals(0, sv.evaluate(values, 100.0), 1.0E-11);
        junit.framework.Assert.assertEquals(0, sv.evaluate(values, 100.0, org.apache.commons.math.stat.descriptive.moment.SemiVariance.UPSIDE_VARIANCE, false, 0, values.length), 1.0E-11);
    }
}