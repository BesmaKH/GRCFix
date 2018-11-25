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
 * A tableau for use in the Simplex method.
 *
 * <p>
 * Example:
 * <pre>
 *   W |  Z |  x1 |  x2 |  x- | s1 |  s2 |  a1 |  RHS
 * ---------------------------------------------------
 *  -1    0    0     0     0     0     0     1     0   &lt;= phase 1 objective
 *   0    1   -15   -10    0     0     0     0     0   &lt;= phase 2 objective
 *   0    0    1     0     0     1     0     0     2   &lt;= constraint 1
 *   0    0    0     1     0     0     1     0     3   &lt;= constraint 2
 *   0    0    1     1     0     0     0     1     4   &lt;= constraint 3
 * </pre>
 * W: Phase 1 objective function</br>
 * Z: Phase 2 objective function</br>
 * x1 &amp; x2: Decision variables</br>
 * x-: Extra decision variable to allow for negative values</br>
 * s1 &amp; s2: Slack/Surplus variables</br>
 * a1: Artificial variable</br>
 * RHS: Right hand side</br>
 * </p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ class SimplexTableau implements java.io.Serializable {

    /**
     * Column label for negative vars.
     */     private static final java.lang.String NEGATIVE_VAR_COLUMN_LABEL = "x-";
    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = -1369660067587938365L;
    /**
     * Linear objective function.
     */     private final org.apache.commons.math.optimization.linear.LinearObjectiveFunction f;
    /**
     * Linear constraints.
     */     private final java.util.List<org.apache.commons.math.optimization.linear.LinearConstraint> constraints;
    /**
     * Whether to restrict the variables to non-negative values.
     */     private final boolean restrictToNonNegative;
    /**
     * The variables each column represents
     */     private final java.util.List<java.lang.String> columnLabels = new java.util.ArrayList<java.lang.String>();
    /**
     * Simple tableau.
     */     private transient org.apache.commons.math.linear.RealMatrix tableau;
    /**
     * Number of decision variables.
     */     private final int numDecisionVariables;
    /**
     * Number of slack variables.
     */     private final int numSlackVariables;
    /**
     * Number of artificial variables.
     */     private int numArtificialVariables;
    /**
     * Amount of error to accept in floating point comparisons.
     */     private final double epsilon;
    /**
     * Build a tableau for a linear problem.
     *
     * @param f
     * 		linear objective function
     * @param constraints
     * 		linear constraints
     * @param goalType
     * 		type of optimization goal: either {@link GoalType#MAXIMIZE}
     * 		or {@link GoalType#MINIMIZE}
     * @param restrictToNonNegative
     * 		whether to restrict the variables to non-negative values
     * @param epsilon
     * 		amount of error to accept in floating point comparisons
     */     SimplexTableau(final org.apache.commons.math.optimization.linear.LinearObjectiveFunction f, final java.util.Collection<org.apache.commons.math.optimization.linear.LinearConstraint> constraints, final org.apache.commons.math.optimization.GoalType goalType, final boolean restrictToNonNegative, final double epsilon) {         this.f = f;         this.constraints = normalizeConstraints(constraints);
        this.restrictToNonNegative = restrictToNonNegative;
        this.epsilon = epsilon;
        this.numDecisionVariables = (f.getCoefficients().getDimension()) + 
        (restrictToNonNegative ? 0 : 1);
        this.numSlackVariables = (getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.LEQ)) + 
        (getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.GEQ));
        this.numArtificialVariables = (getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.EQ)) + 
        (getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.GEQ));
        this.tableau = createTableau((goalType == (org.apache.commons.math.optimization.GoalType.MAXIMIZE)));
        initializeColumnLabels();
    }

    /**
     * Initialize the labels for the columns.
     */
    protected void initializeColumnLabels() {
        if ((getNumObjectiveFunctions()) == 2) {
            columnLabels.add("W");
        }
        columnLabels.add("Z");
        for (int i = 0; i < (getOriginalNumDecisionVariables()); i++) {
            columnLabels.add(("x" + i));
        }
        if (!(restrictToNonNegative)) {
            columnLabels.add(org.apache.commons.math.optimization.linear.SimplexTableau.NEGATIVE_VAR_COLUMN_LABEL);
        }
        for (int i = 0; i < (getNumSlackVariables()); i++) {
            columnLabels.add(("s" + i));
        }
        for (int i = 0; i < (getNumArtificialVariables()); i++) {
            columnLabels.add(("a" + i));
        }
        columnLabels.add("RHS");
    }

    /**
     * Create the tableau by itself.
     *
     * @param maximize
     * 		if true, goal is to maximize the objective function
     * @return created tableau
     */     protected org.apache.commons.math.linear.RealMatrix createTableau(final boolean maximize) {
        // create a matrix of the correct size
        int width = ((((numDecisionVariables) + (numSlackVariables)) + 
        (numArtificialVariables)) + (getNumObjectiveFunctions())) + 1;// + 1 is for RHS
        int height = (constraints.size()) + (getNumObjectiveFunctions());
        org.apache.commons.math.linear.Array2DRowRealMatrix matrix = new org.apache.commons.math.linear.Array2DRowRealMatrix(height, width);

        // initialize the objective function rows
        if ((getNumObjectiveFunctions()) == 2) {
            matrix.setEntry(0, 0, (-1));
        }
        int zIndex = ((getNumObjectiveFunctions()) == 1) ? 0 : 1;
        matrix.setEntry(zIndex, zIndex, (maximize ? 1 : -1));
        org.apache.commons.math.linear.RealVector objectiveCoefficients = 
        (maximize) ? f.getCoefficients().mapMultiply((-1)) : f.getCoefficients();
        copyArray(objectiveCoefficients.getData(), matrix.getDataRef()[zIndex]);
        matrix.setEntry(zIndex, (width - 1), 
        (maximize ? f.getConstantTerm() : (-1) * (f.getConstantTerm())));

        if (!(restrictToNonNegative)) {
            matrix.setEntry(zIndex, ((getSlackVariableOffset()) - 1), 
            org.apache.commons.math.optimization.linear.SimplexTableau.getInvertedCoeffiecientSum(objectiveCoefficients));
        }

        // initialize the constraint rows
        int slackVar = 0;
        int artificialVar = 0;
        for (int i = 0; i < (constraints.size()); i++) {
            org.apache.commons.math.optimization.linear.LinearConstraint constraint = constraints.get(i);
            int row = (getNumObjectiveFunctions()) + i;

            // decision variable coefficients
            copyArray(constraint.getCoefficients().getData(), matrix.getDataRef()[row]);

            // x-
            if (!(restrictToNonNegative)) {
                matrix.setEntry(row, ((getSlackVariableOffset()) - 1), 
                org.apache.commons.math.optimization.linear.SimplexTableau.getInvertedCoeffiecientSum(constraint.getCoefficients()));
            }

            // RHS
            matrix.setEntry(row, (width - 1), constraint.getValue());

            // slack variables



            // excess
            if ((constraint.getRelationship()) == (org.apache.commons.math.optimization.linear.Relationship.LEQ)) {                 matrix.setEntry(row, ((getSlackVariableOffset()) + (slackVar++)), 1);// slack
            }// excess
            else                 if ((constraint.getRelationship()) == (org.apache.commons.math.optimization.linear.Relationship.GEQ)) {                     matrix.setEntry(row, ((getSlackVariableOffset()) + (slackVar++)), (-1));}             // artificial variables
            if (((constraint.getRelationship()) == (org.apache.commons.math.optimization.linear.Relationship.EQ)) || 
            ((constraint.getRelationship()) == (org.apache.commons.math.optimization.linear.Relationship.GEQ))) {
                matrix.setEntry(0, ((getArtificialVariableOffset()) + artificialVar), 1);
                matrix.setEntry(row, ((getArtificialVariableOffset()) + (artificialVar++)), 1);
                matrix.setRowVector(0, matrix.getRowVector(0).subtract(matrix.getRowVector(row)));
            }
        }

        return matrix;
    }

    /**
     * Get new versions of the constraints which have positive right hand sides.
     *
     * @param originalConstraints
     * 		original (not normalized) constraints
     * @return new versions of the constraints
     */     public java.util.List<org.apache.commons.math.optimization.linear.LinearConstraint> normalizeConstraints(java.util.Collection<org.apache.commons.math.optimization.linear.LinearConstraint> originalConstraints) {         java.util.List<org.apache.commons.math.optimization.linear.LinearConstraint> normalized = new java.util.ArrayList<org.apache.commons.math.optimization.linear.LinearConstraint>();
        for (org.apache.commons.math.optimization.linear.LinearConstraint constraint : originalConstraints) {
            normalized.add(normalize(constraint));
        }
        return normalized;
    }

    /**
     * Get a new equation equivalent to this one with a positive right hand side.
     *
     * @param constraint
     * 		reference constraint
     * @return new equation
     */     private org.apache.commons.math.optimization.linear.LinearConstraint normalize(final org.apache.commons.math.optimization.linear.LinearConstraint constraint) {         if ((constraint.getValue()) < 0) {
            return new org.apache.commons.math.optimization.linear.LinearConstraint(constraint.getCoefficients().mapMultiply((-1)), 
            constraint.getRelationship().oppositeRelationship(), 
            ((-1) * (constraint.getValue())));
        }
        return new org.apache.commons.math.optimization.linear.LinearConstraint(constraint.getCoefficients(), 
        constraint.getRelationship(), constraint.getValue());
    }

    /**
     * Get the number of objective functions in this tableau.
     *
     * @return 2 for Phase 1.  1 for Phase 2.
     */     protected final int getNumObjectiveFunctions() {
        return (this.numArtificialVariables) > 0 ? 2 : 1;
    }

    /**
     * Get a count of constraints corresponding to a specified relationship.
     *
     * @param relationship
     * 		relationship to count
     * @return number of constraint with the specified relationship
     */     private int getConstraintTypeCounts(final org.apache.commons.math.optimization.linear.Relationship relationship) {         int count = 0;
        for (final org.apache.commons.math.optimization.linear.LinearConstraint constraint : constraints) {
            if ((constraint.getRelationship()) == relationship) {
                ++count;
            }
        }
        return count;
    }

    /**
     * Get the -1 times the sum of all coefficients in the given array.
     *
     * @param coefficients
     * 		coefficients to sum
     * @return the -1 times the sum of all coefficients in the given array.
     */     protected static double getInvertedCoeffiecientSum(final org.apache.commons.math.linear.RealVector coefficients) {         double sum = 0;
        for (double coefficient : coefficients.getData()) {
            sum -= coefficient;
        }
        return sum;
    }

    /**
     * Checks whether the given column is basic.
     *
     * @param col
     * 		index of the column to check
     * @return the row that the variable is basic in.  null if the column is not basic
     */     protected java.lang.Integer getBasicRow(final int col) {         java.lang.Integer row = null;
        for (int i = 0; i < (getHeight()); i++) {
            if ((org.apache.commons.math.util.MathUtils.equals(getEntry(i, col), 1.0, epsilon)) && (row == null)) {
                row = i;
            }else                 if (!(org.apache.commons.math.util.MathUtils.equals(getEntry(i, col), 0.0, epsilon))) {
                    return null;
                }
        }
        return row;
    }

    /**
     * Removes the phase 1 objective function, positive cost non-artificial variables,
     * and the non-basic artificial variables from this tableau.
     */
    protected void dropPhase1Objective() {
        if ((getNumObjectiveFunctions()) == 1) {
            return;
        }

        java.util.List<java.lang.Integer> columnsToDrop = new java.util.ArrayList<java.lang.Integer>();
        columnsToDrop.add(0);

        // positive cost non-artificial variables
        for (int i = getNumObjectiveFunctions(); i < (getArtificialVariableOffset()); i++) {
            if ((org.apache.commons.math.util.MathUtils.compareTo(tableau.getEntry(0, i), 0, epsilon)) > 0) {
                columnsToDrop.add(i);
            }
        }

        // non-basic artificial variables
        for (int i = 0; i < (getNumArtificialVariables()); i++) {
            int col = i + (getArtificialVariableOffset());
            if ((getBasicRow(col)) == null) {
                columnsToDrop.add(col);
            }
        }

        double[][] matrix = new double[(getHeight()) - 1][(getWidth()) - (columnsToDrop.size())];
        for (int i = 1; i < (getHeight()); i++) {
            int col = 0;
            for (int j = 0; j < (getWidth()); j++) {
                if (!(columnsToDrop.contains(j))) {
                    matrix[(i - 1)][(col++)] = tableau.getEntry(i, j);
                }
            }
        }

        for (int i = (columnsToDrop.size()) - 1; i >= 0; i--) {
            columnLabels.remove(((int) (columnsToDrop.get(i))));
        }

        this.tableau = new org.apache.commons.math.linear.Array2DRowRealMatrix(matrix);
        this.numArtificialVariables = 0;
    }

    /**
     *
     *
     * @param src
     * 		the source array
     * @param dest
     * 		the destination array
     */     private void copyArray(final double[] src, final double[] dest) {         java.lang.System.arraycopy(src, 0, dest, getNumObjectiveFunctions(), src.length);}
    /**
     * Returns whether the problem is at an optimal state.
     *
     * @return whether the model has been solved
     */     boolean isOptimal() {
        for (int i = getNumObjectiveFunctions(); i < ((getWidth()) - 1); i++) {
            if ((org.apache.commons.math.util.MathUtils.compareTo(tableau.getEntry(0, i), 0, epsilon)) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the current solution.
     *
     * @return current solution
     */
    protected org.apache.commons.math.optimization.RealPointValuePair getSolution() {
        int negativeVarColumn = columnLabels.indexOf(org.apache.commons.math.optimization.linear.SimplexTableau.NEGATIVE_VAR_COLUMN_LABEL);
        java.lang.Integer negativeVarBasicRow = (negativeVarColumn > 0) ? getBasicRow(negativeVarColumn) : null;
        double mostNegative = (negativeVarBasicRow == null) ? 0 : getEntry(negativeVarBasicRow, getRhsOffset());

        java.util.Set<java.lang.Integer> basicRows = new java.util.HashSet<java.lang.Integer>();
        double[] coefficients = new double[getOriginalNumDecisionVariables()];
        for (int i = 0; i < (coefficients.length); i++) {
            int colIndex = columnLabels.indexOf(("x" + i));
            if (colIndex < 0) {
                coefficients[i] = 0;
                continue;
            }
            java.lang.Integer basicRow = getBasicRow(colIndex);
            if (basicRows.contains(basicRow)) {
                // if multiple variables can take a given value
                // then we choose the first and set the rest equal to 0
                coefficients[i] = 0;
            }else {
                basicRows.add(basicRow);
                coefficients[i] = 
                (basicRow == null ? 0 : getEntry(basicRow, getRhsOffset())) - 
                (restrictToNonNegative ? 0 : mostNegative);
            }
        }
        return new org.apache.commons.math.optimization.RealPointValuePair(coefficients, f.getValue(coefficients));
    }

    /**
     * Subtracts a multiple of one row from another.
     * <p>
     * After application of this operation, the following will hold:
     *   minuendRow = minuendRow - multiple * subtrahendRow
     * </p>
     *
     * @param dividendRow
     * 		index of the row
     * @param divisor
     * 		value of the divisor
     */     protected void divideRow(final int dividendRow, final double divisor) {         for (int j = 0; j < (getWidth()); j++) {             tableau.setEntry(dividendRow, j, ((tableau.getEntry(dividendRow, j)) / divisor));
        }
    }

    /**
     * Subtracts a multiple of one row from another.
     * <p>
     * After application of this operation, the following will hold:
     *   minuendRow = minuendRow - multiple * subtrahendRow
     * </p>
     *
     * @param minuendRow
     * 		row index
     * @param subtrahendRow
     * 		row index
     * @param multiple
     * 		multiplication factor
     */     protected void subtractRow(final int minuendRow, final int subtrahendRow, final double multiple) {         tableau.setRowVector(minuendRow, tableau.getRowVector(minuendRow).subtract(tableau.getRowVector(subtrahendRow).mapMultiply(multiple)));
    }

    /**
     * Get the width of the tableau.
     *
     * @return width of the tableau
     */     protected final int getWidth() {
        return tableau.getColumnDimension();
    }

    /**
     * Get the height of the tableau.
     *
     * @return height of the tableau
     */     protected final int getHeight() {
        return tableau.getRowDimension();
    }

    /**
     * Get an entry of the tableau.
     *
     * @param row
     * 		row index
     * @param column
     * 		column index
     * @return entry at (row, column)
     */     protected final double getEntry(final int row, final int column) {         return tableau.getEntry(row, column);}
    /**
     * Set an entry of the tableau.
     *
     * @param row
     * 		row index
     * @param column
     * 		column index
     * @param value
     * 		for the entry
     */     protected final void setEntry(final int row, final int column, final double value) {         tableau.setEntry(row, column, value);}
    /**
     * Get the offset of the first slack variable.
     *
     * @return offset of the first slack variable
     */     protected final int getSlackVariableOffset() {
        return (getNumObjectiveFunctions()) + (numDecisionVariables);
    }

    /**
     * Get the offset of the first artificial variable.
     *
     * @return offset of the first artificial variable
     */     protected final int getArtificialVariableOffset() {
        return ((getNumObjectiveFunctions()) + (numDecisionVariables)) + (numSlackVariables);
    }

    /**
     * Get the offset of the right hand side.
     *
     * @return offset of the right hand side
     */     protected final int getRhsOffset() {
        return (getWidth()) - 1;
    }

    /**
     * Get the number of decision variables.
     * <p>
     * If variables are not restricted to positive values, this will include 1
     * extra decision variable to represent the absolute value of the most
     * negative variable.
     * </p>
     *
     * @return number of decision variables
     * @see #getOriginalNumDecisionVariables()
     */     protected final int getNumDecisionVariables() {
        return numDecisionVariables;
    }

    /**
     * Get the original number of decision variables.
     *
     * @return original number of decision variables
     * @see #getNumDecisionVariables()
     */     protected final int getOriginalNumDecisionVariables() {
        return f.getCoefficients().getDimension();
    }

    /**
     * Get the number of slack variables.
     *
     * @return number of slack variables
     */     protected final int getNumSlackVariables() {
        return numSlackVariables;
    }

    /**
     * Get the number of artificial variables.
     *
     * @return number of artificial variables
     */     protected final int getNumArtificialVariables() {
        return numArtificialVariables;
    }

    /**
     * Get the tableau data.
     *
     * @return tableau data
     */     protected final double[][] getData() {
        return tableau.getData();
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(java.lang.Object other) {

        if ((this) == other) {
            return true;
        }

        if (other instanceof org.apache.commons.math.optimization.linear.SimplexTableau) {
            org.apache.commons.math.optimization.linear.SimplexTableau rhs = ((org.apache.commons.math.optimization.linear.SimplexTableau) (other));
            return ((((((((restrictToNonNegative) == (rhs.restrictToNonNegative)) && 
            ((numDecisionVariables) == (rhs.numDecisionVariables))) && 
            ((numSlackVariables) == (rhs.numSlackVariables))) && 
            ((numArtificialVariables) == (rhs.numArtificialVariables))) && 
            ((epsilon) == (rhs.epsilon))) && 
            (f.equals(rhs.f))) && 
            (constraints.equals(rhs.constraints))) && 
            (tableau.equals(rhs.tableau));
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        return (((((((java.lang.Boolean.valueOf(restrictToNonNegative).hashCode()) ^ 
        (numDecisionVariables)) ^ 
        (numSlackVariables)) ^ 
        (numArtificialVariables)) ^ 
        (java.lang.Double.valueOf(epsilon).hashCode())) ^ 
        (f.hashCode())) ^ 
        (constraints.hashCode())) ^ 
        (tableau.hashCode());
    }

    /**
     * Serialize the instance.
     *
     * @param oos
     * 		stream where object should be written
     * @throws IOException
     * 		if object cannot be written to stream
     */     private void writeObject(java.io.ObjectOutputStream oos) throws java.io.IOException {         oos.defaultWriteObject();         org.apache.commons.math.linear.MatrixUtils.serializeRealMatrix(tableau, oos);
    }

    /**
     * Deserialize the instance.
     *
     * @param ois
     * 		stream from which the object should be read
     * @throws ClassNotFoundException
     * 		if a class in the stream cannot be found
     * @throws IOException
     * 		if object cannot be read from the stream
     */     private void readObject(java.io.ObjectInputStream ois) throws java.io.IOException, java.lang.ClassNotFoundException {         ois.defaultReadObject();         org.apache.commons.math.linear.MatrixUtils.deserializeRealMatrix(this, "tableau", ois);}

}