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
 * Implements the Beta distribution.
 * <p>
 * References:
 * <ul>
 * <li><a href="http://en.wikipedia.org/wiki/Beta_distribution">
 * Beta distribution</a></li>
 * </ul>
 * </p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class BetaDistributionImpl extends 
org.apache.commons.math.distribution.AbstractContinuousDistribution implements org.apache.commons.math.distribution.BetaDistribution {

    /**
     * Default inverse cumulative probability accurac
     *
     * @since 2.1
     */     public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9;

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = -1221965979403477668L;
    /**
     * First shape parameter.
     */     private double alpha;
    /**
     * Second shape parameter.
     */     private double beta;
    /**
     * Normalizing factor used in density computations.
     * updated whenever alpha or beta are changed.
     */     private double z;

    /**
     * Inverse cumulative probability accuracy
     */     private final double solverAbsoluteAccuracy;
    /**
     * Build a new instance.
     *
     * @param alpha
     * 		first shape parameter (must be positive)
     * @param beta
     * 		second shape parameter (must be positive)
     * @param inverseCumAccuracy
     * 		the maximum absolute error in inverse cumulative probability estimates
     * 		(defaults to {@link #DEFAULT_INVERSE_ABSOLUTE_ACCURACY})
     * @since 2.1
     */     public BetaDistributionImpl(double alpha, double beta, double inverseCumAccuracy) {         this.alpha = alpha;         this.beta = beta;         z = java.lang.Double.NaN;
        solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    /**
     * Build a new instance.
     *
     * @param alpha
     * 		first shape parameter (must be positive)
     * @param beta
     * 		second shape parameter (must be positive)
     */     public BetaDistributionImpl(double alpha, double beta) {         this(alpha, beta, org.apache.commons.math.distribution.BetaDistributionImpl.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);}

    /**
     * {@inheritDoc}
     *
     * @deprecated as of 2.1 (class will become immutable in 3.0)
     */     @java.lang.Deprecated     public void setAlpha(double alpha) {
        this.alpha = alpha;
        z = java.lang.Double.NaN;
    }

    /**
     * {@inheritDoc}
     */     public double getAlpha() {         return alpha;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated as of 2.1 (class will become immutable in 3.0)
     */     @java.lang.Deprecated     public void setBeta(double beta) {
        this.beta = beta;
        z = java.lang.Double.NaN;
    }

    /**
     * {@inheritDoc}
     */     public double getBeta() {         return beta;
    }

    /**
     * Recompute the normalization factor.
     */
    private void recomputeZ() {
        if (java.lang.Double.isNaN(z)) {
            z = ((org.apache.commons.math.special.Gamma.logGamma(alpha)) + (org.apache.commons.math.special.Gamma.logGamma(beta))) - (org.apache.commons.math.special.Gamma.logGamma(((alpha) + (beta))));
        }
    }

    /**
     * Return the probability density for a particular point.
     *
     * @param x
     * 		The point at which the density should be computed.
     * @return The pdf at point x.
     * @deprecated 
     */     public double density(java.lang.Double x) {
        return density(x.doubleValue());
    }

    /**
     * Return the probability density for a particular point.
     *
     * @param x
     * 		The point at which the density should be computed.
     * @return The pdf at point x.
     * @since 2.1
     */     public double density(double x) {
        recomputeZ();
        if ((x < 0) || (x > 1)) {
            return 0;
        }else             if (x == 0) {
                if ((alpha) < 1) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_0_FOR_SOME_ALPHA, alpha);
                }
                return 0;
            }else                 if (x == 1) {
                    if ((beta) < 1) {
                        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                        org.apache.commons.math.util.LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_1_FOR_SOME_BETA, beta);
                    }
                    return 0;
                }else {
                    double logX = java.lang.Math.log(x);
                    double log1mX = java.lang.Math.log1p((-x));
                    return java.lang.Math.exp((((((alpha) - 1) * logX) + (((beta) - 1) * log1mX)) - (z)));
                }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double inverseCumulativeProbability(double p) throws org.apache.commons.math.MathException {
        if (p == 0) {
            return 0;
        }else             if (p == 1) {
                return 1;
            }else {
                return super.inverseCumulativeProbability(p);
            }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected double getInitialDomain(double p) {
        return p;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected double getDomainLowerBound(double p) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected double getDomainUpperBound(double p) {
        return 1;
    }

    /**
     * {@inheritDoc}
     */     public double cumulativeProbability(double x) throws org.apache.commons.math.MathException {         if (x <= 0) {
            return 0;
        }else             if (x >= 1) {
                return 1;
            }else {
                return org.apache.commons.math.special.Beta.regularizedBeta(x, alpha, beta);
            }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double cumulativeProbability(double x0, double x1) throws org.apache.commons.math.MathException {
        return (cumulativeProbability(x1)) - (cumulativeProbability(x0));
    }

    /**
     * Return the absolute accuracy setting of the solver used to estimate
     * inverse cumulative probabilities.
     *
     * @return the solver absolute accuracy
     * @since 2.1
     */
    @java.lang.Override
    protected double getSolverAbsoluteAccuracy() {
        return solverAbsoluteAccuracy;
    }
}