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
package org.apache.commons.math.optimization.direct;









/**
 * This class implements the multi-directional direct search method.
 *
 * @version $Revision$ $Date$
 * @see NelderMead
 * @since 1.2
 */
public class MultiDirectional extends org.apache.commons.math.optimization.direct.DirectSearchOptimizer {

    /**
     * Expansion coefficient.
     */     private final double khi;
    /**
     * Contraction coefficient.
     */     private final double gamma;
    /**
     * Build a multi-directional optimizer with default coefficients.
     * <p>The default values are 2.0 for khi and 0.5 for gamma.</p>
     */     public MultiDirectional() {
        this.khi = 2.0;
        this.gamma = 0.5;
    }

    /**
     * Build a multi-directional optimizer with specified coefficients.
     *
     * @param khi
     * 		expansion coefficient
     * @param gamma
     * 		contraction coefficient
     */     public MultiDirectional(final double khi, final double gamma) {         this.khi = khi;         this.gamma = gamma;}

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected void iterateSimplex(final java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {

        final org.apache.commons.math.optimization.RealConvergenceChecker checker = getConvergenceChecker();
        while (true) {

            incrementIterationsCounter();

            // save the original vertex
            final org.apache.commons.math.optimization.RealPointValuePair[] original = simplex;
            final org.apache.commons.math.optimization.RealPointValuePair best = original[0];

            // perform a reflection step
            final org.apache.commons.math.optimization.RealPointValuePair reflected = evaluateNewSimplex(original, 1.0, comparator);
            if ((comparator.compare(reflected, best)) < 0) {

                // compute the expanded simplex
                final org.apache.commons.math.optimization.RealPointValuePair[] reflectedSimplex = simplex;
                final org.apache.commons.math.optimization.RealPointValuePair expanded = evaluateNewSimplex(original, khi, comparator);
                if ((comparator.compare(reflected, expanded)) <= 0) {
                    // accept the reflected simplex
                    simplex = reflectedSimplex;
                }

                return;

            }

            // compute the contracted simplex
            final org.apache.commons.math.optimization.RealPointValuePair contracted = evaluateNewSimplex(original, gamma, comparator);
            if ((comparator.compare(contracted, best)) < 0) {
                // accept the contracted simplex
                return;
            }

            // check convergence
            final int iter = getIterations();
            boolean converged = true;
            for (int i = 0; i < (simplex.length); ++i) {
                converged &= checker.converged(iter, original[i], simplex[i]);
            }
            if (converged) {
                return;
            }

        } 

    }

    /**
     * Compute and evaluate a new simplex.
     *
     * @param original
     * 		original simplex (to be preserved)
     * @param coeff
     * 		linear coefficient
     * @param comparator
     * 		comparator to use to sort simplex vertices from best to poorest
     * @return best point in the transformed simplex
     * @exception FunctionEvaluationException if the function cannot be evaluated at
     * some point
     * @exception OptimizationException if the maximal number of evaluations is exceeded
     */     private org.apache.commons.math.optimization.RealPointValuePair evaluateNewSimplex(final org.apache.commons.math.optimization.RealPointValuePair[] original, final double coeff, final java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        final double[] xSmallest = original[0].getPointRef();
        final int n = xSmallest.length;

        // create the linearly transformed simplex
        simplex = new org.apache.commons.math.optimization.RealPointValuePair[n + 1];
        simplex[0] = original[0];
        for (int i = 1; i <= n; ++i) {
            final double[] xOriginal = original[i].getPointRef();
            final double[] xTransformed = new double[n];
            for (int j = 0; j < n; ++j) {
                xTransformed[j] = (xSmallest[j]) + (coeff * ((xSmallest[j]) - (xOriginal[j])));
            }
            simplex[i] = new org.apache.commons.math.optimization.RealPointValuePair(xTransformed, java.lang.Double.NaN, false);
        }

        // evaluate it
        evaluateSimplex(comparator);
        return simplex[0];

    }

}