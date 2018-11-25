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
package org.apache.commons.math.optimization.general;














/**
 * Base class for implementing optimizers for multivariate scalar functions.
 * This base class handles the boiler-plate methods associated to thresholds
 * settings, iterations and evaluations counting.
 * This class is mainly intended to enforce the internal coherence of
 * Commons-Math.
 * A class that implements an optimization algorithm should inherit from
 * {@link AbstractScalarOptimizer} or from
 * {@link AbstractScalarDifferentiableOptimizer}.
 *
 * @version $Revision$ $Date$
 * @since 2.2
 */
public abstract class BaseAbstractScalarOptimizer<T extends org.apache.commons.math.analysis.MultivariateRealFunction> implements 
org.apache.commons.math.optimization.BaseMultivariateRealOptimizer<T> {
    /**
     * Default maximal number of iterations allowed ({@value}).
     */     public static final int DEFAULT_MAX_ITERATIONS = 1000;     /**
     * Default maximal number of iterations allowed ({@value}).
     */     public static final int DEFAULT_MAX_EVALUATIONS = 10000;
    /**
     * Convergence checker.
     */     private org.apache.commons.math.optimization.RealConvergenceChecker checker;     /**
     * Type of optimization.
     */     private org.apache.commons.math.optimization.GoalType goal;     /**
     * Initial guess.
     */     private double[] start;     /**
     * Maximal number of iterations allowed.
     */     private int maxIterations;     /**
     * Number of iterations already performed.
     */     private int iterations;     /**
     * Maximal number of evaluations allowed.
     */     private int maxEvaluations;     /**
     * Number of evaluations already performed.
     */     private int evaluations;     /**
     * Objective function.
     */     private org.apache.commons.math.analysis.MultivariateRealFunction function;
    /**
     * Simple constructor with default settings.
     * The convergence check is set to a {@link SimpleScalarValueChecker},
     * the allowed number of iterations and evaluations are set to their
     * default values.
     */
    protected BaseAbstractScalarOptimizer() {
        this(new org.apache.commons.math.optimization.SimpleScalarValueChecker(), 
        org.apache.commons.math.optimization.general.BaseAbstractScalarOptimizer.DEFAULT_MAX_ITERATIONS, 
        org.apache.commons.math.optimization.general.BaseAbstractScalarOptimizer.DEFAULT_MAX_EVALUATIONS);
    }
    /**
     *
     *
     * @param convergenceChecker
     * 		Convergence checker.
     * @param maxIterations
     * 		Maximum number of iterations.
     * @param maxEvaluations
     * 		Maximum number of evaluations.
     */     protected BaseAbstractScalarOptimizer(org.apache.commons.math.optimization.RealConvergenceChecker checker, int maxIterations, int maxEvaluations) {         this.checker = checker;         this.maxIterations = maxIterations;
        this.maxEvaluations = maxEvaluations;
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
     */     public int getIterations() {         return iterations;
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
     */     public int getEvaluations() {         return evaluations;
    }

    /**
     * {@inheritDoc}
     */     public void setConvergenceChecker(org.apache.commons.math.optimization.RealConvergenceChecker checker) {         this.checker = checker;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.optimization.RealConvergenceChecker getConvergenceChecker() {         return checker;
    }

    /**
     * Increment the iterations counter by 1.
     *
     * @throws OptimizationException
     * 		if the maximal number
     * 		of iterations is exceeded
     */     protected void incrementIterationsCounter() throws org.apache.commons.math.optimization.OptimizationException {
        if ((++(iterations)) > (maxIterations)) {
            throw new org.apache.commons.math.optimization.OptimizationException(new org.apache.commons.math.MaxIterationsExceededException(maxIterations));
        }
    }

    /**
     * Compute the objective function value.
     *
     * @param evaluationPoint
     * 		point at which the objective function must be evaluated
     * @return objective function value at specified point
     * @throws FunctionEvaluationException
     * 		if the function cannot be evaluated
     * 		or its dimension doesn't match problem dimension or the maximal number
     * 		of iterations is exceeded
     */     protected double computeObjectiveValue(double[] evaluationPoint) throws org.apache.commons.math.FunctionEvaluationException {         if ((++(evaluations)) > (maxEvaluations)) {
            throw new org.apache.commons.math.FunctionEvaluationException(new org.apache.commons.math.MaxEvaluationsExceededException(maxEvaluations), 
            evaluationPoint);
        }
        return function.value(evaluationPoint);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.optimization.RealPointValuePair optimize(T f, org.apache.commons.math.optimization.GoalType goalType, 
    double[] startPoint) throws 
    org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {

        // reset counters
        iterations = 0;
        evaluations = 0;

        // store optimization problem characteristics
        function = f;
        goal = goalType;
        start = startPoint.clone();

        return doOptimize();
    }

    /**
     *
     *
     * @return the optimization type.
     */     public org.apache.commons.math.optimization.GoalType getGoalType() {         return goal;
    }

    /**
     *
     *
     * @return the initial guess.
     */     public double[] getStartPoint() {         return start.clone();
    }

    /**
     * Perform the bulk of optimization algorithm.
     *
     * @return the point/value pair giving the optimal value for objective function
     * @throws FunctionEvaluationException
     * 		if the objective function throws one during
     * 		the search
     * @throws OptimizationException
     * 		if the algorithm failed to converge
     * @throws IllegalArgumentException
     * 		if the start point dimension is wrong
     */     protected abstract org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException;}