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
package org.apache.commons.math.transform;










/**
 * Implements the <a href="http://mathworld.wolfram.com/FastFourierTransform.html">
 * Fast Fourier Transform</a> for transformation of one-dimensional data sets.
 * For reference, see <b>Applied Numerical Linear Algebra</b>, ISBN 0898713897,
 * chapter 6.
 * <p>
 * There are several conventions for the definition of FFT and inverse FFT,
 * mainly on different coefficient and exponent. Here the equations are listed
 * in the comments of the corresponding methods.</p>
 * <p>
 * We require the length of data set to be power of 2, this greatly simplifies
 * and speeds up the code. Users can pad the data with zeros to meet this
 * requirement. There are other flavors of FFT, for reference, see S. Winograd,
 * <i>On computing the discrete Fourier transform</i>, Mathematics of Computation,
 * 32 (1978), 175 - 199.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public class FastFourierTransformer implements java.io.Serializable {

    /**
     * Serializable version identifier.
     */     static final long serialVersionUID = 5138259215438106000L;
    /**
     * roots of unity
     */     private org.apache.commons.math.transform.FastFourierTransformer.RootsOfUnity roots = new org.apache.commons.math.transform.FastFourierTransformer.RootsOfUnity();
    /**
     * Construct a default transformer.
     */
    public FastFourierTransformer() {
        super();
    }

    /**
     * Transform the given real data set.
     * <p>
     * The formula is $ y_n = \Sigma_{k=0}^{N-1} e^{-2 \pi i nk/N} x_k $
     * </p>
     *
     * @param f
     * 		the real data array to be transformed
     * @return the complex transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] transform(double[] f) throws java.lang.IllegalArgumentException {
        return fft(f, false);
    }

    /**
     * Transform the given real function, sampled on the given interval.
     * <p>
     * The formula is $ y_n = \Sigma_{k=0}^{N-1} e^{-2 \pi i nk/N} x_k $
     * </p>
     *
     * @param f
     * 		the function to be sampled and transformed
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param n
     * 		the number of sample points
     * @return the complex transformed array
     * @throws FunctionEvaluationException
     * 		if function cannot be evaluated
     * 		at some point
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] transform(org.apache.commons.math.analysis.UnivariateRealFunction f, double min, double max, int n) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {         double[] data = org.apache.commons.math.transform.FastFourierTransformer.sample(f, min, max, n);         return fft(data, false);}

    /**
     * Transform the given complex data set.
     * <p>
     * The formula is $ y_n = \Sigma_{k=0}^{N-1} e^{-2 \pi i nk/N} x_k $
     * </p>
     *
     * @param f
     * 		the complex data array to be transformed
     * @return the complex transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] transform(org.apache.commons.math.complex.Complex[] f) throws java.lang.IllegalArgumentException {
        roots.computeOmega(f.length);
        return fft(f);
    }

    /**
     * Transform the given real data set.
     * <p>
     * The formula is $y_n = (1/\sqrt{N}) \Sigma_{k=0}^{N-1} e^{-2 \pi i nk/N} x_k$
     * </p>
     *
     * @param f
     * 		the real data array to be transformed
     * @return the complex transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] transform2(double[] f) throws java.lang.IllegalArgumentException {

        double scaling_coefficient = 1.0 / (java.lang.Math.sqrt(f.length));
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(f, false), scaling_coefficient);
    }

    /**
     * Transform the given real function, sampled on the given interval.
     * <p>
     * The formula is $y_n = (1/\sqrt{N}) \Sigma_{k=0}^{N-1} e^{-2 \pi i nk/N} x_k$
     * </p>
     *
     * @param f
     * 		the function to be sampled and transformed
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param n
     * 		the number of sample points
     * @return the complex transformed array
     * @throws FunctionEvaluationException
     * 		if function cannot be evaluated
     * 		at some point
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] transform2(org.apache.commons.math.analysis.UnivariateRealFunction f, double min, double max, int n) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {         double[] data = org.apache.commons.math.transform.FastFourierTransformer.sample(f, min, max, n);         double scaling_coefficient = 1.0 / (java.lang.Math.sqrt(n));
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(data, false), scaling_coefficient);
    }

    /**
     * Transform the given complex data set.
     * <p>
     * The formula is $y_n = (1/\sqrt{N}) \Sigma_{k=0}^{N-1} e^{-2 \pi i nk/N} x_k$
     * </p>
     *
     * @param f
     * 		the complex data array to be transformed
     * @return the complex transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] transform2(org.apache.commons.math.complex.Complex[] f) throws java.lang.IllegalArgumentException {

        roots.computeOmega(f.length);
        double scaling_coefficient = 1.0 / (java.lang.Math.sqrt(f.length));
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(f), scaling_coefficient);
    }

    /**
     * Inversely transform the given real data set.
     * <p>
     * The formula is $ x_k = (1/N) \Sigma_{n=0}^{N-1} e^{2 \pi i nk/N} y_n $
     * </p>
     *
     * @param f
     * 		the real data array to be inversely transformed
     * @return the complex inversely transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] inversetransform(double[] f) throws java.lang.IllegalArgumentException {

        double scaling_coefficient = 1.0 / (f.length);
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(f, true), scaling_coefficient);
    }

    /**
     * Inversely transform the given real function, sampled on the given interval.
     * <p>
     * The formula is $ x_k = (1/N) \Sigma_{n=0}^{N-1} e^{2 \pi i nk/N} y_n $
     * </p>
     *
     * @param f
     * 		the function to be sampled and inversely transformed
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param n
     * 		the number of sample points
     * @return the complex inversely transformed array
     * @throws FunctionEvaluationException
     * 		if function cannot be evaluated
     * 		at some point
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] inversetransform(org.apache.commons.math.analysis.UnivariateRealFunction f, double min, double max, int n) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {         double[] data = org.apache.commons.math.transform.FastFourierTransformer.sample(f, min, max, n);         double scaling_coefficient = 1.0 / n;
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(data, true), scaling_coefficient);
    }

    /**
     * Inversely transform the given complex data set.
     * <p>
     * The formula is $ x_k = (1/N) \Sigma_{n=0}^{N-1} e^{2 \pi i nk/N} y_n $
     * </p>
     *
     * @param f
     * 		the complex data array to be inversely transformed
     * @return the complex inversely transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] inversetransform(org.apache.commons.math.complex.Complex[] f) throws java.lang.IllegalArgumentException {

        roots.computeOmega((-(f.length)));// pass negative argument
        double scaling_coefficient = 1.0 / (f.length);
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(f), scaling_coefficient);
    }

    /**
     * Inversely transform the given real data set.
     * <p>
     * The formula is $x_k = (1/\sqrt{N}) \Sigma_{n=0}^{N-1} e^{2 \pi i nk/N} y_n$
     * </p>
     *
     * @param f
     * 		the real data array to be inversely transformed
     * @return the complex inversely transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] inversetransform2(double[] f) throws java.lang.IllegalArgumentException {

        double scaling_coefficient = 1.0 / (java.lang.Math.sqrt(f.length));
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(f, true), scaling_coefficient);
    }

    /**
     * Inversely transform the given real function, sampled on the given interval.
     * <p>
     * The formula is $x_k = (1/\sqrt{N}) \Sigma_{n=0}^{N-1} e^{2 \pi i nk/N} y_n$
     * </p>
     *
     * @param f
     * 		the function to be sampled and inversely transformed
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param n
     * 		the number of sample points
     * @return the complex inversely transformed array
     * @throws FunctionEvaluationException
     * 		if function cannot be evaluated
     * 		at some point
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] inversetransform2(org.apache.commons.math.analysis.UnivariateRealFunction f, double min, double max, int n) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {         double[] data = org.apache.commons.math.transform.FastFourierTransformer.sample(f, min, max, n);         double scaling_coefficient = 1.0 / (java.lang.Math.sqrt(n));
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(data, true), scaling_coefficient);
    }

    /**
     * Inversely transform the given complex data set.
     * <p>
     * The formula is $x_k = (1/\sqrt{N}) \Sigma_{n=0}^{N-1} e^{2 \pi i nk/N} y_n$
     * </p>
     *
     * @param f
     * 		the complex data array to be inversely transformed
     * @return the complex inversely transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public org.apache.commons.math.complex.Complex[] inversetransform2(org.apache.commons.math.complex.Complex[] f) throws java.lang.IllegalArgumentException {

        roots.computeOmega((-(f.length)));// pass negative argument
        double scaling_coefficient = 1.0 / (java.lang.Math.sqrt(f.length));
        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(fft(f), scaling_coefficient);
    }

    /**
     * Perform the base-4 Cooley-Tukey FFT algorithm (including inverse).
     *
     * @param f
     * 		the real data array to be transformed
     * @param isInverse
     * 		the indicator of forward or inverse transform
     * @return the complex transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     protected org.apache.commons.math.complex.Complex[] fft(double[] f, boolean isInverse) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.transform.FastFourierTransformer.verifyDataSet(f);
        org.apache.commons.math.complex.Complex[] F = new org.apache.commons.math.complex.Complex[f.length];
        if ((f.length) == 1) {
            F[0] = new org.apache.commons.math.complex.Complex(f[0], 0.0);
            return F;
        }

        // Rather than the naive real to complex conversion, pack 2N
        // real numbers into N complex numbers for better performance.
        int N = (f.length) >> 1;
        org.apache.commons.math.complex.Complex[] c = new org.apache.commons.math.complex.Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = new org.apache.commons.math.complex.Complex(f[(2 * i)], f[((2 * i) + 1)]);
        }
        roots.computeOmega((isInverse ? -N : N));
        org.apache.commons.math.complex.Complex[] z = fft(c);

        // reconstruct the FFT result for the original array
        roots.computeOmega((isInverse ? (-2) * N : 2 * N));
        F[0] = new org.apache.commons.math.complex.Complex((2 * ((z[0].getReal()) + (z[0].getImaginary()))), 0.0);
        F[N] = new org.apache.commons.math.complex.Complex((2 * ((z[0].getReal()) - (z[0].getImaginary()))), 0.0);
        for (int i = 1; i < N; i++) {
            org.apache.commons.math.complex.Complex A = z[(N - i)].conjugate();
            org.apache.commons.math.complex.Complex B = z[i].add(A);
            org.apache.commons.math.complex.Complex C = z[i].subtract(A);
            // Complex D = roots.getOmega(i).multiply(Complex.I);
            org.apache.commons.math.complex.Complex D = new org.apache.commons.math.complex.Complex((-(roots.getOmegaImaginary(i))), 
            roots.getOmegaReal(i));
            F[i] = B.subtract(C.multiply(D));
            F[((2 * N) - i)] = F[i].conjugate();
        }

        return org.apache.commons.math.transform.FastFourierTransformer.scaleArray(F, 0.5);
    }

    /**
     * Perform the base-4 Cooley-Tukey FFT algorithm (including inverse).
     *
     * @param data
     * 		the complex data array to be transformed
     * @return the complex transformed array
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     protected org.apache.commons.math.complex.Complex[] fft(org.apache.commons.math.complex.Complex[] data) throws java.lang.IllegalArgumentException {

        final int n = data.length;
        final org.apache.commons.math.complex.Complex[] f = new org.apache.commons.math.complex.Complex[n];

        // initial simple cases
        org.apache.commons.math.transform.FastFourierTransformer.verifyDataSet(data);
        if (n == 1) {
            f[0] = data[0];
            return f;
        }
        if (n == 2) {
            f[0] = data[0].add(data[1]);
            f[1] = data[0].subtract(data[1]);
            return f;
        }

        // permute original data array in bit-reversal order
        int ii = 0;
        for (int i = 0; i < n; i++) {
            f[i] = data[ii];
            int k = n >> 1;
            while ((ii >= k) && (k > 0)) {
                ii -= k;                 k >>= 1;
            } 
            ii += k;
        }

        // the bottom base-4 round
        for (int i = 0; i < n; i += 4) {
            final org.apache.commons.math.complex.Complex a = f[i].add(f[(i + 1)]);
            final org.apache.commons.math.complex.Complex b = f[(i + 2)].add(f[(i + 3)]);
            final org.apache.commons.math.complex.Complex c = f[i].subtract(f[(i + 1)]);
            final org.apache.commons.math.complex.Complex d = f[(i + 2)].subtract(f[(i + 3)]);
            final org.apache.commons.math.complex.Complex e1 = c.add(d.multiply(org.apache.commons.math.complex.Complex.I));
            final org.apache.commons.math.complex.Complex e2 = c.subtract(d.multiply(org.apache.commons.math.complex.Complex.I));
            f[i] = a.add(b);
            f[(i + 2)] = a.subtract(b);
            // omegaCount indicates forward or inverse transform
            f[(i + 1)] = (roots.isForward()) ? e2 : e1;
            f[(i + 3)] = (roots.isForward()) ? e1 : e2;
        }

        // iterations from bottom to top take O(N*logN) time
        for (int i = 4; i < n; i <<= 1) {
            final int m = n / (i << 1);
            for (int j = 0; j < n; j += i << 1) {
                for (int k = 0; k < i; k++) {
                    // z = f[i+j+k].multiply(roots.getOmega(k*m));
                    final int k_times_m = k * m;
                    final double omega_k_times_m_real = roots.getOmegaReal(k_times_m);
                    final double omega_k_times_m_imaginary = roots.getOmegaImaginary(k_times_m);
                    // z = f[i+j+k].multiply(omega[k*m]);
                    final org.apache.commons.math.complex.Complex z = new org.apache.commons.math.complex.Complex(
                    (((f[((i + j) + k)].getReal()) * omega_k_times_m_real) - 
                    ((f[((i + j) + k)].getImaginary()) * omega_k_times_m_imaginary)), 
                    (((f[((i + j) + k)].getReal()) * omega_k_times_m_imaginary) + 
                    ((f[((i + j) + k)].getImaginary()) * omega_k_times_m_real)));

                    f[((i + j) + k)] = f[(j + k)].subtract(z);
                    f[(j + k)] = f[(j + k)].add(z);
                }
            }
        }
        return f;
    }

    /**
     * Sample the given univariate real function on the given interval.
     * <p>
     * The interval is divided equally into N sections and sample points
     * are taken from min to max-(max-min)/N. Usually f(x) is periodic
     * such that f(min) = f(max) (note max is not sampled), but we don't
     * require that.</p>
     *
     * @param f
     * 		the function to be sampled
     * @param min
     * 		the lower bound for the interval
     * @param max
     * 		the upper bound for the interval
     * @param n
     * 		the number of sample points
     * @return the samples array
     * @throws FunctionEvaluationException
     * 		if function cannot be evaluated
     * 		at some point
     * @throws IllegalArgumentException
     * 		if any parameters are invalid
     */     public static double[] sample(org.apache.commons.math.analysis.UnivariateRealFunction f, double min, double max, int n) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {         if (n <= 0) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, 
            n);
        }
        org.apache.commons.math.transform.FastFourierTransformer.verifyInterval(min, max);

        double[] s = new double[n];
        double h = (max - min) / n;
        for (int i = 0; i < n; i++) {
            s[i] = f.value((min + (i * h)));
        }
        return s;
    }

    /**
     * Multiply every component in the given real array by the
     * given real number. The change is made in place.
     *
     * @param f
     * 		the real array to be scaled
     * @param d
     * 		the real scaling coefficient
     * @return a reference to the scaled array
     */     public static double[] scaleArray(double[] f, double d) {         for (int i = 0; i < (f.length); i++) {
            f[i] *= d;
        }
        return f;
    }

    /**
     * Multiply every component in the given complex array by the
     * given real number. The change is made in place.
     *
     * @param f
     * 		the complex array to be scaled
     * @param d
     * 		the real scaling coefficient
     * @return a reference to the scaled array
     */     public static org.apache.commons.math.complex.Complex[] scaleArray(org.apache.commons.math.complex.Complex[] f, double d) {         for (int i = 0; i < (f.length); i++) {
            f[i] = new org.apache.commons.math.complex.Complex((d * (f[i].getReal())), (d * (f[i].getImaginary())));
        }
        return f;
    }

    /**
     * Returns true if the argument is power of 2.
     *
     * @param n
     * 		the number to test
     * @return true if the argument is power of 2
     */     public static boolean isPowerOf2(long n) {
        return (n > 0) && ((n & (n - 1)) == 0);
    }

    /**
     * Verifies that the data set has length of power of 2.
     *
     * @param d
     * 		the data array
     * @throws IllegalArgumentException
     * 		if array length is not power of 2
     */     public static void verifyDataSet(double[] d) throws java.lang.IllegalArgumentException {         if (!(org.apache.commons.math.transform.FastFourierTransformer.isPowerOf2(d.length))) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, d.length);
        }
    }

    /**
     * Verifies that the data set has length of power of 2.
     *
     * @param o
     * 		the data array
     * @throws IllegalArgumentException
     * 		if array length is not power of 2
     */     public static void verifyDataSet(java.lang.Object[] o) throws java.lang.IllegalArgumentException {         if (!(org.apache.commons.math.transform.FastFourierTransformer.isPowerOf2(o.length))) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, o.length);
        }
    }

    /**
     * Verifies that the endpoints specify an interval.
     *
     * @param lower
     * 		lower endpoint
     * @param upper
     * 		upper endpoint
     * @throws IllegalArgumentException
     * 		if not interval
     */     public static void verifyInterval(double lower, double upper) throws java.lang.IllegalArgumentException {
        if (lower >= upper) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, 
            lower, upper);
        }
    }

    /**
     * Performs a multi-dimensional Fourier transform on a given array.
     * Use {@link #inversetransform2(Complex[])} and
     * {@link #transform2(Complex[])} in a row-column implementation
     * in any number of dimensions with O(N&times;log(N)) complexity with
     * N=n<sub>1</sub>&times;n<sub>2</sub>&times;n<sub>3</sub>&times;...&times;n<sub>d</sub>,
     * n<sub>x</sub>=number of elements in dimension x,
     * and d=total number of dimensions.
     *
     * @param mdca
     * 		Multi-Dimensional Complex Array id est Complex[][][][]
     * @param forward
     * 		inverseTransform2 is preformed if this is false
     * @return transform of mdca as a Multi-Dimensional Complex Array id est Complex[][][][]
     * @throws IllegalArgumentException
     * 		if any dimension is not a power of two
     */     public java.lang.Object mdfft(java.lang.Object mdca, boolean forward) throws java.lang.IllegalArgumentException {         org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix mdcm = 
        ((org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix) (new org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix(mdca).clone()));
        int[] dimensionSize = mdcm.getDimensionSizes();
        // cycle through each dimension
        for (int i = 0; i < (dimensionSize.length); i++) {
            mdfft(mdcm, forward, i, new int[0]);
        }
        return mdcm.getArray();
    }

    /**
     * Performs one dimension of a multi-dimensional Fourier transform.
     *
     * @param mdcm
     * 		input matrix
     * @param forward
     * 		inverseTransform2 is preformed if this is false
     * @param d
     * 		index of the dimension to process
     * @param subVector
     * 		recursion subvector
     * @throws IllegalArgumentException
     * 		if any dimension is not a power of two
     */     private void mdfft(org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix mdcm, boolean forward, int d, int[] subVector) throws java.lang.IllegalArgumentException {         int[] dimensionSize = mdcm.getDimensionSizes();         // if done
        if ((subVector.length) == (dimensionSize.length)) {
            org.apache.commons.math.complex.Complex[] temp = new org.apache.commons.math.complex.Complex[dimensionSize[d]];
            for (int i = 0; i < (dimensionSize[d]); i++) {
                // fft along dimension d
                subVector[d] = i;
                temp[i] = mdcm.get(subVector);
            }

            if (forward)
                temp = transform2(temp);else

                temp = inversetransform2(temp);

            for (int i = 0; i < (dimensionSize[d]); i++) {
                subVector[d] = i;
                mdcm.set(temp[i], subVector);
            }
        }else {
            int[] vector = new int[(subVector.length) + 1];
            java.lang.System.arraycopy(subVector, 0, vector, 0, subVector.length);
            if ((subVector.length) == d) {
                // value is not important once the recursion is done.
                // then an fft will be applied along the dimension d.
                vector[d] = 0;
                mdfft(mdcm, forward, d, vector);
            }else {
                for (int i = 0; i < (dimensionSize[subVector.length]); i++) {
                    vector[subVector.length] = i;
                    // further split along the next dimension
                    mdfft(mdcm, forward, d, vector);
                }
            }
        }
        return;
    }

    /**
     * Complex matrix implementation.
     * Not designed for synchronized access
     * may eventually be replaced by jsr-83 of the java community process
     * http://jcp.org/en/jsr/detail?id=83
     * may require additional exception throws for other basic requirements.
     */
    private static class MultiDimensionalComplexMatrix implements 
    java.lang.Cloneable {

        /**
         * Size in all dimensions.
         */         protected int[] dimensionSize;
        /**
         * Storage array.
         */         protected java.lang.Object multiDimensionalComplexArray;
        /**
         * Simple constructor.
         *
         * @param multiDimensionalComplexArray
         * 		array containing the matrix elements
         */         public MultiDimensionalComplexMatrix(java.lang.Object multiDimensionalComplexArray) {             this.multiDimensionalComplexArray = multiDimensionalComplexArray;

            // count dimensions
            int numOfDimensions = 0;
            for (java.lang.Object lastDimension = multiDimensionalComplexArray; 
            lastDimension instanceof java.lang.Object[];) {
                final java.lang.Object[] array = ((java.lang.Object[]) (lastDimension));
                numOfDimensions++;
                lastDimension = array[0];
            }

            // allocate array with exact count
            dimensionSize = new int[numOfDimensions];

            // fill array
            numOfDimensions = 0;
            for (java.lang.Object lastDimension = multiDimensionalComplexArray; 
            lastDimension instanceof java.lang.Object[];) {
                final java.lang.Object[] array = ((java.lang.Object[]) (lastDimension));
                dimensionSize[(numOfDimensions++)] = array.length;
                lastDimension = array[0];
            }

        }

        /**
         * Get a matrix element.
         *
         * @param vector
         * 		indices of the element
         * @return matrix element
         * @exception IllegalArgumentException if dimensions do not match
         */         public org.apache.commons.math.complex.Complex get(int... vector) throws java.lang.IllegalArgumentException {
            if (vector == null) {
                if ((dimensionSize.length) > 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, 0, dimensionSize.length);
                }
                return null;
            }
            if ((vector.length) != (dimensionSize.length)) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, vector.length, dimensionSize.length);
            }

            java.lang.Object lastDimension = multiDimensionalComplexArray;

            for (int i = 0; i < (dimensionSize.length); i++) {
                lastDimension = ((java.lang.Object[]) (lastDimension))[vector[i]];
            }
            return ((org.apache.commons.math.complex.Complex) (lastDimension));
        }

        /**
         * Set a matrix element.
         *
         * @param magnitude
         * 		magnitude of the element
         * @param vector
         * 		indices of the element
         * @return the previous value
         * @exception IllegalArgumentException if dimensions do not match
         */         public org.apache.commons.math.complex.Complex set(org.apache.commons.math.complex.Complex magnitude, int... vector) throws java.lang.IllegalArgumentException {             if (vector == null) {
                if ((dimensionSize.length) > 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, 0, dimensionSize.length);
                }
                return null;
            }
            if ((vector.length) != (dimensionSize.length)) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, vector.length, dimensionSize.length);
            }

            java.lang.Object[] lastDimension = ((java.lang.Object[]) (multiDimensionalComplexArray));
            for (int i = 0; i < ((dimensionSize.length) - 1); i++) {
                lastDimension = ((java.lang.Object[]) (lastDimension[vector[i]]));
            }

            org.apache.commons.math.complex.Complex lastValue = ((org.apache.commons.math.complex.Complex) (lastDimension[vector[((dimensionSize.length) - 1)]]));
            lastDimension[vector[((dimensionSize.length) - 1)]] = magnitude;

            return lastValue;
        }

        /**
         * Get the size in all dimensions.
         *
         * @return size in all dimensions
         */         public int[] getDimensionSizes() {
            return dimensionSize.clone();
        }

        /**
         * Get the underlying storage array
         *
         * @return underlying storage array
         */         public java.lang.Object getArray() {
            return multiDimensionalComplexArray;
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public java.lang.Object clone() {
            org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix mdcm = 
            new org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix(java.lang.reflect.Array.newInstance(
            org.apache.commons.math.complex.Complex.class, dimensionSize));
            clone(mdcm);
            return mdcm;
        }

        /**
         * Copy contents of current array into mdcm.
         *
         * @param mdcm
         * 		array where to copy data
         */         private void clone(org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix mdcm) {             int[] vector = new int[dimensionSize.length];
            int size = 1;
            for (int i = 0; i < (dimensionSize.length); i++) {
                size *= dimensionSize[i];
            }
            int[][] vectorList = new int[size][dimensionSize.length];
            for (int[] nextVector : vectorList) {
                java.lang.System.arraycopy(vector, 0, nextVector, 0, 
                dimensionSize.length);
                for (int i = 0; i < (dimensionSize.length); i++) {
                    (vector[i])++;
                    if ((vector[i]) < (dimensionSize[i])) {
                        break;
                    }else {
                        vector[i] = 0;
                    }
                }
            }

            for (int[] nextVector : vectorList) {
                mdcm.set(get(nextVector), nextVector);
            }
        }
    }


    /**
     * Computes the n<sup>th</sup> roots of unity.
     * A cache of already computed values is maintained.
     */     private static class RootsOfUnity implements java.io.Serializable {

        /**
         * Serializable version id.
         */         private static final long serialVersionUID = 6404784357747329667L;
        /**
         * Number of roots of unity.
         */         private int omegaCount;
        /**
         * Real part of the roots.
         */         private double[] omegaReal;
        /**
         * Imaginary part of the roots for forward transform.
         */         private double[] omegaImaginaryForward;
        /**
         * Imaginary part of the roots for reverse transform.
         */         private double[] omegaImaginaryInverse;
        /**
         * Forward/reverse indicator.
         */         private boolean isForward;
        /**
         * Build an engine for computing then <sup>th</sup> roots of unity
         */
        public RootsOfUnity() {

            omegaCount = 0;
            omegaReal = null;
            omegaImaginaryForward = null;
            omegaImaginaryInverse = null;
            isForward = true;

        }

        /**
         * Check if computation has been done for forward or reverse transform.
         *
         * @return true if computation has been done for forward transform
         * @throws IllegalStateException
         * 		if no roots of unity have been computed yet
         */         public synchronized boolean isForward() throws java.lang.IllegalStateException {
            if ((omegaCount) == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.util.LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET);
            }
            return isForward;

        }

        /**
         * Computes the n<sup>th</sup> roots of unity.
         * <p>The computed omega[] = { 1, w, w<sup>2</sup>, ... w<sup>(n-1)</sup> } where
         * w = exp(-2 &pi; i / n), i = &sqrt;(-1).</p>
         * <p>Note that n is positive for
         * forward transform and negative for inverse transform.</p>
         *
         * @param n
         * 		number of roots of unity to compute,
         * 		positive for forward transform, negative for inverse transform
         * @throws IllegalArgumentException
         * 		if n = 0
         */         public synchronized void computeOmega(int n) throws java.lang.IllegalArgumentException {             if (n == 0) {                 throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.CANNOT_COMPUTE_0TH_ROOT_OF_UNITY);
            }

            isForward = n > 0;

            // avoid repetitive calculations
            final int absN = java.lang.Math.abs(n);

            if (absN == (omegaCount)) {
                return;
            }

            // calculate everything from scratch, for both forward and inverse versions
            final double t = (2.0 * (java.lang.Math.PI)) / absN;
            final double cosT = java.lang.Math.cos(t);
            final double sinT = java.lang.Math.sin(t);
            omegaReal = new double[absN];
            omegaImaginaryForward = new double[absN];
            omegaImaginaryInverse = new double[absN];
            omegaReal[0] = 1.0;
            omegaImaginaryForward[0] = 0.0;
            omegaImaginaryInverse[0] = 0.0;
            for (int i = 1; i < absN; i++) {
                omegaReal[i] = 
                ((omegaReal[(i - 1)]) * cosT) + ((omegaImaginaryForward[(i - 1)]) * sinT);
                omegaImaginaryForward[i] = 
                ((omegaImaginaryForward[(i - 1)]) * cosT) - ((omegaReal[(i - 1)]) * sinT);
                omegaImaginaryInverse[i] = -(omegaImaginaryForward[i]);
            }
            omegaCount = absN;

        }

        /**
         * Get the real part of the k<sup>th</sup> n<sup>th</sup> root of unity
         *
         * @param k
         * 		index of the n<sup>th</sup> root of unity
         * @return real part of the k<sup>th</sup> n<sup>th</sup> root of unity
         * @throws IllegalStateException
         * 		if no roots of unity have been computed yet
         * @throws IllegalArgumentException
         * 		if k is out of range
         */         public synchronized double getOmegaReal(int k) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {             if ((omegaCount) == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.util.LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET);
            }
            if ((k < 0) || (k >= (omegaCount))) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, k, 0, ((omegaCount) - 1));
            }

            return omegaReal[k];

        }

        /**
         * Get the imaginary part of the k<sup>th</sup> n<sup>th</sup> root of unity
         *
         * @param k
         * 		index of the n<sup>th</sup> root of unity
         * @return imaginary part of the k<sup>th</sup> n<sup>th</sup> root of unity
         * @throws IllegalStateException
         * 		if no roots of unity have been computed yet
         * @throws IllegalArgumentException
         * 		if k is out of range
         */         public synchronized double getOmegaImaginary(int k) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {             if ((omegaCount) == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.util.LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET);
            }
            if ((k < 0) || (k >= (omegaCount))) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, k, 0, ((omegaCount) - 1));
            }

            return isForward ? omegaImaginaryForward[k] : omegaImaginaryInverse[k];

        }

    }

}