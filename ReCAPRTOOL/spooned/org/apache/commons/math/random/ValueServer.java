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
 * Generates values for use in simulation applications.
 * <p>
 * How values are generated is determined by the <code>mode</code>
 * property.</p>
 * <p>
 * Supported <code>mode</code> values are: <ul>
 * <li> DIGEST_MODE -- uses an empirical distribution </li>
 * <li> REPLAY_MODE -- replays data from <code>valuesFileURL</code></li>
 * <li> UNIFORM_MODE -- generates uniformly distributed random values with
 *                      mean = <code>mu</code> </li>
 * <li> EXPONENTIAL_MODE -- generates exponentially distributed random values
 *                         with mean = <code>mu</code></li>
 * <li> GAUSSIAN_MODE -- generates Gaussian distributed random values with
 *                       mean = <code>mu</code> and
 *                       standard deviation = <code>sigma</code></li>
 * <li> CONSTANT_MODE -- returns <code>mu</code> every time.</li></ul></p>
 *
 * @version $Revision$ $Date$
 */

public class ValueServer {

    /**
     * Use empirical distribution.
     */     public static final int DIGEST_MODE = 0;
    /**
     * Replay data from valuesFilePath.
     */     public static final int REPLAY_MODE = 1;
    /**
     * Uniform random deviates with mean = &mu;.
     */     public static final int UNIFORM_MODE = 2;
    /**
     * Exponential random deviates with mean = &mu;.
     */     public static final int EXPONENTIAL_MODE = 3;
    /**
     * Gaussian random deviates with mean = &mu;, std dev = &sigma;.
     */     public static final int GAUSSIAN_MODE = 4;
    /**
     * Always return mu
     */     public static final int CONSTANT_MODE = 5;
    /**
     * mode determines how values are generated.
     */     private int mode = 5;
    /**
     * URI to raw data values.
     */     private java.net.URL valuesFileURL = null;
    /**
     * Mean for use with non-data-driven modes.
     */     private double mu = 0.0;
    /**
     * Standard deviation for use with GAUSSIAN_MODE.
     */     private double sigma = 0.0;
    /**
     * Empirical probability distribution for use with DIGEST_MODE.
     */     private org.apache.commons.math.random.EmpiricalDistribution empiricalDistribution = null;
    /**
     * File pointer for REPLAY_MODE.
     */     private java.io.BufferedReader filePointer = null;
    /**
     * RandomDataImpl to use for random data generation.
     */     private org.apache.commons.math.random.RandomData randomData = new org.apache.commons.math.random.RandomDataImpl();
    // Data generation modes ======================================

    /**
     * Creates new ValueServer
     */     public ValueServer() {}

    /**
     * Construct a ValueServer instance using a RandomData as its source
     * of random data.
     *
     * @param randomData
     * 		the RandomData instance used to source random data
     * @since 1.1
     */     public ValueServer(org.apache.commons.math.random.RandomData randomData) {
        this.randomData = randomData;
    }

    /**
     * Returns the next generated value, generated according
     * to the mode value (see MODE constants).
     *
     * @return generated value
     * @throws IOException
     * 		in REPLAY_MODE if a file I/O error occurs
     */     public double getNext() throws java.io.IOException {
        switch (mode) {
            case org.apache.commons.math.random.ValueServer.DIGEST_MODE :                 return getNextDigest();
            case org.apache.commons.math.random.ValueServer.REPLAY_MODE :                 return getNextReplay();
            case org.apache.commons.math.random.ValueServer.UNIFORM_MODE :                 return getNextUniform();
            case org.apache.commons.math.random.ValueServer.EXPONENTIAL_MODE :                 return getNextExponential();
            case org.apache.commons.math.random.ValueServer.GAUSSIAN_MODE :                 return getNextGaussian();
            case org.apache.commons.math.random.ValueServer.CONSTANT_MODE :                 return mu;
            default :                 throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(
                org.apache.commons.math.util.LocalizedFormats.UNKNOWN_MODE, 
                mode, 
                "DIGEST_MODE", org.apache.commons.math.random.ValueServer.DIGEST_MODE, "REPLAY_MODE", org.apache.commons.math.random.ValueServer.REPLAY_MODE, 
                "UNIFORM_MODE", org.apache.commons.math.random.ValueServer.UNIFORM_MODE, "EXPONENTIAL_MODE", org.apache.commons.math.random.ValueServer.EXPONENTIAL_MODE, 
                "GAUSSIAN_MODE", org.apache.commons.math.random.ValueServer.GAUSSIAN_MODE, "CONSTANT_MODE", org.apache.commons.math.random.ValueServer.CONSTANT_MODE);}

    }

