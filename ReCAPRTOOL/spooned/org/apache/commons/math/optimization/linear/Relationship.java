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
package org.apache.commons.math.optimization.linear;


/**
 * Types of relationships between two cells in a Solver {@link LinearConstraint}.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public enum Relationship {

    /**
     * Equality relationship.
     */
    EQ("="),     /**
     * Lesser than or equal relationship.
     */
    LEQ("<="),     /**
     * Greater than or equal relationship.
     */
    GEQ(">=");     /**
     * Display string for the relationship.
     */     private java.lang.String stringValue;
    /**
     * Simple constructor.
     *
     * @param stringValue
     * 		display string for the relationship
     */     private Relationship(java.lang.String stringValue) {         this.stringValue = stringValue;}

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public java.lang.String toString() {
        return stringValue;
    }

    /**
     * Get the relationship obtained when multiplying all coefficients by -1.
     *
     * @return relationship obtained when multiplying all coefficients by -1
     */     public org.apache.commons.math.optimization.linear.Relationship oppositeRelationship() {
        switch (this) {
            case LEQ :
                return org.apache.commons.math.optimization.linear.Relationship.GEQ;
            case GEQ :
                return org.apache.commons.math.optimization.linear.Relationship.LEQ;
            default :
                return org.apache.commons.math.optimization.linear.Relationship.EQ;}

    }}

