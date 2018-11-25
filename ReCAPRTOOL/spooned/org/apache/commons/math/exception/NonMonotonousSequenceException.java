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
 * Exception to be thrown when the a sequence of values is not monotonously
 * increasing or decreasing.
 *
 * @since 2.2
 * @version $Revision$ $Date$
 */
public class NonMonotonousSequenceException extends org.apache.commons.math.exception.MathIllegalNumberException {
    /**
     * Direction (positive for increasing, negative for decreasing).
     */
    private final org.apache.commons.math.util.MathUtils.Order.Direction direction;
    /**
     * Whether the sequence must be strictly increasing or decreasing.
     */
    private final boolean strict;
    /**
     * Index of the wrong value.
     */
    private final int index;
    /**
     * Previous value.
     */
    private final java.lang.Number previous;

    /**
     * Construct the exception.
     * This constructor uses default values assuming that the sequence should
     * have been strictly increasing.
     *
     * @param wrong
     * 		Value that did not match the requirements.
     * @param previous
     * 		Previous value in the sequence.
     * @param index
     * 		Index of the value that did not match the requirements.
     */     public NonMonotonousSequenceException(java.lang.Number wrong, java.lang.Number previous, int index) {
        this(wrong, previous, index, org.apache.commons.math.util.MathUtils.Order.Direction.INCREASING, true);
    }

    /**
     * Construct the exception.
     *
     * @param wrong
     * 		Value that did not match the requirements.
     * @param previous
     * 		Previous value in the sequence.
     * @param index
     * 		Index of the value that did not match the requirements.
     * @param direction
     * 		Strictly positive for a sequence required to be
     * 		increasing, negative (or zero) for a decreasing sequence.
     * @param strict
     * 		Whether the sequence must be strictly increasing or
     * 		decreasing.
     */     public NonMonotonousSequenceException(java.lang.Number wrong, java.lang.Number previous, int index, org.apache.commons.math.util.MathUtils.Order.Direction direction, boolean strict) {
        super((direction == (org.apache.commons.math.util.MathUtils.Order.Direction.INCREASING) ? 
        strict ? 
        org.apache.commons.math.util.LocalizedFormats.NOT_STRICTLY_INCREASING_SEQUENCE : 
        org.apache.commons.math.util.LocalizedFormats.NOT_INCREASING_SEQUENCE : 
        strict ? 
        org.apache.commons.math.util.LocalizedFormats.NOT_STRICTLY_DECREASING_SEQUENCE : 
        org.apache.commons.math.util.LocalizedFormats.NOT_DECREASING_SEQUENCE), 
        wrong, previous, index, (index - 1));

        this.direction = direction;
        this.strict = strict;
        this.index = index;
        this.previous = previous;
    }

    /**
     *
     *
     * @return the order direction.
     */     public org.apache.commons.math.util.MathUtils.Order.Direction getDirection() {         return direction;
    }
    /**
     *
     *
     * @return {@code true} is the sequence should be strictly monotonous.
     */     public boolean getStrict() {         return strict;
    }
    /**
     * Get the index of the wrong value.
     *
     * @return the current index.
     */
    public int getIndex() {
        return index;
    }
    /**
     *
     *
     * @return the previous value.
     */     public java.lang.Number getPrevious() {         return previous;
    }
}