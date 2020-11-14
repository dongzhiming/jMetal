package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午3:46
 * @Descriptiom: #
 */
public class IMOP2 extends IMOP1 {
    public IMOP2(double a1, int K) {
        super(a1, K);
        setName("IMOP2");
    }

    public IMOP2() {
        this(0.05, 5);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double y1 = y(solution, a1);
        double g = g(solution);

        solution.setObjective(0, g + Math.pow(Math.cos(y1 * Math.PI / 2), 0.5));
        solution.setObjective(1, g + Math.pow(Math.sin(y1 * Math.PI / 2), 0.5));

        return solution;
    }
}
