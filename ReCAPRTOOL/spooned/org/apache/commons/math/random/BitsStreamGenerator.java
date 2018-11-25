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
 * Base class for random number generators that generates bits streams.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public abstract class BitsStreamGenerator implements org.apache.commons.math.random.RandomGenerator {

    /**
     * Next gaussian.
     */     private double nextGaussian;
    /**
     * Creates a new random number generator.
     */     public BitsStreamGenerator() {
        nextGaussian = java.lang.Double.NaN;
    }

    /**
     * {@inheritDoc}
     */     public abstract void setSeed(int seed);
    /**
     * {@inheritDoc}
     */     public abstract void setSeed(int[] seed);
    /**
     * {@inheritDoc}
     */     public abstract void setSeed(long seed);
    /**
     * Generate next pseudorandom number.
     * <p>This method is the core generation algorithm. It is used by all the
     * public generation methods for the various primitive types {@link
     * #nextBoolean()}, {@link #nextBytes(byte[])}, {@link #nextDouble()},
     * {@link #nextFloat()}, {@link #nextGaussian()}, {@link #nextInt()},
     * {@link #next(int)} and {@link #nextLong()}.</p>
     *
     * @param bits
     * 		number of random bits to produce
     * @return random bits generated
     */     protected abstract int next(int bits);     /**
     * {@inheritDoc}
     */     public boolean nextBoolean() {         return (next(1)) != 0;
    }

    /**
     * {@inheritDoc}
     */     public void nextBytes(byte[] bytes) {         int i = 0;
        final int iEnd = (bytes.length) - 3;
        while (i < iEnd) {
            final int random = next(32);
            bytes[i] = ((byte) (random & 255));
            bytes[(i + 1)] = ((byte) ((random >> 8) & 255));
            bytes[(i + 2)] = ((byte) ((random >> 16) & 255));
            bytes[(i + 3)] = ((byte) ((random >> 24) & 255));
            i += 4;
        } 
        int random = next(32);
        while (i < (bytes.length)) {
            bytes[(i++)] = ((byte) (random & 255));
            random = random >> 8;
        } 
    }

    /**
     * {@inheritDoc}
     */     public double nextDouble() {         final long high = ((long) (next(26))) << 26;
        final int low = next(26);
        return (high | low) * 2.220446049250313E-16;
    }

    /**
     * {@inheritDoc}
     */     public float nextFloat() {         return (next(23)) * 1.1920929E-7F;
    }

    /**
     * {@inheritDoc}
     */     public double nextGaussian() {
        final double random;
        if (java.lang.Double.isNaN(nextGaussian)) {
            // generate a new pair of gaussian numbers
            final double x = nextDouble();
            final double y = nextDouble();
            final double alpha = (2 * (java.lang.Math.PI)) * x;
            final double r = java.lang.Math.sqrt(((-2) * (java.lang.Math.log(y))));
            random = r * (java.lang.Math.cos(alpha));
            nextGaussian = r * (java.lang.Math.sin(alpha));
        }else {
            // use the second element of the pair already generated
            random = nextGaussian;
            nextGaussian = java.lang.Double.NaN;
        }

        return random;

    }

    /**
     * {@inheritDoc}
     */     public int nextInt() {         return next(32);
    }

    /**
     * {@inheritDoc}
     */     public int nextInt(int n) throws java.lang.IllegalArgumentException {
        if (n < 1) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(n);
        }

        // find bit mask for n
        int mask = n;
        mask |= mask >> 1;
        mask |= mask >> 2;
        mask |= mask >> 4;
        mask |= mask >> 8;
        mask |= mask >> 16;

        while (true) {
            final int random = (next(32)) & mask;
            if (random < n) {
                return random;
            }
        } 

    }

    /**
     * {@inheritDoc}
     */     public long nextLong() {         final long high = ((long) (next(32))) << 32;
        final long low = ((long) (next(32))) & 4294967295L;
        return high | low;
    }

}