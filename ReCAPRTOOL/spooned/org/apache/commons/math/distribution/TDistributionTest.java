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
 * Test cases for TDistribution.
 * Extends ContinuousDistributionAbstractTest.  See class javadoc for
 * ContinuousDistributionAbstractTest for details.
 *
 * @version $Revision$ $Date$
 */
public class TDistributionTest extends org.apache.commons.math.distribution.ContinuousDistributionAbstractTest {

    /**
     * Constructor for TDistributionTest.
     *
     * @param name
     * 		
     */     public TDistributionTest(java.lang.String name) {         super(name);
    }

    // -------------- Implementations for abstract methods -----------------------

    /**
     * Creates the default continuous distribution instance to use in tests.
     */     @java.lang.Override     public org.apache.commons.math.distribution.TDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.TDistributionImpl(5.0);
    }

    /**
     * Creates the default cumulative probability distribution test input values
     */     @java.lang.Override     public double[] makeCumulativeTestPoints() {
        // quantiles computed using R version 2.9.2
        return new double[]{ -5.89342953136, -3.36492999891, -2.57058183564, -2.01504837333, -1.47588404882, 
        5.89342953136, 3.36492999891, 2.57058183564, 2.01504837333, 1.47588404882 };
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
        return new double[]{ 7.56494565517E-4, 0.0109109752919, 0.0303377878006, 0.0637967988952, 0.128289492005, 
        7.56494565517E-4, 0.0109109752919, 0.0303377878006, 0.0637967988952, 0.128289492005 };
    }

    // --------------------- Override tolerance  --------------
    @java.lang.Override
    protected void setUp() throws java.lang.Exception {
        super.setUp();
        setTolerance(1.0E-9);
    }

    // ---------------------------- Additional test cases -------------------------
    /**
     *
     *
     * @see <a href="http://issues.apache.org/bugzilla/show_bug.cgi?id=27243">
     * Bug report that prompted this unit test.</a>
     */     public void testCumulativeProbabilityAgaintStackOverflow() throws java.lang.Exception {         org.apache.commons.math.distribution.TDistributionImpl td = new org.apache.commons.math.distribution.TDistributionImpl(5.0);
        td.cumulativeProbability(0.1);
        td.cumulativeProbability(0.01);
    }

    public void testSmallDf() throws java.lang.Exception {
        setDistribution(new org.apache.commons.math.distribution.TDistributionImpl(1.0));
        // quantiles computed using R version 2.9.2
        setCumulativeTestPoints(new double[]{ -318.308838986, -31.8205159538, -12.7062047362, 
        -6.31375151468, -3.07768353718, 318.308838986, 31.8205159538, 12.7062047362, 
        6.31375151468, 3.07768353718 });
        setDensityTestValues(new double[]{ 3.14158231817E-6, 3.14055924703E-4, 0.00195946145194, 
        0.00778959736375, 0.0303958893917, 3.14158231817E-6, 3.14055924703E-4, 
        0.00195946145194, 0.00778959736375, 0.0303958893917 });
        setInverseCumulativeTestValues(getCumulativeTestPoints());
        verifyCumulativeProbabilities();
        verifyInverseCumulativeProbabilities();
        verifyDensities();
    }

    public void testInverseCumulativeProbabilityExtremes() throws java.lang.Exception {
        setInverseCumulativeTestPoints(new double[]{ 0, 1 });
        setInverseCumulativeTestValues(
        new double[]{ java.lang.Double.NEGATIVE_INFINITY, java.lang.Double.POSITIVE_INFINITY });
        verifyInverseCumulativeProbabilities();
    }

    public void testDfAccessors() {
        org.apache.commons.math.distribution.TDistribution distribution = ((org.apache.commons.math.distribution.TDistribution) (getDistribution()));
        junit.framework.Assert.assertEquals(5.0, distribution.getDegreesOfFreedom(), java.lang.Double.MIN_VALUE);
        distribution.setDegreesOfFreedom(4.0);
        junit.framework.Assert.assertEquals(4.0, distribution.getDegreesOfFreedom(), java.lang.Double.MIN_VALUE);
        try {
            distribution.setDegreesOfFreedom(0.0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException for df = 0");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

}