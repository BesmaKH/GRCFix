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





public class ElitisticListPopulationTest {

    private static int counter = 0;

    @org.junit.Test
    public void testNextGeneration() {
        org.apache.commons.math.genetics.ElitisticListPopulation pop = new org.apache.commons.math.genetics.ElitisticListPopulation(100, 0.203);

        for (int i = 0; i < (pop.getPopulationLimit()); i++) {
            pop.addChromosome(new org.apache.commons.math.genetics.ElitisticListPopulationTest.DummyChromosome());
        }

        org.apache.commons.math.genetics.Population nextGeneration = pop.nextGeneration();

        org.junit.Assert.assertEquals(20, nextGeneration.getPopulationSize());
    }

    private static class DummyChromosome extends org.apache.commons.math.genetics.Chromosome {
        private final int fitness;

        public DummyChromosome() {
            this.fitness = org.apache.commons.math.genetics.ElitisticListPopulationTest.counter;
            (org.apache.commons.math.genetics.ElitisticListPopulationTest.counter)++;
        }

        public double fitness() {
            return this.fitness;
        }
    }

}