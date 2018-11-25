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
 *
 * @version $Revision$ $Date$
 */ public class ContinuedFractionTest extends junit.framework.TestCase {     /**
     * Constructor for ContinuedFractionTest.
     *
     * @param name
     * 		
     */     public ContinuedFractionTest(java.lang.String name) {         super(name);
    }

    public void testGoldenRatio() {
        org.apache.commons.math.util.ContinuedFraction cf = new org.apache.commons.math.util.ContinuedFraction() {

            @java.lang.Override
            public double getA(int n, double x) {
                return 1.0;
            }

            @java.lang.Override
            public double getB(int n, double x) {
                return 1.0;
            }
        };

        try {
            double gr = cf.evaluate(0.0, 1.0E-8);
            junit.framework.Assert.assertEquals(1.61803399, gr, 1.0E-8);
        } catch (org.apache.commons.math.MathException e) {
            junit.framework.Assert.fail(e.getMessage());
        }
    }
}