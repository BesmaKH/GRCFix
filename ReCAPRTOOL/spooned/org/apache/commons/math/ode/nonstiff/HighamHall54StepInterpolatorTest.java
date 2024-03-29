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





















public class HighamHall54StepInterpolatorTest {

    @org.junit.Test
    public void derivativesConsistency() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        org.apache.commons.math.ode.TestProblem3 pb = new org.apache.commons.math.ode.TestProblem3(0.1);
        double minStep = 0;
        double maxStep = (pb.getFinalTime()) - (pb.getInitialTime());
        double scalAbsoluteTolerance = 1.0E-8;
        double scalRelativeTolerance = scalAbsoluteTolerance;
        org.apache.commons.math.ode.nonstiff.HighamHall54Integrator integ = new org.apache.commons.math.ode.nonstiff.HighamHall54Integrator(minStep, maxStep, 
        scalAbsoluteTolerance, 
        scalRelativeTolerance);
        org.apache.commons.math.ode.sampling.StepInterpolatorTestUtils.checkDerivativesConsistency(integ, pb, 1.1E-10);
    }

    @org.junit.Test
    public void serialization() throws 

    java.io.IOException, java.lang.ClassNotFoundException, org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {

        org.apache.commons.math.ode.TestProblem3 pb = new org.apache.commons.math.ode.TestProblem3(0.9);
        double minStep = 0;
        double maxStep = (pb.getFinalTime()) - (pb.getInitialTime());
        double scalAbsoluteTolerance = 1.0E-8;
        double scalRelativeTolerance = scalAbsoluteTolerance;
        org.apache.commons.math.ode.nonstiff.HighamHall54Integrator integ = new org.apache.commons.math.ode.nonstiff.HighamHall54Integrator(minStep, maxStep, 
        scalAbsoluteTolerance, 
        scalRelativeTolerance);
        integ.addStepHandler(new org.apache.commons.math.ode.ContinuousOutputModel());
        integ.integrate(pb, 
        pb.getInitialTime(), pb.getInitialState(), 
        pb.getFinalTime(), new double[pb.getDimension()]);

        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(bos);
        for (org.apache.commons.math.ode.sampling.StepHandler handler : integ.getStepHandlers()) {
            oos.writeObject(handler);
        }

        org.junit.Assert.assertTrue(((bos.size()) > 158000));
        org.junit.Assert.assertTrue(((bos.size()) < 159000));

        java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(bos.toByteArray());
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bis);
        org.apache.commons.math.ode.ContinuousOutputModel cm = ((org.apache.commons.math.ode.ContinuousOutputModel) (ois.readObject()));

        java.util.Random random = new java.util.Random(347588535632L);
        double maxError = 0.0;
        for (int i = 0; i < 1000; ++i) {
            double r = random.nextDouble();
            double time = (r * (pb.getInitialTime())) + ((1.0 - r) * (pb.getFinalTime()));
            cm.setInterpolatedTime(time);
            double[] interpolatedY = cm.getInterpolatedState();
            double[] theoreticalY = pb.computeTheoreticalState(time);
            double dx = (interpolatedY[0]) - (theoreticalY[0]);
            double dy = (interpolatedY[1]) - (theoreticalY[1]);
            double error = (dx * dx) + (dy * dy);
            if (error > maxError) {
                maxError = error;
            }
        }

        org.junit.Assert.assertTrue((maxError < 1.6E-10));

    }

    @org.junit.Test
    public void checkClone() throws 
    org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        org.apache.commons.math.ode.TestProblem3 pb = new org.apache.commons.math.ode.TestProblem3(0.9);
        double minStep = 0;
        double maxStep = (pb.getFinalTime()) - (pb.getInitialTime());
        double scalAbsoluteTolerance = 1.0E-8;
        double scalRelativeTolerance = scalAbsoluteTolerance;
        org.apache.commons.math.ode.nonstiff.HighamHall54Integrator integ = new org.apache.commons.math.ode.nonstiff.HighamHall54Integrator(minStep, maxStep, 
        scalAbsoluteTolerance, 
        scalRelativeTolerance);
        integ.addStepHandler(new org.apache.commons.math.ode.sampling.StepHandler() {
            public void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator interpolator, boolean isLast) throws 
            org.apache.commons.math.ode.DerivativeException {
                org.apache.commons.math.ode.sampling.StepInterpolator cloned = interpolator.copy();
                double tA = cloned.getPreviousTime();
                double tB = cloned.getCurrentTime();
                double halfStep = (java.lang.Math.abs((tB - tA))) / 2;
                org.junit.Assert.assertEquals(interpolator.getPreviousTime(), tA, 1.0E-12);
                org.junit.Assert.assertEquals(interpolator.getCurrentTime(), tB, 1.0E-12);
                for (int i = 0; i < 10; ++i) {
                    double t = ((i * tB) + ((9 - i) * tA)) / 9;
                    interpolator.setInterpolatedTime(t);
                    org.junit.Assert.assertTrue(((java.lang.Math.abs(((cloned.getInterpolatedTime()) - t))) > (halfStep / 10)));
                    cloned.setInterpolatedTime(t);
                    org.junit.Assert.assertEquals(t, cloned.getInterpolatedTime(), 1.0E-12);
                    double[] referenceState = interpolator.getInterpolatedState();
                    double[] cloneState = cloned.getInterpolatedState();
                    for (int j = 0; j < (referenceState.length); ++j) {
                        org.junit.Assert.assertEquals(referenceState[j], cloneState[j], 1.0E-12);
                    }
                }
            }
            public boolean requiresDenseOutput() {
                return true;
            }
            public void reset() {
            }
        });
        integ.integrate(pb, 
        pb.getInitialTime(), pb.getInitialState(), 
        pb.getFinalTime(), new double[pb.getDimension()]);

    }

}