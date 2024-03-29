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
 * Default implementation of
 * {@link org.apache.commons.math.distribution.FDistribution}.
 *
 * @version $Revision$ $Date$
 */
public class FDistributionImpl extends 
org.apache.commons.math.distribution.AbstractContinuousDistribution implements 
java.io.Serializable , org.apache.commons.math.distribution.FDistribution {

    /**
     * Default inverse cumulative probability accuracy
     *
     * @since 2.1
     */     public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9;

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -8516354193418641566L;
    /**
     * The numerator degrees of freedom
     */     private double numeratorDegreesOfFreedom;
    /**
     * The numerator degrees of freedom
     */     private double denominatorDegreesOfFreedom;
    /**
     * Inverse cumulative probability accuracy
     */     private final double solverAbsoluteAccuracy;
    /**
     * Create a F distribution using the given degrees of freedom.
     *
     * @param numeratorDegreesOfFreedom
     * 		the numerator degrees of freedom.
     * @param denominatorDegreesOfFreedom
     * 		the denominator degrees of freedom.
     */     public FDistributionImpl(double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom) {         this(numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, org.apache.commons.math.distribution.FDistributionImpl.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    /**
     * Create a F distribution using the given degrees of freedom and inverse cumulative probability accuracy.
     *
     * @param numeratorDegreesOfFreedom
     * 		the numerator degrees of freedom.
     * @param denominatorDegreesOfFreedom
     * 		the denominator degrees of freedom.
     * @param inverseCumAccuracy
     * 		the maximum absolute error in inverse cumulative probability estimates
     * 		(defaults to {@link #DEFAULT_INVERSE_ABSOLUTE_ACCURACY})
     * @since 2.1
     */     public FDistributionImpl(double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom, double inverseCumAccuracy) {         super();         setNumeratorDegreesOfFreedomInternal(numeratorDegreesOfFreedom);
        setDenominatorDegreesOfFreedomInternal(denominatorDegreesOfFreedom);
        solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    /**
     * Returns the probability density for a particular point.
     *
     * @param x
     * 		The point at which the density should be computed.
     * @return The pdf at point x.
     * @since 2.1
     */     @java.lang.Override
    public double density(double x) {
        final double nhalf = (numeratorDegreesOfFreedom) / 2;
        final double mhalf = (denominatorDegreesOfFreedom) / 2;
        final double logx = java.lang.Math.log(x);
        final double logn = java.lang.Math.log(numeratorDegreesOfFreedom);
        final double logm = java.lang.Math.log(denominatorDegreesOfFreedom);
        final double lognxm = java.lang.Math.log((((numeratorDegreesOfFreedom) * x) + (denominatorDegreesOfFreedom)));
        return java.lang.Math.exp((((((((nhalf * logn) + (nhalf * logx)) - logx) + (mhalf * logm)) - (nhalf * lognxm)) - 
        (mhalf * lognxm)) - (org.apache.commons.math.special.Beta.logBeta(nhalf, mhalf))));
    }

    /**
     * For this distribution, X, this method returns P(X &lt; x).
     *
     * The implementation of this method is based on:
     * <ul>
     * <li>
     * <a href="http://mathworld.wolfram.com/F-Distribution.html">
     * F-Distribution</a>, equation (4).</li>
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
            double n = numeratorDegreesOfFreedom;
            double m = denominatorDegreesOfFreedom;

            ret = org.apache.commons.math.special.Beta.regularizedBeta(((n * x) / (m + (n * x))), 
            (0.5 * n), 
            (0.5 * m));
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
     * 		if <code>p</code> is not a valid
     * 		probability.
     */     @java.lang.Override     public double inverseCumulativeProbability(final double p) throws org.apache.commons.math.MathException {
        if (p == 0) {
            return 0.0;
        }
        if (p == 1) {
            return java.lang.Double.POSITIVE_INFINITY;
        }
        return super.inverseCumulativeProbability(p);
    }

    /**
     * Access the domain value lower bound, based on <code>p</code>, used to
     * bracket a CDF root.  This method is used by
     * {@link #inverseCumulativeProbability(double)} to find critical values.
     *
     * @param p
     * 		the desired probability for the critical value
     * @return domain value lower bound, i.e.
     * P(X &lt; <i>lower bound</i>) &lt; <code>p</code>
     */     @java.lang.Override
    protected double getDomainLowerBound(double p) {
        return 0.0;
    }

    /**
     * Access the domain value upper bound, based on <code>p</code>, used to
     * bracket a CDF root.  This method is used by
     * {@link #inverseCumulativeProbability(double)} to find critical values.
     *
     * @param p
     * 		the desired probability for the critical value
     * @return domain value upper bound, i.e.
     * P(X &lt; <i>upper bound</i>) &gt; <code>p</code>
     */     @java.lang.Override
    protected double getDomainUpperBound(double p) {
        return java.lang.Double.MAX_VALUE;
    }

    /**
     * Access the initial domain value, based on <code>p</code>, used to
     * bracket a CDF root.  This method is used by
     * {@link #inverseCumulativeProbability(double)} to find critical values.
     *
     * @param p
     * 		the desired probability for the critical value
     * @return initial domain value
     */     @java.lang.Override
    protected double getInitialDomain(double p) {
        double ret = 1.0;
        double d = denominatorDegreesOfFreedom;
        if (d > 2.0) {
            // use mean
            ret = d / (d - 2.0);
        }
        return ret;
    }

    /**
     * Modify the numerator degrees of freedom.
     *
     * @param degreesOfFreedom
     * 		the new numerator degrees of freedom.
     * @throws IllegalArgumentException
     * 		if <code>degreesOfFreedom</code> is not
     * 		positive.
     * @deprecated as of 2.1 (class will become immutable in 3.0)
     */     @java.lang.Deprecated     public void setNumeratorDegreesOfFreedom(double degreesOfFreedom) {         setNumeratorDegreesOfFreedomInternal(degreesOfFreedom);
    }

    /**
     * Modify the numerator degrees of freedom.
     *
     * @param degreesOfFreedom
     * 		the new numerator degrees of freedom.
     * @throws IllegalArgumentException
     * 		if <code>degreesOfFreedom</code> is not
     * 		positive.
     */     private void setNumeratorDegreesOfFreedomInternal(double degreesOfFreedom) {         if (degreesOfFreedom <= 0.0) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_POSITIVE_DEGREES_OF_FREEDOM, degreesOfFreedom);
        }
        this.numeratorDegreesOfFreedom = degreesOfFreedom;
    }

    /**
     * Access the numerator degrees of freedom.
     *
     * @return the numerator degrees of freedom.
     */     public double getNumeratorDegreesOfFreedom() {
        return numeratorDegreesOfFreedom;
    }

    /**
     * Modify the denominator degrees of freedom.
     *
     * @param degreesOfFreedom
     * 		the new denominator degrees of freedom.
     * @throws IllegalArgumentException
     * 		if <code>degreesOfFreedom</code> is not
     * 		positive.
     * @deprecated as of 2.1 (class will become immutable in 3.0)
     */     @java.lang.Deprecated     public void setDenominatorDegreesOfFreedom(double degreesOfFreedom) {         setDenominatorDegreesOfFreedomInternal(degreesOfFreedom);
    }

    /**
     * Modify the denominator degrees of freedom.
     *
     * @param degreesOfFreedom
     * 		the new denominator degrees of freedom.
     * @throws IllegalArgumentException
     * 		if <code>degreesOfFreedom</code> is not
     * 		positive.
     */     private void setDenominatorDegreesOfFreedomInternal(double degreesOfFreedom) {         if (degreesOfFreedom <= 0.0) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_POSITIVE_DEGREES_OF_FREEDOM, degreesOfFreedom);
        }
        this.denominatorDegreesOfFreedom = degreesOfFreedom;
    }

    /**
     * Access the denominator degrees of freedom.
     *
     * @return the denominator degrees of freedom.
     */     public double getDenominatorDegreesOfFreedom() {
        return denominatorDegreesOfFreedom;
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