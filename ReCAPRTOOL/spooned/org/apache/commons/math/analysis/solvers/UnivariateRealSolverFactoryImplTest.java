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
package org.apache.commons.math.analysis.solvers;





/**
 *
 *
 * @version $Revision$ $Date$
 */ public class UnivariateRealSolverFactoryImplTest extends junit.framework.TestCase {
    /**
     * solver factory
     */     private org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory factory;
    /**
     *
     *
     * @throws java.lang.Exception
     * 		
     * @see junit.framework.TestCase#tearDown()
     */     @java.lang.Override     protected void setUp() throws java.lang.Exception {         super.setUp();
        factory = new org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactoryImpl();
    }

    /**
     *
     *
     * @throws java.lang.Exception
     * 		
     * @see junit.framework.TestCase#tearDown()
     */     @java.lang.Override     protected void tearDown() throws java.lang.Exception {         factory = null;
        super.tearDown();
    }

    public void testNewBisectionSolverValid() {
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = factory.newBisectionSolver();
        junit.framework.Assert.assertNotNull(solver);
        junit.framework.Assert.assertTrue((solver instanceof org.apache.commons.math.analysis.solvers.BisectionSolver));
    }

    public void testNewNewtonSolverValid() {
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = factory.newNewtonSolver();
        junit.framework.Assert.assertNotNull(solver);
        junit.framework.Assert.assertTrue((solver instanceof org.apache.commons.math.analysis.solvers.NewtonSolver));
    }

    public void testNewBrentSolverValid() {
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = factory.newBrentSolver();
        junit.framework.Assert.assertNotNull(solver);
        junit.framework.Assert.assertTrue((solver instanceof org.apache.commons.math.analysis.solvers.BrentSolver));
    }

    public void testNewSecantSolverValid() {
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = factory.newSecantSolver();
        junit.framework.Assert.assertNotNull(solver);
        junit.framework.Assert.assertTrue((solver instanceof org.apache.commons.math.analysis.solvers.SecantSolver));
    }

}