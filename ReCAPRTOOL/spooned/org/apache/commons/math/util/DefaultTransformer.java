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
 * A Default NumberTransformer for java.lang.Numbers and Numeric Strings. This
 * provides some simple conversion capabilities to turn any java.lang.Number
 * into a primitive double or to turn a String representation of a Number into
 * a double.
 *
 * @version $Revision$ $Date$
 */
public class DefaultTransformer implements java.io.Serializable , org.apache.commons.math.util.NumberTransformer {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 4019938025047800455L;
    /**
     *
     *
     * @param o
     * 		the object that gets transformed.
     * @return a double primitive representation of the Object o.
     * @throws org.apache.commons.math.MathException
     * 		If it cannot successfully
     * 		be transformed or is null.
     * @see <a href="http://commons.apache.org/collections/api-release/org/apache/commons/collections/Transformer.html"/>
     */     public double transform(java.lang.Object o) throws org.apache.commons.math.MathException {         if (o == null) {             throw new org.apache.commons.math.MathException(org.apache.commons.math.util.LocalizedFormats.NULL_OBJECT_TRANSFORMATION);
        }

        if (o instanceof java.lang.Number) {
            return ((java.lang.Number) (o)).doubleValue();
        }

        try {
            return java.lang.Double.valueOf(o.toString()).doubleValue();
        } catch (java.lang.NumberFormatException e) {
            throw new org.apache.commons.math.MathException(e, 
            org.apache.commons.math.util.LocalizedFormats.CANNOT_TRANSFORM_TO_DOUBLE, e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(java.lang.Object other) {
        if ((this) == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return other instanceof org.apache.commons.math.util.DefaultTransformer;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        // some arbitrary number ...
        return 401993047;
    }

}