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
package org.apache.commons.math.distribution;

/**
 * F-Distribution.
 *
 * <p>
 * References:
 * <ul>
 * <li><a href="http://mathworld.wolfram.com/F-Distribution.html">
 * F-Distribution</a></li>
 * </ul>
 * </p>
 *
 * @version $Revision$ $Date$
 */
public interface FDistribution extends org.apache.commons.math.distribution.ContinuousDistribution {
    /**
     * Modify the numerator degrees of freedom.
     *
     * @param degreesOfFreedom
     * 		the new numerator degrees of freedom.
     * @deprecated as of v2.1
     */     @java.lang.Deprecated     void setNumeratorDegreesOfFreedom(double degreesOfFreedom);

    /**
     * Access the numerator degrees of freedom.
     *
     * @return the numerator degrees of freedom.
     */     double getNumeratorDegreesOfFreedom();

    /**
     * Modify the denominator degrees of freedom.
     *
     * @param degreesOfFreedom
     * 		the new denominator degrees of freedom.
     * @deprecated as of v2.1
     */     @java.lang.Deprecated     void setDenominatorDegreesOfFreedom(double degreesOfFreedom);

    /**
     * Access the denominator degrees of freedom.
     *
     * @return the denominator degrees of freedom.
     */     double getDenominatorDegreesOfFreedom();}
