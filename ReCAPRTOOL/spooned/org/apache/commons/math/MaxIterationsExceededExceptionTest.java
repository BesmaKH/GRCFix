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
 */ public class MaxIterationsExceededExceptionTest extends junit.framework.TestCase {
    public void testSimpleConstructor() {
        org.apache.commons.math.MaxIterationsExceededException ex = new org.apache.commons.math.MaxIterationsExceededException(1000000);
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertNotNull(ex.getMessage());
        junit.framework.Assert.assertTrue(((ex.getMessage().indexOf("1,000,000")) > 0));
        junit.framework.Assert.assertEquals(1000000, ex.getMaxIterations());
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

    public void testComplexConstructor() {
        org.apache.commons.math.MaxIterationsExceededException ex = 
        new org.apache.commons.math.MaxIterationsExceededException(1000000, 
        org.apache.commons.math.util.LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION, 
        1234567);
        junit.framework.Assert.assertNull(ex.getCause());
        junit.framework.Assert.assertNotNull(ex.getMessage());
        junit.framework.Assert.assertTrue(((ex.getMessage().indexOf("1,000,000")) < 0));
        junit.framework.Assert.assertTrue(((ex.getMessage().indexOf("1,234,567")) > 0));
        junit.framework.Assert.assertEquals(1000000, ex.getMaxIterations());
        junit.framework.Assert.assertFalse(ex.getMessage().equals(ex.getMessage(java.util.Locale.FRENCH)));
    }

}