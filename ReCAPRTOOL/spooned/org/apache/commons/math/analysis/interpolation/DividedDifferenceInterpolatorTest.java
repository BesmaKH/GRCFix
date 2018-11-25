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
package org.apache.commons.math.analysis.interpolation;








/**
 * Testcase for Divided Difference interpolator.
 * <p>
 * The error of polynomial interpolation is
 *     f(z) - p(z) = f^(n)(zeta) * (z-x[0])(z-x[1])...(z-x[n-1]) / n!
 * where f^(n) is the n-th derivative of the approximated function and
 * zeta is some point in the interval determined by x[] and z.
 * <p>
 * Since zeta is unknown, f^(n)(zeta) cannot be calculated. But we can bound
 * it and use the absolute value upper bound for estimates. For reference,
 * see <b>Introduction to Numerical Analysis</b>, ISBN 038795452X, chapter 2.
 *
 * @version $Revision$ $Date$
 */
public final class DividedDifferenceInterpolatorTest extends junit.framework.TestCase {

    /**
     * Test of interpolator for the sine function.
     * <p>
     * |sin^(n)(zeta)| <= 1.0, zeta in [0, 2*PI]
     */
    public void testSinFunction() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator interpolator = new org.apache.commons.math.analysis.interpolation.DividedDifferenceInterpolator();
        double[] x;         double[] y;         double z;         double expected;         double result;         double tolerance;

        // 6 interpolating points on interval [0, 2*PI]
        int n = 6;
        double min = 0.0;         double max = 2 * (java.lang.Math.PI);
        x = new double[n];
        y = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = min + ((i * (max - min)) / n);
            y[i] = f.value(x[i]);
        }
        double derivativebound = 1.0;
        org.apache.commons.math.analysis.UnivariateRealFunction p = interpolator.interpolate(x, y);

        z = (java.lang.Math.PI) / 4;         expected = f.value(z);         result = p.value(z);
        tolerance = java.lang.Math.abs((derivativebound * (partialerror(x, z))));
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = (java.lang.Math.PI) * 1.5;         expected = f.value(z);         result = p.value(z);
        tolerance = java.lang.Math.abs((derivativebound * (partialerror(x, z))));
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of interpolator for the exponential function.
     * <p>
     * |expm1^(n)(zeta)| <= e, zeta in [-1, 1]
     */
    public void testExpm1Function() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.Expm1Function();
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator interpolator = new org.apache.commons.math.analysis.interpolation.DividedDifferenceInterpolator();
        double[] x;         double[] y;         double z;         double expected;         double result;         double tolerance;

        // 5 interpolating points on interval [-1, 1]
        int n = 5;
        double min = -1.0;         double max = 1.0;
        x = new double[n];
        y = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = min + ((i * (max - min)) / n);
            y[i] = f.value(x[i]);
        }
        double derivativebound = java.lang.Math.E;
        org.apache.commons.math.analysis.UnivariateRealFunction p = interpolator.interpolate(x, y);

        z = 0.0;         expected = f.value(z);         result = p.value(z);
        tolerance = java.lang.Math.abs((derivativebound * (partialerror(x, z))));
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = 0.5;         expected = f.value(z);         result = p.value(z);
        tolerance = java.lang.Math.abs((derivativebound * (partialerror(x, z))));
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        z = -0.5;         expected = f.value(z);         result = p.value(z);
        tolerance = java.lang.Math.abs((derivativebound * (partialerror(x, z))));
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of parameters for the interpolator.
     */
    public void testParameters() throws java.lang.Exception {
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator interpolator = new org.apache.commons.math.analysis.interpolation.DividedDifferenceInterpolator();

        try {
            // bad abscissas array
            double[] x = new double[]{ 1.0, 2.0, 2.0, 4.0 };
            double[] y = new double[]{ 0.0, 4.0, 4.0, 2.5 };
            org.apache.commons.math.analysis.UnivariateRealFunction p = interpolator.interpolate(x, y);
            p.value(0.0);
            junit.framework.Assert.fail("Expecting MathException - bad abscissas array");
        } catch (org.apache.commons.math.MathException ex) {
            // expected
        }
    }

    /**
     * Returns the partial error term (z-x[0])(z-x[1])...(z-x[n-1])/n!
     */
    protected double partialerror(double[] x, double z) throws 
    java.lang.IllegalArgumentException {

        if ((x.length) < 1) {
            throw new java.lang.IllegalArgumentException(
            "Interpolation array cannot be empty.");
        }
        double out = 1;
        for (int i = 0; i < (x.length); i++) {
            out *= (z - (x[i])) / (i + 1);
        }
        return out;
    }
}