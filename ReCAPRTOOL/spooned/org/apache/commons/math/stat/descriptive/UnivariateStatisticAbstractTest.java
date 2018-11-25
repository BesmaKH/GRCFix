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
 * s * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math.stat.descriptive;










/**
 * Test cases for the {@link UnivariateStatistic} class.
 *
 * @version $Revision$ $Date$
 */ public abstract class UnivariateStatisticAbstractTest extends junit.framework.TestCase {

    protected double mean = 12.404545454545454;
    protected double geoMean = 12.070589161633011;

    protected double var = 10.00235930735931;
    protected double std = java.lang.Math.sqrt(var);
    protected double skew = 1.43742372919619;
    protected double kurt = 2.3771912648047;

    protected double min = 8.2;
    protected double max = 21.0;
    protected double median = 12.0;
    protected double percentile5 = 8.29;
    protected double percentile95 = 20.82;

    protected double product = 6.280964005638334E23;
    protected double sumLog = 54.796980611645154;
    protected double sumSq = 3595.25;
    protected double sum = 272.9;
    protected double secondMoment = 210.04954545454547;
    protected double thirdMoment = 868.0906859504136;
    protected double fourthMoment = 9244.080993773481;


    protected double weightedMean = 12.366995073891626;
    protected double weightedVar = 9.974760968886391;
    protected double weightedStd = java.lang.Math.sqrt(weightedVar);
    protected double weightedProduct = 8.517647448765288E21;
    protected double weightedSum = 251.05;

    protected double tolerance = 1.0E-11;

    protected double[] testArray = 
    new double[]{ 12.5, 12.0, 11.8, 14.2, 14.9, 14.5, 21.0, 8.2, 10.3, 11.3, 
    14.1, 9.9, 12.2, 12.0, 12.1, 11.0, 19.8, 11.0, 10.0, 8.8, 
    9.0, 12.3 };

    protected double[] testWeightsArray = 
    new double[]{ 1.5, 0.8, 1.2, 0.4, 0.8, 1.8, 1.2, 1.1, 1.0, 0.7, 
    1.3, 0.6, 0.7, 1.3, 0.7, 1.0, 0.4, 0.1, 1.4, 0.9, 
    1.1, 0.3 };

    protected double[] identicalWeightsArray = 
    new double[]{ 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 
    0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 
    0.5, 0.5 };

    protected double[] unitWeightsArray = 
    new double[]{ 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 
    1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 
    1.0, 1.0 };


    public UnivariateStatisticAbstractTest(java.lang.String name) {
        super(name);
    }

    public abstract org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic();

    public abstract double expectedValue();

    public double getTolerance() {
        return tolerance;
    }

    public void testEvaluation() throws java.lang.Exception {
        junit.framework.Assert.assertEquals(
        expectedValue(), 
        getUnivariateStatistic().evaluate(testArray), 
        getTolerance());
    }

    public void testCopy() throws java.lang.Exception {
        org.apache.commons.math.stat.descriptive.UnivariateStatistic original = getUnivariateStatistic();
        org.apache.commons.math.stat.descriptive.UnivariateStatistic copy = original.copy();
        junit.framework.Assert.assertEquals(
        expectedValue(), 
        copy.evaluate(testArray), 
        getTolerance());
    }

    /**
     * Tests consistency of weighted statistic computation.
     * For statistics that support weighted evaluation, this test case compares
     * the result of direct computation on an array with repeated values with
     * a weighted computation on the corresponding (shorter) array with each
     * value appearing only once but with a weight value equal to its multiplicity
     * in the repeating array.
     */

    public void testWeightedConsistency() throws java.lang.Exception {

        // See if this statistic computes weighted statistics
        // If not, skip this test
        org.apache.commons.math.stat.descriptive.UnivariateStatistic statistic = getUnivariateStatistic();
        if (!(statistic instanceof org.apache.commons.math.stat.descriptive.WeightedEvaluation)) {
            return;
        }

        // Create arrays of values and corresponding integral weights
        // and longer array with values repeated according to the weights
        final int len = 10;// length of values array
        final double mu = 0;// mean of test data
        final double sigma = 5;// std dev of test data
        double[] values = new double[len];
        double[] weights = new double[len];
        org.apache.commons.math.random.RandomData randomData = new org.apache.commons.math.random.RandomDataImpl();

        // Fill weights array with random int values between 1 and 5
        int[] intWeights = new int[len];
        for (int i = 0; i < len; i++) {
            intWeights[i] = randomData.nextInt(1, 5);
            weights[i] = intWeights[i];
        }

        // Fill values array with random data from N(mu, sigma)
        // and fill valuesList with values from values array with
        // values[i] repeated weights[i] times, each i
        java.util.List<java.lang.Double> valuesList = new java.util.ArrayList<java.lang.Double>();
        for (int i = 0; i < len; i++) {
            double value = randomData.nextGaussian(mu, sigma);
            values[i] = value;
            for (int j = 0; j < (intWeights[i]); j++) {
                valuesList.add(new java.lang.Double(value));
            }
        }

        // Dump valuesList into repeatedValues array
        int sumWeights = valuesList.size();
        double[] repeatedValues = new double[sumWeights];
        for (int i = 0; i < sumWeights; i++) {
            repeatedValues[i] = valuesList.get(i);
        }

        // Compare result of weighted statistic computation with direct computation
        // on array of repeated values
        org.apache.commons.math.stat.descriptive.WeightedEvaluation weightedStatistic = ((org.apache.commons.math.stat.descriptive.WeightedEvaluation) (statistic));
        org.apache.commons.math.TestUtils.assertRelativelyEquals(statistic.evaluate(repeatedValues), 
        weightedStatistic.evaluate(values, weights, 0, values.length), 
        1.0E-13);

        // Check consistency of weighted evaluation methods
        junit.framework.Assert.assertEquals(weightedStatistic.evaluate(values, weights, 0, values.length), 
        weightedStatistic.evaluate(values, weights), java.lang.Double.MIN_VALUE);

    }

}