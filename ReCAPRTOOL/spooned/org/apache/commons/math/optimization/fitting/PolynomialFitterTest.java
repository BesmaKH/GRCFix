/**
 * Licensed to the Apache Software Foundation (ASF) under one
 */ /**
 * or more contributor license agreements.  See the NOTICE file
 */ /**
 * distributed with this work for additional information
 */ /**
 * regarding copyright ownership.  The ASF licenses this file
 */ /**
 * to you under the Apache License, Version 2.0 (the
 */ /**
 * "License"); you may not use this file except in compliance
 */ /**
 * with the License.  You may obtain a copy of the License at
 */ /**
 *
 */ /**
 * http://www.apache.org/licenses/LICENSE-2.0
 */ /**
 *
 */ /**
 * Unless required by applicable law or agreed to in writing,
 */ /**
 * software distributed under the License is distributed on an
 */ /**
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 */ /**
 * KIND, either express or implied.  See the License for the
 */ /**
 * specific language governing permissions and limitations
 */ /**
 * under the License.
 */
package org.apache.commons.math.optimization.fitting; public class PolynomialFitterTest {     @org.junit.Test
    public void testNoError() throws org.apache.commons.math.optimization.OptimizationException {
        java.util.Random randomizer = new java.util.Random(64925784252L);
        for (int degree = 1; degree < 10; ++degree) {
            org.apache.commons.math.analysis.polynomials.PolynomialFunction p = buildRandomPolynomial(degree, randomizer);

            org.apache.commons.math.optimization.fitting.PolynomialFitter fitter = 
            new org.apache.commons.math.optimization.fitting.PolynomialFitter(degree, new org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer());
            for (int i = 0; i <= degree; ++i) {
                fitter.addObservedPoint(1.0, i, p.value(i));
            }

            org.apache.commons.math.analysis.polynomials.PolynomialFunction fitted = fitter.fit();

            for (double x = -1.0; x < 1.0; x += 0.01) {
                double error = (java.lang.Math.abs(((p.value(x)) - (fitted.value(x))))) / 
                (1.0 + (java.lang.Math.abs(p.value(x))));
                org.junit.Assert.assertEquals(0.0, error, 1.0E-6);
            }

        }

    }

    @org.junit.Test
    public void testSmallError() throws org.apache.commons.math.optimization.OptimizationException {
        java.util.Random randomizer = new java.util.Random(53882150042L);
        double maxError = 0;
        for (int degree = 0; degree < 10; ++degree) {
            org.apache.commons.math.analysis.polynomials.PolynomialFunction p = buildRandomPolynomial(degree, randomizer);

            org.apache.commons.math.optimization.fitting.PolynomialFitter fitter = 
            new org.apache.commons.math.optimization.fitting.PolynomialFitter(degree, new org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer());
            for (double x = -1.0; x < 1.0; x += 0.01) {
                fitter.addObservedPoint(1.0, x, 
                ((p.value(x)) + (0.1 * (randomizer.nextGaussian()))));
            }

            org.apache.commons.math.analysis.polynomials.PolynomialFunction fitted = fitter.fit();

            for (double x = -1.0; x < 1.0; x += 0.01) {
                double error = (java.lang.Math.abs(((p.value(x)) - (fitted.value(x))))) / 
                (1.0 + (java.lang.Math.abs(p.value(x))));
                maxError = java.lang.Math.max(maxError, error);
                org.junit.Assert.assertTrue(((java.lang.Math.abs(error)) < 0.1));
            }
        }
        org.junit.Assert.assertTrue((maxError > 0.01));

    }

    @org.junit.Test
    public void testRedundantSolvable() {
        // Levenberg-Marquardt should handle redundant information gracefully
        checkUnsolvableProblem(new org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer(), true);
    }

    @org.junit.Test
    public void testRedundantUnsolvable() {
        // Gauss-Newton should not be able to solve redundant information
        org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer = 
        new org.apache.commons.math.optimization.general.GaussNewtonOptimizer(true);
        checkUnsolvableProblem(optimizer, false);
    }

    private void checkUnsolvableProblem(org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer, 
    boolean solvable) {
        java.util.Random randomizer = new java.util.Random(1248788532L);
        for (int degree = 0; degree < 10; ++degree) {
            org.apache.commons.math.analysis.polynomials.PolynomialFunction p = buildRandomPolynomial(degree, randomizer);

            org.apache.commons.math.optimization.fitting.PolynomialFitter fitter = new org.apache.commons.math.optimization.fitting.PolynomialFitter(degree, optimizer);

            // reusing the same point over and over again does not bring
            // information, the problem cannot be solved in this case for
            // degrees greater than 1 (but one point is sufficient for
            // degree 0)
            for (double x = -1.0; x < 1.0; x += 0.01) {
                fitter.addObservedPoint(1.0, 0.0, p.value(0.0));
            }

            try {
                fitter.fit();
                org.junit.Assert.assertTrue((solvable || (degree == 0)));
            } catch (org.apache.commons.math.optimization.OptimizationException e) {
                org.junit.Assert.assertTrue(((!solvable) && (degree > 0)));
            }

        }

    }

    private org.apache.commons.math.analysis.polynomials.PolynomialFunction buildRandomPolynomial(int degree, java.util.Random randomizer) {
        final double[] coefficients = new double[degree + 1];
        for (int i = 0; i <= degree; ++i) {
            coefficients[i] = randomizer.nextGaussian();
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(coefficients);
    }

}