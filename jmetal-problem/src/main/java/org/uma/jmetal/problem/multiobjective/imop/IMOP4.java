package org.uma.jmetal.problem.multiobjective.imop;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 19-4-25 下午4:05
 * @Descriptiom: #
 */
public class IMOP4 extends IMOP1 {

    public IMOP4(double a1, int K) {
        super(a1, K);
        setName("IMOP4");
        setNumberOfObjectives(3);
    }

    public IMOP4() {
        this(0.05, 5);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double y1 = y(solution, a1);
        double g = g(solution);

        solution.setObjective(0, (1.0 + g) * y1);
        solution.setObjective(1, (1.0 + g) * (y1 + Math.sin(10 * Math.PI * y1) / 10));
        solution.setObjective(2, (1.0 + g) * (1.0 - y1));

        return solution;
    }
}
