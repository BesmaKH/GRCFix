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
 */ public class PercentileTest extends org.apache.commons.math.stat.descriptive.UnivariateStatisticAbstractTest {

    protected org.apache.commons.math.stat.descriptive.rank.Percentile stat;

    /**
     *
     *
     * @param name
     * 		
     */     public PercentileTest(java.lang.String name) {         super(name);}

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.rank.Percentile(95.0);
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public double expectedValue() {
        return this.percentile95;
    }

    public void testHighPercentile() {
        double[] d = new double[]{ 1, 2, 3 };
        org.apache.commons.math.stat.descriptive.rank.Percentile p = new org.apache.commons.math.stat.descriptive.rank.Percentile(75);
        junit.framework.Assert.assertEquals(3.0, p.evaluate(d), 1.0E-5);
    }

    public void testPercentile() {
        double[] d = new double[]{ 1, 3, 2, 4 };
        org.apache.commons.math.stat.descriptive.rank.Percentile p = new org.apache.commons.math.stat.descriptive.rank.Percentile(30);
        junit.framework.Assert.assertEquals(1.5, p.evaluate(d), 1.0E-5);
        p.setQuantile(25);
        junit.framework.Assert.assertEquals(1.25, p.evaluate(d), 1.0E-5);
        p.setQuantile(75);
        junit.framework.Assert.assertEquals(3.75, p.evaluate(d), 1.0E-5);
        p.setQuantile(50);
        junit.framework.Assert.assertEquals(2.5, p.evaluate(d), 1.0E-5);

        // invalid percentiles
        try {
            p.evaluate(d, 0, d.length, (-1.0));
            junit.framework.Assert.fail();
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
        try {
            p.evaluate(d, 0, d.length, 101.0);
            junit.framework.Assert.fail();
        } catch (java.lang.IllegalArgumentException ex) {
            // success
        }
    }

    public void testNISTExample() {
        double[] d = new double[]{ 95.1772, 95.1567, 95.1937, 95.1959, 
        95.1442, 95.061, 95.1591, 95.1195, 95.1772, 95.0925, 95.199, 95.1682 };

        org.apache.commons.math.stat.descriptive.rank.Percentile p = new org.apache.commons.math.stat.descriptive.rank.Percentile(90);
        junit.framework.Assert.assertEquals(95.1981, p.evaluate(d), 1.0E-4);
        junit.framework.Assert.assertEquals(95.199, p.evaluate(d, 0, d.length, 100.0), 0);
    }

    public void test5() {
        org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile(5);
        junit.framework.Assert.assertEquals(this.percentile5, percentile.evaluate(testArray), getTolerance());
    }

    public void testNullEmpty() {
        org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile(50);
        double[] nullArray = null;
        double[] emptyArray = new double[]{  };
        try {
            percentile.evaluate(nullArray);
            junit.framework.Assert.fail("Expecting IllegalArgumentException for null array");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(percentile.evaluate(emptyArray)));
    }

    public void testSingleton() {
        org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile(50);
        double[] singletonArray = new double[]{ 1.0 };
        junit.framework.Assert.assertEquals(1.0, percentile.evaluate(singletonArray), 0);
        junit.framework.Assert.assertEquals(1.0, percentile.evaluate(singletonArray, 0, 1), 0);
        junit.framework.Assert.assertEquals(1.0, percentile.evaluate(singletonArray, 0, 1, 5), 0);
        junit.framework.Assert.assertEquals(1.0, percentile.evaluate(singletonArray, 0, 1, 100), 0);
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(percentile.evaluate(singletonArray, 0, 0)));
    }

    public void testSpecialValues() {
        org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile(50);
        double[] specialValues = new double[]{ 0.0, 1.0, 2.0, 3.0, 4.0, java.lang.Double.NaN };
        junit.framework.Assert.assertEquals(2.5, percentile.evaluate(specialValues), 0);
        specialValues = new double[]{ java.lang.Double.NEGATIVE_INFINITY, 1.0, 2.0, 3.0, 
        java.lang.Double.NaN, java.lang.Double.POSITIVE_INFINITY };
        junit.framework.Assert.assertEquals(2.5, percentile.evaluate(specialValues), 0);
        specialValues = new double[]{ 1.0, 1.0, java.lang.Double.POSITIVE_INFINITY, 
        java.lang.Double.POSITIVE_INFINITY };
        junit.framework.Assert.assertTrue(java.lang.Double.isInfinite(percentile.evaluate(specialValues)));
        specialValues = new double[]{ 1.0, 1.0, java.lang.Double.NaN, 
        java.lang.Double.NaN };
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(percentile.evaluate(specialValues)));
        specialValues = new double[]{ 1.0, 1.0, java.lang.Double.NEGATIVE_INFINITY, 
        java.lang.Double.NEGATIVE_INFINITY };
        // Interpolation results in NEGATIVE_INFINITY + POSITIVE_INFINITY
        junit.framework.Assert.assertTrue(java.lang.Double.isNaN(percentile.evaluate(specialValues)));
    }

    public void testSetQuantile() {
        org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile(10);
        percentile.setQuantile(100);// OK
        junit.framework.Assert.assertEquals(100, percentile.getQuantile(), 0);
        try {
            percentile.setQuantile(0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
        try {
            new org.apache.commons.math.stat.descriptive.rank.Percentile(0);
            junit.framework.Assert.fail("Expecting IllegalArgumentException");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }

}