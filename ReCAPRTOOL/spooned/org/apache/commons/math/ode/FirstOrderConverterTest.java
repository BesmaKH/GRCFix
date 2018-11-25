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










public class FirstOrderConverterTest extends 
junit.framework.TestCase {

    public FirstOrderConverterTest(java.lang.String name) {
        super(name);
    }

    public void testDoubleDimension() {
        for (int i = 1; i < 10; ++i) {
            org.apache.commons.math.ode.SecondOrderDifferentialEquations eqn2 = new org.apache.commons.math.ode.FirstOrderConverterTest.Equations(i, 0.2);
            org.apache.commons.math.ode.FirstOrderConverter eqn1 = new org.apache.commons.math.ode.FirstOrderConverter(eqn2);
            junit.framework.Assert.assertTrue(((eqn1.getDimension()) == (2 * (eqn2.getDimension()))));
        }
    }

    public void testDecreasingSteps() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {

        double previousError = java.lang.Double.NaN;
        for (int i = 0; i < 10; ++i) {

            double step = java.lang.Math.pow(2.0, (-(i + 1)));
            double error = (integrateWithSpecifiedStep(4.0, 0.0, 1.0, step)) - 
            (java.lang.Math.sin(4.0));
            if (i > 0) {
                junit.framework.Assert.assertTrue(((java.lang.Math.abs(error)) < (java.lang.Math.abs(previousError))));
            }
            previousError = error;

        }
    }

    public void testSmallStep() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        double error = (integrateWithSpecifiedStep(4.0, 0.0, 1.0, 1.0E-4)) - 
        (java.lang.Math.sin(4.0));
        junit.framework.Assert.assertTrue(((java.lang.Math.abs(error)) < 1.0E-10));
    }

    public void testBigStep() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        double error = (integrateWithSpecifiedStep(4.0, 0.0, 1.0, 0.5)) - 
        (java.lang.Math.sin(4.0));
        junit.framework.Assert.assertTrue(((java.lang.Math.abs(error)) > 0.1));
    }

    private static class Equations implements 
    org.apache.commons.math.ode.SecondOrderDifferentialEquations {

        private int n;

        private double omega2;

        public Equations(int n, double omega) {
            this.n = n;
            omega2 = omega * omega;
        }

        public int getDimension() {
            return n;
        }

        public void computeSecondDerivatives(double t, double[] y, double[] yDot, 
        double[] yDDot) {
            for (int i = 0; i < (n); ++i) {
                yDDot[i] = (-(omega2)) * (y[i]);
            }
        }

    }

    private double integrateWithSpecifiedStep(double omega, 
    double t0, double t, 
    double step) throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        double[] y0 = new double[2];
        y0[0] = java.lang.Math.sin((omega * t0));
        y0[1] = omega * (java.lang.Math.cos((omega * t0)));
        org.apache.commons.math.ode.nonstiff.ClassicalRungeKuttaIntegrator i = new org.apache.commons.math.ode.nonstiff.ClassicalRungeKuttaIntegrator(step);
        double[] y = new double[2];
        i.integrate(new org.apache.commons.math.ode.FirstOrderConverter(new org.apache.commons.math.ode.FirstOrderConverterTest.Equations(1, omega)), t0, y0, t, y);
        return y[0];
    }

}