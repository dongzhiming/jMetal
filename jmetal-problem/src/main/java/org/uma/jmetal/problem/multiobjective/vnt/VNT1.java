package org.uma.jmetal.problem.multiobjective.vnt;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @Authror: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 下午2:39 2019/11/24
 * @Description:
 */
public class VNT1 extends AbstractDoubleProblem {
    public VNT1() {
        setNumberOfVariables(2);
        setNumberOfObjectives(3);
        setNumberOfConstraints(0);
        setName("VNT1");

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-2.0);
            upperLimit.add(2.0);
        }

        setVariableBounds(lowerLimit, upperLimit);
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        List<Double> x = solution.variables();

        solution.objectives()[0] = x.get(0) * x.get(0) + (x.get(1) - 1) * (x.get(1) - 1);
        solution.objectives()[1] = x.get(0) * x.get(0) + (x.get(1) + 1) * (x.get(1) + 1) + 1;
        solution.objectives()[2] = (x.get(0) - 1) * (x.get(0) - 1) + x.get(1) * x.get(1) + 2;

        return solution;
    }
}
