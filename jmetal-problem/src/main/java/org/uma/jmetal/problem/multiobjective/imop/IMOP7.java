package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午4:24
 * @Descriptiom: #
 */
public class IMOP7 extends IMOP5 {

    public IMOP7(double a1, double a2, int K) {
        super(a1, a2, K);
        setName("IMOP7");
    }

    public IMOP7() {
        this(0.05, 10, 5);
    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double y1 = y(solution, 0, a1);
        double y2 = y(solution, 1, a2);
        double g = g(solution);

        double f0 = (1 + g) * Math.cos(y1 * Math.PI / 2) * Math.cos(y2 * Math.PI / 2);
        double f1 = (1 + g) * Math.cos(y1 * Math.PI / 2) * Math.sin(y2 * Math.PI / 2);
        double f2 = (1 + g) * Math.sin(y1 * Math.PI / 2);

        double r = Math.min(
                Math.min(
                        Math.abs(f0 - f1),
                        Math.abs(f1 - f2)),
                Math.abs(f2 - f0));

        r = 10 * Math.max(0, r - 0.1);

        solution.setObjective(0, f0 + r);
        solution.setObjective(1, f1 + r);
        solution.setObjective(2, f2 + r);
    }
}
