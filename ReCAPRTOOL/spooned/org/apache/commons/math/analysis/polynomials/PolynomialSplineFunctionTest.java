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
package org.apache.commons.math.analysis.polynomials;







/**
 * Tests the PolynomialSplineFunction implementation.
 *
 * @version $Revision$
 */
public class PolynomialSplineFunctionTest extends junit.framework.TestCase {

    /**
     * Error tolerance for tests
     */     protected double tolerance = 1.0E-12;
    /**
     * Quadratic polynomials used in tests:
     *
     * x^2 + x            [-1, 0)
     * x^2 + x + 2        [0, 1)
     * x^2 + x + 4        [1, 2)
     *
     * Defined so that evaluation using PolynomialSplineFunction evaluation
     * algorithm agrees at knot point boundaries.
     */
    protected org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[]{ 
    new org.apache.commons.math.analysis.polynomials.PolynomialFunction(new double[]{ 0.0, 1.0, 1.0 }), 
    new org.apache.commons.math.analysis.polynomials.PolynomialFunction(new double[]{ 2.0, 1.0, 1.0 }), 
    new org.apache.commons.math.analysis.polynomials.PolynomialFunction(new double[]{ 4.0, 1.0, 1.0 }) };


    /**
     * Knot points
     */     protected double[] knots = new double[]{ -1, 0, 1, 2 };
    /**
     * Derivative of test polynomials -- 2x + 1
     */     protected org.apache.commons.math.analysis.polynomials.PolynomialFunction dp = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(new double[]{ 1.0, 2.0 });


    public void testConstructor() {
        org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction spline = 
        new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(knots, polynomials);
        junit.framework.Assert.assertTrue(java.util.Arrays.equals(knots, spline.getKnots()));
        junit.framework.Assert.assertEquals(1.0, spline.getPolynomials()[0].getCoefficients()[2], 0);
        junit.framework.Assert.assertEquals(3, spline.getN());

        try {             // too few knots
            new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(new double[]{ 0 }, polynomials);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        try {             // too many knots
            new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(new double[]{ 0, 1, 2, 3, 4 }, polynomials);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        try {             // knots not increasing
            new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(new double[]{ 0, 1, 3, 2 }, polynomials);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

    public void testValues() throws java.lang.Exception {
        org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction spline = 
        new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(knots, polynomials);
        org.apache.commons.math.analysis.UnivariateRealFunction dSpline = spline.derivative();

        /**
         * interior points -- spline value at x should equal p(x - knot)
         * where knot is the largest knot point less than or equal to x and p
         * is the polynomial defined over the knot segment to which x belongs.
         */
        double x = -1;
        int index = 0;
        for (int i = 0; i < 10; i++) {
            x += 0.25;
            index = findKnot(knots, x);
            junit.framework.Assert.assertEquals(("spline function evaluation failed for x=" + x), 
            polynomials[index].value((x - (knots[index]))), spline.value(x), tolerance);
            junit.framework.Assert.assertEquals(("spline derivative evaluation failed for x=" + x), 
            dp.value((x - (knots[index]))), dSpline.value(x), tolerance);
        }

        // knot points -- centering should zero arguments
        for (int i = 0; i < 3; i++) {
            junit.framework.Assert.assertEquals(("spline function evaluation failed for knot=" + (knots[i])), 
            polynomials[i].value(0), spline.value(knots[i]), tolerance);
            junit.framework.Assert.assertEquals(("spline function evaluation failed for knot=" + (knots[i])), 
            dp.value(0), dSpline.value(knots[i]), tolerance);
        }

        try {             // outside of domain -- under min
            x = spline.value((-1.5));
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (org.apache.commons.math.FunctionEvaluationException ex) {
            // expected
        }

        try {             // outside of domain -- over max
            x = spline.value(2.5);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (org.apache.commons.math.FunctionEvaluationException ex) {
            // expected
        }
    }

    /**
     * Do linear search to find largest knot point less than or equal to x.
     *  Implementation does binary search.
     */
    protected int findKnot(double[] knots, double x) {
        if ((x < (knots[0])) || (x >= (knots[((knots.length) - 1)]))) {
            throw new java.lang.IllegalArgumentException("x is out of range");
        }
        for (int i = 0; i < (knots.length); i++) {
            if ((knots[i]) > x) {
                return i - 1;
            }
        }
        throw new java.lang.IllegalArgumentException("x is out of range");
    }
}