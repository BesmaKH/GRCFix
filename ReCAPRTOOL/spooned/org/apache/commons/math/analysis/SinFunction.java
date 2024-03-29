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
 * Auxillary class for testing solvers.
 *
 * The function is extraordinarily well behaved around zero roots: it
 * has an inflection point there (second order derivative is zero),
 * which means linear approximation (Regula Falsi) will converge
 * quadratically.
 *
 * @version $Revision$ $Date$
 */
public class SinFunction implements org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction {

    /* Evaluate sinus fuction.
    @see org.apache.commons.math.UnivariateRealFunction#value(double)
     */
    public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {
        return java.lang.Math.sin(x);
    }

    /* First derivative of sinus function */

    public org.apache.commons.math.analysis.UnivariateRealFunction derivative() {
        return new org.apache.commons.math.analysis.UnivariateRealFunction() {
            public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {
                return java.lang.Math.cos(x);
            }
        };
    }

}