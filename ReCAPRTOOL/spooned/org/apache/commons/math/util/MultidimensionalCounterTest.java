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
package org.apache.commons.math.util;








/**
 *
 */
public class MultidimensionalCounterTest {
    @org.junit.Test
    public void testPreconditions() {
        org.apache.commons.math.util.MultidimensionalCounter c;

        try {
            c = new org.apache.commons.math.util.MultidimensionalCounter(0, 1);
            org.junit.Assert.fail("NotStrictlyPositiveException expected");
        } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
            // Expected.
        }
        try {
            c = new org.apache.commons.math.util.MultidimensionalCounter(2, 0);
            org.junit.Assert.fail("NotStrictlyPositiveException expected");
        } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
            // Expected.
        }
        try {
            c = new org.apache.commons.math.util.MultidimensionalCounter((-1), 1);
            org.junit.Assert.fail("NotStrictlyPositiveException expected");
        } catch (org.apache.commons.math.exception.NotStrictlyPositiveException e) {
            // Expected.
        }

        c = new org.apache.commons.math.util.MultidimensionalCounter(2, 3);
        try {
            c.getCount(1, 1, 1);
            org.junit.Assert.fail("DimensionMismatchException expected");
        } catch (org.apache.commons.math.exception.DimensionMismatchException e) {
            // Expected.
        }
        try {
            c.getCount(3, 1);
            org.junit.Assert.fail("OutOfRangeException expected");
        } catch (org.apache.commons.math.exception.OutOfRangeException e) {
            // Expected.
        }
        try {
            c.getCount(0, (-1));
            org.junit.Assert.fail("OutOfRangeException expected");
        } catch (org.apache.commons.math.exception.OutOfRangeException e) {
            // Expected.
        }
        try {
            c.getCounts((-1));
            org.junit.Assert.fail("OutOfRangeException expected");
        } catch (org.apache.commons.math.exception.OutOfRangeException e) {
            // Expected.
        }
        try {
            c.getCounts(6);
            org.junit.Assert.fail("OutOfRangeException expected");
        } catch (org.apache.commons.math.exception.OutOfRangeException e) {
            // Expected.
        }
    }

    @org.junit.Test
    public void testIteratorPreconditions() {
        org.apache.commons.math.util.MultidimensionalCounter.Iterator iter = new org.apache.commons.math.util.MultidimensionalCounter(2, 3).iterator();
        try {
            iter.getCount((-1));
            org.junit.Assert.fail("IndexOutOfBoundsException expected");
        } catch (java.lang.IndexOutOfBoundsException e) {
            // Expected.
        }
        try {
            iter.getCount(2);
            org.junit.Assert.fail("IndexOutOfBoundsException expected");
        } catch (java.lang.IndexOutOfBoundsException e) {
            // Expected.
        }
    }

    @org.junit.Test
    public void testMulti2UniConversion() {
        final org.apache.commons.math.util.MultidimensionalCounter c = new org.apache.commons.math.util.MultidimensionalCounter(2, 4, 5);
        org.junit.Assert.assertEquals(c.getCount(1, 2, 3), 33);
    }

    @org.junit.Test
    public void testAccessors() {
        final int[] originalSize = new int[]{ 2, 6, 5 };
        final org.apache.commons.math.util.MultidimensionalCounter c = new org.apache.commons.math.util.MultidimensionalCounter(originalSize);
        final int nDim = c.getDimension();
        org.junit.Assert.assertEquals(nDim, originalSize.length);

        final int[] size = c.getSizes();
        for (int i = 0; i < nDim; i++) {
            org.junit.Assert.assertEquals(originalSize[i], size[i]);
        }
    }

    @org.junit.Test
    public void testIterationConsistency() {
        final org.apache.commons.math.util.MultidimensionalCounter c = new org.apache.commons.math.util.MultidimensionalCounter(2, 3, 2);
        final int[][] expected = new int[][]{ 
        new int[]{ 0, 0, 0 }, 
        new int[]{ 0, 0, 1 }, 
        new int[]{ 0, 1, 0 }, 
        new int[]{ 0, 1, 1 }, 
        new int[]{ 0, 2, 0 }, 
        new int[]{ 0, 2, 1 }, 
        new int[]{ 1, 0, 0 }, 
        new int[]{ 1, 0, 1 }, 
        new int[]{ 1, 1, 0 }, 
        new int[]{ 1, 1, 1 }, 
        new int[]{ 1, 2, 0 }, 
        new int[]{ 1, 2, 1 } };


        final int totalSize = c.getSize();
        final int nDim = c.getDimension();
        final org.apache.commons.math.util.MultidimensionalCounter.Iterator iter = c.iterator();
        for (int i = 0; i < totalSize; i++) {
            if (!(iter.hasNext())) {
                org.junit.Assert.fail("Too short");
            }
            final int uniDimIndex = iter.next();
            org.junit.Assert.assertEquals(("Wrong iteration at " + i), i, uniDimIndex);

            for (int dimIndex = 0; dimIndex < nDim; dimIndex++) {
                org.junit.Assert.assertEquals((((("Wrong multidimensional index for [" + i) + "][") + dimIndex) + "]"), 
                expected[i][dimIndex], iter.getCount(dimIndex));
            }

            org.junit.Assert.assertEquals((("Wrong unidimensional index for [" + i) + "]"), 
            c.getCount(expected[i]), uniDimIndex);

            final int[] indices = c.getCounts(uniDimIndex);
            for (int dimIndex = 0; dimIndex < nDim; dimIndex++) {
                org.junit.Assert.assertEquals((((("Wrong multidimensional index for [" + i) + "][") + dimIndex) + "]"), 
                expected[i][dimIndex], indices[dimIndex]);
            }
        }

        if (iter.hasNext()) {
            org.junit.Assert.fail("Too long");
        }
    }
}