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
 * This class implements the {@link FieldVector} interface with a {@link OpenIntToFieldHashMap} backing store.
 *
 * @param <T>
 * 		the type of the field elements
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class SparseFieldVector<T extends org.apache.commons.math.FieldElement<T>> implements java.io.Serializable , org.apache.commons.math.linear.FieldVector<T> {
    /**
     * Serial version id
     */
    private static final long serialVersionUID = 7841233292190413362L;
    /**
     * Field to which the elements belong.
     */     private final org.apache.commons.math.Field<T> field;     /**
     * Entries of the vector.
     */     private final org.apache.commons.math.util.OpenIntToFieldHashMap<T> entries;     /**
     * Dimension of the vector.
     */     private final int virtualSize;
    /**
     * Build a 0-length vector.
     * <p>Zero-length vectors may be used to initialize construction of vectors
     * by data gathering. We start with zero-length and use either the {@link
     * #SparseFieldVector(SparseFieldVector, int)} constructor
     * or one of the <code>append</code> method ({@link #append(FieldElement)},
     * {@link #append(FieldElement[])}, {@link #append(FieldVector)},
     * {@link #append(SparseFieldVector)}) to gather data into this vector.</p>
     *
     * @param field
     * 		field to which the elements belong
     */     public SparseFieldVector(org.apache.commons.math.Field<T> field) {         this(field, 0);
    }


    /**
     * Construct a (dimension)-length vector of zeros.
     *
     * @param field
     * 		field to which the elements belong
     * @param dimension
     * 		Size of the vector
     */     public SparseFieldVector(org.apache.commons.math.Field<T> field, int dimension) {         this.field = field;         virtualSize = dimension;
        entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<T>(field);
    }

    /**
     * Build a resized vector, for use with append.
     *
     * @param v
     * 		The original vector
     * @param resize
     * 		The amount to resize it
     */     protected SparseFieldVector(org.apache.commons.math.linear.SparseFieldVector<T> v, int resize) {         field = v.field;         virtualSize = (v.getDimension()) + resize;
        entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<T>(v.entries);
    }


    /**
     * Build a vector with known the sparseness (for advanced use only).
     *
     * @param field
     * 		field to which the elements belong
     * @param dimension
     * 		The size of the vector
     * @param expectedSize
     * 		The expected number of non-zero entries
     */     public SparseFieldVector(org.apache.commons.math.Field<T> field, int dimension, int expectedSize) {         this.field = field;         virtualSize = dimension;         entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<T>(field, expectedSize);
    }

    /**
     * Create from a Field array.
     * Only non-zero entries will be stored
     *
     * @param field
     * 		field to which the elements belong
     * @param values
     * 		The set of values to create from
     */     public SparseFieldVector(org.apache.commons.math.Field<T> field, T[] values) {         this.field = field;         virtualSize = values.length;
        entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<T>(field);
        for (int key = 0; key < (values.length); key++) {
            T value = values[key];
            entries.put(key, value);
        }
    }



    /**
     * Copy constructor.
     *
     * @param v
     * 		The instance to copy from
     */     public SparseFieldVector(org.apache.commons.math.linear.SparseFieldVector<T> v) {         field = v.field;
        virtualSize = v.getDimension();
        entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<T>(v.getEntries());
    }

    /**
     * Get the entries of this instance.
     *
     * @return entries of this instance
     */     private org.apache.commons.math.util.OpenIntToFieldHashMap<T> getEntries() {
        return entries;
    }

    /**
     * Optimized method to add sparse vectors.
     *
     * @param v
     * 		vector to add
     * @return The sum of <code>this</code> and <code>v</code>
     * @throws IllegalArgumentException
     * 		If the dimensions don't match
     */     public org.apache.commons.math.linear.FieldVector<T> add(org.apache.commons.math.linear.SparseFieldVector<T> v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());         org.apache.commons.math.linear.SparseFieldVector<T> res = ((org.apache.commons.math.linear.SparseFieldVector<T>) (copy()));
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            T value = iter.value();
            if (entries.containsKey(key)) {
                res.setEntry(key, entries.get(key).add(value));
            }else {
                res.setEntry(key, value);
            }
        } 
        return res;

    }


    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> add(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(field, getDimension());
        for (int i = 0; i < (v.length); i++) {
            res.setEntry(i, v[i].add(getEntry(i)));
        }
        return res;
    }

    /**
     * Construct a vector by appending a vector to this vector.
     *
     * @param v
     * 		vector to append to this one.
     * @return a new vector
     */     public org.apache.commons.math.linear.FieldVector<T> append(org.apache.commons.math.linear.SparseFieldVector<T> v) {         org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this, v.getDimension());
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = v.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(((iter.key()) + (virtualSize)), iter.value());
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> append(org.apache.commons.math.linear.FieldVector<T> v) {         if (v instanceof org.apache.commons.math.linear.SparseFieldVector<?>) {
            return append(((org.apache.commons.math.linear.SparseFieldVector<T>) (v)));
        }else {
            return append(v.toArray());
        }
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> append(T d) {         org.apache.commons.math.linear.FieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this, 1);
        res.setEntry(virtualSize, d);
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> append(T[] a) {         org.apache.commons.math.linear.FieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this, a.length);
        for (int i = 0; i < (a.length); i++) {
            res.setEntry((i + (virtualSize)), a[i]);
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> copy() {         return new org.apache.commons.math.linear.SparseFieldVector<T>(this);
    }

    /**
     * {@inheritDoc}
     */     public T dotProduct(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());
        T res = field.getZero();
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res = res.add(v.getEntry(iter.key()).multiply(iter.value()));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public T dotProduct(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        T res = field.getZero();
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            int idx = iter.key();
            T value = field.getZero();
            if (idx < (v.length)) {
                value = v[idx];
            }
            res = res.add(value.multiply(iter.value()));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> ebeDivide(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), iter.value().divide(v.getEntry(iter.key())));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> ebeDivide(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), iter.value().divide(v[iter.key()]));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> ebeMultiply(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), iter.value().multiply(v.getEntry(iter.key())));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> ebeMultiply(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), iter.value().multiply(v[iter.key()]));
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public T[] getData() {         T[] res = buildArray(virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
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
     * {@inheritDoc}
     */     public T getEntry(int index) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        return entries.get(index);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.Field<T> getField() {         return field;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> getSubVector(int index, int n) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(index);
        checkIndex(((index + n) - 1));
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(field, n);
        int end = index + n;
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
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
     */     public org.apache.commons.math.linear.FieldVector<T> mapAdd(T d) {         return copy().mapAddToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapAddToSelf(T d) {         for (int i = 0; i < (virtualSize); i++) {
            setEntry(i, getEntry(i).add(d));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapDivide(T d) {         return copy().mapDivideToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapDivideToSelf(T d) {         org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            entries.put(iter.key(), iter.value().divide(d));
        } 
        return this;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapInv() {         return copy().mapInvToSelf();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapInvToSelf() {         for (int i = 0; i < (virtualSize); i++) {
            setEntry(i, field.getOne().divide(getEntry(i)));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapMultiply(T d) {         return copy().mapMultiplyToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapMultiplyToSelf(T d) {         org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            entries.put(iter.key(), iter.value().multiply(d));
        } 
        return this;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapSubtract(T d) {         return copy().mapSubtractToSelf(d);
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> mapSubtractToSelf(T d) {         return mapAddToSelf(field.getZero().subtract(d));
    }

    /**
     * Optimized method to compute outer product when both vectors are sparse.
     *
     * @param v
     * 		vector with which outer product should be computed
     * @return the square matrix outer product between instance and v
     * @throws IllegalArgumentException
     * 		if v is not the same size as {@code this}
     */     public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(org.apache.commons.math.linear.SparseFieldVector<T> v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());
        org.apache.commons.math.linear.SparseFieldMatrix<T> res = new org.apache.commons.math.linear.SparseFieldMatrix<T>(field, virtualSize, virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter2 = v.entries.iterator();
            while (iter2.hasNext()) {
                iter2.advance();
                res.setEntry(iter.key(), iter2.key(), iter.value().multiply(iter2.value()));
            } 
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        org.apache.commons.math.linear.FieldMatrix<T> res = new org.apache.commons.math.linear.SparseFieldMatrix<T>(field, virtualSize, virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            int row = iter.key();
            org.apache.commons.math.FieldElement<T> value = iter.value();
            for (int col = 0; col < (virtualSize); col++) {
                res.setEntry(row, col, value.multiply(v[col]));
            }
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {
        if (v instanceof org.apache.commons.math.linear.SparseFieldVector<?>)
            return outerProduct(((org.apache.commons.math.linear.SparseFieldVector<T>) (v)));else

            return outerProduct(v.toArray());
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> projection(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(v.getDimension());
        return v.mapMultiply(dotProduct(v).divide(v.dotProduct(v)));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> projection(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        return projection(new org.apache.commons.math.linear.SparseFieldVector<T>(field, v));
    }

    /**
     * {@inheritDoc}
     */     public void set(T value) {         for (int i = 0; i < (virtualSize); i++) {
            setEntry(i, value);
        }
    }

    /**
     * {@inheritDoc}
     */     public void setEntry(int index, T value) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        entries.put(index, value);
    }

    /**
     * {@inheritDoc}
     */     public void setSubVector(int index, org.apache.commons.math.linear.FieldVector<T> v) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(index);
        checkIndex(((index + (v.getDimension())) - 1));
        setSubVector(index, v.getData());
    }

    /**
     * {@inheritDoc}
     */     public void setSubVector(int index, T[] v) throws org.apache.commons.math.linear.MatrixIndexException {         checkIndex(index);
        checkIndex(((index + (v.length)) - 1));
        for (int i = 0; i < (v.length); i++) {
            setEntry((i + index), v[i]);
        }

    }

    /**
     * Optimized method to subtract SparseRealVectors.
     *
     * @param v
     * 		The vector to subtract from <code>this</code>
     * @return The difference of <code>this</code> and <code>v</code>
     * @throws IllegalArgumentException
     * 		If the dimensions don't match
     */     public org.apache.commons.math.linear.SparseFieldVector<T> subtract(org.apache.commons.math.linear.SparseFieldVector<T> v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.getDimension());         org.apache.commons.math.linear.SparseFieldVector<T> res = ((org.apache.commons.math.linear.SparseFieldVector<T>) (copy()));
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (entries.containsKey(key)) {
                res.setEntry(key, entries.get(key).subtract(iter.value()));
            }else {
                res.setEntry(key, field.getZero().subtract(iter.value()));
            }
        } 
        return res;
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> subtract(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {
        if (v instanceof org.apache.commons.math.linear.SparseFieldVector<?>)
            return subtract(((org.apache.commons.math.linear.SparseFieldVector<T>) (v)));else

            return subtract(v.toArray());
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> subtract(T[] v) throws java.lang.IllegalArgumentException {         checkVectorDimensions(v.length);
        org.apache.commons.math.linear.SparseFieldVector<T> res = new org.apache.commons.math.linear.SparseFieldVector<T>(this);
        for (int i = 0; i < (v.length); i++) {
            if (entries.containsKey(i)) {
                res.setEntry(i, entries.get(i).subtract(v[i]));
            }else {
                res.setEntry(i, field.getZero().subtract(v[i]));
            }
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */     public T[] toArray() {         return getData();
    }

    /**
     * Check if an index is valid.
     *
     * @param index
     * 		index to check
     * @exception MatrixIndexException
     * if index is not valid
     */
    private void checkIndex(final int index) throws org.apache.commons.math.linear.MatrixIndexException {
        if ((index < 0) || (index >= (getDimension()))) {
            throw new org.apache.commons.math.linear.MatrixIndexException(
            "index {0} out of allowed range [{1}, {2}]", 
            index, 0, ((getDimension()) - 1));
        }
    }

    /**
     * Check if instance dimension is equal to some expected value.
     *
     * @param n
     * 		expected dimension.
     * @exception IllegalArgumentException
     * if the dimension is inconsistent with vector size
     */
    protected void checkVectorDimensions(int n) throws java.lang.IllegalArgumentException {
        if ((getDimension()) != n) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, 
            getDimension(), n);
        }
    }


    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.linear.FieldVector<T> add(org.apache.commons.math.linear.FieldVector<T> v) throws java.lang.IllegalArgumentException {         if (v instanceof org.apache.commons.math.linear.SparseFieldVector<?>) {
            return add(((org.apache.commons.math.linear.SparseFieldVector<T>) (v)));
        }else {
            return add(v.toArray());
        }
    }

    /**
     * Build an array of elements.
     *
     * @param length
     * 		size of the array to build
     * @return a new array
     */     // field is type T     @java.lang.SuppressWarnings("unchecked")     private T[] buildArray(final int length) {         return ((T[]) (java.lang.reflect.Array.newInstance(field.getZero().getClass(), length)));
    }


    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((field) == null ? 0 : field.hashCode());
        result = (prime * result) + (virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            int temp = iter.value().hashCode();
            result = (prime * result) + temp;
        } 
        return result;
    }


    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(java.lang.Object obj) {

        if ((this) == obj) {
            return true;
        }

        if (!(obj instanceof org.apache.commons.math.linear.SparseFieldVector<?>)) {
            return false;
        }

        // OK, because "else if" check below ensures that
        // other must be the same type as this         @java.lang.SuppressWarnings("unchecked")
        org.apache.commons.math.linear.SparseFieldVector<T> other = ((org.apache.commons.math.linear.SparseFieldVector<T>) (obj));
        if ((field) == null) {
            if ((other.field) != null) {
                return false;
            }
        }else             if (!(field.equals(other.field))) {
                return false;
            }
        if ((virtualSize) != (other.virtualSize)) {
            return false;
        }

        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            T test = other.getEntry(iter.key());
            if (!(test.equals(iter.value()))) {
                return false;
            }
        } 
        iter = other.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            T test = iter.value();
            if (!(test.equals(getEntry(iter.key())))) {
                return false;
            }
        } 
        return true;
    }



}