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
package org.apache.commons.math.complex;












/**
 * Formats a Complex number in cartesian format "Re(c) + Im(c)i".  'i' can
 * be replaced with 'j' (or anything else), and the number format for both real
 * and imaginary parts can be configured.
 *
 * @version $Revision$ $Date$
 */
public class ComplexFormat extends org.apache.commons.math.util.CompositeFormat {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -3343698360149467646L;
    /**
     * The default imaginary character.
     */     private static final java.lang.String DEFAULT_IMAGINARY_CHARACTER = "i";
    /**
     * The notation used to signify the imaginary part of the complex number.
     */     private java.lang.String imaginaryCharacter;
    /**
     * The format used for the imaginary part.
     */     private java.text.NumberFormat imaginaryFormat;
    /**
     * The format used for the real part.
     */     private java.text.NumberFormat realFormat;
    /**
     * Create an instance with the default imaginary character, 'i', and the
     * default number format for both real and imaginary parts.
     */
    public ComplexFormat() {
        this(org.apache.commons.math.complex.ComplexFormat.DEFAULT_IMAGINARY_CHARACTER, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    /**
     * Create an instance with a custom number format for both real and
     * imaginary parts.
     *
     * @param format
     * 		the custom format for both real and imaginary parts.
     */     public ComplexFormat(java.text.NumberFormat format) {         this(org.apache.commons.math.complex.ComplexFormat.DEFAULT_IMAGINARY_CHARACTER, format);
    }

    /**
     * Create an instance with a custom number format for the real part and a
     * custom number format for the imaginary part.
     *
     * @param realFormat
     * 		the custom format for the real part.
     * @param imaginaryFormat
     * 		the custom format for the imaginary part.
     */     public ComplexFormat(java.text.NumberFormat realFormat, java.text.NumberFormat imaginaryFormat) {         this(org.apache.commons.math.complex.ComplexFormat.DEFAULT_IMAGINARY_CHARACTER, realFormat, imaginaryFormat);}

    /**
     * Create an instance with a custom imaginary character, and the default
     * number format for both real and imaginary parts.
     *
     * @param imaginaryCharacter
     * 		The custom imaginary character.
     */     public ComplexFormat(java.lang.String imaginaryCharacter) {         this(imaginaryCharacter, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    /**
     * Create an instance with a custom imaginary character, and a custom number
     * format for both real and imaginary parts.
     *
     * @param imaginaryCharacter
     * 		The custom imaginary character.
     * @param format
     * 		the custom format for both real and imaginary parts.
     */     public ComplexFormat(java.lang.String imaginaryCharacter, java.text.NumberFormat format) {         this(imaginaryCharacter, format, ((java.text.NumberFormat) (format.clone())));}

    /**
     * Create an instance with a custom imaginary character, a custom number
     * format for the real part, and a custom number format for the imaginary
     * part.
     *
     * @param imaginaryCharacter
     * 		The custom imaginary character.
     * @param realFormat
     * 		the custom format for the real part.
     * @param imaginaryFormat
     * 		the custom format for the imaginary part.
     */     public ComplexFormat(java.lang.String imaginaryCharacter, java.text.NumberFormat realFormat, java.text.NumberFormat imaginaryFormat) {         super();         setImaginaryCharacter(imaginaryCharacter);
        setImaginaryFormat(imaginaryFormat);
        setRealFormat(realFormat);
    }

    /**
     * Get the set of locales for which complex formats are available.
     * <p>This is the same set as the {@link NumberFormat} set.</p>
     *
     * @return available complex format locales.
     */     public static java.util.Locale[] getAvailableLocales() {
        return java.text.NumberFormat.getAvailableLocales();
    }

    /**
     * This static method calls {@link #format(Object)} on a default instance of
     * ComplexFormat.
     *
     * @param c
     * 		Complex object to format
     * @return A formatted number in the form "Re(c) + Im(c)i"
     */     public static java.lang.String formatComplex(org.apache.commons.math.complex.Complex c) {
        return org.apache.commons.math.complex.ComplexFormat.getInstance().format(c);
    }

    /**
     * Formats a {@link Complex} object to produce a string.
     *
     * @param complex
     * 		the object to format.
     * @param toAppendTo
     * 		where the text is to be appended
     * @param pos
     * 		On input: an alignment field, if desired. On output: the
     * 		offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */     public java.lang.StringBuffer format(org.apache.commons.math.complex.Complex complex, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);

        // format real
        double re = complex.getReal();
        formatDouble(re, getRealFormat(), toAppendTo, pos);

        // format sign and imaginary
        double im = complex.getImaginary();
        if (im < 0.0) {
            toAppendTo.append(" - ");
            formatDouble((-im), getImaginaryFormat(), toAppendTo, pos);
            toAppendTo.append(getImaginaryCharacter());
        }else             if ((im > 0.0) || (java.lang.Double.isNaN(im))) {
                toAppendTo.append(" + ");
                formatDouble(im, getImaginaryFormat(), toAppendTo, pos);
                toAppendTo.append(getImaginaryCharacter());
            }

        return toAppendTo;
    }

    /**
     * Formats a object to produce a string.  <code>obj</code> must be either a
     * {@link Complex} object or a {@link Number} object.  Any other type of
     * object will result in an {@link IllegalArgumentException} being thrown.
     *
     * @param obj
     * 		the object to format.
     * @param toAppendTo
     * 		where the text is to be appended
     * @param pos
     * 		On input: an alignment field, if desired. On output: the
     * 		offsets of the alignment field
     * @return the value passed in as toAppendTo.
     * @see java.text.Format#format(java.lang.Object, java.lang.StringBuffer, java.text.FieldPosition)
     * @throws IllegalArgumentException
     * 		is <code>obj</code> is not a valid type.
     */     @java.lang.Override     public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos) {
        java.lang.StringBuffer ret = null;

        if (obj instanceof org.apache.commons.math.complex.Complex) {
            ret = format(((org.apache.commons.math.complex.Complex) (obj)), toAppendTo, pos);
        }else             if (obj instanceof java.lang.Number) {
                ret = format(new org.apache.commons.math.complex.Complex(((java.lang.Number) (obj)).doubleValue(), 0.0), 
                toAppendTo, pos);
            }else {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                org.apache.commons.math.util.LocalizedFormats.CANNOT_FORMAT_INSTANCE_AS_COMPLEX, 
                obj.getClass().getName());
            }

        return ret;
    }

    /**
     * Access the imaginaryCharacter.
     *
     * @return the imaginaryCharacter.
     */     public java.lang.String getImaginaryCharacter() {
        return imaginaryCharacter;
    }

    /**
     * Access the imaginaryFormat.
     *
     * @return the imaginaryFormat.
     */     public java.text.NumberFormat getImaginaryFormat() {
        return imaginaryFormat;
    }

    /**
     * Returns the default complex format for the current locale.
     *
     * @return the default complex format.
     */     public static org.apache.commons.math.complex.ComplexFormat getInstance() {
        return org.apache.commons.math.complex.ComplexFormat.getInstance(java.util.Locale.getDefault());
    }

    /**
     * Returns the default complex format for the given locale.
     *
     * @param locale
     * 		the specific locale used by the format.
     * @return the complex format specific to the given locale.
     */     public static org.apache.commons.math.complex.ComplexFormat getInstance(java.util.Locale locale) {         java.text.NumberFormat f = org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat(locale);
        return new org.apache.commons.math.complex.ComplexFormat(f);
    }

    /**
     * Access the realFormat.
     *
     * @return the realFormat.
     */     public java.text.NumberFormat getRealFormat() {
        return realFormat;
    }

    /**
     * Parses a string to produce a {@link Complex} object.
     *
     * @param source
     * 		the string to parse
     * @return the parsed {@link Complex} object.
     * @exception ParseException if the beginning of the specified string
     * cannot be parsed.
     */     public org.apache.commons.math.complex.Complex parse(java.lang.String source) throws java.text.ParseException {
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.complex.Complex result = parse(source, parsePosition);
        if ((parsePosition.getIndex()) == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(
            parsePosition.getErrorIndex(), 
            org.apache.commons.math.util.LocalizedFormats.UNPARSEABLE_COMPLEX_NUMBER, source);
        }
        return result;
    }

    /**
     * Parses a string to produce a {@link Complex} object.
     *
     * @param source
     * 		the string to parse
     * @param pos
     * 		input/ouput parsing parameter.
     * @return the parsed {@link Complex} object.
     */     public org.apache.commons.math.complex.Complex parse(java.lang.String source, java.text.ParsePosition pos) {         int initialIndex = pos.getIndex();

        // parse whitespace
        parseAndIgnoreWhitespace(source, pos);

        // parse real
        java.lang.Number re = parseNumber(source, getRealFormat(), pos);
        if (re == null) {
            // invalid real number
            // set index back to initial, error index should already be set
            pos.setIndex(initialIndex);
            return null;
        }

        // parse sign
        int startIndex = pos.getIndex();
        char c = parseNextCharacter(source, pos);
        int sign = 0;
        switch (c) {
            case 0 :
                // no sign
                // return real only complex number
                return new org.apache.commons.math.complex.Complex(re.doubleValue(), 0.0);
            case '-' :
                sign = -1;
                break;
            case '+' :
                sign = 1;
                break;
            default :
                // invalid sign
                // set index back to initial, error index should be the last
                // character examined.
                pos.setIndex(initialIndex);
                pos.setErrorIndex(startIndex);
                return null;}


        // parse whitespace
        parseAndIgnoreWhitespace(source, pos);

        // parse imaginary
        java.lang.Number im = parseNumber(source, getRealFormat(), pos);
        if (im == null) {
            // invalid imaginary number
            // set index back to initial, error index should already be set
            pos.setIndex(initialIndex);
            return null;
        }

        // parse imaginary character
        if (!(parseFixedstring(source, getImaginaryCharacter(), pos))) {
            return null;
        }

        return new org.apache.commons.math.complex.Complex(re.doubleValue(), ((im.doubleValue()) * sign));

    }

    /**
     * Parses a string to produce a object.
     *
     * @param source
     * 		the string to parse
     * @param pos
     * 		input/ouput parsing parameter.
     * @return the parsed object.
     * @see java.text.Format#parseObject(java.lang.String, java.text.ParsePosition)
     */     @java.lang.Override     public java.lang.Object parseObject(java.lang.String source, java.text.ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Modify the imaginaryCharacter.
     *
     * @param imaginaryCharacter
     * 		The new imaginaryCharacter value.
     * @throws IllegalArgumentException
     * 		if <code>imaginaryCharacter</code> is
     * 		<code>null</code> or an empty string.
     */     public void setImaginaryCharacter(java.lang.String imaginaryCharacter) {         if ((imaginaryCharacter == null) || ((imaginaryCharacter.length()) == 0)) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.EMPTY_STRING_FOR_IMAGINARY_CHARACTER);
        }
        this.imaginaryCharacter = imaginaryCharacter;
    }

    /**
     * Modify the imaginaryFormat.
     *
     * @param imaginaryFormat
     * 		The new imaginaryFormat value.
     * @throws IllegalArgumentException
     * 		if <code>imaginaryFormat</code> is
     * 		<code>null</code>.
     */     public void setImaginaryFormat(java.text.NumberFormat imaginaryFormat) {         if (imaginaryFormat == null) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NULL_IMAGINARY_FORMAT);
        }
        this.imaginaryFormat = imaginaryFormat;
    }

    /**
     * Modify the realFormat.
     *
     * @param realFormat
     * 		The new realFormat value.
     * @throws IllegalArgumentException
     * 		if <code>realFormat</code> is
     * 		<code>null</code>.
     */     public void setRealFormat(java.text.NumberFormat realFormat) {         if (realFormat == null) {             throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
            org.apache.commons.math.util.LocalizedFormats.NULL_REAL_FORMAT);
        }
        this.realFormat = realFormat;
    }

}