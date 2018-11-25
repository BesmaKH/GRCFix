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
 */ public class FunctionEvaluationExceptionTest extends junit.framework.TestCase {
    public void testConstructor() {
        org.apache.commons.math.FunctionEvaluationException ex = new org.apache.commons.math.FunctionEvaluationException(0.0);
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertNotNull(ex.getMessage());
        junit.framework.Assert.assertTrue(((ex.getMessage().indexOf("0")) > 0));
        junit.framework.Assert.assertEquals(0.0, ex.getArgument()[0], 0);
    }

    public void testConstructorArray() {
        org.apache.commons.math.FunctionEvaluationException ex = 
        new org.apache.commons.math.FunctionEvaluationException(new double[]{ 0, 1, 2 });
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertNotNull(ex.getMessage());
        junit.framework.Assert.assertTrue(((ex.getMessage().indexOf("0")) > 0));
        junit.framework.Assert.assertEquals(0.0, ex.getArgument()[0], 0);
        junit.framework.Assert.assertEquals(1.0, ex.getArgument()[1], 0);
        junit.framework.Assert.assertEquals(2.0, ex.getArgument()[2], 0);
    }

    public void testConstructorPatternArguments() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.EVALUATION_FAILED;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Double.valueOf(0.0) };
        org.apache.commons.math.FunctionEvaluationException ex = new org.apache.commons.math.FunctionEvaluationException(0.0, pattern, arguments);
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

    public void testConstructorArrayPatternArguments() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.EVALUATION_FAILED;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Double.valueOf(0.0) };
        org.apache.commons.math.FunctionEvaluationException ex = 
        new org.apache.commons.math.FunctionEvaluationException(new double[]{ 0, 1, 2 }, pattern, arguments);
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
        junit.framework.Assert.assertEquals(0.0, ex.getArgument()[0], 0);
        junit.framework.Assert.assertEquals(1.0, ex.getArgument()[1], 0);
        junit.framework.Assert.assertEquals(2.0, ex.getArgument()[2], 0);
    }

    public void testConstructorPatternArgumentsCause() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.EVALUATION_FAILED;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Double.valueOf(0.0) };
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.FunctionEvaluationException ex = new org.apache.commons.math.FunctionEvaluationException(cause, 0.0, pattern, arguments);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

    public void testConstructorArrayPatternArgumentsCause() {
        org.apache.commons.math.util.LocalizedFormats pattern = org.apache.commons.math.util.LocalizedFormats.EVALUATION_FAILED;
        java.lang.Object[] arguments = new java.lang.Object[]{ java.lang.Double.valueOf(0.0) };
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.FunctionEvaluationException ex = 
        new org.apache.commons.math.FunctionEvaluationException(cause, new double[]{ 0, 1, 2 }, pattern, arguments);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
        junit.framework.Assert.assertEquals(pattern, ex.getLocalizablePattern());
        junit.framework.Assert.assertEquals(arguments.length, ex.getArguments().length);
        for (int i = 0; i < (arguments.length); ++i) {
            junit.framework.Assert.assertEquals(arguments[i], ex.getArguments()[i]);
        }
        junit.framework.Assert.assertFalse(pattern.equals(ex.getMessage()));
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
        junit.framework.Assert.assertEquals(0.0, ex.getArgument()[0], 0);
        junit.framework.Assert.assertEquals(1.0, ex.getArgument()[1], 0);
        junit.framework.Assert.assertEquals(2.0, ex.getArgument()[2], 0);
    }

    public void testConstructorArgumentCause() {
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.FunctionEvaluationException ex = new org.apache.commons.math.FunctionEvaluationException(cause, 0.0);
        junit.framework.Assert.assertEquals(cause, ex.getCause());
        junit.framework.Assert.assertTrue(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

    public void testConstructorArrayArgumentCause() {
        java.lang.String inMsg = "inner message";
        java.lang.Exception cause = new java.lang.Exception(inMsg);
        org.apache.commons.math.FunctionEvaluationException ex = 
        new org.apache.commons.math.FunctionEvaluationException(cause, new double[]{ 0, 1, 2 });
        junit.framework.Assert.assertEquals(cause, ex.getCause());
        junit.framework.Assert.assertTrue(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
        junit.framework.Assert.assertEquals(0.0, ex.getArgument()[0], 0);
        junit.framework.Assert.assertEquals(1.0, ex.getArgument()[1], 0);
        junit.framework.Assert.assertEquals(2.0, ex.getArgument()[2], 0);
    }

}