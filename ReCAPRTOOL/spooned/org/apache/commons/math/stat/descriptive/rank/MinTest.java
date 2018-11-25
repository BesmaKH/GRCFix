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
package org.apache.commons.math.stat.descriptive.rank;




/**
 * Test cases for the {@link UnivariateStatistic} class.
 *
 * @version $Revision$ $Date$
 */ public class MinTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.rank.Min stat;

    /**
     *
     *
     * @param name
     * 		
     */     public MinTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.rank.Min();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.min;
    }

    public void testSpecialValues() {
        double[] testArray = new double[]{ 0.0, java.lang.Double.NaN, java.lang.Double.POSITIVE_INFINITY, 
        java.lang.Double.NEGATIVE_INFINITY };
        org.apache.commons.math.stat.descriptive.rank.Min min = new org.apache.commons.math.stat.descriptive.rank.Min();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(min.getResult()));
        min.increment(testArray[0]);
        junit.framework.Assert.assertEquals(0.0, min.getResult(), 0);
        min.increment(testArray[1]);
        junit.framework.Assert.assertEquals(0.0, min.getResult(), 0);
        min.increment(testArray[2]);
        junit.framework.Assert.assertEquals(0.0, min.getResult(), 0);
        min.increment(testArray[3]);
        junit.framework.Assert.assertEquals(java.lang.Double.NEGATIVE_INFINITY, min.getResult(), 0);
        junit.framework.Assert.assertEquals(java.lang.Double.NEGATIVE_INFINITY, min.evaluate(testArray), 0);
    }

    public void testNaNs() {
        org.apache.commons.math.stat.descriptive.rank.Min min = new org.apache.commons.math.stat.descriptive.rank.Min();
        double nan = java.lang.Double.NaN;
        junit.framework.Assert.assertEquals(2.0, min.evaluate(new double[]{ nan, 2.0, 3.0 }), 0);
        junit.framework.Assert.assertEquals(1.0, min.evaluate(new double[]{ 1.0, nan, 3.0 }), 0);
        junit.framework.Assert.assertEquals(1.0, min.evaluate(new double[]{ 1.0, 2.0, nan }), 0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(min.evaluate(new double[]{ nan, nan, nan })));
    }

}