    /**
     * Fills the input array with values generated using getNext() repeatedly.
     *
     * @param values
     * 		array to be filled
     * @throws IOException
     * 		in REPLAY_MODE if a file I/O error occurs
     */     public void fill(double[] values) throws java.io.IOException {         for (int i = 0; i < (values.length); i++) {
            values[i] = getNext();
        }
    }

    /**
     * Returns an array of length <code>length</code> with values generated
     * using getNext() repeatedly.
     *
     * @param length
     * 		length of output array
     * @return array of generated values
     * @throws IOException
     * 		in REPLAY_MODE if a file I/O error occurs
     */     public double[] fill(int length) throws java.io.IOException {         double[] out = new double[length];
        for (int i = 0; i < length; i++) {
            out[i] = getNext();
        }
        return out;
    }

    /**
     * Computes the empirical distribution using values from the file
     * in <code>valuesFileURL</code>, using the default number of bins.
     * <p>
     * <code>valuesFileURL</code> must exist and be
     * readable by *this at runtime.</p>
     * <p>
     * This method must be called before using <code>getNext()</code>
     * with <code>mode = DIGEST_MODE</code></p>
     *
     * @throws IOException
     * 		if an I/O error occurs reading the input file
     */     public void computeDistribution() throws java.io.IOException {
        empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl();
        empiricalDistribution.load(valuesFileURL);
    }

    /**
     * Computes the empirical distribution using values from the file
     * in <code>valuesFileURL</code> and <code>binCount</code> bins.
     * <p>
     * <code>valuesFileURL</code> must exist and be readable by this process
     * at runtime.</p>
     * <p>
     * This method must be called before using <code>getNext()</code>
     * with <code>mode = DIGEST_MODE</code></p>
     *
     * @param binCount
     * 		the number of bins used in computing the empirical
     * 		distribution
     * @throws IOException
     * 		if an error occurs reading the input file
     */     public void computeDistribution(int binCount) throws java.io.IOException {
        empiricalDistribution = new org.apache.commons.math.random.EmpiricalDistributionImpl(binCount);
        empiricalDistribution.load(valuesFileURL);
        mu = empiricalDistribution.getSampleStats().getMean();
        sigma = empiricalDistribution.getSampleStats().getStandardDeviation();
    }

    /**
     * Getter for property mode.
     *
     * @return Value of property mode.
     */     public int getMode() {         return mode;
    }

    /**
     * Setter for property mode.
     *
     * @param mode
     * 		New value of property mode.
     */     public void setMode(int mode) {         this.mode = mode;}

    /**
     * Getter for <code>valuesFileURL<code>
     *
     * @return Value of property valuesFileURL.
     */     public java.net.URL getValuesFileURL() {
        return valuesFileURL;
    }

    /**
     * Sets the <code>valuesFileURL</code> using a string URL representation
     *
     * @param url
     * 		String representation for new valuesFileURL.
     * @throws MalformedURLException
     * 		if url is not well formed
     */     public void setValuesFileURL(java.lang.String url) throws java.net.MalformedURLException {         this.valuesFileURL = new java.net.URL(url);}

    /**
     * Sets the <code>valuesFileURL</code>
     *
     * @param url
     * 		New value of property valuesFileURL.
     */     public void setValuesFileURL(java.net.URL url) {         this.valuesFileURL = url;
    }

    /**
     * Getter for property empiricalDistribution.
     *
     * @return Value of property empiricalDistribution.
     */     public org.apache.commons.math.random.EmpiricalDistribution getEmpiricalDistribution() {         return empiricalDistribution;
    }

