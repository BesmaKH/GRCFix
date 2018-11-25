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
package org.apache.commons.math.analysis;





public class BinaryFunctionTest {

    @org.junit.Test
    public void testAdd() throws org.apache.commons.math.FunctionEvaluationException {
        org.junit.Assert.assertEquals(5.0, org.apache.commons.math.analysis.BinaryFunction.ADD.value(2, 3), 1.0E-15);
        org.junit.Assert.assertEquals(0.0, org.apache.commons.math.analysis.BinaryFunction.ADD.value((-1), 1), 1.0E-15);
    }

    @org.junit.Test
    public void testSubtract() throws org.apache.commons.math.FunctionEvaluationException {
        org.junit.Assert.assertEquals((-1.0), org.apache.commons.math.analysis.BinaryFunction.SUBTRACT.value(2, 3), 1.0E-15);
        org.junit.Assert.assertEquals((-2.0), org.apache.commons.math.analysis.BinaryFunction.SUBTRACT.value((-1), 1), 1.0E-15);
    }

    @org.junit.Test
    public void testMultiply() throws org.apache.commons.math.FunctionEvaluationException {
        org.junit.Assert.assertEquals(6.0, org.apache.commons.math.analysis.BinaryFunction.MULTIPLY.value(2, 3), 1.0E-15);
        org.junit.Assert.assertEquals((-1.0), org.apache.commons.math.analysis.BinaryFunction.MULTIPLY.value((-1), 1), 1.0E-15);
    }

    @org.junit.Test
    public void testDivide() throws org.apache.commons.math.FunctionEvaluationException {
        org.junit.Assert.assertEquals(1.5, org.apache.commons.math.analysis.BinaryFunction.DIVIDE.value(3, 2), 1.0E-15);
        org.junit.Assert.assertEquals((-1.0), org.apache.commons.math.analysis.BinaryFunction.DIVIDE.value((-1), 1), 1.0E-15);
    }

    @org.junit.Test
    public void testPow() throws org.apache.commons.math.FunctionEvaluationException {
        org.junit.Assert.assertEquals(9.0, org.apache.commons.math.analysis.BinaryFunction.POW.value(3, 2), 1.0E-15);
        org.junit.Assert.assertEquals((-1.0), org.apache.commons.math.analysis.BinaryFunction.POW.value((-1), 1), 1.0E-15);
    }

    @org.junit.Test
    public void testAtan2() throws org.apache.commons.math.FunctionEvaluationException {
        org.junit.Assert.assertEquals(((java.lang.Math.PI) / 4), org.apache.commons.math.analysis.BinaryFunction.ATAN2.value(1, 1), 1.0E-15);
        org.junit.Assert.assertEquals(((-(java.lang.Math.PI)) / 4), org.apache.commons.math.analysis.BinaryFunction.ATAN2.value((-1), 1), 1.0E-15);
    }

    @org.junit.Test
    public void testFix1st() throws org.apache.commons.math.FunctionEvaluationException {
        org.apache.commons.math.analysis.ComposableFunction f = org.apache.commons.math.analysis.BinaryFunction.POW.fix1stArgument(2);
        for (double x = 0.0; x < 1.0; x += 0.01) {
            org.junit.Assert.assertEquals(java.lang.Math.pow(2.0, x), f.value(x), 1.0E-15);
        }
    }

    @org.junit.Test
    public void testFix2nd() throws org.apache.commons.math.FunctionEvaluationException {
        org.apache.commons.math.analysis.ComposableFunction f = org.apache.commons.math.analysis.BinaryFunction.POW.fix2ndArgument(2);
        for (double y = 0.0; y < 1.0; y += 0.01) {
            org.junit.Assert.assertEquals((y * y), f.value(y), 1.0E-15);
        }
    }

}