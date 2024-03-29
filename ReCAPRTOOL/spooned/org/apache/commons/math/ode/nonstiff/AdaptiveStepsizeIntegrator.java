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
package org.apache.commons.math.ode.nonstiff;









/**
 * This abstract class holds the common part of all adaptive
 * stepsize integrators for Ordinary Differential Equations.
 *
 * <p>These algorithms perform integration with stepsize control, which
 * means the user does not specify the integration step but rather a
 * tolerance on error. The error threshold is computed as
 * <pre>
 * threshold_i = absTol_i + relTol_i * max (abs (ym), abs (ym+1))
 * </pre>
 * where absTol_i is the absolute tolerance for component i of the
 * state vector and relTol_i is the relative tolerance for the same
 * component. The user can also use only two scalar values absTol and
 * relTol which will be used for all components.
 * </p>
 *
 * <p>If the Ordinary Differential Equations is an {@link ExtendedFirstOrderDifferentialEquations
 * extended ODE} rather than a {@link FirstOrderDifferentialEquations basic ODE},
 * then <em>only</em> the {@link ExtendedFirstOrderDifferentialEquations#getMainSetDimension()
 * main set} part of the state vector is used for stepsize control, not the complete
 * state vector.
 * </p>
 *
 * <p>If the estimated error for ym+1 is such that
 * <pre>
 * sqrt((sum (errEst_i / threshold_i)^2 ) / n) < 1
 * </pre>
 *
 * (where n is the main set dimension) then the step is accepted,
 * otherwise the step is rejected and a new attempt is made with a new
 * stepsize.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */


