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
 */
public class GeneticAlgorithmTestBinary {

    // parameters for the GA
    private static final int DIMENSION = 50;
    private static final int POPULATION_SIZE = 50;
    private static final int NUM_GENERATIONS = 50;
    private static final double ELITISM_RATE = 0.2;
    private static final double CROSSOVER_RATE = 1;
    private static final double MUTATION_RATE = 0.1;
    private static final int TOURNAMENT_ARITY = 2;

    @org.junit.Test
    public void test() {
        // to test a stochastic algorithm is hard, so this will rather be an usage example

        // initialize a new genetic algorithm
        org.apache.commons.math.genetics.GeneticAlgorithm ga = 

        // all selected chromosomes will be recombined (=crosssover)
        new org.apache.commons.math.genetics.GeneticAlgorithm(new org.apache.commons.math.genetics.OnePointCrossover<java.lang.Integer>(), org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.CROSSOVER_RATE, new org.apache.commons.math.genetics.BinaryMutation(), 
        org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.MUTATION_RATE, 
        new org.apache.commons.math.genetics.TournamentSelection(org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.TOURNAMENT_ARITY));


        org.junit.Assert.assertEquals(0, ga.getGenerationsEvolved());

        // initial population
        org.apache.commons.math.genetics.Population initial = org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.randomPopulation();
        // stopping conditions
        org.apache.commons.math.genetics.StoppingCondition stopCond = new org.apache.commons.math.genetics.FixedGenerationCount(org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.NUM_GENERATIONS);

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
        org.junit.Assert.assertEquals(org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.NUM_GENERATIONS, ga.getGenerationsEvolved());

    }




    /**
     * Initializes a random population.
     */
    private static org.apache.commons.math.genetics.ElitisticListPopulation randomPopulation() {
        java.util.List<org.apache.commons.math.genetics.Chromosome> popList = new java.util.LinkedList<org.apache.commons.math.genetics.Chromosome>();

        for (int i = 0; i < (org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.POPULATION_SIZE); i++) {
            org.apache.commons.math.genetics.BinaryChromosome randChrom = new org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.FindOnes(org.apache.commons.math.genetics.BinaryChromosome.randomBinaryRepresentation(org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.DIMENSION));
            popList.add(randChrom);
        }
        return new org.apache.commons.math.genetics.ElitisticListPopulation(popList, popList.size(), org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.ELITISM_RATE);
    }

    /**
     * Chromosomes represented by a binary chromosome.
     *
     * The goal is to set all bits (genes) to 1.
     */
    private static class FindOnes extends org.apache.commons.math.genetics.BinaryChromosome {

        public FindOnes(java.util.List<java.lang.Integer> representation) {
            super(representation);
        }

        /**
         * Returns number of elements != 0
         */
        public double fitness() {
            int num = 0;
            for (int val : this.getRepresentation()) {
                if (val != 0)
                    num++;
            }
            // number of elements >= 0
            return num;
        }

        @java.lang.Override
        public org.apache.commons.math.genetics.AbstractListChromosome<java.lang.Integer> newFixedLengthChromosome(java.util.List<java.lang.Integer> chromosomeRepresentation) {
            return new org.apache.commons.math.genetics.GeneticAlgorithmTestBinary.FindOnes(chromosomeRepresentation);
        }

    }
}