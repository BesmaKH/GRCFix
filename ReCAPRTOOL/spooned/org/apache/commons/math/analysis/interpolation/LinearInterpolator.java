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
 * Implements a linear function for interpolation of real univariate functions.
 */
public class LinearInterpolator implements org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator {
    /**
     * Computes a linear interpolating function for the data set.
     *
     * @param x
     * 		the arguments for the interpolation points
     * @param y
     * 		the values for the interpolation points
     * @return a function which interpolates the data set
     * @throws DimensionMismatchException
     * 		if {@code x} and {@code y}
     * 		have different sizes.
     * @throws NonMonotonousSequenceException
     * 		if {@code x} is not sorted in
     * 		strict increasing order.
     * @throws NumberIsTooSmallException
     * 		if the size of {@code x} is smaller
     * 		than 2.
     */     public org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction interpolate(double[] x, double[] y) {         if ((x.length) != (y.length)) {             throw new org.apache.commons.math.exception.DimensionMismatchException(x.length, y.length);}         if ((x.length) < 2) {
            throw new org.apache.commons.math.exception.NumberIsTooSmallException(org.apache.commons.math.util.LocalizedFormats.NUMBER_OF_POINTS, 
            x.length, 2, true);
        }

        // Number of intervals.  The number of data points is n + 1.
        int n = (x.length) - 1;

        org.apache.commons.math.util.MathUtils.checkOrder(x);

        // Slope of the lines between the datapoints.
        final double[] m = new double[n];
        for (int i = 0; i < n; i++) {
            m[i] = ((y[(i + 1)]) - (y[i])) / ((x[(i + 1)]) - (x[i]));
        }

        org.apache.commons.math.analysis.polynomials.PolynomialFunction[] polynomials = new org.apache.commons.math.analysis.polynomials.PolynomialFunction[n];
        final double[] coefficients = new double[2];
        for (int i = 0; i < n; i++) {
            coefficients[0] = y[i];
            coefficients[1] = m[i];
            polynomials[i] = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
        }

        return new org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction(x, polynomials);
    }
}