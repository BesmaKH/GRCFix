/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.commons.math.stat.descriptive;


/**
 * Test cases for the {@link SynchronizedMultivariateSummaryStatisticsTest} class.
 *
 * @version $Revision$ $Date: 2007-08-16 15:36:33 -0500 (Thu, 16 Aug
 * 2007) $
 */ public final class SynchronizedMultivariateSummaryStatisticsTest extends org.apache.commons.math.stat.descriptive.MultivariateSummaryStatisticsTest {

    public SynchronizedMultivariateSummaryStatisticsTest(java.lang.String name) {
        super(name);
    }

    @java.lang.Override
    protected org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics createMultivariateSummaryStatistics(int k, boolean isCovarianceBiasCorrected) {
        return new org.apache.commons.math.stat.descriptive.SynchronizedMultivariateSummaryStatistics(k, isCovarianceBiasCorrected);
    }

}