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
 * Class for constructing localized messages.
 *
 * @since 2.2
 * @version $Revision$ $Date$
 */
public class MessageFactory {
    /**
     * Class contains only static methods.
     */
    private MessageFactory() {}

    /**
     * Builds a message string by from a pattern and its arguments.
     *
     * @param locale
     * 		Locale in which the message should be translated.
     * @param pattern
     * 		Format specifier.
     * @param arguments
     * 		Format arguments.
     * @return a message string.
     * @since 2.2
     */     public static java.lang.String buildMessage(java.util.Locale locale, org.apache.commons.math.util.Localizable pattern, java.lang.Object... arguments) {
        final java.lang.String locPattern = pattern.getLocalizedString(locale);
        return new java.text.MessageFormat(locPattern, locale).format(arguments);
    }
}