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
public class StandardDeviationTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.moment.StandardDeviation stat;

    /**
     *
     *
     * @param name
     * 		
     */     public StandardDeviationTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.moment.StandardDeviation();
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.std;
    }

    /**
     * Make sure Double.NaN is returned iff n = 0
     */

    public void testNaN() {
        org.apache.commons.math.stat.descriptive.moment.StandardDeviation std = new org.apache.commons.math.stat.descriptive.moment.StandardDeviation();
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(std.getResult()));
        std.increment(1.0);
        junit.framework.Assert.assertEquals(0.0, std.getResult(), 0);
    }

    /**
     * Test population version of variance
     */
    public void testPopulation() {
        double[] values = new double[]{ -1.0, 3.1, 4.0, -2.1, 22.0, 11.7, 3.0, 14.0 };
        double sigma = populationStandardDeviation(values);
        org.apache.commons.math.stat.descriptive.moment.SecondMoment m = new org.apache.commons.math.stat.descriptive.moment.SecondMoment();
        m.evaluate(values);// side effect is to add values
        org.apache.commons.math.stat.descriptive.moment.StandardDeviation s1 = new org.apache.commons.math.stat.descriptive.moment.StandardDeviation();
        s1.setBiasCorrected(false);
        junit.framework.Assert.assertEquals(sigma, s1.evaluate(values), 1.0E-14);
        s1.incrementAll(values);
        junit.framework.Assert.assertEquals(sigma, s1.getResult(), 1.0E-14);
        s1 = new org.apache.commons.math.stat.descriptive.moment.StandardDeviation(false, m);
        junit.framework.Assert.assertEquals(sigma, s1.getResult(), 1.0E-14);
        s1 = new org.apache.commons.math.stat.descriptive.moment.StandardDeviation(false);
        junit.framework.Assert.assertEquals(sigma, s1.evaluate(values), 1.0E-14);
        s1.incrementAll(values);
        junit.framework.Assert.assertEquals(sigma, s1.getResult(), 1.0E-14);
    }

    /**
     * Definitional formula for population standard deviation
     */
    protected double populationStandardDeviation(double[] v) {
        double mean = new org.apache.commons.math.stat.descriptive.moment.Mean().evaluate(v);
        double sum = 0;
        for (int i = 0; i < (v.length); i++) {
            sum += ((v[i]) - mean) * ((v[i]) - mean);
        }
        return java.lang.Math.sqrt((sum / (v.length)));
    }

}