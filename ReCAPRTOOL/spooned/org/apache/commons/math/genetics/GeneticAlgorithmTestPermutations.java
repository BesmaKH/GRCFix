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








/**
 * This is also an example of usage.
 *
 * This algorithm does "stochastic sorting" of a sequence 0,...,N.
 */

public class GeneticAlgorithmTestPermutations {

    // parameters for the GA
    private static final int DIMENSION = 20;
    private static final int POPULATION_SIZE = 80;
    private static final int NUM_GENERATIONS = 200;
    private static final double ELITISM_RATE = 0.2;
    private static final double CROSSOVER_RATE = 1;
    private static final double MUTATION_RATE = 0.08;
    private static final int TOURNAMENT_ARITY = 2;

    // numbers from 0 to N-1
    private static final java.util.List<java.lang.Integer> sequence = new java.util.ArrayList<java.lang.Integer>();
    static {
        for (int i = 0; i < (org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.DIMENSION); i++) {
            org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.sequence.add(i);
        }
    }

    @org.junit.Test
    public void test() {
        // to test a stochastic algorithm is hard, so this will rather be an usage example

        // initialize a new genetic algorithm
        org.apache.commons.math.genetics.GeneticAlgorithm ga = new org.apache.commons.math.genetics.GeneticAlgorithm(
        new org.apache.commons.math.genetics.OnePointCrossover<java.lang.Integer>(), 
        org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.CROSSOVER_RATE, 
        new org.apache.commons.math.genetics.RandomKeyMutation(), 
        org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.MUTATION_RATE, 
        new org.apache.commons.math.genetics.TournamentSelection(org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.TOURNAMENT_ARITY));


        // initial population
        org.apache.commons.math.genetics.Population initial = org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.randomPopulation();
        // stopping conditions
        org.apache.commons.math.genetics.StoppingCondition stopCond = new org.apache.commons.math.genetics.FixedGenerationCount(org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.NUM_GENERATIONS);

        // best initial chromosome
        org.apache.commons.math.genetics.Chromosome bestInitial = initial.getFittestChromosome();

        // run the algorithm
        org.apache.commons.math.genetics.Population finalPopulation = ga.evolve(initial, stopCond);

        // best chromosome from the final population
        org.apache.commons.math.genetics.Chromosome bestFinal = finalPopulation.getFittestChromosome();

        // the only thing we can test is whether the final solution is not worse than the initial one
        // however, for some implementations of GA, this need not be true :)
        org.junit.Assert.
        assertTrue(((bestFinal.compareTo(bestInitial)) > 0));

        // System.out.println(bestInitial);
        // System.out.println(bestFinal);
    }


    /**
     * Initializes a random population
     */
    private static org.apache.commons.math.genetics.ElitisticListPopulation randomPopulation() {
        java.util.List<org.apache.commons.math.genetics.Chromosome> popList = new java.util.ArrayList<org.apache.commons.math.genetics.Chromosome>();
        for (int i = 0; i < (org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.POPULATION_SIZE); i++) {
            org.apache.commons.math.genetics.Chromosome randChrom = new org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.MinPermutations(org.apache.commons.math.genetics.RandomKey.randomPermutation(org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.DIMENSION));
            popList.add(randChrom);
        }
        return new org.apache.commons.math.genetics.ElitisticListPopulation(popList, popList.size(), org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.ELITISM_RATE);
    }

    /**
     * Chromosomes representing a permutation of (0,1,2,...,DIMENSION-1).
     *
     * The goal is to sort the sequence.
     */
    private static class MinPermutations extends org.apache.commons.math.genetics.RandomKey<java.lang.Integer> {

        public MinPermutations(java.util.List<java.lang.Double> representation) {
            super(representation);
        }

        public double fitness() {
            int res = 0;
            java.util.List<java.lang.Integer> decoded = decode(org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.sequence);
            for (int i = 0; i < (decoded.size()); i++) {
                int value = decoded.get(i);
                if (value != i) {
                    // bad position found
                    res += java.lang.Math.abs((value - i));
                }
            }
            // the most fitted chromosome is the one with minimal error
            // therefore we must return negative value
            return -res;
        }

        @java.lang.Override
        public org.apache.commons.math.genetics.AbstractListChromosome<java.lang.Double> newFixedLengthChromosome(java.util.List<java.lang.Double> chromosomeRepresentation) {
            return new org.apache.commons.math.genetics.GeneticAlgorithmTestPermutations.MinPermutations(chromosomeRepresentation);
        }
    }
}