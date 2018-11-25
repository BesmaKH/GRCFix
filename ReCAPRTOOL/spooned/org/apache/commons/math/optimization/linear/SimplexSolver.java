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
 * Solves a linear problem using the Two-Phase Simplex Method.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class SimplexSolver extends org.apache.commons.math.optimization.linear.AbstractLinearOptimizer {

    /**
     * Default amount of error to accept in floating point comparisons.
     */     private static final double DEFAULT_EPSILON = 1.0E-6;
    /**
     * Amount of error to accept in floating point comparisons.
     */     protected final double epsilon;
    /**
     * Build a simplex solver with default settings.
     */
    public SimplexSolver() {
        this(org.apache.commons.math.optimization.linear.SimplexSolver.DEFAULT_EPSILON);
    }

    /**
     * Build a simplex solver with a specified accepted amount of error
     *
     * @param epsilon
     * 		the amount of error to accept in floating point comparisons
     */     public SimplexSolver(final double epsilon) {         this.epsilon = epsilon;
    }

    /**
     * Returns the column with the most negative coefficient in the objective function row.
     *
     * @param tableau
     * 		simple tableau for the problem
     * @return column with the most negative coefficient
     */     private java.lang.Integer getPivotColumn(org.apache.commons.math.optimization.linear.SimplexTableau tableau) {         double minValue = 0;
        java.lang.Integer minPos = null;
        for (int i = tableau.getNumObjectiveFunctions(); i < ((tableau.getWidth()) - 1); i++) {
            if ((org.apache.commons.math.util.MathUtils.compareTo(tableau.getEntry(0, i), minValue, epsilon)) < 0) {
                minValue = tableau.getEntry(0, i);
                minPos = i;
            }
        }
        return minPos;
    }

    /**
     * Returns the row with the minimum ratio as given by the minimum ratio test (MRT).
     *
     * @param tableau
     * 		simple tableau for the problem
     * @param col
     * 		the column to test the ratio of.  See {@link #getPivotColumn(SimplexTableau)}
     * @return row with the minimum ratio
     */     private java.lang.Integer getPivotRow(org.apache.commons.math.optimization.linear.SimplexTableau tableau, final int col) {         // create a list of all the rows that tie for the lowest score in the minimum ratio test         java.util.List<java.lang.Integer> minRatioPositions = new java.util.ArrayList<java.lang.Integer>();
        double minRatio = java.lang.Double.MAX_VALUE;
        for (int i = tableau.getNumObjectiveFunctions(); i < (tableau.getHeight()); i++) {
            final double rhs = tableau.getEntry(i, ((tableau.getWidth()) - 1));
            final double entry = tableau.getEntry(i, col);
            if ((org.apache.commons.math.util.MathUtils.compareTo(entry, 0, epsilon)) > 0) {
                final double ratio = rhs / entry;
                if (org.apache.commons.math.util.MathUtils.equals(ratio, minRatio, epsilon)) {
                    minRatioPositions.add(i);
                }else                     if (ratio < minRatio) {
                        minRatio = ratio;
                        minRatioPositions = new java.util.ArrayList<java.lang.Integer>();
                        minRatioPositions.add(i);
                    }
            }
        }




        // there's a degeneracy as indicated by a tie in the minimum ratio test
        // check if there's an artificial variable that can be forced out of the basis
        if ((minRatioPositions.size()) == 0) {             return null;}// there's a degeneracy as indicated by a tie in the minimum ratio test         // check if there's an artificial variable that can be forced out of the basis
        else             if ((minRatioPositions.size()) > 1) {                 for (java.lang.Integer row : minRatioPositions) {                     for (int i = 0; i < (tableau.getNumArtificialVariables()); i++) {
                        int column = i + (tableau.getArtificialVariableOffset());
                        if ((org.apache.commons.math.util.MathUtils.equals(tableau.getEntry(row, column), 1, epsilon)) && 
                        (row.equals(tableau.getBasicRow(column)))) {
                            return row;
                        }
                    }
                }
            }
        return minRatioPositions.get(0);
    }

    /**
     * Runs one iteration of the Simplex method on the given model.
     *
     * @param tableau
     * 		simple tableau for the problem
     * @throws OptimizationException
     * 		if the maximal iteration count has been
     * 		exceeded or if the model is found not to have a bounded solution
     */     protected void doIteration(final org.apache.commons.math.optimization.linear.SimplexTableau tableau) throws org.apache.commons.math.optimization.OptimizationException {
        incrementIterationsCounter();

        java.lang.Integer pivotCol = getPivotColumn(tableau);
        java.lang.Integer pivotRow = getPivotRow(tableau, pivotCol);
        if (pivotRow == null) {
            throw new org.apache.commons.math.optimization.linear.UnboundedSolutionException();
        }

        // set the pivot element to 1
        double pivotVal = tableau.getEntry(pivotRow, pivotCol);
        tableau.divideRow(pivotRow, pivotVal);

        // set the rest of the pivot column to 0
        for (int i = 0; i < (tableau.getHeight()); i++) {
            if (i != pivotRow) {
                double multiplier = tableau.getEntry(i, pivotCol);
                tableau.subtractRow(i, pivotRow, multiplier);
            }
        }
    }

    /**
     * Solves Phase 1 of the Simplex method.
     *
     * @param tableau
     * 		simple tableau for the problem
     * @exception OptimizationException if the maximal number of iterations is
     * exceeded, or if the problem is found not to have a bounded solution, or
     * if there is no feasible solution
     */     protected void solvePhase1(final org.apache.commons.math.optimization.linear.SimplexTableau tableau) throws org.apache.commons.math.optimization.OptimizationException {
        // make sure we're in Phase 1
        if ((tableau.getNumArtificialVariables()) == 0) {
            return;
        }

        while (!(tableau.isOptimal())) {
            doIteration(tableau);
        } 

        // if W is not zero then we have no feasible solution
        if (!(org.apache.commons.math.util.MathUtils.equals(tableau.getEntry(0, tableau.getRhsOffset()), 0, epsilon))) {
            throw new org.apache.commons.math.optimization.linear.NoFeasibleSolutionException();
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.optimization.OptimizationException {
        final org.apache.commons.math.optimization.linear.SimplexTableau tableau = 
        new org.apache.commons.math.optimization.linear.SimplexTableau(function, linearConstraints, goal, nonNegative, epsilon);

        solvePhase1(tableau);
        tableau.dropPhase1Objective();

        while (!(tableau.isOptimal())) {
            doIteration(tableau);
        } 
        return tableau.getSolution();
    }

}