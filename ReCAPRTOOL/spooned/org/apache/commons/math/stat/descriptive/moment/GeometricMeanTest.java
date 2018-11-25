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
package org.apache.commons.math.stat.descriptive.moment;




/**
 * Test cases for the {@link UnivariateStatistic} class.
 *
 * @version $Revision$ $Date$
 */ public class GeometricMeanTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.moment.GeometricMean stat;

    /**
     *
     *
     * @param name
     * 		
     */     public GeometricMeanTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.geoMean;
    }

    public void testSpecialValues() {
        org.apache.commons.math.stat.descriptive.moment.GeometricMean mean = new org.apache.commons.math.stat.descriptive.moment.GeometricMean();
        // empty
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(mean.getResult()));

        // finite data
        mean.increment(1.0);
        junit.framework.Assert.assertFalse(java.lang.Double.isNaN(mean.getResult()));

        // add 0 -- makes log sum blow to minus infinity, should make 0
        mean.increment(0.0);
        junit.framework.Assert.assertEquals(0.0, mean.getResult(), 0);

        // add positive infinity - note the minus infinity above
        mean.increment(java.lang.Double.POSITIVE_INFINITY);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(mean.getResult()));

        // clear
        mean.clear();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(mean.getResult()));

        // positive infinity by itself
        mean.increment(java.lang.Double.POSITIVE_INFINITY);
        junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, mean.getResult(), 0);

        // negative value -- should make NaN
        mean.increment((-2.0));
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(mean.getResult()));
    }

}