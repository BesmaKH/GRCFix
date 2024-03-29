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
 * This class implements the 5(4) Higham and Hall integrator for
 * Ordinary Differential Equations.
 *
 * <p>This integrator is an embedded Runge-Kutta integrator
 * of order 5(4) used in local extrapolation mode (i.e. the solution
 * is computed using the high order formula) with stepsize control
 * (and automatic step initialization) and continuous output. This
 * method uses 7 functions evaluations per step.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */

public class HighamHall54Integrator extends org.apache.commons.math.ode.nonstiff.EmbeddedRungeKuttaIntegrator {

    /**
     * Integrator method name.
     */     private static final java.lang.String METHOD_NAME = "Higham-Hall 5(4)";
    /**
     * Time steps Butcher array.
     */     private static final double[] STATIC_C = new double[]{ 2.0 / 9.0, 1.0 / 3.0, 1.0 / 2.0, 3.0 / 5.0, 1.0, 1.0 };


    /**
     * Internal weights Butcher array.
     */     private static final double[][] STATIC_A = new double[][]{ new double[]{ 2.0 / 9.0 }, 
    new double[]{ 1.0 / 12.0, 1.0 / 4.0 }, 
    new double[]{ 1.0 / 8.0, 0.0, 3.0 / 8.0 }, 
    new double[]{ 91.0 / 500.0, (-27.0) / 100.0, 78.0 / 125.0, 8.0 / 125.0 }, 
    new double[]{ (-11.0) / 20.0, 27.0 / 20.0, 12.0 / 5.0, (-36.0) / 5.0, 5.0 }, 
    new double[]{ 1.0 / 12.0, 0.0, 27.0 / 32.0, (-4.0) / 3.0, 125.0 / 96.0, 5.0 / 48.0 } };


    /**
     * Propagation weights Butcher array.
     */     private static final double[] STATIC_B = new double[]{ 1.0 / 12.0, 0.0, 27.0 / 32.0, (-4.0) / 3.0, 125.0 / 96.0, 5.0 / 48.0, 0.0 };


    /**
     * Error weights Butcher array.
     */     private static final double[] STATIC_E = new double[]{ (-1.0) / 20.0, 0.0, 81.0 / 160.0, (-6.0) / 5.0, 25.0 / 32.0, 1.0 / 16.0, (-1.0) / 10.0 };


    /**
     * Simple constructor.
     * Build a fifth order Higham and Hall integrator with the given step bounds
     *
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
     */     public HighamHall54Integrator(final double minStep, final double maxStep, final double scalAbsoluteTolerance, final double scalRelativeTolerance) {         super(org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.METHOD_NAME, false, org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_C, org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_A, org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_B, new org.apache.commons.math.ode.nonstiff.HighamHall54StepInterpolator(), minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);}

    /**
     * Simple constructor.
     * Build a fifth order Higham and Hall integrator with the given step bounds
     *
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
     */     public HighamHall54Integrator(final double minStep, final double maxStep, final double[] vecAbsoluteTolerance, final double[] vecRelativeTolerance) {         super(org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.METHOD_NAME, false, org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_C, org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_A, org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_B, new org.apache.commons.math.ode.nonstiff.HighamHall54StepInterpolator(), minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);}

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int getOrder() {
        return 5;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     protected double estimateError(final double[][] yDotK, final 
    double[] y0, final double[] y1, final 
    double h) {

        double error = 0;

        for (int j = 0; j < (mainSetDimension); ++j) {
            double errSum = (org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_E[0]) * (yDotK[0][j]);
            for (int l = 1; l < (org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_E.length); ++l) {
                errSum += (org.apache.commons.math.ode.nonstiff.HighamHall54Integrator.STATIC_E[l]) * (yDotK[l][j]);
            }

            final double yScale = java.lang.Math.max(java.lang.Math.abs(y0[j]), java.lang.Math.abs(y1[j]));
            final double tol = ((vecAbsoluteTolerance) == null) ? 
            (scalAbsoluteTolerance) + ((scalRelativeTolerance) * yScale) : 
            (vecAbsoluteTolerance[j]) + ((vecRelativeTolerance[j]) * yScale);
            final double ratio = (h * errSum) / tol;
            error += ratio * ratio;

        }

        return java.lang.Math.sqrt((error / (mainSetDimension)));

    }

}