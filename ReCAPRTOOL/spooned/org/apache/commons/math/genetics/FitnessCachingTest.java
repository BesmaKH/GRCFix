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








public class FitnessCachingTest {

    // parameters for the GA
    private static final int DIMENSION = 50;
    private static final double CROSSOVER_RATE = 1;
    private static final double MUTATION_RATE = 0.1;
    private static final int TOURNAMENT_ARITY = 5;

    private static final int POPULATION_SIZE = 10;
    private static final int NUM_GENERATIONS = 50;
    private static final double ELITISM_RATE = 0.2;

    // how many times was the fitness computed
    private static int fitnessCalls = 0;


    @org.junit.Test
    public void testFitnessCaching() {
        // initialize a new genetic algorithm
        org.apache.commons.math.genetics.GeneticAlgorithm ga = 

        // all selected chromosomes will be recombined (=crosssover)

        // no mutation
        new org.apache.commons.math.genetics.GeneticAlgorithm(new org.apache.commons.math.genetics.OnePointCrossover<java.lang.Integer>(), org.apache.commons.math.genetics.FitnessCachingTest.CROSSOVER_RATE, new org.apache.commons.math.genetics.BinaryMutation(), org.apache.commons.math.genetics.FitnessCachingTest.MUTATION_RATE, new org.apache.commons.math.genetics.TournamentSelection(org.apache.commons.math.genetics.FitnessCachingTest.TOURNAMENT_ARITY));


        // initial population
        org.apache.commons.math.genetics.Population initial = org.apache.commons.math.genetics.FitnessCachingTest.randomPopulation();
        // stopping conditions
        org.apache.commons.math.genetics.StoppingCondition stopCond = new org.apache.commons.math.genetics.FixedGenerationCount(org.apache.commons.math.genetics.FitnessCachingTest.NUM_GENERATIONS);

        // run the algorithm
        ga.evolve(initial, stopCond);



        /* some chromosomes are copied */         int neededCalls = (org.apache.commons.math.genetics.FitnessCachingTest.POPULATION_SIZE)/* initial population */
         + (((org.apache.commons.math.genetics.FitnessCachingTest.NUM_GENERATIONS) - 1)/* for each population */
         * ((int) ((org.apache.commons.math.genetics.FitnessCachingTest.POPULATION_SIZE) * (1.0 - (org.apache.commons.math.genetics.FitnessCachingTest.ELITISM_RATE)))));         org.junit.Assert.assertTrue(((org.apache.commons.math.genetics.FitnessCachingTest.fitnessCalls) <= neededCalls));// some chromosomes after crossover may be the same os old ones
    }


    /**
     * Initializes a random population.
     */
    private static org.apache.commons.math.genetics.ElitisticListPopulation randomPopulation() {
        java.util.List<org.apache.commons.math.genetics.Chromosome> popList = new java.util.LinkedList<org.apache.commons.math.genetics.Chromosome>();

        for (int i = 0; i < (org.apache.commons.math.genetics.FitnessCachingTest.POPULATION_SIZE); i++) {
            org.apache.commons.math.genetics.BinaryChromosome randChrom = new org.apache.commons.math.genetics.FitnessCachingTest.DummyCountingBinaryChromosome(org.apache.commons.math.genetics.BinaryChromosome.randomBinaryRepresentation(org.apache.commons.math.genetics.FitnessCachingTest.DIMENSION));
            popList.add(randChrom);
        }
        return new org.apache.commons.math.genetics.ElitisticListPopulation(popList, popList.size(), org.apache.commons.math.genetics.FitnessCachingTest.ELITISM_RATE);
    }

    private static class DummyCountingBinaryChromosome extends org.apache.commons.math.genetics.DummyBinaryChromosome {

        public DummyCountingBinaryChromosome(java.util.List<java.lang.Integer> representation) {
            super(representation);
        }

        @java.lang.Override
        public double fitness() {
            (org.apache.commons.math.genetics.FitnessCachingTest.fitnessCalls)++;
            return 0;
        }
    }
}