package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午4:36
 * @Descriptiom: #
 */
public class IMOP8 extends IMOP5 {

    public IMOP8(double a1, double a2, int K) {
        super(a1, a2, K);
        setName("IMOP8");
    }

    public IMOP8() {
        this(0.05, 10, 5);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double y1 = y(solution, 0, a1);
        double y2 = y(solution, 1, a2);
        double g = g(solution);

        solution.objectives()[0] = y1;
        solution.objectives()[1] = y2;
        solution.objectives()[2] = (1 + g) * (3 - y1 / (1 + g) * (1 + Math.sin(19 * Math.PI * y1)) - y2 / (1 + g) * (1 + Math.sin(19 * Math.PI * y2)));

        return solution;
    }
}
