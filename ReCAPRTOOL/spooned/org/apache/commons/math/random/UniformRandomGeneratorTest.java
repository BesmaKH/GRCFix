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
package org.apache.commons.math.random; public class UniformRandomGeneratorTest extends junit.framework.TestCase {     public UniformRandomGeneratorTest(java.lang.String name) {         super(name);}
    public void testMeanAndStandardDeviation() {
        org.apache.commons.math.random.RandomGenerator rg = new org.apache.commons.math.random.JDKRandomGenerator();
        rg.setSeed(17399225432L);
        org.apache.commons.math.random.UniformRandomGenerator generator = new org.apache.commons.math.random.UniformRandomGenerator(rg);
        double[] sample = new double[10000];
        for (int i = 0; i < (sample.length); ++i) {
            sample[i] = generator.nextNormalizedDouble();
        }
        junit.framework.Assert.assertEquals(0.0, org.apache.commons.math.stat.StatUtils.mean(sample), 0.07);
        junit.framework.Assert.assertEquals(1.0, org.apache.commons.math.stat.StatUtils.variance(sample), 0.02);
    }

}