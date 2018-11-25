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
 * Testcase for UnivariateRealSolver.
 * Because Brent-Dekker is guaranteed to converge in less than the default
 * maximum iteration count due to bisection fallback, it is quite hard to
 * debug. I include measured iteration counts plus one in order to detect
 * regressions. On average Brent-Dekker should use 4..5 iterations for the
 * default absolute accuracy of 10E-8 for sinus and the quintic function around
 * zero, and 5..10 iterations for the other zeros.
 *
 * @version $Revision:670469 $ $Date:2008-06-23 10:01:38 +0200 (lun., 23 juin 2008) $
 */
public final class BrentSolverTest extends junit.framework.TestCase {

    public BrentSolverTest(java.lang.String name) {
        super(name);
    }

    @java.lang.Deprecated
    public void testDeprecated() throws org.apache.commons.math.MathException {
        // The sinus function is behaved well around the root at #pi. The second
        // order derivative is zero, which means linar approximating methods will
        // still converge quadratically.
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        double result;
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.BrentSolver(f);
        // Somewhat benign interval. The function is monotone.
        result = solver.solve(3, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 4 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 5));
        // Larger and somewhat less benign interval. The function is grows first.
        result = solver.solve(1, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 5 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 6));
        solver = new org.apache.commons.math.analysis.solvers.SecantSolver(f);
        result = solver.solve(3, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 4 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 5));
        result = solver.solve(1, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 5 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 6));
        junit.framework.Assert.assertEquals(result, solver.getResult(), 0);
    }

    public void testSinZero() throws org.apache.commons.math.MathException {
        // The sinus function is behaved well around the root at #pi. The second
        // order derivative is zero, which means linar approximating methods will
        // still converge quadratically.
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        double result;
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.BrentSolver();
        // Somewhat benign interval. The function is monotone.
        result = solver.solve(f, 3, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 4 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 5));
        // Larger and somewhat less benign interval. The function is grows first.
        result = solver.solve(f, 1, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 5 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 6));
        solver = new org.apache.commons.math.analysis.solvers.SecantSolver();
        result = solver.solve(f, 3, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 4 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 5));
        result = solver.solve(f, 1, 4);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, java.lang.Math.PI, solver.getAbsoluteAccuracy());
        // 5 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 6));
        junit.framework.Assert.assertEquals(result, solver.getResult(), 0);
    }

    public void testQuinticZero() throws org.apache.commons.math.MathException {
        // The quintic function has zeros at 0, +-0.5 and +-1.
        // Around the root of 0 the function is well behaved, with a second derivative
        // of zero a 0.
        // The other roots are less well to find, in particular the root at 1, because
        // the function grows fast for x>1.
        // The function has extrema (first derivative is zero) at 0.27195613 and 0.82221643,
        // intervals containing these values are harder for the solvers.
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.QuinticFunction();
        double result;
        // Brent-Dekker solver.
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.BrentSolver();
        // Symmetric bracket around 0. Test whether solvers can handle hitting
        // the root in the first iteration.
        result = solver.solve(f, (-0.2), 0.2);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 2));
        // 1 iterations on i586 JDK 1.4.1.
        // Asymmetric bracket around 0, just for fun. Contains extremum.
        result = solver.solve(f, (-0.1), 0.3);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        // 5 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 6));
        // Large bracket around 0. Contains two extrema.
        result = solver.solve(f, (-0.3), 0.45);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        // 6 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 7));
        // Benign bracket around 0.5, function is monotonous.
        result = solver.solve(f, 0.3, 0.7);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0.5, solver.getAbsoluteAccuracy());
        // 6 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 7));
        // Less benign bracket around 0.5, contains one extremum.
        result = solver.solve(f, 0.2, 0.6);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0.5, solver.getAbsoluteAccuracy());
        // 6 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 7));
        // Large, less benign bracket around 0.5, contains both extrema.
        result = solver.solve(f, 0.05, 0.95);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0.5, solver.getAbsoluteAccuracy());
        // 8 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 9));
        // Relatively benign bracket around 1, function is monotonous. Fast growth for x>1
        // is still a problem.
        result = solver.solve(f, 0.85, 1.25);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 8 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 9));
        // Less benign bracket around 1 with extremum.
        result = solver.solve(f, 0.8, 1.2);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 8 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 9));
        // Large bracket around 1. Monotonous.
        result = solver.solve(f, 0.85, 1.75);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 10 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 11));
        // Large bracket around 1. Interval contains extremum.
        result = solver.solve(f, 0.55, 1.45);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 7 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 8));
        // Very large bracket around 1 for testing fast growth behaviour.
        result = solver.solve(f, 0.85, 5);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 12 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 13));
        // Secant solver.
        solver = new org.apache.commons.math.analysis.solvers.SecantSolver();
        result = solver.solve(f, (-0.2), 0.2);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        // 1 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 2));
        result = solver.solve(f, (-0.1), 0.3);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        // 5 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 6));
        result = solver.solve(f, (-0.3), 0.45);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        // 6 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 7));
        result = solver.solve(f, 0.3, 0.7);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0.5, solver.getAbsoluteAccuracy());
        // 7 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 8));
        result = solver.solve(f, 0.2, 0.6);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0.5, solver.getAbsoluteAccuracy());
        // 6 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 7));
        result = solver.solve(f, 0.05, 0.95);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 0.5, solver.getAbsoluteAccuracy());
        // 8 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 9));
        result = solver.solve(f, 0.85, 1.25);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 10 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 11));
        result = solver.solve(f, 0.8, 1.2);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 8 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 9));
        result = solver.solve(f, 0.85, 1.75);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 14 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 15));
        // The followig is especially slow because the solver first has to reduce
        // the bracket to exclude the extremum. After that, convergence is rapide.
        result = solver.solve(f, 0.55, 1.45);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 7 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 8));
        result = solver.solve(f, 0.85, 5);
        // System.out.println(
        // "Root: " + result + " Iterations: " + solver.getIterationCount());
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        // 14 iterations on i586 JDK 1.4.1.
        junit.framework.Assert.assertTrue(((solver.getIterationCount()) <= 15));
        // Static solve method
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, (-0.2), 0.2);
        junit.framework.Assert.assertEquals(result, 0, solver.getAbsoluteAccuracy());
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, (-0.1), 0.3);
        junit.framework.Assert.assertEquals(result, 0, 1.0E-8);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, (-0.3), 0.45);
        junit.framework.Assert.assertEquals(result, 0, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.3, 0.7);
        junit.framework.Assert.assertEquals(result, 0.5, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.2, 0.6);
        junit.framework.Assert.assertEquals(result, 0.5, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.05, 0.95);
        junit.framework.Assert.assertEquals(result, 0.5, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.85, 1.25);
        junit.framework.Assert.assertEquals(result, 1.0, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.8, 1.2);
        junit.framework.Assert.assertEquals(result, 1.0, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.85, 1.75);
        junit.framework.Assert.assertEquals(result, 1.0, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.55, 1.45);
        junit.framework.Assert.assertEquals(result, 1.0, 1.0E-6);
        result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(f, 0.85, 5);
        junit.framework.Assert.assertEquals(result, 1.0, 1.0E-6);
    }

    public void testRootEndpoints() throws java.lang.Exception {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.BrentSolver();

        // endpoint is root
        double result = solver.solve(f, java.lang.Math.PI, 4);
        junit.framework.Assert.assertEquals(java.lang.Math.PI, result, solver.getAbsoluteAccuracy());

        result = solver.solve(f, 3, java.lang.Math.PI);
        junit.framework.Assert.assertEquals(java.lang.Math.PI, result, solver.getAbsoluteAccuracy());

        result = solver.solve(f, java.lang.Math.PI, 4, 3.5);
        junit.framework.Assert.assertEquals(java.lang.Math.PI, result, solver.getAbsoluteAccuracy());

        result = solver.solve(f, 3, java.lang.Math.PI, 3.07);
        junit.framework.Assert.assertEquals(java.lang.Math.PI, result, solver.getAbsoluteAccuracy());

    }

    public void testBadEndpoints() throws java.lang.Exception {
        org.apache.commons.math.analysis.UnivariateRealFunction f = new org.apache.commons.math.analysis.SinFunction();
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.BrentSolver();
        try {             // bad interval
            solver.solve(f, 1, (-1));
            junit.framework.Assert.fail("Expecting IllegalArgumentException - bad interval");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {             // no bracket
            solver.solve(f, 1, 1.5);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - non-bracketing");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {             // no bracket
            solver.solve(f, 1, 1.5, 1.2);
            junit.framework.Assert.fail("Expecting IllegalArgumentException - non-bracketing");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

    public void testInitialGuess() throws org.apache.commons.math.MathException {

        org.apache.commons.math.analysis.MonitoredFunction f = new org.apache.commons.math.analysis.MonitoredFunction(new org.apache.commons.math.analysis.QuinticFunction());
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = new org.apache.commons.math.analysis.solvers.BrentSolver();
        double result;

        // no guess
        result = solver.solve(f, 0.6, 7.0);
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        int referenceCallsCount = f.getCallsCount();
        junit.framework.Assert.assertTrue((referenceCallsCount >= 13));

        // invalid guess (it *is* a root, but outside of the range)
        try {
            result = solver.solve(f, 0.6, 7.0, 0.0);
            junit.framework.Assert.fail("an IllegalArgumentException was expected");
        } catch (java.lang.IllegalArgumentException iae) {
            // expected behaviour
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail(("wrong exception caught: " + (e.getMessage())));
        }

        // bad guess
        f.setCallsCount(0);
        result = solver.solve(f, 0.6, 7.0, 0.61);
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        junit.framework.Assert.assertTrue(((f.getCallsCount()) > referenceCallsCount));

        // good guess
        f.setCallsCount(0);
        result = solver.solve(f, 0.6, 7.0, 0.999999);
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        junit.framework.Assert.assertTrue(((f.getCallsCount()) < referenceCallsCount));

        // perfect guess
        f.setCallsCount(0);
        result = solver.solve(f, 0.6, 7.0, 1.0);
        junit.framework.Assert.assertEquals(result, 1.0, solver.getAbsoluteAccuracy());
        junit.framework.Assert.assertEquals(0, solver.getIterationCount());
        junit.framework.Assert.assertEquals(1, f.getCallsCount());

    }

}