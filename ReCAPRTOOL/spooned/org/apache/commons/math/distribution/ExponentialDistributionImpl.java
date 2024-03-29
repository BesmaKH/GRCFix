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
 * The default implementation of {@link ExponentialDistribution}.
 *
 * @version $Revision$ $Date$
 */
public class ExponentialDistributionImpl extends org.apache.commons.math.distribution.AbstractContinuousDistribution implements 
java.io.Serializable , org.apache.commons.math.distribution.ExponentialDistribution {

    /**
     * Default inverse cumulative probability accuracy
     *
     * @since 2.1
     */     public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9;

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 2401296428283614780L;
    /**
     * The mean of this distribution.
     */     private double mean;
    /**
     * Inverse cumulative probability accuracy
     */     private final double solverAbsoluteAccuracy;
    /**
     * Create a exponential distribution with the given mean.
     *
     * @param mean
     * 		mean of this distribution.
     */     public ExponentialDistributionImpl(double mean) {         this(mean, org.apache.commons.math.distribution.ExponentialDistributionImpl.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    /**
     * Create a exponential distribution with the given mean.
     *
     * @param mean
     * 		mean of this distribution.
     * @param inverseCumAccuracy
     * 		the maximum absolute error in inverse cumulative probability estimates
     * 		(defaults to {@link #DEFAULT_INVERSE_ABSOLUTE_ACCURACY})
     * @since 2.1
     */     public ExponentialDistributionImpl(double mean, double inverseCumAccuracy) {         super();         setMeanInternal(mean);
        solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    /**
     * Modify the mean.
     *
     * @param mean
     * 		the new mean.
     * @throws IllegalArgumentException
     * 		if <code>mean</code> is not positive.
     * @deprecated as of 2.1 (class will become immutable in 3.0)
     */     @java.lang.Deprecated     public void setMean(double mean) {         setMeanInternal(mean);
    }
    /**
     * Modify the mean.
     *
     * @param newMean
     * 		the new mean.
     * @throws IllegalArgumentException
     * 		if <code>newMean</code> is not positive.
     */     private void setMeanInternal(double newMean) {         if (newMean <= 0.0) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_POSITIVE_MEAN, newMean);
        }
        this.mean = newMean;
    }

    /**
     * Access the mean.
     *
     * @return the mean.
     */     public double getMean() {
        return mean;
    }

    /**
     * Return the probability density for a particular point.
     *
     * @param x
     * 		The point at which the density should be computed.
     * @return The pdf at point x.
     * @deprecated - use density(double)
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
     */     @java.lang.Override
    public double density(double x) {
        if (x < 0) {
            return 0;
        }
        return (java.lang.Math.exp(((-x) / (mean)))) / (mean);
    }

    /**
     * For this distribution, X, this method returns P(X &lt; x).
     *
     * The implementation of this method is based on:
     * <ul>
     * <li>
     * <a href="http://mathworld.wolfram.com/ExponentialDistribution.html">
     * Exponential Distribution</a>, equation (1).</li>
     * </ul>
     *
     * @param x
     * 		the value at which the CDF is evaluated.
     * @return CDF for this distribution.
     * @throws MathException
     * 		if the cumulative probability can not be
     * 		computed due to convergence or other numerical errors.
     */     public double cumulativeProbability(double x) throws org.apache.commons.math.MathException {         double ret;
        if (x <= 0.0) {
            ret = 0.0;
        }else {
            ret = 1.0 - (java.lang.Math.exp(((-x) / (mean))));
        }
        return ret;
    }

    /**
     * For this distribution, X, this method returns the critical point x, such
     * that P(X &lt; x) = <code>p</code>.
     * <p>
     * Returns 0 for p=0 and <code>Double.POSITIVE_INFINITY</code> for p=1.</p>
     *
     * @param p
     * 		the desired probability
     * @return x, such that P(X &lt; x) = <code>p</code>
     * @throws MathException
     * 		if the inverse cumulative probability can not be
     * 		computed due to convergence or other numerical errors.
     * @throws IllegalArgumentException
     * 		if p < 0 or p > 1.
     */     @java.lang.Override     public double inverseCumulativeProbability(double p) throws org.apache.commons.math.MathException {         double ret;

        if ((p < 0.0) || (p > 1.0)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, p, 0.0, 1.0);
        }else             if (p == 1.0) {
                ret = java.lang.Double.POSITIVE_INFINITY;
            }else {
                ret = (-(mean)) * (java.lang.Math.log((1.0 - p)));
            }

        return ret;
    }

    /**
     * Generates a random value sampled from this distribution.
     *
     * <p><strong>Algorithm Description</strong>: Uses the <a
     * href="http://www.jesus.ox.ac.uk/~clifford/a5/chap1/node5.html"> Inversion
     * Method</a> to generate exponentially distributed random values from
     * uniform deviates. </p>
     *
     * @return random value
     * @since 2.2
     * @throws MathException
     * 		if an error occurs generating the random value
     */     @java.lang.Override
    public double sample() throws org.apache.commons.math.MathException {
        return randomData.nextExponential(mean);
    }

    /**
     * Access the domain value lower bound, based on <code>p</code>, used to
     * bracket a CDF root.
     *
     * @param p
     * 		the desired probability for the critical value
     * @return domain value lower bound, i.e.
     * P(X &lt; <i>lower bound</i>) &lt; <code>p</code>
     */     @java.lang.Override
    protected double getDomainLowerBound(double p) {
        return 0;
    }

    /**
     * Access the domain value upper bound, based on <code>p</code>, used to
     * bracket a CDF root.
     *
     * @param p
     * 		the desired probability for the critical value
     * @return domain value upper bound, i.e.
     * P(X &lt; <i>upper bound</i>) &gt; <code>p</code>
     */     @java.lang.Override
    protected double getDomainUpperBound(double p) {
        // NOTE: exponential is skewed to the left
        // NOTE: therefore, P(X < &mu;) > .5
        if (
        p < 0.5) {
            // use mean
            return mean;
        }else {
            // use max
            return java.lang.Double.MAX_VALUE;
        }
    }

    /**
     * Access the initial domain value, based on <code>p</code>, used to
     * bracket a CDF root.
     *
     * @param p
     * 		the desired probability for the critical value
     * @return initial domain value
     */     @java.lang.Override
    protected double getInitialDomain(double p) {
        // TODO: try to improve on this estimate
        // TODO: what should really happen here is not derive from AbstractContinuousDistribution
        // TODO: because the inverse cumulative distribution is simple.
        // Exponential is skewed to the left, therefore, P(X < &mu;) > .5
        if (p < 0.5) {
            // use 1/2 mean
            return (mean) * 0.5;
        }else {
            // use mean
            return mean;
        }
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