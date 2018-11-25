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







public class ListPopulationTest {

    @org.junit.Test
    public void testGetFittestChromosome() {
        org.apache.commons.math.genetics.Chromosome c1 = new org.apache.commons.math.genetics.Chromosome() {
            public double fitness() {
                return 0;
            }
        };
        org.apache.commons.math.genetics.Chromosome c2 = new org.apache.commons.math.genetics.Chromosome() {
            public double fitness() {
                return 10;
            }
        };
        org.apache.commons.math.genetics.Chromosome c3 = new org.apache.commons.math.genetics.Chromosome() {
            public double fitness() {
                return 15;
            }
        };

        java.util.ArrayList<org.apache.commons.math.genetics.Chromosome> chromosomes = new java.util.ArrayList<org.apache.commons.math.genetics.Chromosome>();
        chromosomes.add(c1);
        chromosomes.add(c2);
        chromosomes.add(c3);

        org.apache.commons.math.genetics.ListPopulation population = new org.apache.commons.math.genetics.ListPopulation(chromosomes, 10) {

            public org.apache.commons.math.genetics.Population nextGeneration() {
                // not important
                return null;
            }
        };

        org.junit.Assert.assertEquals(c3, population.getFittestChromosome());
    }

}