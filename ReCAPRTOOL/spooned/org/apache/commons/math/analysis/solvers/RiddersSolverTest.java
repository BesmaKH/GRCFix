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
package org.apache.commons.math.analysis.solvers;









/**
 * Testcase for Ridders solver.
 * <p>
 * Ridders' method converges superlinearly, more specific, its rate of
 * convergence is sqrt(2). Test runs show that for a default absolute
 * accuracy of 1E-6, it generally takes less than 5 iterations for close
 * initial bracket and 5 to 10 iterations for distant initial bracket
 * to converge.
 *
 * @version $Revision$ $Date$
 */
public final class RiddersSolverTest extends junit.framework.TestCase {

    /**
     * Test the deprecated APIs.
     */
    @java.lang.Deprecated
    public void testDeprecated() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.RiddersSolver(f);
        double min;         double max;         double expected;         double result;         double tolerance;

        min = 3.0;         max = 4.0;         expected = java.lang.Math.PI;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -1.0;         max = 1.5;         expected = 0.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the sine function.
     */
    public void testSinFunction() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.RiddersSolver();
        double min;         double max;         double expected;         double result;         double tolerance;

        min = 3.0;         max = 4.0;         expected = java.lang.Math.PI;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -1.0;         max = 1.5;         expected = 0.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the quintic function.
     */
    public void testQuinticFunction() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.QuinticFunction();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.RiddersSolver();
        double min;         double max;         double expected;         double result;         double tolerance;

        min = -0.4;         max = 0.2;         expected = 0.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = 0.75;         max = 1.5;         expected = 1.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -0.9;         max = -0.2;         expected = -0.5;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the exponential function.
     */
    public void testExpm1Function() throws org.apache.commons.math.MathException {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.Expm1Function();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.RiddersSolver();
        double min;         double max;         double expected;         double result;         double tolerance;

        min = -1.0;         max = 2.0;         expected = 0.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -20.0;         max = 10.0;         expected = 0.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -50.0;         max = 100.0;         expected = 0.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of parameters for the solver.
     */
    public void testParameters() throws java.lang.Exception {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.RiddersSolver();

        try {
            // bad interval
            solver.solve(f, 1, (-1));
            junit.framework.Assert.fail("Expecting IllegalArgumentException - bad interval");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {
            // no bracketing
            solver.solve(f, 2, 3);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - no bracketing");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }
}