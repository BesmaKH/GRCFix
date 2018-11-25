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
 * Auxillary class for testing purposes.
 *
 * @version $Revision$ $Date$
 */
public class Expm1Function implements org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction {

    public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {
        // Math.expm1() is available in jdk 1.5 but not in jdk 1.4.2.
        return (java.lang.Math.exp(x)) - 1.0;
    }

    public org.apache.commons.math.analysis.UnivariateRealFunction derivative() {
        return new org.apache.commons.math.analysis.UnivariateRealFunction() {
            public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {
                return java.lang.Math.exp(x);
            }
        };
    }
}