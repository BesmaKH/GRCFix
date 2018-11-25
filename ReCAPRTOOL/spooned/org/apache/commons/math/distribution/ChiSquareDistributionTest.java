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
 * Test cases for ChiSquareDistribution.
 * Extends ContinuousDistributionAbstractTest.  See class javadoc for
 * ContinuousDistributionAbstractTest for details.
 *
 * @version $Revision$ $Date$
 */
public class ChiSquareDistributionTest extends org.apache.commons.math.distribution.ContinuousDistributionAbstractTest {

    /**
     * Constructor for ChiSquareDistributionTest.
     *
     * @param name
     * 		
     */     public ChiSquareDistributionTest(java.lang.String name) {         super(name);
    }

    // -------------- Implementations for abstract methods -----------------------

    /**
     * Creates the default continuous distribution instance to use in tests.
     */     @java.lang.Override     public org.apache.commons.math.distribution.ChiSquaredDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.ChiSquaredDistributionImpl(5.0);
    }

    /**
     * Creates the default cumulative probability distribution test input values
     */     @java.lang.Override     public double[] makeCumulativeTestPoints() {
        // quantiles computed using R version 2.9.2
        return new double[]{ 0.210212602629, 0.554298076728, 0.831211613487, 1.14547622606, 1.61030798696, 
        20.5150056524, 15.0862724694, 12.832501994, 11.0704976935, 9.23635689978 };
    }

    /**
     * Creates the default cumulative probability density test expected values
     */     @java.lang.Override     public double[] makeCumulativeTestValues() {
        return new double[]{ 0.001, 0.01, 0.025, 0.05, 0.1, 0.999, 0.99, 0.975, 0.95, 0.9 };
    }

    /**
     * Creates the default inverse cumulative probability test input values
     */     @java.lang.Override     public double[] makeInverseCumulativeTestPoints() {
        return new double[]{ 0, 0.001, 0.01, 0.025, 0.05, 0.1, 0.999, 
        0.99, 0.975, 0.95, 0.9, 1 };
    }

    /**
     * Creates the default inverse cumulative probability density test expected values
     */     @java.lang.Override     public double[] makeInverseCumulativeTestValues() {
        return new double[]{ 0, 0.210212602629, 0.554298076728, 0.831211613487, 1.14547622606, 1.61030798696, 
        20.5150056524, 15.0862724694, 12.832501994, 11.0704976935, 9.23635689978, 
        java.lang.Double.POSITIVE_INFINITY };
    }

    /**
     * Creates the default probability density test expected values
     */     @java.lang.Override     public double[] makeDensityTestValues() {
        return new double[]{ 0.0115379817652, 0.0415948507811, 0.0665060119842, 0.0919455953114, 0.121472591024, 
        4.33630076361E-4, 0.00412780610309, 0.00999340341045, 0.0193246438937, 0.0368460089216 };
    }

    // --------------------- Override tolerance  --------------
    @java.lang.Override
    protected void setUp() throws java.lang.Exception {
        super.setUp();
        setTolerance(1.0E-9);
    }

    // ---------------------------- Additional test cases -------------------------

    public void testSmallDf() throws java.lang.Exception {
        setDistribution(new org.apache.commons.math.distribution.ChiSquaredDistributionImpl(0.1));
        setTolerance(1.0E-4);
        // quantiles computed using R version 1.8.1 (linux version)
        setCumulativeTestPoints(new double[]{ 1.168926E-60, 1.168926E-40, 1.063132E-32, 
        1.144775E-26, 1.168926E-20, 5.472917, 2.175255, 1.13438, 
        0.5318646, 0.1526342 });
        setInverseCumulativeTestValues(getCumulativeTestPoints());
        setInverseCumulativeTestPoints(getCumulativeTestValues());
        verifyCumulativeProbabilities();
        verifyInverseCumulativeProbabilities();
    }

    public void testDfAccessors() {
        org.apache.commons.math.distribution.ChiSquaredDistribution distribution = ((org.apache.commons.math.distribution.ChiSquaredDistribution) (getDistribution()));
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

    public void testDensity() {
        double[] x = new double[]{ -0.1, 1.0E-6, 0.5, 1, 2, 5 };
        // R 2.5: print(dchisq(x, df=1), digits=10)
        checkDensity(1, x, new double[]{ 0.0, 398.94208093034, 0.43939128947, 0.24197072452, 0.10377687436, 0.01464498256 });
        // R 2.5: print(dchisq(x, df=0.1), digits=10)
        checkDensity(0.1, x, new double[]{ 0.0, 24864.53997, 0.07464238732, 0.03009077718, 0.009447299159, 8.827199396E-4 });
        // R 2.5: print(dchisq(x, df=2), digits=10)
        checkDensity(2, x, new double[]{ 0.0, 0.49999975, 0.38940039154, 0.30326532986, 0.18393972059, 0.04104249931 });
        // R 2.5: print(dchisq(x, df=10), digits=10)
        checkDensity(10, x, new double[]{ 0.0, 1.302082682E-27, 6.337896998E-5, 7.897534632E-4, 0.007664155024, 0.06680094289 });
    }

    private void checkDensity(double df, double[] x, double[] expected) {
        org.apache.commons.math.distribution.ChiSquaredDistribution d = new org.apache.commons.math.distribution.ChiSquaredDistributionImpl(df);
        for (int i = 0; i < (x.length); i++) {
            junit.framework.Assert.assertEquals(expected[i], d.density(x[i]), 1.0E-5);
        }
    }

}