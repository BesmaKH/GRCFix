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
 * Test cases for {@link ZipfDistribution}.
 * Extends IntegerDistributionAbstractTest.  See class javadoc for
 * IntegerDistributionAbstractTest for details.
 *
 * @version $Revision$ $Date$
 */
public class ZipfDistributionTest extends org.apache.commons.math.distribution.IntegerDistributionAbstractTest {
    public ZipfDistributionTest(java.lang.String name) {
        super(name);
    }

    // -------------- Implementations for abstract methods -----------------------

    /**
     * Creates the default discrete distribution instance to use in tests.
     */     @java.lang.Override     public org.apache.commons.math.distribution.IntegerDistribution makeDistribution() {
        return new org.apache.commons.math.distribution.ZipfDistributionImpl(10, 1);
    }

    /**
     * Creates the default probability density test input values
     */     @java.lang.Override     public int[] makeDensityTestPoints() {
        return new int[]{ -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
    }

    /**
     * Creates the default probability density test expected values
     */     @java.lang.Override     public double[] makeDensityTestValues() {
        return new double[]{ 0.0, 0.0, 0.3414, 0.1707, 0.1138, 0.0854, 0.0683, 
        0.0569, 0.0488, 0.0427, 0.0379, 0.0341, 0.0 };
    }

    /**
     * Creates the default cumulative probability density test input values
     */     @java.lang.Override     public int[] makeCumulativeTestPoints() {
        return makeDensityTestPoints();
    }

    /**
     * Creates the default cumulative probability density test expected values
     */     @java.lang.Override     public double[] makeCumulativeTestValues() {
        return new double[]{ 0.0, 0.0, 0.3414, 0.5121, 0.6259, 0.7113, 
        0.7796, 0.8365, 0.8852, 0.9279, 0.9659, 1.0, 1.0 };
    }

    /**
     * Creates the default inverse cumulative probability test input values
     */     @java.lang.Override     public double[] makeInverseCumulativeTestPoints() {
        return new double[]{ 0, 0.001, 0.01, 0.025, 0.05, 0.3414, 0.3415, 0.999, 
        0.99, 0.975, 0.95, 0.9, 1 };
    }

    /**
     * Creates the default inverse cumulative probability density test expected values
     */     @java.lang.Override     public int[] makeInverseCumulativeTestValues() {
        return new int[]{ 0, 0, 0, 0, 0, 0, 1, 9, 9, 9, 8, 7, 10 };
    }
}