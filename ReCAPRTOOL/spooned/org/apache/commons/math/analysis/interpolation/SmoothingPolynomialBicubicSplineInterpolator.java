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
 * Generates a bicubic interpolation function.
 * Prior to generating the interpolating function, the input is smoothed using
 * polynomial fitting.
 *
 * @version $Revision$ $Date$
 * @since 2.2
 */
public class SmoothingPolynomialBicubicSplineInterpolator extends 
org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolator {
    private final org.apache.commons.math.optimization.fitting.PolynomialFitter xFitter;
    private final org.apache.commons.math.optimization.fitting.PolynomialFitter yFitter;

    /**
     * Default constructor. The degree of the fitting polynomials is set to 3.
     */
    public SmoothingPolynomialBicubicSplineInterpolator() {
        this(3);
    }

    /**
     *
     *
     * @param degree
     * 		Degree of the polynomial fitting functions.
     */     public SmoothingPolynomialBicubicSplineInterpolator(int degree) {         this(degree, degree);}

    /**
     *
     *
     * @param xDegree
     * 		Degree of the polynomial fitting functions along the
     * 		x-dimension.
     * @param yDegree
     * 		Degree of the polynomial fitting functions along the
     * 		y-dimension.
     */     public SmoothingPolynomialBicubicSplineInterpolator(int xDegree, int yDegree) {         xFitter = new org.apache.commons.math.optimization.fitting.PolynomialFitter(xDegree, new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(false));         yFitter = new org.apache.commons.math.optimization.fitting.PolynomialFitter(yDegree, new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(false));
    }

    /**
     * {@inheritDoc}
     */
    public org.apache.commons.math.analysis.interpolation.BicubicSplineInterpolatingFunction interpolate(final double[] xval, final 
    double[] yval, final 
    double[][] fval) throws 
    org.apache.commons.math.MathException {
        if ((((xval.length) == 0) || ((yval.length) == 0)) || ((fval.length) == 0)) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
        if ((xval.length) != (fval.length)) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(xval.length, fval.length);
        }

        final int xLen = xval.length;
        final int yLen = yval.length;

        for (int i = 0; i < xLen; i++) {
            if ((fval[i].length) != yLen) {
                throw new org.apache.commons.math.exception.DimensionMismatchException(fval[i].length, yLen);
            }
        }

        org.apache.commons.math.util.MathUtils.checkOrder(xval);
        org.apache.commons.math.util.MathUtils.checkOrder(yval);

        // For each line y[j] (0 <= j < yLen), construct a polynomial, with
        // respect to variable x, fitting array fval[][j]
        final org.apache.commons.math.analysis.polynomials.PolynomialFunction[] yPolyX = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[yLen];
        for (int j = 0; j < yLen; j++) {
            xFitter.clearObservations();
            for (int i = 0; i < xLen; i++) {
                xFitter.addObservedPoint(1, xval[i], fval[i][j]);
            }

            yPolyX[j] = xFitter.fit();
        }

        // For every knot (xval[i], yval[j]) of the grid, calculate corrected
        // values fval_1
        final double[][] fval_1 = new double[xLen][yLen];
        for (int j = 0; j < yLen; j++) {
            final org.apache.commons.math.analysis.polynomials.PolynomialFunction f = yPolyX[j];
            for (int i = 0; i < xLen; i++) {
                fval_1[i][j] = f.value(xval[i]);
            }
        }

        // For each line x[i] (0 <= i < xLen), construct a polynomial, with
        // respect to variable y, fitting array fval_1[i][]
        final org.apache.commons.math.analysis.polynomials.PolynomialFunction[] xPolyY = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[xLen];
        for (int i = 0; i < xLen; i++) {
            yFitter.clearObservations();
            for (int j = 0; j < yLen; j++) {
                yFitter.addObservedPoint(1, yval[j], fval_1[i][j]);
            }

            xPolyY[i] = yFitter.fit();
        }

        // For every knot (xval[i], yval[j]) of the grid, calculate corrected
        // values fval_2
        final double[][] fval_2 = new double[xLen][yLen];
        for (int i = 0; i < xLen; i++) {
            final org.apache.commons.math.analysis.polynomials.PolynomialFunction f = xPolyY[i];
            for (int j = 0; j < yLen; j++) {
                fval_2[i][j] = f.value(yval[j]);
            }
        }

        return super.interpolate(xval, yval, fval_2);
    }
}