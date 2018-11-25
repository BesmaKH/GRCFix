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
package org.apache.commons.math.analysis;





/**
 * Base class for {@link UnivariateRealFunction} that can be composed with other functions.
 *
 * @since 2.1
 * @version $Revision$ $Date$
 */
public abstract class ComposableFunction implements org.apache.commons.math.analysis.UnivariateRealFunction {

    /**
     * The constant function always returning 0.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ZERO = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return 0;
        }
    };

    /**
     * The constant function always returning 1.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ONE = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return 1;
        }
    };

    /**
     * The identity function.
     */     public static final org.apache.commons.math.analysis.ComposableFunction IDENTITY = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return d;
        }
    };

    /**
     * The {@code Math.abs} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ABS = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.abs(d);
        }
    };

    /**
     * The - operator wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction NEGATE = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return -d;
        }
    };

    /**
     * The invert operator wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction INVERT = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return 1 / d;
        }
    };

    /**
     * The {@code Math.sin} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction SIN = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.sin(d);
        }
    };

    /**
     * The {@code Math.sqrt} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction SQRT = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.sqrt(d);
        }
    };

    /**
     * The {@code Math.sinh} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction SINH = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.sinh(d);
        }
    };

    /**
     * The {@code Math.exp} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction EXP = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.exp(d);
        }
    };

    /**
     * The {@code Math.expm1} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction EXPM1 = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.expm1(d);
        }
    };

    /**
     * The {@code Math.asin} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ASIN = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.asin(d);
        }
    };

    /**
     * The {@code Math.atan} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ATAN = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.atan(d);
        }
    };

    /**
     * The {@code Math.tan} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction TAN = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.tan(d);
        }
    };

    /**
     * The {@code Math.tanh} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction TANH = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.tanh(d);
        }
    };

    /**
     * The {@code Math.cbrt} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction CBRT = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.cbrt(d);
        }
    };

    /**
     * The {@code Math.ceil} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction CEIL = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.ceil(d);
        }
    };

    /**
     * The {@code Math.floor} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction FLOOR = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.floor(d);
        }
    };

    /**
     * The {@code Math.log} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction LOG = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.log(d);
        }
    };

    /**
     * The {@code Math.log10} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction LOG10 = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.log10(d);
        }
    };

    /**
     * The {@code Math.log1p} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction LOG1P = new org.apache.commons.math.analysis.ComposableFunction() {         @java.lang.Override
        public double value(double d) {
            return java.lang.Math.log1p(d);
        }
    };

    /**
     * The {@code Math.cos} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction COS = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.cos(d);
        }
    };

    /**
     * The {@code Math.abs} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ACOS = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.acos(d);
        }
    };

    /**
     * The {@code Math.cosh} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction COSH = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.cosh(d);
        }
    };

    /**
     * The {@code Math.rint} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction RINT = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.rint(d);
        }
    };

    /**
     * The {@code Math.signum} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction SIGNUM = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.signum(d);
        }
    };

    /**
     * The {@code Math.ulp} method wrapped as a {@link ComposableFunction}.
     */     public static final org.apache.commons.math.analysis.ComposableFunction ULP = new org.apache.commons.math.analysis.ComposableFunction() {         /**
         * {@inheritDoc}
         */         @java.lang.Override         public double value(double d) {
            return java.lang.Math.ulp(d);
        }
    };

    /**
     * Precompose the instance with another function.
     * <p>
     * The composed function h created by {@code h = g.of(f)} is such
     * that {@code h.value(x) == g.value(f.value(x))} for all x.
     * </p>
     *
     * @param f
     * 		function to compose with
     * @return a new function which computes {@code this.value(f.value(x))}
     * @see #postCompose(UnivariateRealFunction)
     */     public org.apache.commons.math.analysis.ComposableFunction of(final org.apache.commons.math.analysis.UnivariateRealFunction f) {         return new org.apache.commons.math.analysis.ComposableFunction() {
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return org.apache.commons.math.analysis.ComposableFunction.this.value(f.value(x));
            }
        };
    }

    /**
     * Postcompose the instance with another function.
     * <p>
     * The composed function h created by {@code h = g.postCompose(f)} is such
     * that {@code h.value(x) == f.value(g.value(x))} for all x.
     * </p>
     *
     * @param f
     * 		function to compose with
     * @return a new function which computes {@code f.value(this.value(x))}
     * @see #of(UnivariateRealFunction)
     */     public org.apache.commons.math.analysis.ComposableFunction postCompose(final org.apache.commons.math.analysis.UnivariateRealFunction f) {         return new org.apache.commons.math.analysis.ComposableFunction() {
            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return f.value(org.apache.commons.math.analysis.ComposableFunction.this.value(x));
            }
        };
    }

    /**
     * Return a function combining the instance and another function.
     * <p>
     * The function h created by {@code h = g.combine(f, combiner)} is such that
     * {@code h.value(x) == combiner.value(g.value(x), f.value(x))} for all x.
     * </p>
     *
     * @param f
     * 		function to combine with the instance
     * @param combiner
     * 		bivariate function used for combining
     * @return a new function which computes {@code combine.value(this.value(x), f.value(x))}
     */     public org.apache.commons.math.analysis.ComposableFunction combine(final org.apache.commons.math.analysis.UnivariateRealFunction f, final org.apache.commons.math.analysis.BivariateRealFunction combiner) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return combiner.value(org.apache.commons.math.analysis.ComposableFunction.this.value(x), f.value(x));
            }
        };
    }

    /**
     * Return a function adding the instance and another function.
     *
     * @param f
     * 		function to combine with the instance
     * @return a new function which computes {@code this.value(x) + f.value(x)}
     */     public org.apache.commons.math.analysis.ComposableFunction add(final org.apache.commons.math.analysis.UnivariateRealFunction f) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return (org.apache.commons.math.analysis.ComposableFunction.this.value(x)) + (f.value(x));
            }
        };
    }

    /**
     * Return a function adding a constant term to the instance.
     *
     * @param a
     * 		term to add
     * @return a new function which computes {@code this.value(x) + a}
     */     public org.apache.commons.math.analysis.ComposableFunction add(final double a) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return (org.apache.commons.math.analysis.ComposableFunction.this.value(x)) + a;
            }
        };
    }

    /**
     * Return a function subtracting another function from the instance.
     *
     * @param f
     * 		function to combine with the instance
     * @return a new function which computes {@code this.value(x) - f.value(x)}
     */     public org.apache.commons.math.analysis.ComposableFunction subtract(final org.apache.commons.math.analysis.UnivariateRealFunction f) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return (org.apache.commons.math.analysis.ComposableFunction.this.value(x)) - (f.value(x));
            }
        };
    }

    /**
     * Return a function multiplying the instance and another function.
     *
     * @param f
     * 		function to combine with the instance
     * @return a new function which computes {@code this.value(x) * f.value(x)}
     */     public org.apache.commons.math.analysis.ComposableFunction multiply(final org.apache.commons.math.analysis.UnivariateRealFunction f) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return (org.apache.commons.math.analysis.ComposableFunction.this.value(x)) * (f.value(x));
            }
        };
    }

    /**
     * Return a function scaling the instance by a constant factor.
     *
     * @param scaleFactor
     * 		constant scaling factor
     * @return a new function which computes {@code this.value(x) * scaleFactor}
     */     public org.apache.commons.math.analysis.ComposableFunction multiply(final double scaleFactor) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return (org.apache.commons.math.analysis.ComposableFunction.this.value(x)) * scaleFactor;
            }
        };
    }
    /**
     * Return a function dividing the instance by another function.
     *
     * @param f
     * 		function to combine with the instance
     * @return a new function which computes {@code this.value(x) / f.value(x)}
     */     public org.apache.commons.math.analysis.ComposableFunction divide(final org.apache.commons.math.analysis.UnivariateRealFunction f) {         return new org.apache.commons.math.analysis.ComposableFunction() {

            /**
             * {@inheritDoc}
             */             @java.lang.Override             public double value(double x) throws org.apache.commons.math.FunctionEvaluationException {                 return (org.apache.commons.math.analysis.ComposableFunction.this.value(x)) / (f.value(x));
            }
        };
    }

    /**
     * Generates a function that iteratively apply instance function on all
     * elements of an array.
     * <p>
     * The generated function behaves as follows:
     * <ul>
     *   <li>initialize result = initialValue</li>
     *   <li>iterate: {@code result = combiner.value(result,
     *   this.value(nextMultivariateEntry));}</li>
     *   <li>return result</li>
     * </ul>
     * </p>
     *
     * @param combiner
     * 		combiner to use between entries
     * @param initialValue
     * 		initial value to use before first entry
     * @return a new function that iteratively applie instance function on all
     * elements of an array.
     */     public org.apache.commons.math.analysis.MultivariateRealFunction asCollector(final org.apache.commons.math.analysis.BivariateRealFunction combiner, final double initialValue) {         return new org.apache.commons.math.analysis.MultivariateRealFunction() {
            /**
             * {@inheritDoc}
             */             public double value(double[] point) throws java.lang.IllegalArgumentException, org.apache.commons.math.FunctionEvaluationException {
                double result = initialValue;
                for (final double entry : point) {
                    result = combiner.value(result, org.apache.commons.math.analysis.ComposableFunction.this.value(entry));
                }
                return result;
            }
        };
    }

    /**
     * Generates a function that iteratively apply instance function on all
     * elements of an array.
     * <p>
     * Calling this method is equivalent to call {@link
     * #asCollector(BivariateRealFunction, double) asCollector(BivariateRealFunction, 0.0)}.
     * </p>
     *
     * @param combiner
     * 		combiner to use between entries
     * @return a new function that iteratively applie instance function on all
     * elements of an array.
     * @see #asCollector(BivariateRealFunction, double)
     */     public org.apache.commons.math.analysis.MultivariateRealFunction asCollector(final org.apache.commons.math.analysis.BivariateRealFunction combiner) {         return asCollector(combiner, 0.0);
    }

    /**
     * Generates a function that iteratively apply instance function on all
     * elements of an array.
     * <p>
     * Calling this method is equivalent to call {@link
     * #asCollector(BivariateRealFunction, double) asCollector(BinaryFunction.ADD, initialValue)}.
     * </p>
     *
     * @param initialValue
     * 		initial value to use before first entry
     * @return a new function that iteratively applie instance function on all
     * elements of an array.
     * @see #asCollector(BivariateRealFunction, double)
     * @see BinaryFunction#ADD
     */     public org.apache.commons.math.analysis.MultivariateRealFunction asCollector(final double initialValue) {         return asCollector(org.apache.commons.math.analysis.BinaryFunction.ADD, initialValue);
    }

    /**
     * Generates a function that iteratively apply instance function on all
     * elements of an array.
     * <p>
     * Calling this method is equivalent to call {@link
     * #asCollector(BivariateRealFunction, double) asCollector(BinaryFunction.ADD, 0.0)}.
     * </p>
     *
     * @return a new function that iteratively applie instance function on all
     * elements of an array.
     * @see #asCollector(BivariateRealFunction, double)
     * @see BinaryFunction#ADD
     */     public org.apache.commons.math.analysis.MultivariateRealFunction asCollector() {
        return asCollector(org.apache.commons.math.analysis.BinaryFunction.ADD, 0.0);
    }

    /**
     * {@inheritDoc}
     */     public abstract double value(double x) throws org.apache.commons.math.FunctionEvaluationException;
}