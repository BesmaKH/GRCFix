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




















/**
 * <p>Some of the unit tests are re-implementations of the MINPACK <a
 * href="http://www.netlib.org/minpack/ex/file17">file17</a> and <a
 * href="http://www.netlib.org/minpack/ex/file22">file22</a> test files.
 * The redistribution policy for MINPACK is available <a
 * href="http://www.netlib.org/minpack/disclaimer">here</a>, for
 * convenience, it is reproduced below.</p>
 *
 * <table border="0" width="80%" cellpadding="10" align="center" bgcolor="#E0E0E0">
 * <tr><td>
 *    Minpack Copyright Notice (1999) University of Chicago.
 *    All rights reserved
 * </td></tr>
 * <tr><td>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * <ol>
 *  <li>Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.</li>
 * <li>Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.</li>
 * <li>The end-user documentation included with the redistribution, if any,
 *     must include the following acknowledgment:
 *     <code>This product includes software developed by the University of
 *           Chicago, as Operator of Argonne National Laboratory.</code>
 *     Alternately, this acknowledgment may appear in the software itself,
 *     if and wherever such third-party acknowledgments normally appear.</li>
 * <li><strong>WARRANTY DISCLAIMER. THE SOFTWARE IS SUPPLIED "AS IS"
 *     WITHOUT WARRANTY OF ANY KIND. THE COPYRIGHT HOLDER, THE
 *     UNITED STATES, THE UNITED STATES DEPARTMENT OF ENERGY, AND
 *     THEIR EMPLOYEES: (1) DISCLAIM ANY WARRANTIES, EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO ANY IMPLIED WARRANTIES
 *     OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE
 *     OR NON-INFRINGEMENT, (2) DO NOT ASSUME ANY LEGAL LIABILITY
 *     OR RESPONSIBILITY FOR THE ACCURACY, COMPLETENESS, OR
 *     USEFULNESS OF THE SOFTWARE, (3) DO NOT REPRESENT THAT USE OF
 *     THE SOFTWARE WOULD NOT INFRINGE PRIVATELY OWNED RIGHTS, (4)
 *     DO NOT WARRANT THAT THE SOFTWARE WILL FUNCTION
 *     UNINTERRUPTED, THAT IT IS ERROR-FREE OR THAT ANY ERRORS WILL
 *     BE CORRECTED.</strong></li>
 * <li><strong>LIMITATION OF LIABILITY. IN NO EVENT WILL THE COPYRIGHT
 *     HOLDER, THE UNITED STATES, THE UNITED STATES DEPARTMENT OF
 *     ENERGY, OR THEIR EMPLOYEES: BE LIABLE FOR ANY INDIRECT,
 *     INCIDENTAL, CONSEQUENTIAL, SPECIAL OR PUNITIVE DAMAGES OF
 *     ANY KIND OR NATURE, INCLUDING BUT NOT LIMITED TO LOSS OF
 *     PROFITS OR LOSS OF DATA, FOR ANY REASON WHATSOEVER, WHETHER
 *     SUCH LIABILITY IS ASSERTED ON THE BASIS OF CONTRACT, TORT
 *     (INCLUDING NEGLIGENCE OR STRICT LIABILITY), OR OTHERWISE,
 *     EVEN IF ANY OF SAID PARTIES HAS BEEN WARNED OF THE
 *     POSSIBILITY OF SUCH LOSS OR DAMAGES.</strong></li>
 * <ol></td></tr>
 * </table>
 *
 * @author Argonne National Laboratory. MINPACK project. March 1980 (original fortran minpack tests)
 * @author Burton S. Garbow (original fortran minpack tests)
 * @author Kenneth E. Hillstrom (original fortran minpack tests)
 * @author Jorge J. More (original fortran minpack tests)
 * @author Luc Maisonobe (non-minpack tests and minpack tests Java translation)
 */
public class MultiStartDifferentiableMultivariateVectorialOptimizerTest {

