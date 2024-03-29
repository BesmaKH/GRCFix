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
package org.apache.commons.math.ode.sampling;












public class StepNormalizerTest extends 
junit.framework.TestCase {

    public StepNormalizerTest(java.lang.String name) {
        super(name);
        pb = null;
        integ = null;
    }

    public void testBoundaries() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        double range = (pb.getFinalTime()) - (pb.getInitialTime());
        setLastSeen(false);
        integ.addStepHandler(new org.apache.commons.math.ode.sampling.StepNormalizer((range / 10.0), 
        new org.apache.commons.math.ode.sampling.FixedStepHandler() {
            private static final long serialVersionUID = 1650337364641626444L;
            private boolean firstCall = true;
            public void handleStep(double t, 
            double[] y, 
            double[] yDot, 
            boolean isLast) {
                if (firstCall) {
                    checkValue(t, pb.getInitialTime());
                    firstCall = false;
                }
                if (isLast) {
                    setLastSeen(true);
                    checkValue(t, pb.getFinalTime());
                }
            }
        }));
        integ.integrate(pb, 
        pb.getInitialTime(), pb.getInitialState(), 
        pb.getFinalTime(), new double[pb.getDimension()]);
        junit.framework.Assert.assertTrue(lastSeen);
    }

    public void testBeforeEnd() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        final double range = (pb.getFinalTime()) - (pb.getInitialTime());
        setLastSeen(false);
        integ.addStepHandler(new org.apache.commons.math.ode.sampling.StepNormalizer((range / 10.5), 
        new org.apache.commons.math.ode.sampling.FixedStepHandler() {
            private static final long serialVersionUID = 2228457391561277298L;
            public void handleStep(double t, 
            double[] y, 
            double[] yDot, 
            boolean isLast) {
                if (isLast) {
                    setLastSeen(true);
                    checkValue(t, 
                    ((pb.getFinalTime()) - (range / 21.0)));
                }
            }
        }));
        integ.integrate(pb, 
        pb.getInitialTime(), pb.getInitialState(), 
        pb.getFinalTime(), new double[pb.getDimension()]);
        junit.framework.Assert.assertTrue(lastSeen);
    }

    public void checkValue(double value, double reference) {
        junit.framework.Assert.assertTrue(((java.lang.Math.abs((value - reference))) < 1.0E-10));
    }

    public void setLastSeen(boolean lastSeen) {
        this.lastSeen = lastSeen;
    }

    @java.lang.Override
    public void setUp() {
        pb = new org.apache.commons.math.ode.TestProblem3(0.9);
        double minStep = 0;
        double maxStep = (pb.getFinalTime()) - (pb.getInitialTime());
        integ = new org.apache.commons.math.ode.nonstiff.DormandPrince54Integrator(minStep, maxStep, 1.0E-7, 1.0E-8);
        lastSeen = false;
    }

    @java.lang.Override
    public void tearDown() {
        pb = null;
        integ = null;
    }

    org.apache.commons.math.ode.TestProblem3 pb;
    org.apache.commons.math.ode.FirstOrderIntegrator integ;
    boolean lastSeen;

}