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
package org.apache.commons.math.analysis;



/**
 * An interface representing a univariate vectorial function.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public interface UnivariateVectorialFunction {

    /**
     * Compute the value for the function.
     *
     * @param x
     * 		the point for which the function value should be computed
     * @return the value
     * @throws FunctionEvaluationException
     * 		if the function evaluation fails
     */     double[] value(double x) throws org.apache.commons.math.FunctionEvaluationException;}