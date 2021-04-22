package org.uma.jmetal.problem.multiobjective.vnt;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @Authror: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 下午2:56 2019/11/24
 * @Description:
 */
public class VNT3 extends AbstractDoubleProblem {
    public VNT3() {
        setNumberOfVariables(2);
        setNumberOfObjectives(3);
        setNumberOfConstraints(0);
        setName("VNT3");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-3.0);
            upperLimit.add(3.0);
        }

        setVariableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        List<Double> x = solution.variables();

        double tmp = x.get(0) * x.get(0) + x.get(1) * x.get(1);

        solution.objectives()[0] = 0.5 * tmp + Math.sin(tmp);
        solution.objectives()[1] = (3 * x.get(0) - 2 * x.get(1) + 4) * (3 * x.get(0) - 2 * x.get(1) + 4) / 8 + (x.get(0) - x.get(1) + 1) * (x.get(0) - x.get(1) + 1) / 27 + 15;
        solution.objectives()[2] = 1 / (tmp + 1) - 1.1 * Math.exp(-tmp);

        return solution;
    }
}
