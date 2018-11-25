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
 * Test cases for the {@link FirstMoment} class.
 *
 * @version $Revision$ $Date$
 */ public class FirstMomentTest extends org.apache.commons.math.stat.descriptive.StorelessUnivariateStatisticAbstractTest {

    /**
     * descriptive statistic.
     */     protected org.apache.commons.math.stat.descriptive.moment.FirstMoment stat;
    /**
     *
     *
     * @param name
     * 		
     */     public FirstMomentTest(java.lang.String name) {         super(name);}

    /**
     *
     *
     * @see org.apache.commons.math.stat.descriptive.UnivariateStatisticAbstractTest#getUnivariateStatistic()
     */     @java.lang.Override     public org.apache.commons.math.stat.descriptive.UnivariateStatistic getUnivariateStatistic() {
        return new org.apache.commons.math.stat.descriptive.moment.FirstMoment();
    }

    /**
     *
     *
     * @see org.apache.commons.math.stat.descriptive.UnivariateStatisticAbstractTest#expectedValue()
     */     @java.lang.Override     public double expectedValue() {
        return this.mean;
    }

}