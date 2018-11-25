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















public class MultiStartMultivariateRealOptimizerTest {

    @org.junit.Test
    public void testRosenbrock() throws 
    org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {

        org.apache.commons.math.optimization.MultiStartMultivariateRealOptimizerTest.Rosenbrock rosenbrock = new org.apache.commons.math.optimization.MultiStartMultivariateRealOptimizerTest.Rosenbrock();
        org.apache.commons.math.optimization.direct.NelderMead underlying = new org.apache.commons.math.optimization.direct.NelderMead();
        underlying.setStartConfiguration(new double[][]{ 
        new double[]{ -1.2, 1.0 }, new double[]{ 0.9, 1.2 }, new double[]{ 3.5, -2.3 } });

        org.apache.commons.math.random.JDKRandomGenerator g = new org.apache.commons.math.random.JDKRandomGenerator();
        g.setSeed(16069223052L);
        org.apache.commons.math.random.RandomVectorGenerator generator = 
        new org.apache.commons.math.random.UncorrelatedRandomVectorGenerator(2, new org.apache.commons.math.random.GaussianRandomGenerator(g));
        org.apache.commons.math.optimization.MultiStartMultivariateRealOptimizer optimizer = 
        new org.apache.commons.math.optimization.MultiStartMultivariateRealOptimizer(underlying, 10, generator);
        optimizer.setConvergenceChecker(new org.apache.commons.math.optimization.SimpleScalarValueChecker((-1), 0.001));
        optimizer.setMaxIterations(100);
        org.apache.commons.math.optimization.RealPointValuePair optimum = 
        optimizer.optimize(rosenbrock, org.apache.commons.math.optimization.GoalType.MINIMIZE, new double[]{ -1.2, 1.0 });

        org.junit.Assert.assertEquals(rosenbrock.getCount(), optimizer.getEvaluations());
        org.junit.Assert.assertTrue(((optimizer.getEvaluations()) > 20));
        org.junit.Assert.assertTrue(((optimizer.getEvaluations()) < 250));
        org.junit.Assert.assertTrue(((optimum.getValue()) < 8.0E-4));

    }

    private static class Rosenbrock implements org.apache.commons.math.analysis.MultivariateRealFunction {

        private int count;

        public Rosenbrock() {
            count = 0;
        }

        public double value(double[] x) throws org.apache.commons.math.FunctionEvaluationException {
            ++(count);
            double a = (x[1]) - ((x[0]) * (x[0]));
            double b = 1.0 - (x[0]);
            return ((100 * a) * a) + (b * b);
        }

        public int getCount() {
            return count;
        }

    }

}