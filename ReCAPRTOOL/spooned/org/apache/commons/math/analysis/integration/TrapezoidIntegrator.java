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
package org.apache.commons.math.analysis.integration;







/**
 * Implements the <a href="http://mathworld.wolfram.com/TrapezoidalRule.html">
 * Trapezoidal Rule</a> for integration of real univariate functions. For
 * reference, see <b>Introduction to Numerical Analysis</b>, ISBN 038795452X,
 * chapter 3.
 * <p>
 * The function should be integrable.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public class TrapezoidIntegrator extends org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl {

    /**
     * Intermediate result.
     */     private double s;
    /**
     * Construct an integrator for the given function.
     *
     * @param f
     * 		function to integrate
     * @deprecated as of 2.0 the integrand function is passed as an argument
     * to the {@link #integrate(UnivariateRealFunction, double, double)}method.
     */     @java.lang.Deprecated
    public TrapezoidIntegrator(org.apache.commons.math.analysis.UnivariateRealFunction f) {
        super(f, 64);
    }

    /**
     * Construct an integrator.
     */
    public TrapezoidIntegrator() {
        super(64);
    }

    /**
     * Compute the n-th stage integral of trapezoid rule. This function
     * should only be called by API <code>integrate()</code> in the package.
     * To save time it does not verify arguments - caller does.
     * <p>
     * The interval is divided equally into 2^n sections rather than an
     * arbitrary m sections because this configuration can best utilize the
     * alrealy computed values.</p>
     *
     * @param f
     * 		the integrand function
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param n
     * 		the stage of 1/2 refinement, n = 0 is no refinement
     * @return the value of n-th stage integral
     * @throws FunctionEvaluationException
     * 		if an error occurs evaluating the
     * 		function
     */     double stage(final org.apache.commons.math.analysis.UnivariateRealFunction f, final double min, final double max, final int n) throws org.apache.commons.math.FunctionEvaluationException {         if (n == 0) {
            s = (0.5 * (max - min)) * ((f.value(min)) + (f.value(max)));
            return s;
        }else {
            final long np = 1L << (n - 1);// number of new points in this stage
            double sum = 0;
            final double spacing = (max - min) / np;// spacing between adjacent new points
            double x = min + (0.5 * spacing);// the first new point
            for (long i = 0; i < np; i++) {
                sum += f.value(x);
                x += spacing;
            }
            // add the new sum to previously calculated result
            s = 0.5 * ((s) + (sum * spacing));
            return s;
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Deprecated     public double integrate(final double min, final double max) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.MaxIterationsExceededException {
        return integrate(f, min, max);
    }

    /**
     * {@inheritDoc}
     */     public double integrate(final org.apache.commons.math.analysis.UnivariateRealFunction f, final double min, final double max) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.MaxIterationsExceededException {

        clearResult();
        verifyInterval(min, max);
        verifyIterationCount();

        double oldt = stage(f, min, max, 0);
        for (int i = 1; i <= (maximalIterationCount); ++i) {
            final double t = stage(f, min, max, i);
            if (i >= (minimalIterationCount)) {
                final double delta = java.lang.Math.abs((t - oldt));
                final double rLimit = 
                ((relativeAccuracy) * ((java.lang.Math.abs(oldt)) + (java.lang.Math.abs(t)))) * 0.5;
                if ((delta <= rLimit) || (delta <= (absoluteAccuracy))) {
                    setResult(t, i);
                    return result;
                }
            }
            oldt = t;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(maximalIterationCount);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected void verifyIterationCount() throws java.lang.IllegalArgumentException {
        super.verifyIterationCount();
        // at most 64 bisection refinements
        if ((maximalIterationCount) > 64) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INVALID_ITERATIONS_LIMITS, 
            0, 64);
        }
    }
}