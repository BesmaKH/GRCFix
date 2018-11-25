/**
 * Licensed to the Apache Software Foundation (ASF) under one
 */ /**
 * or more contributor license agreements.  See the NOTICE file
 */ /**
 * distributed with this work for additional information
 */ /**
 * regarding copyright ownership.  The ASF licenses this file
 */ /**
 * to you under the Apache License, Version 2.0 (the
 */ /**
 * "License"); you may not use this file except in compliance
 */ /**
 * with the License.  You may obtain a copy of the License at
 */ /**
 * http://www.apache.org/licenses/LICENSE-2.0
 */ /**
 * Unless required by applicable law or agreed to in writing,
 */ /**
 * software distributed under the License is distributed on an
 */ /**
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 */ /**
 * KIND, either express or implied.  See the License for the
 */ /**
 * specific language governing permissions and limitations
 */ /**
 * under the License.
 */
package org.apache.commons.math.stat.descriptive.moment; public class VectorialMeanTest extends junit.framework.TestCase {     public VectorialMeanTest(java.lang.String name) {         super(name);         points = null;
    }

    public void testMismatch() {
        try {
            new org.apache.commons.math.stat.descriptive.moment.VectorialMean(8).increment(new double[5]);
            junit.framework.Assert.fail("an exception should have been thrown");
        } catch (org.apache.commons.math.DimensionMismatchException dme) {
            junit.framework.Assert.assertEquals(5, dme.getDimension1());
            junit.framework.Assert.assertEquals(8, dme.getDimension2());
        } catch (java.lang.Exception e) {
            junit.framework.Assert.fail(("wrong exception type caught: " + (e.getClass().getName())));
        }
    }

    public void testSimplistic() throws org.apache.commons.math.DimensionMismatchException {
        org.apache.commons.math.stat.descriptive.moment.VectorialMean stat = new org.apache.commons.math.stat.descriptive.moment.VectorialMean(2);
        stat.increment(new double[]{ -1.0, 1.0 });
        stat.increment(new double[]{ 1.0, -1.0 });
        double[] mean = stat.getResult();
        junit.framework.Assert.assertEquals(0.0, mean[0], 1.0E-12);
        junit.framework.Assert.assertEquals(0.0, mean[1], 1.0E-12);
    }

    public void testBasicStats() throws org.apache.commons.math.DimensionMismatchException {

        org.apache.commons.math.stat.descriptive.moment.VectorialMean stat = new org.apache.commons.math.stat.descriptive.moment.VectorialMean(points[0].length);
        for (int i = 0; i < (points.length); ++i) {
            stat.increment(points[i]);
        }

        junit.framework.Assert.assertEquals(points.length, stat.getN());

        double[] mean = stat.getResult();
        double[] refMean = new double[]{ 1.78, 1.62, 3.12 };

        for (int i = 0; i < (mean.length); ++i) {
            junit.framework.Assert.assertEquals(refMean[i], mean[i], 1.0E-12);
        }

    }

    public void testSerial() throws org.apache.commons.math.DimensionMismatchException {
        org.apache.commons.math.stat.descriptive.moment.VectorialMean stat = new org.apache.commons.math.stat.descriptive.moment.VectorialMean(points[0].length);
        for (int i = 0; i < (points.length); ++i) {
            stat.increment(points[i]);
        }
        junit.framework.Assert.assertEquals(stat, org.apache.commons.math.TestUtils.serializeAndRecover(stat));
    }
    @java.lang.Override
    public void setUp() {
        points = new double[][]{ 
        new double[]{ 1.2, 2.3, 4.5 }, 
        new double[]{ -0.7, 2.3, 5.0 }, 
        new double[]{ 3.1, 0.0, -3.1 }, 
        new double[]{ 6.0, 1.2, 4.2 }, 
        new double[]{ -0.7, 2.3, 5.0 } };

    }

    @java.lang.Override
    public void tearDown() {
        points = null;
    }

    private double[][] points;

}