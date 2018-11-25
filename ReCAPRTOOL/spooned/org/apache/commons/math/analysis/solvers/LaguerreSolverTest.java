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
 * Testcase for Laguerre solver.
 * <p>
 * Laguerre's method is very efficient in solving polynomials. Test runs
 * show that for a default absolute accuracy of 1E-6, it generally takes
 * less than 5 iterations to find one root, provided solveAll() is not
 * invoked, and 15 to 20 iterations to find all roots for quintic function.
 *
 * @version $Revision$ $Date$
 */
public final class LaguerreSolverTest extends junit.framework.TestCase {

    /**
     * Test deprecated APIs.
     */
    @java.lang.Deprecated
    public void testDeprecated() throws org.apache.commons.math.MathException {
        double min;         double max;         double expected;         double result;         double tolerance;

        // p(x) = 4x - 1
        double[] coefficients = new double[]{ -1.0, 4.0 };
        org.apache.commons.math.analysis.polynomials.PolynomialFunction f = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.LaguerreSolver(f);

        min = 0.0;         max = 1.0;         expected = 0.25;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the linear function.
     */
    public void testLinearFunction() throws org.apache.commons.math.MathException {
        double min;         double max;         double expected;         double result;         double tolerance;

        // p(x) = 4x - 1
        double[] coefficients = new double[]{ -1.0, 4.0 };
        org.apache.commons.math.analysis.polynomials.PolynomialFunction f = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.LaguerreSolver();

        min = 0.0;         max = 1.0;         expected = 0.25;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the quadratic function.
     */
    public void testQuadraticFunction() throws org.apache.commons.math.MathException {
        double min;         double max;         double expected;         double result;         double tolerance;

        // p(x) = 2x^2 + 5x - 3 = (x+3)(2x-1)
        double[] coefficients = new double[]{ -3.0, 5.0, 2.0 };
        org.apache.commons.math.analysis.polynomials.PolynomialFunction f = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.LaguerreSolver();

        min = 0.0;         max = 2.0;         expected = 0.5;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -4.0;         max = -1.0;         expected = -3.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the quintic function.
     */
    public void testQuinticFunction() throws org.apache.commons.math.MathException {
        double min;         double max;         double expected;         double result;         double tolerance;

        // p(x) = x^5 - x^4 - 12x^3 + x^2 - x - 12 = (x+1)(x+3)(x-4)(x^2-x+1)
        double[] coefficients = new double[]{ -12.0, -1.0, 1.0, -12.0, -1.0, 1.0 };
        org.apache.commons.math.analysis.polynomials.PolynomialFunction f = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.LaguerreSolver();

        min = -2.0;         max = 2.0;         expected = -1.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = -5.0;         max = -2.5;         expected = -3.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);

        min = 3.0;         max = 6.0;         expected = 4.0;
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs((expected * (solver.getRelativeAccuracy()))));
        result = solver.solve(f, min, max);
        junit.framework.Assert.assertEquals(expected, result, tolerance);
    }

    /**
     * Test of solver for the quintic function using solveAll().
     */
    public void testQuinticFunction2() throws org.apache.commons.math.MathException {
        double initial = 0.0;         double tolerance;
        org.apache.commons.math.complex.Complex expected;         org.apache.commons.math.complex.Complex[] result;

        // p(x) = x^5 + 4x^3 + x^2 + 4 = (x+1)(x^2-x+1)(x^2+4)
        double[] coefficients = new double[]{ 4.0, 0.0, 1.0, 4.0, 0.0, 1.0 };
        org.apache.commons.math.analysis.solvers.LaguerreSolver solver = new org.apache.commons.math.analysis.solvers.LaguerreSolver();
        result = solver.solveAll(coefficients, initial);

        expected = new org.apache.commons.math.complex.Complex(0.0, (-2.0));
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs(((expected.abs()) * (solver.getRelativeAccuracy()))));
        org.apache.commons.math.TestUtils.assertContains(result, expected, tolerance);

        expected = new org.apache.commons.math.complex.Complex(0.0, 2.0);
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs(((expected.abs()) * (solver.getRelativeAccuracy()))));
        org.apache.commons.math.TestUtils.assertContains(result, expected, tolerance);

        expected = new org.apache.commons.math.complex.Complex(0.5, (0.5 * (java.lang.Math.sqrt(3.0))));
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs(((expected.abs()) * (solver.getRelativeAccuracy()))));
        org.apache.commons.math.TestUtils.assertContains(result, expected, tolerance);

        expected = new org.apache.commons.math.complex.Complex((-1.0), 0.0);
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs(((expected.abs()) * (solver.getRelativeAccuracy()))));
        org.apache.commons.math.TestUtils.assertContains(result, expected, tolerance);

        expected = new org.apache.commons.math.complex.Complex(0.5, ((-0.5) * (java.lang.Math.sqrt(3.0))));
        tolerance = java.lang.Math.max(solver.getAbsoluteAccuracy(), 
        java.lang.Math.abs(((expected.abs()) * (solver.getRelativeAccuracy()))));
        org.apache.commons.math.TestUtils.assertContains(result, expected, tolerance);
    }

    /**
     * Test of parameters for the solver.
     */
    public void testParameters() throws java.lang.Exception {
        double[] coefficients = new double[]{ -3.0, 5.0, 2.0 };
        org.apache.commons.math.analysis.polynomials.PolynomialFunction f = new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.LaguerreSolver();

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
        try {
            // bad function
            solver.solve(new org.apache.commons.math.analysis.SinFunction(), (-1), 1);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - bad function");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }
}