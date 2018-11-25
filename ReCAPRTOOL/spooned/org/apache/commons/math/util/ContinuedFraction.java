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
package org.apache.commons.math.util;





/**
 * Provides a generic means to evaluate continued fractions.  Subclasses simply
 * provided the a and b coefficients to evaluate the continued fraction.
 *
 * <p>
 * References:
 * <ul>
 * <li><a href="http://mathworld.wolfram.com/ContinuedFraction.html">
 * Continued Fraction</a></li>
 * </ul>
 * </p>
 *
 * @version $Revision$ $Date$
 */
public abstract class ContinuedFraction {

    /**
     * Maximum allowed numerical error.
     */     private static final double DEFAULT_EPSILON = 1.0E-8;
    /**
     * Default constructor.
     */
    protected ContinuedFraction() {
        super();
    }

    /**
     * Access the n-th a coefficient of the continued fraction.  Since a can be
     * a function of the evaluation point, x, that is passed in as well.
     *
     * @param n
     * 		the coefficient index to retrieve.
     * @param x
     * 		the evaluation point.
     * @return the n-th a coefficient.
     */     protected abstract double getA(int n, double x);     /**
     * Access the n-th b coefficient of the continued fraction.  Since b can be
     * a function of the evaluation point, x, that is passed in as well.
     *
     * @param n
     * 		the coefficient index to retrieve.
     * @param x
     * 		the evaluation point.
     * @return the n-th b coefficient.
     */     protected abstract double getB(int n, double x);     /**
     * Evaluates the continued fraction at the value x.
     *
     * @param x
     * 		the evaluation point.
     * @return the value of the continued fraction evaluated at x.
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public double evaluate(double x) throws org.apache.commons.math.MathException {         return evaluate(x, org.apache.commons.math.util.ContinuedFraction.DEFAULT_EPSILON, java.lang.Integer.MAX_VALUE);}

    /**
     * Evaluates the continued fraction at the value x.
     *
     * @param x
     * 		the evaluation point.
     * @param epsilon
     * 		maximum error allowed.
     * @return the value of the continued fraction evaluated at x.
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public double evaluate(double x, double epsilon) throws org.apache.commons.math.MathException {         return evaluate(x, epsilon, java.lang.Integer.MAX_VALUE);}
    /**
     * Evaluates the continued fraction at the value x.
     *
     * @param x
     * 		the evaluation point.
     * @param maxIterations
     * 		maximum number of convergents
     * @return the value of the continued fraction evaluated at x.
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public double evaluate(double x, int maxIterations) throws org.apache.commons.math.MathException {         return evaluate(x, org.apache.commons.math.util.ContinuedFraction.DEFAULT_EPSILON, maxIterations);}
    /**
     * <p>
     * Evaluates the continued fraction at the value x.
     * </p>
     *
     * <p>
     * The implementation of this method is based on equations 14-17 of:
     * <ul>
     * <li>
     *   Eric W. Weisstein. "Continued Fraction." From MathWorld--A Wolfram Web
     *   Resource. <a target="_blank"
     *   href="http://mathworld.wolfram.com/ContinuedFraction.html">
     *   http://mathworld.wolfram.com/ContinuedFraction.html</a>
     * </li>
     * </ul>
     * The recurrence relationship defined in those equations can result in
     * very large intermediate results which can result in numerical overflow.
     * As a means to combat these overflow conditions, the intermediate results
     * are scaled whenever they threaten to become numerically unstable.</p>
     *
     * @param x
     * 		the evaluation point.
     * @param epsilon
     * 		maximum error allowed.
     * @param maxIterations
     * 		maximum number of convergents
     * @return the value of the continued fraction evaluated at x.
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public double evaluate(double x, double epsilon, int maxIterations) throws org.apache.commons.math.MathException {         double p0 = 1.0;
        double p1 = getA(0, x);
        double q0 = 0.0;
        double q1 = 1.0;
        double c = p1 / q1;
        int n = 0;
        double relativeError = java.lang.Double.MAX_VALUE;
        while ((n < maxIterations) && (relativeError > epsilon)) {
            ++n;
            double a = getA(n, x);
            double b = getB(n, x);
            double p2 = (a * p1) + (b * p0);
            double q2 = (a * q1) + (b * q0);
            boolean infinite = false;
            if ((java.lang.Double.isInfinite(p2)) || (java.lang.Double.isInfinite(q2))) {
                /* Need to scale. Try successive powers of the larger of a or b
                up to 5th power. Throw ConvergenceException if one or both
                of p2, q2 still overflow.
                 */

                double scaleFactor = 1.0;
                double lastScaleFactor = 1.0;
                final int maxPower = 5;
                final double scale = java.lang.Math.max(a, b);
                if (scale <= 0) {                     // Can't scale
                    throw new org.apache.commons.math.ConvergenceException(
                    org.apache.commons.math.util.LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, 
                    x);
                }
                infinite = true;
                for (int i = 0; i < maxPower; i++) {
                    lastScaleFactor = scaleFactor;
                    scaleFactor *= scale;
                    if ((a != 0.0) && (a > b)) {
                        p2 = (p1 / lastScaleFactor) + ((b / scaleFactor) * p0);
                        q2 = (q1 / lastScaleFactor) + ((b / scaleFactor) * q0);
                    }else                         if (b != 0) {
                            p2 = ((a / scaleFactor) * p1) + (p0 / lastScaleFactor);
                            q2 = ((a / scaleFactor) * q1) + (q0 / lastScaleFactor);
                        }
                    infinite = (java.lang.Double.isInfinite(p2)) || (java.lang.Double.isInfinite(q2));
                    if (!infinite) {
                        break;
                    }
                }
            }

            if (infinite) {
                // Scaling failed
                throw new org.apache.commons.math.ConvergenceException(
                org.apache.commons.math.util.LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, 
                x);
            }

            double r = p2 / q2;

            if (java.lang.Double.isNaN(r)) {
                throw new org.apache.commons.math.ConvergenceException(
                org.apache.commons.math.util.LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE, 
                x);
            }
            relativeError = java.lang.Math.abs(((r / c) - 1.0));

            // prepare for next iteration
            c = p2 / q2;
            p0 = p1;
            p1 = p2;
            q0 = q1;
            q1 = q2;
        } 

        if (n >= maxIterations) {
            throw new org.apache.commons.math.MaxIterationsExceededException(maxIterations, 
            org.apache.commons.math.util.LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION, 
            x);
        }

        return c;
    }
}