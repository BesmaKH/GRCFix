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
 * Test cases for the {@link SumOfSquares} class.
 *
 * @version $Revision$ $Date$
 */
public class SumSqTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.summary.SumOfSquares stat;

    /**
     *
     *
     * @param name
     * 		
     */     public SumSqTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.sumSq;
    }

    public void testSpecialValues() {
        org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumSq = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sumSq.getResult()));
        sumSq.increment(2.0);
        junit.framework.Assert.assertEquals(4.0, sumSq.getResult(), 0);
        sumSq.increment(java.lang.Double.POSITIVE_INFINITY);
        junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, sumSq.getResult(), 0);
        sumSq.increment(java.lang.Double.NEGATIVE_INFINITY);
        junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, sumSq.getResult(), 0);
        sumSq.increment(java.lang.Double.NaN);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sumSq.getResult()));
        sumSq.increment(1);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(sumSq.getResult()));
    }

}