public abstract class AdaptiveStepsizeIntegrator extends 
org.apache.commons.math.ode.AbstractIntegrator {

    /**
     * Allowed absolute scalar error.
     */     protected final double scalAbsoluteTolerance;
    /**
     * Allowed relative scalar error.
     */     protected final double scalRelativeTolerance;
    /**
     * Allowed absolute vectorial error.
     */     protected final double[] vecAbsoluteTolerance;
    /**
     * Allowed relative vectorial error.
     */     protected final double[] vecRelativeTolerance;
    /**
     * User supplied initial step.
     */     private double initialStep;
    /**
     * Minimal step.
     */     private final double minStep;
    /**
     * Maximal step.
     */     private final double maxStep;
    /**
     * Main set dimension.
     */     protected int mainSetDimension;
    /**
     * Build an integrator with the given stepsize bounds.
     * The default step handler does nothing.
     *
     * @param name
     * 		name of the method
     * @param minStep
     * 		minimal step (must be positive even for backward
     * 		integration), the last step can be smaller than this
     * @param maxStep
     * 		maximal step (must be positive even for backward
     * 		integration)
     * @param scalAbsoluteTolerance
     * 		allowed absolute error
     * @param scalRelativeTolerance
     * 		allowed relative error
     */     public AdaptiveStepsizeIntegrator(final java.lang.String name, final double minStep, final double maxStep, final double scalAbsoluteTolerance, final double scalRelativeTolerance) {         super(name);
        this.minStep = java.lang.Math.abs(minStep);
        this.maxStep = java.lang.Math.abs(maxStep);
        this.initialStep = -1.0;

        this.scalAbsoluteTolerance = scalAbsoluteTolerance;
        this.scalRelativeTolerance = scalRelativeTolerance;
        this.vecAbsoluteTolerance = null;
        this.vecRelativeTolerance = null;

        resetInternalState();

    }

    /**
     * Build an integrator with the given stepsize bounds.
     * The default step handler does nothing.
     *
     * @param name
     * 		name of the method
     * @param minStep
     * 		minimal step (must be positive even for backward
     * 		integration), the last step can be smaller than this
     * @param maxStep
     * 		maximal step (must be positive even for backward
     * 		integration)
     * @param vecAbsoluteTolerance
     * 		allowed absolute error
     * @param vecRelativeTolerance
     * 		allowed relative error
     */     public AdaptiveStepsizeIntegrator(final java.lang.String name, final double minStep, final double maxStep, final double[] vecAbsoluteTolerance, final double[] vecRelativeTolerance) {         super(name);
        this.minStep = minStep;
        this.maxStep = maxStep;
        this.initialStep = -1.0;

        this.scalAbsoluteTolerance = 0;
        this.scalRelativeTolerance = 0;
        this.vecAbsoluteTolerance = vecAbsoluteTolerance.clone();
        this.vecRelativeTolerance = vecRelativeTolerance.clone();

        resetInternalState();

    }

    /**
     * Set the initial step size.
     * <p>This method allows the user to specify an initial positive
     * step size instead of letting the integrator guess it by
     * itself. If this method is not called before integration is
     * started, the initial step size will be estimated by the
     * integrator.</p>
     *
     * @param initialStepSize
     * 		initial step size to use (must be positive even
     * 		for backward integration ; providing a negative value or a value
     * 		outside of the min/max step interval will lead the integrator to
     * 		ignore the value and compute the initial step size by itself)
     */     public void setInitialStepSize(final double initialStepSize) {         if ((initialStepSize < (minStep)) || (initialStepSize > (maxStep))) {             initialStep = -1.0;
        }else {
            initialStep = initialStepSize;
        }
    }

    /**
     * Perform some sanity checks on the integration parameters.
     *
     * @param equations
     * 		differential equations set
     * @param t0
     * 		start time
     * @param y0
     * 		state vector at t0
     * @param t
     * 		target time for the integration
     * @param y
     * 		placeholder where to put the state vector
     * @exception IntegratorException if some inconsistency is detected
     */     @java.lang.Override     protected void sanityChecks(final org.apache.commons.math.ode.FirstOrderDifferentialEquations equations, final double t0, final double[] y0, final double t, final double[] y) throws org.apache.commons.math.ode.IntegratorException {         super.sanityChecks(equations, t0, y0, t, y);

        if (equations instanceof org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations) {
            mainSetDimension = ((org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations) (equations)).getMainSetDimension();
        }else {
            mainSetDimension = equations.getDimension();
        }

        if (((vecAbsoluteTolerance) != null) && ((vecAbsoluteTolerance.length) != (mainSetDimension))) {
            throw new org.apache.commons.math.ode.IntegratorException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, mainSetDimension, vecAbsoluteTolerance.length);
        }

        if (((vecRelativeTolerance) != null) && ((vecRelativeTolerance.length) != (mainSetDimension))) {
            throw new org.apache.commons.math.ode.IntegratorException(
            org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, mainSetDimension, vecRelativeTolerance.length);
        }

    }

    /**
     * Initialize the integration step.
     *
     * @param equations
     * 		differential equations set
     * @param forward
     * 		forward integration indicator
     * @param order
     * 		order of the method
     * @param scale
     * 		scaling vector for the state vector (can be shorter than state vector)
     * @param t0
     * 		start time
     * @param y0
     * 		state vector at t0
     * @param yDot0
     * 		first time derivative of y0
     * @param y1
     * 		work array for a state vector
     * @param yDot1
     * 		work array for the first time derivative of y1
     * @return first integration step
     * @exception DerivativeException this exception is propagated to
     * the caller if the underlying user function triggers one
     */     public double initializeStep(final org.apache.commons.math.ode.FirstOrderDifferentialEquations equations, final boolean forward, final int order, final double[] scale, final double t0, final double[] y0, final double[] yDot0, final double[] y1, final double[] yDot1) throws org.apache.commons.math.ode.DerivativeException {         if ((initialStep) > 0) {             // use the user provided value
            return forward ? initialStep : -(initialStep);}         // very rough first guess : h = 0.01 * ||y/scale|| / ||y'/scale||
        // this guess will be used to perform an Euler step
        double ratio;
        double yOnScale2 = 0;
        double yDotOnScale2 = 0;
        for (int j = 0; j < (scale.length); ++j) {
            ratio = (y0[j]) / (scale[j]);
            yOnScale2 += ratio * ratio;
            ratio = (yDot0[j]) / (scale[j]);
            yDotOnScale2 += ratio * ratio;
        }

        double h = ((yOnScale2 < 1.0E-10) || (yDotOnScale2 < 1.0E-10)) ? 
        1.0E-6 : 0.01 * (java.lang.Math.sqrt((yOnScale2 / yDotOnScale2)));
        if (!forward) {
            h = -h;
        }

        // perform an Euler step using the preceding rough guess
        for (int j = 0; j < (y0.length); ++j) {
            y1[j] = (y0[j]) + (h * (yDot0[j]));
        }
        computeDerivatives((t0 + h), y1, yDot1);

        // estimate the second derivative of the solution
        double yDDotOnScale = 0;
        for (int j = 0; j < (scale.length); ++j) {
            ratio = ((yDot1[j]) - (yDot0[j])) / (scale[j]);
            yDDotOnScale += ratio * ratio;
        }
        yDDotOnScale = (java.lang.Math.sqrt(yDDotOnScale)) / h;

        // step size is computed such that
        // h^order * max (||y'/tol||, ||y''/tol||) = 0.01
        final double maxInv2 = java.lang.Math.max(java.lang.Math.sqrt(yDotOnScale2), yDDotOnScale);
        final double h1 = (maxInv2 < 1.0E-15) ? 
        java.lang.Math.max(1.0E-6, (0.001 * (java.lang.Math.abs(h)))) : 
        java.lang.Math.pow((0.01 / maxInv2), (1.0 / order));
        h = java.lang.Math.min((100.0 * (java.lang.Math.abs(h))), h1);
        h = java.lang.Math.max(h, (1.0E-12 * (java.lang.Math.abs(t0))));// avoids cancellation when computing t1 - t0
        if (h < (getMinStep())) {
            h = getMinStep();
        }
        if (h > (getMaxStep())) {
            h = getMaxStep();
        }
        if (!forward) {
            h = -h;
        }

        return h;

    }

    /**
     * Filter the integration step.
     *
     * @param h
     * 		signed step
     * @param forward
     * 		forward integration indicator
     * @param acceptSmall
     * 		if true, steps smaller than the minimal value
     * 		are silently increased up to this value, if false such small
     * 		steps generate an exception
     * @return a bounded integration step (h if no bound is reach, or a bounded value)
     * @exception IntegratorException if the step is too small and acceptSmall is false
     */     protected double filterStep(final double h, final boolean forward, final boolean acceptSmall) throws org.apache.commons.math.ode.IntegratorException {         double filteredH = h;         if ((java.lang.Math.abs(h)) < (minStep)) {
            if (acceptSmall) {
                filteredH = (forward) ? minStep : -(minStep);
            }else {
                throw new org.apache.commons.math.ode.IntegratorException(
                org.apache.commons.math.util.LocalizedFormats.MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION, 
                minStep, java.lang.Math.abs(h));
            }
        }

        if (filteredH > (maxStep)) {
            filteredH = maxStep;
        }else             if (filteredH < (-(maxStep))) {
                filteredH = -(maxStep);
            }

        return filteredH;

    }

    /**
     * {@inheritDoc}
     */     public abstract double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations equations, double t0, double[] y0, 
    double t, double[] y) throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException;

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getCurrentStepStart() {
        return stepStart;
    }

    /**
     * Reset internal state to dummy values.
     */     protected void resetInternalState() {         stepStart = java.lang.Double.NaN;
        stepSize = java.lang.Math.sqrt(((minStep) * (maxStep)));
    }

    /**
     * Get the minimal step.
     *
     * @return minimal step
     */     public double getMinStep() {         return minStep;
    }

    /**
     * Get the maximal step.
     *
     * @return maximal step
     */     public double getMaxStep() {         return maxStep;
    }

}