    @org.junit.Test
    public void testTrivial() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        org.apache.commons.math.optimization.MultiStartDifferentiableMultivariateVectorialOptimizerTest.LinearProblem problem = 
        new org.apache.commons.math.optimization.MultiStartDifferentiableMultivariateVectorialOptimizerTest.LinearProblem(new double[][]{ new double[]{ 2 } }, new double[]{ 3 });
        org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer underlyingOptimizer = 
        new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(true);
        org.apache.commons.math.random.JDKRandomGenerator g = new org.apache.commons.math.random.JDKRandomGenerator();
        g.setSeed(16069223052L);
        org.apache.commons.math.random.RandomVectorGenerator generator = 
        new org.apache.commons.math.random.UncorrelatedRandomVectorGenerator(1, new org.apache.commons.math.random.GaussianRandomGenerator(g));
        org.apache.commons.math.optimization.MultiStartDifferentiableMultivariateVectorialOptimizer optimizer = 
        new org.apache.commons.math.optimization.MultiStartDifferentiableMultivariateVectorialOptimizer(underlyingOptimizer, 
        10, generator);
        optimizer.setMaxIterations(100);
        optimizer.setConvergenceChecker(new org.apache.commons.math.optimization.SimpleVectorialValueChecker(1.0E-6, 1.0E-6));

        // no optima before first optimization attempt
        try {
            optimizer.getOptima();
            org.junit.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalStateException ise) {
            // expected
        }
        org.apache.commons.math.optimization.VectorialPointValuePair optimum = 
        optimizer.optimize(problem, problem.target, new double[]{ 1 }, new double[]{ 0 });
        org.junit.Assert.assertEquals(1.5, optimum.getPoint()[0], 1.0E-10);
        org.junit.Assert.assertEquals(3.0, optimum.getValue()[0], 1.0E-10);
        org.apache.commons.math.optimization.VectorialPointValuePair[] optima = optimizer.getOptima();
        org.junit.Assert.assertEquals(10, optima.length);
        for (int i = 0; i < (optima.length); ++i) {
            org.junit.Assert.assertEquals(1.5, optima[i].getPoint()[0], 1.0E-10);
            org.junit.Assert.assertEquals(3.0, optima[i].getValue()[0], 1.0E-10);
        }
        org.junit.Assert.assertTrue(((optimizer.getEvaluations()) > 20));
        org.junit.Assert.assertTrue(((optimizer.getEvaluations()) < 50));
        org.junit.Assert.assertTrue(((optimizer.getIterations()) > 20));
        org.junit.Assert.assertTrue(((optimizer.getIterations()) < 50));
        org.junit.Assert.assertTrue(((optimizer.getJacobianEvaluations()) > 20));
        org.junit.Assert.assertTrue(((optimizer.getJacobianEvaluations()) < 50));
        org.junit.Assert.assertEquals(100, optimizer.getMaxIterations());
    }

    @org.junit.Test(expected = org.apache.commons.math.optimization.OptimizationException.class)
    public void testNoOptimum() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer underlyingOptimizer = 
        new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(true);
        org.apache.commons.math.random.JDKRandomGenerator g = new org.apache.commons.math.random.JDKRandomGenerator();
        g.setSeed(12373523445L);
        org.apache.commons.math.random.RandomVectorGenerator generator = 
        new org.apache.commons.math.random.UncorrelatedRandomVectorGenerator(1, new org.apache.commons.math.random.GaussianRandomGenerator(g));
        org.apache.commons.math.optimization.MultiStartDifferentiableMultivariateVectorialOptimizer optimizer = 
        new org.apache.commons.math.optimization.MultiStartDifferentiableMultivariateVectorialOptimizer(underlyingOptimizer, 
        10, generator);
        optimizer.setMaxIterations(100);
        optimizer.setConvergenceChecker(new org.apache.commons.math.optimization.SimpleVectorialValueChecker(1.0E-6, 1.0E-6));
        optimizer.optimize(new org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction() {
            public org.apache.commons.math.analysis.MultivariateMatrixFunction jacobian() {
                return null;
            }
            public double[] value(double[] point) throws org.apache.commons.math.FunctionEvaluationException {
                throw new org.apache.commons.math.FunctionEvaluationException(point[0]);
            }
        }, new double[]{ 2 }, new double[]{ 1 }, new double[]{ 0 });
    }

    private static class LinearProblem implements java.io.Serializable , org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction {

        private static final long serialVersionUID = -8804268799379350190L;
        final org.apache.commons.math.linear.RealMatrix factors;
        final double[] target;
        public LinearProblem(double[][] factors, double[] target) {
            this.factors = new org.apache.commons.math.linear.BlockRealMatrix(factors);
            this.target = target;
        }

        public double[] value(double[] variables) {
            return factors.operate(variables);
        }

        public org.apache.commons.math.analysis.MultivariateMatrixFunction jacobian() {
            return new org.apache.commons.math.analysis.MultivariateMatrixFunction() {
                private static final long serialVersionUID = -8387467946663627585L;
                public double[][] value(double[] point) {
                    return factors.getData();
                }
            };
        }

    }

}