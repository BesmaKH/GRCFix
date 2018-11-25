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
 */
public class SkewnessTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.moment.Skewness stat;

    /**
     *
     *
     * @param name
     * 		
     */     public SkewnessTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.moment.Skewness();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.skew;
    }

    /**
     * Make sure Double.NaN is returned iff n < 3
     */

    public void testNaN() {
        org.apache.commons.math.stat.descriptive.moment.Skewness skew = new org.apache.commons.math.stat.descriptive.moment.Skewness();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(skew.getResult()));
        skew.increment(1.0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(skew.getResult()));
        skew.increment(1.0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(skew.getResult()));
        skew.increment(1.0);
        junit.framework.Assert.assertFalse(java.lang.Double.isNaN(skew.getResult()));
    }

}