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
 * One point crossover policy. A random crossover point is selected and the
 * first part from each parent is copied to the corresponding child, and the
 * second parts are copied crosswise.
 *
 * Example:
 * <pre>
 * -C- denotes a crossover point
 *                   -C-                                -C-
 * p1 = (1 0 1 0 0 1  | 0 1 1)    X    p2 = (0 1 1 0 1 0  | 1 1 1)
 *         \------------/ \-----/              \------------/ \-----/
 *            ||         (*)                       ||        (**)
 *            VV         (**)                      VV        (*)
 *      /------------\ /-----\              /------------\ /-----\
 * c1 = (1 0 1 0 0 1  | 1 1 1)    X    p2 = (0 1 1 0 1 0  | 0 1 1)
 * </pre>
 *
 * This policy works only on {@link AbstractListChromosome}, and therefore it
 * is parametrized by T. Moreover, the chromosomes must have same lengths.
 *
 * @param <T>
 * 		generic type of the {@link AbstractListChromosome}s for crossover
 * @since 2.0
 * @version $Revision$ $Date$
 */
public class OnePointCrossover<T> implements org.apache.commons.math.genetics.CrossoverPolicy {

    /**
     * Performs one point crossover. A random crossover point is selected and the
     * first part from each parent is copied to the corresponding child, and the
     * second parts are copied crosswise.
     *
     * Example:
     * -C- denotes a crossover point
     *                   -C-                                -C-
     * p1 = (1 0 1 0 0 1  | 0 1 1)    X    p2 = (0 1 1 0 1 0  | 1 1 1)
     *         \------------/ \-----/              \------------/ \-----/
     *            ||         (*)                       ||        (**)
     *            VV         (**)                      VV        (*)
     *      /------------\ /-----\              /------------\ /-----\
     * c1 = (1 0 1 0 0 1  | 1 1 1)    X    p2 = (0 1 1 0 1 0  | 0 1 1)
     *
     * @param first
     * 		first parent (p1)
     * @param second
     * 		second parent (p2)
     * @return pair of two children (c1,c2)
     */     // OK because of instanceof checks     @java.lang.SuppressWarnings("unchecked")     public org.apache.commons.math.genetics.ChromosomePair crossover(org.apache.commons.math.genetics.Chromosome first, org.apache.commons.math.genetics.Chromosome second) {
        if (!((first instanceof org.apache.commons.math.genetics.AbstractListChromosome<?>) && (second instanceof org.apache.commons.math.genetics.AbstractListChromosome<?>))) {
            throw new java.lang.IllegalArgumentException("One point crossover works on FixedLengthChromosomes only.");
        }
        return crossover(((org.apache.commons.math.genetics.AbstractListChromosome<T>) (first)), ((org.apache.commons.math.genetics.AbstractListChromosome<T>) (second)));
    }


    /**
     * Helper for {@link #crossover(Chromosome, Chromosome)}. Performs the actual crossover.
     *
     * @param first
     * 		the first chromosome.
     * @param second
     * 		the second chromosome.
     * @return the pair of new chromosomes that resulted from the crossover.
     */     private org.apache.commons.math.genetics.ChromosomePair crossover(org.apache.commons.math.genetics.AbstractListChromosome<T> first, org.apache.commons.math.genetics.AbstractListChromosome<T> second) {         int length = first.getLength();
        if (length != (second.getLength()))
            throw new java.lang.IllegalArgumentException("Both chromosomes must have same lengths.");

        // array representations of the parents
        java.util.List<T> parent1Rep = first.getRepresentation();
        java.util.List<T> parent2Rep = second.getRepresentation();
        // and of the children
        java.util.ArrayList<T> child1Rep = new java.util.ArrayList<T>(first.getLength());
        java.util.ArrayList<T> child2Rep = new java.util.ArrayList<T>(second.getLength());

        // select a crossover point at random (0 and length makes no sense)
        int crossoverIndex = 1 + (org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt((length - 2)));

        // copy the first part
        for (int i = 0; i < crossoverIndex; i++) {
            child1Rep.add(parent1Rep.get(i));
            child2Rep.add(parent2Rep.get(i));
        }
        // and switch the second part
        for (int i = crossoverIndex; i < length; i++) {
            child1Rep.add(parent2Rep.get(i));
            child2Rep.add(parent1Rep.get(i));
        }

        return new org.apache.commons.math.genetics.ChromosomePair(
        first.newFixedLengthChromosome(child1Rep), 
        second.newFixedLengthChromosome(child2Rep));

    }

}