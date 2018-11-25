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
 * Implementation of a genetic algorithm. All factors that govern the operation
 * of the algorithm can be configured for a specific problem.
 *
 * @since 2.0
 * @version $Revision$ $Date$
 */
public class GeneticAlgorithm {

    /**
     * Static random number generator shared by GA implementation classes.
     * Set the randomGenerator seed to get reproducible results.
     * Use {@link #setRandomGenerator(RandomGenerator)} to supply an alternative
     * to the default JDK-provided PRNG.
     */
    // @GuardedBy("this")
    private static org.apache.commons.math.random.RandomGenerator randomGenerator = new org.apache.commons.math.random.JDKRandomGenerator();

    /**
     * the crossover policy used by the algorithm.
     */     private final org.apache.commons.math.genetics.CrossoverPolicy crossoverPolicy;
    /**
     * the rate of crossover for the algorithm.
     */     private final double crossoverRate;
    /**
     * the mutation policy used by the algorithm.
     */     private final org.apache.commons.math.genetics.MutationPolicy mutationPolicy;
    /**
     * the rate of mutation for the algorithm.
     */     private final double mutationRate;
    /**
     * the selection policy used by the algorithm.
     */     private final org.apache.commons.math.genetics.SelectionPolicy selectionPolicy;
    /**
     * the number of generations evolved to reach {@link StoppingCondition} in the last run.
     */     private int generationsEvolved = 0;
    /**
     *
     *
     * @param crossoverPolicy
     * 		The {@link CrossoverPolicy}
     * @param crossoverRate
     * 		The crossover rate as a percentage (0-1 inclusive)
     * @param mutationPolicy
     * 		The {@link MutationPolicy}
     * @param mutationRate
     * 		The mutation rate as a percentage (0-1 inclusive)
     * @param selectionPolicy
     * 		The {@link SelectionPolicy}
     */     public GeneticAlgorithm(org.apache.commons.math.genetics.CrossoverPolicy crossoverPolicy, double crossoverRate, org.apache.commons.math.genetics.MutationPolicy mutationPolicy, double mutationRate, org.apache.commons.math.genetics.SelectionPolicy selectionPolicy) {         if ((crossoverRate < 0) || (crossoverRate > 1)) {             throw new java.lang.IllegalArgumentException("crossoverRate must be between 0 and 1");}
        if ((mutationRate < 0) || (mutationRate > 1)) {
            throw new java.lang.IllegalArgumentException("mutationRate must be between 0 and 1");
        }
        this.crossoverPolicy = crossoverPolicy;
        this.crossoverRate = crossoverRate;
        this.mutationPolicy = mutationPolicy;
        this.mutationRate = mutationRate;
        this.selectionPolicy = selectionPolicy;
    }

    /**
     * Set the (static) random generator.
     *
     * @param random
     * 		random generator
     */     public static synchronized void setRandomGenerator(org.apache.commons.math.random.RandomGenerator random) {
        org.apache.commons.math.genetics.GeneticAlgorithm.randomGenerator = random;
    }

    /**
     * Returns the (static) random generator.
     *
     * @return the static random generator shared by GA implementation classes
     */
    public static synchronized org.apache.commons.math.random.RandomGenerator getRandomGenerator() {
        return org.apache.commons.math.genetics.GeneticAlgorithm.randomGenerator;
    }

    /**
     * Evolve the given population. Evolution stops when the stopping condition
     * is satisfied. Updates the {@link #getGenerationsEvolved() generationsEvolved}
     * property with the number of generations evolved before the StoppingCondition
     * is satisfied.
     *
     * @param initial
     * 		the initial, seed population.
     * @param condition
     * 		the stopping condition used to stop evolution.
     * @return the population that satisfies the stopping condition.
     */     public org.apache.commons.math.genetics.Population evolve(org.apache.commons.math.genetics.Population initial, org.apache.commons.math.genetics.StoppingCondition condition) {         org.apache.commons.math.genetics.Population current = initial;
        generationsEvolved = 0;
        while (!(condition.isSatisfied(current))) {
            current = nextGeneration(current);
            (generationsEvolved)++;
        } 
        return current;
    }

    /**
     * <p>Evolve the given population into the next generation.</p>
     * <p><ol>
     *    <li>Get nextGeneration population to fill from <code>current</code>
     *        generation, using its nextGeneration method</li>
     *    <li>Loop until new generation is filled:</li>
     *    <ul><li>Apply configured SelectionPolicy to select a pair of parents
     *            from <code>current</code></li>
     *        <li>With probability = {@link #getCrossoverRate()}, apply
     *            configured {@link CrossoverPolicy} to parents</li>
     *        <li>With probability = {@link #getMutationRate()}, apply
     *            configured {@link MutationPolicy} to each of the offspring</li>
     *        <li>Add offspring individually to nextGeneration,
     *            space permitting</li>
     *    </ul>
     *    <li>Return nextGeneration</li>
     *    </ol>
     * </p>
     *
     * @param current
     * 		the current population.
     * @return the population for the next generation.
     */     public org.apache.commons.math.genetics.Population nextGeneration(org.apache.commons.math.genetics.Population current) {
        org.apache.commons.math.genetics.Population nextGeneration = current.nextGeneration();

        org.apache.commons.math.random.RandomGenerator randGen = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator();

        while ((nextGeneration.getPopulationSize()) < (nextGeneration.getPopulationLimit())) {
            // select parent chromosomes
            org.apache.commons.math.genetics.ChromosomePair pair = getSelectionPolicy().select(current);

            // crossover?
            if ((randGen.nextDouble()) < (getCrossoverRate())) {
                // apply crossover policy to create two offspring
                pair = getCrossoverPolicy().crossover(pair.getFirst(), pair.getSecond());
            }

            // mutation?
            if ((randGen.nextDouble()) < (getMutationRate())) {
                // apply mutation policy to the chromosomes
                pair = new org.apache.commons.math.genetics.ChromosomePair(
                getMutationPolicy().mutate(pair.getFirst()), 
                getMutationPolicy().mutate(pair.getSecond()));
            }

            // add the first chromosome to the population
            nextGeneration.addChromosome(pair.getFirst());
            // is there still a place for the second chromosome?
            if ((nextGeneration.getPopulationSize()) < (nextGeneration.getPopulationLimit())) {
                // add the second chromosome to the population
                nextGeneration.addChromosome(pair.getSecond());
            }
        } 

        return nextGeneration;
    }

    /**
     * Returns the crossover policy.
     *
     * @return crossover policy
     */     public org.apache.commons.math.genetics.CrossoverPolicy getCrossoverPolicy() {
        return crossoverPolicy;
    }

    /**
     * Returns the crossover rate.
     *
     * @return crossover rate
     */     public double getCrossoverRate() {
        return crossoverRate;
    }

    /**
     * Returns the mutation policy.
     *
     * @return mutation policy
     */     public org.apache.commons.math.genetics.MutationPolicy getMutationPolicy() {
        return mutationPolicy;
    }

    /**
     * Returns the mutation rate.
     *
     * @return mutation rate
     */     public double getMutationRate() {
        return mutationRate;
    }

    /**
     * Returns the selection policy.
     *
     * @return selection policy
     */     public org.apache.commons.math.genetics.SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    /**
     * Returns the number of generations evolved to
     * reach {@link StoppingCondition} in the last run.
     *
     * @return number of generations evolved
     * @since 2.1
     */
    public int getGenerationsEvolved() {
        return generationsEvolved;
    }

}