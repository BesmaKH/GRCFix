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
 * Test cases for CauchyDistribution.
 * Extends ContinuousDistributionAbstractTest.  See class javadoc for
 * ContinuousDistributionAbstractTest for details.
 *
 * @version $Revision$ $Date$
 */
public class CauchyDistributionTest extends org.apache.commons.math.distribution.ContinuousDistributionAbstractTest {

    /**
     * Constructor for CauchyDistributionTest.
     *
     * @param arg0
     * 		
     */     public CauchyDistributionTest(java.lang.String arg0) {         super(arg0);
    }

    // --------------------- Override tolerance  --------------
    protected double defaultTolerance = org.apache.commons.math.distribution.NormalDistributionImpl.DEFAULT_INVERSE_ABSOLUTE_ACCURACY;
    @java.lang.Override
    protected void setUp() throws java.lang.Exception {
        super.setUp();
        setTolerance(defaultTolerance);
    }

    // -------------- Implementations for abstract methods -----------------------

    /**
     * Creates the default continuous distribution instance to use in tests.
     */     @java.lang.Override     public org.apache.commons.math.distribution.CauchyDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.CauchyDistributionImpl(1.2, 2.1);
    }

    /**
     * Creates the default cumulative probability distribution test input values
     */     @java.lang.Override     public double[] makeCumulativeTestPoints() {
        // quantiles computed using R 2.9.2
        return new double[]{ -667.24856187, -65.6230835029, -25.483029946, -12.0588781808, 
        -5.26313542807, 669.64856187, 68.0230835029, 27.883029946, 14.4588781808, 7.66313542807 };
    }

    /**
     * Creates the default cumulative probability density test expected values
     */     @java.lang.Override     public double[] makeCumulativeTestValues() {
        return new double[]{ 0.001, 0.01, 0.025, 0.05, 0.1, 0.999, 
        0.99, 0.975, 0.95, 0.9 };
    }

    /**
     * Creates the default probability density test expected values
     */     @java.lang.Override     public double[] makeDensityTestValues() {
        return new double[]{ 1.49599158008E-6, 1.49550440335E-4, 9.33076881878E-4, 0.00370933207799, 0.0144742330437, 
        1.49599158008E-6, 1.49550440335E-4, 9.33076881878E-4, 0.00370933207799, 0.0144742330437 };
    }

    // ---------------------------- Additional test cases -------------------------

    public void testInverseCumulativeProbabilityExtremes() throws java.lang.Exception {
        setInverseCumulativeTestPoints(new double[]{ 0.0, 1.0 });
        setInverseCumulativeTestValues(
        new double[]{ java.lang.Double.NEGATIVE_INFINITY, java.lang.Double.POSITIVE_INFINITY });
        verifyInverseCumulativeProbabilities();
    }

    public void testMedian() {
        org.apache.commons.math.distribution.CauchyDistribution distribution = ((org.apache.commons.math.distribution.CauchyDistribution) (getDistribution()));
        double expected = java.lang.Math.random();
        distribution.setMedian(expected);
        junit.framework.Assert.assertEquals(expected, distribution.getMedian(), 0.0);
    }

    public void testScale() {
        org.apache.commons.math.distribution.CauchyDistribution distribution = ((org.apache.commons.math.distribution.CauchyDistribution) (getDistribution()));
        double expected = java.lang.Math.random();
        distribution.setScale(expected);
        junit.framework.Assert.assertEquals(expected, distribution.getScale(), 0.0);
    }

    public void testSetScale() {
        org.apache.commons.math.distribution.CauchyDistribution distribution = ((org.apache.commons.math.distribution.CauchyDistribution) (getDistribution()));
        try {
            distribution.setScale(0.0);
            junit.framework.Assert.fail("Can not have 0.0 scale.");
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }

        try {
            distribution.setScale((-1.0));
            junit.framework.Assert.fail("Can not have negative scale.");
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
    }
}