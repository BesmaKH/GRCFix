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
package org.apache.commons.math.estimation;










/**
 * Base class for implementing estimators.
 * <p>This base class handles the boilerplates methods associated to thresholds
 * settings, jacobian and error estimation.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 * @deprecated as of 2.0, everything in package org.apache.commons.math.estimation has
 * been deprecated and replaced by package org.apache.commons.math.optimization.general
 */
@java.lang.Deprecated
public abstract class AbstractEstimator implements org.apache.commons.math.estimation.Estimator {

    /**
     * Default maximal number of cost evaluations allowed.
     */     public static final int DEFAULT_MAX_COST_EVALUATIONS = 100;
    /**
     * Array of measurements.
     */     protected org.apache.commons.math.estimation.WeightedMeasurement[] measurements;
    /**
     * Array of parameters.
     */     protected org.apache.commons.math.estimation.EstimatedParameter[] parameters;
    /**
     * Jacobian matrix.
     * <p>This matrix is in canonical form just after the calls to
     * {@link #updateJacobian()}, but may be modified by the solver
     * in the derived class (the {@link LevenbergMarquardtEstimator
     * Levenberg-Marquardt estimator} does this).</p>
     */
    protected double[] jacobian;

    /**
     * Number of columns of the jacobian matrix.
     */     protected int cols;
    /**
     * Number of rows of the jacobian matrix.
     */     protected int rows;
    /**
     * Residuals array.
     * <p>This array is in canonical form just after the calls to
     * {@link #updateJacobian()}, but may be modified by the solver
     * in the derived class (the {@link LevenbergMarquardtEstimator
     * Levenberg-Marquardt estimator} does this).</p>
     */     protected double[] residuals;

    /**
     * Cost value (square root of the sum of the residuals).
     */     protected double cost;
    /**
     * Maximal allowed number of cost evaluations.
     */     private int maxCostEval;
    /**
     * Number of cost evaluations.
     */     private int costEvaluations;
    /**
     * Number of jacobian evaluations.
     */     private int jacobianEvaluations;
    /**
     * Build an abstract estimator for least squares problems.
     * <p>The maximal number of cost evaluations allowed is set
     * to its default value {@link #DEFAULT_MAX_COST_EVALUATIONS}.</p>
     */
    protected AbstractEstimator() {
        setMaxCostEval(org.apache.commons.math.estimation.AbstractEstimator.DEFAULT_MAX_COST_EVALUATIONS);
    }

    /**
     * Set the maximal number of cost evaluations allowed.
     *
     * @param maxCostEval
     * 		maximal number of cost evaluations allowed
     * @see #estimate
     */     public final void setMaxCostEval(int maxCostEval) {
        this.maxCostEval = maxCostEval;
    }

    /**
     * Get the number of cost evaluations.
     *
     * @return number of cost evaluations
     */
    public final int getCostEvaluations() {
        return costEvaluations;
    }

    /**
     * Get the number of jacobian evaluations.
     *
     * @return number of jacobian evaluations
     */
    public final int getJacobianEvaluations() {
        return jacobianEvaluations;
    }

    /**
     * Update the jacobian matrix.
     */
    protected void updateJacobian() {
        incrementJacobianEvaluationsCounter();
        java.util.Arrays.fill(jacobian, 0);
        int index = 0;
        for (int i = 0; i < (rows); i++) {
            org.apache.commons.math.estimation.WeightedMeasurement wm = measurements[i];
            double factor = -(java.lang.Math.sqrt(wm.getWeight()));
            for (int j = 0; j < (cols); ++j) {
                jacobian[(index++)] = factor * (wm.getPartial(parameters[j]));
            }
        }
    }

    /**
     * Increment the jacobian evaluations counter.
     */
    protected final void incrementJacobianEvaluationsCounter() {
        ++(jacobianEvaluations);
    }

    /**
     * Update the residuals array and cost function value.
     *
     * @exception EstimationException if the number of cost evaluations
     * exceeds the maximum allowed
     */     protected void updateResidualsAndCost() throws 
    org.apache.commons.math.estimation.EstimationException {

        if ((++(costEvaluations)) > (maxCostEval)) {
            throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.util.LocalizedFormats.MAX_EVALUATIONS_EXCEEDED, 
            maxCostEval);
        }

