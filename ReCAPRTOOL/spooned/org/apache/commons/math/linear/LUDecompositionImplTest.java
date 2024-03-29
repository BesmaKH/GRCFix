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




public class LUDecompositionImplTest extends junit.framework.TestCase {
    private double[][] testData = new double[][]{ 
    new double[]{ 1.0, 2.0, 3.0 }, 
    new double[]{ 2.0, 5.0, 3.0 }, 
    new double[]{ 1.0, 0.0, 8.0 } };

    private double[][] testDataMinus = new double[][]{ 
    new double[]{ -1.0, -2.0, -3.0 }, 
    new double[]{ -2.0, -5.0, -3.0 }, 
    new double[]{ -1.0, 0.0, -8.0 } };

    private double[][] luData = new double[][]{ 
    new double[]{ 2.0, 3.0, 3.0 }, 
    new double[]{ 0.0, 5.0, 7.0 }, 
    new double[]{ 6.0, 9.0, 8.0 } };


    // singular matrices
    private double[][] singular = new double[][]{ 
    new double[]{ 2.0, 3.0 }, 
    new double[]{ 2.0, 3.0 } };

    private double[][] bigSingular = new double[][]{ 
    new double[]{ 1.0, 2.0, 3.0, 4.0 }, 
    new double[]{ 2.0, 5.0, 3.0, 4.0 }, 
    new double[]{ 7.0, 3.0, 256.0, 1930.0 }, 
    new double[]{ 3.0, 7.0, 6.0, 8.0 } };
    // 4th row = 1st + 2nd

    private static final double entryTolerance = 1.0E-15;

    private static final double normTolerance = 1.0E-13;

    public LUDecompositionImplTest(java.lang.String name) {
        super(name);
    }

    /**
     * test dimensions
     */     public void testDimensions() {         org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.LUDecomposition LU = new org.apache.commons.math.linear.LUDecompositionImpl(matrix);
        junit.framework.Assert.assertEquals(testData.length, LU.getL().getRowDimension());
        junit.framework.Assert.assertEquals(testData.length, LU.getL().getColumnDimension());
        junit.framework.Assert.assertEquals(testData.length, LU.getU().getRowDimension());
        junit.framework.Assert.assertEquals(testData.length, LU.getU().getColumnDimension());
        junit.framework.Assert.assertEquals(testData.length, LU.getP().getRowDimension());
        junit.framework.Assert.assertEquals(testData.length, LU.getP().getColumnDimension());

    }

