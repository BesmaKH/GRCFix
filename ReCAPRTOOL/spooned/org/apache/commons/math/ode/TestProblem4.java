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
package org.apache.commons.math.ode;




/**
 * This class is used in the junit tests for the ODE integrators.
 *
 * <p>This specific problem is the following differential equation :
 * <pre>
 *    x'' = -x
 * </pre>
 * And when x decreases down to 0, the state should be changed as follows :
 * <pre>
 *   x' -> -x'
 * </pre>
 * The theoretical solution of this problem is x = |sin(t+a)|
 * </p>
 */

public class TestProblem4 extends 
org.apache.commons.math.ode.TestProblemAbstract {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = -5910438521889015745L;
    /**
     * Time offset.
     */     private double a;
    /**
     * theoretical state
     */     private double[] y;
    /**
     * Simple constructor.
     */     public TestProblem4() {         super();
        a = 1.2;
        double[] y0 = new double[]{ java.lang.Math.sin(a), java.lang.Math.cos(a) };
        setInitialConditions(0.0, y0);
        setFinalConditions(15);
        double[] errorScale = new double[]{ 1.0, 0.0 };
        setErrorScale(errorScale);
        y = new double[y0.length];
    }

    /**
     * Copy constructor.
     *
     * @param problem
     * 		problem to copy
     */     public TestProblem4(org.apache.commons.math.ode.TestProblem4 problem) {         super(problem);
        a = problem.a;
        y = problem.y.clone();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.ode.TestProblem4 copy() {
        return new org.apache.commons.math.ode.TestProblem4(this);
    }

    @java.lang.Override
    public org.apache.commons.math.ode.events.EventHandler[] getEventsHandlers() {
        return new org.apache.commons.math.ode.events.EventHandler[]{ new org.apache.commons.math.ode.TestProblem4.Bounce(), new org.apache.commons.math.ode.TestProblem4.Stop() };
    }

    @java.lang.Override
    public void doComputeDerivatives(double t, double[] y, double[] yDot) {
        yDot[0] = y[1];
        yDot[1] = -(y[0]);
    }

    @java.lang.Override
    public double[] computeTheoreticalState(double t) {
        double sin = java.lang.Math.sin((t + (a)));
        double cos = java.lang.Math.cos((t + (a)));
        y[0] = java.lang.Math.abs(sin);
        y[1] = (sin >= 0) ? cos : -cos;
        return y;
    }

    private static class Bounce implements org.apache.commons.math.ode.events.EventHandler {

        private static final long serialVersionUID = 1356097180027801200L;
        private int sign;

        public Bounce() {
            sign = +1;
        }

        public double g(double t, double[] y) {
            return (sign) * (y[0]);
        }

        public int eventOccurred(double t, double[] y, boolean increasing) {
            // this sign change is needed because the state will be reset soon
            sign = -(sign);
            return org.apache.commons.math.ode.events.EventHandler.RESET_STATE;
        }

        public void resetState(double t, double[] y) {
            y[0] = -(y[0]);
            y[1] = -(y[1]);
        }

    }

    private static class Stop implements org.apache.commons.math.ode.events.EventHandler {

        private static final long serialVersionUID = 6975050568227951931L;

        public Stop() {
        }

        public double g(double t, double[] y) {
            return t - 12.0;
        }

        public int eventOccurred(double t, double[] y, boolean increasing) {
            return org.apache.commons.math.ode.events.EventHandler.STOP;
        }

        public void resetState(double t, double[] y) {
        }

    }

}