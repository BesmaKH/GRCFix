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
package org.apache.commons.math.exception;




/**
 * Exception to be thrown when a number is too small.
 *
 * @since 2.2
 * @version $Revision$ $Date$
 */
public class NumberIsTooSmallException extends org.apache.commons.math.exception.MathIllegalNumberException {
    /**
     * Higher bound.
     */
    private final java.lang.Number min;
    /**
     * Whether the maximum is included in the allowed range.
     */
    private final boolean boundIsAllowed;

    /**
     * Construct the exception.
     *
     * @param wrong
     * 		Value that is smaller than the minimum.
     * @param min
     * 		minimum.
     * @param boundIsAllowed
     * 		Whether {@code min} is included in the allowed range.
     */     public NumberIsTooSmallException(java.lang.Number wrong, java.lang.Number min, boolean boundIsAllowed) {
        this(null, wrong, min, boundIsAllowed);
    }

    /**
     * Construct the exception with a specific context.
     *
     * @param specific
     * 		Specific contexte pattern .
     * @param wrong
     * 		Value that is smaller than the minimum.
     * @param min
     * 		minimum.
     * @param boundIsAllowed
     * 		Whether {@code min} is included in the allowed range.
     */     public NumberIsTooSmallException(org.apache.commons.math.util.Localizable specific, java.lang.Number wrong, java.lang.Number min, boolean boundIsAllowed) {
        super(specific, 
        (boundIsAllowed ? 
        org.apache.commons.math.util.LocalizedFormats.NUMBER_TOO_SMALL : 
        org.apache.commons.math.util.LocalizedFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED), 
        wrong, min);

        this.min = min;
        this.boundIsAllowed = boundIsAllowed;
    }

    /**
     *
     *
     * @return {@code true} if the minimum is included in the allowed range.
     */     public boolean getBoundIsAllowed() {         return boundIsAllowed;
    }

    /**
     *
     *
     * @return the minimum.
     */     public java.lang.Number getMin() {         return min;
    }
}