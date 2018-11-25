/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.commons.math.special;






/**
 *
 *
 * @version $Revision$ $Date$
 */ public class ErfTest extends junit.framework.TestCase {
    public void testErf0() throws org.apache.commons.math.MathException {
        double actual = org.apache.commons.math.special.Erf.erf(0.0);
        double expected = 0.0;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);
    }

    public void testErf1960() throws org.apache.commons.math.MathException {
        double x = 1.96 / (java.lang.Math.sqrt(2.0));
        double actual = org.apache.commons.math.special.Erf.erf(x);
        double expected = 0.95;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);

        actual = org.apache.commons.math.special.Erf.erf((-x));
        expected = -expected;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);
    }

    public void testErf2576() throws org.apache.commons.math.MathException {
        double x = 2.576 / (java.lang.Math.sqrt(2.0));
        double actual = org.apache.commons.math.special.Erf.erf(x);
        double expected = 0.99;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);

        actual = org.apache.commons.math.special.Erf.erf((-x));
        expected = -expected;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);
    }

    public void testErf2807() throws org.apache.commons.math.MathException {
        double x = 2.807 / (java.lang.Math.sqrt(2.0));
        double actual = org.apache.commons.math.special.Erf.erf(x);
        double expected = 0.995;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);

        actual = org.apache.commons.math.special.Erf.erf((-x));
        expected = -expected;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);
    }

    public void testErf3291() throws org.apache.commons.math.MathException {
        double x = 3.291 / (java.lang.Math.sqrt(2.0));
        double actual = org.apache.commons.math.special.Erf.erf(x);
        double expected = 0.999;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);

        actual = org.apache.commons.math.special.Erf.erf((-x));
        expected = -expected;
        junit.framework.Assert.assertEquals(expected, actual, 1.0E-5);
    }

    /**
     * MATH-301
     */
    public void testLargeValues() throws java.lang.Exception {
        for (int i = 1; i < 200; i++) {
            double result = org.apache.commons.math.special.Erf.erf(i);
            junit.framework.Assert.assertFalse(java.lang.Double.isNaN(result));
            junit.framework.Assert.assertTrue(((result > 0) && (result <= 1)));
        }
    }
}