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
 * Representation of real numbers with arbitrary precision field.
 * <p>
 * This class is a singleton.
 * </p>
 *
 * @see BigReal
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class BigRealField implements java.io.Serializable , org.apache.commons.math.Field<org.apache.commons.math.util.BigReal> {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 4756431066541037559L;
    /**
     * Private constructor for the singleton.
     */     private BigRealField() {
    }

    /**
     * Get the unique instance.
     *
     * @return the unique instance
     */     public static org.apache.commons.math.util.BigRealField getInstance() {         return org.apache.commons.math.util.BigRealField.LazyHolder.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.util.BigReal getOne() {         return org.apache.commons.math.util.BigReal.ONE;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.util.BigReal getZero() {         return org.apache.commons.math.util.BigReal.ZERO;
    }

    // CHECKSTYLE: stop HideUtilityClassConstructor
    /**
     * Holder for the instance.
     * <p>We use here the Initialization On Demand Holder Idiom.</p>
     */     private static class LazyHolder {
        /**
         * Cached field instance.
         */         private static final org.apache.commons.math.util.BigRealField INSTANCE = new org.apache.commons.math.util.BigRealField();}
    // CHECKSTYLE: resume HideUtilityClassConstructor

    /**
     * Handle deserialization of the singleton.
     *
     * @return the singleton instance
     */     private java.lang.Object readResolve() {         // return the singleton instance
        return org.apache.commons.math.util.BigRealField.LazyHolder.INSTANCE;
    }

}