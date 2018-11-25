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
 * Test the SplineInterpolator.
 *
 * @version $Revision$ $Date$
 */
public class SplineInterpolatorTest {

    /**
     * error tolerance for spline interpolator value at knot points
     */     protected double knotTolerance = 1.0E-12;
    /**
     * error tolerance for interpolating polynomial coefficients
     */     protected double coefficientTolerance = 1.0E-6;
    /**
     * error tolerance for interpolated values -- high value is from sin test
     */     protected double interpolationTolerance = 0.01;
    @org.junit.Test
    public void testInterpolateLinearDegenerateTwoSegment() throws 
    java.lang.Exception {
        double[] x = new double[]{ 0.0, 0.5, 1.0 };
        double[] y = new double[]{ 0.0, 0.5, 1.0 };
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator i = new org.apache.commons.math.analysis.interpolation.SplineInterpolator();
        org.apache.commons.math.analysis.UnivariateRealFunction f = i.interpolate(x, y);
        verifyInterpolation(f, x, y);
        verifyConsistency(((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)), x);

        // Verify coefficients using analytical values
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = ((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)).getPolynomials();
        double[] target = new double[]{ y[0], 1.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[0].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[1], 1.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[1].getCoefficients(), target, coefficientTolerance);

        // Check interpolation
        org.junit.Assert.assertEquals(0.0, f.value(0.0), interpolationTolerance);
        org.junit.Assert.assertEquals(0.4, f.value(0.4), interpolationTolerance);
        org.junit.Assert.assertEquals(1.0, f.value(1.0), interpolationTolerance);
    }

    @org.junit.Test
    public void testInterpolateLinearDegenerateThreeSegment() throws 
    java.lang.Exception {
        double[] x = new double[]{ 0.0, 0.5, 1.0, 1.5 };
        double[] y = new double[]{ 0.0, 0.5, 1.0, 1.5 };
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator i = new org.apache.commons.math.analysis.interpolation.SplineInterpolator();
        org.apache.commons.math.analysis.UnivariateRealFunction f = i.interpolate(x, y);
        verifyInterpolation(f, x, y);

        // Verify coefficients using analytical values
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = ((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)).getPolynomials();
        double[] target = new double[]{ y[0], 1.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[0].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[1], 1.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[1].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[2], 1.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[2].getCoefficients(), target, coefficientTolerance);

        // Check interpolation
        org.junit.Assert.assertEquals(0, f.value(0), interpolationTolerance);
        org.junit.Assert.assertEquals(1.4, f.value(1.4), interpolationTolerance);
        org.junit.Assert.assertEquals(1.5, f.value(1.5), interpolationTolerance);
    }

    @org.junit.Test
    public void testInterpolateLinear() throws java.lang.Exception {
        double[] x = new double[]{ 0.0, 0.5, 1.0 };
        double[] y = new double[]{ 0.0, 0.5, 0.0 };
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator i = new org.apache.commons.math.analysis.interpolation.SplineInterpolator();
        org.apache.commons.math.analysis.UnivariateRealFunction f = i.interpolate(x, y);
        verifyInterpolation(f, x, y);
        verifyConsistency(((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)), x);

        // Verify coefficients using analytical values
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = ((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)).getPolynomials();
        double[] target = new double[]{ y[0], 1.5, 0.0, -2.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[0].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[1], 0.0, -3.0, 2.0 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[1].getCoefficients(), target, coefficientTolerance);
    }

