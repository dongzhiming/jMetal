package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午3:34
 * @Descriptiom: #
 */
public class IMOP1 extends AbstractDoubleProblem {
    protected double a1;
    protected int K;

    public IMOP1(double a1, int K) {
        setNumberOfVariables(10);
        setNumberOfObjectives(2);
        setNumberOfConstraints(0);
        setName("IMOP1");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(0.0);
            upperLimit.add(1.0);
        }

        setVariableBounds(lowerLimit, upperLimit);

        this.a1 = a1;
        this.K = K;
    }

    public IMOP1() {
        this(0.05, 5);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double y1 = y(solution, a1);
        double g = g(solution);

        solution.objectives()[0] = g + Math.pow(Math.cos(y1 * Math.PI / 2), 8);
        solution.objectives()[1] = g + Math.pow(Math.sin(y1 * Math.PI / 2), 8);

        return solution;
    }

    protected double y(DoubleSolution solution, double a) {
        double y = 0;

        for (int i = 0; i < K; i++) {
            y += solution.variables().get(i);
        }

        return Math.pow(y / K, a);
    }

    protected double y(DoubleSolution solution, int begin, double a) {
        double y = 0;

        for (int i = begin; i < K; i += 2) {
            y += solution.variables().get(i);
        }

        return Math.pow(y / Math.ceil((K - begin) / 2.0), a);
    }

    protected double g(DoubleSolution solution) {
        double g = 0;

        for (int i = K; i < solution.variables().size(); i++) {
            g += Math.pow(solution.variables().get(i) - 0.5, 2);
        }

        return g;
    }
}
