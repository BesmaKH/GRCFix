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
 * Testcase for the smoothing bicubic interpolator.
 *
 * @version $Revision$ $Date$
 */
public final class SmoothingPolynomialBicubicSplineInterpolatorTest {
    /**
     * Test preconditions.
     */
    @org.junit.Test
    public void testPreconditions() throws org.apache.commons.math.MathException {
        double[] xval = new double[]{ 3, 4, 5, 6.5 };
        double[] yval = new double[]{ -4, -3, -1, 2.5 };
        double[][] zval = new double[xval.length][yval.length];

        org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator interpolator = new org.apache.commons.math.analysis.interpolation.SmoothingPolynomialBicubicSplineInterpolator(0);

        @java.lang.SuppressWarnings("unused")
        org.apache.commons.math.analysis.BivariateRealFunction p = interpolator.interpolate(xval, yval, zval);

        double[] wxval = new double[]{ 3, 2, 5, 6.5 };
        try {
            p = interpolator.interpolate(wxval, yval, zval);
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalArgumentException e) {
            // Expected
        }

        double[] wyval = new double[]{ -4, -3, -1, -1 };
        try {
            p = interpolator.interpolate(xval, wyval, zval);
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalArgumentException e) {
            // Expected
        }

        double[][] wzval = new double[xval.length][(yval.length) + 1];
        try {
            p = interpolator.interpolate(xval, yval, wzval);
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (org.apache.commons.math.exception.DimensionMismatchException e) {
            // Expected
        }
        wzval = new double[(xval.length) - 1][yval.length];
        try {
            p = interpolator.interpolate(xval, yval, wzval);
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (org.apache.commons.math.exception.DimensionMismatchException e) {
            // Expected
        }
        wzval = new double[xval.length][(yval.length) - 1];
        try {
            p = interpolator.interpolate(xval, yval, wzval);
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (org.apache.commons.math.exception.DimensionMismatchException e) {
            // Expected
        }
    }

    /**
     * Test of interpolator for a plane.
     * <p>
     * z = 2 x - 3 y + 5
     */
    @org.junit.Test
    public void testPlane() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.BivariateRealFunction f = new org.apache.commons.math.analysis.BivariateRealFunction() {
            public double value(double x, double y) {
                return (((2 * x) - (3 * y)) + 5) + 
                ((((int) (java.lang.Math.abs(((5 * x) + (3 * y))))) % 2) == 0 ? 1 : -1);
            }
        };

        org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator interpolator = new org.apache.commons.math.analysis.interpolation.SmoothingPolynomialBicubicSplineInterpolator(1);

        double[] xval = new double[]{ 3, 4, 5, 6.5 };
        double[] yval = new double[]{ -4, -3, -1, 2, 2.5 };
        double[][] zval = new double[xval.length][yval.length];
        for (int i = 0; i < (xval.length); i++) {
            for (int j = 0; j < (yval.length); j++) {
                zval[i][j] = f.value(xval[i], yval[j]);
            }
        }

        org.apache.commons.math.analysis.BivariateRealFunction p = interpolator.interpolate(xval, yval, zval);
        double x;         double y;
        double expected;         double result;

        x = 4;
        y = -3;
        expected = f.value(x, y);
        result = p.value(x, y);
        org.junit.Assert.assertEquals("On sample point", expected, result, 2);

        x = 4.5;
        y = -1.5;
        expected = f.value(x, y);
        result = p.value(x, y);
        org.junit.Assert.assertEquals("half-way between sample points (middle of the patch)", expected, result, 2);

        x = 3.5;
        y = -3.5;
        expected = f.value(x, y);
        result = p.value(x, y);
        org.junit.Assert.assertEquals("half-way between sample points (border of the patch)", expected, result, 2);
    }

    /**
     * Test of interpolator for a paraboloid.
     * <p>
     * z = 2 x<sup>2</sup> - 3 y<sup>2</sup> + 4 x y - 5
     */
    @org.junit.Test
    public void testParaboloid() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.BivariateRealFunction f = new org.apache.commons.math.analysis.BivariateRealFunction() {
            public double value(double x, double y) {
                return (((((2 * x) * x) - ((3 * y) * y)) + ((4 * x) * y)) - 5) + 
                ((((int) (java.lang.Math.abs(((5 * x) + (3 * y))))) % 2) == 0 ? 1 : -1);
            }
        };

        org.apache.commons.math.analysis.interpolation.BivariateRealGridInterpolator interpolator = new org.apache.commons.math.analysis.interpolation.SmoothingPolynomialBicubicSplineInterpolator(4);

        double[] xval = new double[]{ 3, 4, 5, 6.5 };
        double[] yval = new double[]{ -4, -3, -2, -1, 0.5, 2.5 };
        double[][] zval = new double[xval.length][yval.length];
        for (int i = 0; i < (xval.length); i++) {
            for (int j = 0; j < (yval.length); j++) {
                zval[i][j] = f.value(xval[i], yval[j]);
            }
        }

        org.apache.commons.math.analysis.BivariateRealFunction p = interpolator.interpolate(xval, yval, zval);
        double x;         double y;
        double expected;         double result;

        x = 5;
        y = 0.5;
        expected = f.value(x, y);
        result = p.value(x, y);
        org.junit.Assert.assertEquals("On sample point", expected, result, 2);

        x = 4.5;
        y = -1.5;
        expected = f.value(x, y);
        result = p.value(x, y);
        org.junit.Assert.assertEquals("half-way between sample points (middle of the patch)", expected, result, 2);

        x = 3.5;
        y = -3.5;
        expected = f.value(x, y);
        result = p.value(x, y);
        org.junit.Assert.assertEquals("half-way between sample points (border of the patch)", expected, result, 2);
    }
}