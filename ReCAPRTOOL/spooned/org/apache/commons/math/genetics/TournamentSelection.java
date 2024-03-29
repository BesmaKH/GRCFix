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
 * Tournament selection scheme. Each of the two selected chromosomes is selected
 * based on n-ary tournament -- this is done by drawing {@link #arity} random
 * chromosomes without replacement from the population, and then selecting the
 * fittest chromosome among them.
 *
 * @since 2.0
 * @version $Revision$ $Date$
 */
public class TournamentSelection implements org.apache.commons.math.genetics.SelectionPolicy {

    /**
     * number of chromosomes included in the tournament selections
     */     private int arity;
    /**
     * Creates a new TournamentSelection instance.
     *
     * @param arity
     * 		how many chromosomes will be drawn to the tournament
     */
    public TournamentSelection(int arity) {
        this.arity = arity;
    }

    /**
     * Select two chromosomes from the population. Each of the two selected
     * chromosomes is selected based on n-ary tournament -- this is done by
     * drawing {@link #arity} random chromosomes without replacement from the
     * population, and then selecting the fittest chromosome among them.
     *
     * @param population
     * 		the population from which the chromosomes are choosen.
     * @return the selected chromosomes.
     */
    public org.apache.commons.math.genetics.ChromosomePair select(org.apache.commons.math.genetics.Population population) {
        return new org.apache.commons.math.genetics.ChromosomePair(
        tournament(((org.apache.commons.math.genetics.ListPopulation) (population))), 
        tournament(((org.apache.commons.math.genetics.ListPopulation) (population))));

    }

    /**
     * Helper for {@link #select(Population)}. Draw {@link #arity} random
     * chromosomes without replacement from the population, and then select the
     * fittest chromosome among them.
     *
     * @param population
     * 		the population from which the chromosomes are choosen.
     * @return the selected chromosome.
     */
    private org.apache.commons.math.genetics.Chromosome tournament(org.apache.commons.math.genetics.ListPopulation population) {
        if ((population.getPopulationSize()) < (this.arity))
            throw new java.lang.IllegalArgumentException("Tournament arity cannot be bigger than population size.");
        // auxiliary population
        org.apache.commons.math.genetics.ListPopulation tournamentPopulation = new org.apache.commons.math.genetics.ListPopulation(this.arity) {
            public org.apache.commons.math.genetics.Population nextGeneration() {
                // not useful here
                return null;
            }
        };

        // create a copy of the chromosome list
        java.util.List<org.apache.commons.math.genetics.Chromosome> chromosomes = new java.util.ArrayList<org.apache.commons.math.genetics.Chromosome>(population.getChromosomes());
        for (int i = 0; i < (this.arity); i++) {
            // select a random individual and add it to the tournament
            int rind = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(chromosomes.size());
            tournamentPopulation.addChromosome(chromosomes.get(rind));
            // do not select it again
            chromosomes.remove(rind);
        }
        // the winner takes it all
        return tournamentPopulation.getFittestChromosome();
    }

    /**
     * Gets the arity (number of chromosomes drawn to the tournament).
     *
     * @return arity of the tournament
     */
    public int getArity() {
        return arity;
    }

    /**
     * Sets the arity (number of chromosomes drawn to the tournament).
     *
     * @param arity
     * 		arity of the tournament
     */     public void setArity(int arity) {
        this.arity = arity;
    }

}