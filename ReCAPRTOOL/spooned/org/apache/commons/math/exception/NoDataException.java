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
 * Exception to be thrown when the required data is missing.
 *
 * @since 2.2
 * @version $Revision$ $Date$
 */
public class NoDataException extends org.apache.commons.math.exception.MathIllegalArgumentException {
    /**
     * Construct the exception.
     */
    public NoDataException() {
        this(null);
    }
    /**
     * Construct the exception with a specific context.
     *
     * @param specific
     * 		Contextual information on what caused the exception.
     */     public NoDataException(org.apache.commons.math.util.Localizable specific) {
        super(specific, org.apache.commons.math.util.LocalizedFormats.NO_DATA, null);
    }
}