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
 * A TestCase that retries tests when assertions fail.
 * <p>
 * If one or more tests throw an AssertionFailedError, all tests are
 * repeated one time.
 * <p>
 * Errors or exceptions other than AssertionFailedError do not lead to retries.
 *
 * @version $Revision$ $Date$
 */
public abstract class RetryTestCase extends junit.framework.TestCase {

    public RetryTestCase() {
        super();
    }

    public RetryTestCase(java.lang.String arg0) {
        super(arg0);
    }

    /**
     * Override runTest() to catch AssertionFailedError and retry
     */
    @java.lang.Override
    protected void runTest() throws java.lang.Throwable {
        try {
            super.runTest();
        } catch (junit.framework.AssertionFailedError err) {
            // System.out.println("Retrying " + this.getName());
            super.runTest();
        }
    }

}