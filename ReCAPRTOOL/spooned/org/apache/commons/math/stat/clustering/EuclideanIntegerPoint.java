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
package org.apache.commons.math.stat.clustering;







/**
 * A simple implementation of {@link Clusterable} for points with integer coordinates.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class EuclideanIntegerPoint implements java.io.Serializable , org.apache.commons.math.stat.clustering.Clusterable<org.apache.commons.math.stat.clustering.EuclideanIntegerPoint> {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 3946024775784901369L;
    /**
     * Point coordinates.
     */     private final int[] point;
    /**
     * Build an instance wrapping an integer array.
     * <p>The wrapped array is referenced, it is <em>not</em> copied.</p>
     *
     * @param point
     * 		the n-dimensional point in integer space
     */     public EuclideanIntegerPoint(final int[] point) {         this.point = point;
    }

    /**
     * Get the n-dimensional point in integer space.
     *
     * @return a reference (not a copy!) to the wrapped array
     */     public int[] getPoint() {
        return point;
    }

    /**
     * {@inheritDoc}
     */     public double distanceFrom(final org.apache.commons.math.stat.clustering.EuclideanIntegerPoint p) {         return org.apache.commons.math.util.MathUtils.distance(point, p.getPoint());
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.stat.clustering.EuclideanIntegerPoint centroidOf(final java.util.Collection<org.apache.commons.math.stat.clustering.EuclideanIntegerPoint> points) {         int[] centroid = new int[getPoint().length];
        for (org.apache.commons.math.stat.clustering.EuclideanIntegerPoint p : points) {
            for (int i = 0; i < (centroid.length); i++) {
                centroid[i] += p.getPoint()[i];
            }
        }
        for (int i = 0; i < (centroid.length); i++) {
            centroid[i] /= points.size();
        }
        return new org.apache.commons.math.stat.clustering.EuclideanIntegerPoint(centroid);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(final java.lang.Object other) {
        if (!(other instanceof org.apache.commons.math.stat.clustering.EuclideanIntegerPoint)) {
            return false;
        }
        final int[] otherPoint = ((org.apache.commons.math.stat.clustering.EuclideanIntegerPoint) (other)).getPoint();
        if ((point.length) != (otherPoint.length)) {
            return false;
        }
        for (int i = 0; i < (point.length); i++) {
            if ((point[i]) != (otherPoint[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        int hashCode = 0;
        for (java.lang.Integer i : point) {
            hashCode += ((i.hashCode()) * 13) + 7;
        }
        return hashCode;
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.1
     */     @java.lang.Override
    public java.lang.String toString() {
        final java.lang.StringBuffer buff = new java.lang.StringBuffer("(");
        final int[] coordinates = getPoint();
        for (int i = 0; i < (coordinates.length); i++) {
            buff.append(coordinates[i]);
            if (i < ((coordinates.length) - 1)) {
                buff.append(",");
            }
        }
        buff.append(")");
        return buff.toString();
    }

}