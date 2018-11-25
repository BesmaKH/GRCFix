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
package org.apache.commons.math;




















/**
 *
 *
 * @version $Revision$ $Date$
 */ public class TestUtils {     /**
     * Collection of static methods used in math unit tests.
     */
    private TestUtils() {
        super();
    }

    /**
     * Verifies that expected and actual are within delta, or are both NaN or
     * infinities of the same sign.
     */
    public static void assertEquals(double expected, double actual, double delta) {
        org.apache.commons.math.TestUtils.assertEquals(null, expected, actual, delta);
    }

    /**
     * Verifies that expected and actual are within delta, or are both NaN or
     * infinities of the same sign.
     */
    public static void assertEquals(java.lang.String msg, double expected, double actual, double delta) {
        // check for NaN
        if (java.lang.Double.isNaN(expected)) {
            junit.framework.Assert.assertTrue((("" + actual) + " is not NaN."), 
            java.lang.Double.isNaN(actual));
        }else {
            junit.framework.Assert.assertEquals(msg, expected, actual, delta);
        }
    }

    /**
     * Verifies that the two arguments are exactly the same, either
     * both NaN or infinities of same sign, or identical floating point values.
     */
    public static void assertSame(double expected, double actual) {
        org.apache.commons.math.TestUtils.assertEquals(expected, actual, 0);
    }

    /**
     * Verifies that real and imaginary parts of the two complex arguments
     * are exactly the same.  Also ensures that NaN / infinite components match.
     */
    public static void assertSame(org.apache.commons.math.complex.Complex expected, org.apache.commons.math.complex.Complex actual) {
        org.apache.commons.math.TestUtils.assertSame(expected.getReal(), actual.getReal());
        org.apache.commons.math.TestUtils.assertSame(expected.getImaginary(), actual.getImaginary());
    }

    /**
     * Verifies that real and imaginary parts of the two complex arguments
     * differ by at most delta.  Also ensures that NaN / infinite components match.
     */
    public static void assertEquals(org.apache.commons.math.complex.Complex expected, org.apache.commons.math.complex.Complex actual, double delta) {
        org.apache.commons.math.TestUtils.assertEquals(expected.getReal(), actual.getReal(), delta);
        org.apache.commons.math.TestUtils.assertEquals(expected.getImaginary(), actual.getImaginary(), delta);
    }

    /**
     * Verifies that two double arrays have equal entries, up to tolerance
     */
    public static void assertEquals(double[] expected, double[] observed, double tolerance) {
        org.apache.commons.math.TestUtils.assertEquals("Array comparison failure", expected, observed, tolerance);
    }

    /**
     * Serializes an object to a bytes array and then recovers the object from the bytes array.
     * Returns the deserialized object.
     *
     * @param o
     * 		object to serialize and recover
     * @return the recovered, deserialized object
     */     public static java.lang.Object serializeAndRecover(java.lang.Object o) {
        try {
            // serialize the Object
            java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream so = new java.io.ObjectOutputStream(bos);
            so.writeObject(o);

            // deserialize the Object
            java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(bos.toByteArray());
            java.io.ObjectInputStream si = new java.io.ObjectInputStream(bis);
            return si.readObject();
        } catch (java.io.IOException ioe) {
            return null;
        } catch (java.lang.ClassNotFoundException cnfe) {
            return null;
        }
    }

    /**
     * Verifies that serialization preserves equals and hashCode.
     * Serializes the object, then recovers it and checks equals and hash code.
     *
     * @param object
     * 		the object to serialize and recover
     */     public static void checkSerializedEquality(java.lang.Object object) {
        java.lang.Object object2 = org.apache.commons.math.TestUtils.serializeAndRecover(object);
        junit.framework.Assert.assertEquals("Equals check", object, object2);
        junit.framework.Assert.assertEquals("HashCode check", object.hashCode(), object2.hashCode());
    }

    /**
     * Verifies that the relative error in actual vs. expected is less than or
     * equal to relativeError.  If expected is infinite or NaN, actual must be
     * the same (NaN or infinity of the same sign).
     *
     * @param expected
     * 		expected value
     * @param actual
     * 		observed value
     * @param relativeError
     * 		maximum allowable relative error
     */     public static void assertRelativelyEquals(double expected, double actual, double relativeError) {         org.apache.commons.math.TestUtils.assertRelativelyEquals(null, expected, actual, relativeError);
    }

    /**
     * Verifies that the relative error in actual vs. expected is less than or
     * equal to relativeError.  If expected is infinite or NaN, actual must be
     * the same (NaN or infinity of the same sign).
     *
     * @param msg
     * 		message to return with failure
     * @param expected
     * 		expected value
     * @param actual
     * 		observed value
     * @param relativeError
     * 		maximum allowable relative error
     */     public static void assertRelativelyEquals(java.lang.String msg, double expected, double actual, double relativeError) {         if (java.lang.Double.isNaN(expected)) {             junit.framework.Assert.assertTrue(msg, java.lang.Double.isNaN(actual));
        }else             if (java.lang.Double.isNaN(actual)) {
                junit.framework.Assert.assertTrue(msg, java.lang.Double.isNaN(expected));
            }else                 if ((java.lang.Double.isInfinite(actual)) || (java.lang.Double.isInfinite(expected))) {
                    junit.framework.Assert.assertEquals(expected, actual, relativeError);
                }else                     if (expected == 0.0) {
                        junit.framework.Assert.assertEquals(msg, actual, expected, relativeError);
                    }else {
                        double absError = (java.lang.Math.abs(expected)) * relativeError;
                        junit.framework.Assert.assertEquals(msg, expected, actual, absError);
                    }
    }

    /**
     * Fails iff values does not contain a number within epsilon of z.
     *
     * @param msg
     * 		message to return with failure
     * @param values
     * 		complex array to search
     * @param z
     * 		value sought
     * @param epsilon
     * 		tolerance
     */     public static void assertContains(java.lang.String msg, org.apache.commons.math.complex.Complex[] values, org.apache.commons.math.complex.Complex z, double epsilon) {         int i = 0;         boolean found = false;
        while ((!found) && (i < (values.length))) {
            try {
                org.apache.commons.math.TestUtils.assertEquals(values[i], z, epsilon);
                found = true;
            } catch (junit.framework.AssertionFailedError er) {
                // no match
            }
            i++;
        } 
        if (!found) {
            junit.framework.Assert.fail(((msg + 
            " Unable to find ") + (org.apache.commons.math.complex.ComplexFormat.formatComplex(z))));
        }
    }

    /**
     * Fails iff values does not contain a number within epsilon of z.
     *
     * @param values
     * 		complex array to search
     * @param z
     * 		value sought
     * @param epsilon
     * 		tolerance
     */     public static void assertContains(org.apache.commons.math.complex.Complex[] values, org.apache.commons.math.complex.Complex z, double epsilon) {         org.apache.commons.math.TestUtils.assertContains(null, values, z, epsilon);
    }

    /**
     * Fails iff values does not contain a number within epsilon of x.
     *
     * @param msg
     * 		message to return with failure
     * @param values
     * 		double array to search
     * @param x
     * 		value sought
     * @param epsilon
     * 		tolerance
     */     public static void assertContains(java.lang.String msg, double[] values, double x, double epsilon) {         int i = 0;         boolean found = false;
        while ((!found) && (i < (values.length))) {
            try {
                org.apache.commons.math.TestUtils.assertEquals(values[i], x, epsilon);
                found = true;
            } catch (junit.framework.AssertionFailedError er) {
                // no match
            }
            i++;
        } 
        if (!found) {
            junit.framework.Assert.fail(((msg + " Unable to find") + x));
        }
    }

    /**
     * Fails iff values does not contain a number within epsilon of x.
     *
     * @param values
     * 		double array to search
     * @param x
     * 		value sought
     * @param epsilon
     * 		tolerance
     */     public static void assertContains(double[] values, double x, double epsilon) {         org.apache.commons.math.TestUtils.assertContains(null, values, x, epsilon);
    }

    /**
     * verifies that two matrices are close (1-norm)
     */     public static void assertEquals(java.lang.String msg, org.apache.commons.math.linear.RealMatrix expected, org.apache.commons.math.linear.RealMatrix observed, double tolerance) {

        junit.framework.Assert.assertNotNull((msg + "\nObserved should not be null"), observed);

        if (((expected.getColumnDimension()) != (observed.getColumnDimension())) || 
        ((expected.getRowDimension()) != (observed.getRowDimension()))) {
            java.lang.StringBuffer messageBuffer = new java.lang.StringBuffer(msg);
            messageBuffer.append("\nObserved has incorrect dimensions.");
            messageBuffer.append(((("\nobserved is " + (observed.getRowDimension())) + 
            " x ") + (observed.getColumnDimension())));
            messageBuffer.append(((("\nexpected " + (expected.getRowDimension())) + 
            " x ") + (expected.getColumnDimension())));
            junit.framework.Assert.fail(messageBuffer.toString());
        }

        org.apache.commons.math.linear.RealMatrix delta = expected.subtract(observed);
        if ((delta.getNorm()) >= tolerance) {
            java.lang.StringBuffer messageBuffer = new java.lang.StringBuffer(msg);
            messageBuffer.append(("\nExpected: " + expected));
            messageBuffer.append(("\nObserved: " + observed));
            messageBuffer.append(("\nexpected - observed: " + delta));
            junit.framework.Assert.fail(messageBuffer.toString());
        }
    }

    /**
     * verifies that two matrices are equal
     */     public static void assertEquals(org.apache.commons.math.linear.FieldMatrix<? extends org.apache.commons.math.FieldElement<?>> expected, org.apache.commons.math.linear.FieldMatrix<? extends org.apache.commons.math.FieldElement<?>> observed) {

        junit.framework.Assert.assertNotNull("Observed should not be null", observed);

        if (((expected.getColumnDimension()) != (observed.getColumnDimension())) || 
        ((expected.getRowDimension()) != (observed.getRowDimension()))) {
            java.lang.StringBuffer messageBuffer = new java.lang.StringBuffer();
            messageBuffer.append("Observed has incorrect dimensions.");
            messageBuffer.append(((("\nobserved is " + (observed.getRowDimension())) + 
            " x ") + (observed.getColumnDimension())));
            messageBuffer.append(((("\nexpected " + (expected.getRowDimension())) + 
            " x ") + (expected.getColumnDimension())));
            junit.framework.Assert.fail(messageBuffer.toString());
        }

        for (int i = 0; i < (expected.getRowDimension()); ++i) {
            for (int j = 0; j < (expected.getColumnDimension()); ++j) {
                org.apache.commons.math.FieldElement<?> eij = expected.getEntry(i, j);
                org.apache.commons.math.FieldElement<?> oij = observed.getEntry(i, j);
                junit.framework.Assert.assertEquals(eij, oij);
            }
        }
    }

    /**
     * verifies that two arrays are close (sup norm)
     */     public static void assertEquals(java.lang.String msg, double[] expected, double[] observed, double tolerance) {
        java.lang.StringBuffer out = new java.lang.StringBuffer(msg);
        if ((expected.length) != (observed.length)) {
            out.append("\n Arrays not same length. \n");
            out.append("expected has length ");
            out.append(expected.length);
            out.append(" observed length = ");
            out.append(observed.length);
            junit.framework.Assert.fail(out.toString());
        }
        boolean failure = false;
        for (int i = 0; i < (expected.length); i++) {
            try {
                org.apache.commons.math.TestUtils.assertEquals(expected[i], observed[i], tolerance);
            } catch (junit.framework.AssertionFailedError ex) {
                failure = true;
                out.append("\n Elements at index ");
                out.append(i);
                out.append(" differ. ");
                out.append(" expected = ");
                out.append(expected[i]);
                out.append(" observed = ");
                out.append(observed[i]);
            }
        }
        if (failure) {
            junit.framework.Assert.fail(out.toString());
        }
    }

    /**
     * verifies that two arrays are equal
     */     public static <T extends org.apache.commons.math.FieldElement<T>> void assertEquals(T[] m, T[] n) {         if ((m.length) != (n.length)) {
            junit.framework.Assert.fail("vectors not same length");
        }
        for (int i = 0; i < (m.length); i++) {
            junit.framework.Assert.assertEquals(m[i], n[i]);
        }
    }

    /**
     * Computes the sum of squared deviations of <values> from <target>
     *
     * @param values
     * 		array of deviates
     * @param target
     * 		value to compute deviations from
     * @return sum of squared deviations
     */     public static double sumSquareDev(double[] values, double target) {         double sumsq = 0.0;
        for (int i = 0; i < (values.length); i++) {
            final double dev = (values[i]) - target;
            sumsq += dev * dev;
        }
        return sumsq;
    }

    /**
     * Asserts the null hypothesis for a ChiSquare test.  Fails and dumps arguments and test
     * statistics if the null hypothesis can be rejected with confidence 100 * (1 - alpha)%
     *
     * @param valueLabels
     * 		
     * @param expected
     * 		expected counts
     * @param observed
     * 		observed counts
     * @param alpha
     * 		significance level of the test
     */     public static void assertChiSquareAccept(java.lang.String[] valueLabels, double[] expected, long[] observed, double alpha) throws java.lang.Exception {         org.apache.commons.math.stat.inference.ChiSquareTest chiSquareTest = new org.apache.commons.math.stat.inference.ChiSquareTestImpl();         try {             // Fail if we can reject null hypothesis that distributions are the same
            junit.framework.Assert.assertFalse(chiSquareTest.chiSquareTest(expected, observed, alpha));
        } catch (junit.framework.AssertionFailedError ex) {
            java.lang.StringBuffer msgBuffer = new java.lang.StringBuffer();
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
            msgBuffer.append("Chisquare test failed");
            msgBuffer.append(" p-value = ");
            msgBuffer.append(chiSquareTest.chiSquareTest(expected, observed));
            msgBuffer.append(" chisquare statistic = ");
            msgBuffer.append(chiSquareTest.chiSquare(expected, observed));
            msgBuffer.append(". \n");
            msgBuffer.append("value\texpected\tobserved\n");
            for (int i = 0; i < (expected.length); i++) {
                msgBuffer.append(valueLabels[i]);
                msgBuffer.append("\t");
                msgBuffer.append(df.format(expected[i]));
                msgBuffer.append("\t\t");
                msgBuffer.append(observed[i]);
                msgBuffer.append("\n");
            }
            msgBuffer.append("This test can fail randomly due to sampling error with probability ");
            msgBuffer.append(alpha);
            msgBuffer.append(".");
            junit.framework.Assert.fail(msgBuffer.toString());
        }
    }

    /**
     * Asserts the null hypothesis for a ChiSquare test.  Fails and dumps arguments and test
     * statistics if the null hypothesis can be rejected with confidence 100 * (1 - alpha)%
     *
     * @param values
     * 		
     * @param expected
     * 		expected counts
     * @param observed
     * 		observed counts
     * @param alpha
     * 		significance level of the test
     */     public static void assertChiSquareAccept(int[] values, double[] expected, long[] observed, double alpha) throws java.lang.Exception {         java.lang.String[] labels = new java.lang.String[values.length];         for (int i = 0; i < (values.length); i++) {             labels[i] = java.lang.Integer.toString(values[i]);
        }
        org.apache.commons.math.TestUtils.assertChiSquareAccept(labels, expected, observed, alpha);
    }

    /**
     * Asserts the null hypothesis for a ChiSquare test.  Fails and dumps arguments and test
     * statistics if the null hypothesis can be rejected with confidence 100 * (1 - alpha)%
     *
     * @param values
     * 		
     * @param expected
     * 		expected counts
     * @param observed
     * 		observed counts
     * @param alpha
     * 		significance level of the test
     */     public static void assertChiSquareAccept(double[] values, double[] expected, long[] observed, double alpha) throws java.lang.Exception {         java.lang.String[] labels = new java.lang.String[values.length];         for (int i = 0; i < (values.length); i++) {             labels[i] = java.lang.Double.toString(values[i]);
        }
        org.apache.commons.math.TestUtils.assertChiSquareAccept(labels, expected, observed, alpha);
    }

    /**
     * Asserts the null hypothesis for a ChiSquare test.  Fails and dumps arguments and test
     * statistics if the null hypothesis can be rejected with confidence 100 * (1 - alpha)%
     *
     * @param expected
     * 		expected counts
     * @param observed
     * 		observed counts
     * @param alpha
     * 		significance level of the test
     */     public static void assertChiSquareAccept(double[] expected, long[] observed, double alpha) throws java.lang.Exception {         java.lang.String[] labels = new java.lang.String[expected.length];         for (int i = 0; i < (labels.length); i++) {
            labels[i] = java.lang.Integer.toString((i + 1));
        }
        org.apache.commons.math.TestUtils.assertChiSquareAccept(labels, expected, observed, alpha);
    }

    /**
     * Computes the 25th, 50th and 75th percentiles of the given distribution and returns
     * these values in an array.
     */
    public static double[] getDistributionQuartiles(org.apache.commons.math.distribution.ContinuousDistribution distribution) throws java.lang.Exception {
        double[] quantiles = new double[3];
        quantiles[0] = distribution.inverseCumulativeProbability(0.25);
        quantiles[1] = distribution.inverseCumulativeProbability(0.5);
        quantiles[2] = distribution.inverseCumulativeProbability(0.75);
        return quantiles;
    }

    /**
     * Updates observed counts of values in quartiles.
     * counts[0] <-> 1st quartile ... counts[3] <-> top quartile
     */
    public static void updateCounts(double value, long[] counts, double[] quartiles) {
        if (value < (quartiles[0])) {
            (counts[0])++;
        }else             if (value > (quartiles[2])) {
                (counts[3])++;
            }else                 if (value > (quartiles[1])) {
                    (counts[2])++;
                }else {
                    (counts[1])++;
                }
    }

    /**
     * Eliminates points with zero mass from densityPoints and densityValues parallel
     * arrays.  Returns the number of positive mass points and collapses the arrays so
     * that the first <returned value> elements of the input arrays represent the positive
     * mass points.
     */
    public static int eliminateZeroMassPoints(int[] densityPoints, double[] densityValues) {
        int positiveMassCount = 0;
        for (int i = 0; i < (densityValues.length); i++) {
            if ((densityValues[i]) > 0) {
                positiveMassCount++;
            }
        }
        if (positiveMassCount < (densityValues.length)) {
            int[] newPoints = new int[positiveMassCount];
            double[] newValues = new double[positiveMassCount];
            int j = 0;
            for (int i = 0; i < (densityValues.length); i++) {
                if ((densityValues[i]) > 0) {
                    newPoints[j] = densityPoints[i];
                    newValues[j] = densityValues[i];
                    j++;
                }
            }
            java.lang.System.arraycopy(newPoints, 0, densityPoints, 0, positiveMassCount);
            java.lang.System.arraycopy(newValues, 0, densityValues, 0, positiveMassCount);
        }
        return positiveMassCount;
    }
}