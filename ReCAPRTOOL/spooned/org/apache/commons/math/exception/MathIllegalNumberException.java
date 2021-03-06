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
 * Base class for exceptions raised by a wrong number.
 * This class is not intended to be instantiated directly: it should serve
 * as a base class to create all the exceptions that are raised because some
 * precondition is violated by a number argument.
 *
 * @since 2.2
 * @version $Revision$ $Date$
 */
public class MathIllegalNumberException extends org.apache.commons.math.exception.MathIllegalArgumentException {
    /**
     * Requested.
     */     private final java.lang.Number argument;
    /**
     * Construct an exception.
     *
     * @param specific
     * 		Localizable pattern.
     * @param general
     * 		Localizable pattern.
     * @param arguments
     * 		Arguments. The first element must be the requested
     * 		value that raised the exception.
     */     protected MathIllegalNumberException(org.apache.commons.math.util.Localizable specific, org.apache.commons.math.util.Localizable general, java.lang.Number wrong, java.lang.Object... arguments) 
    {
        super(specific, general, wrong, arguments);
        argument = wrong;
    }

    /**
     * Construct an exception.
     *
     * @param general
     * 		Localizable pattern.
     * @param arguments
     * 		Arguments. The first element must be the requested
     * 		value that raised the exception.
     */     protected MathIllegalNumberException(org.apache.commons.math.util.Localizable general, java.lang.Number wrong, java.lang.Object... arguments) 
    {
        super(general, wrong, arguments);
        argument = wrong;
    }

    /**
     *
     *
     * @return the requested value.
     */     public java.lang.Number getArgument() {         return argument;
    }
}