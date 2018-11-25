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
package org.apache.commons.math.stat.descriptive;











/**
 *
 *
 * @version $Revision$ $Date$
 */ public class ListUnivariateImpl extends org.apache.commons.math.stat.descriptive.DescriptiveStatistics implements java.io.Serializable {
    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = -8837442489133392138L;
    /**
     * Holds a reference to a list - GENERICs are going to make
     * our lives easier here as we could only accept List<Number>
     */
    protected java.util.List<java.lang.Object> list;

    /**
     * Number Transformer maps Objects to Number for us.
     */     protected org.apache.commons.math.util.NumberTransformer transformer;
    /**
     * No argument Constructor
     */
    public ListUnivariateImpl() {
        this(new java.util.ArrayList<java.lang.Object>());
    }

    /**
     * Construct a ListUnivariate with a specific List.
     *
     * @param list
     * 		The list that will back this DescriptiveStatistics
     */     public ListUnivariateImpl(java.util.List<java.lang.Object> list) {         this(list, new org.apache.commons.math.util.DefaultTransformer());
    }

    /**
     * Construct a ListUnivariate with a specific List.
     *
     * @param list
     * 		The list that will back this DescriptiveStatistics
     * @param transformer
     * 		the number transformer used to convert the list items.
     */     public ListUnivariateImpl(java.util.List<java.lang.Object> list, org.apache.commons.math.util.NumberTransformer transformer) {         super();         this.list = list;
        this.transformer = transformer;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double[] getValues() {

        int length = list.size();

        // If the window size is not INFINITE_WINDOW AND
        // the current list is larger that the window size, we need to
        // take into account only the last n elements of the list
        // as definied by windowSize
        if (
        ((windowSize) != (org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW)) && 
        ((windowSize) < (list.size()))) 
        {
            length = (list.size()) - (java.lang.Math.max(0, ((list.size()) - (windowSize))));
        }

        // Create an array to hold all values
        double[] copiedArray = new double[length];

        for (int i = 0; i < (copiedArray.length); i++) {
            copiedArray[i] = getElement(i);
        }
        return copiedArray;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public double getElement(int index) {

        double value = java.lang.Double.NaN;

        int calcIndex = index;

        if (((windowSize) != (org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW)) && 
        ((windowSize) < (list.size()))) 
        {
            calcIndex = ((list.size()) - (windowSize)) + index;
        }


        try {
            value = transformer.transform(list.get(calcIndex));
        } catch (org.apache.commons.math.MathException e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public long getN() {
        int n = 0;

        if ((windowSize) != (org.apache.commons.math.stat.descriptive.DescriptiveStatistics.INFINITE_WINDOW)) {
            if ((list.size()) > (windowSize)) {
                n = windowSize;
            }else {
                n = list.size();
            }
        }else {
            n = list.size();
        }
        return n;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public void addValue(double v) {
        list.add(java.lang.Double.valueOf(v));
    }

    /**
     * Adds an object to this list.
     *
     * @param o
     * 		Object to add to the list
     */     public void addObject(java.lang.Object o) {         list.add(o);
    }

    /**
     * Clears all statistics.
     * <p>
     * <strong>N.B.: </strong> This method has the side effect of clearing the underlying list.
     */
    @java.lang.Override
    public void clear() {
        list.clear();
    }

    /**
     * Apply the given statistic to this univariate collection.
     *
     * @param stat
     * 		the statistic to apply
     * @return the computed value of the statistic.
     */     @java.lang.Override     public double apply(org.apache.commons.math.stat.descriptive.UnivariateStatistic stat) {
        double[] v = this.getValues();

        if (v != null) {
            return stat.evaluate(v, 0, v.length);
        }
        return java.lang.Double.NaN;
    }

    /**
     * Access the number transformer.
     *
     * @return the number transformer.
     */     public org.apache.commons.math.util.NumberTransformer getTransformer() {
        return transformer;
    }

    /**
     * Modify the number transformer.
     *
     * @param transformer
     * 		the new number transformer.
     */     public void setTransformer(org.apache.commons.math.util.NumberTransformer transformer) {         this.transformer = transformer;
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public synchronized void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
        // Discard elements from the front of the list if the windowSize is less than
        // the size of the list.
        int extra = (list.size()) - windowSize;
        for (int i = 0; i < extra; i++) {
            list.remove(0);
        }
    }

    /**
     * {@inheritDoc}
     */     @java.lang.Override     public synchronized int getWindowSize() {
        return windowSize;
    }

}