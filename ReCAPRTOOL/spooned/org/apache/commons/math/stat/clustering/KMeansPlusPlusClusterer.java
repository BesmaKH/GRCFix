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
package org.apache.commons.math.stat.clustering;







/**
 * Clustering algorithm based on David Arthur and Sergei Vassilvitski k-means++ algorithm.
 *
 * @param <T>
 * 		type of the points to cluster
 * @see <a href="http://en.wikipedia.org/wiki/K-means%2B%2B">K-means++ (wikipedia)</a>
 * @version $Revision$ $Date$
 * @since 2.0
 */ public class KMeansPlusPlusClusterer<T extends org.apache.commons.math.stat.clustering.Clusterable<T>> {
    /**
     * Random generator for choosing initial centers.
     */     private final java.util.Random random;
    /**
     * Build a clusterer.
     *
     * @param random
     * 		random generator to use for choosing initial centers
     */     public KMeansPlusPlusClusterer(final java.util.Random random) {         this.random = random;}

    /**
     * Runs the K-means++ clustering algorithm.
     *
     * @param points
     * 		the points to cluster
     * @param k
     * 		the number of clusters to split the data into
     * @param maxIterations
     * 		the maximum number of iterations to run the algorithm
     * 		for.  If negative, no maximum will be used
     * @return a list of clusters containing the points
     */     public java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> cluster(final java.util.Collection<T> points, final int k, final int maxIterations) {         // create the initial clusters
        java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> clusters = org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.chooseInitialCenters(points, k, random);
        org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.assignPointsToClusters(clusters, points);

        // iterate through updating the centers until we're done
        final int max = (maxIterations < 0) ? java.lang.Integer.MAX_VALUE : maxIterations;
        for (int count = 0; count < max; count++) {
            boolean clusteringChanged = false;
            java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> newClusters = new java.util.ArrayList<org.apache.commons.math.stat.clustering.Cluster<T>>();
            for (final org.apache.commons.math.stat.clustering.Cluster<T> cluster : clusters) {
                final T newCenter = cluster.getCenter().centroidOf(cluster.getPoints());
                if (!(newCenter.equals(cluster.getCenter()))) {
                    clusteringChanged = true;
                }
                newClusters.add(new org.apache.commons.math.stat.clustering.Cluster<T>(newCenter));
            }
            if (!clusteringChanged) {
                return clusters;
            }
            org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.assignPointsToClusters(newClusters, points);
            clusters = newClusters;
        }
        return clusters;
    }

    /**
     * Adds the given points to the closest {@link Cluster}.
     *
     * @param <T>
     * 		type of the points to cluster
     * @param clusters
     * 		the {@link Cluster}s to add the points to
     * @param points
     * 		the points to add to the given {@link Cluster}s
     */     private static <T extends org.apache.commons.math.stat.clustering.Clusterable<T>> void assignPointsToClusters(final java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> clusters, final java.util.Collection<T> points) {         for (final T p : points) {
            org.apache.commons.math.stat.clustering.Cluster<T> cluster = org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.getNearestCluster(clusters, p);
            cluster.addPoint(p);
        }
    }

    /**
     * Use K-means++ to choose the initial centers.
     *
     * @param <T>
     * 		type of the points to cluster
     * @param points
     * 		the points to choose the initial centers from
     * @param k
     * 		the number of centers to choose
     * @param random
     * 		random generator to use
     * @return the initial centers
     */     private static <T extends org.apache.commons.math.stat.clustering.Clusterable<T>> java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> chooseInitialCenters(final java.util.Collection<T> points, final int k, final java.util.Random random) {         final java.util.List<T> pointSet = new java.util.ArrayList<T>(points);
        final java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> resultSet = new java.util.ArrayList<org.apache.commons.math.stat.clustering.Cluster<T>>();

        // Choose one center uniformly at random from among the data points.
        final T firstPoint = pointSet.remove(random.nextInt(pointSet.size()));
        resultSet.add(new org.apache.commons.math.stat.clustering.Cluster<T>(firstPoint));

        final double[] dx2 = new double[pointSet.size()];
        while ((resultSet.size()) < k) {
            // For each data point x, compute D(x), the distance between x and
            // the nearest center that has already been chosen.
            int sum = 0;
            for (int i = 0; i < (pointSet.size()); i++) {
                final T p = pointSet.get(i);
                final org.apache.commons.math.stat.clustering.Cluster<T> nearest = org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.getNearestCluster(resultSet, p);
                final double d = p.distanceFrom(nearest.getCenter());
                sum += d * d;
                dx2[i] = sum;
            }

            // Add one new data point as a center. Each point x is chosen with
            // probability proportional to D(x)2
            final double r = (random.nextDouble()) * sum;
            for (int i = 0; i < (dx2.length); i++) {
                if ((dx2[i]) >= r) {
                    final T p = pointSet.remove(i);
                    resultSet.add(new org.apache.commons.math.stat.clustering.Cluster<T>(p));
                    break;
                }
            }
        } 

        return resultSet;

    }

    /**
     * Returns the nearest {@link Cluster} to the given point
     *
     * @param <T>
     * 		type of the points to cluster
     * @param clusters
     * 		the {@link Cluster}s to search
     * @param point
     * 		the point to find the nearest {@link Cluster} for
     * @return the nearest {@link Cluster} to the given point
     */     private static <T extends org.apache.commons.math.stat.clustering.Clusterable<T>> org.apache.commons.math.stat.clustering.Cluster<T> getNearestCluster(final java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> clusters, final T point) {         double minDistance = java.lang.Double.MAX_VALUE;
        org.apache.commons.math.stat.clustering.Cluster<T> minCluster = null;
        for (final org.apache.commons.math.stat.clustering.Cluster<T> c : clusters) {
            final double distance = point.distanceFrom(c.getCenter());
            if (distance < minDistance) {
                minDistance = distance;
                minCluster = c;
            }
        }
        return minCluster;
    }

}