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
 * Base class for commons-math unchecked exceptions.
 *
 * @version $Revision$ $Date$
 * @since 2.0
 */
public class MathRuntimeException extends java.lang.RuntimeException {

    /**
     * Serializable version identifier.
     */     private static final long serialVersionUID = 9058794795027570002L;
    /**
     * Pattern used to build the message.
     */
    private final org.apache.commons.math.util.Localizable pattern;

    /**
     * Arguments used to build the message.
     */
    private final java.lang.Object[] arguments;

    /**
     * Constructs a new <code>MathRuntimeException</code> with specified
     * formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @deprecated as of 2.2 replaced by {@link #MathRuntimeException(Localizable, Object...)}
     */     @java.lang.Deprecated     public MathRuntimeException(final java.lang.String pattern, final java.lang.Object... arguments) {         this(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>MathRuntimeException</code> with specified
     * formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @since 2.2
     */     public MathRuntimeException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         this.pattern = pattern;         this.arguments = (arguments == null) ? new java.lang.Object[0] : arguments.clone();
    }

    /**
     * Constructs a new <code>MathRuntimeException</code> with specified
     * nested <code>Throwable</code> root cause.
     *
     * @param rootCause
     * 		the exception or error that caused this exception
     * 		to be thrown.
     */     public MathRuntimeException(final java.lang.Throwable rootCause) {
        super(rootCause);
        this.pattern = org.apache.commons.math.util.LocalizedFormats.SIMPLE_MESSAGE;
        this.arguments = new java.lang.Object[]{ rootCause == null ? "" : rootCause.getMessage() };
    }

    /**
     * Constructs a new <code>MathRuntimeException</code> with specified
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
     * @deprecated as of 2.2 replaced by {@link #MathRuntimeException(Throwable, Localizable, Object...)}
     */     @java.lang.Deprecated     public MathRuntimeException(final java.lang.Throwable rootCause, final java.lang.String pattern, final java.lang.Object... arguments) {         this(rootCause, new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>MathRuntimeException</code> with specified
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
     */     public MathRuntimeException(final java.lang.Throwable rootCause, final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         super(rootCause);         this.pattern = pattern;
        this.arguments = (arguments == null) ? new java.lang.Object[0] : arguments.clone();
    }

    /**
     * Builds a message string by from a pattern and its arguments.
     *
     * @param locale
     * 		Locale in which the message should be translated
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return a message string
     * @since 2.2
     */     private static java.lang.String buildMessage(final java.util.Locale locale, final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.text.MessageFormat(pattern.getLocalizedString(locale), locale).format(arguments);}

    /**
     * Gets the pattern used to build the message of this throwable.
     *
     * @return the pattern used to build the message of this throwable
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
     */     public java.lang.Object[] getArguments() {
        return arguments.clone();
    }

    /**
     * Gets the message in a specified locale.
     *
     * @param locale
     * 		Locale in which the message should be translated
     * @return localized message
     */     public java.lang.String getMessage(final java.util.Locale locale) {
        if ((pattern) != null) {
            return org.apache.commons.math.MathRuntimeException.buildMessage(locale, pattern, arguments);
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
    public void printStackTrace(final java.io.PrintStream out) {
        synchronized(out) {
            java.io.PrintWriter pw = new java.io.PrintWriter(out, false);
            printStackTrace(pw);
            // Flush the PrintWriter before it's GC'ed.
            pw.flush();
        }
    }

    /**
     * Constructs a new <code>ArithmeticException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createArithmeticException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.lang.ArithmeticException createArithmeticException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createArithmeticException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>ArithmeticException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.lang.ArithmeticException createArithmeticException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.lang.ArithmeticException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 5305498554076846637L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>ArrayIndexOutOfBoundsException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createArrayIndexOutOfBoundsException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.lang.ArrayIndexOutOfBoundsException createArrayIndexOutOfBoundsException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createArrayIndexOutOfBoundsException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>ArrayIndexOutOfBoundsException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.lang.ArrayIndexOutOfBoundsException createArrayIndexOutOfBoundsException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.lang.ArrayIndexOutOfBoundsException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 6718518191249632175L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>EOFException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createEOFException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.io.EOFException createEOFException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createEOFException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>EOFException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.io.EOFException createEOFException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.io.EOFException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 6067985859347601503L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>IOException</code> with specified nested
     * <code>Throwable</code> root cause.
     * <p>This factory method allows chaining of other exceptions within an
     * <code>IOException</code> even for Java 5. The constructor for
     * <code>IOException</code> with a cause parameter was introduced only
     * with Java 6.</p>
     *
     * @param rootCause
     * 		the exception or error that caused this exception
     * 		to be thrown.
     * @return built exception
     */     public static java.io.IOException createIOException(final java.lang.Throwable rootCause) {         java.io.IOException ioe = new java.io.IOException(rootCause.getLocalizedMessage());
        ioe.initCause(rootCause);
        return ioe;
    }

    /**
     * Constructs a new <code>IllegalArgumentException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createIllegalArgumentException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.lang.IllegalArgumentException createIllegalArgumentException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>IllegalArgumentException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.lang.IllegalArgumentException createIllegalArgumentException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.lang.IllegalArgumentException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = -4284649691002411505L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>IllegalArgumentException</code> with specified nested
     * <code>Throwable</code> root cause.
     *
     * @param rootCause
     * 		the exception or error that caused this exception
     * 		to be thrown.
     * @return built exception
     */     public static java.lang.IllegalArgumentException createIllegalArgumentException(final java.lang.Throwable rootCause) {         java.lang.IllegalArgumentException iae = new java.lang.IllegalArgumentException(rootCause.getLocalizedMessage());
        iae.initCause(rootCause);
        return iae;
    }

    /**
     * Constructs a new <code>IllegalStateException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createIllegalStateException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.lang.IllegalStateException createIllegalStateException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createIllegalStateException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>IllegalStateException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.lang.IllegalStateException createIllegalStateException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.lang.IllegalStateException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 6880901520234515725L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>ConcurrentModificationException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createConcurrentModificationException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.util.ConcurrentModificationException createConcurrentModificationException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>ConcurrentModificationException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.util.ConcurrentModificationException createConcurrentModificationException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.util.ConcurrentModificationException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = -1878427236170442052L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>NoSuchElementException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createNoSuchElementException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.util.NoSuchElementException createNoSuchElementException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createNoSuchElementException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>NoSuchElementException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.util.NoSuchElementException createNoSuchElementException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.util.NoSuchElementException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 1632410088350355086L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>NullPointerException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createNullPointerException(Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.lang.NullPointerException createNullPointerException(final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createNullPointerException(new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>NullPointerException</code> with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.lang.NullPointerException createNullPointerException(final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.lang.NullPointerException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 451965530686593945L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Constructs a new <code>ParseException</code> with specified
     * formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param offset
     * 		offset at which error occurred
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @deprecated as of 2.2 replaced by {@link #createParseException(int, Localizable, Object...)}
     */     @java.lang.Deprecated     public static java.text.ParseException createParseException(final int offset, final java.lang.String pattern, final java.lang.Object... arguments) {
        return org.apache.commons.math.MathRuntimeException.createParseException(offset, new org.apache.commons.math.util.DummyLocalizable(pattern), arguments);
    }

    /**
     * Constructs a new <code>ParseException</code> with specified
     * formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     *
     * @param offset
     * 		offset at which error occurred
     * @param pattern
     * 		format specifier
     * @param arguments
     * 		format arguments
     * @return built exception
     * @since 2.2
     */     public static java.text.ParseException createParseException(final int offset, final org.apache.commons.math.util.Localizable pattern, final java.lang.Object... arguments) {         return new java.text.ParseException(null, offset) {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = 8153587599409010120L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, pattern, arguments);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), pattern, arguments);
            }

        };
    }

    /**
     * Create an {@link java.lang.RuntimeException} for an internal error.
     *
     * @param cause
     * 		underlying cause
     * @return an {@link java.lang.RuntimeException} for an internal error
     */     public static java.lang.RuntimeException createInternalError(final java.lang.Throwable cause) {         final java.lang.String argument = "https://issues.apache.org/jira/browse/MATH";

        return new java.lang.RuntimeException() {

            /**
             * Serializable version identifier.
             */             private static final long serialVersionUID = -201865440834027016L;
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.util.LocalizedFormats.INTERNAL_ERROR, argument);
            }

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.util.LocalizedFormats.INTERNAL_ERROR, argument);
            }

        };

    }

}