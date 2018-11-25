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
 */ public class KurtosisTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.moment.Kurtosis stat;

    /**
     *
     *
     * @param name
     * 		
     */     public KurtosisTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.kurt;
    }

    /**
     * Make sure Double.NaN is returned iff n < 4
     */

    public void testNaN() {
        org.apache.commons.math.stat.descriptive.moment.Kurtosis kurt = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(kurt.getResult()));
        kurt.increment(1.0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(kurt.getResult()));
        kurt.increment(1.0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(kurt.getResult()));
        kurt.increment(1.0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(kurt.getResult()));
        kurt.increment(1.0);
        junit.framework.Assert.assertFalse(java.lang.Double.isNaN(kurt.getResult()));
    }

}