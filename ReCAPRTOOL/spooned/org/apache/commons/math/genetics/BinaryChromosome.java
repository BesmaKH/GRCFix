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
 * Chromosome represented by a vector of 0s and 1s.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public abstract class BinaryChromosome extends org.apache.commons.math.genetics.AbstractListChromosome<java.lang.Integer> {

    /**
     * Constructor.
     *
     * @param representation
     * 		list of {0,1} values representing the chromosome
     */     public BinaryChromosome(java.util.List<java.lang.Integer> representation) {         super(representation);
    }

    /**
     * Constructor.
     *
     * @param representation
     * 		array of {0,1} values representing the chromosome
     */     public BinaryChromosome(java.lang.Integer[] representation) {         super(representation);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    protected void checkValidity(java.util.List<java.lang.Integer> chromosomeRepresentation) throws org.apache.commons.math.genetics.InvalidRepresentationException {
        for (int i : chromosomeRepresentation) {
            if ((i < 0) || (i > 1))
                throw new org.apache.commons.math.genetics.InvalidRepresentationException("Elements can be only 0 or 1.");
        }
    }

    /**
     * Returns a representation of a random binary array of length <code>length</code>.
     *
     * @param length
     * 		length of the array
     * @return a random binary array of length <code>length</code>
     */     public static java.util.List<java.lang.Integer> randomBinaryRepresentation(int length) {         // random binary list
        java.util.List<java.lang.Integer> rList = new java.util.ArrayList<java.lang.Integer>(length);
        for (int j = 0; j < length; j++) {
            rList.add(org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(2));
        }
        return rList;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    protected boolean isSame(org.apache.commons.math.genetics.Chromosome another) {
        // type check
        if (!(another instanceof org.apache.commons.math.genetics.BinaryChromosome))
            return false;
        org.apache.commons.math.genetics.BinaryChromosome anotherBc = ((org.apache.commons.math.genetics.BinaryChromosome) (another));
        // size check
        if ((getLength()) != (anotherBc.getLength()))
            return false;

        for (int i = 0; i < (getRepresentation().size()); i++) {
            if (!(getRepresentation().get(i).equals(anotherBc.getRepresentation().get(i))))
                return false;
        }
        // all is ok
        return true;
    }
}