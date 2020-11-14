package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午4:09
 * @Descriptiom: #
 */
public class IMOP5 extends IMOP1 {
    protected double a2;

    public IMOP5(double a1, double a2, int K) {
        super(a1, K);
        setName("IMOP5");
        setNumberOfObjectives(3);

        this.a2 = a2;
    }

    public IMOP5() {
        this(0.05, 10, 5);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double y1 = y(solution, 0, a1);
        double y2 = y(solution, 1, a2);
        double g = g(solution);

        double f0 = 0.4 * Math.cos(Math.PI * Math.ceil(y1 * 8) / 4) + 0.1 * y2 * Math.cos(16 * Math.PI * y1);
        double f1 = 0.4 * Math.sin(Math.PI * Math.ceil(y1 * 8) / 4) + 0.1 * y2 * Math.sin(16 * Math.PI * y1);
        double f2 = 0.5 - f0 - f1;

        solution.setObjective(0, f0 + g);
        solution.setObjective(1, f1 + g);
        solution.setObjective(2, f2 + g);

        return solution;
    }
}
