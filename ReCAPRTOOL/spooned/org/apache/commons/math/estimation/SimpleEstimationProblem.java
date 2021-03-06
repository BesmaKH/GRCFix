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
package org.apache.commons.math.estimation;





/**
 * Simple implementation of the {@link EstimationProblem
 * EstimationProblem} interface for boilerplate data handling.
 * <p>This class <em>only</em> handles parameters and measurements
 * storage and unbound parameters filtering. It does not compute
 * anything by itself. It should either be used with measurements
 * implementation that are smart enough to know about the
 * various parameters in order to compute the partial derivatives
 * appropriately. Since the problem-specific logic is mainly related to
 * the various measurements models, the simplest way to use this class
 * is by extending it and using one internal class extending
 * {@link WeightedMeasurement WeightedMeasurement} for each measurement
 * type. The instances of the internal classes would have access to the
 * various parameters and their current estimate.</p>
 *
 * @version $Revision$ $Date$
 * @since 1.2
 * @deprecated as of 2.0, everything in package org.apache.commons.math.estimation has
 * been deprecated and replaced by package org.apache.commons.math.optimization.general
 */

@java.lang.Deprecated
public class SimpleEstimationProblem implements org.apache.commons.math.estimation.EstimationProblem {

    /**
     * Estimated parameters.
     */     private final java.util.List<org.apache.commons.math.estimation.EstimatedParameter> parameters;
    /**
     * Measurements.
     */     private final java.util.List<org.apache.commons.math.estimation.WeightedMeasurement> measurements;
    /**
     * Build an empty instance without parameters nor measurements.
     */
    public SimpleEstimationProblem() {
        parameters = new java.util.ArrayList<org.apache.commons.math.estimation.EstimatedParameter>();
        measurements = new java.util.ArrayList<org.apache.commons.math.estimation.WeightedMeasurement>();
    }

    /**
     * Get all the parameters of the problem.
     *
     * @return parameters
     */     public org.apache.commons.math.estimation.EstimatedParameter[] getAllParameters() {
        return parameters.toArray(new org.apache.commons.math.estimation.EstimatedParameter[parameters.size()]);
    }

    /**
     * Get the unbound parameters of the problem.
     *
     * @return unbound parameters
     */     public org.apache.commons.math.estimation.EstimatedParameter[] getUnboundParameters() {

        // filter the unbound parameters
        java.util.List<org.apache.commons.math.estimation.EstimatedParameter> unbound = new java.util.ArrayList<org.apache.commons.math.estimation.EstimatedParameter>(parameters.size());
        for (org.apache.commons.math.estimation.EstimatedParameter p : parameters) {
            if (!(p.isBound())) {
                unbound.add(p);
            }
        }

        // convert to an array
        return unbound.toArray(new org.apache.commons.math.estimation.EstimatedParameter[unbound.size()]);

    }

    /**
     * Get the measurements of an estimation problem.
     *
     * @return measurements
     */     public org.apache.commons.math.estimation.WeightedMeasurement[] getMeasurements() {
        return measurements.toArray(new org.apache.commons.math.estimation.WeightedMeasurement[measurements.size()]);
    }

    /**
     * Add a parameter to the problem.
     *
     * @param p
     * 		parameter to add
     */     protected void addParameter(org.apache.commons.math.estimation.EstimatedParameter p) {         parameters.add(p);}

    /**
     * Add a new measurement to the set.
     *
     * @param m
     * 		measurement to add
     */     protected void addMeasurement(org.apache.commons.math.estimation.WeightedMeasurement m) {         measurements.add(m);
    }

}