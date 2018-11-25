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
 * Formats a vector in components list format "{v0; v1; ...; vk-1}".
 * <p>The prefix and suffix "{" and "}" and the separator "; " can be replaced by
 * any user-defined strings. The number format for components can be configured.</p>
 * <p>White space is ignored at parse time, even if it is in the prefix, suffix
 * or separator specifications. So even if the default separator does include a space
 * character that is used at format time, both input string "{1;1;1}" and
 * " { 1 ; 1 ; 1 } " will be parsed without error and the same vector will be
 * returned. In the second case, however, the parse position after parsing will be
 * just after the closing curly brace, i.e. just before the trailing space.</p>
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public class RealVectorFormat extends org.apache.commons.math.util.CompositeFormat {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -708767813036157690L;
    /**
     * The default prefix: "{".
     */     private static final java.lang.String DEFAULT_PREFIX = "{";
    /**
     * The default suffix: "}".
     */     private static final java.lang.String DEFAULT_SUFFIX = "}";
    /**
     * The default separator: ", ".
     */     private static final java.lang.String DEFAULT_SEPARATOR = "; ";
    /**
     * Prefix.
     */     private final java.lang.String prefix;
    /**
     * Suffix.
     */     private final java.lang.String suffix;
    /**
     * Separator.
     */     private final java.lang.String separator;
    /**
     * Trimmed prefix.
     */     private final java.lang.String trimmedPrefix;
    /**
     * Trimmed suffix.
     */     private final java.lang.String trimmedSuffix;
    /**
     * Trimmed separator.
     */     private final java.lang.String trimmedSeparator;
    /**
     * The format used for components.
     */     private java.text.NumberFormat format;
    /**
     * Create an instance with default settings.
     * <p>The instance uses the default prefix, suffix and separator:
     * "{", "}", and "; " and the default number format for components.</p>
     */
    public RealVectorFormat() {
        this(org.apache.commons.math.linear.RealVectorFormat.DEFAULT_PREFIX, org.apache.commons.math.linear.RealVectorFormat.DEFAULT_SUFFIX, org.apache.commons.math.linear.RealVectorFormat.DEFAULT_SEPARATOR, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    /**
     * Create an instance with a custom number format for components.
     *
     * @param format
     * 		the custom format for components.
     */     public RealVectorFormat(final java.text.NumberFormat format) {         this(org.apache.commons.math.linear.RealVectorFormat.DEFAULT_PREFIX, org.apache.commons.math.linear.RealVectorFormat.DEFAULT_SUFFIX, org.apache.commons.math.linear.RealVectorFormat.DEFAULT_SEPARATOR, format);
    }

    /**
     * Create an instance with custom prefix, suffix and separator.
     *
     * @param prefix
     * 		prefix to use instead of the default "{"
     * @param suffix
     * 		suffix to use instead of the default "}"
     * @param separator
     * 		separator to use instead of the default "; "
     */     public RealVectorFormat(final java.lang.String prefix, final java.lang.String suffix, final java.lang.String separator) {         this(prefix, suffix, separator, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());}

    /**
     * Create an instance with custom prefix, suffix, separator and format
     * for components.
     *
     * @param prefix
     * 		prefix to use instead of the default "{"
     * @param suffix
     * 		suffix to use instead of the default "}"
     * @param separator
     * 		separator to use instead of the default "; "
     * @param format
     * 		the custom format for components.
     */     public RealVectorFormat(final java.lang.String prefix, final java.lang.String suffix, final java.lang.String separator, final java.text.NumberFormat format) {         this.prefix = prefix;         this.suffix = suffix;         this.separator = separator;
        trimmedPrefix = prefix.trim();
        trimmedSuffix = suffix.trim();
        trimmedSeparator = separator.trim();
        this.format = format;
    }

    /**
     * Get the set of locales for which real vectors formats are available.
     * <p>This is the same set as the {@link NumberFormat} set.</p>
     *
     * @return available real vector format locales.
     */     public static java.util.Locale[] getAvailableLocales() {
        return java.text.NumberFormat.getAvailableLocales();
    }

    /**
     * Get the format prefix.
     *
     * @return format prefix.
     */     public java.lang.String getPrefix() {
        return prefix;
    }

    /**
     * Get the format suffix.
     *
     * @return format suffix.
     */     public java.lang.String getSuffix() {
        return suffix;
    }

    /**
     * Get the format separator between components.
     *
     * @return format separator.
     */     public java.lang.String getSeparator() {
        return separator;
    }

    /**
     * Get the components format.
     *
     * @return components format.
     */     public java.text.NumberFormat getFormat() {
        return format;
    }

    /**
     * Returns the default real vector format for the current locale.
     *
     * @return the default real vector format.
     */     public static org.apache.commons.math.linear.RealVectorFormat getInstance() {
        return org.apache.commons.math.linear.RealVectorFormat.getInstance(java.util.Locale.getDefault());
    }

    /**
     * Returns the default real vector format for the given locale.
     *
     * @param locale
     * 		the specific locale used by the format.
     * @return the real vector format specific to the given locale.
     */     public static org.apache.commons.math.linear.RealVectorFormat getInstance(final java.util.Locale locale) {         return new org.apache.commons.math.linear.RealVectorFormat(org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat(locale));
    }

    /**
     * This static method calls {@link #format(Object)} on a default instance of
     * RealVectorFormat.
     *
     * @param v
     * 		RealVector object to format
     * @return A formatted vector
     */     public static java.lang.String formatRealVector(org.apache.commons.math.linear.RealVector v) {
        return org.apache.commons.math.linear.RealVectorFormat.getInstance().format(v);
    }

    /**
     * Formats a {@link RealVector} object to produce a string.
     *
     * @param vector
     * 		the object to format.
     * @param toAppendTo
     * 		where the text is to be appended
     * @param pos
     * 		On input: an alignment field, if desired. On output: the
     * 		offsets of the alignment field
     * @return the value passed in as toAppendTo.
     */     public java.lang.StringBuffer format(org.apache.commons.math.linear.RealVector vector, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos) {         pos.setBeginIndex(0);
        pos.setEndIndex(0);

        // format prefix
        toAppendTo.append(prefix);

        // format components
        for (int i = 0; i < (vector.getDimension()); ++i) {
            if (i > 0) {
                toAppendTo.append(separator);
            }
            formatDouble(vector.getEntry(i), format, toAppendTo, pos);
        }

        // format suffix
        toAppendTo.append(suffix);

        return toAppendTo;

    }

    /**
     * Formats a object to produce a string.
     * <p><code>obj</code> must be a  {@link RealVector} object. Any other type of
     * object will result in an {@link IllegalArgumentException} being thrown.</p>
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
     */     @java.lang.Override     public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos) {         if (obj instanceof org.apache.commons.math.linear.RealVector) {
            return format(((org.apache.commons.math.linear.RealVector) (obj)), toAppendTo, pos);
        }

        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
        org.apache.commons.math.util.LocalizedFormats.CANNOT_FORMAT_INSTANCE_AS_REAL_VECTOR, 
        obj.getClass().getName());

    }

    /**
     * Parses a string to produce a {@link RealVector} object.
     *
     * @param source
     * 		the string to parse
     * @return the parsed {@link RealVector} object.
     * @exception ParseException if the beginning of the specified string
     * cannot be parsed.
     */     public org.apache.commons.math.linear.ArrayRealVector parse(java.lang.String source) throws java.text.ParseException {         java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.linear.ArrayRealVector result = parse(source, parsePosition);
        if ((parsePosition.getIndex()) == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(
            parsePosition.getErrorIndex(), 
            org.apache.commons.math.util.LocalizedFormats.UNPARSEABLE_REAL_VECTOR, source);
        }
        return result;
    }

    /**
     * Parses a string to produce a {@link RealVector} object.
     *
     * @param source
     * 		the string to parse
     * @param pos
     * 		input/ouput parsing parameter.
     * @return the parsed {@link RealVector} object.
     */     public org.apache.commons.math.linear.ArrayRealVector parse(java.lang.String source, java.text.ParsePosition pos) {         int initialIndex = pos.getIndex();
        // parse prefix
        parseAndIgnoreWhitespace(source, pos);
        if (!(parseFixedstring(source, trimmedPrefix, pos))) {
            return null;
        }

        // parse components
        java.util.List<java.lang.Number> components = new java.util.ArrayList<java.lang.Number>();
        for (boolean loop = true; loop;) {

            if (!(components.isEmpty())) {
                parseAndIgnoreWhitespace(source, pos);
                if (!(parseFixedstring(source, trimmedSeparator, pos))) {
                    loop = false;
                }
            }

            if (loop) {
                parseAndIgnoreWhitespace(source, pos);
                java.lang.Number component = parseNumber(source, format, pos);
                if (component != null) {
                    components.add(component);
                }else {
                    // invalid component
                    // set index back to initial, error index should already be set
                    pos.setIndex(initialIndex);
                    return null;
                }
            }

        }

        // parse suffix
        parseAndIgnoreWhitespace(source, pos);
        if (!(parseFixedstring(source, trimmedSuffix, pos))) {
            return null;
        }

        // build vector
        double[] data = new double[components.size()];
        for (int i = 0; i < (data.length); ++i) {
            data[i] = components.get(i).doubleValue();
        }
        return new org.apache.commons.math.linear.ArrayRealVector(data, false);

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
     */     @java.lang.Override     public java.lang.Object parseObject(java.lang.String source, java.text.ParsePosition pos) {         return parse(source, pos);
    }

}