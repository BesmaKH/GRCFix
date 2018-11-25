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
 * This is a utility class that provides computation methods related to the
 * Gamma family of functions.
 *
 * @version $Revision$ $Date$
 */
public class Gamma {

    /**
     * <a href="http://en.wikipedia.org/wiki/Euler-Mascheroni_constant">Euler-Mascheroni constant</a>
     *
     * @since 2.0
     */     public static final double GAMMA = 0.5772156649015329;

    /**
     * Maximum allowed numerical error.
     */     private static final double DEFAULT_EPSILON = 1.0E-14;
    /**
     * Lanczos coefficients
     */     private static final double[] LANCZOS = new double[]{ 
    0.9999999999999971, 
    57.15623566586292, 
    -59.59796035547549, 
    14.136097974741746, 
    -0.4919138160976202, 
    3.399464998481189E-5, 
    4.652362892704858E-5, 
    -9.837447530487956E-5, 
    1.580887032249125E-4, 
    -2.1026444172410488E-4, 
    2.1743961811521265E-4, 
    -1.643181065367639E-4, 
    8.441822398385275E-5, 
    -2.6190838401581408E-5, 
    3.6899182659531625E-6 };


    /**
     * Avoid repeated computation of log of 2 PI in logGamma
     */     private static final double HALF_LOG_2_PI = 0.5 * (java.lang.Math.log((2.0 * (java.lang.Math.PI))));
    // limits for switching algorithm in digamma
    /**
     * C limit.
     */     private static final double C_LIMIT = 49;
    /**
     * S limit.
     */     private static final double S_LIMIT = 1.0E-5;
    /**
     * Default constructor.  Prohibit instantiation.
     */
    private Gamma() {
        super();
    }

    /**
     * Returns the natural logarithm of the gamma function &#915;(x).
     *
     * The implementation of this method is based on:
     * <ul>
     * <li><a href="http://mathworld.wolfram.com/GammaFunction.html">
     * Gamma Function</a>, equation (28).</li>
     * <li><a href="http://mathworld.wolfram.com/LanczosApproximation.html">
     * Lanczos Approximation</a>, equations (1) through (5).</li>
     * <li><a href="http://my.fit.edu/~gabdo/gamma.txt">Paul Godfrey, A note on
     * the computation of the convergent Lanczos complex Gamma approximation
     * </a></li>
     * </ul>
     *
     * @param x
     * 		the value.
     * @return log(&#915;(x))
     */     public static double logGamma(double x) {
        double ret;

        if ((java.lang.Double.isNaN(x)) || (x <= 0.0)) {
            ret = java.lang.Double.NaN;
        }else {
            double g = 607.0 / 128.0;

            double sum = 0.0;
            for (int i = (org.apache.commons.math.special.Gamma.LANCZOS.length) - 1; i > 0; --i) {
                sum = sum + ((org.apache.commons.math.special.Gamma.LANCZOS[i]) / (x + i));
            }
            sum = sum + (org.apache.commons.math.special.Gamma.LANCZOS[0]);

            double tmp = (x + g) + 0.5;
            ret = ((((x + 0.5) * (java.lang.Math.log(tmp))) - tmp) + 
            (org.apache.commons.math.special.Gamma.HALF_LOG_2_PI)) + (java.lang.Math.log((sum / x)));
        }

        return ret;
    }

