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
 * This class represents exceptions thrown when a matrix expected to
 * be positive definite is not.
 *
 * @since 1.2
 * @version $Revision$ $Date$
 */

public class NotPositiveDefiniteMatrixException extends org.apache.commons.math.MathException {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 4122929125438624648L;
    /**
     * Simple constructor.
     * build an exception with a default message.
     */     public NotPositiveDefiniteMatrixException() {
        super(org.apache.commons.math.util.LocalizedFormats.NOT_POSITIVE_DEFINITE_MATRIX);
    }

}