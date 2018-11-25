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
package org.apache.commons.math.optimization;














public class MultiStartUnivariateRealOptimizerTest {

    @org.junit.Test
    public void testSinMin() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.optimization.UnivariateRealOptimizer underlying = new org.apache.commons.math.optimization.univariate.BrentOptimizer();
        org.apache.commons.math.random.JDKRandomGenerator g = new org.apache.commons.math.random.JDKRandomGenerator();
        g.setSeed(44428400075L);
        org.apache.commons.math.optimization.MultiStartUnivariateRealOptimizer minimizer = 
        new org.apache.commons.math.optimization.MultiStartUnivariateRealOptimizer(underlying, 10, g);
        minimizer.optimize(f, org.apache.commons.math.optimization.GoalType.MINIMIZE, (-100.0), 100.0);
        double[] optima = minimizer.getOptima();
        double[] optimaValues = minimizer.getOptimaValues();
        for (int i = 1; i < (optima.length); ++i) {
            double d = ((optima[i]) - (optima[(i - 1)])) / (2 * (java.lang.Math.PI));
            org.junit.Assert.assertTrue(((java.lang.Math.abs((d - (java.lang.Math.rint(d))))) < 1.0E-8));
            org.junit.Assert.assertEquals((-1.0), f.value(optima[i]), 1.0E-10);
            org.junit.Assert.assertEquals(f.value(optima[i]), optimaValues[i], 1.0E-10);
        }
        org.junit.Assert.assertTrue(((minimizer.getEvaluations()) > 2900));
        org.junit.Assert.assertTrue(((minimizer.getEvaluations()) < 3100));
    }

    @org.junit.Test
    public void testQuinticMin() throws org.apache.commons.math.MathException {
        // The quintic function has zeros at 0, +-0.5 and +-1.
        // The function has extrema (first derivative is zero) at 0.27195613 and 0.82221643,
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.QuinticFunction();
        org.apache.commons.math.optimization.UnivariateRealOptimizer underlying = new org.apache.commons.math.optimization.univariate.BrentOptimizer();
        org.apache.commons.math.random.JDKRandomGenerator g = new org.apache.commons.math.random.JDKRandomGenerator();
        g.setSeed(4312000053L);
        org.apache.commons.math.optimization.MultiStartUnivariateRealOptimizer minimizer = 
        new org.apache.commons.math.optimization.MultiStartUnivariateRealOptimizer(underlying, 5, g);
        minimizer.setAbsoluteAccuracy((10 * (minimizer.getAbsoluteAccuracy())));
        minimizer.setRelativeAccuracy((10 * (minimizer.getRelativeAccuracy())));

        try {
            minimizer.getOptima();
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalStateException ise) {
            // expected
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail("wrong exception caught");
        }
        try {
            minimizer.getOptimaValues();
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalStateException ise) {
            // expected
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail("wrong exception caught");
        }

        org.junit.Assert.assertEquals((-0.27195612846834), minimizer.optimize(f, org.apache.commons.math.optimization.GoalType.MINIMIZE, (-0.3), (-0.2)), 1.0E-13);
        org.junit.Assert.assertEquals((-0.27195612846834), minimizer.getResult(), 1.0E-13);
        org.junit.Assert.assertEquals((-0.04433426954946), minimizer.getFunctionValue(), 1.0E-13);

        double[] optima = minimizer.getOptima();
        double[] optimaValues = minimizer.getOptimaValues();
        for (int i = 0; i < (optima.length); ++i) {
            org.junit.Assert.assertEquals(f.value(optima[i]), optimaValues[i], 1.0E-10);
        }

        org.junit.Assert.assertTrue(((minimizer.getEvaluations()) >= 510));
        org.junit.Assert.assertTrue(((minimizer.getEvaluations()) <= 530));
        org.junit.Assert.assertTrue(((minimizer.getIterationCount()) >= 150));
        org.junit.Assert.assertTrue(((minimizer.getIterationCount()) <= 170));

    }

}