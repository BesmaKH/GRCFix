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
package org.apache.commons.math.random;



/**
 * Test cases for the RandomAdaptor class
 *
 * @version $Revision$ $Date$
 */

public class RandomAdaptorTest extends org.apache.commons.math.random.RandomDataTest {

    public RandomAdaptorTest(java.lang.String name) {
        super(name);
    }

    public void testAdaptor() {
        org.apache.commons.math.random.RandomAdaptorTest.ConstantGenerator generator = new org.apache.commons.math.random.RandomAdaptorTest.ConstantGenerator();
        java.util.Random random = org.apache.commons.math.random.RandomAdaptor.createAdaptor(generator);
        checkConstant(random);
        org.apache.commons.math.random.RandomAdaptor randomAdaptor = new org.apache.commons.math.random.RandomAdaptor(generator);
        checkConstant(randomAdaptor);
    }

    private void checkConstant(java.util.Random random) {
        byte[] bytes = new byte[]{ 0 };
        random.nextBytes(bytes);
        junit.framework.Assert.assertEquals(0, bytes[0]);
        junit.framework.Assert.assertEquals(false, random.nextBoolean());
        junit.framework.Assert.assertEquals(0, random.nextDouble(), 0);
        junit.framework.Assert.assertEquals(0, random.nextFloat(), 0);
        junit.framework.Assert.assertEquals(0, random.nextGaussian(), 0);
        junit.framework.Assert.assertEquals(0, random.nextInt());
        junit.framework.Assert.assertEquals(0, random.nextInt(1));
        junit.framework.Assert.assertEquals(0, random.nextLong());
        random.setSeed(100);
        junit.framework.Assert.assertEquals(0, random.nextDouble(), 0);
    }

    /* "Constant" generator to test Adaptor delegation.
    "Powered by Eclipse ;-)"
     */


    private static class ConstantGenerator implements org.apache.commons.math.random.RandomGenerator {

        public boolean nextBoolean() {
            return false;
        }

        public void nextBytes(byte[] bytes) {
        }

        public double nextDouble() {
            return 0;
        }

        public float nextFloat() {
            return 0;
        }

        public double nextGaussian() {
            return 0;
        }

        public int nextInt() {
            return 0;
        }

        public int nextInt(int n) {
            return 0;
        }

        public long nextLong() {
            return 0;
        }

        public void setSeed(int seed) {
        }

        public void setSeed(int[] seed) {
        }

        public void setSeed(long seed) {
        }

    }
}