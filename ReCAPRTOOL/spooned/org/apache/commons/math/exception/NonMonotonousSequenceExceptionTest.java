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
package org.apache.commons.math.exception;






/**
 * Test for {@link NonMonotonousSequenceException}.
 *
 * @version $Revision$ $Date$
 */
public class NonMonotonousSequenceExceptionTest {
    @org.junit.Test
    public void testAccessors() {
        org.apache.commons.math.exception.NonMonotonousSequenceException e = 
        new org.apache.commons.math.exception.NonMonotonousSequenceException(0, (-1), 1, org.apache.commons.math.util.MathUtils.Order.Direction.DECREASING, false);
        org.junit.Assert.assertEquals(0, e.getArgument());
        org.junit.Assert.assertEquals((-1), e.getPrevious());
        org.junit.Assert.assertEquals(1, e.getIndex());
        org.junit.Assert.assertTrue(((e.getDirection()) == (org.apache.commons.math.util.MathUtils.Order.Direction.DECREASING)));
        org.junit.Assert.assertFalse(e.getStrict());

        e = new org.apache.commons.math.exception.NonMonotonousSequenceException((-1), 0, 1);
        org.junit.Assert.assertEquals((-1), e.getArgument());
        org.junit.Assert.assertEquals(0, e.getPrevious());
        org.junit.Assert.assertEquals(1, e.getIndex());
        org.junit.Assert.assertTrue(((e.getDirection()) == (org.apache.commons.math.util.MathUtils.Order.Direction.INCREASING)));
        org.junit.Assert.assertTrue(e.getStrict());
    }
}