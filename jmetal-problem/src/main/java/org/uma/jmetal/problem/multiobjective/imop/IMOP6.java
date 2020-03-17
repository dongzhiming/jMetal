package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午4:18
 * @Descriptiom: #
 */
public class IMOP6 extends IMOP5 {

    public IMOP6(double a1, double a2, int K) {
        super(a1, a2, K);
        setName("IMOP6");
    }

    public IMOP6() {
        this(0.05, 10, 5);
    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double y1 = y(solution, 0, a1);
        double y2 = y(solution, 1, a2);
        double g = g(solution);
        double r = Math.max(0, Math.min(Math.pow(Math.sin(3 * Math.PI * y1), 2), Math.pow(Math.sin(3 * Math.PI * y2), 2)) - 0.5);

        solution.setObjective(0, (1 + g) * y1 + Math.ceil(r));
        solution.setObjective(1, (1 + g) * y2 + Math.ceil(r));
        solution.setObjective(2, (0.5 + g) * (2 - y1 - y2) + Math.ceil(r));
    }
}
