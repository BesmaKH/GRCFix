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
package org.apache.commons.math.optimization;











/**
 * Special implementation of the {@link DifferentiableMultivariateRealOptimizer} interface adding
 * multi-start features to an existing optimizer.
 * <p>
 * This class wraps a classical optimizer to use it several times in
 * turn with different starting points in order to avoid being trapped
 * into a local extremum when looking for a global one.
 * </p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class MultiStartDifferentiableMultivariateRealOptimizer implements 
org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer {

    /**
     * Underlying classical optimizer.
     */     private final org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer optimizer;
    /**
     * Maximal number of iterations allowed.
     */     private int maxIterations;
    /**
     * Number of iterations already performed for all starts.
     */     private int totalIterations;
    /**
     * Maximal number of evaluations allowed.
     */     private int maxEvaluations;
    /**
     * Number of evaluations already performed for all starts.
     */     private int totalEvaluations;
    /**
     * Number of gradient evaluations already performed for all starts.
     */     private int totalGradientEvaluations;
    /**
     * Number of starts to go.
     */     private int starts;
    /**
     * Random generator for multi-start.
     */     private org.apache.commons.math.random.RandomVectorGenerator generator;
    /**
     * Found optima.
     */     private org.apache.commons.math.optimization.RealPointValuePair[] optima;
    /**
     * Create a multi-start optimizer from a single-start optimizer
     *
     * @param optimizer
     * 		single-start optimizer to wrap
     * @param starts
     * 		number of starts to perform (including the
     * 		first one), multi-start is disabled if value is less than or
     * 		equal to 1
     * @param generator
     * 		random vector generator to use for restarts
     */     public MultiStartDifferentiableMultivariateRealOptimizer(final org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer optimizer, final int starts, final org.apache.commons.math.random.RandomVectorGenerator generator) {         this.optimizer = optimizer;
        this.totalIterations = 0;
        this.totalEvaluations = 0;
        this.totalGradientEvaluations = 0;
        this.starts = starts;
        this.generator = generator;
        this.optima = null;
        setMaxIterations(java.lang.Integer.MAX_VALUE);
        setMaxEvaluations(java.lang.Integer.MAX_VALUE);
    }

    /**
     * Get all the optima found during the last call to {@link
     * #optimize(DifferentiableMultivariateRealFunction, GoalType, double[])
     * optimize}.
     * <p>The optimizer stores all the optima found during a set of
     * restarts. The {@link #optimize(DifferentiableMultivariateRealFunction,
     * GoalType, double[]) optimize} method returns the best point only. This
     * method returns all the points found at the end of each starts,
     * including the best one already returned by the {@link
     * #optimize(DifferentiableMultivariateRealFunction, GoalType, double[])
     * optimize} method.
     * </p>
     * <p>
     * The returned array as one element for each start as specified
     * in the constructor. It is ordered with the results from the
     * runs that did converge first, sorted from best to worst
     * objective value (i.e in ascending order if minimizing and in
     * descending order if maximizing), followed by and null elements
     * corresponding to the runs that did not converge. This means all
     * elements will be null if the {@link #optimize(DifferentiableMultivariateRealFunction,
     * GoalType, double[]) optimize} method did throw a {@link
     * org.apache.commons.math.ConvergenceException ConvergenceException}).
     * This also means that if the first element is non null, it is the best
     * point found across all starts.</p>
     *
     * @return array containing the optima
     * @exception IllegalStateException if {@link #optimize(DifferentiableMultivariateRealFunction,
     * GoalType, double[]) optimize} has not been called
     */     public org.apache.commons.math.optimization.RealPointValuePair[] getOptima() throws java.lang.IllegalStateException {         if ((optima) == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.util.LocalizedFormats.NO_OPTIMUM_COMPUTED_YET);
        }
        return optima.clone();
    }

    /**
     * {@inheritDoc}
     */     public void setMaxIterations(int maxIterations) {         this.maxIterations = maxIterations;
    }

    /**
     * {@inheritDoc}
     */     public int getMaxIterations() {         return maxIterations;
    }

    /**
     * {@inheritDoc}
     */     public int getIterations() {         return totalIterations;
    }

    /**
     * {@inheritDoc}
     */     public void setMaxEvaluations(int maxEvaluations) {         this.maxEvaluations = maxEvaluations;
    }

    /**
     * {@inheritDoc}
     */     public int getMaxEvaluations() {         return maxEvaluations;
    }

    /**
     * {@inheritDoc}
     */     public int getEvaluations() {         return totalEvaluations;
    }

    /**
     * {@inheritDoc}
     */     public int getGradientEvaluations() {         return totalGradientEvaluations;
    }

    /**
     * {@inheritDoc}
     */     public void setConvergenceChecker(org.apache.commons.math.optimization.RealConvergenceChecker checker) {         optimizer.setConvergenceChecker(checker);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.optimization.RealConvergenceChecker getConvergenceChecker() {         return optimizer.getConvergenceChecker();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.optimization.RealPointValuePair optimize(final org.apache.commons.math.analysis.DifferentiableMultivariateRealFunction f, final org.apache.commons.math.optimization.GoalType goalType, 
    double[] startPoint) throws 
    org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {

        optima = new org.apache.commons.math.optimization.RealPointValuePair[starts];
        totalIterations = 0;
        totalEvaluations = 0;
        totalGradientEvaluations = 0;

        // multi-start loop
        for (int i = 0; i < (starts); ++i) {

            try {
                optimizer.setMaxIterations(((maxIterations) - (totalIterations)));
                optimizer.setMaxEvaluations(((maxEvaluations) - (totalEvaluations)));
                optima[i] = optimizer.optimize(f, goalType, 
                (i == 0 ? startPoint : generator.nextVector()));
            } catch (org.apache.commons.math.FunctionEvaluationException fee) {
                optima[i] = null;
            } catch (org.apache.commons.math.optimization.OptimizationException oe) {
                optima[i] = null;
            }

            totalIterations += optimizer.getIterations();
            totalEvaluations += optimizer.getEvaluations();
            totalGradientEvaluations += optimizer.getGradientEvaluations();

        }

        // sort the optima from best to worst, followed by null elements
        java.util.Arrays.sort(optima, new java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair>() {
            public int compare(final org.apache.commons.math.optimization.RealPointValuePair o1, final org.apache.commons.math.optimization.RealPointValuePair o2) {
                if (o1 == null) {
                    return o2 == null ? 0 : +1;
                }else                     if (o2 == null) {
                        return -1;
                    }
                final double v1 = o1.getValue();
                final double v2 = o2.getValue();
                return goalType == (org.apache.commons.math.optimization.GoalType.MINIMIZE) ? 
                java.lang.Double.compare(v1, v2) : java.lang.Double.compare(v2, v1);
            }
        });

        if ((optima[0]) == null) {
            throw new org.apache.commons.math.optimization.OptimizationException(
            org.apache.commons.math.util.LocalizedFormats.NO_CONVERGENCE_WITH_ANY_START_POINT, 
            starts);
        }

        // return the found point given the best objective function value
        return optima[0];

    }

}