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
 * Mutation for {@link BinaryChromosome}s. Randomly changes one gene.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public class BinaryMutation implements org.apache.commons.math.genetics.MutationPolicy {

    /**
     * Mutate the given chromosome. Randomly changes one gene.
     *
     * @param original
     * 		the original chromosome.
     * @return the mutated chromomsome.
     */     public org.apache.commons.math.genetics.Chromosome mutate(org.apache.commons.math.genetics.Chromosome original) {         if (!(original instanceof org.apache.commons.math.genetics.BinaryChromosome)) {
            throw new java.lang.IllegalArgumentException("Binary mutation works on BinaryChromosome only.");
        }

        org.apache.commons.math.genetics.BinaryChromosome origChrom = ((org.apache.commons.math.genetics.BinaryChromosome) (original));
        java.util.List<java.lang.Integer> newRepr = new java.util.ArrayList<java.lang.Integer>(origChrom.getRepresentation());

        // randomly select a gene
        int geneIndex = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(origChrom.getLength());
        // and change it
        newRepr.set(geneIndex, ((origChrom.getRepresentation().get(geneIndex)) == 0 ? 1 : 0));

        org.apache.commons.math.genetics.Chromosome newChrom = origChrom.newFixedLengthChromosome(newRepr);
        return newChrom;
    }

}