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
 * Testcase for Lagrange form of polynomial function.
 * <p>
 * We use n+1 points to interpolate a polynomial of degree n. This should
 * give us the exact same polynomial as result. Thus we can use a very
 * small tolerance to account only for round-off errors.
 *
 * @version $Revision$ $Date$
 */
public final class PolynomialFunctionLagrangeFormTest extends junit.framework.TestCase {

    /**
     * Test of polynomial for the linear function.
     */
    public void testLinearFunction() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm p;
        double[] c;         double z;         double expected;         double result;         double tolerance = 1.0E-12;

        // p(x) = 1.5x - 4
        double[] x = new double[]{ 0.0, 3.0 };
        double[] y = new double[]{ -4.0, 0.5 };
        p = new org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm(x, y);

        z = 2.0;         expected = -1.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = 4.5;         expected = 2.75;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = 6.0;         expected = 5.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        junit.framework.Assert.assertEquals(1, p.degree());

        c = p.getCoefficients();
        junit.framework.Assert.assertEquals(2, c.length);
        junit.framework.Assert.assertEquals((-4.0), c[0], tolerance);
        junit.framework.Assert.assertEquals(1.5, c[1], tolerance);
    }

    /**
     * Test of polynomial for the quadratic function.
     */
    public void testQuadraticFunction() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm p;
        double[] c;         double z;         double expected;         double result;         double tolerance = 1.0E-12;

        // p(x) = 2x^2 + 5x - 3 = (2x - 1)(x + 3)
        double[] x = new double[]{ 0.0, -1.0, 0.5 };
        double[] y = new double[]{ -3.0, -6.0, 0.0 };
        p = new org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm(x, y);

        z = 1.0;         expected = 4.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = 2.5;         expected = 22.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = -2.0;         expected = -5.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        junit.framework.Assert.assertEquals(2, p.degree());

        c = p.getCoefficients();
        junit.framework.Assert.assertEquals(3, c.length);
        junit.framework.Assert.assertEquals((-3.0), c[0], tolerance);
        junit.framework.Assert.assertEquals(5.0, c[1], tolerance);
        junit.framework.Assert.assertEquals(2.0, c[2], tolerance);
    }

    /**
     * Test of polynomial for the quintic function.
     */
    public void testQuinticFunction() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm p;
        double[] c;         double z;         double expected;         double result;         double tolerance = 1.0E-12;

        // p(x) = x^5 - x^4 - 7x^3 + x^2 + 6x = x(x^2 - 1)(x + 2)(x - 3)
        double[] x = new double[]{ 1.0, -1.0, 2.0, 3.0, -3.0, 0.5 };
        double[] y = new double[]{ 0.0, 0.0, -24.0, 0.0, -144.0, 2.34375 };
        p = new org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm(x, y);

        z = 0.0;         expected = 0.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = -2.0;         expected = 0.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = 4.0;         expected = 360.0;         result = p.value(z);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        junit.framework.Assert.assertEquals(5, p.degree());

        c = p.getCoefficients();
        junit.framework.Assert.assertEquals(6, c.length);
        junit.framework.Assert.assertEquals(0.0, c[0], tolerance);
        junit.framework.Assert.assertEquals(6.0, c[1], tolerance);
        junit.framework.Assert.assertEquals(1.0, c[2], tolerance);
        junit.framework.Assert.assertEquals((-7.0), c[3], tolerance);
        junit.framework.Assert.assertEquals((-1.0), c[4], tolerance);
        junit.framework.Assert.assertEquals(1.0, c[5], tolerance);
    }

    /**
     * Test of parameters for the polynomial.
     */
    public void testParameters() throws java.lang.Exception {

        try {
            // bad input array length
            double[] x = new double[]{ 1.0 };
            double[] y = new double[]{ 2.0 };
            new org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm(x, y);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - bad input array length");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {
            // mismatch input arrays
            double[] x = new double[]{ 1.0, 2.0, 3.0, 4.0 };
            double[] y = new double[]{ 0.0, -4.0, -24.0 };
            new org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm(x, y);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - mismatch input arrays");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }
}