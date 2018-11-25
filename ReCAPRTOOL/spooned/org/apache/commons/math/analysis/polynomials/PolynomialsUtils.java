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
package org.apache.commons.math.analysis.polynomials;





/**
 * A collection of static methods that operate on or return polynomials.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public class PolynomialsUtils {

    /**
     * Coefficients for Chebyshev polynomials.
     */     private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> CHEBYSHEV_COEFFICIENTS;
    /**
     * Coefficients for Hermite polynomials.
     */     private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> HERMITE_COEFFICIENTS;
    /**
     * Coefficients for Laguerre polynomials.
     */     private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> LAGUERRE_COEFFICIENTS;
    /**
     * Coefficients for Legendre polynomials.
     */     private static final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> LEGENDRE_COEFFICIENTS;
    static {

        // initialize recurrence for Chebyshev polynomials
        // T0(X) = 1, T1(X) = 0 + 1 * X
        CHEBYSHEV_COEFFICIENTS = new java.util.ArrayList<org.apache.commons.math.fraction.BigFraction>();
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.CHEBYSHEV_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.CHEBYSHEV_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ZERO);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.CHEBYSHEV_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);

        // initialize recurrence for Hermite polynomials
        // H0(X) = 1, H1(X) = 0 + 2 * X
        HERMITE_COEFFICIENTS = new java.util.ArrayList<org.apache.commons.math.fraction.BigFraction>();
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.HERMITE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.HERMITE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ZERO);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.HERMITE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.TWO);

        // initialize recurrence for Laguerre polynomials
        // L0(X) = 1, L1(X) = 1 - 1 * X
        LAGUERRE_COEFFICIENTS = new java.util.ArrayList<org.apache.commons.math.fraction.BigFraction>();
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LAGUERRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LAGUERRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LAGUERRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.MINUS_ONE);

        // initialize recurrence for Legendre polynomials
        // P0(X) = 1, P1(X) = 0 + 1 * X
        LEGENDRE_COEFFICIENTS = new java.util.ArrayList<org.apache.commons.math.fraction.BigFraction>();
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LEGENDRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LEGENDRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ZERO);
        org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LEGENDRE_COEFFICIENTS.add(org.apache.commons.math.fraction.BigFraction.ONE);

    }

    /**
     * Private constructor, to prevent instantiation.
     */
    private PolynomialsUtils() {
    }

    /**
     * Create a Chebyshev polynomial of the first kind.
     * <p><a href="http://mathworld.wolfram.com/ChebyshevPolynomialoftheFirstKind.html">Chebyshev
     * polynomials of the first kind</a> are orthogonal polynomials.
     * They can be defined by the following recurrence relations:
     * <pre>
     *  T<sub>0</sub>(X)   = 1
     *  T<sub>1</sub>(X)   = X
     *  T<sub>k+1</sub>(X) = 2X T<sub>k</sub>(X) - T<sub>k-1</sub>(X)
     * </pre></p>
     *
     * @param degree
     * 		degree of the polynomial
     * @return Chebyshev polynomial of specified degree
     */     public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createChebyshevPolynomial(final int degree) {         return org.apache.commons.math.analysis.polynomials.PolynomialsUtils.buildPolynomial(degree, org.apache.commons.math.analysis.polynomials.PolynomialsUtils.CHEBYSHEV_COEFFICIENTS, 
        new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() {
            private final org.apache.commons.math.fraction.BigFraction[] coeffs = new org.apache.commons.math.fraction.BigFraction[]{ org.apache.commons.math.fraction.BigFraction.ZERO, org.apache.commons.math.fraction.BigFraction.TWO, org.apache.commons.math.fraction.BigFraction.ONE };
            /**
             * {@inheritDoc}
             */             public org.apache.commons.math.fraction.BigFraction[] generate(int k) {                 return coeffs;
            }
        });
    }

    /**
     * Create a Hermite polynomial.
     * <p><a href="http://mathworld.wolfram.com/HermitePolynomial.html">Hermite
     * polynomials</a> are orthogonal polynomials.
     * They can be defined by the following recurrence relations:
     * <pre>
     *  H<sub>0</sub>(X)   = 1
     *  H<sub>1</sub>(X)   = 2X
     *  H<sub>k+1</sub>(X) = 2X H<sub>k</sub>(X) - 2k H<sub>k-1</sub>(X)
     * </pre></p>
     *
     * @param degree
     * 		degree of the polynomial
     * @return Hermite polynomial of specified degree
     */     public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createHermitePolynomial(final int degree) {
        return org.apache.commons.math.analysis.polynomials.PolynomialsUtils.buildPolynomial(degree, org.apache.commons.math.analysis.polynomials.PolynomialsUtils.HERMITE_COEFFICIENTS, 
        new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() {
            /**
             * {@inheritDoc}
             */             public org.apache.commons.math.fraction.BigFraction[] generate(int k) {                 return new org.apache.commons.math.fraction.BigFraction[]{ 
                org.apache.commons.math.fraction.BigFraction.ZERO, 
                org.apache.commons.math.fraction.BigFraction.TWO, 
                new org.apache.commons.math.fraction.BigFraction((2 * k)) };
            }
        });
    }

    /**
     * Create a Laguerre polynomial.
     * <p><a href="http://mathworld.wolfram.com/LaguerrePolynomial.html">Laguerre
     * polynomials</a> are orthogonal polynomials.
     * They can be defined by the following recurrence relations:
     * <pre>
     *        L<sub>0</sub>(X)   = 1
     *        L<sub>1</sub>(X)   = 1 - X
     *  (k+1) L<sub>k+1</sub>(X) = (2k + 1 - X) L<sub>k</sub>(X) - k L<sub>k-1</sub>(X)
     * </pre></p>
     *
     * @param degree
     * 		degree of the polynomial
     * @return Laguerre polynomial of specified degree
     */     public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createLaguerrePolynomial(final int degree) {         return org.apache.commons.math.analysis.polynomials.PolynomialsUtils.buildPolynomial(degree, org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LAGUERRE_COEFFICIENTS, 
        new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() {
            /**
             * {@inheritDoc}
             */             public org.apache.commons.math.fraction.BigFraction[] generate(int k) {                 final int kP1 = k + 1;
                return new org.apache.commons.math.fraction.BigFraction[]{ 
                new org.apache.commons.math.fraction.BigFraction(((2 * k) + 1), kP1), 
                new org.apache.commons.math.fraction.BigFraction((-1), kP1), 
                new org.apache.commons.math.fraction.BigFraction(k, kP1) };
            }
        });
    }

    /**
     * Create a Legendre polynomial.
     * <p><a href="http://mathworld.wolfram.com/LegendrePolynomial.html">Legendre
     * polynomials</a> are orthogonal polynomials.
     * They can be defined by the following recurrence relations:
     * <pre>
     *        P<sub>0</sub>(X)   = 1
     *        P<sub>1</sub>(X)   = X
     *  (k+1) P<sub>k+1</sub>(X) = (2k+1) X P<sub>k</sub>(X) - k P<sub>k-1</sub>(X)
     * </pre></p>
     *
     * @param degree
     * 		degree of the polynomial
     * @return Legendre polynomial of specified degree
     */     public static org.apache.commons.math.analysis.polynomials.PolynomialFunction createLegendrePolynomial(final int degree) {         return org.apache.commons.math.analysis.polynomials.PolynomialsUtils.buildPolynomial(degree, org.apache.commons.math.analysis.polynomials.PolynomialsUtils.LEGENDRE_COEFFICIENTS, 
        new org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator() {
            /**
             * {@inheritDoc}
             */             public org.apache.commons.math.fraction.BigFraction[] generate(int k) {                 final int kP1 = k + 1;
                return new org.apache.commons.math.fraction.BigFraction[]{ 
                org.apache.commons.math.fraction.BigFraction.ZERO, 
                new org.apache.commons.math.fraction.BigFraction((k + kP1), kP1), 
                new org.apache.commons.math.fraction.BigFraction(k, kP1) };
            }
        });
    }

    /**
     * Get the coefficients array for a given degree.
     *
     * @param degree
     * 		degree of the polynomial
     * @param coefficients
     * 		list where the computed coefficients are stored
     * @param generator
     * 		recurrence coefficients generator
     * @return coefficients array
     */     private static org.apache.commons.math.analysis.polynomials.PolynomialFunction buildPolynomial(final int degree, final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> coefficients, final org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator generator) {         final int maxDegree = ((int) (java.lang.Math.floor(java.lang.Math.sqrt((2 * (coefficients.size())))))) - 1;
        synchronized(org.apache.commons.math.analysis.polynomials.PolynomialsUtils.class) {
            if (degree > maxDegree) {
                org.apache.commons.math.analysis.polynomials.PolynomialsUtils.computeUpToDegree(degree, maxDegree, generator, coefficients);
            }
        }

        // coefficient  for polynomial 0 is  l [0]
        // coefficients for polynomial 1 are l [1] ... l [2] (degrees 0 ... 1)
        // coefficients for polynomial 2 are l [3] ... l [5] (degrees 0 ... 2)
        // coefficients for polynomial 3 are l [6] ... l [9] (degrees 0 ... 3)
        // coefficients for polynomial 4 are l[10] ... l[14] (degrees 0 ... 4)
        // coefficients for polynomial 5 are l[15] ... l[20] (degrees 0 ... 5)
        // coefficients for polynomial 6 are l[21] ... l[27] (degrees 0 ... 6)
        // ...
        final int start = (degree * (degree + 1)) / 2;

        final double[] a = new double[degree + 1];
        for (int i = 0; i <= degree; ++i) {
            a[i] = coefficients.get((start + i)).doubleValue();
        }

        // build the polynomial
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(a);

    }

    /**
     * Compute polynomial coefficients up to a given degree.
     *
     * @param degree
     * 		maximal degree
     * @param maxDegree
     * 		current maximal degree
     * @param generator
     * 		recurrence coefficients generator
     * @param coefficients
     * 		list where the computed coefficients should be appended
     */     private static void computeUpToDegree(final int degree, final int maxDegree, final org.apache.commons.math.analysis.polynomials.PolynomialsUtils.RecurrenceCoefficientsGenerator generator, final java.util.ArrayList<org.apache.commons.math.fraction.BigFraction> coefficients) {         int startK = ((maxDegree - 1) * maxDegree) / 2;         for (int k = maxDegree; k < degree; ++k) {

            // start indices of two previous polynomials Pk(X) and Pk-1(X)
            int startKm1 = startK;
            startK += k;

            // Pk+1(X) = (a[0] + a[1] X) Pk(X) - a[2] Pk-1(X)
            org.apache.commons.math.fraction.BigFraction[] ai = generator.generate(k);

            org.apache.commons.math.fraction.BigFraction ck = coefficients.get(startK);
            org.apache.commons.math.fraction.BigFraction ckm1 = coefficients.get(startKm1);

            // degree 0 coefficient
            coefficients.add(ck.multiply(ai[0]).subtract(ckm1.multiply(ai[2])));

            // degree 1 to degree k-1 coefficients
            for (int i = 1; i < k; ++i) {
                final org.apache.commons.math.fraction.BigFraction ckPrev = ck;
                ck = coefficients.get((startK + i));
                ckm1 = coefficients.get((startKm1 + i));
                coefficients.add(ck.multiply(ai[0]).add(ckPrev.multiply(ai[1])).subtract(ckm1.multiply(ai[2])));
            }

            // degree k coefficient
            final org.apache.commons.math.fraction.BigFraction ckPrev = ck;
            ck = coefficients.get((startK + k));
            coefficients.add(ck.multiply(ai[0]).add(ckPrev.multiply(ai[1])));

            // degree k+1 coefficient
            coefficients.add(ck.multiply(ai[1]));

        }

    }

    /**
     * Interface for recurrence coefficients generation.
     */     private static interface RecurrenceCoefficientsGenerator {         /**
         * Generate recurrence coefficients.
         *
         * @param k
         * 		highest degree of the polynomials used in the recurrence
         * @return an array of three coefficients such that
         * P<sub>k+1</sub>(X) = (a[0] + a[1] X) P<sub>k</sub>(X) - a[2] P<sub>k-1</sub>(X)
         */         org.apache.commons.math.fraction.BigFraction[] generate(int k);}

}