package org.uma.jmetal.problem.multiobjective.vnt;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @Authror: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 下午2:51 2019/11/24
 * @Description:
 */
public class VNT2 extends AbstractDoubleProblem {
    public VNT2() {
        setNumberOfVariables(2);
        setNumberOfObjectives(3);
        setNumberOfConstraints(0);
        setName("VNT2");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-4.0);
            upperLimit.add(4.0);
        }

        setVariableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        List<Double> x = solution.getVariables();

        solution.setObjective(0, (x.get(0) - 2) * (x.get(0) - 2) / 2 + (x.get(1) + 1) * (x.get(1) + 1) / 13 + 3);
        solution.setObjective(1, (x.get(0) + x.get(1) - 3) * (x.get(0) + x.get(1) - 3) / 36 + (-x.get(0) + x.get(1) + 2) * (-x.get(0) + x.get(1) + 2) / 8 - 17);
        solution.setObjective(2, (x.get(0) + 2 * x.get(1) - 1) * (x.get(0) + 2 * x.get(1) - 1) / 175 + (2 * x.get(1) - x.get(0)) * (2 * x.get(1) - x.get(0)) / 17 - 13);

        return solution;
    }
}
