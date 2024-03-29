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
package org.apache.commons.math.ode.events;





/**
 * This exception is made available to users to report
 * the error conditions that are triggered by {@link EventHandler}
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class EventException extends org.apache.commons.math.MathException {

    /**
     * Serialization UID.
     */     private static final long serialVersionUID = -898215297400035290L;
    /**
     * Simple constructor.
     * Build an exception by translating and formating a message
     *
     * @param specifier
     * 		format specifier (to be translated)
     * @param parts
     * 		to insert in the format (no translation)
     * @deprecated as of 2.2 replaced by {@link #EventException(Localizable, Object...)}
     */     @java.lang.Deprecated     public EventException(final java.lang.String specifier, final java.lang.Object... parts) {         super(specifier, parts);}

    /**
     * Simple constructor.
     * Build an exception by translating and formating a message
     *
     * @param specifier
     * 		format specifier (to be translated)
     * @param parts
     * 		to insert in the format (no translation)
     * @since 2.2
     */     public EventException(final org.apache.commons.math.util.Localizable specifier, final java.lang.Object... parts) {         super(specifier, parts);}
    /**
     * Create an exception with a given root cause.
     *
     * @param cause
     * 		the exception or error that caused this exception to be thrown
     */     public EventException(final java.lang.Throwable cause) {         super(cause);
    }

}