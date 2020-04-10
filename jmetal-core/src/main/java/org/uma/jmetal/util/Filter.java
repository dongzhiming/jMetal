package org.uma.jmetal.util;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.distance.Distance;
import org.uma.jmetal.util.distance.impl.EuclideanDistanceBetweenSolutionsInObjectiveSpace;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.util.distance.EuclideanDistance;
import org.uma.jmetal.util.point.util.distance.PointDistance;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 2019/8/23 下午9:16
 * @Descriptiom: #
 */
public class Filter {
    public static double[][] filter(double[][] referencePoints, int expected) {
        if (referencePoints.length < expected) {
            JMetalLogger.logger.warning("referencePoints.length < expected.");
            return referencePoints;
        }

        int dim = referencePoints[0].length;
        double[][] rps = new double[expected][dim];

        int total = referencePoints.length;
        double[] dis2set = new double[total];
        boolean[] selected = new boolean[total];
        for (int i = 0; i < total; i++) {
            dis2set[i] = Double.MAX_VALUE;
            selected[i] = false;
        }

        // distance matrix
        PointDistance distance = new EuclideanDistance();
        double[][] disV = new double[total][total];
        for (int i = 0; i < total; i++) {
            disV[i][i] = 0.0;
            for (int j = i + 1; j < total; j++) {
                disV[i][j] = distance.compute(new ArrayPoint(referencePoints[i]), new ArrayPoint(referencePoints[j]));
                disV[j][i] = disV[i][j];
            }
        }

        // select extreme points
        for (int k = 0; k < (expected > dim ? dim : expected); k++) {
            int index = 0;
            double fmax = -Double.MAX_VALUE;
            for (int i = 0; i < total; i++) {
                if (!selected[i] && referencePoints[i][k] > fmax) {
                    index = i;
                    fmax = referencePoints[i][k];
                }
            }

            rps[k] = referencePoints[index];

            selected[index] = true;

            for (int i = 0; i < total; i++) {
                if (!selected[i] && dis2set[i] > disV[i][index]) {
                    dis2set[i] = disV[i][index];
                }
            }
        }

        if (rps.length < dim) {
            JMetalLogger.logger.warning("rps.length < dim");
        }

        // select all the other points
        for (int k = dim; k < expected; k++) {
            int index = 0;
            double fmax = -Double.MAX_VALUE;

            for (int i = 0; i < total; i++) {
                if (!selected[i] && dis2set[i] > fmax) {
                    fmax = dis2set[i];
                    index = i;
                }
            }

            rps[k] = referencePoints[index];

            selected[index] = true;

            for (int i = 0; i < total; i++) {
                if (!selected[i] && dis2set[i] > disV[i][index]) {
                    dis2set[i] = disV[i][index];
                }
            }
        }

        return rps;
    }

    public static <S extends Solution<?>> List<S> filter(List<S> referencePoints, int expected) {
        if (referencePoints.size() < expected) {
            JMetalLogger.logger.warning("referencePoints.size() < expected.");
            return referencePoints;
        }

        int dim = referencePoints.get(0).getNumberOfObjectives();
        List<S> rps = new ArrayList<>(expected);

        int total = referencePoints.size();
        double[] dis2set = new double[total];
        boolean[] selected = new boolean[total];
        for (int i = 0; i < total; i++) {
            dis2set[i] = Double.MAX_VALUE;
            selected[i] = false;
        }

        // distance matrix
        Distance distance = new EuclideanDistanceBetweenSolutionsInObjectiveSpace();
        double[][] disV = new double[total][total];
        for (int i = 0; i < total; i++) {
            disV[i][i] = 0.0;
            for (int j = i + 1; j < total; j++) {
                disV[i][j] = distance.getDistance(referencePoints.get(i), referencePoints.get(j));
                disV[j][i] = disV[i][j];
            }
        }

        // select extreme points
        for (int k = 0; k < (expected > dim ? dim : expected); k++) {
            int index = 0;
            double fmax = -Double.MAX_VALUE;
            for (int i = 0; i < total; i++) {
                if (!selected[i] && referencePoints.get(i).getObjective(k) > fmax) {
                    index = i;
                    fmax = referencePoints.get(i).getObjective(k);
                }
            }

            rps.add((S) referencePoints.get(index).copy());

            selected[index] = true;

            for (int i = 0; i < total; i++) {
                if (!selected[i] && dis2set[i] > disV[i][index]) {
                    dis2set[i] = disV[i][index];
                }
            }
        }

        if (rps.size() < dim) {
            JMetalLogger.logger.warning("rps.size() < dim");
        }

        // select all the other points
        for (int k = dim; k < expected; k++) {
            int index = 0;
            double fmax = -Double.MAX_VALUE;

            for (int i = 0; i < total; i++) {
                if (!selected[i] && dis2set[i] > fmax) {
                    fmax = dis2set[i];
                    index = i;
                }
            }

            rps.add((S) referencePoints.get(index).copy());

            selected[index] = true;

            for (int i = 0; i < total; i++) {
                if (!selected[i] && dis2set[i] > disV[i][index]) {
                    dis2set[i] = disV[i][index];
                }
            }
        }

        return rps;
    }
}
