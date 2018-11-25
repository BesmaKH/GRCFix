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
package org.apache.commons.math.stat.descriptive.summary;




/**
 * Test cases for the {@link UnivariateStatistic} class.
 *
 * @version $Revision$ $Date$
 */ public class SumLogTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.summary.SumOfLogs stat;

    /**
     *
     *
     * @param name
     * 		
     */     public SumLogTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.sumLog;
    }

    public void testSpecialValues() {
        org.apache.commons.math.stat.descriptive.summary.SumOfLogs sum = new org.apache.commons.math.stat.descriptive.summary.SumOfLogs();
        // empty
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sum.getResult()));

        // finite data
        sum.increment(1.0);
        junit.framework.Assert.assertFalse(java.lang.Double.isNaN(sum.getResult()));

        // add negative infinity
        sum.increment(0.0);
        junit.framework.Assert.assertEquals(java.lang.Double.NEGATIVE_INFINITY, sum.getResult(), 0);

        // add positive infinity -- should make NaN
        sum.increment(java.lang.Double.POSITIVE_INFINITY);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sum.getResult()));

        // clear
        sum.clear();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sum.getResult()));

        // positive infinity by itself
        sum.increment(java.lang.Double.POSITIVE_INFINITY);
        junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, sum.getResult(), 0);

        // negative value -- should make NaN
        sum.increment((-2.0));
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sum.getResult()));
    }

}