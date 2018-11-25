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
package org.apache.commons.math.stat.regression;








public abstract class MultipleLinearRegressionAbstractTest {

    protected org.apache.commons.math.stat.regression.MultipleLinearRegression regression;

    @org.junit.Before
    public void setUp() {
        regression = createRegression();
    }

    protected abstract org.apache.commons.math.stat.regression.MultipleLinearRegression createRegression();

    protected abstract int getNumberOfRegressors();

    protected abstract int getSampleSize();

    @org.junit.Test
    public void canEstimateRegressionParameters() {
        double[] beta = regression.estimateRegressionParameters();
        org.junit.Assert.assertEquals(getNumberOfRegressors(), beta.length);
    }

    @org.junit.Test
    public void canEstimateResiduals() {
        double[] e = regression.estimateResiduals();
        org.junit.Assert.assertEquals(getSampleSize(), e.length);
    }

    @org.junit.Test
    public void canEstimateRegressionParametersVariance() {
        double[][] variance = regression.estimateRegressionParametersVariance();
        org.junit.Assert.assertEquals(getNumberOfRegressors(), variance.length);
    }

    @org.junit.Test
    public void canEstimateRegressandVariance() {
        if ((getSampleSize()) > (getNumberOfRegressors())) {
            double variance = regression.estimateRegressandVariance();
            org.junit.Assert.assertTrue((variance > 0.0));
        }
    }

}