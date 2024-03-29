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








public class ChromosomeTest {

    @org.junit.Test
    public void testCompareTo() {
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
                return 10;
            }
        };

        org.junit.Assert.assertTrue(((c1.compareTo(c2)) < 0));
        org.junit.Assert.assertTrue(((c2.compareTo(c1)) > 0));
        org.junit.Assert.assertEquals(0, c3.compareTo(c2));
        org.junit.Assert.assertEquals(0, c2.compareTo(c3));
    }

    private static abstract class DummyChromosome extends org.apache.commons.math.genetics.Chromosome {
        private final int repr;

        public DummyChromosome(final int repr) {
            this.repr = repr;
        }
        @java.lang.Override
        protected boolean isSame(org.apache.commons.math.genetics.Chromosome another) {
            return (((org.apache.commons.math.genetics.ChromosomeTest.DummyChromosome) (another)).repr) == (repr);
        }
    }

    @org.junit.Test
    public void testFindSameChromosome() {
        org.apache.commons.math.genetics.Chromosome c1 = new org.apache.commons.math.genetics.ChromosomeTest.DummyChromosome(1) {
            public double fitness() {
                return 1;
            }
        };
        org.apache.commons.math.genetics.Chromosome c2 = new org.apache.commons.math.genetics.ChromosomeTest.DummyChromosome(2) {
            public double fitness() {
                return 2;
            }
        };
        org.apache.commons.math.genetics.Chromosome c3 = new org.apache.commons.math.genetics.ChromosomeTest.DummyChromosome(3) {
            public double fitness() {
                return 3;
            }
        };
        org.apache.commons.math.genetics.Chromosome c4 = new org.apache.commons.math.genetics.ChromosomeTest.DummyChromosome(1) {
            public double fitness() {
                return 5;
            }
        };
        org.apache.commons.math.genetics.Chromosome c5 = new org.apache.commons.math.genetics.ChromosomeTest.DummyChromosome(15) {
            public double fitness() {
                return 15;
            }
        };

        java.util.List<org.apache.commons.math.genetics.Chromosome> popChr = new java.util.ArrayList<org.apache.commons.math.genetics.Chromosome>();
        popChr.add(c1);
        popChr.add(c2);
        popChr.add(c3);
        org.apache.commons.math.genetics.Population pop = new org.apache.commons.math.genetics.ListPopulation(popChr, 3) {
            public org.apache.commons.math.genetics.Population nextGeneration() {
                // not important
                return null;
            }
        };

        org.junit.Assert.assertNull(c5.findSameChromosome(pop));
        org.junit.Assert.assertEquals(c1, c4.findSameChromosome(pop));

        c4.searchForFitnessUpdate(pop);
        org.junit.Assert.assertEquals(1, c4.getFitness(), 0);
    }

}