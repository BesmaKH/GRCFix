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
package org.apache.commons.math.util;




/**
 * Interface for localizable strings.
 *
 * @version $Revision$ $Date$
 * @since 2.2
 */
public interface Localizable extends java.io.Serializable {

    /**
     * Get the source (non-localized) string.
     *
     * @return source string
     */     java.lang.String getSourceString();

    /**
     * Get the localized string.
     *
     * @param locale
     * 		locale into which to get the string
     * @return localized string or the source string if no localized version is available
     */     java.lang.String getLocalizedString(java.util.Locale locale);}
