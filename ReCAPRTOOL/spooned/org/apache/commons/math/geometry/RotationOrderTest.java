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
package org.apache.commons.math.geometry;








public class RotationOrderTest extends 
junit.framework.TestCase {

    public RotationOrderTest(java.lang.String name) {
        super(name);
    }

    public void testName() {

        org.apache.commons.math.geometry.RotationOrder[] orders = new org.apache.commons.math.geometry.RotationOrder[]{ 
        org.apache.commons.math.geometry.RotationOrder.XYZ, org.apache.commons.math.geometry.RotationOrder.XZY, org.apache.commons.math.geometry.RotationOrder.YXZ, 
        org.apache.commons.math.geometry.RotationOrder.YZX, org.apache.commons.math.geometry.RotationOrder.ZXY, org.apache.commons.math.geometry.RotationOrder.ZYX, 
        org.apache.commons.math.geometry.RotationOrder.XYX, org.apache.commons.math.geometry.RotationOrder.XZX, org.apache.commons.math.geometry.RotationOrder.YXY, 
        org.apache.commons.math.geometry.RotationOrder.YZY, org.apache.commons.math.geometry.RotationOrder.ZXZ, org.apache.commons.math.geometry.RotationOrder.ZYZ };


        for (int i = 0; i < (orders.length); ++i) {
            junit.framework.Assert.assertEquals(getFieldName(orders[i]), orders[i].toString());
        }

    }

    private java.lang.String getFieldName(org.apache.commons.math.geometry.RotationOrder order) {
        try {
            java.lang.reflect.Field[] fields = org.apache.commons.math.geometry.RotationOrder.class.getFields();
            for (int i = 0; i < (fields.length); ++i) {
                if ((fields[i].get(null)) == order) {
                    return fields[i].getName();
                }
            }
        } catch (java.lang.IllegalAccessException iae) {
            // ignored
        }
        return "unknown";
    }

}