    /**
     * Returns the regularized gamma function P(a, x).
     *
     * @param a
     * 		the a parameter.
     * @param x
     * 		the value.
     * @return the regularized gamma function P(a, x)
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public static double regularizedGammaP(double a, double x) throws org.apache.commons.math.MathException {
        return org.apache.commons.math.special.Gamma.regularizedGammaP(a, x, org.apache.commons.math.special.Gamma.DEFAULT_EPSILON, java.lang.Integer.MAX_VALUE);
    }


    /**
     * Returns the regularized gamma function P(a, x).
     *
     * The implementation of this method is based on:
     * <ul>
     * <li>
     * <a href="http://mathworld.wolfram.com/RegularizedGammaFunction.html">
     * Regularized Gamma Function</a>, equation (1).</li>
     * <li>
     * <a href="http://mathworld.wolfram.com/IncompleteGammaFunction.html">
     * Incomplete Gamma Function</a>, equation (4).</li>
     * <li>
     * <a href="http://mathworld.wolfram.com/ConfluentHypergeometricFunctionoftheFirstKind.html">
     * Confluent Hypergeometric Function of the First Kind</a>, equation (1).
     * </li>
     * </ul>
     *
     * @param a
     * 		the a parameter.
     * @param x
     * 		the value.
     * @param epsilon
     * 		When the absolute value of the nth item in the
     * 		series is less than epsilon the approximation ceases
     * 		to calculate further elements in the series.
     * @param maxIterations
     * 		Maximum number of "iterations" to complete.
     * @return the regularized gamma function P(a, x)
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public static double regularizedGammaP(double a, double x, double epsilon, int maxIterations) throws org.apache.commons.math.MathException 
    {
        double ret;






        // use regularizedGammaQ because it should converge faster in this
        // case.


        // calculate series
        // current element index
        // n-th element in the series
        // partial sum

        // compute next element in the series



        // update partial sum
        if ((((java.lang.Double.isNaN(a)) || (java.lang.Double.isNaN(x))) || (a <= 0.0)) || (x < 0.0)) {             ret = java.lang.Double.NaN;}// use regularizedGammaQ because it should converge faster in this         // case.         // calculate series         // current element index         // n-th element in the series         // partial sum         // compute next element in the series         // update partial sum
        else             if (x == 0.0) {                 ret = 0.0;}else                 if (x >= (a + 1)) {                     ret = 1.0 - (org.apache.commons.math.special.Gamma.regularizedGammaQ(a, x, epsilon, maxIterations));}else {                     double n = 0.0;                     double an = 1.0 / a;                     double sum = an;                     while ((((java.lang.Math.abs((an / sum))) > epsilon) && (n < maxIterations)) && (sum < (java.lang.Double.POSITIVE_INFINITY))) {                         n = n + 1.0;                         an = an * (x / (a + n));                         sum = sum + an;} 
                    if (n >= maxIterations) {
                        throw new org.apache.commons.math.MaxIterationsExceededException(maxIterations);
                    }else                         if (java.lang.Double.isInfinite(sum)) {
                            ret = 1.0;
                        }else {
                            ret = (java.lang.Math.exp((((-x) + (a * (java.lang.Math.log(x)))) - (org.apache.commons.math.special.Gamma.logGamma(a))))) * sum;
                        }
                }

        return ret;
    }

    /**
     * Returns the regularized gamma function Q(a, x) = 1 - P(a, x).
     *
     * @param a
     * 		the a parameter.
     * @param x
     * 		the value.
     * @return the regularized gamma function Q(a, x)
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public static double regularizedGammaQ(double a, double x) throws org.apache.commons.math.MathException {
        return org.apache.commons.math.special.Gamma.regularizedGammaQ(a, x, org.apache.commons.math.special.Gamma.DEFAULT_EPSILON, java.lang.Integer.MAX_VALUE);
    }

    /**
     * Returns the regularized gamma function Q(a, x) = 1 - P(a, x).
     *
     * The implementation of this method is based on:
     * <ul>
     * <li>
     * <a href="http://mathworld.wolfram.com/RegularizedGammaFunction.html">
     * Regularized Gamma Function</a>, equation (1).</li>
     * <li>
     * <a href="http://functions.wolfram.com/GammaBetaErf/GammaRegularized/10/0003/">
     * Regularized incomplete gamma function: Continued fraction representations  (formula 06.08.10.0003)</a></li>
     * </ul>
     *
     * @param a
     * 		the a parameter.
     * @param x
     * 		the value.
     * @param epsilon
     * 		When the absolute value of the nth item in the
     * 		series is less than epsilon the approximation ceases
     * 		to calculate further elements in the series.
     * @param maxIterations
     * 		Maximum number of "iterations" to complete.
     * @return the regularized gamma function P(a, x)
     * @throws MathException
     * 		if the algorithm fails to converge.
     */     public static double regularizedGammaQ(final double a, double x, double epsilon, int maxIterations) throws org.apache.commons.math.MathException 
    {
        double ret;






        // use regularizedGammaP because it should converge faster in this
        // case.


        // create continued fraction
        if ((((java.lang.Double.isNaN(a)) || (java.lang.Double.isNaN(x))) || (a <= 0.0)) || (x < 0.0)) {             ret = java.lang.Double.NaN;}// use regularizedGammaP because it should converge faster in this         // case.         // create continued fraction
        else             if (x == 0.0) {                 ret = 1.0;}else                 if (x < (a + 1.0)) {                     ret = 1.0 - (org.apache.commons.math.special.Gamma.regularizedGammaP(a, x, epsilon, maxIterations));}else {                     org.apache.commons.math.util.ContinuedFraction cf = new org.apache.commons.math.util.ContinuedFraction() {
                        @java.lang.Override
                        protected double getA(int n, double x) {
                            return (((2.0 * n) + 1.0) - a) + x;
                        }

                        @java.lang.Override
                        protected double getB(int n, double x) {
                            return n * (a - n);
                        }
                    };

                    ret = 1.0 / (cf.evaluate(x, epsilon, maxIterations));
                    ret = (java.lang.Math.exp((((-x) + (a * (java.lang.Math.log(x)))) - (org.apache.commons.math.special.Gamma.logGamma(a))))) * ret;
                }

        return ret;
    }


