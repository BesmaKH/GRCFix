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
package org.apache.commons.math.stat.inference;






/**
 * Test cases for the OneWayAnovaImpl class.
 *
 * @version $Revision$ $Date$
 */

public class OneWayAnovaTest extends junit.framework.TestCase {

    protected org.apache.commons.math.stat.inference.OneWayAnova testStatistic = new org.apache.commons.math.stat.inference.OneWayAnovaImpl();

    private double[] emptyArray = new double[]{  };

    private double[] classA = 
    new double[]{ 93.0, 103.0, 95.0, 101.0, 91.0, 105.0, 96.0, 94.0, 101.0 };
    private double[] classB = 
    new double[]{ 99.0, 92.0, 102.0, 100.0, 102.0, 89.0 };
    private double[] classC = 
    new double[]{ 110.0, 115.0, 111.0, 117.0, 128.0, 117.0 };

    public OneWayAnovaTest(java.lang.String name) {
        super(name);
    }

    public void testAnovaFValue() throws java.lang.Exception {
        // Target comparison values computed using R version 2.6.0 (Linux version)
        java.util.List<double[]> threeClasses = new java.util.ArrayList<double[]>();
        threeClasses.add(classA);
        threeClasses.add(classB);
        threeClasses.add(classC);

        junit.framework.Assert.assertEquals("ANOVA F-value", 24.67361709460624, 
        testStatistic.anovaFValue(threeClasses), 1.0E-12);

        java.util.List<double[]> twoClasses = new java.util.ArrayList<double[]>();
        twoClasses.add(classA);
        twoClasses.add(classB);

        junit.framework.Assert.assertEquals("ANOVA F-value", 0.0150579150579, 
        testStatistic.anovaFValue(twoClasses), 1.0E-12);

        java.util.List<double[]> emptyContents = new java.util.ArrayList<double[]>();
        emptyContents.add(emptyArray);
        emptyContents.add(classC);
        try {
            testStatistic.anovaFValue(emptyContents);
            junit.framework.Assert.fail("empty array for key classX, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }

        java.util.List<double[]> tooFew = new java.util.ArrayList<double[]>();
        tooFew.add(classA);
        try {
            testStatistic.anovaFValue(tooFew);
            junit.framework.Assert.fail("less than two classes, IllegalArgumentException expected");
        } catch (java.lang.IllegalArgumentException ex) {
            // expected
        }
    }


    public void testAnovaPValue() throws java.lang.Exception {
        // Target comparison values computed using R version 2.6.0 (Linux version)
        java.util.List<double[]> threeClasses = new java.util.ArrayList<double[]>();
        threeClasses.add(classA);
        threeClasses.add(classB);
        threeClasses.add(classC);

        junit.framework.Assert.assertEquals("ANOVA P-value", 6.959446E-6, 
        testStatistic.anovaPValue(threeClasses), 1.0E-12);

        java.util.List<double[]> twoClasses = new java.util.ArrayList<double[]>();
        twoClasses.add(classA);
        twoClasses.add(classB);

        junit.framework.Assert.assertEquals("ANOVA P-value", 0.904212960464, 
        testStatistic.anovaPValue(twoClasses), 1.0E-12);

    }

    public void testAnovaTest() throws java.lang.Exception {
        // Target comparison values computed using R version 2.3.1 (Linux version)
        java.util.List<double[]> threeClasses = new java.util.ArrayList<double[]>();
        threeClasses.add(classA);
        threeClasses.add(classB);
        threeClasses.add(classC);

        junit.framework.Assert.assertTrue("ANOVA Test P<0.01", testStatistic.anovaTest(threeClasses, 0.01));

        java.util.List<double[]> twoClasses = new java.util.ArrayList<double[]>();
        twoClasses.add(classA);
        twoClasses.add(classB);

        junit.framework.Assert.assertFalse("ANOVA Test P>0.01", testStatistic.anovaTest(twoClasses, 0.01));
    }

}