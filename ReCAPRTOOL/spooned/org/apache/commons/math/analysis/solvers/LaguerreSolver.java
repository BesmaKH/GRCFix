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
 * Implements the <a href="http://mathworld.wolfram.com/LaguerresMethod.html">
 * Laguerre's Method</a> for root finding of real coefficient polynomials.
 * For reference, see <b>A First Course in Numerical Analysis</b>,
 * ISBN 048641454X, chapter 8.
 * <p>
 * Laguerre's method is global in the sense that it can start with any initial
 * approximation and be able to solve all roots from that point.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public class LaguerreSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {

    /**
     * polynomial function to solve.
     *
     * @deprecated as of 2.0 the function is not stored anymore in the instance
     */     @java.lang.Deprecated     private final org.apache.commons.math.analysis.polynomials.PolynomialFunction p;

    /**
     * Construct a solver for the given function.
     *
     * @param f
     * 		function to solve
     * @throws IllegalArgumentException
     * 		if function is not polynomial
     * @deprecated as of 2.0 the function to solve is passed as an argument
     * to the {@link #solve(UnivariateRealFunction, double, double)} or
     * {@link UnivariateRealSolverImpl#solve(UnivariateRealFunction, double, double, double)}
     * method.
     */     @java.lang.Deprecated     public LaguerreSolver(org.apache.commons.math.analysis.UnivariateRealFunction f) throws 
    java.lang.IllegalArgumentException {
        super(f, 100, 1.0E-6);
        if (f instanceof org.apache.commons.math.analysis.polynomials.PolynomialFunction) {
            p = ((org.apache.commons.math.analysis.polynomials.PolynomialFunction) (f));
        }else {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.FUNCTION_NOT_POLYNOMIAL);
        }
    }

    /**
     * Construct a solver.
     */
    public LaguerreSolver() {
        super(100, 1.0E-6);
        p = null;
    }

    /**
     * Returns a copy of the polynomial function.
     *
     * @return a fresh copy of the polynomial function
     * @deprecated as of 2.0 the function is not stored anymore within the instance.
     */
    @java.lang.Deprecated
    public org.apache.commons.math.analysis.polynomials.PolynomialFunction getPolynomialFunction() {
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(p.getCoefficients());
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Deprecated     public double solve(final double min, final double max) throws 
    org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return solve(p, min, max);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Deprecated     public double solve(final double min, final double max, final double initial) throws 
    org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return solve(p, min, max, initial);
    }

    /**
     * Find a real root in the given interval with initial value.
     * <p>
     * Requires bracketing condition.</p>
     *
     * @param f
     * 		function to solve (must be polynomial)
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param initial
     * 		the start value to use
     * @return the point at which the function value is zero
     * @throws ConvergenceException
     * 		if the maximum iteration count is exceeded
     * 		or the solver detects convergence problems otherwise
     * @throws FunctionEvaluationException
     * 		if an error occurs evaluating the
     * 		function
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public double solve(final org.apache.commons.math.analysis.UnivariateRealFunction f, final double min, final double max, final double initial) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {         // check for zeros before verifying bracketing
        if ((f.value(min)) == 0.0) {             return min;}
        if ((f.value(max)) == 0.0) {
            return max;
        }
        if ((f.value(initial)) == 0.0) {
            return initial;
        }

        verifyBracketing(min, max, f);
        verifySequence(min, initial, max);
        if (isBracketing(min, initial, f)) {
            return solve(f, min, initial);
        }else {
            return solve(f, initial, max);
        }

    }

    /**
     * Find a real root in the given interval.
     * <p>
     * Despite the bracketing condition, the root returned by solve(Complex[],
     * Complex) may not be a real zero inside [min, max]. For example,
     * p(x) = x^3 + 1, min = -2, max = 2, initial = 0. We can either try
     * another initial value, or, as we did here, call solveAll() to obtain
     * all roots and pick up the one that we're looking for.</p>
     *
     * @param f
     * 		the function to solve
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @return the point at which the function value is zero
     * @throws ConvergenceException
     * 		if the maximum iteration count is exceeded
     * 		or the solver detects convergence problems otherwise
     * @throws FunctionEvaluationException
     * 		if an error occurs evaluating the
     * 		function
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public double solve(final org.apache.commons.math.analysis.UnivariateRealFunction f, final double min, final double max) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {         // check function type
        if (!(f instanceof org.apache.commons.math.analysis.polynomials.PolynomialFunction)) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.util.LocalizedFormats.FUNCTION_NOT_POLYNOMIAL);
        }

        // check for zeros before verifying bracketing
        if ((f.value(min)) == 0.0) {             return min;}
        if ((f.value(max)) == 0.0) {             return max;}
        verifyBracketing(min, max, f);

        double[] coefficients = ((org.apache.commons.math.analysis.polynomials.PolynomialFunction) (f)).getCoefficients();
        org.apache.commons.math.complex.Complex[] c = new org.apache.commons.math.complex.Complex[coefficients.length];
        for (int i = 0; i < (coefficients.length); i++) {
            c[i] = new org.apache.commons.math.complex.Complex(coefficients[i], 0.0);
        }
        org.apache.commons.math.complex.Complex initial = new org.apache.commons.math.complex.Complex((0.5 * (min + max)), 0.0);
        org.apache.commons.math.complex.Complex z = solve(c, initial);
        if (isRootOK(min, max, z)) {
            setResult(z.getReal(), iterationCount);
            return result;
        }

        // solve all roots and select the one we're seeking
        org.apache.commons.math.complex.Complex[] root = solveAll(c, initial);
        for (int i = 0; i < (root.length); i++) {
            if (isRootOK(min, max, root[i])) {
                setResult(root[i].getReal(), iterationCount);
                return result;
            }
        }

        // should never happen
        throw new org.apache.commons.math.ConvergenceException();
    }

    /**
     * Returns true iff the given complex root is actually a real zero
     * in the given interval, within the solver tolerance level.
     *
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param z
     * 		the complex root
     * @return true iff z is the sought-after real zero
     */     protected boolean isRootOK(double min, double max, org.apache.commons.math.complex.Complex z) {         double tolerance = java.lang.Math.max(((relativeAccuracy) * (z.abs())), absoluteAccuracy);         return (isSequence(min, z.getReal(), max)) && 
        (((java.lang.Math.abs(z.getImaginary())) <= tolerance) || 
        ((z.abs()) <= (functionValueAccuracy)));
    }

    /**
     * Find all complex roots for the polynomial with the given coefficients,
     * starting from the given initial value.
     *
     * @param coefficients
     * 		the polynomial coefficients array
     * @param initial
     * 		the start value to use
     * @return the point at which the function value is zero
     * @throws ConvergenceException
     * 		if the maximum iteration count is exceeded
     * 		or the solver detects convergence problems otherwise
     * @throws FunctionEvaluationException
     * 		if an error occurs evaluating the
     * 		function
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] solveAll(double[] coefficients, double initial) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {         org.apache.commons.math.complex.Complex[] c = new org.apache.commons.math.complex.Complex[coefficients.length];         org.apache.commons.math.complex.Complex z = new org.apache.commons.math.complex.Complex(initial, 0.0);
        for (int i = 0; i < (c.length); i++) {
            c[i] = new org.apache.commons.math.complex.Complex(coefficients[i], 0.0);
        }
        return solveAll(c, z);
    }

    /**
     * Find all complex roots for the polynomial with the given coefficients,
     * starting from the given initial value.
     *
     * @param coefficients
     * 		the polynomial coefficients array
     * @param initial
     * 		the start value to use
     * @return the point at which the function value is zero
     * @throws MaxIterationsExceededException
     * 		if the maximum iteration count is exceeded
     * 		or the solver detects convergence problems otherwise
     * @throws FunctionEvaluationException
     * 		if an error occurs evaluating the
     * 		function
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] solveAll(org.apache.commons.math.complex.Complex[] coefficients, org.apache.commons.math.complex.Complex initial) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.MaxIterationsExceededException {         int n = (coefficients.length) - 1;         int iterationCount = 0;
        if (n < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NON_POSITIVE_POLYNOMIAL_DEGREE, n);
        }
        org.apache.commons.math.complex.Complex[] c = new org.apache.commons.math.complex.Complex[n + 1];// coefficients for deflated polynomial
        for (int i = 0; i <= n; i++) {
            c[i] = coefficients[i];
        }

        // solve individual root successively
        org.apache.commons.math.complex.Complex[] root = new org.apache.commons.math.complex.Complex[n];
        for (int i = 0; i < n; i++) {
            org.apache.commons.math.complex.Complex[] subarray = new org.apache.commons.math.complex.Complex[(n - i) + 1];
            java.lang.System.arraycopy(c, 0, subarray, 0, subarray.length);
            root[i] = solve(subarray, initial);
            // polynomial deflation using synthetic division
            org.apache.commons.math.complex.Complex newc = c[(n - i)];
            org.apache.commons.math.complex.Complex oldc = null;
            for (int j = (n - i) - 1; j >= 0; j--) {
                oldc = c[j];
                c[j] = newc;
                newc = oldc.add(newc.multiply(root[i]));
            }
            iterationCount += this.iterationCount;
        }

        resultComputed = true;
        this.iterationCount = iterationCount;
        return root;
    }

    /**
     * Find a complex root for the polynomial with the given coefficients,
     * starting from the given initial value.
     *
     * @param coefficients
     * 		the polynomial coefficients array
     * @param initial
     * 		the start value to use
     * @return the point at which the function value is zero
     * @throws MaxIterationsExceededException
     * 		if the maximum iteration count is exceeded
     * 		or the solver detects convergence problems otherwise
     * @throws FunctionEvaluationException
     * 		if an error occurs evaluating the
     * 		function
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex solve(org.apache.commons.math.complex.Complex[] coefficients, org.apache.commons.math.complex.Complex initial) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.MaxIterationsExceededException {         int n = (coefficients.length) - 1;         if (n < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NON_POSITIVE_POLYNOMIAL_DEGREE, n);
        }
        org.apache.commons.math.complex.Complex N = new org.apache.commons.math.complex.Complex(n, 0.0);
        org.apache.commons.math.complex.Complex N1 = new org.apache.commons.math.complex.Complex((n - 1), 0.0);

        int i = 1;
        org.apache.commons.math.complex.Complex pv = null;
        org.apache.commons.math.complex.Complex dv = null;
        org.apache.commons.math.complex.Complex d2v = null;
        org.apache.commons.math.complex.Complex G = null;
        org.apache.commons.math.complex.Complex G2 = null;
        org.apache.commons.math.complex.Complex H = null;
        org.apache.commons.math.complex.Complex delta = null;
        org.apache.commons.math.complex.Complex denominator = null;
        org.apache.commons.math.complex.Complex z = initial;
        org.apache.commons.math.complex.Complex oldz = new org.apache.commons.math.complex.Complex(java.lang.Double.POSITIVE_INFINITY, java.lang.Double.POSITIVE_INFINITY);
        while (i <= (maximalIterationCount)) {
            // Compute pv (polynomial value), dv (derivative value), and
            // d2v (second derivative value) simultaneously.
            pv = coefficients[n];
            dv = org.apache.commons.math.complex.Complex.ZERO;
            d2v = org.apache.commons.math.complex.Complex.ZERO;
            for (int j = n - 1; j >= 0; j--) {
                d2v = dv.add(z.multiply(d2v));
                dv = pv.add(z.multiply(dv));
                pv = coefficients[j].add(z.multiply(pv));
            }
            d2v = d2v.multiply(new org.apache.commons.math.complex.Complex(2.0, 0.0));

            // check for convergence
            double tolerance = java.lang.Math.max(((relativeAccuracy) * (z.abs())), 
            absoluteAccuracy);
            if ((z.subtract(oldz).abs()) <= tolerance) {
                resultComputed = true;
                iterationCount = i;
                return z;
            }
            if ((pv.abs()) <= (functionValueAccuracy)) {
                resultComputed = true;
                iterationCount = i;
                return z;
            }

            // now pv != 0, calculate the new approximation
            G = dv.divide(pv);
            G2 = G.multiply(G);
            H = G2.subtract(d2v.divide(pv));
            delta = N1.multiply(N.multiply(H).subtract(G2));
            // choose a denominator larger in magnitude
            org.apache.commons.math.complex.Complex deltaSqrt = delta.sqrt();
            org.apache.commons.math.complex.Complex dplus = G.add(deltaSqrt);
            org.apache.commons.math.complex.Complex dminus = G.subtract(deltaSqrt);
            denominator = ((dplus.abs()) > (dminus.abs())) ? dplus : dminus;
            // Perturb z if denominator is zero, for instance,
            // p(x) = x^3 + 1, z = 0.
            if (denominator.equals(new org.apache.commons.math.complex.Complex(0.0, 0.0))) {
                z = z.add(new org.apache.commons.math.complex.Complex(absoluteAccuracy, absoluteAccuracy));
                oldz = new org.apache.commons.math.complex.Complex(java.lang.Double.POSITIVE_INFINITY, 
                java.lang.Double.POSITIVE_INFINITY);
            }else {
                oldz = z;
                z = z.subtract(N.divide(denominator));
            }
            i++;
        } 
        throw new org.apache.commons.math.MaxIterationsExceededException(maximalIterationCount);
    }
}