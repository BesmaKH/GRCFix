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
 * Test for {@link NumberIsTooLargeException}.
 *
 * @version $Revision$ $Date$
 */
public class NumberIsTooLargeExceptionTest {
    @org.junit.Test
    public void testAccessors() {
        final org.apache.commons.math.exception.NumberIsTooLargeException e = new org.apache.commons.math.exception.NumberIsTooLargeException(1, 0, true);
        org.junit.Assert.assertEquals(1, e.getArgument());
        org.junit.Assert.assertEquals(0, e.getMax());
        org.junit.Assert.assertTrue(e.getBoundIsAllowed());
    }
}