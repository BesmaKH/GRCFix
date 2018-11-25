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
package org.apache.commons.math.distribution;




/**
 * <p>
 * Utility class used by various distributions to accurately compute their
 * respective probability mass functions. The implementation for this class is
 * based on the Catherine Loader's <a target="_blank"
 * href="http://www.herine.net/stat/software/dbinom.html">dbinom</a> routines.
 * </p>
 * <p>
 * This class is not intended to be called directly.
 * </p>
 * <p>
 * References:
 * <ol>
 * <li>Catherine Loader (2000). "Fast and Accurate Computation of Binomial
 * Probabilities.". <a target="_blank"
 * href="http://www.herine.net/stat/papers/dbinom.pdf">
 * http://www.herine.net/stat/papers/dbinom.pdf</a></li>
 * </ol>
 * </p>
 *
 * @since 2.1
 * @version $Revision$ $Date$
 */
final class SaddlePointExpansion {

    /**
     * 1/2 * log(2 &#960;).
     */     private static final double HALF_LOG_2_PI = 0.5 * (java.lang.Math.log(org.apache.commons.math.util.MathUtils.TWO_PI));
    /**
     * exact Stirling expansion error for certain values.
     */     private static final double[] EXACT_STIRLING_ERRORS = new double[]{ 0.0/* 0.0 */
    , 0.15342640972002736/* 0.5 */
    , 0.08106146679532726/* 1.0 */
    , 0.05481412105191765/* 1.5 */
    , 0.0413406959554093/* 2.0 */
    , 0.03316287351993629/* 2.5 */
    , 0.02767792568499834/* 3.0 */
    , 0.023746163656297496/* 3.5 */
    , 0.020790672103765093/* 4.0 */
    , 0.018488450532673187/* 4.5 */
    , 0.016644691189821193/* 5.0 */
    , 0.015134973221917378/* 5.5 */
    , 0.013876128823070748/* 6.0 */
    , 0.012810465242920227/* 6.5 */
    , 0.01189670994589177/* 7.0 */
    , 0.011104559758206917/* 7.5 */
    , 0.010411265261972096/* 8.0 */
    , 0.009799416126158804/* 8.5 */
    , 0.009255462182712733/* 9.0 */
    , 0.008768700134139386/* 9.5 */
    , 0.00833056343336287/* 10.0 */
    , 0.00793411456431402/* 10.5 */
    , 0.007573675487951841/* 11.0 */
    , 0.007244554301320383/* 11.5 */
    , 0.00694284010720953/* 12.0 */
    , 0.006665247032707682/* 12.5 */
    , 0.006408994188004207/* 13.0 */
    , 0.006171712263039458/* 13.5 */
    , 0.0059513701127588475/* 14.0 */
    , 0.0057462165130101155/* 14.5 */
    , 0.005554733551962801/* 15.0 */
     };
    /**
     * Default constructor.
     */
    private SaddlePointExpansion() {
        super();
    }

    /**
     * Compute the error of Stirling's series at the given value.
     * <p>
     * References:
     * <ol>
     * <li>Eric W. Weisstein. "Stirling's Series." From MathWorld--A Wolfram Web
     * Resource. <a target="_blank"
     * href="http://mathworld.wolfram.com/StirlingsSeries.html">
     * http://mathworld.wolfram.com/StirlingsSeries.html</a></li>
     * </ol>
     * </p>
     *
     * @param z
     * 		the value.
     * @return the Striling's series error.
     */     static double getStirlingError(double z) {
        double ret;
        if (z < 15.0) {
            double z2 = 2.0 * z;
            if ((java.lang.Math.floor(z2)) == z2) {
                ret = org.apache.commons.math.distribution.SaddlePointExpansion.EXACT_STIRLING_ERRORS[((int) (z2))];
            }else {
                ret = (((org.apache.commons.math.special.Gamma.logGamma((z + 1.0))) - ((z + 0.5) * (java.lang.Math.log(z)))) + 
                z) - (org.apache.commons.math.distribution.SaddlePointExpansion.HALF_LOG_2_PI);
            }
        }else {
            double z2 = z * z;
            ret = (0.08333333333333333 - 
            ((0.002777777777777778 - 
            ((7.936507936507937E-4 - 
            ((5.952380952380953E-4 - 
            (8.417508417508417E-4 / 
            z2)) / z2)) / z2)) / z2)) / z;
        }
        return ret;
    }

    /**
     * A part of the deviance portion of the saddle point approximation.
     * <p>
     * References:
     * <ol>
     * <li>Catherine Loader (2000). "Fast and Accurate Computation of Binomial
     * Probabilities.". <a target="_blank"
     * href="http://www.herine.net/stat/papers/dbinom.pdf">
     * http://www.herine.net/stat/papers/dbinom.pdf</a></li>
     * </ol>
     * </p>
     *
     * @param x
     * 		the x value.
     * @param mu
     * 		the average.
     * @return a part of the deviance.
     */     static double getDeviancePart(double x, double mu) {         double ret;
        if ((java.lang.Math.abs((x - mu))) < (0.1 * (x + mu))) {
            double d = x - mu;
            double v = d / (x + mu);
            double s1 = v * d;
            double s = java.lang.Double.NaN;
            double ej = (2.0 * x) * v;
            v = v * v;
            int j = 1;
            while (s1 != s) {
                s = s1;
                ej *= v;
                s1 = s + (ej / ((j * 2) + 1));
                ++j;
            } 
            ret = s1;
        }else {
            ret = ((x * (java.lang.Math.log((x / mu)))) + mu) - x;
        }
        return ret;
    }

    /**
     * Compute the PMF for a binomial distribution using the saddle point
     * expansion.
     *
     * @param x
     * 		the value at which the probability is evaluated.
     * @param n
     * 		the number of trials.
     * @param p
     * 		the probability of success.
     * @param q
     * 		the probability of failure (1 - p).
     * @return log(p(x)).
     */     static double logBinomialProbability(int x, int n, double p, double q) {         double ret;         if (x == 0) {             if (p < 0.1) {
                ret = (-(org.apache.commons.math.distribution.SaddlePointExpansion.getDeviancePart(n, (n * q)))) - (n * p);
            }else {
                ret = n * (java.lang.Math.log(q));
            }
        }else             if (x == n) {
                if (q < 0.1) {
                    ret = (-(org.apache.commons.math.distribution.SaddlePointExpansion.getDeviancePart(n, (n * p)))) - (n * q);
                }else {
                    ret = n * (java.lang.Math.log(p));
                }
            }else {
                ret = ((((org.apache.commons.math.distribution.SaddlePointExpansion.getStirlingError(n)) - (org.apache.commons.math.distribution.SaddlePointExpansion.getStirlingError(x))) - 
                (org.apache.commons.math.distribution.SaddlePointExpansion.getStirlingError((n - x)))) - (org.apache.commons.math.distribution.SaddlePointExpansion.getDeviancePart(x, (n * p)))) - 
                (org.apache.commons.math.distribution.SaddlePointExpansion.getDeviancePart((n - x), (n * q)));
                double f = (((org.apache.commons.math.util.MathUtils.TWO_PI) * x) * (n - x)) / n;
                ret = ((-0.5) * (java.lang.Math.log(f))) + ret;
            }
        return ret;
    }
}