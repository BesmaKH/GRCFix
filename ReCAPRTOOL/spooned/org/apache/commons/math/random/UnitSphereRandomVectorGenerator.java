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
package org.apache.commons.math.random;



/**
 * Generate random vectors isotropically located on the surface of a sphere.
 *
 * @since 2.1
 * @version $Revision$ $Date$
 */

public class UnitSphereRandomVectorGenerator implements 
org.apache.commons.math.random.RandomVectorGenerator {
    /**
     * RNG used for generating the individual components of the vectors.
     */
    private final org.apache.commons.math.random.RandomGenerator rand;
    /**
     * Space dimension.
     */
    private final int dimension;

    /**
     *
     *
     * @param dimension
     * 		Space dimension.
     * @param rand
     * 		RNG for the individual components of the vectors.
     */     public UnitSphereRandomVectorGenerator(final int dimension, final org.apache.commons.math.random.RandomGenerator rand) {         this.dimension = dimension;         this.rand = rand;
    }
    /**
     * Create an object that will use a default RNG ({@link MersenneTwister}),
     * in order to generate the individual components.
     *
     * @param dimension
     * 		Space dimension.
     */     public UnitSphereRandomVectorGenerator(final int dimension) {
        this(dimension, new org.apache.commons.math.random.MersenneTwister());
    }

    /**
     * {@inheritDoc}
     */     public double[] nextVector() {
        final double[] v = new double[dimension];

        double normSq;
        do {
            normSq = 0;
            for (int i = 0; i < (dimension); i++) {
                final double comp = (2 * (rand.nextDouble())) - 1;
                v[i] = comp;
                normSq += comp * comp;
            }
        } while (normSq > 1 );

        final double f = 1 / (java.lang.Math.sqrt(normSq));
        for (int i = 0; i < (dimension); i++) {
            v[i] *= f;
        }

        return v;

    }

}