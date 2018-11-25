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
package org.apache.commons.math.analysis.interpolation;














/**
 * Interpolating function that implements the
 * <a href="http://www.dudziak.com/microsphere.php">Microsphere Projection</a>.
 *
 * @version $Revision$ $Date$
 */
public class MicrosphereInterpolatingFunction implements 
org.apache.commons.math.analysis.MultivariateRealFunction {
    /**
     * Space dimension.
     */
    private final int dimension;
    /**
     * Internal accounting data for the interpolation algorithm.
     * Each element of the list corresponds to one surface element of
     * the microsphere.
     */
    private final java.util.List<org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement> microsphere;
    /**
     * Exponent used in the power law that computes the weights of the
     * sample data.
     */
    private final double brightnessExponent;
    /**
     * Sample data.
     */
    private final java.util.Map<org.apache.commons.math.linear.RealVector, java.lang.Double> samples;

    /**
     * Class for storing the accounting data needed to perform the
     * microsphere projection.
     */
    private static class MicrosphereSurfaceElement {

        /**
         * Normal vector characterizing a surface element.
         */         private final org.apache.commons.math.linear.RealVector normal;
        /**
         * Illumination received from the brightest sample.
         */         private double brightestIllumination;
        /**
         * Brightest sample.
         */         private java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> brightestSample;
        /**
         *
         *
         * @param n
         * 		Normal vector characterizing a surface element
         * 		of the microsphere.
         */         MicrosphereSurfaceElement(double[] n) {             normal = new org.apache.commons.math.linear.ArrayRealVector(n);}

        /**
         * Return the normal vector.
         *
         * @return the normal vector
         */         org.apache.commons.math.linear.RealVector normal() {
            return normal;
        }

        /**
         * Reset "illumination" and "sampleIndex".
         */
        void reset() {
            brightestIllumination = 0;
            brightestSample = null;
        }

        /**
         * Store the illumination and index of the brightest sample.
         *
         * @param illuminationFromSample
         * 		illumination received from sample
         * @param sample
         * 		current sample illuminating the element
         */         void store(final double illuminationFromSample, final java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> sample) {             if (illuminationFromSample > (this.brightestIllumination)) {
                this.brightestIllumination = illuminationFromSample;
                this.brightestSample = sample;
            }
        }

        /**
         * Get the illumination of the element.
         *
         * @return the illumination.
         */         double illumination() {
            return brightestIllumination;
        }

        /**
         * Get the sample illuminating the element the most.
         *
         * @return the sample.
         */         java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> sample() {
            return brightestSample;
        }
    }

    /**
     *
     *
     * @param xval
     * 		the arguments for the interpolation points.
     * 		{@code xval[i][0]} is the first component of interpolation point
     * 		{@code i}, {@code xval[i][1]} is the second component, and so on
     * 		until {@code xval[i][d-1]}, the last component of that interpolation
     * 		point (where {@code dimension} is thus the dimension of the sampled
     * 		space).
     * @param yval
     * 		the values for the interpolation points
     * @param brightnessExponent
     * 		Brightness dimming factor.
     * @param microsphereElements
     * 		Number of surface elements of the
     * 		microsphere.
     * @param rand
     * 		Unit vector generator for creating the microsphere.
     * @throws DimensionMismatchException
     * 		if the lengths of {@code yval} and
     * 		{@code xval} (equal to {@code n}, the number of interpolation points)
     * 		do not match, or the the arrays {@code xval[0]} ... {@code xval[n]},
     * 		have lengths different from {@code dimension}.
     * @throws NoDataException
     * 		if there are no data (xval null or zero length)
     */     public MicrosphereInterpolatingFunction(double[][] xval, double[] yval, int brightnessExponent, int microsphereElements, org.apache.commons.math.random.UnitSphereRandomVectorGenerator rand) throws java.lang.IllegalArgumentException, org.apache.commons.math.DimensionMismatchException {         if (((xval.length) == 0) || ((xval[0]) == null)) {             throw new org.apache.commons.math.exception.NoDataException();}

        if ((xval.length) != (yval.length)) {
            throw new org.apache.commons.math.DimensionMismatchException(xval.length, yval.length);
        }

        dimension = xval[0].length;
        this.brightnessExponent = brightnessExponent;

        // Copy data samples.
        samples = new java.util.HashMap<org.apache.commons.math.linear.RealVector, java.lang.Double>(yval.length);
        for (int i = 0; i < (xval.length); ++i) {
            final double[] xvalI = xval[i];
            if ((xvalI.length) != (dimension)) {
                throw new org.apache.commons.math.DimensionMismatchException(xvalI.length, dimension);
            }

            samples.put(new org.apache.commons.math.linear.ArrayRealVector(xvalI), yval[i]);
        }

        microsphere = new java.util.ArrayList<org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement>(microsphereElements);
        // Generate the microsphere, assuming that a fairly large number of
        // randomly generated normals will represent a sphere.
        for (int i = 0; i < microsphereElements; i++) {
            microsphere.add(new org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement(rand.nextVector()));
        }

    }

    /**
     *
     *
     * @param point
     * 		Interpolation point.
     * @return the interpolated value.
     */     public double value(double[] point) {         final org.apache.commons.math.linear.RealVector p = new org.apache.commons.math.linear.ArrayRealVector(point);

        // Reset.
        for (org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement md : microsphere) {
            md.reset();
        }

        // Compute contribution of each sample points to the microsphere elements illumination
        for (java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> sd : samples.entrySet()) {

            // Vector between interpolation point and current sample point.
            final org.apache.commons.math.linear.RealVector diff = sd.getKey().subtract(p);
            final double diffNorm = diff.getNorm();

            if ((java.lang.Math.abs(diffNorm)) < (java.lang.Math.ulp(1.0))) {
                // No need to interpolate, as the interpolation point is
                // actually (very close to) one of the sampled points.
                return sd.getValue();
            }

            for (org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement md : microsphere) {
                final double w = java.lang.Math.pow(diffNorm, (-(brightnessExponent)));
                md.store(((cosAngle(diff, md.normal())) * w), sd);
            }

        }

        // Interpolation calculation.
        double value = 0;
        double totalWeight = 0;
        for (org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement md : microsphere) {
            final double iV = md.illumination();
            final java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> sd = md.sample();
            if (sd != null) {
                value += iV * (sd.getValue());
                totalWeight += iV;
            }
        }

        return value / totalWeight;

    }

    /**
     * Compute the cosine of the angle between 2 vectors.
     *
     * @param v
     * 		Vector.
     * @param w
     * 		Vector.
     * @return cosine of the angle
     */     private double cosAngle(final org.apache.commons.math.linear.RealVector v, final org.apache.commons.math.linear.RealVector w) {         return (v.dotProduct(w)) / ((v.getNorm()) * (w.getNorm()));
    }

}