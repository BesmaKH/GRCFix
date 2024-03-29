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
 * Interpolator that implements the algorithm described in
 * <em>William Dudziak</em>'s
 * <a href="http://www.dudziak.com/microsphere.pdf">MS thesis</a>.
 *
 * @since 2.1
 * @version $Revision$ $Date$
 */
public class MicrosphereInterpolator implements 
org.apache.commons.math.analysis.interpolation.MultivariateRealInterpolator {

    /**
     * Default number of surface elements that composes the microsphere.
     */
    public static final int DEFAULT_MICROSPHERE_ELEMENTS = 2000;

    /**
     * Default exponent used the weights calculation.
     */
    public static final int DEFAULT_BRIGHTNESS_EXPONENT = 2;

    /**
     * Number of surface elements of the microsphere.
     */
    private int microsphereElements;

    /**
     * Exponent used in the power law that computes the weights of the
     * sample data.
     */
    private int brightnessExponent;

    /**
     * Create a microsphere interpolator with default settings.
     * <p>Calling this constructor is equivalent to call {@link
     * #MicrosphereInterpolator(int, int)
     * MicrosphereInterpolator(MicrosphereInterpolator.DEFAULT_MICROSPHERE_ELEMENTS,
     * MicrosphereInterpolator.DEFAULT_BRIGHTNESS_EXPONENT)}.</p>
     */     public MicrosphereInterpolator() {
        this(org.apache.commons.math.analysis.interpolation.MicrosphereInterpolator.DEFAULT_MICROSPHERE_ELEMENTS, org.apache.commons.math.analysis.interpolation.MicrosphereInterpolator.DEFAULT_BRIGHTNESS_EXPONENT);
    }

    /**
     * Create a microsphere interpolator.
     *
     * @param microsphereElements
     * 		number of surface elements of the microsphere.
     * @param brightnessExponent
     * 		exponent used in the power law that computes the
     * 		weights of the sample data.
     * @throws NotPositiveException
     * 		if {@code microsphereElements <= 0}
     * 		or {@code brightnessExponent < 0}.
     */     public MicrosphereInterpolator(final int microsphereElements, final int brightnessExponent) {         setMicropshereElements(microsphereElements);         setBrightnessExponent(brightnessExponent);}

    /**
     * {@inheritDoc}
     */
    public org.apache.commons.math.analysis.MultivariateRealFunction interpolate(final double[][] xval, final 
    double[] yval) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        final org.apache.commons.math.random.UnitSphereRandomVectorGenerator rand = 
        new org.apache.commons.math.random.UnitSphereRandomVectorGenerator(xval[0].length);
        return new org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction(xval, yval, 
        brightnessExponent, 
        microsphereElements, 
        rand);
    }

    /**
     * Set the brightness exponent.
     *
     * @param exponent
     * 		Exponent for computing the distance dimming
     * 		factor.
     * @throws NotPositiveException
     * 		if {@code exponent < 0}.
     */     public void setBrightnessExponent(final int exponent) {         if (exponent < 0) {             throw new org.apache.commons.math.exception.NotPositiveException(exponent);
        }
        brightnessExponent = exponent;
    }

    /**
     * Set the number of microsphere elements.
     *
     * @param elements
     * 		Number of surface elements of the microsphere.
     * @throws NotStrictlyPositiveException
     * 		if {@code elements <= 0}.
     */     public void setMicropshereElements(final int elements) {         if (elements <= 0) {             throw new org.apache.commons.math.exception.NotStrictlyPositiveException(elements);
        }
        microsphereElements = elements;
    }
}