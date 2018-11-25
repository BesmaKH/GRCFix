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
 * This class implements the {@link RealVector} interface with a {@link OpenIntToDoubleHashMap} backing store.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class OpenMapRealVector extends org.apache.commons.math.linear.AbstractRealVector implements java.io.Serializable , org.apache.commons.math.linear.SparseRealVector {

    /**
     * Default Tolerance for having a value considered zero.
     */     public static final double DEFAULT_ZERO_TOLERANCE = 1.0E-12;
    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 8772222695580707260L;
    /**
     * Entries of the vector.
     */     private final org.apache.commons.math.util.OpenIntToDoubleHashMap entries;
    /**
     * Dimension of the vector.
     */     private final int virtualSize;
    /**
     * Tolerance for having a value considered zero.
     */     private double epsilon;
    /**
     * Build a 0-length vector.
     * <p>Zero-length vectors may be used to initialized construction of vectors
     * by data gathering. We start with zero-length and use either the {@link
     * #OpenMapRealVector(OpenMapRealVector, int)} constructor
     * or one of the <code>append</code> method ({@link #append(double)}, {@link
     * #append(double[])}, {@link #append(RealVector)}) to gather data
     * into this vector.</p>
     */
    public OpenMapRealVector() {
        this(0, org.apache.commons.math.linear.OpenMapRealVector.DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Construct a (dimension)-length vector of zeros.
     *
     * @param dimension
     * 		size of the vector
     */     public OpenMapRealVector(int dimension) {         this(dimension, org.apache.commons.math.linear.OpenMapRealVector.DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Construct a (dimension)-length vector of zeros, specifying zero tolerance.
     *
     * @param dimension
     * 		Size of the vector
     * @param epsilon
     * 		The tolerance for having a value considered zero
     */     public OpenMapRealVector(int dimension, double epsilon) {         virtualSize = dimension;         entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0);
        this.epsilon = epsilon;
    }

    /**
     * Build a resized vector, for use with append.
     *
     * @param v
     * 		The original vector
     * @param resize
     * 		The amount to resize it
     */     protected OpenMapRealVector(org.apache.commons.math.linear.OpenMapRealVector v, int resize) {         virtualSize = (v.getDimension()) + resize;         entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(v.entries);
        epsilon = v.epsilon;
    }

    /**
     * Build a vector with known the sparseness (for advanced use only).
     *
     * @param dimension
     * 		The size of the vector
     * @param expectedSize
     * 		The expected number of non-zero entries
     */     public OpenMapRealVector(int dimension, int expectedSize) {         this(dimension, expectedSize, org.apache.commons.math.linear.OpenMapRealVector.DEFAULT_ZERO_TOLERANCE);}

    /**
     * Build a vector with known the sparseness and zero tolerance setting (for advanced use only).
     *
     * @param dimension
     * 		The size of the vector
     * @param expectedSize
     * 		The expected number of non-zero entries
     * @param epsilon
     * 		The tolerance for having a value considered zero
     */     public OpenMapRealVector(int dimension, int expectedSize, double epsilon) {         virtualSize = dimension;         entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(expectedSize, 0.0);         this.epsilon = epsilon;
    }

    /**
     * Create from a double array.
     * Only non-zero entries will be stored
     *
     * @param values
     * 		The set of values to create from
     */     public OpenMapRealVector(double[] values) {         this(values, org.apache.commons.math.linear.OpenMapRealVector.DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Create from a double array, specifying zero tolerance.
     * Only non-zero entries will be stored
     *
     * @param values
     * 		The set of values to create from
     * @param epsilon
     * 		The tolerance for having a value considered zero
     */     public OpenMapRealVector(double[] values, double epsilon) {         virtualSize = values.length;         entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0);
        this.epsilon = epsilon;
        for (int key = 0; key < (values.length); key++) {
            double value = values[key];
            if (!(isDefaultValue(value))) {
                entries.put(key, value);
            }
        }
    }

    /**
     * Create from a Double array.
     * Only non-zero entries will be stored
     *
     * @param values
     * 		The set of values to create from
     */     public OpenMapRealVector(java.lang.Double[] values) {         this(values, org.apache.commons.math.linear.OpenMapRealVector.DEFAULT_ZERO_TOLERANCE);
    }

    /**
     * Create from a Double array.
     * Only non-zero entries will be stored
     *
     * @param values
     * 		The set of values to create from
     * @param epsilon
     * 		The tolerance for having a value considered zero
     */     public OpenMapRealVector(java.lang.Double[] values, double epsilon) {         virtualSize = values.length;         entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0);
        this.epsilon = epsilon;
        for (int key = 0; key < (values.length); key++) {
            double value = values[key].doubleValue();
            if (!(isDefaultValue(value))) {
                entries.put(key, value);
            }
        }
    }

    /**
     * Copy constructor.
     *
     * @param v
     * 		The instance to copy from
     */     public OpenMapRealVector(org.apache.commons.math.linear.OpenMapRealVector v) {         virtualSize = v.getDimension();
        entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(v.getEntries());
        epsilon = v.epsilon;
    }

    /**
     * Generic copy constructor.
     *
     * @param v
     * 		The instance to copy from
     */     public OpenMapRealVector(org.apache.commons.math.linear.RealVector v) {         virtualSize = v.getDimension();
        entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0);
        epsilon = org.apache.commons.math.linear.OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
        for (int key = 0; key < (virtualSize); key++) {
            double value = v.getEntry(key);
            if (!(isDefaultValue(value))) {
                entries.put(key, value);
            }
        }
    }

    /**
     * Get the entries of this instance.
     *
     * @return entries of this instance
     */     private org.apache.commons.math.util.OpenIntToDoubleHashMap getEntries() {
        return entries;
    }

    /**
     * Determine if this value is within epsilon of zero.
     *
     * @param value
     * 		The value to test
     * @return <code>true</code> if this value is within epsilon to zero, <code>false</code> otherwise
     * @since 2.1
     */     protected boolean isDefaultValue(double value) {         return (java.lang.Math.abs(value)) < (epsilon);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealVector add(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return add(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }else {
            return super.add(v);
        }
    }

    /**
     * Optimized method to add two OpenMapRealVectors.  Copies the larger vector, iterates over the smaller.
     *
     * @param v
     * 		Vector to add with
     * @return The sum of <code>this</code> with <code>v</code>
     * @throws IllegalArgumentException
     * 		If the dimensions don't match
     */     public org.apache.commons.math.linear.OpenMapRealVector add(org.apache.commons.math.linear.OpenMapRealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());         boolean copyThis = (entries.size()) > (v.entries.size());
        org.apache.commons.math.linear.OpenMapRealVector res = (copyThis) ? this.copy() : v.copy();
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = (copyThis) ? v.entries.iterator() : entries.iterator();
        org.apache.commons.math.util.OpenIntToDoubleHashMap randomAccess = (copyThis) ? entries : v.entries;
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (randomAccess.containsKey(key)) {
                res.setEntry(key, ((randomAccess.get(key)) + (iter.value())));
            }else {
                res.setEntry(key, iter.value());
            }
        } 
        return res;
    }

    /**
     * Optimized method to append a OpenMapRealVector.
     *
     * @param v
     * 		vector to append
     * @return The result of appending <code>v</code> to self
     */     public org.apache.commons.math.linear.OpenMapRealVector append(org.apache.commons.math.linear.OpenMapRealVector v) {         org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this, v.getDimension());
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = v.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(((iter.key()) + (virtualSize)), iter.value());
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.OpenMapRealVector append(org.apache.commons.math.linear.RealVector v) {         if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return append(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }
        return append(v.getData());
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.OpenMapRealVector append(double d) {         org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this, 1);
        res.setEntry(virtualSize, d);
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.OpenMapRealVector append(double[] a) {         org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this, a.length);
        for (int i = 0; i < (a.length); i++) {
            res.setEntry((i + (virtualSize)), a[i]);
        }
        return res;
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.1
     */     @java.lang.Override
    public org.apache.commons.math.linear.OpenMapRealVector copy() {
        return new org.apache.commons.math.linear.OpenMapRealVector(this);
    }

    /**
     * Optimized method to compute the dot product with an OpenMapRealVector.
     * Iterates over the smaller of the two.
     *
     * @param v
     * 		The vector to compute the dot product with
     * @return The dot product of <code>this</code> and <code>v</code>
     * @throws IllegalArgumentException
     * 		If the dimensions don't match
     */     public double dotProduct(org.apache.commons.math.linear.OpenMapRealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());         boolean thisIsSmaller = (entries.size()) < (v.entries.size());
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = (thisIsSmaller) ? entries.iterator() : v.entries.iterator();
        org.apache.commons.math.util.OpenIntToDoubleHashMap larger = (thisIsSmaller) ? v.entries : entries;
        double d = 0;
        while (iter.hasNext()) {
            iter.advance();
            d += (iter.value()) * (larger.get(iter.key()));
        } 
        return d;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double dotProduct(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {
        if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return dotProduct(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }else {
            return super.dotProduct(v);
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.OpenMapRealVector ebeDivide(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());
        org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), ((iter.value()) / (v.getEntry(iter.key()))));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector ebeDivide(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), ((iter.value()) / (v[iter.key()])));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.OpenMapRealVector ebeMultiply(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());
        org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), ((iter.value()) * (v.getEntry(iter.key()))));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector ebeMultiply(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), ((iter.value()) * (v[iter.key()])));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.OpenMapRealVector getSubVector(int index, int n) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        checkIndex(((index + n) - 1));
        org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(n);
        int end = index + n;
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if ((key >= index) && (key < end)) {
                res.setEntry((key - index), iter.value());
            }
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] getData() {
        double[] res = new double[virtualSize];
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res[iter.key()] = iter.value();
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public int getDimension() {         return virtualSize;
    }

    /**
     * Optimized method to compute distance.
     *
     * @param v
     * 		The vector to compute distance to
     * @return The distance from <code>this</code> and <code>v</code>
     * @throws IllegalArgumentException
     * 		If the dimensions don't match
     */     public double getDistance(org.apache.commons.math.linear.OpenMapRealVector v) throws java.lang.IllegalArgumentException {         org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();         double res = 0;
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            double delta;
            delta = (iter.value()) - (v.getEntry(key));
            res += delta * delta;
        } 
        iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (!(entries.containsKey(key))) {
                final double value = iter.value();
                res += value * value;
            }
        } 
        return java.lang.Math.sqrt(res);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getDistance(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return getDistance(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }
        return getDistance(v.getData());
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getDistance(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        double res = 0;
        for (int i = 0; i < (v.length); i++) {
            double delta = (entries.get(i)) - (v[i]);
            res += delta * delta;
        }
        return java.lang.Math.sqrt(res);
    }

    /**
     * {@inheritDoc}
     */     public double getEntry(int index) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        return entries.get(index);
    }

    /**
     * Distance between two vectors.
     * <p>This method computes the distance consistent with
     * L<sub>1</sub> norm, i.e. the sum of the absolute values of
     * elements differences.</p>
     *
     * @param v
     * 		vector to which distance is requested
     * @return distance between two vectors.
     */     public double getL1Distance(org.apache.commons.math.linear.OpenMapRealVector v) {         double max = 0;
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            double delta = java.lang.Math.abs(((iter.value()) - (v.getEntry(iter.key()))));
            max += delta;
        } 
        iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (!(entries.containsKey(key))) {
                double delta = java.lang.Math.abs(iter.value());
                max += java.lang.Math.abs(delta);
            }
        } 
        return max;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getL1Distance(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return getL1Distance(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }
        return getL1Distance(v.getData());
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getL1Distance(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        double max = 0;
        for (int i = 0; i < (v.length); i++) {
            double delta = java.lang.Math.abs(((getEntry(i)) - (v[i])));
            max += delta;
        }
        return max;
    }

    /**
     * Optimized method to compute LInfDistance.
     *
     * @param v
     * 		The vector to compute from
     * @return the LInfDistance
     */     private double getLInfDistance(org.apache.commons.math.linear.OpenMapRealVector v) {         double max = 0;
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            double delta = java.lang.Math.abs(((iter.value()) - (v.getEntry(iter.key()))));
            if (delta > max) {
                max = delta;
            }
        } 
        iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (!(entries.containsKey(key))) {
                if ((iter.value()) > max) {
                    max = iter.value();
                }
            }
        } 
        return max;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getLInfDistance(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return getLInfDistance(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }
        return getLInfDistance(v.getData());
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getLInfDistance(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        double max = 0;
        for (int i = 0; i < (v.length); i++) {
            double delta = java.lang.Math.abs(((getEntry(i)) - (v[i])));
            if (delta > max) {
                max = delta;
            }
        }
        return max;
    }

    /**
     * {@inheritDoc}
     */     public boolean isInfinite() {         boolean infiniteFound = false;
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            final double value = iter.value();
            if (java.lang.Double.isNaN(value)) {
                return false;
            }
            if (java.lang.Double.isInfinite(value)) {
                infiniteFound = true;
            }
        } 
        return infiniteFound;
    }

    /**
     * {@inheritDoc}
     */     public boolean isNaN() {         org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            if (java.lang.Double.isNaN(iter.value())) {
                return true;
            }
        } 
        return false;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector mapAdd(double d) {
        return copy().mapAddToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector mapAddToSelf(double d) {
        for (int i = 0; i < (virtualSize); i++) {
            setEntry(i, ((getEntry(i)) + d));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.RealMatrix outerProduct(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        org.apache.commons.math.linear.RealMatrix res = new org.apache.commons.math.linear.OpenMapRealMatrix(virtualSize, virtualSize);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            int row = iter.key();
            double value = iter.value();
            for (int col = 0; col < (virtualSize); col++) {
                res.setEntry(row, col, (value * (v[col])));
            }
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.RealVector projection(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());
        return v.mapMultiply(((dotProduct(v)) / (v.dotProduct(v))));
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector projection(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        return ((org.apache.commons.math.linear.OpenMapRealVector) (projection(new org.apache.commons.math.linear.OpenMapRealVector(v))));
    }

    /**
     * {@inheritDoc}
     */     public void setEntry(int index, double value) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        if (!(isDefaultValue(value))) {
            entries.put(index, value);
        }else             if (entries.containsKey(index)) {
                entries.remove(index);
            }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setSubVector(int index, org.apache.commons.math.linear.RealVector v) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(index);
        checkIndex(((index + (v.getDimension())) - 1));
        setSubVector(index, v.getData());
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void setSubVector(int index, double[] v) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(index);
        checkIndex(((index + (v.length)) - 1));
        for (int i = 0; i < (v.length); i++) {
            setEntry((i + index), v[i]);
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void set(double value) {
        for (int i = 0; i < (virtualSize); i++) {
            setEntry(i, value);
        }
    }

    /**
     * Optimized method to subtract OpenMapRealVectors.
     *
     * @param v
     * 		The vector to subtract from <code>this</code>
     * @return The difference of <code>this</code> and <code>v</code>
     * @throws IllegalArgumentException
     * 		If the dimensions don't match
     */     public org.apache.commons.math.linear.OpenMapRealVector subtract(org.apache.commons.math.linear.OpenMapRealVector v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());         org.apache.commons.math.linear.OpenMapRealVector res = copy();
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (entries.containsKey(key)) {
                res.setEntry(key, ((entries.get(key)) - (iter.value())));
            }else {
                res.setEntry(key, (-(iter.value())));
            }
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector subtract(org.apache.commons.math.linear.RealVector v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        if (v instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return subtract(((org.apache.commons.math.linear.OpenMapRealVector) (v)));
        }
        return subtract(v.getData());
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector subtract(double[] v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.length);
        org.apache.commons.math.linear.OpenMapRealVector res = new org.apache.commons.math.linear.OpenMapRealVector(this);
        for (int i = 0; i < (v.length); i++) {
            if (entries.containsKey(i)) {
                res.setEntry(i, ((entries.get(i)) - (v[i])));
            }else {
                res.setEntry(i, (-(v[i])));
            }
        }
        return res;
    }


    /**
     * {@inheritDoc}
     */     @java.lang.Override     public org.apache.commons.math.linear.OpenMapRealVector unitVector() {
        org.apache.commons.math.linear.OpenMapRealVector res = copy();
        res.unitize();
        return res;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void unitize() {
        double norm = getNorm();
        if (isDefaultValue(norm)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.util.LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR);
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            entries.put(iter.key(), ((iter.value()) / norm));
        } 

    }


    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] toArray() {
        return getData();
    }

    /**
     * {@inheritDoc}
     * <p> Implementation Note: This works on exact values, and as a result
     * it is possible for {@code a.subtract(b)} to be the zero vector, while
     * {@code a.hashCode() != b.hashCode()}.</p>
     */     @java.lang.Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = java.lang.Double.doubleToLongBits(epsilon);
        result = (prime * result) + ((int) (temp ^ (temp >>> 32)));
        result = (prime * result) + (virtualSize);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            temp = java.lang.Double.doubleToLongBits(iter.value());
            result = (prime * result) + ((int) (temp ^ (temp >> 32)));
        } 
        return result;
    }

    /**
     * <p> Implementation Note: This performs an exact comparison, and as a result
     * it is possible for {@code a.subtract(b}} to be the zero vector, while
     * {@code  a.equals(b) == false}.</p>
     * {@inheritDoc}
     */
    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if ((this) == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.linear.OpenMapRealVector)) {
            return false;
        }
        org.apache.commons.math.linear.OpenMapRealVector other = ((org.apache.commons.math.linear.OpenMapRealVector) (obj));
        if ((virtualSize) != (other.virtualSize)) {
            return false;
        }
        if ((java.lang.Double.doubleToLongBits(epsilon)) != 
        (java.lang.Double.doubleToLongBits(other.epsilon))) {
            return false;
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            double test = other.getEntry(iter.key());
            if ((java.lang.Double.doubleToLongBits(test)) != (java.lang.Double.doubleToLongBits(iter.value()))) {
                return false;
            }
        } 
        iter = other.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            double test = iter.value();
            if ((java.lang.Double.doubleToLongBits(test)) != (java.lang.Double.doubleToLongBits(getEntry(iter.key())))) {
                return false;
            }
        } 
        return true;
    }

    /**
     *
     *
     * @return the percentage of none zero elements as a decimal percent.
     * @deprecated Use the correctly spelled {@link #getSparsity()}
     */     @java.lang.Deprecated
    public double getSparcity() {
        return getSparsity();
    }

    /**
     *
     *
     * @return the percentage of none zero elements as a decimal percent.
     */     public double getSparsity() {
        return ((double) (entries.size())) / ((double) (getDimension()));
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator() {
        return new org.apache.commons.math.linear.OpenMapRealVector.OpenMapSparseIterator();
    }

    /**
     * Implementation of <code>Entry</code> optimized for OpenMap.
     * <p>This implementation does not allow arbitrary calls to <code>setIndex</code>
     * since the order that entries are returned is undefined.
     */
    protected class OpenMapEntry extends org.apache.commons.math.linear.RealVector.Entry {

        /**
         * Iterator pointing to the entry.
         */         private final org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter;
        /**
         * Build an entry from an iterator point to an element.
         *
         * @param iter
         * 		iterator pointing to the entry
         */         protected OpenMapEntry(org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter) {             this.iter = iter;}

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public double getValue() {
            return iter.value();
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public void setValue(double value) {
            entries.put(iter.key(), value);
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public int getIndex() {
            return iter.key();
        }

    }

    /**
     * Iterator class to do iteration over just the non-zero elements.
     *  <p>This implementation is fail-fast, so cannot be used to modify any zero element.
     */

    protected class OpenMapSparseIterator implements java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> {

        /**
         * Underlying iterator.
         */         private final org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter;
        /**
         * Current entry.
         */         private final org.apache.commons.math.linear.RealVector.Entry current;
        /**
         * Simple constructor.
         */         protected OpenMapSparseIterator() {             iter = entries.iterator();
            current = new org.apache.commons.math.linear.OpenMapRealVector.OpenMapEntry(iter);
        }

        /**
         * {@inheritDoc}
         */         public boolean hasNext() {             return iter.hasNext();
        }

        /**
         * {@inheritDoc}
         */         public org.apache.commons.math.linear.RealVector.Entry next() {             iter.advance();
            return current;
        }

        /**
         * {@inheritDoc}
         */         public void remove() {             throw new java.lang.UnsupportedOperationException("Not supported");
        }

    }
}