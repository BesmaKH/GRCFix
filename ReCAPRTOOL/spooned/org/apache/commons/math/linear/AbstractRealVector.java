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
package org.apache.commons.math.linear;












/**
 * This class provides default basic implementations for many methods in the
 * {@link RealVector} interface with.
 *
 * @version $Revision$ $Date$
 * @since 2.1
 */ public abstract class AbstractRealVector implements org.apache.commons.math.linear.RealVector {

    /**
     * Check if instance and specified vectors have the same dimension.
     *
     * @param v
     * 		vector to compare instance with
     * @exception IllegalArgumentException if the vectors do not
     * have the same dimension
     */     protected void checkVectorDimensions(org.apache.commons.math.linear.RealVector v) {         checkVectorDimensions(v.getDimension());
    }

    /**
     * Check if instance dimension is equal to some expected value.
     *
     * @param n
     * 		expected dimension.
     * @exception IllegalArgumentException if the dimension is
     * inconsistent with vector size
     */     protected void checkVectorDimensions(int n) throws 
    java.lang.IllegalArgumentException {
        double d = getDimension();
        if (d != n) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, d, n);
        }
    }

    /**
     * Check if an index is valid.
     *
     * @param index
     * 		index to check
     * @exception MatrixIndexException if index is not valid
     */     protected void checkIndex(final int index) throws org.apache.commons.math.linear.MatrixIndexException {
        if ((index < 0) || (index >= (getDimension()))) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            "index {0} out of allowed range [{1}, {2}]", 
            index, 0, ((getDimension()) - 1));
        }
    }

    /**
     * {@inheritDoc}
     */     public void setSubVector(int index, org.apache.commons.math.linear.RealVector v) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        checkIndex(((index + (v.getDimension())) - 1));
        setSubVector(index, v.getData());
    }

    /**
     * {@inheritDoc}
     */     public void setSubVector(int index, double[] v) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        checkIndex(((index + (v.length)) - 1));
        for (int i = 0; i < (v.length); i++) {
            setEntry((i + index), v[i]);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector add(double[] v) throws java.lang.IllegalArgumentException {         double[] result = v.clone();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            result[e.getIndex()] += e.getValue();
        } 
        return new org.apache.commons.math.linear.ArrayRealVector(result, false);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector add(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         if (v instanceof org.apache.commons.math.linear.ArrayRealVector) {
            double[] values = ((org.apache.commons.math.linear.ArrayRealVector) (v)).getDataRef();
            return add(values);
        }
        org.apache.commons.math.linear.RealVector result = v.copy();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            final int index = e.getIndex();
            result.setEntry(index, ((e.getValue()) + (result.getEntry(index))));
        } 
        return result;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector subtract(double[] v) throws java.lang.IllegalArgumentException {         double[] result = v.clone();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            final int index = e.getIndex();
            result[index] = (e.getValue()) - (result[index]);
        } 
        return new org.apache.commons.math.linear.ArrayRealVector(result, false);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector subtract(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         if (v instanceof org.apache.commons.math.linear.ArrayRealVector) {
            double[] values = ((org.apache.commons.math.linear.ArrayRealVector) (v)).getDataRef();
            return add(values);
        }
        org.apache.commons.math.linear.RealVector result = v.copy();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            final int index = e.getIndex();
            v.setEntry(index, ((e.getValue()) - (result.getEntry(index))));
        } 
        return result;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAdd(double d) {         return copy().mapAddToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAddToSelf(double d) {         if (d != 0) {
            try {
                return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.ADD.fix1stArgument(d));
            } catch (org.apache.commons.math.FunctionEvaluationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */     public abstract org.apache.commons.math.linear.AbstractRealVector copy();
    /**
     * {@inheritDoc}
     */     public double dotProduct(double[] v) throws java.lang.IllegalArgumentException {         return dotProduct(new org.apache.commons.math.linear.ArrayRealVector(v, false));
    }

    /**
     * {@inheritDoc}
     */     public double dotProduct(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v);
        double d = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            d += (e.getValue()) * (v.getEntry(e.getIndex()));
        } 
        return d;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector ebeDivide(double[] v) throws java.lang.IllegalArgumentException {         return ebeDivide(new org.apache.commons.math.linear.ArrayRealVector(v, false));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector ebeMultiply(double[] v) throws java.lang.IllegalArgumentException {         return ebeMultiply(new org.apache.commons.math.linear.ArrayRealVector(v, false));
    }

    /**
     * {@inheritDoc}
     */     public double getDistance(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v);
        double d = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            final double diff = (e.getValue()) - (v.getEntry(e.getIndex()));
            d += diff * diff;
        } 
        return java.lang.Math.sqrt(d);
    }

    /**
     * {@inheritDoc}
     */     public double getNorm() {         double sum = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            final double value = e.getValue();
            sum += value * value;
        } 
        return java.lang.Math.sqrt(sum);
    }

    /**
     * {@inheritDoc}
     */     public double getL1Norm() {         double norm = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            norm += java.lang.Math.abs(e.getValue());
        } 
        return norm;
    }

    /**
     * {@inheritDoc}
     */     public double getLInfNorm() {         double norm = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            norm = java.lang.Math.max(norm, java.lang.Math.abs(e.getValue()));
        } 
        return norm;
    }

    /**
     * {@inheritDoc}
     */     public double getDistance(double[] v) throws java.lang.IllegalArgumentException {         return getDistance(new org.apache.commons.math.linear.ArrayRealVector(v, false));
    }

    /**
     * {@inheritDoc}
     */     public double getL1Distance(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v);
        double d = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            d += java.lang.Math.abs(((e.getValue()) - (v.getEntry(e.getIndex()))));
        } 
        return d;
    }

    /**
     * {@inheritDoc}
     */     public double getL1Distance(double[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        double d = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            d += java.lang.Math.abs(((e.getValue()) - (v[e.getIndex()])));
        } 
        return d;
    }

    /**
     * {@inheritDoc}
     */     public double getLInfDistance(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v);
        double d = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            d = java.lang.Math.max(java.lang.Math.abs(((e.getValue()) - (v.getEntry(e.getIndex())))), d);
        } 
        return d;
    }

    /**
     * {@inheritDoc}
     */     public double getLInfDistance(double[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        double d = 0;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            d = java.lang.Math.max(java.lang.Math.abs(((e.getValue()) - (v[e.getIndex()]))), d);
        } 
        return d;
    }

    /**
     * Get the index of the minimum entry.
     *
     * @return index of the minimum entry or -1 if vector length is 0
     * or all entries are NaN
     */     public int getMinIndex() {         int minIndex = -1;
        double minValue = java.lang.Double.POSITIVE_INFINITY;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> iterator = iterator();
        while (iterator.hasNext()) {
            final org.apache.commons.math.linear.RealVector.Entry entry = iterator.next();
            if ((entry.getValue()) <= minValue) {
                minIndex = entry.getIndex();
                minValue = entry.getValue();
            }
        } 
        return minIndex;
    }

    /**
     * Get the value of the minimum entry.
     *
     * @return value of the minimum entry or NaN if all entries are NaN
     */     public double getMinValue() {         final int minIndex = getMinIndex();
        return minIndex < 0 ? java.lang.Double.NaN : getEntry(minIndex);
    }

    /**
     * Get the index of the maximum entry.
     *
     * @return index of the maximum entry or -1 if vector length is 0
     * or all entries are NaN
     */     public int getMaxIndex() {         int maxIndex = -1;
        double maxValue = java.lang.Double.NEGATIVE_INFINITY;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> iterator = iterator();
        while (iterator.hasNext()) {
            final org.apache.commons.math.linear.RealVector.Entry entry = iterator.next();
            if ((entry.getValue()) >= maxValue) {
                maxIndex = entry.getIndex();
                maxValue = entry.getValue();
            }
        } 
        return maxIndex;
    }

    /**
     * Get the value of the maximum entry.
     *
     * @return value of the maximum entry or NaN if all entries are NaN
     */     public double getMaxValue() {         final int maxIndex = getMaxIndex();
        return maxIndex < 0 ? java.lang.Double.NaN : getEntry(maxIndex);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAbs() {         return copy().mapAbsToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAbsToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ABS);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAcos() {         return copy().mapAcosToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAcosToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ACOS);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAsin() {         return copy().mapAsinToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAsinToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ASIN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAtan() {         return copy().mapAtanToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapAtanToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ATAN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCbrt() {         return copy().mapCbrtToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCbrtToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.CBRT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCeil() {         return copy().mapCeilToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCeilToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.CEIL);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCos() {         return copy().mapCosToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCosToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.COS);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCosh() {         return copy().mapCoshToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapCoshToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.COSH);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapDivide(double d) {         return copy().mapDivideToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapDivideToSelf(double d) {         try {
            return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.DIVIDE.fix2ndArgument(d));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapExp() {         return copy().mapExpToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapExpToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.EXP);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapExpm1() {         return copy().mapExpm1ToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapExpm1ToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.EXPM1);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapFloor() {         return copy().mapFloorToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapFloorToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.FLOOR);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapInv() {         return copy().mapInvToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapInvToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.INVERT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapLog() {         return copy().mapLogToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapLogToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.LOG);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapLog10() {         return copy().mapLog10ToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapLog10ToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.LOG10);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapLog1p() {         return copy().mapLog1pToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapLog1pToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.LOG1P);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapMultiply(double d) {         return copy().mapMultiplyToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapMultiplyToSelf(double d) {         try {
            return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.MULTIPLY.fix1stArgument(d));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapPow(double d) {         return copy().mapPowToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapPowToSelf(double d) {         try {
            return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.POW.fix2ndArgument(d));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapRint() {         return copy().mapRintToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapRintToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.RINT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSignum() {         return copy().mapSignumToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSignumToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SIGNUM);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSin() {         return copy().mapSinToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSinToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SIN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSinh() {         return copy().mapSinhToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSinhToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SINH);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSqrt() {         return copy().mapSqrtToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSqrtToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SQRT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSubtract(double d) {         return copy().mapSubtractToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapSubtractToSelf(double d) {         return mapAddToSelf((-d));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapTan() {         return copy().mapTanToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapTanToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.TAN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapTanh() {         return copy().mapTanhToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapTanhToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.TANH);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapUlp() {         return copy().mapUlpToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapUlpToSelf() {         try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ULP);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealMatrix outerProduct(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         org.apache.commons.math.linear.RealMatrix product;
        if ((v instanceof org.apache.commons.math.linear.SparseRealVector) || ((this) instanceof org.apache.commons.math.linear.SparseRealVector)) {
            product = new org.apache.commons.math.linear.OpenMapRealMatrix(this.getDimension(), v.getDimension());
        }else {
            product = new org.apache.commons.math.linear.Array2DRowRealMatrix(this.getDimension(), v.getDimension());
        }
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> thisIt = sparseIterator();
        org.apache.commons.math.linear.RealVector.Entry thisE = null;
        while ((thisIt.hasNext()) && ((thisE = thisIt.next()) != null)) {
            java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> otherIt = v.sparseIterator();
            org.apache.commons.math.linear.RealVector.Entry otherE = null;
            while ((otherIt.hasNext()) && ((otherE = otherIt.next()) != null)) {
                product.setEntry(thisE.getIndex(), otherE.getIndex(), 
                ((thisE.getValue()) * (otherE.getValue())));
            } 
        } 

        return product;

    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealMatrix outerProduct(double[] v) throws java.lang.IllegalArgumentException {         return outerProduct(new org.apache.commons.math.linear.ArrayRealVector(v, false));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector projection(double[] v) throws java.lang.IllegalArgumentException {         return projection(new org.apache.commons.math.linear.ArrayRealVector(v, false));
    }

    /**
     * {@inheritDoc}
     */     public void set(double value) {         java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        org.apache.commons.math.linear.RealVector.Entry e = null;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            e.setValue(value);
        } 
    }

    /**
     * {@inheritDoc}
     */     public double[] toArray() {         int dim = getDimension();
        double[] values = new double[dim];
        for (int i = 0; i < dim; i++) {
            values[i] = getEntry(i);
        }
        return values;
    }

    /**
     * {@inheritDoc}
     */     public double[] getData() {         return toArray();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector unitVector() {         org.apache.commons.math.linear.RealVector copy = copy();
        copy.unitize();
        return copy;
    }

    /**
     * {@inheritDoc}
     */     public void unitize() {         mapDivideToSelf(getNorm());
    }

    /**
     * {@inheritDoc}
     */     public java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator() {         return new org.apache.commons.math.linear.AbstractRealVector.SparseEntryIterator();
    }

    /**
     * {@inheritDoc}
     */     public java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> iterator() {         final int dim = getDimension();
        return new java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry>() {

            /**
             * Current index.
             */             private int i = 0;
            /**
             * Current entry.
             */             private org.apache.commons.math.linear.AbstractRealVector.EntryImpl e = new org.apache.commons.math.linear.AbstractRealVector.EntryImpl();
            /**
             * {@inheritDoc}
             */             public boolean hasNext() {                 return (i) < dim;
            }

            /**
             * {@inheritDoc}
             */             public org.apache.commons.math.linear.RealVector.Entry next() {                 e.setIndex(((i)++));
                return e;
            }

            /**
             * {@inheritDoc}
             */             public void remove() {                 throw new java.lang.UnsupportedOperationException("Not supported");
            }
        };
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector map(org.apache.commons.math.analysis.UnivariateRealFunction function) throws org.apache.commons.math.FunctionEvaluationException {         return copy().mapToSelf(function);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector mapToSelf(org.apache.commons.math.analysis.UnivariateRealFunction function) throws org.apache.commons.math.FunctionEvaluationException {         java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = ((function.value(0)) == 0) ? sparseIterator() : iterator();
        org.apache.commons.math.linear.RealVector.Entry e;
        while ((it.hasNext()) && ((e = it.next()) != null)) {
            e.setValue(function.value(e.getValue()));
        } 
        return this;
    }

    /**
     * An entry in the vector.
     */     protected class EntryImpl extends org.apache.commons.math.linear.RealVector.Entry {
        /**
         * Simple constructor.
         */         public EntryImpl() {             setIndex(0);
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public double getValue() {
            return getEntry(getIndex());
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public void setValue(double newValue) {
            setEntry(getIndex(), newValue);
        }
    }

    /**
     * This class should rare be used, but is here to provide
     * a default implementation of sparseIterator(), which is implemented
     * by walking over the entries, skipping those whose values are the default one.
     *
     * Concrete subclasses which are SparseVector implementations should
     * make their own sparse iterator, not use this one.
     *
     * This implementation might be useful for ArrayRealVector, when expensive
     * operations which preserve the default value are to be done on the entries,
     * and the fraction of non-default values is small (i.e. someone took a
     * SparseVector, and passed it into the copy-constructor of ArrayRealVector)
     */
    protected class SparseEntryIterator implements java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> {

        /**
         * Dimension of the vector.
         */         private final int dim;
        /**
         * last entry returned by {@link #next()}
         */         private org.apache.commons.math.linear.AbstractRealVector.EntryImpl current;
        /**
         * Next entry for {@link #next()} to return.
         */         private org.apache.commons.math.linear.AbstractRealVector.EntryImpl next;
        /**
         * Simple constructor.
         */         protected SparseEntryIterator() {             dim = getDimension();
            current = new org.apache.commons.math.linear.AbstractRealVector.EntryImpl();
            next = new org.apache.commons.math.linear.AbstractRealVector.EntryImpl();
            if ((next.getValue()) == 0) {
                advance(next);
            }
        }

        /**
         * Advance an entry up to the next nonzero one.
         *
         * @param e
         * 		entry to advance
         */         protected void advance(org.apache.commons.math.linear.AbstractRealVector.EntryImpl e) {             if (e == null) {                 return;
            }
            do {
                e.setIndex(((e.getIndex()) + 1));
            } while (((e.getIndex()) < (dim)) && ((e.getValue()) == 0) );
            if ((e.getIndex()) >= (dim)) {
                e.setIndex((-1));
            }
        }

        /**
         * {@inheritDoc}
         */         public boolean hasNext() {             return (next.getIndex()) >= 0;
        }

        /**
         * {@inheritDoc}
         */         public org.apache.commons.math.linear.RealVector.Entry next() {             int index = next.getIndex();
            if (index < 0) {
                throw new java.util.NoSuchElementException();
            }
            current.setIndex(index);
            advance(next);
            return current;
        }

        /**
         * {@inheritDoc}
         */         public void remove() {             throw new java.lang.UnsupportedOperationException("Not supported");
        }
    }

}