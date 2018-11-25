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
package org.apache.commons.math.analysis.solvers;








/**
 *
 *
 * @version $Revision$ $Date$
 */ public class UnivariateRealSolverUtilsTest extends junit.framework.TestCase {
    protected org.apache.commons.math.analysis.UnivariateRealFunction sin = new org.apache.commons.math.analysis.SinFunction();

    public void testSolveNull() throws org.apache.commons.math.MathException {
        try {
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(null, 0.0, 4.0);
            junit.framework.Assert.fail();
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
    }

    public void testSolveBadParameters() throws org.apache.commons.math.MathException {
        try {             // bad endpoints
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(sin, 0.0, 4.0, 4.0);
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {             // bad accuracy
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(sin, 0.0, 4.0, 0.0);
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

    public void testSolveSin() throws org.apache.commons.math.MathException {
        double x = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(sin, 1.0, 4.0);
        junit.framework.Assert.assertEquals(java.lang.Math.PI, x, 1.0E-4);
    }

    public void testSolveAccuracyNull() throws org.apache.commons.math.MathException {
        try {
            double accuracy = 1.0E-6;
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(null, 0.0, 4.0, accuracy);
            junit.framework.Assert.fail();
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
    }

    public void testSolveAccuracySin() throws org.apache.commons.math.MathException {
        double accuracy = 1.0E-6;
        double x = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(sin, 1.0, 
        4.0, accuracy);
        junit.framework.Assert.assertEquals(java.lang.Math.PI, x, accuracy);
    }

    public void testSolveNoRoot() throws org.apache.commons.math.MathException {
        try {
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(sin, 1.0, 1.5);
            junit.framework.Assert.fail("Expecting IllegalArgumentException ");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

    public void testBracketSin() throws org.apache.commons.math.MathException {
        double[] result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(sin, 
        0.0, (-2.0), 2.0);
        junit.framework.Assert.assertTrue(((sin.value(result[0])) < 0));
        junit.framework.Assert.assertTrue(((sin.value(result[1])) > 0));
    }

    public void testBracketEndpointRoot() throws org.apache.commons.math.MathException {
        double[] result = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(sin, 1.5, 0, 2.0);
        junit.framework.Assert.assertEquals(0.0, sin.value(result[0]), 1.0E-15);
        junit.framework.Assert.assertTrue(((sin.value(result[1])) > 0));
    }

    public void testBadParameters() throws org.apache.commons.math.MathException {
        try {             // null function
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(null, 1.5, 0, 2.0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {             // initial not between endpoints
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(sin, 2.5, 0, 2.0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {             // endpoints not valid
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(sin, 1.5, 2.0, 1.0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {             // bad maximum iterations
            org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(sin, 1.5, 0, 2.0, 0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

}