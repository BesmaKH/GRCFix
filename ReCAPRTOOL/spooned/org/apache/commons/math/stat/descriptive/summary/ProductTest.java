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
 */ public class ProductTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.summary.Product stat;

    /**
     *
     *
     * @param name
     * 		
     */     public ProductTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.summary.Product();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double getTolerance() {
        return 1.0E9;// sic -- big absolute error due to only 15 digits of accuracy in double
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.product;
    }

    /**
     * Expected value for  the testArray defined in UnivariateStatisticAbstractTest
     */     public double expectedWeightedValue() {         return this.weightedProduct;
    }

    public void testSpecialValues() {
        org.apache.commons.math.stat.descriptive.summary.Product product = new org.apache.commons.math.stat.descriptive.summary.Product();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(product.getResult()));
        product.increment(1);
        junit.framework.Assert.assertEquals(1, product.getResult(), 0);
        product.increment(java.lang.Double.POSITIVE_INFINITY);
        junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, product.getResult(), 0);
        product.increment(java.lang.Double.NEGATIVE_INFINITY);
        junit.framework.Assert.assertEquals(java.lang.Double.NEGATIVE_INFINITY, product.getResult(), 0);
        product.increment(java.lang.Double.NaN);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(product.getResult()));
        product.increment(1);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(product.getResult()));
    }

    public void testWeightedProduct() {
        org.apache.commons.math.stat.descriptive.summary.Product product = new org.apache.commons.math.stat.descriptive.summary.Product();
        junit.framework.Assert.assertEquals(expectedWeightedValue(), product.evaluate(testArray, testWeightsArray, 0, testArray.length), getTolerance());
        junit.framework.Assert.assertEquals(expectedValue(), product.evaluate(testArray, unitWeightsArray, 0, testArray.length), getTolerance());
    }

}