        cost = 0;
        int index = 0;
        for (int i = 0; i < (rows); i++ , index += cols) {
            org.apache.commons.math.estimation.WeightedMeasurement wm = measurements[i];
            double residual = wm.getResidual();
            residuals[i] = (java.lang.Math.sqrt(wm.getWeight())) * residual;
            cost += ((wm.getWeight()) * residual) * residual;
        }
        cost = java.lang.Math.sqrt(cost);

    }

    /**
     * Get the Root Mean Square value.
     * Get the Root Mean Square value, i.e. the root of the arithmetic
     * mean of the square of all weighted residuals. This is related to the
     * criterion that is minimized by the estimator as follows: if
     * <em>c</em> if the criterion, and <em>n</em> is the number of
     * measurements, then the RMS is <em>sqrt (c/n)</em>.
     *
     * @param problem
     * 		estimation problem
     * @return RMS value
     */     public double getRMS(org.apache.commons.math.estimation.EstimationProblem problem) {
        org.apache.commons.math.estimation.WeightedMeasurement[] wm = problem.getMeasurements();
        double criterion = 0;
        for (int i = 0; i < (wm.length); ++i) {
            double residual = wm[i].getResidual();
            criterion += ((wm[i].getWeight()) * residual) * residual;
        }
        return java.lang.Math.sqrt((criterion / (wm.length)));
    }

    /**
     * Get the Chi-Square value.
     *
     * @param problem
     * 		estimation problem
     * @return chi-square value
     */     public double getChiSquare(org.apache.commons.math.estimation.EstimationProblem problem) {         org.apache.commons.math.estimation.WeightedMeasurement[] wm = problem.getMeasurements();
        double chiSquare = 0;
        for (int i = 0; i < (wm.length); ++i) {
            double residual = wm[i].getResidual();
            chiSquare += (residual * residual) / (wm[i].getWeight());
        }
        return chiSquare;
    }

    /**
     * Get the covariance matrix of unbound estimated parameters.
     *
     * @param problem
     * 		estimation problem
     * @return covariance matrix
     * @exception EstimationException if the covariance matrix
     * cannot be computed (singular problem)
     */     public double[][] getCovariances(org.apache.commons.math.estimation.EstimationProblem problem) throws org.apache.commons.math.estimation.EstimationException {

        // set up the jacobian
        updateJacobian();

        // compute transpose(J).J, avoiding building big intermediate matrices
        final int n = problem.getMeasurements().length;
        final int m = problem.getUnboundParameters().length;
        final int max = m * n;
        double[][] jTj = new double[m][m];
        for (int i = 0; i < m; ++i) {
            for (int j = i; j < m; ++j) {
                double sum = 0;
                for (int k = 0; k < max; k += m) {
                    sum += (jacobian[(k + i)]) * (jacobian[(k + j)]);
                }
                jTj[i][j] = sum;
                jTj[j][i] = sum;
            }
        }

        try {
            // compute the covariances matrix
            org.apache.commons.math.linear.RealMatrix inverse = 
            new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(jTj)).getSolver().getInverse();
            return inverse.getData();
        } catch (org.apache.commons.math.linear.InvalidMatrixException ime) {
            throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.util.LocalizedFormats.UNABLE_TO_COMPUTE_COVARIANCE_SINGULAR_PROBLEM);
        }

    }

    /**
     * Guess the errors in unbound estimated parameters.
     * <p>Guessing is covariance-based, it only gives rough order of magnitude.</p>
     *
     * @param problem
     * 		estimation problem
     * @return errors in estimated parameters
     * @exception EstimationException if the covariances matrix cannot be computed
     * or the number of degrees of freedom is not positive (number of measurements
     * lesser or equal to number of parameters)
     */     public double[] guessParametersErrors(org.apache.commons.math.estimation.EstimationProblem problem) throws org.apache.commons.math.estimation.EstimationException {
        int m = problem.getMeasurements().length;
        int p = problem.getUnboundParameters().length;
        if (m <= p) {
            throw new org.apache.commons.math.estimation.EstimationException(
            org.apache.commons.math.util.LocalizedFormats.NO_DEGREES_OF_FREEDOM, 
            m, p);
        }
        double[] errors = new double[problem.getUnboundParameters().length];
        final double c = java.lang.Math.sqrt(((getChiSquare(problem)) / (m - p)));
        double[][] covar = getCovariances(problem);
        for (int i = 0; i < (errors.length); ++i) {
            errors[i] = (java.lang.Math.sqrt(covar[i][i])) * c;
        }
        return errors;
    }

    /**
     * Initialization of the common parts of the estimation.
     * <p>This method <em>must</em> be called at the start
     * of the {@link #estimate(EstimationProblem) estimate}
     * method.</p>
     *
     * @param problem
     * 		estimation problem to solve
     */     protected void initializeEstimate(org.apache.commons.math.estimation.EstimationProblem problem) {
        // reset counters
        costEvaluations = 0;
        jacobianEvaluations = 0;

        // retrieve the equations and the parameters
        measurements = problem.getMeasurements();
        parameters = problem.getUnboundParameters();

        // arrays shared with the other private methods
        rows = measurements.length;
        cols = parameters.length;
        jacobian = new double[(rows) * (cols)];
        residuals = new double[rows];

        cost = java.lang.Double.POSITIVE_INFINITY;

    }

    /**
     * Solve an estimation problem.
     *
     * <p>The method should set the parameters of the problem to several
     * trial values until it reaches convergence. If this method returns
     * normally (i.e. without throwing an exception), then the best
     * estimate of the parameters can be retrieved from the problem
     * itself, through the {@link EstimationProblem#getAllParameters
     * EstimationProblem.getAllParameters} method.</p>
     *
     * @param problem
     * 		estimation problem to solve
     * @exception EstimationException if the problem cannot be solved
     */
    public abstract void estimate(org.apache.commons.math.estimation.EstimationProblem problem) throws 
    org.apache.commons.math.estimation.EstimationException;

}