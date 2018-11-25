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
 *
 *
 * @version $Revision$ $Date$
 */ public class TransformerMapTest extends junit.framework.TestCase {     /**
     *
     */
    public void testPutTransformer() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();

        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertEquals(expected, map.getTransformer(org.apache.commons.math.util.TransformerMapTest.class));
    }

    /**
     *
     */
    public void testContainsClass() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();
        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertTrue(map.containsClass(org.apache.commons.math.util.TransformerMapTest.class));
    }

    /**
     *
     */
    public void testContainsTransformer() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();
        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertTrue(map.containsTransformer(expected));
    }

    /**
     *
     */
    public void testRemoveTransformer() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();

        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertTrue(map.containsClass(org.apache.commons.math.util.TransformerMapTest.class));
        junit.framework.Assert.assertTrue(map.containsTransformer(expected));
        map.removeTransformer(org.apache.commons.math.util.TransformerMapTest.class);
        junit.framework.Assert.assertFalse(map.containsClass(org.apache.commons.math.util.TransformerMapTest.class));
        junit.framework.Assert.assertFalse(map.containsTransformer(expected));
    }

    /**
     *
     */
    public void testClear() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();

        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertTrue(map.containsClass(org.apache.commons.math.util.TransformerMapTest.class));
        map.clear();
        junit.framework.Assert.assertFalse(map.containsClass(org.apache.commons.math.util.TransformerMapTest.class));
    }

    /**
     *
     */
    public void testClasses() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();
        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertTrue(map.classes().contains(org.apache.commons.math.util.TransformerMapTest.class));
    }

    /**
     *
     */
    public void testTransformers() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();
        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertTrue(map.transformers().contains(expected));
    }

    public void testSerial() {
        org.apache.commons.math.util.NumberTransformer expected = new org.apache.commons.math.util.DefaultTransformer();
        org.apache.commons.math.util.TransformerMap map = new org.apache.commons.math.util.TransformerMap();
        map.putTransformer(org.apache.commons.math.util.TransformerMapTest.class, expected);
        junit.framework.Assert.assertEquals(map, org.apache.commons.math.TestUtils.serializeAndRecover(map));
    }

}