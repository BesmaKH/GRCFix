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

















public class DummyStepInterpolatorTest {

    @org.junit.Test
    public void testNoReset() throws org.apache.commons.math.ode.DerivativeException {

        double[] y = new double[]{ 0.0, 1.0, -2.0 };
        org.apache.commons.math.ode.sampling.DummyStepInterpolator interpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolator(y, new double[y.length], true);
        interpolator.storeTime(0);
        interpolator.shift();
        interpolator.storeTime(1);

        double[] result = interpolator.getInterpolatedState();
        for (int i = 0; i < (result.length); ++i) {
            org.junit.Assert.assertTrue(((java.lang.Math.abs(((result[i]) - (y[i])))) < 1.0E-10));
        }

    }

    @org.junit.Test
    public void testFixedState() throws 
    org.apache.commons.math.ode.DerivativeException {

        double[] y = new double[]{ 1.0, 3.0, -4.0 };
        org.apache.commons.math.ode.sampling.DummyStepInterpolator interpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolator(y, new double[y.length], true);
        interpolator.storeTime(0);
        interpolator.shift();
        interpolator.storeTime(1);

        interpolator.setInterpolatedTime(0.1);
        double[] result = interpolator.getInterpolatedState();
        for (int i = 0; i < (result.length); ++i) {
            org.junit.Assert.assertTrue(((java.lang.Math.abs(((result[i]) - (y[i])))) < 1.0E-10));
        }

        interpolator.setInterpolatedTime(0.5);
        result = interpolator.getInterpolatedState();
        for (int i = 0; i < (result.length); ++i) {
            org.junit.Assert.assertTrue(((java.lang.Math.abs(((result[i]) - (y[i])))) < 1.0E-10));
        }

    }

    @org.junit.Test
    public void testSerialization() throws 
    java.io.IOException, java.lang.ClassNotFoundException, org.apache.commons.math.ode.DerivativeException {

        double[] y = new double[]{ 0.0, 1.0, -2.0 };
        org.apache.commons.math.ode.sampling.DummyStepInterpolator interpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolator(y, new double[y.length], true);
        interpolator.storeTime(0);
        interpolator.shift();
        interpolator.storeTime(1);

        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(bos);
        oos.writeObject(interpolator);

        org.junit.Assert.assertTrue(((bos.size()) > 150));
        org.junit.Assert.assertTrue(((bos.size()) < 250));

        java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(bos.toByteArray());
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bis);
        org.apache.commons.math.ode.sampling.DummyStepInterpolator dsi = ((org.apache.commons.math.ode.sampling.DummyStepInterpolator) (ois.readObject()));

        dsi.setInterpolatedTime(0.5);
        double[] result = dsi.getInterpolatedState();
        for (int i = 0; i < (result.length); ++i) {
            org.junit.Assert.assertTrue(((java.lang.Math.abs(((result[i]) - (y[i])))) < 1.0E-10));
        }

    }

    @org.junit.Test
    public void testImpossibleSerialization() throws 
    java.io.IOException {

        double[] y = new double[]{ 0.0, 1.0, -2.0 };
        org.apache.commons.math.ode.sampling.AbstractStepInterpolator interpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolatorTest.BadStepInterpolator(y, true);
        interpolator.storeTime(0);
        interpolator.shift();
        interpolator.storeTime(1);

        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(bos);
        try {
            oos.writeObject(interpolator);
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (java.io.IOException ioe) {
            // expected behavior
            org.junit.Assert.assertEquals(0, ioe.getMessage().length());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail("wrong exception caught");
        }

    }

    private static class BadStepInterpolator extends org.apache.commons.math.ode.sampling.DummyStepInterpolator {
        @java.lang.SuppressWarnings("unused")
        public BadStepInterpolator() {
        }
        public BadStepInterpolator(double[] y, boolean forward) {
            super(y, new double[y.length], forward);
        }
        @java.lang.Override
        protected void doFinalize() throws 
        org.apache.commons.math.ode.DerivativeException {
            throw new org.apache.commons.math.ode.DerivativeException(null);
        }
    }

}