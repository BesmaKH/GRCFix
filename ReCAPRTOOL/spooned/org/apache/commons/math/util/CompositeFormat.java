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
 * Base class for formatters of composite objects (complex numbers, vectors ...).
 *
 * @version $Revision$ $Date$
 */
public abstract class CompositeFormat extends java.text.Format {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 5358685519349262494L;
    /**
     * Create a default number format.  The default number format is based on
     * {@link NumberFormat#getInstance()} with the only customizing that the
     * maximum number of fraction digits is set to 2.
     *
     * @return the default number format.
     */     protected static java.text.NumberFormat getDefaultNumberFormat() {
        return org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat(java.util.Locale.getDefault());
    }

    /**
     * Create a default number format.  The default number format is based on
     * {@link NumberFormat#getInstance(java.util.Locale)} with the only
     * customizing that the maximum number of fraction digits is set to 2.
     *
     * @param locale
     * 		the specific locale used by the format.
     * @return the default number format specific to the given locale.
     */     protected static java.text.NumberFormat getDefaultNumberFormat(final java.util.Locale locale) {         final java.text.NumberFormat nf = java.text.NumberFormat.getInstance(locale);
        nf.setMaximumFractionDigits(2);
        return nf;
    }

    /**
     * Parses <code>source</code> until a non-whitespace character is found.
     *
     * @param source
     * 		the string to parse
     * @param pos
     * 		input/ouput parsing parameter.  On output, <code>pos</code>
     * 		holds the index of the next non-whitespace character.
     */     protected void parseAndIgnoreWhitespace(final java.lang.String source, final java.text.ParsePosition pos) {
        parseNextCharacter(source, pos);
        pos.setIndex(((pos.getIndex()) - 1));
    }

    /**
     * Parses <code>source</code> until a non-whitespace character is found.
     *
     * @param source
     * 		the string to parse
     * @param pos
     * 		input/ouput parsing parameter.
     * @return the first non-whitespace character.
     */     protected char parseNextCharacter(final java.lang.String source, final java.text.ParsePosition pos) {
        int index = pos.getIndex();
        final int n = source.length();
        char ret = 0;

        if (index < n) {
            char c;
            do {
                c = source.charAt((index++));
            } while ((java.lang.Character.isWhitespace(c)) && (index < n) );
            pos.setIndex(index);

            if (index < n) {
                ret = c;
            }
        }

        return ret;
    }

    /**
     * Parses <code>source</code> for special double values.  These values
     * include Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY.
     *
     * @param source
     * 		the string to parse
     * @param value
     * 		the special value to parse.
     * @param pos
     * 		input/ouput parsing parameter.
     * @return the special number.
     */     private java.lang.Number parseNumber(final java.lang.String source, final double value, final java.text.ParsePosition pos) {         java.lang.Number ret = null;

        java.lang.StringBuffer sb = new java.lang.StringBuffer();
        sb.append('(');
        sb.append(value);
        sb.append(')');

        final int n = sb.length();
        final int startIndex = pos.getIndex();
        final int endIndex = startIndex + n;
        if (endIndex < (source.length())) {
            if ((source.substring(startIndex, endIndex).compareTo(sb.toString())) == 0) {
                ret = java.lang.Double.valueOf(value);
                pos.setIndex(endIndex);
            }
        }

        return ret;
    }

    /**
     * Parses <code>source</code> for a number.  This method can parse normal,
     * numeric values as well as special values.  These special values include
     * Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY.
     *
     * @param source
     * 		the string to parse
     * @param format
     * 		the number format used to parse normal, numeric values.
     * @param pos
     * 		input/ouput parsing parameter.
     * @return the parsed number.
     */     protected java.lang.Number parseNumber(final java.lang.String source, final java.text.NumberFormat format, final java.text.ParsePosition pos) {         final int startIndex = pos.getIndex();
        java.lang.Number number = format.parse(source, pos);
        final int endIndex = pos.getIndex();

        // check for error parsing number
        if (startIndex == endIndex) {
            // try parsing special numbers
            final double[] special = new double[]{ 
            java.lang.Double.NaN, java.lang.Double.POSITIVE_INFINITY, java.lang.Double.NEGATIVE_INFINITY };

            for (int i = 0; i < (special.length); ++i) {
                number = parseNumber(source, special[i], pos);
                if (number != null) {
                    break;
                }
            }
        }

        return number;
    }

    /**
     * Parse <code>source</code> for an expected fixed string.
     *
     * @param source
     * 		the string to parse
     * @param expected
     * 		expected string
     * @param pos
     * 		input/ouput parsing parameter.
     * @return true if the expected string was there
     */     protected boolean parseFixedstring(final java.lang.String source, final java.lang.String expected, final java.text.ParsePosition pos) {         final int startIndex = pos.getIndex();
        final int endIndex = startIndex + (expected.length());
        if (((startIndex >= (source.length())) || 
        (endIndex > (source.length()))) || 
        ((source.substring(startIndex, endIndex).compareTo(expected)) != 0)) {
            // set index back to start, error index should be the start index
            pos.setIndex(startIndex);
            pos.setErrorIndex(startIndex);
            return false;
        }

        // the string was here
        pos.setIndex(endIndex);
        return true;

    }

    /**
     * Formats a double value to produce a string.  In general, the value is
     * formatted using the formatting rules of <code>format</code>.  There are
     * three exceptions to this:
     * <ol>
     * <li>NaN is formatted as '(NaN)'</li>
     * <li>Positive infinity is formatted as '(Infinity)'</li>
     * <li>Negative infinity is formatted as '(-Infinity)'</li>
     * </ol>
     *
     * @param value
     * 		the double to format.
     * @param format
     * 		the format used.
     * @param toAppendTo
     * 		where the text is to be appended
     * @param pos
     * 		On input: an alignment field, if desired. On output: the
     * 		offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */     protected java.lang.StringBuffer formatDouble(final double value, final java.text.NumberFormat format, final java.lang.StringBuffer toAppendTo, final java.text.FieldPosition pos) {         if ((java.lang.Double.isNaN(value)) || (java.lang.Double.isInfinite(value))) {
            toAppendTo.append('(');
            toAppendTo.append(value);
            toAppendTo.append(')');
        }else {
            format.format(value, toAppendTo, pos);
        }
        return toAppendTo;
    }

}