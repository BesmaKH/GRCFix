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
package org.apache.commons.math;







/**
 * Error thrown when a numerical computation exceeds its allowed
 * number of iterations.
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public class MaxIterationsExceededException extends org.apache.commons.math.ConvergenceException {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = -7821226672760574694L;
    /**
     * Maximal number of iterations allowed.
     */     private final int maxIterations;
    /**
     * Constructs an exception with a default detail message.
     *
     * @param maxIterations
     * 		maximal number of iterations allowed
     */     public MaxIterationsExceededException(final int maxIterations) {         super(org.apache.commons.math.util.LocalizedFormats.MAX_ITERATIONS_EXCEEDED, maxIterations);
        this.maxIterations = maxIterations;
    }

    /**
     * Constructs an exception with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param maxIterations
     * 		the exceeded maximal number of iterations
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @deprecated as of 2.2 replaced by {@link #MaxIterationsExceededException(int, Localizable, Object...)}
     */     @java.lang.Deprecated     public MaxIterationsExceededException(final int maxIterations, final java.lang.String pattern, final java.lang.Object... arguments) {         this(maxIterations, new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs an exception with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param maxIterations
     * 		the exceeded maximal number of iterations
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @since 2.2
     */     public MaxIterationsExceededException(final int maxIterations, final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         super(pattern, arguments);         this.maxIterations = maxIterations;
    }

    /**
     * Get the maximal number of iterations allowed.
     *
     * @return maximal number of iterations allowed
     */     public int getMaxIterations() {         return maxIterations;
    }

}