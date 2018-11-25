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
 * This TansformerMap automates the transformation of mixed object types.
 * It provides a means to set NumberTransformers that will be selected
 * based on the Class of the object handed to the Maps
 * <code>double transform(Object o)</code> method.
 *
 * @version $Revision$ $Date$
 */ public class TransformerMap implements java.io.Serializable , org.apache.commons.math.util.NumberTransformer {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 4605318041528645258L;
    /**
     * A default Number Transformer for Numbers and numeric Strings.
     */
    private org.apache.commons.math.util.NumberTransformer defaultTransformer = null;

    /**
     * The internal Map.
     */
    private java.util.Map<java.lang.Class<?>, org.apache.commons.math.util.NumberTransformer> map = null;

    /**
     * Build a map containing only the default transformer.
     */
    public TransformerMap() {
        map = new java.util.HashMap<java.lang.Class<?>, org.apache.commons.math.util.NumberTransformer>();
        defaultTransformer = new org.apache.commons.math.util.DefaultTransformer();
    }

    /**
     * Tests if a Class is present in the TransformerMap.
     *
     * @param key
     * 		Class to check
     * @return true|false
     */     public boolean containsClass(java.lang.Class<?> key) {         return map.containsKey(key);
    }

    /**
     * Tests if a NumberTransformer is present in the TransformerMap.
     *
     * @param value
     * 		NumberTransformer to check
     * @return true|false
     */     public boolean containsTransformer(org.apache.commons.math.util.NumberTransformer value) {         return map.containsValue(value);
    }

    /**
     * Returns the Transformer that is mapped to a class
     * if mapping is not present, this returns null.
     *
     * @param key
     * 		The Class of the object
     * @return the mapped NumberTransformer or null.
     */     public org.apache.commons.math.util.NumberTransformer getTransformer(java.lang.Class<?> key) {         return map.get(key);
    }

    /**
     * Sets a Class to Transformer Mapping in the Map. If
     * the Class is already present, this overwrites that
     * mapping.
     *
     * @param key
     * 		The Class
     * @param transformer
     * 		The NumberTransformer
     * @return the replaced transformer if one is present
     */     public org.apache.commons.math.util.NumberTransformer putTransformer(java.lang.Class<?> key, org.apache.commons.math.util.NumberTransformer transformer) {         return map.put(key, transformer);}

    /**
     * Removes a Class to Transformer Mapping in the Map.
     *
     * @param key
     * 		The Class
     * @return the removed transformer if one is present or
     * null if none was present.
     */     public org.apache.commons.math.util.NumberTransformer removeTransformer(java.lang.Class<?> key) {         return map.remove(key);
    }

    /**
     * Clears all the Class to Transformer mappings.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Returns the Set of Classes used as keys in the map.
     *
     * @return Set of Classes
     */     public java.util.Set<java.lang.Class<?>> classes() {
        return map.keySet();
    }

    /**
     * Returns the Set of NumberTransformers used as values
     * in the map.
     *
     * @return Set of NumberTransformers
     */     public java.util.Collection<org.apache.commons.math.util.NumberTransformer> transformers() {
        return map.values();
    }

    /**
     * Attempts to transform the Object against the map of
     * NumberTransformers. Otherwise it returns Double.NaN.
     *
     * @param o
     * 		the Object to be transformed.
     * @return the double value of the Object.
     * @throws MathException
     * 		if the Object can not be transformed into a Double.
     * @see org.apache.commons.math.util.NumberTransformer#transform(java.lang.Object)
     */     public double transform(java.lang.Object o) throws org.apache.commons.math.MathException {         double value = java.lang.Double.NaN;

        if ((o instanceof java.lang.Number) || (o instanceof java.lang.String)) {
            value = defaultTransformer.transform(o);
        }else {
            org.apache.commons.math.util.NumberTransformer trans = getTransformer(o.getClass());
            if (trans != null) {
                value = trans.transform(o);
            }
        }

        return value;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(java.lang.Object other) {
        if ((this) == other) {
            return true;
        }
        if (other instanceof org.apache.commons.math.util.TransformerMap) {
            org.apache.commons.math.util.TransformerMap rhs = ((org.apache.commons.math.util.TransformerMap) (other));
            if (!(defaultTransformer.equals(rhs.defaultTransformer))) {
                return false;
            }
            if ((map.size()) != (rhs.map.size())) {
                return false;
            }
            for (java.util.Map.Entry<java.lang.Class<?>, org.apache.commons.math.util.NumberTransformer> entry : map.entrySet()) {
                if (!(entry.getValue().equals(rhs.map.get(entry.getKey())))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        int hash = defaultTransformer.hashCode();
        for (org.apache.commons.math.util.NumberTransformer t : map.values()) {
            hash = (hash * 31) + (t.hashCode());
        }
        return hash;
    }

}