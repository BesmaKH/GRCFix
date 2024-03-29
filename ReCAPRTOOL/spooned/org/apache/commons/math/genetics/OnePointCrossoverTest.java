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




public class OnePointCrossoverTest {

    @org.junit.Test
    public void testCrossover() {
        java.lang.Integer[] p1 = new java.lang.Integer[]{ 1, 0, 1, 0, 0, 1, 0, 1, 1 };
        java.lang.Integer[] p2 = new java.lang.Integer[]{ 0, 1, 1, 0, 1, 0, 1, 1, 1 };

        org.apache.commons.math.genetics.BinaryChromosome p1c = new org.apache.commons.math.genetics.DummyBinaryChromosome(p1);
        org.apache.commons.math.genetics.BinaryChromosome p2c = new org.apache.commons.math.genetics.DummyBinaryChromosome(p2);

        org.apache.commons.math.genetics.OnePointCrossover<java.lang.Integer> opc = new org.apache.commons.math.genetics.OnePointCrossover<java.lang.Integer>();

        // how to test a stochastic method?
        for (int i = 0; i < 20; i++) {
            org.apache.commons.math.genetics.ChromosomePair pair = opc.crossover(p1c, p2c);

            java.lang.Integer[] c1 = new java.lang.Integer[p1.length];
            java.lang.Integer[] c2 = new java.lang.Integer[p2.length];

            c1 = ((org.apache.commons.math.genetics.BinaryChromosome) (pair.getFirst())).getRepresentation().toArray(c1);
            c2 = ((org.apache.commons.math.genetics.BinaryChromosome) (pair.getSecond())).getRepresentation().toArray(c2);

            // first and last values will be the same
            org.junit.Assert.assertEquals(((int) (p1[0])), ((int) (c1[0])));
            org.junit.Assert.assertEquals(((int) (p2[0])), ((int) (c2[0])));
            org.junit.Assert.assertEquals(((int) (p1[((p1.length) - 1)])), ((int) (c1[((c1.length) - 1)])));
            org.junit.Assert.assertEquals(((int) (p2[((p2.length) - 1)])), ((int) (c2[((c2.length) - 1)])));
            // moreover, in the above setting, the 2nd, 3rd and 7th values will be the same
            org.junit.Assert.assertEquals(((int) (p1[2])), ((int) (c1[2])));
            org.junit.Assert.assertEquals(((int) (p2[2])), ((int) (c2[2])));
            org.junit.Assert.assertEquals(((int) (p1[3])), ((int) (c1[3])));
            org.junit.Assert.assertEquals(((int) (p2[3])), ((int) (c2[3])));
            org.junit.Assert.assertEquals(((int) (p1[7])), ((int) (c1[7])));
            org.junit.Assert.assertEquals(((int) (p2[7])), ((int) (c2[7])));
        }
    }

}