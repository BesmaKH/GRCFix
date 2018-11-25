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
 * This class contains test cases for the ExpandableDoubleArray.
 *
 * @version $Revision$ $Date$
 */
public abstract class DoubleArrayAbstractTest extends junit.framework.TestCase {

    protected org.apache.commons.math.util.DoubleArray da = null;

    // Array used to test rolling
    protected org.apache.commons.math.util.DoubleArray ra = null;

    public DoubleArrayAbstractTest(java.lang.String name) {
        super(name);
    }

    public void testAdd1000() {

        for (int i = 0; i < 1000; i++) {
            da.addElement(i);
        }

        junit.framework.Assert.assertEquals(
        "Number of elements should be equal to 1000 after adding 1000 values", 
        1000, 
        da.getNumElements());

        junit.framework.Assert.assertEquals(
        "The element at the 56th index should be 56", 
        56.0, 
        da.getElement(56), 
        java.lang.Double.MIN_VALUE);

    }

    public void testGetValues() {
        double[] controlArray = new double[]{ 2.0, 4.0, 6.0 };

        da.addElement(2.0);
        da.addElement(4.0);
        da.addElement(6.0);
        double[] testArray = da.getElements();

        for (int i = 0; i < (da.getNumElements()); i++) {
            junit.framework.Assert.assertEquals(
            (("The testArray values should equal the controlArray values, index i: " + 
            i) + 
            " does not match"), 
            testArray[i], 
            controlArray[i], 
            java.lang.Double.MIN_VALUE);
        }

    }

    public void testAddElementRolling() {
        ra.addElement(0.5);
        ra.addElement(1.0);
        ra.addElement(1.0);
        ra.addElement(1.0);
        ra.addElement(1.0);
        ra.addElement(1.0);
        ra.addElementRolling(2.0);

        junit.framework.Assert.assertEquals(
        "There should be 6 elements in the eda", 
        6, 
        ra.getNumElements());
        junit.framework.Assert.assertEquals(
        "The max element should be 2.0", 
        2.0, 
        org.apache.commons.math.stat.StatUtils.max(ra.getElements()), 
        java.lang.Double.MIN_VALUE);
        junit.framework.Assert.assertEquals(
        "The min element should be 1.0", 
        1.0, 
        org.apache.commons.math.stat.StatUtils.min(ra.getElements()), 
        java.lang.Double.MIN_VALUE);

        for (int i = 0; i < 1024; i++) {
            ra.addElementRolling(i);
        }

        junit.framework.Assert.assertEquals(
        "We just inserted 1024 rolling elements, num elements should still be 6", 
        6, 
        ra.getNumElements());
    }

    public void testMinMax() {
        da.addElement(2.0);
        da.addElement(22.0);
        da.addElement((-2.0));
        da.addElement(21.0);
        da.addElement(22.0);
        da.addElement(42.0);
        da.addElement(62.0);
        da.addElement(22.0);
        da.addElement(122.0);
        da.addElement(1212.0);

        junit.framework.Assert.assertEquals("Min should be -2.0", (-2.0), org.apache.commons.math.stat.StatUtils.min(da.getElements()), java.lang.Double.MIN_VALUE);
        junit.framework.Assert.assertEquals(
        "Max should be 1212.0", 
        1212.0, 
        org.apache.commons.math.stat.StatUtils.max(da.getElements()), 
        java.lang.Double.MIN_VALUE);
    }

}