    @org.junit.Test
    public void testInterpolateSin() throws java.lang.Exception {
        double[] x = 
        new double[]{ 
        0.0, 
        (java.lang.Math.PI) / 6.0, 
        (java.lang.Math.PI) / 2.0, 
        (5.0 * (java.lang.Math.PI)) / 6.0, 
        java.lang.Math.PI, 
        (7.0 * (java.lang.Math.PI)) / 6.0, 
        (3.0 * (java.lang.Math.PI)) / 2.0, 
        (11.0 * (java.lang.Math.PI)) / 6.0, 
        2.0 * (java.lang.Math.PI) };
        double[] y = new double[]{ 0.0, 0.5, 1.0, 0.5, 0.0, -0.5, -1.0, -0.5, 0.0 };
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator i = new org.apache.commons.math.analysis.interpolation.SplineInterpolator();
        org.apache.commons.math.analysis.UnivariateRealFunction f = i.interpolate(x, y);
        verifyInterpolation(f, x, y);
        verifyConsistency(((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)), x);

        /* Check coefficients against values computed using R (version 1.8.1, Red Hat Linux 9)

        To replicate in R:
            x[1] <- 0
            x[2] <- pi / 6, etc, same for y[] (could use y <- scan() for y values)
            g <- splinefun(x, y, "natural")
            splinecoef <- eval(expression(z), envir = environment(g))
            print(splinecoef)
         */
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = ((org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction) (f)).getPolynomials();
        double[] target = new double[]{ y[0], 1.002676, 0.0, -0.17415829 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[0].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[1], 0.8594367, -0.2735672, -0.08707914 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[1].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[2], 1.471804E-17, -0.5471344, 0.08707914 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[2].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[3], -0.8594367, -0.2735672, 0.17415829 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[3].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[4], -1.002676, 6.548562E-17, 0.17415829 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[4].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[5], -0.8594367, 0.2735672, 0.08707914 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[5].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[6], 3.466465E-16, 0.5471344, -0.08707914 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[6].getCoefficients(), target, coefficientTolerance);
        target = new double[]{ y[7], 0.8594367, 0.2735672, -0.17415829 };
        org.apache.commons.math.TestUtils.assertEquals(polynomials[7].getCoefficients(), target, coefficientTolerance);

        // Check interpolation
        org.junit.Assert.assertEquals(((java.lang.Math.sqrt(2.0)) / 2.0), f.value(((java.lang.Math.PI) / 4.0)), interpolationTolerance);
        org.junit.Assert.assertEquals(((java.lang.Math.sqrt(2.0)) / 2.0), f.value(((3.0 * (java.lang.Math.PI)) / 4.0)), interpolationTolerance);
    }

    @org.junit.Test
    public void testIllegalArguments() throws org.apache.commons.math.MathException {
        // Data set arrays of different size.
        org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator i = new org.apache.commons.math.analysis.interpolation.SplineInterpolator();
        try {
            double[] xval = new double[]{ 0.0, 1.0 };
            double[] yval = new double[]{ 0.0, 1.0, 2.0 };
            i.interpolate(xval, yval);
            org.junit.Assert.fail("Failed to detect data set array with different sizes.");
        } catch (org.apache.commons.math.exception.DimensionMismatchException iae) {
            // Expected.
        }
        // X values not sorted.
        try {
            double[] xval = new double[]{ 0.0, 1.0, 0.5 };
            double[] yval = new double[]{ 0.0, 1.0, 2.0 };
            i.interpolate(xval, yval);
            org.junit.Assert.fail("Failed to detect unsorted arguments.");
        } catch (org.apache.commons.math.exception.NonMonotonousSequenceException iae) {
            // Expected.
        }
        // Not enough data to interpolate.
        try {
            double[] xval = new double[]{ 0.0, 1.0 };
            double[] yval = new double[]{ 0.0, 1.0 };
            i.interpolate(xval, yval);
            org.junit.Assert.fail("Failed to detect unsorted arguments.");
        } catch (org.apache.commons.math.exception.NumberIsTooSmallException iae) {
            // Expected.
        }
    }

    /**
     * verifies that f(x[i]) = y[i] for i = 0..n-1 where n is common length.
     */
    protected void verifyInterpolation(org.apache.commons.math.analysis.UnivariateRealFunction f, double[] x, double[] y) throws 
    java.lang.Exception {
        for (int i = 0; i < (x.length); i++) {
            org.junit.Assert.assertEquals(f.value(x[i]), y[i], knotTolerance);
        }
    }

    /**
     * Verifies that interpolating polynomials satisfy consistency requirement:
     *    adjacent polynomials must agree through two derivatives at knot points
     */
    protected void verifyConsistency(org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction f, double[] x) throws 
    java.lang.Exception {
        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = f.getPolynomials();
        for (int i = 1; i < ((x.length) - 2); i++) {
            // evaluate polynomials and derivatives at x[i + 1]
            org.junit.Assert.assertEquals(polynomials[i].value(((x[(i + 1)]) - (x[i]))), polynomials[(i + 1)].value(0), 0.1);
            org.junit.Assert.assertEquals(polynomials[i].derivative().value(((x[(i + 1)]) - (x[i]))), 
            polynomials[(i + 1)].derivative().value(0), 0.5);
            org.junit.Assert.assertEquals(polynomials[i].polynomialDerivative().derivative().value(((x[(i + 1)]) - (x[i]))), 
            polynomials[(i + 1)].polynomialDerivative().derivative().value(0), 0.5);
        }
    }

}