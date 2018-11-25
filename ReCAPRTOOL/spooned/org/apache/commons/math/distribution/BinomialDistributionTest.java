/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.commons.math.distribution;

/**
 * Test cases for BinomialDistribution. Extends IntegerDistributionAbstractTest.
 * See class javadoc for IntegerDistributionAbstractTest for details.
 *
 * @version $Revision$ $Date: 2009-09-05 12:36:48 -0500 (Sat, 05 Sep
 * 2009) $
 */
public class BinomialDistributionTest extends org.apache.commons.math.distribution.IntegerDistributionAbstractTest {

    /**
     * Constructor for BinomialDistributionTest.
     *
     * @param name
     * 		
     */     public BinomialDistributionTest(java.lang.String name) {
        super(name);
    }

    // -------------- Implementations for abstract methods
    // -----------------------

    /**
     * Creates the default discrete distribution instance to use in tests.
     */     @java.lang.Override     public org.apache.commons.math.distribution.IntegerDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.BinomialDistributionImpl(10, 0.7);
    }

    /**
     * Creates the default probability density test input values
     */     @java.lang.Override     public int[] makeDensityTestPoints() {
        return new int[]{ -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
    }

    /**
     * Creates the default probability density test expected values
     */     @java.lang.Override     public double[] makeDensityTestValues() {
        return new double[]{ 0.0, 5.9049E-6, 1.37781E-4, 0.0014467, 
        0.00900169, 0.0367569, 0.102919, 0.200121, 0.266828, 
        0.233474, 0.121061, 0.0282475, 0.0 };
    }

    /**
     * Creates the default cumulative probability density test input values
     */     @java.lang.Override     public int[] makeCumulativeTestPoints() {
        return makeDensityTestPoints();
    }

    /**
     * Creates the default cumulative probability density test expected values
     */     @java.lang.Override     public double[] makeCumulativeTestValues() {
        return new double[]{ 0.0, 0.0, 1.0E-4, 0.0016, 0.0106, 0.0473, 
        0.1503, 0.3504, 0.6172, 0.8507, 0.9718, 1.0, 1.0 };
    }

    /**
     * Creates the default inverse cumulative probability test input values
     */     @java.lang.Override     public double[] makeInverseCumulativeTestPoints() {
        return new double[]{ 0, 0.001, 0.01, 0.025, 0.05, 0.1, 
        0.999, 0.99, 0.975, 0.95, 0.9, 1 };
    }

    /**
     * Creates the default inverse cumulative probability density test expected
     * values
     */
    @java.lang.Override
    public int[] makeInverseCumulativeTestValues() {
        return new int[]{ -1, 1, 2, 3, 4, 4, 9, 9, 9, 8, 8, java.lang.Integer.MAX_VALUE };
    }

    // ----------------- Additional test cases ---------------------------------

    /**
     * Test degenerate case p = 0
     */     public void testDegenerate0() throws java.lang.Exception {         setDistribution(new org.apache.commons.math.distribution.BinomialDistributionImpl(5, 0.0));
        setCumulativeTestPoints(new int[]{ -1, 0, 1, 5, 10 });
        setCumulativeTestValues(new double[]{ 0.0, 1.0, 1.0, 1.0, 1.0 });
        setDensityTestPoints(new int[]{ -1, 0, 1, 10, 11 });
        setDensityTestValues(new double[]{ 0.0, 1.0, 0.0, 0.0, 0.0 });
        setInverseCumulativeTestPoints(new double[]{ 0.1, 0.5 });
        setInverseCumulativeTestValues(new int[]{ -1, -1 });
        verifyDensities();
        verifyCumulativeProbabilities();
        verifyInverseCumulativeProbabilities();
    }

    /**
     * Test degenerate case p = 1
     */     public void testDegenerate1() throws java.lang.Exception {         setDistribution(new org.apache.commons.math.distribution.BinomialDistributionImpl(5, 1.0));
        setCumulativeTestPoints(new int[]{ -1, 0, 1, 2, 5, 10 });
        setCumulativeTestValues(new double[]{ 0.0, 0.0, 0.0, 0.0, 1.0, 1.0 });
        setDensityTestPoints(new int[]{ -1, 0, 1, 2, 5, 10 });
        setDensityTestValues(new double[]{ 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 });
        setInverseCumulativeTestPoints(new double[]{ 0.1, 0.5 });
        setInverseCumulativeTestValues(new int[]{ 4, 4 });
        verifyDensities();
        verifyCumulativeProbabilities();
        verifyInverseCumulativeProbabilities();
    }

}