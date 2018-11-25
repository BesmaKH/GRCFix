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
 * Implements one-way ANOVA statistics defined in the {@link OneWayAnovaImpl}
 * interface.
 *
 * <p>Uses the
 * {@link org.apache.commons.math.distribution.FDistribution
 *  commons-math F Distribution implementation} to estimate exact p-values.</p>
 *
 * <p>This implementation is based on a description at
 * http://faculty.vassar.edu/lowry/ch13pt1.html</p>
 * <pre>
 * Abbreviations: bg = between groups,
 *                wg = within groups,
 *                ss = sum squared deviations
 * </pre>
 *
 * @since 1.2
 * @version $Revision$ $Date$
 */
public class OneWayAnovaImpl implements org.apache.commons.math.stat.inference.OneWayAnova {

    /**
     * Default constructor.
     */
    public OneWayAnovaImpl() {
    }

    /**
     * {@inheritDoc}<p>
     * This implementation computes the F statistic using the definitional
     * formula<pre>
     *   F = msbg/mswg</pre>
     * where<pre>
     *  msbg = between group mean square
     *  mswg = within group mean square</pre>
     * are as defined <a href="http://faculty.vassar.edu/lowry/ch13pt1.html">
     * here</a></p>
     */
    public double anovaFValue(java.util.Collection<double[]> categoryData) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        org.apache.commons.math.stat.inference.OneWayAnovaImpl.AnovaStats a = anovaStats(categoryData);
        return a.F;
    }

    /**
     * {@inheritDoc}<p>
     * This implementation uses the
     * {@link org.apache.commons.math.distribution.FDistribution
     * commons-math F Distribution implementation} to estimate the exact
     * p-value, using the formula<pre>
     *   p = 1 - cumulativeProbability(F)</pre>
     * where <code>F</code> is the F value and <code>cumulativeProbability</code>
     * is the commons-math implementation of the F distribution.</p>
     */
    public double anovaPValue(java.util.Collection<double[]> categoryData) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        org.apache.commons.math.stat.inference.OneWayAnovaImpl.AnovaStats a = anovaStats(categoryData);
        org.apache.commons.math.distribution.FDistribution fdist = new org.apache.commons.math.distribution.FDistributionImpl(a.dfbg, a.dfwg);
        return 1.0 - (fdist.cumulativeProbability(a.F));
    }

    /**
     * {@inheritDoc}<p>
     * This implementation uses the
     * {@link org.apache.commons.math.distribution.FDistribution
     * commons-math F Distribution implementation} to estimate the exact
     * p-value, using the formula<pre>
     *   p = 1 - cumulativeProbability(F)</pre>
     * where <code>F</code> is the F value and <code>cumulativeProbability</code>
     * is the commons-math implementation of the F distribution.</p>
     * <p>True is returned iff the estimated p-value is less than alpha.</p>
     */
    public boolean anovaTest(java.util.Collection<double[]> categoryData, double alpha) throws 
    java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        if ((alpha <= 0) || (alpha > 0.5)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, 
            alpha, 0, 0.5);
        }
        return (anovaPValue(categoryData)) < alpha;
    }


    /**
     * This method actually does the calculations (except P-value).
     *
     * @param categoryData
     * 		<code>Collection</code> of <code>double[]</code>
     * 		arrays each containing data for one category
     * @return computed AnovaStats
     * @throws IllegalArgumentException
     * 		if categoryData does not meet
     * 		preconditions specified in the interface definition
     * @throws MathException
     * 		if an error occurs computing the Anova stats
     */     private org.apache.commons.math.stat.inference.OneWayAnovaImpl.AnovaStats anovaStats(java.util.Collection<double[]> categoryData) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        // check if we have enough categories
        if ((categoryData.size()) < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED, 
            categoryData.size());
        }

        // check if each category has enough data and all is double[]
        for (double[] array : categoryData) {
            if ((array.length) <= 1) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, 
                array.length);
            }
        }

        int dfwg = 0;
        double sswg = 0;
        org.apache.commons.math.stat.descriptive.summary.Sum totsum = new org.apache.commons.math.stat.descriptive.summary.Sum();
        org.apache.commons.math.stat.descriptive.summary.SumOfSquares totsumsq = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        int totnum = 0;

        for (double[] data : categoryData) {

            org.apache.commons.math.stat.descriptive.summary.Sum sum = new org.apache.commons.math.stat.descriptive.summary.Sum();
            org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumsq = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
            int num = 0;

            for (int i = 0; i < (data.length); i++) {
                double val = data[i];

                // within category
                num++;
                sum.increment(val);
                sumsq.increment(val);

                // for all categories
                totnum++;
                totsum.increment(val);
                totsumsq.increment(val);
            }
            dfwg += num - 1;
            double ss = (sumsq.getResult()) - (((sum.getResult()) * (sum.getResult())) / num);
            sswg += ss;
        }
        double sst = (totsumsq.getResult()) - (((totsum.getResult()) * 
        (totsum.getResult())) / totnum);
        double ssbg = sst - sswg;
        int dfbg = (categoryData.size()) - 1;
        double msbg = ssbg / dfbg;
        double mswg = sswg / dfwg;
        double F = msbg / mswg;

        return new org.apache.commons.math.stat.inference.OneWayAnovaImpl.AnovaStats(dfbg, dfwg, F);
    }

    /**
     * Convenience class to pass dfbg,dfwg,F values around within AnovaImpl.
     * No get/set methods provided.
     */
    private static class AnovaStats {

        /**
         * Degrees of freedom in numerator (between groups).
         */         private int dfbg;
        /**
         * Degrees of freedom in denominator (within groups).
         */         private int dfwg;
        /**
         * Statistic.
         */         private double F;
        /**
         * Constructor
         *
         * @param dfbg
         * 		degrees of freedom in numerator (between groups)
         * @param dfwg
         * 		degrees of freedom in denominator (within groups)
         * @param F
         * 		statistic
         */         private AnovaStats(int dfbg, int dfwg, double F) {             this.dfbg = dfbg;             this.dfwg = dfwg;             this.F = F;
        }
    }

}