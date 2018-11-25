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
 * Arbitrary precision decimal number.
 * <p>
 * This class is a simple wrapper around the standard <code>BigDecimal</code>
 * in order to implement the {@link FieldElement} interface.
 * </p>
 *
 * @since 2.0
 * @version $Revision$ $Date$
 */ public class BigReal implements java.io.Serializable , java.lang.Comparable<org.apache.commons.math.util.BigReal> , org.apache.commons.math.FieldElement<org.apache.commons.math.util.BigReal> {

    /**
     * A big real representing 0.
     */     public static final org.apache.commons.math.util.BigReal ZERO = new org.apache.commons.math.util.BigReal(java.math.BigDecimal.ZERO);
    /**
     * A big real representing 1.
     */     public static final org.apache.commons.math.util.BigReal ONE = new org.apache.commons.math.util.BigReal(java.math.BigDecimal.ONE);
    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 4984534880991310382L;
    /**
     * Underlying BigDecimal.
     */     private final java.math.BigDecimal d;
    /**
     * Rounding mode for divisions. *
     */     private java.math.RoundingMode roundingMode = java.math.RoundingMode.HALF_UP;
    /**
     * * BigDecimal scale **
     */     private int scale = 64;
    /**
     * Build an instance from a BigDecimal.
     *
     * @param val
     * 		value of the instance
     */     public BigReal(java.math.BigDecimal val) {         d = val;}

    /**
     * Build an instance from a BigInteger.
     *
     * @param val
     * 		value of the instance
     */     public BigReal(java.math.BigInteger val) {         d = new java.math.BigDecimal(val);}

    /**
     * Build an instance from an unscaled BigInteger.
     *
     * @param unscaledVal
     * 		unscaled value
     * @param scale
     * 		scale to use
     */     public BigReal(java.math.BigInteger unscaledVal, int scale) {         d = new java.math.BigDecimal(unscaledVal, scale);}
    /**
     * Build an instance from an unscaled BigInteger.
     *
     * @param unscaledVal
     * 		unscaled value
     * @param scale
     * 		scale to use
     * @param mc
     * 		to used
     */     public BigReal(java.math.BigInteger unscaledVal, int scale, java.math.MathContext mc) {         d = new java.math.BigDecimal(unscaledVal, scale, mc);}     /**
     * Build an instance from a BigInteger.
     *
     * @param val
     * 		value of the instance
     * @param mc
     * 		context to use
     */     public BigReal(java.math.BigInteger val, java.math.MathContext mc) {         d = new java.math.BigDecimal(val, mc);}
    /**
     * Build an instance from a characters representation.
     *
     * @param in
     * 		character representation of the value
     */     public BigReal(char[] in) {         d = new java.math.BigDecimal(in);}

    /**
     * Build an instance from a characters representation.
     *
     * @param in
     * 		character representation of the value
     * @param offset
     * 		offset of the first character to analyze
     * @param len
     * 		length of the array slice to analyze
     */     public BigReal(char[] in, int offset, int len) {         d = new java.math.BigDecimal(in, offset, len);}     /**
     * Build an instance from a characters representation.
     *
     * @param in
     * 		character representation of the value
     * @param offset
     * 		offset of the first character to analyze
     * @param len
     * 		length of the array slice to analyze
     * @param mc
     * 		context to use
     */     public BigReal(char[] in, int offset, int len, java.math.MathContext mc) {         d = new java.math.BigDecimal(in, offset, len, mc);}     /**
     * Build an instance from a characters representation.
     *
     * @param in
     * 		character representation of the value
     * @param mc
     * 		context to use
     */     public BigReal(char[] in, java.math.MathContext mc) {         d = new java.math.BigDecimal(in, mc);}     /**
     * Build an instance from a double.
     *
     * @param val
     * 		value of the instance
     */     public BigReal(double val) {         d = new java.math.BigDecimal(val);}

    /**
     * Build an instance from a double.
     *
     * @param val
     * 		value of the instance
     * @param mc
     * 		context to use
     */     public BigReal(double val, java.math.MathContext mc) {         d = new java.math.BigDecimal(val, mc);}
    /**
     * Build an instance from an int.
     *
     * @param val
     * 		value of the instance
     */     public BigReal(int val) {         d = new java.math.BigDecimal(val);}

    /**
     * Build an instance from an int.
     *
     * @param val
     * 		value of the instance
     * @param mc
     * 		context to use
     */     public BigReal(int val, java.math.MathContext mc) {         d = new java.math.BigDecimal(val, mc);}
    /**
     * Build an instance from a long.
     *
     * @param val
     * 		value of the instance
     */     public BigReal(long val) {         d = new java.math.BigDecimal(val);}

    /**
     * Build an instance from a long.
     *
     * @param val
     * 		value of the instance
     * @param mc
     * 		context to use
     */     public BigReal(long val, java.math.MathContext mc) {         d = new java.math.BigDecimal(val, mc);}
    /**
     * Build an instance from a String representation.
     *
     * @param val
     * 		character representation of the value
     */     public BigReal(java.lang.String val) {         d = new java.math.BigDecimal(val);}

    /**
     * Build an instance from a String representation.
     *
     * @param val
     * 		character representation of the value
     * @param mc
     * 		context to use
     */     public BigReal(java.lang.String val, java.math.MathContext mc) {         d = new java.math.BigDecimal(val, mc);}
    /**
     * *
     * Gets the rounding mode for division operations
     * The default is {@code RoundingMode.HALF_UP}
     *
     * @return the rounding mode.
     * @since 2.1
     */     public java.math.RoundingMode getRoundingMode() {         return roundingMode;
    }

    /**
     * *
     * Sets the rounding mode for decimal divisions.
     *
     * @param roundingMode
     * 		rounding mode for decimal divisions
     * @since 2.1
     */     public void setRoundingMode(java.math.RoundingMode roundingMode) {         this.roundingMode = roundingMode;}

    /**
     * *
     * Sets the scale for division operations.
     * The default is 64
     *
     * @return the scale
     * @since 2.1
     */     public int getScale() {         return scale;
    }

    /**
     * *
     * Sets the scale for division operations.
     *
     * @param scale
     * 		scale for division operations
     * @since 2.1
     */     public void setScale(int scale) {         this.scale = scale;}

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.util.BigReal add(org.apache.commons.math.util.BigReal a) {         return new org.apache.commons.math.util.BigReal(d.add(a.d));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.util.BigReal subtract(org.apache.commons.math.util.BigReal a) {         return new org.apache.commons.math.util.BigReal(d.subtract(a.d));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.util.BigReal divide(org.apache.commons.math.util.BigReal a) throws java.lang.ArithmeticException {         return new org.apache.commons.math.util.BigReal(d.divide(a.d, scale, roundingMode));
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.util.BigReal multiply(org.apache.commons.math.util.BigReal a) {         return new org.apache.commons.math.util.BigReal(d.multiply(a.d));
    }

    /**
     * {@inheritDoc}
     */     public int compareTo(org.apache.commons.math.util.BigReal a) {         return d.compareTo(a.d);
    }

    /**
     * Get the double value corresponding to the instance.
     *
     * @return double value corresponding to the instance
     */     public double doubleValue() {         return d.doubleValue();
    }

    /**
     * Get the BigDecimal value corresponding to the instance.
     *
     * @return BigDecimal value corresponding to the instance
     */     public java.math.BigDecimal bigDecimalValue() {         return d;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public boolean equals(java.lang.Object other) {
        if ((this) == other) {
            return true;
        }

        if (other instanceof org.apache.commons.math.util.BigReal) {
            return d.equals(((org.apache.commons.math.util.BigReal) (other)).d);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public int hashCode() {
        return d.hashCode();
    }

    /**
     * {@inheritDoc}
     */     public org.apache.commons.math.Field<org.apache.commons.math.util.BigReal> getField() {         return org.apache.commons.math.util.BigRealField.getInstance();
    }

}