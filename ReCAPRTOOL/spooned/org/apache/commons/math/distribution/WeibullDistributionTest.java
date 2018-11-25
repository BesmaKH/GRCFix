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
 * Test cases for WeibullDistribution.
 * Extends ContinuousDistributionAbstractTest.  See class javadoc for
 * ContinuousDistributionAbstractTest for details.
 *
 * @version $Revision$ $Date$
 */
public class WeibullDistributionTest extends org.apache.commons.math.distribution.ContinuousDistributionAbstractTest {

    /**
     * Constructor for CauchyDistributionTest.
     *
     * @param arg0
     * 		
     */     public WeibullDistributionTest(java.lang.String arg0) {         super(arg0);
    }

    // -------------- Implementations for abstract methods -----------------------

    /**
     * Creates the default continuous distribution instance to use in tests.
     */     @java.lang.Override     public org.apache.commons.math.distribution.WeibullDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.WeibullDistributionImpl(1.2, 2.1);
    }

    /**
     * Creates the default cumulative probability distribution test input values
     */     @java.lang.Override     public double[] makeCumulativeTestPoints() {
        // quantiles computed using R version 2.9.2
        return new double[]{ 0.00664355180993, 0.0454328283309, 0.0981162737374, 0.176713524579, 0.321946865392, 
        10.5115496887, 7.4976304671, 6.23205600701, 5.23968436955, 4.2079028257 };
    }

    /**
     * Creates the default cumulative probability density test expected values
     */     @java.lang.Override     public double[] makeCumulativeTestValues() {
        return new double[]{ 0.001, 0.01, 0.025, 0.05, 0.1, 0.999, 0.99, 0.975, 0.95, 0.9 };
    }

    /**
     * Creates the default probability density test expected values
     */     @java.lang.Override     public double[] makeDensityTestValues() {
        return new double[]{ 0.180535929306, 0.262801138133, 0.301905425199, 0.330899152971, 
        0.353441418887, 7.88590320203E-4, 0.00737060094841, 0.0177576041516, 0.0343043442574, 0.065664589369 };
    }

    // ---------------------------- Additional test cases -------------------------

    public void testInverseCumulativeProbabilityExtremes() throws java.lang.Exception {
        setInverseCumulativeTestPoints(new double[]{ 0.0, 1.0 });
        setInverseCumulativeTestValues(
        new double[]{ 0.0, java.lang.Double.POSITIVE_INFINITY });
        verifyInverseCumulativeProbabilities();
    }

    public void testAlpha() {
        org.apache.commons.math.distribution.WeibullDistribution distribution = ((org.apache.commons.math.distribution.WeibullDistribution) (getDistribution()));
        double expected = java.lang.Math.random();
        distribution.setShape(expected);
        junit.framework.Assert.assertEquals(expected, distribution.getShape(), 0.0);
    }

    public void testBeta() {
        org.apache.commons.math.distribution.WeibullDistribution distribution = ((org.apache.commons.math.distribution.WeibullDistribution) (getDistribution()));
        double expected = java.lang.Math.random();
        distribution.setScale(expected);
        junit.framework.Assert.assertEquals(expected, distribution.getScale(), 0.0);
    }

    public void testSetAlpha() {
        org.apache.commons.math.distribution.WeibullDistribution distribution = ((org.apache.commons.math.distribution.WeibullDistribution) (getDistribution()));
        try {
            distribution.setShape(0.0);
            junit.framework.Assert.fail("Can not have 0.0 alpha.");
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }

        try {
            distribution.setShape((-1.0));
            junit.framework.Assert.fail("Can not have negative alpha.");
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
    }

    public void testSetBeta() {
        org.apache.commons.math.distribution.WeibullDistribution distribution = ((org.apache.commons.math.distribution.WeibullDistribution) (getDistribution()));
        try {
            distribution.setScale(0.0);
            junit.framework.Assert.fail("Can not have 0.0 beta.");
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }

        try {
            distribution.setScale((-1.0));
            junit.framework.Assert.fail("Can not have negative beta.");
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
    }
}