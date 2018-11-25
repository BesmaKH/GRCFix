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
 * Immutable representation of a real polynomial function with real coefficients.
 * <p>
 * <a href="http://mathworld.wolfram.com/HornersMethod.html">Horner's Method</a>
 *  is used to evaluate the function.</p>
 *
 * @version $Revision$ $Date$
 */
public class PolynomialFunction implements java.io.Serializable , org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction {

    /**
     * Serialization identifier
     */
    private static final long serialVersionUID = -7726511984200295583L;

    /**
     * The coefficients of the polynomial, ordered by degree -- i.e.,
     * coefficients[0] is the constant term and coefficients[n] is the
     * coefficient of x^n where n is the degree of the polynomial.
     */
    private final double[] coefficients;

    /**
     * Construct a polynomial with the given coefficients.  The first element
     * of the coefficients array is the constant term.  Higher degree
     * coefficients follow in sequence.  The degree of the resulting polynomial
     * is the index of the last non-null element of the array, or 0 if all elements
     * are null.
     * <p>
     * The constructor makes a copy of the input array and assigns the copy to
     * the coefficients property.</p>
     *
     * @param c
     * 		polynomial coefficients
     * @throws NullPointerException
     * 		if c is null
     * @throws NoDataException
     * 		if c is empty
     */     public PolynomialFunction(double[] c) {         super();         int n = c.length;
        if (n == 0) {
            throw new org.apache.commons.math.exception.NoDataException(org.apache.commons.math.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        while ((n > 1) && ((c[(n - 1)]) == 0)) {
            --n;
        } 
        this.coefficients = new double[n];
        java.lang.System.arraycopy(c, 0, this.coefficients, 0, n);
    }

    /**
     * Compute the value of the function for the given argument.
     * <p>
     *  The value returned is <br>
     *   <code>coefficients[n] * x^n + ... + coefficients[1] * x  + coefficients[0]</code>
     * </p>
     *
     * @param x
     * 		the argument for which the function value should be computed
     * @return the value of the polynomial at the given point
     * @see UnivariateRealFunction#value(double)
     */     public double value(double x) {
        return org.apache.commons.math.analysis.polynomials.PolynomialFunction.evaluate(coefficients, x);
    }


    /**
     * Returns the degree of the polynomial
     *
     * @return the degree of the polynomial
     */
    public int degree() {
        return (coefficients.length) - 1;
    }

    /**
     * Returns a copy of the coefficients array.
     * <p>
     * Changes made to the returned copy will not affect the coefficients of
     * the polynomial.</p>
     *
     * @return a fresh copy of the coefficients array
     */
    public double[] getCoefficients() {
        return coefficients.clone();
    }

    /**
     * Uses Horner's Method to evaluate the polynomial with the given coefficients at
     * the argument.
     *
     * @param coefficients
     * 		the coefficients of the polynomial to evaluate
     * @param argument
     * 		the input value
     * @return the value of the polynomial
     * @throws NoDataException
     * 		if coefficients is empty
     * @throws NullPointerException
     * 		if coefficients is null
     */     protected static double evaluate(double[] coefficients, double argument) {         int n = coefficients.length;         if (n == 0) {             throw new org.apache.commons.math.exception.NoDataException(org.apache.commons.math.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        double result = coefficients[(n - 1)];
        for (int j = n - 2; j >= 0; j--) {
            result = (argument * result) + (coefficients[j]);
        }
        return result;
    }

    /**
     * Add a polynomial to the instance.
     *
     * @param p
     * 		polynomial to add
     * @return a new polynomial which is the sum of the instance and p
     */     public org.apache.commons.math.analysis.polynomials.PolynomialFunction add(final org.apache.commons.math.analysis.polynomials.PolynomialFunction p) {
        // identify the lowest degree polynomial
        final int lowLength = java.lang.Math.min(coefficients.length, p.coefficients.length);
        final int highLength = java.lang.Math.max(coefficients.length, p.coefficients.length);

        // build the coefficients array
        double[] newCoefficients = new double[highLength];
        for (int i = 0; i < lowLength; ++i) {
            newCoefficients[i] = (coefficients[i]) + (p.coefficients[i]);
        }
        java.lang.System.arraycopy(((coefficients.length) < (p.coefficients.length) ? 
        p.coefficients : coefficients), 
        lowLength, 
        newCoefficients, lowLength, 
        (highLength - lowLength));

        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(newCoefficients);

    }

    /**
     * Subtract a polynomial from the instance.
     *
     * @param p
     * 		polynomial to subtract
     * @return a new polynomial which is the difference the instance minus p
     */     public org.apache.commons.math.analysis.polynomials.PolynomialFunction subtract(final org.apache.commons.math.analysis.polynomials.PolynomialFunction p) {
        // identify the lowest degree polynomial
        int lowLength = java.lang.Math.min(coefficients.length, p.coefficients.length);
        int highLength = java.lang.Math.max(coefficients.length, p.coefficients.length);

        // build the coefficients array
        double[] newCoefficients = new double[highLength];
        for (int i = 0; i < lowLength; ++i) {
            newCoefficients[i] = (coefficients[i]) - (p.coefficients[i]);
        }
        if ((coefficients.length) < (p.coefficients.length)) {
            for (int i = lowLength; i < highLength; ++i) {
                newCoefficients[i] = -(p.coefficients[i]);
            }
        }else {
            java.lang.System.arraycopy(coefficients, lowLength, newCoefficients, lowLength, 
            (highLength - lowLength));
        }

        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(newCoefficients);

    }

    /**
     * Negate the instance.
     *
     * @return a new polynomial
     */     public org.apache.commons.math.analysis.polynomials.PolynomialFunction negate() {
        double[] newCoefficients = new double[coefficients.length];
        for (int i = 0; i < (coefficients.length); ++i) {
            newCoefficients[i] = -(coefficients[i]);
        }
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(newCoefficients);
    }

    /**
     * Multiply the instance by a polynomial.
     *
     * @param p
     * 		polynomial to multiply by
     * @return a new polynomial
     */     public org.apache.commons.math.analysis.polynomials.PolynomialFunction multiply(final org.apache.commons.math.analysis.polynomials.PolynomialFunction p) {
        double[] newCoefficients = new double[((coefficients.length) + (p.coefficients.length)) - 1];

        for (int i = 0; i < (newCoefficients.length); ++i) {
            newCoefficients[i] = 0.0;
            for (int j = java.lang.Math.max(0, ((i + 1) - (p.coefficients.length))); 
            j < (java.lang.Math.min(coefficients.length, (i + 1))); 
            ++j) {
                newCoefficients[i] += (coefficients[j]) * (p.coefficients[(i - j)]);
            }
        }

        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(newCoefficients);

    }

    /**
     * Returns the coefficients of the derivative of the polynomial with the given coefficients.
     *
     * @param coefficients
     * 		the coefficients of the polynomial to differentiate
     * @return the coefficients of the derivative or null if coefficients has length 1.
     * @throws NoDataException
     * 		if coefficients is empty
     * @throws NullPointerException
     * 		if coefficients is null
     */     protected static double[] differentiate(double[] coefficients) {         int n = coefficients.length;         if (n == 0) {
            throw new org.apache.commons.math.exception.NoDataException(org.apache.commons.math.util.LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
        }
        if (n == 1) {
            return new double[]{ 0 };
        }
        double[] result = new double[n - 1];
        for (int i = n - 1; i > 0; i--) {
            result[(i - 1)] = i * (coefficients[i]);
        }
        return result;
    }

    /**
     * Returns the derivative as a PolynomialRealFunction
     *
     * @return the derivative polynomial
     */
    public org.apache.commons.math.analysis.polynomials.PolynomialFunction polynomialDerivative() {
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(org.apache.commons.math.analysis.polynomials.PolynomialFunction.differentiate(coefficients));
    }

    /**
     * Returns the derivative as a UnivariateRealFunction
     *
     * @return the derivative function
     */
    public org.apache.commons.math.analysis.UnivariateRealFunction derivative() {
        return polynomialDerivative();
    }

    /**
     * Returns a string representation of the polynomial.
     *
     * <p>The representation is user oriented. Terms are displayed lowest
     * degrees first. The multiplications signs, coefficients equals to
     * one and null terms are not displayed (except if the polynomial is 0,
     * in which case the 0 constant term is displayed). Addition of terms
     * with negative coefficients are replaced by subtraction of terms
     * with positive coefficients except for the first displayed term
     * (i.e. we display <code>-3</code> for a constant negative polynomial,
     * but <code>1 - 3 x + x^2</code> if the negative coefficient is not
     * the first one displayed).</p>
     *
     * @return a string representation of the polynomial
     */
    @java.lang.Override
    public java.lang.String toString() {

        java.lang.StringBuffer s = new java.lang.StringBuffer();
        if ((coefficients[0]) == 0.0) {
            if ((coefficients.length) == 1) {
                return "0";
            }
        }else {
            s.append(java.lang.Double.toString(coefficients[0]));
        }

        for (int i = 1; i < (coefficients.length); ++i) {

            if ((coefficients[i]) != 0) {

                if ((s.length()) > 0) {
                    if ((coefficients[i]) < 0) {
                        s.append(" - ");
                    }else {
                        s.append(" + ");
                    }
                }else {
                    if ((coefficients[i]) < 0) {
                        s.append("-");
                    }
                }

                double absAi = java.lang.Math.abs(coefficients[i]);
                if ((absAi - 1) != 0) {
                    s.append(java.lang.Double.toString(absAi));
                    s.append(' ');
                }

                s.append("x");
                if (i > 1) {
                    s.append('^');
                    s.append(java.lang.Integer.toString(i));
                }
            }

        }

        return s.toString();

    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (java.util.Arrays.hashCode(coefficients));
        return result;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(java.lang.Object obj) {
        if ((this) == obj)
            return true;
        if (!(obj instanceof org.apache.commons.math.analysis.polynomials.PolynomialFunction))
            return false;
        org.apache.commons.math.analysis.polynomials.PolynomialFunction other = ((org.apache.commons.math.analysis.polynomials.PolynomialFunction) (obj));
        if (!(java.util.Arrays.equals(coefficients, other.coefficients)))
            return false;
        return true;
    }

}