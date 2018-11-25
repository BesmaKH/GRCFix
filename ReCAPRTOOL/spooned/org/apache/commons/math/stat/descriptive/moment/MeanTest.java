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
 */ public class MeanTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.moment.Mean stat;

    /**
     *
     *
     * @param name
     * 		
     */     public MeanTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.moment.Mean();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.mean;
    }

    /**
     * Expected value for  the testArray defined in UnivariateStatisticAbstractTest
     */     public double expectedWeightedValue() {         return this.weightedMean;
    }

    public void testSmallSamples() {
        org.apache.commons.math.stat.descriptive.moment.Mean mean = new org.apache.commons.math.stat.descriptive.moment.Mean();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(mean.getResult()));
        mean.increment(1.0);
        junit.framework.Assert.assertEquals(1.0, mean.getResult(), 0);
    }

    public void testWeightedMean() {
        org.apache.commons.math.stat.descriptive.moment.Mean mean = new org.apache.commons.math.stat.descriptive.moment.Mean();
        junit.framework.Assert.assertEquals(expectedWeightedValue(), mean.evaluate(testArray, testWeightsArray, 0, testArray.length), getTolerance());
        junit.framework.Assert.assertEquals(expectedValue(), mean.evaluate(testArray, identicalWeightsArray, 0, testArray.length), getTolerance());
    }

}