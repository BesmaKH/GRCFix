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
 * Dummy implementation of the {@link Localizable} interface, without localization.
 *
 * @version $Revision$ $Date$
 * @since 2.2
 */
public class DummyLocalizable implements org.apache.commons.math.util.Localizable {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 8843275624471387299L;
    /**
     * Source string.
     */     private final java.lang.String source;
    /**
     * Simple constructor.
     */     public DummyLocalizable(final java.lang.String source) {         this.source = source;
    }

    /**
     * {@inheritDoc}
     */     public java.lang.String getSourceString() {         return source;
    }

    /**
     * {@inheritDoc}
     */     public java.lang.String getLocalizedString(java.util.Locale locale) {         return source;
    }

    /**
     * {@inheritDoc}
     */     public java.lang.String toString() {         return source;
    }

}