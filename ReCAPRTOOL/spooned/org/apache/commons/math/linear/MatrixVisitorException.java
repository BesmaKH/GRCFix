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
package org.apache.commons.math.linear;





/**
 * Thrown when a visitor encounters an error while processing a matrix entry.
 *
 * @version $Revision$ $Date$
 */ public class MatrixVisitorException extends org.apache.commons.math.MathRuntimeException {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 3814333035048617048L;
    /**
     * Constructs a new instance with specified formatted detail message.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @deprecated as of 2.2 replaced by {@link #MatrixVisitorException(Localizable, Object...)}
     */     @java.lang.Deprecated     public MatrixVisitorException(final java.lang.String pattern, final java.lang.Object[] arguments) {         super(pattern, arguments);
    }

    /**
     * Constructs a new instance with specified formatted detail message.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @since 2.2
     */     public MatrixVisitorException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object[] arguments) {         super(pattern, arguments);}

}