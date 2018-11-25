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
 */ public class MathExceptionTest extends junit.framework.TestCase {
    public void testConstructor() {
        org.apache.commons.math.MathException ex = new org.apache.commons.math.MathException();
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertEquals("", ex.getMessage());
        junit.framework.Assert.assertEquals("", ex.getMessage(java.util.Locale.FRENCH));
    }

    public void testConstructorPatternArguments() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.ROTATION_MATRIX_DIMENSIONS;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Integer.valueOf(6), java.lang.Integer.valueOf(4) };
        org.apache.commons.math.MathException ex = new org.apache.commons.math.MathException(pattern, arguments);
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

    public void testConstructorCause() {
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.MathException ex = new org.apache.commons.math.MathException(cause);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
    }

    public void testConstructorPatternArgumentsCause() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.ROTATION_MATRIX_DIMENSIONS;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Integer.valueOf(6), java.lang.Integer.valueOf(4) };
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.MathException ex = new org.apache.commons.math.MathException(cause, pattern, arguments);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

    /**
     * Tests the printStackTrace() operation.
     */
    public void testPrintStackTrace() {
        org.apache.commons.math.util.Localizable outMsg = new org.apache.commons.math.util.DummyLocalizable("outer message");
        org.apache.commons.math.util.Localizable inMsg = new org.apache.commons.math.util.DummyLocalizable("inner message");
        org.apache.commons.math.MathException cause = new org.apache.commons.math.MathConfigurationException(inMsg);
        org.apache.commons.math.MathException ex = new org.apache.commons.math.MathException(cause, outMsg);
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        ex.printStackTrace(ps);
        java.lang.String stack = baos.toString();
        java.lang.String outerMsg = "org.apache.commons.math.MathException: outer message";
        java.lang.String innerMsg = "Caused by: " + 
        "org.apache.commons.math.MathConfigurationException: inner message";
        junit.framework.Assert.assertTrue(stack.startsWith(outerMsg));
        junit.framework.Assert.assertTrue(((stack.indexOf(innerMsg)) > 0));

        java.io.PrintWriter pw = new java.io.PrintWriter(ps, true);
        ex.printStackTrace(pw);
        stack = baos.toString();
        junit.framework.Assert.assertTrue(stack.startsWith(outerMsg));
        junit.framework.Assert.assertTrue(((stack.indexOf(innerMsg)) > 0));
    }

    /**
     * Test serialization
     */
    public void testSerialization() {
        org.apache.commons.math.util.Localizable outMsg = new org.apache.commons.math.util.DummyLocalizable("outer message");
        org.apache.commons.math.util.Localizable inMsg = new org.apache.commons.math.util.DummyLocalizable("inner message");
        org.apache.commons.math.MathException cause = new org.apache.commons.math.MathConfigurationException(inMsg);
        org.apache.commons.math.MathException ex = new org.apache.commons.math.MathException(cause, outMsg);
        org.apache.commons.math.MathException image = ((org.apache.commons.math.MathException) (org.apache.commons.math.TestUtils.serializeAndRecover(ex)));

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        ex.printStackTrace(ps);
        java.lang.String stack = baos.toString();

        java.io.ByteArrayOutputStream baos2 = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps2 = new java.io.PrintStream(baos2);
        image.printStackTrace(ps2);
        java.lang.String stack2 = baos2.toString();

        // See if JDK supports nested exceptions.  If not, stack trace of
        // inner exception will not be serialized
        boolean jdkSupportsNesting = false;
        try {
            java.lang.Throwable.class.getDeclaredMethod("getCause", new java.lang.Class[0]);
            jdkSupportsNesting = true;
        } catch (java.lang.NoSuchMethodException e) {
            jdkSupportsNesting = false;
        }

        if (jdkSupportsNesting) {
            junit.framework.Assert.assertEquals(stack, stack2);
        }else {
            junit.framework.Assert.assertTrue(((stack2.indexOf(inMsg.getSourceString())) != (-1)));
            junit.framework.Assert.assertTrue(((stack2.indexOf("MathConfigurationException")) != (-1)));
        }
    }
}