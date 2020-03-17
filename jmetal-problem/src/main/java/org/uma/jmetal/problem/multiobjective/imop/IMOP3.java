package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午3:53
 * @Descriptiom: #
 */
public class IMOP3 extends IMOP1 {

    public IMOP3(double a1, int K) {
        super(a1, K);
        setName("IMOP3");
    }

    public IMOP3() {
        this(0.05, 5);
    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double y1 = y(solution, a1);
        double g = g(solution);

        solution.setObjective(0, g + (1.0 + Math.cos(y1 * Math.PI * 10) / 5 - y1));
        solution.setObjective(1, g + y1);
    }
}
