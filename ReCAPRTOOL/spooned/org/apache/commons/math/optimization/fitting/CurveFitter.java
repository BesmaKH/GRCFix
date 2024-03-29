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
package org.apache.commons.math.optimization.fitting;












/**
 * Fitter for parametric univariate real functions y = f(x).
 * <p>When a univariate real function y = f(x) does depend on some
 * unknown parameters p<sub>0</sub>, p<sub>1</sub> ... p<sub>n-1</sub>,
 * this class can be used to find these parameters. It does this
 * by <em>fitting</em> the curve so it remains very close to a set of
 * observed points (x<sub>0</sub>, y<sub>0</sub>), (x<sub>1</sub>,
 * y<sub>1</sub>) ... (x<sub>k-1</sub>, y<sub>k-1</sub>). This fitting
 * is done by finding the parameters values that minimizes the objective
 * function &sum;(y<sub>i</sub>-f(x<sub>i</sub>))<sup>2</sup>. This is
 * really a least squares problem.</p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class CurveFitter {
    /**
     * Optimizer to use for the fitting.
     */     private final org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer;
    /**
     * Observed points.
     */     private final java.util.List<org.apache.commons.math.optimization.fitting.WeightedObservedPoint> observations;
    /**
     * Simple constructor.
     *
     * @param optimizer
     * 		optimizer to use for the fitting
     */     public CurveFitter(final org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer) {         this.optimizer = optimizer;         observations = new java.util.ArrayList<org.apache.commons.math.optimization.fitting.WeightedObservedPoint>();
    }

    /**
     * Add an observed (x,y) point to the sample with unit weight.
     * <p>Calling this method is equivalent to call
     * <code>addObservedPoint(1.0, x, y)</code>.</p>
     *
     * @param x
     * 		abscissa of the point
     * @param y
     * 		observed value of the point at x, after fitting we should
     * 		have f(x) as close as possible to this value
     * @see #addObservedPoint(double, double, double)
     * @see #addObservedPoint(WeightedObservedPoint)
     * @see #getObservations()
     */     public void addObservedPoint(double x, double y) {         addObservedPoint(1.0, x, y);}
    /**
     * Add an observed weighted (x,y) point to the sample.
     *
     * @param weight
     * 		weight of the observed point in the fit
     * @param x
     * 		abscissa of the point
     * @param y
     * 		observed value of the point at x, after fitting we should
     * 		have f(x) as close as possible to this value
     * @see #addObservedPoint(double, double)
     * @see #addObservedPoint(WeightedObservedPoint)
     * @see #getObservations()
     */     public void addObservedPoint(double weight, double x, double y) {         observations.add(new org.apache.commons.math.optimization.fitting.WeightedObservedPoint(weight, x, y));}     /**
     * Add an observed weighted (x,y) point to the sample.
     *
     * @param observed
     * 		observed point to add
     * @see #addObservedPoint(double, double)
     * @see #addObservedPoint(double, double, double)
     * @see #getObservations()
     */     public void addObservedPoint(org.apache.commons.math.optimization.fitting.WeightedObservedPoint observed) {         observations.add(observed);}

    /**
     * Get the observed points.
     *
     * @return observed points
     * @see #addObservedPoint(double, double)
     * @see #addObservedPoint(double, double, double)
     * @see #addObservedPoint(WeightedObservedPoint)
     */     public org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] getObservations() {         return observations.toArray(new org.apache.commons.math.optimization.fitting.WeightedObservedPoint[observations.size()]);
    }

    /**
     * Remove all observations.
     */
    public void clearObservations() {
        observations.clear();
    }

    /**
     * Fit a curve.
     * <p>This method compute the coefficients of the curve that best
     * fit the sample of observed points previously given through calls
     * to the {@link #addObservedPoint(WeightedObservedPoint)
     * addObservedPoint} method.</p>
     *
     * @param f
     * 		parametric function to fit
     * @param initialGuess
     * 		first guess of the function parameters
     * @return fitted parameters
     * @exception FunctionEvaluationException if the objective function throws one during
     * the search
     * @exception OptimizationException if the algorithm failed to converge
     * @exception IllegalArgumentException if the start point dimension is wrong
     */     public double[] fit(final org.apache.commons.math.optimization.fitting.ParametricRealFunction f, final double[] initialGuess) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        // prepare least squares problem
        double[] target = new double[observations.size()];
        double[] weights = new double[observations.size()];
        int i = 0;
        for (org.apache.commons.math.optimization.fitting.WeightedObservedPoint point : observations) {
            target[i] = point.getY();
            weights[i] = point.getWeight();
            ++i;
        }

        // perform the fit
        org.apache.commons.math.optimization.VectorialPointValuePair optimum = 
        optimizer.optimize(new org.apache.commons.math.optimization.fitting.CurveFitter.TheoreticalValuesFunction(f), target, weights, initialGuess);

        // extract the coefficients
        return optimum.getPointRef();

    }

    /**
     * Vectorial function computing function theoretical values.
     */     private class TheoreticalValuesFunction implements org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction {

        /**
         * Function to fit.
         */         private final org.apache.commons.math.optimization.fitting.ParametricRealFunction f;
        /**
         * Simple constructor.
         *
         * @param f
         * 		function to fit.
         */         public TheoreticalValuesFunction(final org.apache.commons.math.optimization.fitting.ParametricRealFunction f) {             this.f = f;}

        /**
         * {@inheritDoc}
         */         public org.apache.commons.math.analysis.MultivariateMatrixFunction jacobian() {             return new org.apache.commons.math.analysis.MultivariateMatrixFunction() {
                public double[][] value(double[] point) throws 
                java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {

                    final double[][] jacobian = new double[observations.size()][];

                    int i = 0;
                    for (org.apache.commons.math.optimization.fitting.WeightedObservedPoint observed : observations) {
                        jacobian[(i++)] = f.gradient(observed.getX(), point);
                    }

                    return jacobian;

                }
            };
        }

        /**
         * {@inheritDoc}
         */         public double[] value(double[] point) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {

            // compute the residuals
            final double[] values = new double[observations.size()];
            int i = 0;
            for (org.apache.commons.math.optimization.fitting.WeightedObservedPoint observed : observations) {
                values[(i++)] = f.value(observed.getX(), point);
            }

            return values;

        }

    }

}