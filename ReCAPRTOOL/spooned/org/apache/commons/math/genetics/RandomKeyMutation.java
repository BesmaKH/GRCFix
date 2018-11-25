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
 * Mutation operator for {@link RandomKey}s. Changes a randomly chosen element
 * of the array representation to a random value uniformly distributed in [0,1].
 *
 * @since 2.0
 * @version $Revision$ $Date$
 */
public class RandomKeyMutation implements org.apache.commons.math.genetics.MutationPolicy {

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException
     * 		if <code>original</code> is not a
     * 		{@link RandomKey} instance
     */     public org.apache.commons.math.genetics.Chromosome mutate(org.apache.commons.math.genetics.Chromosome original) {
        if (!(original instanceof org.apache.commons.math.genetics.RandomKey<?>)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.RANDOMKEY_MUTATION_WRONG_CLASS, 
            original.getClass().getSimpleName());
        }

        org.apache.commons.math.genetics.RandomKey<?> originalRk = ((org.apache.commons.math.genetics.RandomKey<?>) (original));
        java.util.List<java.lang.Double> repr = originalRk.getRepresentation();
        int rInd = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(repr.size());

        java.util.List<java.lang.Double> newRepr = new java.util.ArrayList<java.lang.Double>(repr);
        newRepr.set(rInd, org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextDouble());

        return originalRk.newFixedLengthChromosome(newRepr);
    }

}