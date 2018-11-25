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
 * Open addressed map from int to double.
 * <p>This class provides a dedicated map from integers to doubles with a
 * much smaller memory overhead than standard <code>java.util.Map</code>.</p>
 * <p>This class is not synchronized. The specialized iterators returned by
 * {@link #iterator()} are fail-fast: they throw a
 * <code>ConcurrentModificationException</code> when they detect the map has been
 * modified during iteration.</p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class OpenIntToDoubleHashMap implements java.io.Serializable {

    /**
     * Status indicator for free table entries.
     */     protected static final byte FREE = 0;
    /**
     * Status indicator for full table entries.
     */     protected static final byte FULL = 1;
    /**
     * Status indicator for removed table entries.
     */     protected static final byte REMOVED = 2;
    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -3646337053166149105L;
    /**
     * Load factor for the map.
     */     private static final float LOAD_FACTOR = 0.5F;
    /**
     * Default starting size.
     * <p>This must be a power of two for bit mask to work properly. </p>
     */     private static final int DEFAULT_EXPECTED_SIZE = 16;

    /**
     * Multiplier for size growth when map fills up.
     * <p>This must be a power of two for bit mask to work properly. </p>
     */     private static final int RESIZE_MULTIPLIER = 2;

    /**
     * Number of bits to perturb the index when probing for collision resolution.
     */     private static final int PERTURB_SHIFT = 5;
    /**
     * Keys table.
     */     private int[] keys;
    /**
     * Values table.
     */     private double[] values;
    /**
     * States table.
     */     private byte[] states;
    /**
     * Return value for missing entries.
     */     private final double missingEntries;
    /**
     * Current size of the map.
     */     private int size;
    /**
     * Bit mask for hash values.
     */     private int mask;
    /**
     * Modifications count.
     */     private transient int count;
    /**
     * Build an empty map with default size and using NaN for missing entries.
     */
    public OpenIntToDoubleHashMap() {
        this(org.apache.commons.math.util.OpenIntToDoubleHashMap.DEFAULT_EXPECTED_SIZE, java.lang.Double.NaN);
    }

    /**
     * Build an empty map with default size
     *
     * @param missingEntries
     * 		value to return when a missing entry is fetched
     */     public OpenIntToDoubleHashMap(final double missingEntries) {         this(org.apache.commons.math.util.OpenIntToDoubleHashMap.DEFAULT_EXPECTED_SIZE, missingEntries);
    }

    /**
     * Build an empty map with specified size and using NaN for missing entries.
     *
     * @param expectedSize
     * 		expected number of elements in the map
     */     public OpenIntToDoubleHashMap(final int expectedSize) {         this(expectedSize, java.lang.Double.NaN);
    }

    /**
     * Build an empty map with specified size.
     *
     * @param expectedSize
     * 		expected number of elements in the map
     * @param missingEntries
     * 		value to return when a missing entry is fetched
     */     public OpenIntToDoubleHashMap(final int expectedSize, final double missingEntries) {         final int capacity = org.apache.commons.math.util.OpenIntToDoubleHashMap.computeCapacity(expectedSize);
        keys = new int[capacity];
        values = new double[capacity];
        states = new byte[capacity];
        this.missingEntries = missingEntries;
        mask = capacity - 1;
    }

    /**
     * Copy constructor.
     *
     * @param source
     * 		map to copy
     */     public OpenIntToDoubleHashMap(final org.apache.commons.math.util.OpenIntToDoubleHashMap source) {         final int length = source.keys.length;
        keys = new int[length];
        java.lang.System.arraycopy(source.keys, 0, keys, 0, length);
        values = new double[length];
        java.lang.System.arraycopy(source.values, 0, values, 0, length);
        states = new byte[length];
        java.lang.System.arraycopy(source.states, 0, states, 0, length);
        missingEntries = source.missingEntries;
        size = source.size;
        mask = source.mask;
        count = source.count;
    }

    /**
     * Compute the capacity needed for a given size.
     *
     * @param expectedSize
     * 		expected size of the map
     * @return capacity to use for the specified size
     */     private static int computeCapacity(final int expectedSize) {         if (expectedSize == 0) {
            return 1;
        }
        final int capacity = ((int) (java.lang.Math.ceil((expectedSize / (org.apache.commons.math.util.OpenIntToDoubleHashMap.LOAD_FACTOR)))));
        final int powerOfTwo = java.lang.Integer.highestOneBit(capacity);
        if (powerOfTwo == capacity) {
            return capacity;
        }
        return org.apache.commons.math.util.OpenIntToDoubleHashMap.nextPowerOfTwo(capacity);
    }

    /**
     * Find the smallest power of two greater than the input value
     *
     * @param i
     * 		input value
     * @return smallest power of two greater than the input value
     */     private static int nextPowerOfTwo(final int i) {         return (java.lang.Integer.highestOneBit(i)) << 1;
    }

    /**
     * Get the stored value associated with the given key
     *
     * @param key
     * 		key associated with the data
     * @return data associated with the key
     */     public double get(final int key) {
        final int hash = org.apache.commons.math.util.OpenIntToDoubleHashMap.hashOf(key);
        int index = hash & (mask);
        if (containsKey(key, index)) {
            return values[index];
        }

        if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE)) {
            return missingEntries;
        }

        int j = index;
        for (int perturb = org.apache.commons.math.util.OpenIntToDoubleHashMap.perturb(hash); (states[index]) != (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE); perturb >>= org.apache.commons.math.util.OpenIntToDoubleHashMap.PERTURB_SHIFT) {
            j = org.apache.commons.math.util.OpenIntToDoubleHashMap.probe(perturb, j);
            index = j & (mask);
            if (containsKey(key, index)) {
                return values[index];
            }
        }

        return missingEntries;

    }

    /**
     * Check if a value is associated with a key.
     *
     * @param key
     * 		key to check
     * @return true if a value is associated with key
     */     public boolean containsKey(final int key) {
        final int hash = org.apache.commons.math.util.OpenIntToDoubleHashMap.hashOf(key);
        int index = hash & (mask);
        if (containsKey(key, index)) {
            return true;
        }

        if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE)) {
            return false;
        }

        int j = index;
        for (int perturb = org.apache.commons.math.util.OpenIntToDoubleHashMap.perturb(hash); (states[index]) != (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE); perturb >>= org.apache.commons.math.util.OpenIntToDoubleHashMap.PERTURB_SHIFT) {
            j = org.apache.commons.math.util.OpenIntToDoubleHashMap.probe(perturb, j);
            index = j & (mask);
            if (containsKey(key, index)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Get an iterator over map elements.
     * <p>The specialized iterators returned are fail-fast: they throw a
     * <code>ConcurrentModificationException</code> when they detect the map
     * has been modified during iteration.</p>
     *
     * @return iterator over the map elements
     */     public org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator() {
        return new org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator();
    }

    /**
     * Perturb the hash for starting probing.
     *
     * @param hash
     * 		initial hash
     * @return perturbed hash
     */     private static int perturb(final int hash) {         return hash & 2147483647;
    }

    /**
     * Find the index at which a key should be inserted
     *
     * @param key
     * 		key to lookup
     * @return index at which key should be inserted
     */     private int findInsertionIndex(final int key) {         return org.apache.commons.math.util.OpenIntToDoubleHashMap.findInsertionIndex(keys, states, key, mask);
    }

    /**
     * Find the index at which a key should be inserted
     *
     * @param keys
     * 		keys table
     * @param states
     * 		states table
     * @param key
     * 		key to lookup
     * @param mask
     * 		bit mask for hash values
     * @return index at which key should be inserted
     */     private static int findInsertionIndex(final int[] keys, final byte[] states, final int key, final int mask) {         final int hash = org.apache.commons.math.util.OpenIntToDoubleHashMap.hashOf(key);         int index = hash & mask;         if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE)) {
            return index;
        }else             if (((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) && ((keys[index]) == key)) {
                return org.apache.commons.math.util.OpenIntToDoubleHashMap.changeIndexSign(index);
            }

        int perturb = org.apache.commons.math.util.OpenIntToDoubleHashMap.perturb(hash);
        int j = index;
        if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) {
            while (true) {
                j = org.apache.commons.math.util.OpenIntToDoubleHashMap.probe(perturb, j);
                index = j & mask;
                perturb >>= org.apache.commons.math.util.OpenIntToDoubleHashMap.PERTURB_SHIFT;

                if (((states[index]) != (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) || ((keys[index]) == key)) {
                    break;
                }
            } 
        }




        // due to the loop exit condition,
        // if (states[index] == FULL) then keys[index] == key
        if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE)) {             return index;}// due to the loop exit condition,         // if (states[index] == FULL) then keys[index] == key
        else             if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) {                 return org.apache.commons.math.util.OpenIntToDoubleHashMap.changeIndexSign(index);}

        final int firstRemoved = index;
        while (true) {
            j = org.apache.commons.math.util.OpenIntToDoubleHashMap.probe(perturb, j);
            index = j & mask;

            if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE)) {
                return firstRemoved;
            }else                 if (((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) && ((keys[index]) == key)) {
                    return org.apache.commons.math.util.OpenIntToDoubleHashMap.changeIndexSign(index);
                }

            perturb >>= org.apache.commons.math.util.OpenIntToDoubleHashMap.PERTURB_SHIFT;

        } 

    }

    /**
     * Compute next probe for collision resolution
     *
     * @param perturb
     * 		perturbed hash
     * @param j
     * 		previous probe
     * @return next probe
     */     private static int probe(final int perturb, final int j) {         return (((j << 2) + j) + perturb) + 1;}

    /**
     * Change the index sign
     *
     * @param index
     * 		initial index
     * @return changed index
     */     private static int changeIndexSign(final int index) {         return (-index) - 1;
    }

    /**
     * Get the number of elements stored in the map.
     *
     * @return number of elements stored in the map
     */     public int size() {
        return size;
    }


    /**
     * Remove the value associated with a key.
     *
     * @param key
     * 		key to which the value is associated
     * @return removed value
     */     public double remove(final int key) {
        final int hash = org.apache.commons.math.util.OpenIntToDoubleHashMap.hashOf(key);
        int index = hash & (mask);
        if (containsKey(key, index)) {
            return doRemove(index);
        }

        if ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE)) {
            return missingEntries;
        }

        int j = index;
        for (int perturb = org.apache.commons.math.util.OpenIntToDoubleHashMap.perturb(hash); (states[index]) != (org.apache.commons.math.util.OpenIntToDoubleHashMap.FREE); perturb >>= org.apache.commons.math.util.OpenIntToDoubleHashMap.PERTURB_SHIFT) {
            j = org.apache.commons.math.util.OpenIntToDoubleHashMap.probe(perturb, j);
            index = j & (mask);
            if (containsKey(key, index)) {
                return doRemove(index);
            }
        }

        return missingEntries;

    }

    /**
     * Check if the tables contain an element associated with specified key
     * at specified index.
     *
     * @param key
     * 		key to check
     * @param index
     * 		index to check
     * @return true if an element is associated with key at index
     */     private boolean containsKey(final int key, final int index) {         return ((key != 0) || ((states[index]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL))) && ((keys[index]) == key);}

    /**
     * Remove an element at specified index.
     *
     * @param index
     * 		index of the element to remove
     * @return removed value
     */     private double doRemove(int index) {         keys[index] = 0;
        states[index] = org.apache.commons.math.util.OpenIntToDoubleHashMap.REMOVED;
        final double previous = values[index];
        values[index] = missingEntries;
        --(size);
        ++(count);
        return previous;
    }

    /**
     * Put a value associated with a key in the map.
     *
     * @param key
     * 		key to which value is associated
     * @param value
     * 		value to put in the map
     * @return previous value associated with the key
     */     public double put(final int key, final double value) {         int index = findInsertionIndex(key);         double previous = missingEntries;
        boolean newMapping = true;
        if (index < 0) {
            index = org.apache.commons.math.util.OpenIntToDoubleHashMap.changeIndexSign(index);
            previous = values[index];
            newMapping = false;
        }
        keys[index] = key;
        states[index] = org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL;
        values[index] = value;
        if (newMapping) {
            ++(size);
            if (shouldGrowTable()) {
                growTable();
            }
            ++(count);
        }
        return previous;

    }

    /**
     * Grow the tables.
     */
    private void growTable() {

        final int oldLength = states.length;
        final int[] oldKeys = keys;
        final double[] oldValues = values;
        final byte[] oldStates = states;

        final int newLength = (org.apache.commons.math.util.OpenIntToDoubleHashMap.RESIZE_MULTIPLIER) * oldLength;
        final int[] newKeys = new int[newLength];
        final double[] newValues = new double[newLength];
        final byte[] newStates = new byte[newLength];
        final int newMask = newLength - 1;
        for (int i = 0; i < oldLength; ++i) {
            if ((oldStates[i]) == (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) {
                final int key = oldKeys[i];
                final int index = org.apache.commons.math.util.OpenIntToDoubleHashMap.findInsertionIndex(newKeys, newStates, key, newMask);
                newKeys[index] = key;
                newValues[index] = oldValues[i];
                newStates[index] = org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL;
            }
        }

        mask = newMask;
        keys = newKeys;
        values = newValues;
        states = newStates;

    }

    /**
     * Check if tables should grow due to increased size.
     *
     * @return true if  tables should grow
     */     private boolean shouldGrowTable() {
        return (size) > (((mask) + 1) * (org.apache.commons.math.util.OpenIntToDoubleHashMap.LOAD_FACTOR));
    }

    /**
     * Compute the hash value of a key
     *
     * @param key
     * 		key to hash
     * @return hash value of the key
     */     private static int hashOf(final int key) {         final int h = key ^ ((key >>> 20) ^ (key >>> 12));
        return (h ^ (h >>> 7)) ^ (h >>> 4);
    }


    /**
     * Iterator class for the map.
     */     public class Iterator {
        /**
         * Reference modification count.
         */         private final int referenceCount;
        /**
         * Index of current element.
         */         private int current;
        /**
         * Index of next element.
         */         private int next;
        /**
         * Simple constructor.
         */
        private Iterator() {

            // preserve the modification count of the map to detect concurrent modifications later
            referenceCount = count;

            // initialize current index
            next = -1;
            try {
                advance();
            } catch (java.util.NoSuchElementException nsee) {
                // ignored
            }

        }

        /**
         * Check if there is a next element in the map.
         *
         * @return true if there is a next element
         */         public boolean hasNext() {
            return (next) >= 0;
        }

        /**
         * Get the key of current entry.
         *
         * @return key of current entry
         * @exception ConcurrentModificationException if the map is modified during iteration
         * @exception NoSuchElementException if there is no element left in the map
         */         public int key() throws 
        java.util.ConcurrentModificationException, java.util.NoSuchElementException {
            if ((referenceCount) != (count)) {
                throw org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(org.apache.commons.math.util.LocalizedFormats.MAP_MODIFIED_WHILE_ITERATING);
            }
            if ((current) < 0) {
                throw org.apache.commons.math.MathRuntimeException.createNoSuchElementException(org.apache.commons.math.util.LocalizedFormats.ITERATOR_EXHAUSTED);
            }
            return keys[current];
        }

        /**
         * Get the value of current entry.
         *
         * @return value of current entry
         * @exception ConcurrentModificationException if the map is modified during iteration
         * @exception NoSuchElementException if there is no element left in the map
         */         public double value() throws 
        java.util.ConcurrentModificationException, java.util.NoSuchElementException {
            if ((referenceCount) != (count)) {
                throw org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(org.apache.commons.math.util.LocalizedFormats.MAP_MODIFIED_WHILE_ITERATING);
            }
            if ((current) < 0) {
                throw org.apache.commons.math.MathRuntimeException.createNoSuchElementException(org.apache.commons.math.util.LocalizedFormats.ITERATOR_EXHAUSTED);
            }
            return values[current];
        }

        /**
         * Advance iterator one step further.
         *
         * @exception ConcurrentModificationException if the map is modified during iteration
         * @exception NoSuchElementException if there is no element left in the map
         */         public void advance() throws 
        java.util.ConcurrentModificationException, java.util.NoSuchElementException {

            if ((referenceCount) != (count)) {
                throw org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(org.apache.commons.math.util.LocalizedFormats.MAP_MODIFIED_WHILE_ITERATING);
            }

            // advance on step
            current = next;

            // prepare next step
            try {
                while ((states[(++(next))]) != (org.apache.commons.math.util.OpenIntToDoubleHashMap.FULL)) {
                    // nothing to do
                } 
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                next = -2;
                if ((current) < 0) {
                    throw org.apache.commons.math.MathRuntimeException.createNoSuchElementException(org.apache.commons.math.util.LocalizedFormats.ITERATOR_EXHAUSTED);
                }
            }

        }

    }

    /**
     * Read a serialized object.
     *
     * @param stream
     * 		input stream
     * @throws IOException
     * 		if object cannot be read
     * @throws ClassNotFoundException
     * 		if the class corresponding
     * 		to the serialized object cannot be found
     */     private void readObject(final java.io.ObjectInputStream stream) throws java.io.IOException, java.lang.ClassNotFoundException {         stream.defaultReadObject();         count = 0;
    }


}