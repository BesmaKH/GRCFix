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
package org.apache.commons.math.random;

















/**
 * Implements <code>EmpiricalDistribution</code> interface.  This implementation
 * uses what amounts to the
 * <a href="http://nedwww.ipac.caltech.edu/level5/March02/Silverman/Silver2_6.html">
 * Variable Kernel Method</a> with Gaussian smoothing:<p>
 * <strong>Digesting the input file</strong>
 * <ol><li>Pass the file once to compute min and max.</li>
 * <li>Divide the range from min-max into <code>binCount</code> "bins."</li>
 * <li>Pass the data file again, computing bin counts and univariate
 *     statistics (mean, std dev.) for each of the bins </li>
 * <li>Divide the interval (0,1) into subintervals associated with the bins,
 *     with the length of a bin's subinterval proportional to its count.</li></ol>
 * <strong>Generating random values from the distribution</strong><ol>
 * <li>Generate a uniformly distributed value in (0,1) </li>
 * <li>Select the subinterval to which the value belongs.
 * <li>Generate a random Gaussian value with mean = mean of the associated
 *     bin and std dev = std dev of associated bin.</li></ol></p><p>
 * <strong>USAGE NOTES:</strong><ul>
 * <li>The <code>binCount</code> is set by default to 1000.  A good rule of thumb
 *    is to set the bin count to approximately the length of the input file divided
 *    by 10. </li>
 * <li>The input file <i>must</i> be a plain text file containing one valid numeric
 *    entry per line.</li>
 * </ul></p>
 *
 * @version $Revision$ $Date$
 */
public class EmpiricalDistributionImpl implements java.io.Serializable , org.apache.commons.math.random.EmpiricalDistribution {

    /**
     * Serializable version identifier
     */     private static final long serialVersionUID = 5729073523949762654L;
    /**
     * List of SummaryStatistics objects characterizing the bins
     */     private java.util.List<org.apache.commons.math.stat.descriptive.SummaryStatistics> binStats = null;
    /**
     * Sample statistics
     */     private org.apache.commons.math.stat.descriptive.SummaryStatistics sampleStats = null;
    /**
     * Max loaded value
     */     private double max = java.lang.Double.NEGATIVE_INFINITY;
    /**
     * Min loaded value
     */     private double min = java.lang.Double.POSITIVE_INFINITY;
    /**
     * Grid size
     */     private double delta = 0.0;
    /**
     * number of bins
     */     private int binCount = 1000;
    /**
     * is the distribution loaded?
     */     private boolean loaded = false;
    /**
     * upper bounds of subintervals in (0,1) "belonging" to the bins
     */     private double[] upperBounds = null;
    /**
     * RandomData instance to use in repeated calls to getNext()
     */     private org.apache.commons.math.random.RandomData randomData = new org.apache.commons.math.random.RandomDataImpl();
    /**
     * Creates a new EmpiricalDistribution with the default bin count.
     */
    public EmpiricalDistributionImpl() {
        binStats = new java.util.ArrayList<org.apache.commons.math.stat.descriptive.SummaryStatistics>();
    }

    /**
     * Creates a new EmpiricalDistribution  with the specified bin count.
     *
     * @param binCount
     * 		number of bins
     */     public EmpiricalDistributionImpl(int binCount) {
        this.binCount = binCount;
        binStats = new java.util.ArrayList<org.apache.commons.math.stat.descriptive.SummaryStatistics>();
    }

    /**
     * Computes the empirical distribution from the provided
     * array of numbers.
     *
     * @param in
     * 		the input data array
     */     public void load(double[] in) {
        org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter da = new org.apache.commons.math.random.EmpiricalDistributionImpl.ArrayDataAdapter(in);
        try {
            da.computeStats();
            fillBinStats(in);
        } catch (java.io.IOException e) {
            throw new org.apache.commons.math.MathRuntimeException(e);
        }
        loaded = true;

    }

    /**
     * Computes the empirical distribution using data read from a URL.
     *
     * @param url
     * 		url of the input file
     * @throws IOException
     * 		if an IO error occurs
     */     public void load(java.net.URL url) throws java.io.IOException {         java.io.BufferedReader in = 
        new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
        try {
            org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter da = new org.apache.commons.math.random.EmpiricalDistributionImpl.StreamDataAdapter(in);
            da.computeStats();
            if ((sampleStats.getN()) == 0) {
                throw org.apache.commons.math.MathRuntimeException.createEOFException(org.apache.commons.math.util.LocalizedFormats.URL_CONTAINS_NO_DATA, 
                url);
            }
            in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
            fillBinStats(in);
            loaded = true;
        } finally {
            try {
                in.close();
            } catch (java.io.IOException ex) {
                // ignore
            }
        }
    }

