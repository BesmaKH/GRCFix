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
package org.apache.commons.math.genetics;







public class BinaryChromosomeTest {

    @org.junit.Test
    public void testInvalidConstructor() {
        java.lang.Integer[][] reprs = new java.lang.Integer[][]{ 
        new java.lang.Integer[]{ 0, 1, 0, 1, 2 }, 
        new java.lang.Integer[]{ 0, 1, 0, 1, -1 } };


        for (java.lang.Integer[] repr : reprs) {
            try {
                new org.apache.commons.math.genetics.DummyBinaryChromosome(repr);
                org.junit.Assert.fail("Exception not caught");
            } catch (java.lang.IllegalArgumentException e) {

            }
        }
    }

    @org.junit.Test
    public void testRandomConstructor() {
        for (int i = 0; i < 20; i++) {
            new org.apache.commons.math.genetics.DummyBinaryChromosome(org.apache.commons.math.genetics.BinaryChromosome.randomBinaryRepresentation(10));
        }
    }

    @org.junit.Test
    public void testIsSame() {
        org.apache.commons.math.genetics.Chromosome c1 = new org.apache.commons.math.genetics.DummyBinaryChromosome(new java.lang.Integer[]{ 0, 1, 0, 1, 0, 1 });
        org.apache.commons.math.genetics.Chromosome c2 = new org.apache.commons.math.genetics.DummyBinaryChromosome(new java.lang.Integer[]{ 0, 1, 1, 0, 1 });
        org.apache.commons.math.genetics.Chromosome c3 = new org.apache.commons.math.genetics.DummyBinaryChromosome(new java.lang.Integer[]{ 0, 1, 0, 1, 0, 1, 1 });
        org.apache.commons.math.genetics.Chromosome c4 = new org.apache.commons.math.genetics.DummyBinaryChromosome(new java.lang.Integer[]{ 1, 1, 0, 1, 0, 1 });
        org.apache.commons.math.genetics.Chromosome c5 = new org.apache.commons.math.genetics.DummyBinaryChromosome(new java.lang.Integer[]{ 0, 1, 0, 1, 0, 0 });
        org.apache.commons.math.genetics.Chromosome c6 = new org.apache.commons.math.genetics.DummyBinaryChromosome(new java.lang.Integer[]{ 0, 1, 0, 1, 0, 1 });

        org.junit.Assert.assertFalse(c1.isSame(c2));
        org.junit.Assert.assertFalse(c1.isSame(c3));
        org.junit.Assert.assertFalse(c1.isSame(c4));
        org.junit.Assert.assertFalse(c1.isSame(c5));
        org.junit.Assert.assertTrue(c1.isSame(c6));
    }

}