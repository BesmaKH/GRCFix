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
 * Extension of {@link MultivariateVectorialFunction} representing a differentiable
 * multivariate vectorial function.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public interface DifferentiableMultivariateVectorialFunction extends 
org.apache.commons.math.analysis.MultivariateVectorialFunction {

    /**
     * Returns the jacobian function.
     *
     * @return the jacobian function
     */     org.apache.commons.math.analysis.MultivariateMatrixFunction jacobian();}

