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
package org.apache.commons.math.util;







public class BigRealFieldTest {

    @org.junit.Test
    public void testZero() {
        org.junit.Assert.assertEquals(org.apache.commons.math.util.BigReal.ZERO, org.apache.commons.math.util.BigRealField.getInstance().getZero());
    }

    @org.junit.Test
    public void testOne() {
        org.junit.Assert.assertEquals(org.apache.commons.math.util.BigReal.ONE, org.apache.commons.math.util.BigRealField.getInstance().getOne());
    }

    @org.junit.Test
    public void testSerial() {
        // deserializing the singleton should give the singleton itself back
        org.apache.commons.math.util.BigRealField field = org.apache.commons.math.util.BigRealField.getInstance();
        org.junit.Assert.assertTrue((field == (org.apache.commons.math.TestUtils.serializeAndRecover(field))));
    }

}