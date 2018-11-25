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







/**
 * Test cases for the {@link Array2DRowFieldMatrix} class.
 *
 * @version $Revision$ $Date$
 */

public final class FieldMatrixImplTest extends junit.framework.TestCase {

    // 3 x 3 identity matrix
    protected org.apache.commons.math.fraction.Fraction[][] id = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(0) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(0) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(1) } };

    // Test data for group operations
    protected org.apache.commons.math.fraction.Fraction[][] testData = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(8) } };
    protected org.apache.commons.math.fraction.Fraction[][] testDataLU = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1, 2), new org.apache.commons.math.fraction.Fraction((-5), 2), new org.apache.commons.math.fraction.Fraction(13, 2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1, 2), new org.apache.commons.math.fraction.Fraction(1, 5), new org.apache.commons.math.fraction.Fraction(1, 5) } };
    protected org.apache.commons.math.fraction.Fraction[][] testDataPlus2 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(5) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(5) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(10) } };
    protected org.apache.commons.math.fraction.Fraction[][] testDataMinus = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction((-1)), new org.apache.commons.math.fraction.Fraction((-2)), new org.apache.commons.math.fraction.Fraction((-3)) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction((-2)), new org.apache.commons.math.fraction.Fraction((-5)), new org.apache.commons.math.fraction.Fraction((-3)) }, 
    new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction((-1)), new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction((-8)) } };
    protected org.apache.commons.math.fraction.Fraction[] testDataRow1 = new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) };
    protected org.apache.commons.math.fraction.Fraction[] testDataCol3 = new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(8) };
    protected org.apache.commons.math.fraction.Fraction[][] testDataInv = 
    new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction((-40)), new org.apache.commons.math.fraction.Fraction(16), new org.apache.commons.math.fraction.Fraction(9) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(13), new org.apache.commons.math.fraction.Fraction((-5)), new org.apache.commons.math.fraction.Fraction((-3)) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction((-2)), new org.apache.commons.math.fraction.Fraction((-1)) } };
    protected org.apache.commons.math.fraction.Fraction[] preMultTest = new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(8), new org.apache.commons.math.fraction.Fraction(12), new org.apache.commons.math.fraction.Fraction(33) };
    protected org.apache.commons.math.fraction.Fraction[][] testData2 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(3) } };
    protected org.apache.commons.math.fraction.Fraction[][] testData2T = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(3) } };
    protected org.apache.commons.math.fraction.Fraction[][] testDataPlusInv = 
    new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction((-39)), new org.apache.commons.math.fraction.Fraction(18), new org.apache.commons.math.fraction.Fraction(12) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(15), new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(0) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction((-2)), new org.apache.commons.math.fraction.Fraction(7) } };

    // lu decomposition tests
    protected org.apache.commons.math.fraction.Fraction[][] luData = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(7) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(9), new org.apache.commons.math.fraction.Fraction(8) } };
    protected org.apache.commons.math.fraction.Fraction[][] luDataLUDecomposition = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(9), new org.apache.commons.math.fraction.Fraction(8) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(7) }, 
    new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1, 3), new org.apache.commons.math.fraction.Fraction(0), new org.apache.commons.math.fraction.Fraction(1, 3) } };

    // singular matrices
    protected org.apache.commons.math.fraction.Fraction[][] singular = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) } };
    protected org.apache.commons.math.fraction.Fraction[][] bigSingular = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, 
    new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(256), new org.apache.commons.math.fraction.Fraction(1930) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(8) } };// 4th row = 1st + 2nd
    protected org.apache.commons.math.fraction.Fraction[][] detData = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(8), new org.apache.commons.math.fraction.Fraction(10) } };
    protected org.apache.commons.math.fraction.Fraction[][] detData2 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(4) } };

    // vectors
    protected org.apache.commons.math.fraction.Fraction[] testVector = new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) };
    protected org.apache.commons.math.fraction.Fraction[] testVector2 = new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) };

    // submatrix accessor tests
    protected org.apache.commons.math.fraction.Fraction[][] subTestData = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3, 2), new org.apache.commons.math.fraction.Fraction(5, 2), new org.apache.commons.math.fraction.Fraction(7, 2), new org.apache.commons.math.fraction.Fraction(9, 2) }, 
    new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(8) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(7) } };
    // array selections
    protected org.apache.commons.math.fraction.Fraction[][] subRows02Cols13 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(8) } };
    protected org.apache.commons.math.fraction.Fraction[][] subRows03Cols12 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6) } };
    protected org.apache.commons.math.fraction.Fraction[][] subRows03Cols123 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(7) } };
    // effective permutations
    protected org.apache.commons.math.fraction.Fraction[][] subRows20Cols123 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(8) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) } };
    protected org.apache.commons.math.fraction.Fraction[][] subRows31Cols31 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(5) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(9, 2), new org.apache.commons.math.fraction.Fraction(5, 2) } };
    // contiguous ranges
    protected org.apache.commons.math.fraction.Fraction[][] subRows01Cols23 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(7, 2), new org.apache.commons.math.fraction.Fraction(9, 2) } };
    protected org.apache.commons.math.fraction.Fraction[][] subRows23Cols00 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4) } };
    protected org.apache.commons.math.fraction.Fraction[][] subRows00Cols33 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4) } };
    // row matrices
    protected org.apache.commons.math.fraction.Fraction[][] subRow0 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) } };
    protected org.apache.commons.math.fraction.Fraction[][] subRow3 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(7) } };
    // column matrices
    protected org.apache.commons.math.fraction.Fraction[][] subColumn1 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5, 2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5) } };
    protected org.apache.commons.math.fraction.Fraction[][] subColumn3 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(9, 2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(8) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(7) } };

    // tolerances
    protected double entryTolerance = 1.0E-15;
    protected double normTolerance = 1.0E-13;

    public FieldMatrixImplTest(java.lang.String name) {
        super(name);
    }

    /**
     * test dimensions
     */     public void testDimensions() {         org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m2 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2);
        junit.framework.Assert.assertEquals("testData row dimension", 3, m.getRowDimension());
        junit.framework.Assert.assertEquals("testData column dimension", 3, m.getColumnDimension());
        junit.framework.Assert.assertTrue("testData is square", m.isSquare());
        junit.framework.Assert.assertEquals("testData2 row dimension", m2.getRowDimension(), 2);
        junit.framework.Assert.assertEquals("testData2 column dimension", m2.getColumnDimension(), 3);
        junit.framework.Assert.assertTrue("testData2 is not square", (!(m2.isSquare())));
    }

    /**
     * test copy functions
     */     public void testCopyFunctions() {         org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m1 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m2 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(m1.getData());
        junit.framework.Assert.assertEquals(m2, m1);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m4 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(m3.getData(), false);
        junit.framework.Assert.assertEquals(m4, m3);
    }

    /**
     * test add
     */     public void testAdd() {         org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> mInv = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testDataInv);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mPlusMInv = m.add(mInv);
        org.apache.commons.math.fraction.Fraction[][] sumEntries = mPlusMInv.getData();
        for (int row = 0; row < (m.getRowDimension()); row++) {
            for (int col = 0; col < (m.getColumnDimension()); col++) {
                junit.framework.Assert.assertEquals(testDataPlusInv[row][col], sumEntries[row][col]);
            }
        }
    }

    /**
     * test add failure
     */     public void testAddFail() {         org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m2 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2);
        try {
            m.add(m2);
            junit.framework.Assert.fail("IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // ignored
        }
    }

    /**
     * test m-n = m + -n
     */     public void testPlusMinus() {         org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m2 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testDataInv);
        org.apache.commons.math.TestUtils.assertEquals(m.subtract(m2), m2.scalarMultiply(new org.apache.commons.math.fraction.Fraction((-1))).add(m));
        try {
            m.subtract(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2));
            junit.framework.Assert.fail("Expecting illegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // ignored
        }
    }

    /**
     * test multiply
     */     public void testMultiply() {         org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> mInv = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testDataInv);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> identity = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(id);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m2 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2);
        org.apache.commons.math.TestUtils.assertEquals(m.multiply(mInv), identity);
        org.apache.commons.math.TestUtils.assertEquals(mInv.multiply(m), identity);
        org.apache.commons.math.TestUtils.assertEquals(m.multiply(identity), m);
        org.apache.commons.math.TestUtils.assertEquals(identity.multiply(mInv), mInv);
        org.apache.commons.math.TestUtils.assertEquals(m2.multiply(identity), m2);
        try {
            m.multiply(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(bigSingular));
            junit.framework.Assert.fail("Expecting illegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // ignored
        }
    }

    // Additional Test for Array2DRowFieldMatrix<Fraction>Test.testMultiply

    private org.apache.commons.math.fraction.Fraction[][] d3 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6), new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(8) } };
    private org.apache.commons.math.fraction.Fraction[][] d4 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4) } };
    private org.apache.commons.math.fraction.Fraction[][] d5 = new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(30) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(70) } };

    public void testMultiply2() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(d3);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m4 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(d4);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m5 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(d5);
        org.apache.commons.math.TestUtils.assertEquals(m3.multiply(m4), m5);
    }

    /**
     * test trace
     */     public void testTrace() {         org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(id);
        junit.framework.Assert.assertEquals("identity trace", new org.apache.commons.math.fraction.Fraction(3), m.getTrace());
        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2);
        try {
            m.getTrace();
            junit.framework.Assert.fail("Expecting NonSquareMatrixException");
        } catch (org.apache.commons.math.linear.NonSquareMatrixException ex) {
            // ignored
        }
    }

    /**
     * test sclarAdd
     */     public void testScalarAdd() {         org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.TestUtils.assertEquals(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testDataPlus2), m.scalarAdd(new org.apache.commons.math.fraction.Fraction(2)));
    }

    /**
     * test operate
     */     public void testOperate() {         org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(id);
        org.apache.commons.math.TestUtils.assertEquals(testVector, m.operate(testVector));
        org.apache.commons.math.TestUtils.assertEquals(testVector, m.operate(new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(testVector)).getData());
        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(bigSingular);
        try {
            m.operate(testVector);
            junit.framework.Assert.fail("Expecting illegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // ignored
        }
    }

    /**
     * test issue MATH-209
     */     public void testMath209() {         org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> a = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(6) } }, 
        false);
        org.apache.commons.math.fraction.Fraction[] b = a.operate(new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(1) });
        junit.framework.Assert.assertEquals(a.getRowDimension(), b.length);
        junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(3), b[0]);
        junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(7), b[1]);
        junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(11), b[2]);
    }

    /**
     * test transpose
     */     public void testTranspose() {         org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mIT = new org.apache.commons.math.linear.FieldLUDecompositionImpl<org.apache.commons.math.fraction.Fraction>(m).getSolver().getInverse().transpose();
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mTI = new org.apache.commons.math.linear.FieldLUDecompositionImpl<org.apache.commons.math.fraction.Fraction>(m.transpose()).getSolver().getInverse();
        org.apache.commons.math.TestUtils.assertEquals(mIT, mTI);
        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mt = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData2T);
        org.apache.commons.math.TestUtils.assertEquals(mt, m.transpose());
    }

    /**
     * test preMultiply by vector
     */     public void testPremultiplyVector() {         org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.TestUtils.assertEquals(m.preMultiply(testVector), preMultTest);
        org.apache.commons.math.TestUtils.assertEquals(m.preMultiply(new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(testVector).getData()), 
        preMultTest);
        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(bigSingular);
        try {
            m.preMultiply(testVector);
            junit.framework.Assert.fail("expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // ignored
        }
    }

    public void testPremultiply() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(d3);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m4 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(d4);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m5 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(d5);
        org.apache.commons.math.TestUtils.assertEquals(m4.preMultiply(m3), m5);

        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> mInv = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testDataInv);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> identity = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(id);
        org.apache.commons.math.TestUtils.assertEquals(m.preMultiply(mInv), identity);
        org.apache.commons.math.TestUtils.assertEquals(mInv.preMultiply(m), identity);
        org.apache.commons.math.TestUtils.assertEquals(m.preMultiply(identity), m);
        org.apache.commons.math.TestUtils.assertEquals(identity.preMultiply(mInv), mInv);
        try {
            m.preMultiply(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(bigSingular));
            junit.framework.Assert.fail("Expecting illegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // ignored
        }
    }

    public void testGetVectors() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.TestUtils.assertEquals(m.getRow(0), testDataRow1);
        org.apache.commons.math.TestUtils.assertEquals(m.getColumn(2), testDataCol3);
        try {
            m.getRow(10);
            junit.framework.Assert.fail("expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // ignored
        }
        try {
            m.getColumn((-1));
            junit.framework.Assert.fail("expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // ignored
        }
    }

    public void testGetEntry() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        junit.framework.Assert.assertEquals("get entry", m.getEntry(0, 1), new org.apache.commons.math.fraction.Fraction(2));
        try {
            m.getEntry(10, 4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    /**
     * test examples in user guide
     */     public void testExamples() {         // Create a real matrix with two rows and three columns
        org.apache.commons.math.fraction.Fraction[][] matrixData = new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5), new org.apache.commons.math.fraction.Fraction(3) } };

        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(matrixData);
        // One more with three rows, two columns
        org.apache.commons.math.fraction.Fraction[][] matrixData2 = new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(5) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(7) } };

        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> n = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(matrixData2);
        // Now multiply m by n
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> p = m.multiply(n);
        junit.framework.Assert.assertEquals(2, p.getRowDimension());
        junit.framework.Assert.assertEquals(2, p.getColumnDimension());
        // Invert p
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> pInverse = new org.apache.commons.math.linear.FieldLUDecompositionImpl<org.apache.commons.math.fraction.Fraction>(p).getSolver().getInverse();
        junit.framework.Assert.assertEquals(2, pInverse.getRowDimension());
        junit.framework.Assert.assertEquals(2, pInverse.getColumnDimension());

        // Solve example
        org.apache.commons.math.fraction.Fraction[][] coefficientsData = new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction((-2)) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction((-1)), new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(6) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction((-3)), new org.apache.commons.math.fraction.Fraction((-5)) } };

        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> coefficients = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(coefficientsData);
        org.apache.commons.math.fraction.Fraction[] constants = new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction((-2)), new org.apache.commons.math.fraction.Fraction(1) };
        org.apache.commons.math.fraction.Fraction[] solution = new org.apache.commons.math.linear.FieldLUDecompositionImpl<org.apache.commons.math.fraction.Fraction>(coefficients).getSolver().solve(constants);
        junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(2).multiply(solution[0]).add(
        new org.apache.commons.math.fraction.Fraction(3).multiply(solution[1])).subtract(
        new org.apache.commons.math.fraction.Fraction(2).multiply(solution[2])), constants[0]);
        junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction((-1)).multiply(solution[0]).add(
        new org.apache.commons.math.fraction.Fraction(7).multiply(solution[1])).add(
        new org.apache.commons.math.fraction.Fraction(6).multiply(solution[2])), constants[1]);
        junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(4).multiply(solution[0]).subtract(
        new org.apache.commons.math.fraction.Fraction(3).multiply(solution[1])).subtract(
        new org.apache.commons.math.fraction.Fraction(5).multiply(solution[2])), constants[2]);

    }

    // test submatrix accessors
    public void testGetSubMatrix() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        checkGetSubMatrix(m, subRows23Cols00, 2, 3, 0, 0);
        checkGetSubMatrix(m, subRows00Cols33, 0, 0, 3, 3);
        checkGetSubMatrix(m, subRows01Cols23, 0, 1, 2, 3);
        checkGetSubMatrix(m, subRows02Cols13, new int[]{ 0, 2 }, new int[]{ 1, 3 });
        checkGetSubMatrix(m, subRows03Cols12, new int[]{ 0, 3 }, new int[]{ 1, 2 });
        checkGetSubMatrix(m, subRows03Cols123, new int[]{ 0, 3 }, new int[]{ 1, 2, 3 });
        checkGetSubMatrix(m, subRows20Cols123, new int[]{ 2, 0 }, new int[]{ 1, 2, 3 });
        checkGetSubMatrix(m, subRows31Cols31, new int[]{ 3, 1 }, new int[]{ 3, 1 });
        checkGetSubMatrix(m, subRows31Cols31, new int[]{ 3, 1 }, new int[]{ 3, 1 });
        checkGetSubMatrix(m, null, 1, 0, 2, 4);
        checkGetSubMatrix(m, null, (-1), 1, 2, 2);
        checkGetSubMatrix(m, null, 1, 0, 2, 2);
        checkGetSubMatrix(m, null, 1, 0, 2, 4);
        checkGetSubMatrix(m, null, new int[]{  }, new int[]{ 0 });
        checkGetSubMatrix(m, null, new int[]{ 0 }, new int[]{ 4 });
    }

    private void checkGetSubMatrix(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m, org.apache.commons.math.fraction.Fraction[][] reference, 
    int startRow, int endRow, int startColumn, int endColumn) {
        try {
            org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> sub = m.getSubMatrix(startRow, endRow, startColumn, endColumn);
            if (reference != null) {
                junit.framework.Assert.assertEquals(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(reference), sub);
            }else {
                junit.framework.Assert.fail("Expecting MatrixIndexException");
            }
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    private void checkGetSubMatrix(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m, org.apache.commons.math.fraction.Fraction[][] reference, 
    int[] selectedRows, int[] selectedColumns) {
        try {
            org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> sub = m.getSubMatrix(selectedRows, selectedColumns);
            if (reference != null) {
                junit.framework.Assert.assertEquals(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(reference), sub);
            }else {
                junit.framework.Assert.fail("Expecting MatrixIndexException");
            }
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    public void testCopySubMatrix() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        checkCopy(m, subRows23Cols00, 2, 3, 0, 0);
        checkCopy(m, subRows00Cols33, 0, 0, 3, 3);
        checkCopy(m, subRows01Cols23, 0, 1, 2, 3);
        checkCopy(m, subRows02Cols13, new int[]{ 0, 2 }, new int[]{ 1, 3 });
        checkCopy(m, subRows03Cols12, new int[]{ 0, 3 }, new int[]{ 1, 2 });
        checkCopy(m, subRows03Cols123, new int[]{ 0, 3 }, new int[]{ 1, 2, 3 });
        checkCopy(m, subRows20Cols123, new int[]{ 2, 0 }, new int[]{ 1, 2, 3 });
        checkCopy(m, subRows31Cols31, new int[]{ 3, 1 }, new int[]{ 3, 1 });
        checkCopy(m, subRows31Cols31, new int[]{ 3, 1 }, new int[]{ 3, 1 });

        checkCopy(m, null, 1, 0, 2, 4);
        checkCopy(m, null, (-1), 1, 2, 2);
        checkCopy(m, null, 1, 0, 2, 2);
        checkCopy(m, null, 1, 0, 2, 4);
        checkCopy(m, null, new int[]{  }, new int[]{ 0 });
        checkCopy(m, null, new int[]{ 0 }, new int[]{ 4 });
    }

    private void checkCopy(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m, org.apache.commons.math.fraction.Fraction[][] reference, 
    int startRow, int endRow, int startColumn, int endColumn) {
        try {
            org.apache.commons.math.fraction.Fraction[][] sub = (reference == null) ? 
            new org.apache.commons.math.fraction.Fraction[1][1] : 
            new org.apache.commons.math.fraction.Fraction[reference.length][reference[0].length];
            m.copySubMatrix(startRow, endRow, startColumn, endColumn, sub);
            if (reference != null) {
                junit.framework.Assert.assertEquals(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(reference), new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(sub));
            }else {
                junit.framework.Assert.fail("Expecting MatrixIndexException");
            }
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    private void checkCopy(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m, org.apache.commons.math.fraction.Fraction[][] reference, 
    int[] selectedRows, int[] selectedColumns) {
        try {
            org.apache.commons.math.fraction.Fraction[][] sub = (reference == null) ? 
            new org.apache.commons.math.fraction.Fraction[1][1] : 
            new org.apache.commons.math.fraction.Fraction[reference.length][reference[0].length];
            m.copySubMatrix(selectedRows, selectedColumns, sub);
            if (reference != null) {
                junit.framework.Assert.assertEquals(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(reference), new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(sub));
            }else {
                junit.framework.Assert.fail("Expecting MatrixIndexException");
            }
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    public void testGetRowMatrix() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mRow0 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subRow0);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mRow3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subRow3);
        junit.framework.Assert.assertEquals("Row0", mRow0, 
        m.getRowMatrix(0));
        junit.framework.Assert.assertEquals("Row3", mRow3, 
        m.getRowMatrix(3));
        try {
            m.getRowMatrix((-1));
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.getRowMatrix(4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    public void testSetRowMatrix() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mRow3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subRow3);
        junit.framework.Assert.assertNotSame(mRow3, m.getRowMatrix(0));
        m.setRowMatrix(0, mRow3);
        junit.framework.Assert.assertEquals(mRow3, m.getRowMatrix(0));
        try {
            m.setRowMatrix((-1), mRow3);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.setRowMatrix(0, m);
            junit.framework.Assert.fail("Expecting InvalidMatrixException");
        } catch (org.apache.commons.math.linear.InvalidMatrixException ex) {
            // expected
        }
    }

    public void testGetColumnMatrix() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mColumn1 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subColumn1);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mColumn3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subColumn3);
        junit.framework.Assert.assertEquals("Column1", mColumn1, 
        m.getColumnMatrix(1));
        junit.framework.Assert.assertEquals("Column3", mColumn3, 
        m.getColumnMatrix(3));
        try {
            m.getColumnMatrix((-1));
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.getColumnMatrix(4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    public void testSetColumnMatrix() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> mColumn3 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subColumn3);
        junit.framework.Assert.assertNotSame(mColumn3, m.getColumnMatrix(1));
        m.setColumnMatrix(1, mColumn3);
        junit.framework.Assert.assertEquals(mColumn3, m.getColumnMatrix(1));
        try {
            m.setColumnMatrix((-1), mColumn3);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.setColumnMatrix(0, m);
            junit.framework.Assert.fail("Expecting InvalidMatrixException");
        } catch (org.apache.commons.math.linear.InvalidMatrixException ex) {
            // expected
        }
    }

    public void testGetRowVector() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> mRow0 = new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(subRow0[0]);
        org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> mRow3 = new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(subRow3[0]);
        junit.framework.Assert.assertEquals("Row0", mRow0, m.getRowVector(0));
        junit.framework.Assert.assertEquals("Row3", mRow3, m.getRowVector(3));
        try {
            m.getRowVector((-1));
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.getRowVector(4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    public void testSetRowVector() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> mRow3 = new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(subRow3[0]);
        junit.framework.Assert.assertNotSame(mRow3, m.getRowMatrix(0));
        m.setRowVector(0, mRow3);
        junit.framework.Assert.assertEquals(mRow3, m.getRowVector(0));
        try {
            m.setRowVector((-1), mRow3);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.setRowVector(0, new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), 5));
            junit.framework.Assert.fail("Expecting InvalidMatrixException");
        } catch (org.apache.commons.math.linear.InvalidMatrixException ex) {
            // expected
        }
    }

    public void testGetColumnVector() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> mColumn1 = columnToVector(subColumn1);
        org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> mColumn3 = columnToVector(subColumn3);
        junit.framework.Assert.assertEquals("Column1", mColumn1, m.getColumnVector(1));
        junit.framework.Assert.assertEquals("Column3", mColumn3, m.getColumnVector(3));
        try {
            m.getColumnVector((-1));
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.getColumnVector(4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    public void testSetColumnVector() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> mColumn3 = columnToVector(subColumn3);
        junit.framework.Assert.assertNotSame(mColumn3, m.getColumnVector(1));
        m.setColumnVector(1, mColumn3);
        junit.framework.Assert.assertEquals(mColumn3, m.getColumnVector(1));
        try {
            m.setColumnVector((-1), mColumn3);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.setColumnVector(0, new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), 5));
            junit.framework.Assert.fail("Expecting InvalidMatrixException");
        } catch (org.apache.commons.math.linear.InvalidMatrixException ex) {
            // expected
        }
    }

    private org.apache.commons.math.linear.FieldVector<org.apache.commons.math.fraction.Fraction> columnToVector(org.apache.commons.math.fraction.Fraction[][] column) {
        org.apache.commons.math.fraction.Fraction[] data = new org.apache.commons.math.fraction.Fraction[column.length];
        for (int i = 0; i < (data.length); ++i) {
            data[i] = column[i][0];
        }
        return new org.apache.commons.math.linear.ArrayFieldVector<org.apache.commons.math.fraction.Fraction>(data, false);
    }

    public void testGetRow() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        checkArrays(subRow0[0], m.getRow(0));
        checkArrays(subRow3[0], m.getRow(3));
        try {
            m.getRow((-1));
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.getRow(4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    public void testSetRow() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        junit.framework.Assert.assertTrue(((subRow3[0][0]) != (m.getRow(0)[0])));
        m.setRow(0, subRow3[0]);
        checkArrays(subRow3[0], m.getRow(0));
        try {
            m.setRow((-1), subRow3[0]);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.setRow(0, new org.apache.commons.math.fraction.Fraction[5]);
            junit.framework.Assert.fail("Expecting InvalidMatrixException");
        } catch (org.apache.commons.math.linear.InvalidMatrixException ex) {
            // expected
        }
    }

    public void testGetColumn() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.fraction.Fraction[] mColumn1 = columnToArray(subColumn1);
        org.apache.commons.math.fraction.Fraction[] mColumn3 = columnToArray(subColumn3);
        checkArrays(mColumn1, m.getColumn(1));
        checkArrays(mColumn3, m.getColumn(3));
        try {
            m.getColumn((-1));
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.getColumn(4);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
    }

    public void testSetColumn() {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(subTestData);
        org.apache.commons.math.fraction.Fraction[] mColumn3 = columnToArray(subColumn3);
        junit.framework.Assert.assertTrue(((mColumn3[0]) != (m.getColumn(1)[0])));
        m.setColumn(1, mColumn3);
        checkArrays(mColumn3, m.getColumn(1));
        try {
            m.setColumn((-1), mColumn3);
            junit.framework.Assert.fail("Expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException ex) {
            // expected
        }
        try {
            m.setColumn(0, new org.apache.commons.math.fraction.Fraction[5]);
            junit.framework.Assert.fail("Expecting InvalidMatrixException");
        } catch (org.apache.commons.math.linear.InvalidMatrixException ex) {
            // expected
        }
    }

    private org.apache.commons.math.fraction.Fraction[] columnToArray(org.apache.commons.math.fraction.Fraction[][] column) {
        org.apache.commons.math.fraction.Fraction[] data = new org.apache.commons.math.fraction.Fraction[column.length];
        for (int i = 0; i < (data.length); ++i) {
            data[i] = column[i][0];
        }
        return data;
    }

    private void checkArrays(org.apache.commons.math.fraction.Fraction[] expected, org.apache.commons.math.fraction.Fraction[] actual) {
        junit.framework.Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < (expected.length); ++i) {
            junit.framework.Assert.assertEquals(expected[i], actual[i]);
        }
    }

    public void testEqualsAndHashCode() {
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m1 = ((org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>) (m.copy()));
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> mt = ((org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>) (m.transpose()));
        junit.framework.Assert.assertTrue(((m.hashCode()) != (mt.hashCode())));
        junit.framework.Assert.assertEquals(m.hashCode(), m1.hashCode());
        junit.framework.Assert.assertEquals(m, m);
        junit.framework.Assert.assertEquals(m, m1);
        junit.framework.Assert.assertFalse(m.equals(null));
        junit.framework.Assert.assertFalse(m.equals(mt));
        junit.framework.Assert.assertFalse(m.equals(new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(bigSingular)));
    }

    public void testToString() {
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        junit.framework.Assert.assertEquals("Array2DRowFieldMatrix{{1,2,3},{2,5,3},{1,0,8}}", m.toString());
        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance());
        junit.framework.Assert.assertEquals("Array2DRowFieldMatrix{}", m.toString());
    }

    public void testSetSubMatrix() throws java.lang.Exception {
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        m.setSubMatrix(detData2, 1, 1);
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> expected = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(
        new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(3) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(4) } });

        junit.framework.Assert.assertEquals(expected, m);

        m.setSubMatrix(detData2, 0, 0);
        expected = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(
        new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(3) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(3) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(4) } });

        junit.framework.Assert.assertEquals(expected, m);

        m.setSubMatrix(testDataPlus2, 0, 0);
        expected = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(
        new org.apache.commons.math.fraction.Fraction[][]{ 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(5) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(4), new org.apache.commons.math.fraction.Fraction(7), new org.apache.commons.math.fraction.Fraction(5) }, 
        new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(3), new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(10) } });

        junit.framework.Assert.assertEquals(expected, m);

        // dimension overflow
        try {
            m.setSubMatrix(testData, 1, 1);
            junit.framework.Assert.fail("expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            // expected
        }
        // dimension underflow
        try {
            m.setSubMatrix(testData, (-1), 1);
            junit.framework.Assert.fail("expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            // expected
        }
        try {
            m.setSubMatrix(testData, 1, (-1));
            junit.framework.Assert.fail("expecting MatrixIndexException");
        } catch (org.apache.commons.math.linear.MatrixIndexException e) {
            // expected
        }

        // null
        try {
            m.setSubMatrix(null, 1, 1);
            junit.framework.Assert.fail("expecting NullPointerException");
        } catch (java.lang.NullPointerException e) {
            // expected
        }
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m2 = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance());
        try {
            m2.setSubMatrix(testData, 0, 1);
            junit.framework.Assert.fail("expecting IllegalStateException");
        } catch (java.lang.IllegalStateException e) {
            // expected
        }
        try {
            m2.setSubMatrix(testData, 1, 0);
            junit.framework.Assert.fail("expecting IllegalStateException");
        } catch (java.lang.IllegalStateException e) {
            // expected
        }

        // ragged
        try {
            m.setSubMatrix(new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(1) }, new org.apache.commons.math.fraction.Fraction[]{ new org.apache.commons.math.fraction.Fraction(2), new org.apache.commons.math.fraction.Fraction(3) } }, 0, 0);
            junit.framework.Assert.fail("expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException e) {
            // expected
        }

        // empty
        try {
            m.setSubMatrix(new org.apache.commons.math.fraction.Fraction[][]{ new org.apache.commons.math.fraction.Fraction[]{  } }, 0, 0);
            junit.framework.Assert.fail("expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException e) {
            // expected
        }

    }

    public void testWalk() {
        int rows = 150;
        int columns = 75;

        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> m = 
        new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInRowOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor());
        org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInOptimizedOrder(getVisitor);
        junit.framework.Assert.assertEquals((rows * columns), getVisitor.getCount());

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInRowOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor(), 1, (rows - 2), 1, (columns - 2));
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInOptimizedOrder(getVisitor, 1, (rows - 2), 1, (columns - 2));
        junit.framework.Assert.assertEquals(((rows - 2) * (columns - 2)), getVisitor.getCount());
        for (int i = 0; i < rows; ++i) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, 0));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, (columns - 1)));
        }
        for (int j = 0; j < columns; ++j) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(0, j));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry((rows - 1), j));
        }

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInColumnOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor());
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInOptimizedOrder(getVisitor);
        junit.framework.Assert.assertEquals((rows * columns), getVisitor.getCount());

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInColumnOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor(), 1, (rows - 2), 1, (columns - 2));
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInOptimizedOrder(getVisitor, 1, (rows - 2), 1, (columns - 2));
        junit.framework.Assert.assertEquals(((rows - 2) * (columns - 2)), getVisitor.getCount());
        for (int i = 0; i < rows; ++i) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, 0));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, (columns - 1)));
        }
        for (int j = 0; j < columns; ++j) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(0, j));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry((rows - 1), j));
        }

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInOptimizedOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor());
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInRowOrder(getVisitor);
        junit.framework.Assert.assertEquals((rows * columns), getVisitor.getCount());

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInOptimizedOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor(), 1, (rows - 2), 1, (columns - 2));
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInRowOrder(getVisitor, 1, (rows - 2), 1, (columns - 2));
        junit.framework.Assert.assertEquals(((rows - 2) * (columns - 2)), getVisitor.getCount());
        for (int i = 0; i < rows; ++i) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, 0));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, (columns - 1)));
        }
        for (int j = 0; j < columns; ++j) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(0, j));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry((rows - 1), j));
        }

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInOptimizedOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor());
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInColumnOrder(getVisitor);
        junit.framework.Assert.assertEquals((rows * columns), getVisitor.getCount());

        m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(org.apache.commons.math.fraction.FractionField.getInstance(), rows, columns);
        m.walkInOptimizedOrder(new org.apache.commons.math.linear.FieldMatrixImplTest.SetVisitor(), 1, (rows - 2), 1, (columns - 2));
        getVisitor = new org.apache.commons.math.linear.FieldMatrixImplTest.GetVisitor();
        m.walkInColumnOrder(getVisitor, 1, (rows - 2), 1, (columns - 2));
        junit.framework.Assert.assertEquals(((rows - 2) * (columns - 2)), getVisitor.getCount());
        for (int i = 0; i < rows; ++i) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, 0));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(i, (columns - 1)));
        }
        for (int j = 0; j < columns; ++j) {
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry(0, j));
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(0), m.getEntry((rows - 1), j));
        }

    }

    public void testSerial() {
        org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction> m = new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(testData);
        junit.framework.Assert.assertEquals(m, org.apache.commons.math.TestUtils.serializeAndRecover(m));
    }

    private static class SetVisitor extends org.apache.commons.math.linear.DefaultFieldMatrixChangingVisitor<org.apache.commons.math.fraction.Fraction> {
        public SetVisitor() {
            super(org.apache.commons.math.fraction.Fraction.ZERO);
        }
        @java.lang.Override
        public org.apache.commons.math.fraction.Fraction visit(int i, int j, org.apache.commons.math.fraction.Fraction value) {
            return new org.apache.commons.math.fraction.Fraction(((i * 1024) + j), 1024);
        }
    }

    private static class GetVisitor extends org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<org.apache.commons.math.fraction.Fraction> {
        private int count;
        public GetVisitor() {
            super(org.apache.commons.math.fraction.Fraction.ZERO);
            count = 0;
        }
        @java.lang.Override
        public void visit(int i, int j, org.apache.commons.math.fraction.Fraction value) {
            ++(count);
            junit.framework.Assert.assertEquals(new org.apache.commons.math.fraction.Fraction(((i * 1024) + j), 1024), value);
        }
        public int getCount() {
            return count;
        }
    }

    // --------------- -----------------Protected methods

    /**
     * extracts the l  and u matrices from compact lu representation
     */     protected void splitLU(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> lu, org.apache.commons.math.fraction.Fraction[][] lowerData, 
    org.apache.commons.math.fraction.Fraction[][] upperData) throws 
    org.apache.commons.math.linear.InvalidMatrixException {
        if (((((!(lu.isSquare())) || 
        ((lowerData.length) != (lowerData[0].length))) || 
        ((upperData.length) != (upperData[0].length))) || 
        ((lowerData.length) != (upperData.length))) || 
        ((lowerData.length) != (lu.getRowDimension()))) {
            throw new org.apache.commons.math.linear.InvalidMatrixException("incorrect dimensions");
        }
        int n = lu.getRowDimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j < i) {
                    lowerData[i][j] = lu.getEntry(i, j);
                    upperData[i][j] = org.apache.commons.math.fraction.Fraction.ZERO;
                }else                     if (i == j) {
                        lowerData[i][j] = org.apache.commons.math.fraction.Fraction.ONE;
                        upperData[i][j] = lu.getEntry(i, j);
                    }else {
                        lowerData[i][j] = org.apache.commons.math.fraction.Fraction.ZERO;
                        upperData[i][j] = lu.getEntry(i, j);
                    }
            }
        }
    }

    /**
     * Returns the result of applying the given row permutation to the matrix
     */     protected org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> permuteRows(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> matrix, int[] permutation) {         if ((!(matrix.isSquare())) || ((matrix.getRowDimension()) != (permutation.length))) {
            throw new java.lang.IllegalArgumentException("dimension mismatch");
        }
        int n = matrix.getRowDimension();
        int m = matrix.getColumnDimension();
        org.apache.commons.math.fraction.Fraction[][] out = new org.apache.commons.math.fraction.Fraction[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out[i][j] = matrix.getEntry(permutation[i], j);
            }
        }
        return new org.apache.commons.math.linear.Array2DRowFieldMatrix<org.apache.commons.math.fraction.Fraction>(out);
    }

}