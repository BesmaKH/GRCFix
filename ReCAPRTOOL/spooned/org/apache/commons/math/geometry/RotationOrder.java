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


/**
 * This class is a utility representing a rotation order specification
 * for Cardan or Euler angles specification.
 *
 * This class cannot be instanciated by the user. He can only use one
 * of the twelve predefined supported orders as an argument to either
 * the {@link Rotation#Rotation(RotationOrder,double,double,double)}
 * constructor or the {@link Rotation#getAngles} method.
 *
 * @version $Revision$ $Date$
 * @since 1.2
 */
public final class RotationOrder {

    /**
     * Set of Cardan angles.
     * this ordered set of rotations is around X, then around Y, then
     * around Z
     */     public static final org.apache.commons.math.geometry.RotationOrder XYZ = 
    new org.apache.commons.math.geometry.RotationOrder("XYZ", org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_K);

    /**
     * Set of Cardan angles.
     * this ordered set of rotations is around X, then around Z, then
     * around Y
     */     public static final org.apache.commons.math.geometry.RotationOrder XZY = 
    new org.apache.commons.math.geometry.RotationOrder("XZY", org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_J);

    /**
     * Set of Cardan angles.
     * this ordered set of rotations is around Y, then around X, then
     * around Z
     */     public static final org.apache.commons.math.geometry.RotationOrder YXZ = 
    new org.apache.commons.math.geometry.RotationOrder("YXZ", org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_K);

    /**
     * Set of Cardan angles.
     * this ordered set of rotations is around Y, then around Z, then
     * around X
     */     public static final org.apache.commons.math.geometry.RotationOrder YZX = 
    new org.apache.commons.math.geometry.RotationOrder("YZX", org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_I);

    /**
     * Set of Cardan angles.
     * this ordered set of rotations is around Z, then around X, then
     * around Y
     */     public static final org.apache.commons.math.geometry.RotationOrder ZXY = 
    new org.apache.commons.math.geometry.RotationOrder("ZXY", org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_J);

    /**
     * Set of Cardan angles.
     * this ordered set of rotations is around Z, then around Y, then
     * around X
     */     public static final org.apache.commons.math.geometry.RotationOrder ZYX = 
    new org.apache.commons.math.geometry.RotationOrder("ZYX", org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_I);

    /**
     * Set of Euler angles.
     * this ordered set of rotations is around X, then around Y, then
     * around X
     */     public static final org.apache.commons.math.geometry.RotationOrder XYX = 
    new org.apache.commons.math.geometry.RotationOrder("XYX", org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_I);

    /**
     * Set of Euler angles.
     * this ordered set of rotations is around X, then around Z, then
     * around X
     */     public static final org.apache.commons.math.geometry.RotationOrder XZX = 
    new org.apache.commons.math.geometry.RotationOrder("XZX", org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_I);

    /**
     * Set of Euler angles.
     * this ordered set of rotations is around Y, then around X, then
     * around Y
     */     public static final org.apache.commons.math.geometry.RotationOrder YXY = 
    new org.apache.commons.math.geometry.RotationOrder("YXY", org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_J);

    /**
     * Set of Euler angles.
     * this ordered set of rotations is around Y, then around Z, then
     * around Y
     */     public static final org.apache.commons.math.geometry.RotationOrder YZY = 
    new org.apache.commons.math.geometry.RotationOrder("YZY", org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_J);

    /**
     * Set of Euler angles.
     * this ordered set of rotations is around Z, then around X, then
     * around Z
     */     public static final org.apache.commons.math.geometry.RotationOrder ZXZ = 
    new org.apache.commons.math.geometry.RotationOrder("ZXZ", org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_I, org.apache.commons.math.geometry.Vector3D.PLUS_K);

    /**
     * Set of Euler angles.
     * this ordered set of rotations is around Z, then around Y, then
     * around Z
     */     public static final org.apache.commons.math.geometry.RotationOrder ZYZ = 
    new org.apache.commons.math.geometry.RotationOrder("ZYZ", org.apache.commons.math.geometry.Vector3D.PLUS_K, org.apache.commons.math.geometry.Vector3D.PLUS_J, org.apache.commons.math.geometry.Vector3D.PLUS_K);

    /**
     * Name of the rotations order.
     */     private final java.lang.String name;
    /**
     * Axis of the first rotation.
     */     private final org.apache.commons.math.geometry.Vector3D a1;
    /**
     * Axis of the second rotation.
     */     private final org.apache.commons.math.geometry.Vector3D a2;
    /**
     * Axis of the third rotation.
     */     private final org.apache.commons.math.geometry.Vector3D a3;
    /**
     * Private constructor.
     * This is a utility class that cannot be instantiated by the user,
     * so its only constructor is private.
     *
     * @param name
     * 		name of the rotation order
     * @param a1
     * 		axis of the first rotation
     * @param a2
     * 		axis of the second rotation
     * @param a3
     * 		axis of the third rotation
     */     private RotationOrder(final java.lang.String name, final org.apache.commons.math.geometry.Vector3D a1, final org.apache.commons.math.geometry.Vector3D a2, final org.apache.commons.math.geometry.Vector3D a3) {         this.name = name;         this.a1 = a1;         this.a2 = a2;         this.a3 = a3;
    }

    /**
     * Get a string representation of the instance.
     *
     * @return a string representation of the instance (in fact, its name)
     */     @java.lang.Override     public java.lang.String toString() {
        return name;
    }

    /**
     * Get the axis of the first rotation.
     *
     * @return axis of the first rotation
     */     public org.apache.commons.math.geometry.Vector3D getA1() {         return a1;
    }

    /**
     * Get the axis of the second rotation.
     *
     * @return axis of the second rotation
     */     public org.apache.commons.math.geometry.Vector3D getA2() {         return a2;
    }

    /**
     * Get the axis of the second rotation.
     *
     * @return axis of the second rotation
     */     public org.apache.commons.math.geometry.Vector3D getA3() {         return a3;
    }

}