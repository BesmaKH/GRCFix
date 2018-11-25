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
 * This class implements a second order Runge-Kutta integrator for
 * Ordinary Differential Equations.
 *
 * <p>This method is an explicit Runge-Kutta method, its Butcher-array
 * is the following one :
 * <pre>
 *    0  |  0    0
 *   1/2 | 1/2   0
 *       |----------
 *       |  0    1
 * </pre>
 * </p>
 *
 * @see EulerIntegrator
 * @see ClassicalRungeKuttaIntegrator
 * @see GillIntegrator
 * @version $Revision$ $Date$
 * @since 1.2
 */


public class MidpointIntegrator extends org.apache.commons.math.ode.nonstiff.RungeKuttaIntegrator {

    /**
     * Time steps Butcher array.
     */     private static final double[] STATIC_C = new double[]{ 1.0 / 2.0 };


    /**
     * Internal weights Butcher array.
     */     private static final double[][] STATIC_A = new double[][]{ new double[]{ 1.0 / 2.0 } };


    /**
     * Propagation weights Butcher array.
     */     private static final double[] STATIC_B = new double[]{ 0.0, 1.0 };


    /**
     * Simple constructor.
     * Build a midpoint integrator with the given step.
     *
     * @param step
     * 		integration step
     */     public MidpointIntegrator(final double step) {         super("midpoint", org.apache.commons.math.ode.nonstiff.MidpointIntegrator.STATIC_C, org.apache.commons.math.ode.nonstiff.MidpointIntegrator.STATIC_A, org.apache.commons.math.ode.nonstiff.MidpointIntegrator.STATIC_B, new org.apache.commons.math.ode.nonstiff.MidpointStepInterpolator(), step);}

}