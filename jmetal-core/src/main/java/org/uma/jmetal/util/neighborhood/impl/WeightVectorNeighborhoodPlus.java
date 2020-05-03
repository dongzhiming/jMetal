package org.uma.jmetal.util.neighborhood.impl;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.uma.jmetal.util.neighborhood.Neighborhood;

import java.util.ArrayList;
import java.util.List;

import static org.uma.jmetal.util.UniformPoint.generateWeightVector;

/**
 * @Author: Dong Zhiming <dongzm@stumail.neu.edu.com>
 * @Date: 上午10:32 2020/5/1
 * @Descriptiom:
 */
public class WeightVectorNeighborhoodPlus<S> implements Neighborhood<S> {
    private int numberOfWeightVectors;
    private int weightVectorSize;
    private int[][] neighborhood;
    private double[][] weightVector;
    private int neighborhoodSize;

    public WeightVectorNeighborhoodPlus(int numberOfWeightVectors, int weightVectorSize, int neighborhoodSize) {
        this.numberOfWeightVectors = numberOfWeightVectors;
        this.weightVectorSize = weightVectorSize;
        this.neighborhoodSize = neighborhoodSize;

        this.neighborhood = new int[numberOfWeightVectors][neighborhoodSize];
        this.weightVector = generateWeightVector(numberOfWeightVectors, weightVectorSize);

        initializeNeighborhood();
    }

    private void initializeNeighborhood() {
        EuclideanDistance euclideanDistance = new EuclideanDistance();
        double[] x = new double[numberOfWeightVectors];
        int[] idx = new int[numberOfWeightVectors];

        for (int i = 0; i < numberOfWeightVectors; i++) {
            // calculate the distances based on weight vectors
            for (int j = 0; j < numberOfWeightVectors; j++) {
                x[j] = euclideanDistance.compute(weightVector[i], weightVector[j]);
                idx[j] = j;
            }

            // find 'niche' nearest neighboring subproblems
            minFastSort(x, idx, numberOfWeightVectors, neighborhoodSize);

            System.arraycopy(idx, 0, neighborhood[i], 0, neighborhoodSize);
        }
    }

    private void minFastSort(double x[], int idx[], int n, int m) {
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < n; j++) {
                if (x[i] > x[j]) {
                    double temp = x[i];
                    x[i] = x[j];
                    x[j] = temp;
                    int id = idx[i];
                    idx[i] = idx[j];
                    idx[j] = id;
                }
            }
        }
    }

    @Override
    public List<S> getNeighbors(List<S> solutionList, int solutionIndex) {
        List<S> neighbourSolutions = new ArrayList<>();

        for (int neighborIndex : neighborhood[solutionIndex]) {
            neighbourSolutions.add(solutionList.get(neighborIndex));
        }
        return neighbourSolutions;
    }

    public int getNumberOfWeightVectors() {
        return numberOfWeightVectors;
    }

    public int getWeightVectorSize() {
        return weightVectorSize;
    }

    public int[][] getNeighborhood() {
        return neighborhood;
    }

    public double[][] getWeightVector() {
        return weightVector;
    }

    public int neighborhoodSize() {
        return neighborhoodSize;
    }
}
