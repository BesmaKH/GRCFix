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
package org.apache.commons.math;











/**
 * Base class for commons-math checked exceptions.
 * <p>
 * Supports nesting, emulating JDK 1.4 behavior if necessary.</p>
 * <p>
 * Adapted from <a href="http://commons.apache.org/collections/api-release/org/apache/commons/collections/FunctorException.html"/>.</p>
 *
 * @version $Revision$ $Date$
 */
public class MathException extends java.lang.Exception {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 7428019509644517071L;
    /**
     * Pattern used to build the message.
     */
    private final org.apache.commons.math.util.Localizable pattern;

    /**
     * Arguments used to build the message.
     */
    private final java.lang.Object[] arguments;

    /**
     * Constructs a new <code>MathException</code> with no
     * detail message.
     */
    public MathException() {
        this.pattern = org.apache.commons.math.util.LocalizedFormats.SIMPLE_MESSAGE;
        this.arguments = new java.lang.Object[]{ "" };
    }

    /**
     * Constructs a new <code>MathException</code> with specified
     * formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @deprecated as of 2.2 replaced by {@link #MathException(Localizable, Object...)}
     */     @java.lang.Deprecated     public MathException(java.lang.String pattern, java.lang.Object... arguments) {         this(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>MathException</code> with specified
     * formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @since 2.2
     */     public MathException(org.apache.commons.math.util.Localizable pattern, java.lang.Object... arguments) {         this.pattern = pattern;         this.arguments = (arguments == null) ? new java.lang.Object[0] : arguments.clone();
    }

    /**
     * Constructs a new <code>MathException</code> with specified
     * nested <code>Throwable</code> root cause.
     *
     * @param rootCause
     * 		the exception or error that caused this exception
     * 		to be thrown.
     */     public MathException(java.lang.Throwable rootCause) {
        super(rootCause);
        this.pattern = org.apache.commons.math.util.LocalizedFormats.SIMPLE_MESSAGE;
        this.arguments = new java.lang.Object[]{ rootCause == null ? "" : rootCause.getMessage() };
    }

    /**
     * Constructs a new <code>MathException</code> with specified
     * formatted detail message and nested <code>Throwable</code> root cause.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param rootCause
     * 		the exception or error that caused this exception
     * 		to be thrown.
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @since 1.2
     * @deprecated as of 2.2 replaced by {@link #MathException(Throwable, Localizable, Object...)}
     */     @java.lang.Deprecated     public MathException(java.lang.Throwable rootCause, java.lang.String pattern, java.lang.Object... arguments) {         this(rootCause, new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);}

    /**
     * Constructs a new <code>MathException</code> with specified
     * formatted detail message and nested <code>Throwable</code> root cause.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param rootCause
     * 		the exception or error that caused this exception
     * 		to be thrown.
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @since 2.2
     */     public MathException(java.lang.Throwable rootCause, org.apache.commons.math.util.Localizable pattern, java.lang.Object... arguments) {         super(rootCause);         this.pattern = pattern;         this.arguments = (arguments == null) ? new java.lang.Object[0] : arguments.clone();
    }

    /**
     * Gets the pattern used to build the message of this throwable.
     *
     * @return the pattern used to build the message of this throwable
     * @since 1.2
     * @deprecated as of 2.2 replaced by {@link #getLocalizablePattern()}
     */     @java.lang.Deprecated
    public java.lang.String getPattern() {
        return pattern.getSourceString();
    }

    /**
     * Gets the localizable pattern used to build the message of this throwable.
     *
     * @return the localizable pattern used to build the message of this throwable
     * @since 2.2
     */     public org.apache.commons.math.util.Localizable getLocalizablePattern() {
        return pattern;
    }

    /**
     * Gets the arguments used to build the message of this throwable.
     *
     * @return the arguments used to build the message of this throwable
     * @since 1.2
     */     public java.lang.Object[] getArguments() {
        return arguments.clone();
    }

    /**
     * Gets the message in a specified locale.
     *
     * @param locale
     * 		Locale in which the message should be translated
     * @return localized message
     * @since 1.2
     */     public java.lang.String getMessage(final java.util.Locale locale) {
        if ((pattern) != null) {
            return new java.text.MessageFormat(pattern.getLocalizedString(locale), locale).format(arguments);
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public java.lang.String getMessage() {
        return getMessage(java.util.Locale.US);
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public java.lang.String getLocalizedMessage() {
        return getMessage(java.util.Locale.getDefault());
    }

    /**
     * Prints the stack trace of this exception to the standard error stream.
     */
    @java.lang.Override
    public void printStackTrace() {
        printStackTrace(java.lang.System.err);
    }

    /**
     * Prints the stack trace of this exception to the specified stream.
     *
     * @param out
     * 		the <code>PrintStream</code> to use for output
     */     @java.lang.Override
    public void printStackTrace(java.io.PrintStream out) {
        synchronized(out) {
            java.io.PrintWriter pw = new java.io.PrintWriter(out, false);
            printStackTrace(pw);
            // Flush the PrintWriter before it's GC'ed.
            pw.flush();
        }
    }

}