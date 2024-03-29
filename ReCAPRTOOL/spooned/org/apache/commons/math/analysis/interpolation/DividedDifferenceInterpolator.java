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
 * Implements the <a href="
 * "http://mathworld.wolfram.com/NewtonsDividedDifferenceInterpolationFormula.html">
 * Divided Difference Algorithm</a> for interpolation of real univariate
 * functions. For reference, see <b>Introduction to Numerical Analysis</b>,
 * ISBN 038795452X, chapter 2.
 * <p>
 * The actual code of Neville's evaluation is in PolynomialFunctionLagrangeForm,
 * this class provides an easy-to-use interface to it.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public class DividedDifferenceInterpolator implements 
java.io.Serializable , org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator {

    /**
     * serializable version identifier
     */     private static final long serialVersionUID = 107049519551235069L;
    /**
     * Computes an interpolating function for the data set.
     *
     * @param x
     * 		the interpolating points array
     * @param y
     * 		the interpolating values array
     * @return a function which interpolates the data set
     * @throws DuplicateSampleAbscissaException
     * 		if arguments are invalid
     */     public org.apache.commons.math.analysis.polynomials.PolynomialFunctionNewtonForm interpolate(double[] x, double[] y) throws org.apache.commons.math.DuplicateSampleAbscissaException {
        /**
         * a[] and c[] are defined in the general formula of Newton form:
         * p(x) = a[0] + a[1](x-c[0]) + a[2](x-c[0])(x-c[1]) + ... +
         *        a[n](x-c[0])(x-c[1])...(x-c[n-1])
         */
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm.verifyInterpolationArray(x, y);

        /**
         * When used for interpolation, the Newton form formula becomes
         * p(x) = f[x0] + f[x0,x1](x-x0) + f[x0,x1,x2](x-x0)(x-x1) + ... +
         *        f[x0,x1,...,x[n-1]](x-x0)(x-x1)...(x-x[n-2])
         * Therefore, a[k] = f[x0,x1,...,xk], c[k] = x[k].
         * <p>
         * Note x[], y[], a[] have the same length but c[]'s size is one less.</p>
         */
        final double[] c = new double[(x.length) - 1];
        java.lang.System.arraycopy(x, 0, c, 0, c.length);

        final double[] a = org.apache.commons.math.analysis.interpolation.DividedDifferenceInterpolator.computeDividedDifference(x, y);
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunctionNewtonForm(a, c);

    }

    /**
     * Returns a copy of the divided difference array.
     * <p>
     * The divided difference array is defined recursively by <pre>
     * f[x0] = f(x0)
     * f[x0,x1,...,xk] = (f(x1,...,xk) - f(x0,...,x[k-1])) / (xk - x0)
     * </pre></p>
     * <p>
     * The computational complexity is O(N^2).</p>
     *
     * @param x
     * 		the interpolating points array
     * @param y
     * 		the interpolating values array
     * @return a fresh copy of the divided difference array
     * @throws DuplicateSampleAbscissaException
     * 		if any abscissas coincide
     */     protected static double[] computeDividedDifference(final double[] x, final double[] y) throws org.apache.commons.math.DuplicateSampleAbscissaException {
        org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm.verifyInterpolationArray(x, y);

        final double[] divdiff = y.clone();// initialization

        final int n = x.length;
        final double[] a = new double[n];
        a[0] = divdiff[0];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < (n - i); j++) {
                final double denominator = (x[(j + i)]) - (x[j]);
                if (denominator == 0.0) {
                    // This happens only when two abscissas are identical.
                    throw new org.apache.commons.math.DuplicateSampleAbscissaException(x[j], j, (j + i));
                }
                divdiff[j] = ((divdiff[(j + 1)]) - (divdiff[j])) / denominator;
            }
            a[i] = divdiff[0];
        }

        return a;
    }
}