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
 * Implements the <a href="http://mathworld.wolfram.com/RombergIntegration.html">
 * Romberg Algorithm</a> for integration of real univariate functions. For
 * reference, see <b>Introduction to Numerical Analysis</b>, ISBN 038795452X,
 * chapter 3.
 * <p>
 * Romberg integration employs k successive refinements of the trapezoid
 * rule to remove error terms less than order O(N^(-2k)). Simpson's rule
 * is a special case of k = 2.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public class RombergIntegrator extends org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl {

    /**
     * Construct an integrator for the given function.
     *
     * @param f
     * 		function to integrate
     * @deprecated as of 2.0 the integrand function is passed as an argument
     * to the {@link #integrate(UnivariateRealFunction, double, double)}method.
     */     @java.lang.Deprecated
    public RombergIntegrator(org.apache.commons.math.analysis.UnivariateRealFunction f) {
        super(f, 32);
    }

    /**
     * Construct an integrator.
     */
    public RombergIntegrator() {
        super(32);
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

        final int m = (maximalIterationCount) + 1;
        double[] previousRow = new double[m];
        double[] currentRow = new double[m];

        clearResult();
        verifyInterval(min, max);
        verifyIterationCount();

        org.apache.commons.math.analysis.integration.TrapezoidIntegrator qtrap = new org.apache.commons.math.analysis.integration.TrapezoidIntegrator();
        currentRow[0] = qtrap.stage(f, min, max, 0);
        double olds = currentRow[0];
        for (int i = 1; i <= (maximalIterationCount); ++i) {

            // switch rows
            final double[] tmpRow = previousRow;
            previousRow = currentRow;
            currentRow = tmpRow;

            currentRow[0] = qtrap.stage(f, min, max, i);
            for (int j = 1; j <= i; j++) {
                // Richardson extrapolation coefficient
                final double r = (1L << (2 * j)) - 1;
                final double tIJm1 = currentRow[(j - 1)];
                currentRow[j] = tIJm1 + ((tIJm1 - (previousRow[(j - 1)])) / r);
            }
            final double s = currentRow[i];
            if (i >= (minimalIterationCount)) {
                final double delta = java.lang.Math.abs((s - olds));
                final double rLimit = ((relativeAccuracy) * ((java.lang.Math.abs(olds)) + (java.lang.Math.abs(s)))) * 0.5;
                if ((delta <= rLimit) || (delta <= (absoluteAccuracy))) {
                    setResult(s, i);
                    return result;
                }
            }
            olds = s;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(maximalIterationCount);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected void verifyIterationCount() throws java.lang.IllegalArgumentException {
        super.verifyIterationCount();
        // at most 32 bisection refinements due to higher order divider
        if ((maximalIterationCount) > 32) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.INVALID_ITERATIONS_LIMITS, 
            0, 32);
        }
    }
}