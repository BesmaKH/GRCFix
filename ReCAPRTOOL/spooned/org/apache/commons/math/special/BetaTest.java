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
package org.apache.commons.math.special;






/**
 *
 *
 * @version $Revision$ $Date$
 */ public class BetaTest extends junit.framework.TestCase {     /**
     * Constructor for BetaTest.
     *
     * @param name
     * 		
     */     public BetaTest(java.lang.String name) {         super(name);
    }

    private void testRegularizedBeta(double expected, double x, double a, 
    double b) 
    {
        try {
            double actual = org.apache.commons.math.special.Beta.regularizedBeta(x, a, b);
            org.apache.commons.math.TestUtils.assertEquals(expected, actual, 1.0E-14);
        } catch (org.apache.commons.math.MathException ex) {
            junit.framework.Assert.fail(ex.getMessage());
        }
    }

    private void testLogBeta(double expected, double a, double b) {
        double actual = org.apache.commons.math.special.Beta.logBeta(a, b);
        org.apache.commons.math.TestUtils.assertEquals(expected, actual, 1.0E-14);
    }

    public void testRegularizedBetaNanPositivePositive() {
        testRegularizedBeta(java.lang.Double.NaN, java.lang.Double.NaN, 1.0, 1.0);
    }

    public void testRegularizedBetaPositiveNanPositive() {
        testRegularizedBeta(java.lang.Double.NaN, 0.5, java.lang.Double.NaN, 1.0);
    }

    public void testRegularizedBetaPositivePositiveNan() {
        testRegularizedBeta(java.lang.Double.NaN, 0.5, 1.0, java.lang.Double.NaN);
    }

    public void testRegularizedBetaNegativePositivePositive() {
        testRegularizedBeta(java.lang.Double.NaN, (-0.5), 1.0, 2.0);
    }

    public void testRegularizedBetaPositiveNegativePositive() {
        testRegularizedBeta(java.lang.Double.NaN, 0.5, (-1.0), 2.0);
    }

    public void testRegularizedBetaPositivePositiveNegative() {
        testRegularizedBeta(java.lang.Double.NaN, 0.5, 1.0, (-2.0));
    }

    public void testRegularizedBetaZeroPositivePositive() {
        testRegularizedBeta(0.0, 0.0, 1.0, 2.0);
    }

    public void testRegularizedBetaPositiveZeroPositive() {
        testRegularizedBeta(java.lang.Double.NaN, 0.5, 0.0, 2.0);
    }

    public void testRegularizedBetaPositivePositiveZero() {
        testRegularizedBeta(java.lang.Double.NaN, 0.5, 1.0, 0.0);
    }

    public void testRegularizedBetaPositivePositivePositive() {
        testRegularizedBeta(0.75, 0.5, 1.0, 2.0);
    }

    public void testLogBetaNanPositive() {
        testLogBeta(java.lang.Double.NaN, java.lang.Double.NaN, 2.0);
    }

    public void testLogBetaPositiveNan() {
        testLogBeta(java.lang.Double.NaN, 1.0, java.lang.Double.NaN);
    }

    public void testLogBetaNegativePositive() {
        testLogBeta(java.lang.Double.NaN, (-1.0), 2.0);
    }

    public void testLogBetaPositiveNegative() {
        testLogBeta(java.lang.Double.NaN, 1.0, (-2.0));
    }

    public void testLogBetaZeroPositive() {
        testLogBeta(java.lang.Double.NaN, 0.0, 2.0);
    }

    public void testLogBetaPositiveZero() {
        testLogBeta(java.lang.Double.NaN, 1.0, 0.0);
    }

    public void testLogBetaPositivePositive() {
        testLogBeta((-0.693147180559945), 1.0, 2.0);
    }
}