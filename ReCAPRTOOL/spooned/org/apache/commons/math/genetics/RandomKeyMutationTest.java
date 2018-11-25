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





public class RandomKeyMutationTest {

    @org.junit.Test
    public void testMutate() {
        org.apache.commons.math.genetics.MutationPolicy mutation = new org.apache.commons.math.genetics.RandomKeyMutation();
        int l = 10;
        for (int i = 0; i < 20; i++) {
            org.apache.commons.math.genetics.DummyRandomKey origRk = new org.apache.commons.math.genetics.DummyRandomKey(org.apache.commons.math.genetics.RandomKey.randomPermutation(l));
            org.apache.commons.math.genetics.Chromosome mutated = mutation.mutate(origRk);
            org.apache.commons.math.genetics.DummyRandomKey mutatedRk = ((org.apache.commons.math.genetics.DummyRandomKey) (mutated));

            int changes = 0;
            for (int j = 0; j < (origRk.getLength()); j++) {
                if ((origRk.getRepresentation().get(j)) != (mutatedRk.getRepresentation().get(j))) {
                    changes++;
                }
            }
            org.junit.Assert.assertEquals(1, changes);
        }
    }

}