    /**
     * test non-square matrix
     */     public void testNonSquare() {         try {
            new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[3][2]));
        } catch (org.apache.commons.math.linear.InvalidMatrixException ime) {
            // expected behavior
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail("wrong exception caught");
        }
    }

    /**
     * test PA = LU
     */     public void testPAEqualLU() {         org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.LUDecomposition lu = new org.apache.commons.math.linear.LUDecompositionImpl(matrix);
        org.apache.commons.math.linear.RealMatrix l = lu.getL();
        org.apache.commons.math.linear.RealMatrix u = lu.getU();
        org.apache.commons.math.linear.RealMatrix p = lu.getP();
        double norm = l.multiply(u).subtract(p.multiply(matrix)).getNorm();
        junit.framework.Assert.assertEquals(0, norm, org.apache.commons.math.linear.LUDecompositionImplTest.normTolerance);

        matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testDataMinus);
        lu = new org.apache.commons.math.linear.LUDecompositionImpl(matrix);
        l = lu.getL();
        u = lu.getU();
        p = lu.getP();
        norm = l.multiply(u).subtract(p.multiply(matrix)).getNorm();
        junit.framework.Assert.assertEquals(0, norm, org.apache.commons.math.linear.LUDecompositionImplTest.normTolerance);

        matrix = org.apache.commons.math.linear.MatrixUtils.createRealIdentityMatrix(17);
        lu = new org.apache.commons.math.linear.LUDecompositionImpl(matrix);
        l = lu.getL();
        u = lu.getU();
        p = lu.getP();
        norm = l.multiply(u).subtract(p.multiply(matrix)).getNorm();
        junit.framework.Assert.assertEquals(0, norm, org.apache.commons.math.linear.LUDecompositionImplTest.normTolerance);

        matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(singular);
        lu = new org.apache.commons.math.linear.LUDecompositionImpl(matrix);
        junit.framework.Assert.assertFalse(lu.getSolver().isNonSingular());
        junit.framework.Assert.assertNull(lu.getL());
        junit.framework.Assert.assertNull(lu.getU());
        junit.framework.Assert.assertNull(lu.getP());

        matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(bigSingular);
        lu = new org.apache.commons.math.linear.LUDecompositionImpl(matrix);
        junit.framework.Assert.assertFalse(lu.getSolver().isNonSingular());
        junit.framework.Assert.assertNull(lu.getL());
        junit.framework.Assert.assertNull(lu.getU());
        junit.framework.Assert.assertNull(lu.getP());

    }

    /**
     * test that L is lower triangular with unit diagonal
     */     public void testLLowerTriangular() {         org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.RealMatrix l = new org.apache.commons.math.linear.LUDecompositionImpl(matrix).getL();
        for (int i = 0; i < (l.getRowDimension()); i++) {
            junit.framework.Assert.assertEquals(l.getEntry(i, i), 1, org.apache.commons.math.linear.LUDecompositionImplTest.entryTolerance);
            for (int j = i + 1; j < (l.getColumnDimension()); j++) {
                junit.framework.Assert.assertEquals(l.getEntry(i, j), 0, org.apache.commons.math.linear.LUDecompositionImplTest.entryTolerance);
            }
        }
    }

    /**
     * test that U is upper triangular
     */     public void testUUpperTriangular() {         org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.RealMatrix u = new org.apache.commons.math.linear.LUDecompositionImpl(matrix).getU();
        for (int i = 0; i < (u.getRowDimension()); i++) {
            for (int j = 0; j < i; j++) {
                junit.framework.Assert.assertEquals(u.getEntry(i, j), 0, org.apache.commons.math.linear.LUDecompositionImplTest.entryTolerance);
            }
        }
    }

    /**
     * test that P is a permutation matrix
     */     public void testPPermutation() {         org.apache.commons.math.linear.RealMatrix matrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData);
        org.apache.commons.math.linear.RealMatrix p = new org.apache.commons.math.linear.LUDecompositionImpl(matrix).getP();

        org.apache.commons.math.linear.RealMatrix ppT = p.multiply(p.transpose());
        org.apache.commons.math.linear.RealMatrix id = org.apache.commons.math.linear.MatrixUtils.createRealIdentityMatrix(p.getRowDimension());
        junit.framework.Assert.assertEquals(0, ppT.subtract(id).getNorm(), org.apache.commons.math.linear.LUDecompositionImplTest.normTolerance);

        for (int i = 0; i < (p.getRowDimension()); i++) {
            int zeroCount = 0;
            int oneCount = 0;
            int otherCount = 0;
            for (int j = 0; j < (p.getColumnDimension()); j++) {
                final double e = p.getEntry(i, j);
                if (e == 0) {
                    ++zeroCount;
                }else                     if (e == 1) {
                        ++oneCount;
                    }else {
                        ++otherCount;
                    }
            }
            junit.framework.Assert.assertEquals(((p.getColumnDimension()) - 1), zeroCount);
            junit.framework.Assert.assertEquals(1, oneCount);
            junit.framework.Assert.assertEquals(0, otherCount);
        }

        for (int j = 0; j < (p.getColumnDimension()); j++) {
            int zeroCount = 0;
            int oneCount = 0;
            int otherCount = 0;
            for (int i = 0; i < (p.getRowDimension()); i++) {
                final double e = p.getEntry(i, j);
                if (e == 0) {
                    ++zeroCount;
                }else                     if (e == 1) {
                        ++oneCount;
                    }else {
                        ++otherCount;
                    }
            }
            junit.framework.Assert.assertEquals(((p.getRowDimension()) - 1), zeroCount);
            junit.framework.Assert.assertEquals(1, oneCount);
            junit.framework.Assert.assertEquals(0, otherCount);
        }

    }


    /**
     * test singular
     */     public void testSingular() {         org.apache.commons.math.linear.LUDecomposition lu = 
        new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData));
        junit.framework.Assert.assertTrue(lu.getSolver().isNonSingular());
        lu = new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(singular));
        junit.framework.Assert.assertFalse(lu.getSolver().isNonSingular());
        lu = new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(bigSingular));
        junit.framework.Assert.assertFalse(lu.getSolver().isNonSingular());
    }

    /**
     * test matrices values
     */     public void testMatricesValues1() {         org.apache.commons.math.linear.LUDecomposition lu = 
        new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(testData));
        org.apache.commons.math.linear.RealMatrix lRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 1.0, 0.0, 0.0 }, 
        new double[]{ 0.5, 1.0, 0.0 }, 
        new double[]{ 0.5, 0.2, 1.0 } });

        org.apache.commons.math.linear.RealMatrix uRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 2.0, 5.0, 3.0 }, 
        new double[]{ 0.0, -2.5, 6.5 }, 
        new double[]{ 0.0, 0.0, 0.2 } });

        org.apache.commons.math.linear.RealMatrix pRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 0.0, 1.0, 0.0 }, 
        new double[]{ 0.0, 0.0, 1.0 }, 
        new double[]{ 1.0, 0.0, 0.0 } });

        int[] pivotRef = new int[]{ 1, 2, 0 };

        // check values against known references
        org.apache.commons.math.linear.RealMatrix l = lu.getL();
        junit.framework.Assert.assertEquals(0, l.subtract(lRef).getNorm(), 1.0E-13);
        org.apache.commons.math.linear.RealMatrix u = lu.getU();
        junit.framework.Assert.assertEquals(0, u.subtract(uRef).getNorm(), 1.0E-13);
        org.apache.commons.math.linear.RealMatrix p = lu.getP();
        junit.framework.Assert.assertEquals(0, p.subtract(pRef).getNorm(), 1.0E-13);
        int[] pivot = lu.getPivot();
        for (int i = 0; i < (pivotRef.length); ++i) {
            junit.framework.Assert.assertEquals(pivotRef[i], pivot[i]);
        }

        // check the same cached instance is returned the second time
        junit.framework.Assert.assertTrue((l == (lu.getL())));
        junit.framework.Assert.assertTrue((u == (lu.getU())));
        junit.framework.Assert.assertTrue((p == (lu.getP())));

    }

    /**
     * test matrices values
     */     public void testMatricesValues2() {         org.apache.commons.math.linear.LUDecomposition lu = 
        new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(luData));
        org.apache.commons.math.linear.RealMatrix lRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 1.0, 0.0, 0.0 }, 
        new double[]{ 0.0, 1.0, 0.0 }, 
        new double[]{ 1.0 / 3.0, 0.0, 1.0 } });

        org.apache.commons.math.linear.RealMatrix uRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 6.0, 9.0, 8.0 }, 
        new double[]{ 0.0, 5.0, 7.0 }, 
        new double[]{ 0.0, 0.0, 1.0 / 3.0 } });

        org.apache.commons.math.linear.RealMatrix pRef = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(new double[][]{ 
        new double[]{ 0.0, 0.0, 1.0 }, 
        new double[]{ 0.0, 1.0, 0.0 }, 
        new double[]{ 1.0, 0.0, 0.0 } });

        int[] pivotRef = new int[]{ 2, 1, 0 };

        // check values against known references
        org.apache.commons.math.linear.RealMatrix l = lu.getL();
        junit.framework.Assert.assertEquals(0, l.subtract(lRef).getNorm(), 1.0E-13);
        org.apache.commons.math.linear.RealMatrix u = lu.getU();
        junit.framework.Assert.assertEquals(0, u.subtract(uRef).getNorm(), 1.0E-13);
        org.apache.commons.math.linear.RealMatrix p = lu.getP();
        junit.framework.Assert.assertEquals(0, p.subtract(pRef).getNorm(), 1.0E-13);
        int[] pivot = lu.getPivot();
        for (int i = 0; i < (pivotRef.length); ++i) {
            junit.framework.Assert.assertEquals(pivotRef[i], pivot[i]);
        }

        // check the same cached instance is returned the second time
        junit.framework.Assert.assertTrue((l == (lu.getL())));
        junit.framework.Assert.assertTrue((u == (lu.getU())));
        junit.framework.Assert.assertTrue((p == (lu.getP())));

    }

}