    /**
     * Resets REPLAY_MODE file pointer to the beginning of the <code>valuesFileURL</code>.
     *
     * @throws IOException
     * 		if an error occurs opening the file
     */     public void resetReplayFile() throws java.io.IOException {
        if ((filePointer) != null) {
            try {
                filePointer.close();
                filePointer = null;
            } catch (java.io.IOException ex) {
                // ignore
            }
        }
        filePointer = new java.io.BufferedReader(new java.io.InputStreamReader(valuesFileURL.openStream()));
    }

    /**
     * Closes <code>valuesFileURL</code> after use in REPLAY_MODE.
     *
     * @throws IOException
     * 		if an error occurs closing the file
     */     public void closeReplayFile() throws java.io.IOException {
        if ((filePointer) != null) {
            filePointer.close();
            filePointer = null;
        }
    }

    /**
     * Getter for property mu.
     *
     * @return Value of property mu.
     */     public double getMu() {         return mu;
    }

    /**
     * Setter for property mu.
     *
     * @param mu
     * 		New value of property mu.
     */     public void setMu(double mu) {         this.mu = mu;}

    /**
     * Getter for property sigma.
     *
     * @return Value of property sigma.
     */     public double getSigma() {         return sigma;
    }

    /**
     * Setter for property sigma.
     *
     * @param sigma
     * 		New value of property sigma.
     */     public void setSigma(double sigma) {         this.sigma = sigma;}

    // ------------- private methods ---------------------------------

    /**
     * Gets a random value in DIGEST_MODE.
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>Before this method is called, <code>computeDistribution()</code>
     * must have completed successfully; otherwise an
     * <code>IllegalStateException</code> will be thrown</li></ul></p>
     *
     * @return next random value from the empirical distribution digest
     */
    private double getNextDigest() {
        if (((empiricalDistribution) == null) || 
        ((empiricalDistribution.getBinStats().size()) == 0)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.util.LocalizedFormats.DIGEST_NOT_INITIALIZED);
        }
        return empiricalDistribution.getNextValue();
    }

    /**
     * Gets next sequential value from the <code>valuesFileURL</code>.
     * <p>
     * Throws an IOException if the read fails.</p>
     * <p>
     * This method will open the <code>valuesFileURL</code> if there is no
     * replay file open.</p>
     * <p>
     * The <code>valuesFileURL</code> will be closed and reopened to wrap around
     * from EOF to BOF if EOF is encountered. EOFException (which is a kind of
     * IOException) may still be thrown if the <code>valuesFileURL</code> is
     * empty.</p>
     *
     * @return next value from the replay file
     * @throws IOException
     * 		if there is a problem reading from the file
     * @throws NumberFormatException
     * 		if an invalid numeric string is
     * 		encountered in the file
     */     private double getNextReplay() throws java.io.IOException {         java.lang.String str = null;
        if ((filePointer) == null) {
            resetReplayFile();
        }
        if ((str = filePointer.readLine()) == null) {
            // we have probably reached end of file, wrap around from EOF to BOF
            closeReplayFile();
            resetReplayFile();
            if ((str = filePointer.readLine()) == null) {
                throw org.apache.commons.math.MathRuntimeException.createEOFException(org.apache.commons.math.util.LocalizedFormats.URL_CONTAINS_NO_DATA, 
                valuesFileURL);
            }
        }
        return java.lang.Double.valueOf(str).doubleValue();
    }

    /**
     * Gets a uniformly distributed random value with mean = mu.
     *
     * @return random uniform value
     */
    private double getNextUniform() {
        return randomData.nextUniform(0, (2 * (mu)));
    }

    /**
     * Gets an exponentially distributed random value with mean = mu.
     *
     * @return random exponential value
     */
    private double getNextExponential() {
        return randomData.nextExponential(mu);
    }

    /**
     * Gets a Gaussian distributed random value with mean = mu
     * and standard deviation = sigma.
     *
     * @return random Gaussian value
     */
    private double getNextGaussian() {
        return randomData.nextGaussian(mu, sigma);
    }

}