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
 */ public class TestBean {     private java.lang.Double x = java.lang.Double.valueOf(1.0);

    private java.lang.String y = "1.0";

    /**
     *
     */
    public java.lang.Double getX() {
        return x;
    }

    /**
     *
     */
    public java.lang.String getY() {
        return y;
    }

    /**
     *
     */
    public void setX(java.lang.Double double1) {
        x = double1;
    }

    /**
     *
     */
    public void setY(java.lang.String string) {
        y = string;
    }

    /**
     *
     */
    public java.lang.Double getZ() {
        throw new org.apache.commons.math.MathRuntimeException(org.apache.commons.math.util.LocalizedFormats.SIMPLE_MESSAGE, "?");
    }

    /**
     *
     */
    public void setZ(java.lang.Double double1) {
    }

}