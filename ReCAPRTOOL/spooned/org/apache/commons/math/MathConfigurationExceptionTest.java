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
 */ public class MathConfigurationExceptionTest extends junit.framework.TestCase {
    public void testConstructor() {
        org.apache.commons.math.MathConfigurationException ex = new org.apache.commons.math.MathConfigurationException();
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertEquals("", ex.getMessage());
        junit.framework.Assert.assertEquals("", ex.getMessage(java.util.Locale.FRENCH));
    }

    public void testConstructorPatternArguments() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.ROTATION_MATRIX_DIMENSIONS;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Integer.valueOf(6), java.lang.Integer.valueOf(4) };
        org.apache.commons.math.MathConfigurationException ex = new org.apache.commons.math.MathConfigurationException(pattern, arguments);
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
        org.apache.commons.math.MathConfigurationException ex = new org.apache.commons.math.MathConfigurationException(cause);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
    }

    public void testConstructorPatternArgumentsCause() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.ROTATION_MATRIX_DIMENSIONS;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Integer.valueOf(6), java.lang.Integer.valueOf(4) };
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.MathConfigurationException ex = new org.apache.commons.math.MathConfigurationException(cause, pattern, arguments);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

}