    /**
     * <p>Computes the digamma function of x.</p>
     *
     * <p>This is an independently written implementation of the algorithm described in
     * Jose Bernardo, Algorithm AS 103: Psi (Digamma) Function, Applied Statistics, 1976.</p>
     *
     * <p>Some of the constants have been changed to increase accuracy at the moderate expense
     * of run-time.  The result should be accurate to within 10^-8 absolute tolerance for
     * x >= 10^-5 and within 10^-8 relative tolerance for x > 0.</p>
     *
     * <p>Performance for large negative values of x will be quite expensive (proportional to
     * |x|).  Accuracy for negative values of x should be about 10^-8 absolute for results
     * less than 10^5 and 10^-8 relative for results larger than that.</p>
     *
     * @param x
     * 		the argument
     * @return digamma(x) to within 10-8 relative or absolute error whichever is smaller
     * @see <a href="http://en.wikipedia.org/wiki/Digamma_function"> Digamma at wikipedia </a>
     * @see <a href="http://www.uv.es/~bernardo/1976AppStatist.pdf"> Bernardo's original article </a>
     * @since 2.0
     */     public static double digamma(double x) {
        if ((x > 0) && (x <= (org.apache.commons.math.special.Gamma.S_LIMIT))) {
            // use method 5 from Bernardo AS103
            // accurate to O(x)
            return (-(org.apache.commons.math.special.Gamma.GAMMA)) - (1 / x);
        }

        if (x >= (org.apache.commons.math.special.Gamma.C_LIMIT)) {
            // use method 4 (accurate to O(1/x^8)
            double inv = 1 / (x * x);
            // 1       1        1         1
            // log(x) -  --- - ------ + ------- - -------
            // 2 x   12 x^2   120 x^4   252 x^6
            return ((java.lang.Math.log(x)) - (0.5 / x)) - (inv * ((1.0 / 12) + (inv * ((1.0 / 120) - (inv / 252)))));
        }

        return (org.apache.commons.math.special.Gamma.digamma((x + 1))) - (1 / x);
    }

    /**
     * <p>Computes the trigamma function of x.  This function is derived by taking the derivative of
     * the implementation of digamma.</p>
     *
     * @param x
     * 		the argument
     * @return trigamma(x) to within 10-8 relative or absolute error whichever is smaller
     * @see <a href="http://en.wikipedia.org/wiki/Trigamma_function"> Trigamma at wikipedia </a>
     * @see Gamma#digamma(double)
     * @since 2.0
     */     public static double trigamma(double x) {
        if ((x > 0) && (x <= (org.apache.commons.math.special.Gamma.S_LIMIT))) {
            return 1 / (x * x);
        }

        if (x >= (org.apache.commons.math.special.Gamma.C_LIMIT)) {
            double inv = 1 / (x * x);
            // 1    1      1       1       1
            // - + ---- + ---- - ----- + -----
            // x      2      3       5       7
            // 2 x    6 x    30 x    42 x
            return ((1 / x) + (inv / 2)) + ((inv / x) * ((1.0 / 6) - (inv * ((1.0 / 30) + (inv / 42)))));
        }

        return (org.apache.commons.math.special.Gamma.trigamma((x + 1))) + (1 / (x * x));
    }
}