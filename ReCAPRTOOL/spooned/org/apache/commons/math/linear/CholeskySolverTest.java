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
package org.apache.commons.math.linear;






public class CholeskySolverTest extends junit.framework.TestCase {

    private double[][] testData = new double[][]{ 
    new double[]{ 1, 2, 4, 7, 11 }, 
    new double[]{ 2, 13, 23, 38, 58 }, 
    new double[]{ 4, 23, 77, 122, 182 }, 
    new double[]{ 7, 38, 122, 294, 430 }, 
    new double[]{ 11, 58, 182, 430, 855 } };


    public CholeskySolverTest(java.lang.String name) {
        super(name);
    }

    /**
     * test solve dimension errors
     */     public void testSolveDimensionErrors() throws org.apache.commons.math.MathException {         org.apache.commons.math.linear.DecompositionSolver solver = 
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData)).getSolver();
        org.apache.commons.math.linear.RealMatrix b = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[2][2]);
        try {
            solver.solve(b);
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalArgumentException iae) {
            // expected behavior
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
        try {
            solver.solve(b.getColumn(0));
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalArgumentException iae) {
            // expected behavior
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
        try {
            solver.solve(new org.apache.commons.math.linear.ArrayRealVectorTest.RealVectorTestImpl(b.getColumn(0)));
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (java.lang.IllegalArgumentException iae) {
            // expected behavior
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    /**
     * test solve
     */     public void testSolve() throws org.apache.commons.math.MathException {         org.apache.commons.math.linear.DecompositionSolver solver = 
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData)).getSolver();
        org.apache.commons.math.linear.RealMatrix b = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 78, -13, 1 }, 
        new double[]{ 414, -62, -1 }, 
        new double[]{ 1312, -202, -37 }, 
        new double[]{ 2989, -542, 145 }, 
        new double[]{ 5510, -1465, 201 } });

        org.apache.commons.math.linear.RealMatrix xRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 1, 0, 1 }, 
        new double[]{ 0, 1, 1 }, 
        new double[]{ 2, 1, -4 }, 
        new double[]{ 2, 2, 2 }, 
        new double[]{ 5, -3, 0 } });


        // using RealMatrix
        junit.framework.Assert.assertEquals(0, solver.solve(b).subtract(xRef).getNorm(), 1.0E-13);

        // using double[]
        for (int i = 0; i < (b.getColumnDimension()); ++i) {
            junit.framework.Assert.assertEquals(0, 
            new org.apache.commons.math.linear.ArrayRealVector(solver.solve(b.getColumn(i))).subtract(xRef.getColumnVector(i)).getNorm(), 
            1.0E-13);
        }

        // using ArrayRealVector
        for (int i = 0; i < (b.getColumnDimension()); ++i) {
            junit.framework.Assert.assertEquals(0, 
            solver.solve(b.getColumnVector(i)).subtract(xRef.getColumnVector(i)).getNorm(), 
            1.0E-13);
        }

        // using RealVector with an alternate implementation
        for (int i = 0; i < (b.getColumnDimension()); ++i) {
            org.apache.commons.math.linear.ArrayRealVectorTest.RealVectorTestImpl v = 
            new org.apache.commons.math.linear.ArrayRealVectorTest.RealVectorTestImpl(b.getColumn(i));
            junit.framework.Assert.assertEquals(0, 
            solver.solve(v).subtract(xRef.getColumnVector(i)).getNorm(), 
            1.0E-13);
        }

    }

    /**
     * test determinant
     */     public void testDeterminant() throws org.apache.commons.math.MathException {         junit.framework.Assert.assertEquals(7290000.0, getDeterminant(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData)), 1.0E-15);
    }

    private double getDeterminant(org.apache.commons.math.linear.RealMatrix m) throws org.apache.commons.math.MathException {
        return new org.apache.commons.math.linear.CholeskyDecompositionImpl(m).getDeterminant();
    }

}