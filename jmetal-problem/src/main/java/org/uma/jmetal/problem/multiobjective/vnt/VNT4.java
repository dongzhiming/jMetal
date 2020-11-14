package org.uma.jmetal.problem.multiobjective.vnt;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @Authror: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 下午2:57 2019/11/24
 * @Description:
 */
public class VNT4 extends AbstractDoubleProblem {
    public VNT4() {
        setNumberOfVariables(2);
        setNumberOfObjectives(3);
        setNumberOfConstraints(3);
        setName("VNT4");

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
        solution.setObjective(1, (x.get(0) + x.get(1) - 3) * (x.get(0) + x.get(1) - 3) / 175 + (2 * x.get(1) - x.get(0)) * (2 * x.get(1) - x.get(0)) / 17 - 13);
        solution.setObjective(2, (3 * x.get(0) - 2 * x.get(1) + 4) * (3 * x.get(0) - 2 * x.get(1) + 4) / 8 + (x.get(0) - x.get(1) + 1) * (x.get(0) - x.get(1) + 1) / 27 + 15);

        evaluateConstraints(solution);

        return solution;
    }

    private void evaluateConstraints(DoubleSolution solution) {
        List<Double> x = solution.getVariables();

        double[] constraint = new double[this.getNumberOfConstraints()];

        constraint[0] = -x.get(1) - 4 * x.get(0) + 4;
        constraint[1] = 1 + x.get(0);
        constraint[2] = -x.get(0) + 2 + x.get(1);

        for (int i = 0; i < getNumberOfConstraints(); i++) {
            solution.setConstraint(i, constraint[i]);
        }
    }
}