    /**
     * Computes the empirical distribution from the input file.
     *
     * @param file
     * 		the input file
     * @throws IOException
     * 		if an IO error occurs
     */     public void load(java.io.File file) throws java.io.IOException {         java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(file));
        try {
            org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter da = new org.apache.commons.math.random.EmpiricalDistributionImpl.StreamDataAdapter(in);
            da.computeStats();
            in = new java.io.BufferedReader(new java.io.FileReader(file));
            fillBinStats(in);
            loaded = true;
        } finally {
            try {
                in.close();
            } catch (java.io.IOException ex) {
                // ignore
            }
        }
    }

    /**
     * Provides methods for computing <code>sampleStats</code> and
     * <code>beanStats</code> abstracting the source of data.
     */
    private abstract class DataAdapter {

        /**
         * Compute bin stats.
         *
         * @throws IOException
         * 		if an error occurs computing bin stats
         */         public abstract void computeBinStats() throws java.io.IOException;

        /**
         * Compute sample statistics.
         *
         * @throws IOException
         * 		if an error occurs computing sample stats
         */         public abstract void computeStats() throws java.io.IOException;

    }

    /**
     * Factory of <code>DataAdapter</code> objects. For every supported source
     * of data (array of doubles, file, etc.) an instance of the proper object
     * is returned.
     */
    private class DataAdapterFactory {
        /**
         * Creates a DataAdapter from a data object
         *
         * @param in
         * 		object providing access to the data
         * @return DataAdapter instance
         */         public org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter getAdapter(java.lang.Object in) {
            if (in instanceof java.io.BufferedReader) {
                java.io.BufferedReader inputStream = ((java.io.BufferedReader) (in));
                return new org.apache.commons.math.random.EmpiricalDistributionImpl.StreamDataAdapter(inputStream);
            }else                 if (in instanceof double[]) {
                    double[] inputArray = ((double[]) (in));
                    return new org.apache.commons.math.random.EmpiricalDistributionImpl.ArrayDataAdapter(inputArray);
                }else {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(
                    org.apache.commons.math.util.LocalizedFormats.INPUT_DATA_FROM_UNSUPPORTED_DATASOURCE, 
                    in.getClass().getName(), 
                    java.io.BufferedReader.class.getName(), double[].class.getName());
                }
        }
    }
    /**
     * <code>DataAdapter</code> for data provided through some input stream
     */
    private class StreamDataAdapter extends org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter {

        /**
         * Input stream providing access to the data
         */         private java.io.BufferedReader inputStream;
        /**
         * Create a StreamDataAdapter from a BufferedReader
         *
         * @param in
         * 		BufferedReader input stream
         */         public StreamDataAdapter(java.io.BufferedReader in) {
            super();
            inputStream = in;
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public void computeBinStats() throws java.io.IOException {
            java.lang.String str = null;
            double val = 0.0;
            while ((str = inputStream.readLine()) != null) {
                val = java.lang.Double.parseDouble(str);
                org.apache.commons.math.stat.descriptive.SummaryStatistics stats = binStats.get(findBin(val));
                stats.addValue(val);
            } 

            inputStream.close();
            inputStream = null;
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public void computeStats() throws java.io.IOException {
            java.lang.String str = null;
            double val = 0.0;
            sampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
            while ((str = inputStream.readLine()) != null) {
                val = java.lang.Double.valueOf(str).doubleValue();
                sampleStats.addValue(val);
            } 
            inputStream.close();
            inputStream = null;
        }
    }

    /**
     * <code>DataAdapter</code> for data provided as array of doubles.
     */
    private class ArrayDataAdapter extends org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter {

        /**
         * Array of input  data values
         */         private double[] inputArray;
        /**
         * Construct an ArrayDataAdapter from a double[] array
         *
         * @param in
         * 		double[] array holding the data
         */         public ArrayDataAdapter(double[] in) {
            super();
            inputArray = in;
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public void computeStats() throws java.io.IOException {
            sampleStats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
            for (int i = 0; i < (inputArray.length); i++) {
                sampleStats.addValue(inputArray[i]);
            }
        }

        /**
         * {@inheritDoc}
         */         @java.lang.Override         public void computeBinStats() throws java.io.IOException {
            for (int i = 0; i < (inputArray.length); i++) {
                org.apache.commons.math.stat.descriptive.SummaryStatistics stats = 
                binStats.get(findBin(inputArray[i]));
                stats.addValue(inputArray[i]);
            }
        }
    }

    /**
     * Fills binStats array (second pass through data file).
     *
     * @param in
     * 		object providing access to the data
     * @throws IOException
     * 		if an IO error occurs
     */     private void fillBinStats(java.lang.Object in) throws java.io.IOException {         // Set up grid
        min = sampleStats.getMin();
        max = sampleStats.getMax();
        delta = ((max) - (min)) / (java.lang.Double.valueOf(binCount).doubleValue());

        // Initialize binStats ArrayList
        if (!(binStats.isEmpty())) {
            binStats.clear();
        }
        for (int i = 0; i < (binCount); i++) {
            org.apache.commons.math.stat.descriptive.SummaryStatistics stats = new org.apache.commons.math.stat.descriptive.SummaryStatistics();
            binStats.add(i, stats);
        }

        // Filling data in binStats Array
        org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapterFactory aFactory = new org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapterFactory();
        org.apache.commons.math.random.EmpiricalDistributionImpl.DataAdapter da = aFactory.getAdapter(in);
        da.computeBinStats();

        // Assign upperBounds based on bin counts
        upperBounds = new double[binCount];
        upperBounds[0] = 
        ((double) (binStats.get(0).getN())) / ((double) (sampleStats.getN()));
        for (int i = 1; i < ((binCount) - 1); i++) {
            upperBounds[i] = (upperBounds[(i - 1)]) + 
            (((double) (binStats.get(i).getN())) / ((double) (sampleStats.getN())));
        }
        upperBounds[((binCount) - 1)] = 1.0;
    }

    /**
     * Returns the index of the bin to which the given value belongs
     *
     * @param value
     * 		the value whose bin we are trying to find
     * @return the index of the bin containing the value
     */     private int findBin(double value) {
        return java.lang.Math.min(
        java.lang.Math.max((((int) (java.lang.Math.ceil(((value - (min)) / (delta))))) - 1), 0), 
        ((binCount) - 1));
    }

    /**
     * Generates a random value from this distribution.
     *
     * @return the random value.
     * @throws IllegalStateException
     * 		if the distribution has not been loaded
     */     public double getNextValue() throws java.lang.IllegalStateException {

        if (!(loaded)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.util.LocalizedFormats.DISTRIBUTION_NOT_LOADED);
        }

        // Start with a uniformly distributed random number in (0,1)
        double x = java.lang.Math.random();

        // Use this to select the bin and generate a Gaussian within the bin
        for (int i = 0; i < (binCount); i++) {
            if (x <= (upperBounds[i])) {
                org.apache.commons.math.stat.descriptive.SummaryStatistics stats = binStats.get(i);
                if ((stats.getN()) > 0) {
                    if ((stats.getStandardDeviation()) > 0) {                         // more than one obs
                        return randomData.nextGaussian(
                        stats.getMean(), stats.getStandardDeviation());
                    }else {
                        return stats.getMean();// only one obs in bin
                    }
                }
            }
        }
        throw new org.apache.commons.math.MathRuntimeException(org.apache.commons.math.util.LocalizedFormats.NO_BIN_SELECTED);
    }

    /**
     * Returns a {@link StatisticalSummary} describing this distribution.
     * <strong>Preconditions:</strong><ul>
     * <li>the distribution must be loaded before invoking this method</li></ul>
     *
     * @return the sample statistics
     * @throws IllegalStateException
     * 		if the distribution has not been loaded
     */     public org.apache.commons.math.stat.descriptive.StatisticalSummary getSampleStats() {
        return sampleStats;
    }

    /**
     * Returns the number of bins.
     *
     * @return the number of bins.
     */
    public int getBinCount() {
        return binCount;
    }

    /**
     * Returns a List of {@link SummaryStatistics} instances containing
     * statistics describing the values in each of the bins.  The list is
     * indexed on the bin number.
     *
     * @return List of bin statistics.
     */
    public java.util.List<org.apache.commons.math.stat.descriptive.SummaryStatistics> getBinStats() {
        return binStats;
    }

    /**
     * <p>Returns a fresh copy of the array of upper bounds for the bins.
     * Bins are: <br/>
     * [min,upperBounds[0]],(upperBounds[0],upperBounds[1]],...,
     *  (upperBounds[binCount-2], upperBounds[binCount-1] = max].</p>
     *
     * <p>Note: In versions 1.0-2.0 of commons-math, this method
     * incorrectly returned the array of probability generator upper
     * bounds now returned by {@link #getGeneratorUpperBounds()}.</p>
     *
     * @return array of bin upper bounds
     * @since 2.1
     */
    public double[] getUpperBounds() {
        double[] binUpperBounds = new double[binCount];
        binUpperBounds[0] = (min) + (delta);
        for (int i = 1; i < ((binCount) - 1); i++) {
            binUpperBounds[i] = (binUpperBounds[(i - 1)]) + (delta);
        }
        binUpperBounds[((binCount) - 1)] = max;
        return binUpperBounds;
    }

    /**
     * <p>Returns a fresh copy of the array of upper bounds of the subintervals
     * of [0,1] used in generating data from the empirical distribution.
     * Subintervals correspond to bins with lengths proportional to bin counts.</p>
     *
     * <p>In versions 1.0-2.0 of commons-math, this array was (incorrectly) returned
     * by {@link #getUpperBounds()}.</p>
     *
     * @since 2.1
     * @return array of upper bounds of subintervals used in data generation
     */
    public double[] getGeneratorUpperBounds() {
        int len = upperBounds.length;
        double[] out = new double[len];
        java.lang.System.arraycopy(upperBounds, 0, out, 0, len);
        return out;
    }

    /**
     * Property indicating whether or not the distribution has been loaded.
     *
     * @return true if the distribution has been loaded
     */
    public boolean isLoaded() {
        return loaded;
    }
}