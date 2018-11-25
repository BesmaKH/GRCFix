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















public class CholeskyDecompositionImplTest {

    private double[][] testData = new double[][]{ 
    new double[]{ 1, 2, 4, 7, 11 }, 
    new double[]{ 2, 13, 23, 38, 58 }, 
    new double[]{ 4, 23, 77, 122, 182 }, 
    new double[]{ 7, 38, 122, 294, 430 }, 
    new double[]{ 11, 58, 182, 430, 855 } };


    /**
     * test dimensions
     */     @org.junit.Test     public void testDimensions() throws org.apache.commons.math.MathException {
        org.apache.commons.math.linear.CholeskyDecomposition llt = 
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData));
        org.junit.Assert.assertEquals(testData.length, llt.getL().getRowDimension());
        org.junit.Assert.assertEquals(testData.length, llt.getL().getColumnDimension());
        org.junit.Assert.assertEquals(testData.length, llt.getLT().getRowDimension());
        org.junit.Assert.assertEquals(testData.length, llt.getLT().getColumnDimension());
    }

    /**
     * test non-square matrix
     */     @org.junit.Test(expected = org.apache.commons.math.linear.NonSquareMatrixException.class)     public void testNonSquare() throws org.apache.commons.math.MathException {
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[3][2]));
    }

    /**
     * test non-symmetric matrix
     */     @org.junit.Test(expected = org.apache.commons.math.linear.NotSymmetricMatrixException.class)     public void testNotSymmetricMatrixException() throws org.apache.commons.math.MathException {
        double[][] changed = testData.clone();
        changed[0][((changed[0].length) - 1)] += 1.0E-5;
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(changed));
    }

    /**
     * test non positive definite matrix
     */     @org.junit.Test(expected = org.apache.commons.math.linear.NotPositiveDefiniteMatrixException.class)     public void testNotPositiveDefinite() throws org.apache.commons.math.MathException {
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 14, 11, 13, 15, 24 }, 
        new double[]{ 11, 34, 13, 8, 25 }, 
        new double[]{ 13, 13, 14, 15, 21 }, 
        new double[]{ 15, 8, 15, 18, 23 }, 
        new double[]{ 24, 25, 21, 23, 45 } }));

    }

    @org.junit.Test(expected = org.apache.commons.math.linear.NotPositiveDefiniteMatrixException.class)
    public void testMath274() throws org.apache.commons.math.MathException {
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 0.40434286, -0.09376327, 0.3032898, 0.04909388 }, 
        new double[]{ -0.09376327, 0.10400408, 0.07137959, 0.04762857 }, 
        new double[]{ 0.3032898, 0.07137959, 0.30458776, 0.04882449 }, 
        new double[]{ 0.04909388, 0.04762857, 0.04882449, 0.07543265 } }));


    }

    /**
     * test A = LLT
     */     @org.junit.Test     public void testAEqualLLT() throws org.apache.commons.math.MathException {
        org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.CholeskyDecomposition llt = new org.apache.commons.math.linear.CholeskyDecompositionImpl(matrix);
        org.apache.commons.math.linear.RealMatrix l = llt.getL();
        org.apache.commons.math.linear.RealMatrix lt = llt.getLT();
        double norm = l.multiply(lt).subtract(matrix).getNorm();
        org.junit.Assert.assertEquals(0, norm, 1.0E-15);
    }

    /**
     * test that L is lower triangular
     */     @org.junit.Test     public void testLLowerTriangular() throws org.apache.commons.math.MathException {
        org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.RealMatrix l = new org.apache.commons.math.linear.CholeskyDecompositionImpl(matrix).getL();
        for (int i = 0; i < (l.getRowDimension()); i++) {
            for (int j = i + 1; j < (l.getColumnDimension()); j++) {
                org.junit.Assert.assertEquals(0.0, l.getEntry(i, j), 0.0);
            }
        }
    }

    /**
     * test that LT is transpose of L
     */     @org.junit.Test     public void testLTTransposed() throws org.apache.commons.math.MathException {
        org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.CholeskyDecomposition llt = new org.apache.commons.math.linear.CholeskyDecompositionImpl(matrix);
        org.apache.commons.math.linear.RealMatrix l = llt.getL();
        org.apache.commons.math.linear.RealMatrix lt = llt.getLT();
        double norm = l.subtract(lt.transpose()).getNorm();
        org.junit.Assert.assertEquals(0, norm, 1.0E-15);
    }

    /**
     * test matrices values
     */     @org.junit.Test     public void testMatricesValues() throws org.apache.commons.math.MathException {
        org.apache.commons.math.linear.RealMatrix lRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 1, 0, 0, 0, 0 }, 
        new double[]{ 2, 3, 0, 0, 0 }, 
        new double[]{ 4, 5, 6, 0, 0 }, 
        new double[]{ 7, 8, 9, 10, 0 }, 
        new double[]{ 11, 12, 13, 14, 15 } });

        org.apache.commons.math.linear.CholeskyDecomposition llt = 
        new org.apache.commons.math.linear.CholeskyDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData));

        // check values against known references
        org.apache.commons.math.linear.RealMatrix l = llt.getL();
        org.junit.Assert.assertEquals(0, l.subtract(lRef).getNorm(), 1.0E-13);
        org.apache.commons.math.linear.RealMatrix lt = llt.getLT();
        org.junit.Assert.assertEquals(0, lt.subtract(lRef.transpose()).getNorm(), 1.0E-13);

        // check the same cached instance is returned the second time
        org.junit.Assert.assertTrue((l == (llt.getL())));
        org.junit.Assert.assertTrue((lt == (llt.getLT())));

    }

}