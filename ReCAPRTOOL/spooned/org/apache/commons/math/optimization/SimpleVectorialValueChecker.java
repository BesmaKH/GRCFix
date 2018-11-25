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
package org.apache.commons.math.optimization;




/**
 * Simple implementation of the {@link VectorialConvergenceChecker} interface using
 * only objective function values.
 * <p>
 * Convergence is considered to have been reached if either the relative
 * difference between the objective function values is smaller than a
 * threshold or if either the absolute difference between the objective
 * function values is smaller than another threshold for all vectors elements.
 * </p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class SimpleVectorialValueChecker implements org.apache.commons.math.optimization.VectorialConvergenceChecker {

    /**
     * Default relative threshold.
     */     private static final double DEFAULT_RELATIVE_THRESHOLD = 100 * (org.apache.commons.math.util.MathUtils.EPSILON);
    /**
     * Default absolute threshold.
     */     private static final double DEFAULT_ABSOLUTE_THRESHOLD = 100 * (org.apache.commons.math.util.MathUtils.SAFE_MIN);
    /**
     * Relative tolerance threshold.
     */     private final double relativeThreshold;
    /**
     * Absolute tolerance threshold.
     */     private final double absoluteThreshold;
    /**
     * Build an instance with default threshold.
     */     public SimpleVectorialValueChecker() {
        this.relativeThreshold = org.apache.commons.math.optimization.SimpleVectorialValueChecker.DEFAULT_RELATIVE_THRESHOLD;
        this.absoluteThreshold = org.apache.commons.math.optimization.SimpleVectorialValueChecker.DEFAULT_ABSOLUTE_THRESHOLD;
    }

    /**
     * Build an instance with a specified threshold.
     * <p>
     * In order to perform only relative checks, the absolute tolerance
     * must be set to a negative value. In order to perform only absolute
     * checks, the relative tolerance must be set to a negative value.
     * </p>
     *
     * @param relativeThreshold
     * 		relative tolerance threshold
     * @param absoluteThreshold
     * 		absolute tolerance threshold
     */     public SimpleVectorialValueChecker(final double relativeThreshold, final double absoluteThreshold) {         this.relativeThreshold = relativeThreshold;         this.absoluteThreshold = absoluteThreshold;
    }

    /**
     * {@inheritDoc}
     */     public boolean converged(final int iteration, final org.apache.commons.math.optimization.VectorialPointValuePair previous, final 
    org.apache.commons.math.optimization.VectorialPointValuePair current) {
        final double[] p = previous.getValueRef();
        final double[] c = current.getValueRef();
        for (int i = 0; i < (p.length); ++i) {
            final double pi = p[i];
            final double ci = c[i];
            final double difference = java.lang.Math.abs((pi - ci));
            final double size = java.lang.Math.max(java.lang.Math.abs(pi), java.lang.Math.abs(ci));
            if ((difference > (size * (relativeThreshold))) && 
            (difference > (absoluteThreshold))) {
                return false;
            }
        }
        return true;
    }

}