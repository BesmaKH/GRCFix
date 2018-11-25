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
package org.apache.commons.math.optimization.fitting;








/**
 * This class implements a curve fitting specialized for sinusoids.
 * <p>Harmonic fitting is a very simple case of curve fitting. The
 * estimated coefficients are the amplitude a, the pulsation &omega; and
 * the phase &phi;: <code>f (t) = a cos (&omega; t + &phi;)</code>. They are
 * searched by a least square estimator initialized with a rough guess
 * based on integrals.</p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class HarmonicFitter {
    /**
     * Fitter for the coefficients.
     */     private final org.apache.commons.math.optimization.fitting.CurveFitter fitter;
    /**
     * Values for amplitude, pulsation &omega; and phase &phi;.
     */     private double[] parameters;
    /**
     * Simple constructor.
     *
     * @param optimizer
     * 		optimizer to use for the fitting
     */     public HarmonicFitter(final org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer) {         this.fitter = new org.apache.commons.math.optimization.fitting.CurveFitter(optimizer);         parameters = null;
    }

    /**
     * Simple constructor.
     * <p>This constructor can be used when a first guess of the
     * coefficients is already known.</p>
     *
     * @param optimizer
     * 		optimizer to use for the fitting
     * @param initialGuess
     * 		guessed values for amplitude (index 0),
     * 		pulsation &omega; (index 1) and phase &phi; (index 2)
     */     public HarmonicFitter(final org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer, final double[] initialGuess) {         this.fitter = new org.apache.commons.math.optimization.fitting.CurveFitter(optimizer);         this.parameters = initialGuess.clone();
    }

    /**
     * Add an observed weighted (x,y) point to the sample.
     *
     * @param weight
     * 		weight of the observed point in the fit
     * @param x
     * 		abscissa of the point
     * @param y
     * 		observed value of the point at x, after fitting we should
     * 		have P(x) as close as possible to this value
     */     public void addObservedPoint(double weight, double x, double y) {         fitter.addObservedPoint(weight, x, y);}     /**
     * Fit an harmonic function to the observed points.
     *
     * @return harmonic function best fitting the observed points
     * @throws OptimizationException
     * 		if the sample is too short or if
     * 		the first guess cannot be computed
     */     public org.apache.commons.math.optimization.fitting.HarmonicFunction fit() throws org.apache.commons.math.optimization.OptimizationException {         try {
            // shall we compute the first guess of the parameters ourselves ?
            if ((parameters) == null) {
                final org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] observations = fitter.getObservations();
                if ((observations.length) < 4) {
                    throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.util.LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, 
                    observations.length, 4);
                }

                org.apache.commons.math.optimization.fitting.HarmonicCoefficientsGuesser guesser = new org.apache.commons.math.optimization.fitting.HarmonicCoefficientsGuesser(observations);
                guesser.guess();
                parameters = new double[]{ 
                guesser.getGuessedAmplitude(), 
                guesser.getGuessedPulsation(), 
                guesser.getGuessedPhase() };


            }

            double[] fitted = fitter.fit(new org.apache.commons.math.optimization.fitting.HarmonicFitter.ParametricHarmonicFunction(), parameters);
            return new org.apache.commons.math.optimization.fitting.HarmonicFunction(fitted[0], fitted[1], fitted[2]);

        } catch (org.apache.commons.math.FunctionEvaluationException fee) {
            // this should never happen
            throw org.apache.commons.math.MathRuntimeException.createInternalError(fee);
        }
    }

    /**
     * Parametric harmonic function.
     */     private static class ParametricHarmonicFunction implements org.apache.commons.math.optimization.fitting.ParametricRealFunction {
        /**
         * {@inheritDoc}
         */         public double value(double x, double[] parameters) {             final double a = parameters[0];
            final double omega = parameters[1];
            final double phi = parameters[2];
            return a * (java.lang.Math.cos(((omega * x) + phi)));
        }

        /**
         * {@inheritDoc}
         */         public double[] gradient(double x, double[] parameters) {             final double a = parameters[0];
            final double omega = parameters[1];
            final double phi = parameters[2];
            final double alpha = (omega * x) + phi;
            final double cosAlpha = java.lang.Math.cos(alpha);
            final double sinAlpha = java.lang.Math.sin(alpha);
            return new double[]{ cosAlpha, ((-a) * x) * sinAlpha, (-a) * sinAlpha };
        }

    }

}