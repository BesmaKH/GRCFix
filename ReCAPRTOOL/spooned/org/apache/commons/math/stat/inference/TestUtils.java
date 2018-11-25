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
package org.apache.commons.math.stat.inference;





/**
 * A collection of static methods to create inference test instances or to
 * perform inference tests.
 *
 * @since 1.1
 * @version $Revision$ $Date$
 */































































































































































































































































































































































































// CHECKSTYLE: resume JavadocMethodCheck public class TestUtils {     /**
     * Singleton TTest instance using default implementation.
     */     private static org.apache.commons.math.stat.inference.TTest tTest = new org.apache.commons.math.stat.inference.TTestImpl();     /**
     * Singleton ChiSquareTest instance using default implementation.
     */     private static org.apache.commons.math.stat.inference.ChiSquareTest chiSquareTest = new org.apache.commons.math.stat.inference.ChiSquareTestImpl();     /**
     * Singleton ChiSquareTest instance using default implementation.
     */     private static org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest unknownDistributionChiSquareTest = new org.apache.commons.math.stat.inference.ChiSquareTestImpl();     /**
     * Singleton OneWayAnova instance using default implementation.
     */     private static org.apache.commons.math.stat.inference.OneWayAnova oneWayAnova = new org.apache.commons.math.stat.inference.OneWayAnovaImpl();     /**
     * Prevent instantiation.
     */     protected TestUtils() {         super();}     /**
     * Set the (singleton) TTest instance.
     *
     * @param chiSquareTest
     * 		the new instance to use
     * @since 1.2
     */     public static void setChiSquareTest(org.apache.commons.math.stat.inference.TTest chiSquareTest) {         org.apache.commons.math.stat.inference.TestUtils.tTest = chiSquareTest;}     /**
     * Return a (singleton) TTest instance.  Does not create a new instance.
     *
     * @return a TTest instance
     */     public static org.apache.commons.math.stat.inference.TTest getTTest() {         return org.apache.commons.math.stat.inference.TestUtils.tTest;}     /**
     * Set the (singleton) ChiSquareTest instance.
     *
     * @param chiSquareTest
     * 		the new instance to use
     * @since 1.2
     */     public static void setChiSquareTest(org.apache.commons.math.stat.inference.ChiSquareTest chiSquareTest) {         org.apache.commons.math.stat.inference.TestUtils.chiSquareTest = chiSquareTest;}     /**
     * Return a (singleton) ChiSquareTest instance.  Does not create a new instance.
     *
     * @return a ChiSquareTest instance
     */     public static org.apache.commons.math.stat.inference.ChiSquareTest getChiSquareTest() {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest;}     /**
     * Set the (singleton) UnknownDistributionChiSquareTest instance.
     *
     * @param unknownDistributionChiSquareTest
     * 		the new instance to use
     * @since 1.2
     */     public static void setUnknownDistributionChiSquareTest(org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest unknownDistributionChiSquareTest) {         org.apache.commons.math.stat.inference.TestUtils.unknownDistributionChiSquareTest = unknownDistributionChiSquareTest;}     /**
     * Return a (singleton) UnknownDistributionChiSquareTest instance.  Does not create a new instance.
     *
     * @return a UnknownDistributionChiSquareTest instance
     */     public static org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest getUnknownDistributionChiSquareTest() {         return org.apache.commons.math.stat.inference.TestUtils.unknownDistributionChiSquareTest;}     /**
     * Set the (singleton) OneWayAnova instance
     *
     * @param oneWayAnova
     * 		the new instance to use
     * @since 1.2
     */     public static void setOneWayAnova(org.apache.commons.math.stat.inference.OneWayAnova oneWayAnova) {         org.apache.commons.math.stat.inference.TestUtils.oneWayAnova = oneWayAnova;}     /**
     * Return a (singleton) OneWayAnova instance.  Does not create a new instance.
     *
     * @return a OneWayAnova instance
     * @since 1.2
     */     public static org.apache.commons.math.stat.inference.OneWayAnova getOneWayAnova() {         return org.apache.commons.math.stat.inference.TestUtils.oneWayAnova;}     // CHECKSTYLE: stop JavadocMethodCheck     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#homoscedasticT(double[], double[])
     */     public static double homoscedasticT(double[] sample1, double[] sample2) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.homoscedasticT(sample1, sample2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#homoscedasticT(org.apache.commons.math.stat.descriptive.StatisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary)
     */     public static double homoscedasticT(org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats1, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats2) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.homoscedasticT(sampleStats1, sampleStats2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#homoscedasticTTest(double[], double[], double)
     */     public static boolean homoscedasticTTest(double[] sample1, double[] sample2, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.homoscedasticTTest(sample1, sample2, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#homoscedasticTTest(double[], double[])
     */     public static double homoscedasticTTest(double[] sample1, double[] sample2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.homoscedasticTTest(sample1, sample2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#homoscedasticTTest(org.apache.commons.math.stat.descriptive.StatisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary)
     */     public static double homoscedasticTTest(org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats1, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.homoscedasticTTest(sampleStats1, sampleStats2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#pairedT(double[], double[])
     */     public static double pairedT(double[] sample1, double[] sample2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.pairedT(sample1, sample2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#pairedTTest(double[], double[], double)
     */     public static boolean pairedTTest(double[] sample1, double[] sample2, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.pairedTTest(sample1, sample2, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#pairedTTest(double[], double[])
     */     public static double pairedTTest(double[] sample1, double[] sample2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.pairedTTest(sample1, sample2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#t(double, double[])
     */     public static double t(double mu, double[] observed) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.t(mu, observed);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#t(double, org.apache.commons.math.stat.descriptive.StatisticalSummary)
     */     public static double t(double mu, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.t(mu, sampleStats);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#t(double[], double[])
     */     public static double t(double[] sample1, double[] sample2) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.t(sample1, sample2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#t(org.apache.commons.math.stat.descriptive.StatisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary)
     */     public static double t(org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats1, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats2) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.t(sampleStats1, sampleStats2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(double, double[], double)
     */     public static boolean tTest(double mu, double[] sample, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(mu, sample, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(double, double[])
     */     public static double tTest(double mu, double[] sample) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(mu, sample);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(double, org.apache.commons.math.stat.descriptive.StatisticalSummary, double)
     */     public static boolean tTest(double mu, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(mu, sampleStats, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(double, org.apache.commons.math.stat.descriptive.StatisticalSummary)
     */     public static double tTest(double mu, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(mu, sampleStats);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(double[], double[], double)
     */     public static boolean tTest(double[] sample1, double[] sample2, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(sample1, sample2, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(double[], double[])
     */     public static double tTest(double[] sample1, double[] sample2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(sample1, sample2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary, double)
     */     public static boolean tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats1, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats2, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(sampleStats1, sampleStats2, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.TTest#tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary)
     */     public static double tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats1, org.apache.commons.math.stat.descriptive.StatisticalSummary sampleStats2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.tTest.tTest(sampleStats1, sampleStats2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.ChiSquareTest#chiSquare(double[], long[])
     */     public static double chiSquare(double[] expected, long[] observed) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest.chiSquare(expected, observed);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.ChiSquareTest#chiSquare(long[][])
     */     public static double chiSquare(long[][] counts) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest.chiSquare(counts);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.ChiSquareTest#chiSquareTest(double[], long[], double)
     */     public static boolean chiSquareTest(double[] expected, long[] observed, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest.chiSquareTest(expected, observed, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.ChiSquareTest#chiSquareTest(double[], long[])
     */     public static double chiSquareTest(double[] expected, long[] observed) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest.chiSquareTest(expected, observed);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.ChiSquareTest#chiSquareTest(long[][], double)
     */     public static boolean chiSquareTest(long[][] counts, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest.chiSquareTest(counts, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.ChiSquareTest#chiSquareTest(long[][])
     */     public static double chiSquareTest(long[][] counts) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.chiSquareTest.chiSquareTest(counts);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest#chiSquareDataSetsComparison(long[], long[])
     * @since 1.2
     */     public static double chiSquareDataSetsComparison(long[] observed1, long[] observed2) throws java.lang.IllegalArgumentException {         return org.apache.commons.math.stat.inference.TestUtils.unknownDistributionChiSquareTest.chiSquareDataSetsComparison(observed1, observed2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest#chiSquareTestDataSetsComparison(long[], long[])
     * @since 1.2
     */     public static double chiSquareTestDataSetsComparison(long[] observed1, long[] observed2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.unknownDistributionChiSquareTest.chiSquareTestDataSetsComparison(observed1, observed2);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest#chiSquareTestDataSetsComparison(long[], long[], double)
     * @since 1.2
     */     public static boolean chiSquareTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.unknownDistributionChiSquareTest.chiSquareTestDataSetsComparison(observed1, observed2, alpha);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.OneWayAnova#anovaFValue(Collection)
     * @since 1.2
     */     public static double oneWayAnovaFValue(java.util.Collection<double[]> categoryData) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.oneWayAnova.anovaFValue(categoryData);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.OneWayAnova#anovaPValue(Collection)
     * @since 1.2
     */     public static double oneWayAnovaPValue(java.util.Collection<double[]> categoryData) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.oneWayAnova.anovaPValue(categoryData);}     /**
     *
     *
     * @see org.apache.commons.math.stat.inference.OneWayAnova#anovaTest(Collection,double)
     * @since 1.2
     */     public static boolean oneWayAnovaTest(java.util.Collection<double[]> categoryData, double alpha) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {         return org.apache.commons.math.stat.inference.TestUtils.oneWayAnova.anovaTest(categoryData, alpha);}}