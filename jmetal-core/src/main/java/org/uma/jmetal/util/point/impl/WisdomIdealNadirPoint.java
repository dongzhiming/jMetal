package org.uma.jmetal.util.point.impl;

import org.uma.jmetal.solution.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhi-Ming Dong <dongzm@stumail.neu.edu.com>
 * @Date: 下午8:00 2020/4/7
 * @Descriptiom:
 */
public class WisdomIdealNadirPoint {
    private IdealPoint idealPoint;
    private NadirPoint nadirPoint;
    private List<double[]> points;

    public WisdomIdealNadirPoint(int dimension) {
        idealPoint = new IdealPoint(dimension);
        nadirPoint = new NadirPoint(dimension);

        points = new ArrayList<>(dimension);
    }

    public boolean update(Solution<?> solution) {
        // Step 1
        if (points.size() == 0) {
            for (int i = 0; i < idealPoint.getDimension(); i++) {
                points.add(solution.objectives().clone());
            }

            idealPoint.update(solution.objectives());
            nadirPoint.update(solution.objectives());

            return true;
        }

        // Step 2
        boolean flag = false;
        for (int i = 0; i < idealPoint.getDimension(); i++) {
            if (solution.objectives()[i] < points.get(i)[i]) {
                points.set(i, solution.objectives().clone());
                idealPoint.setValue(i, solution.objectives()[i]);

                flag = true;
            }
        }

        // Step 3
        if (flag) {
            nadirPoint = new NadirPoint(idealPoint.getDimension());

            for (int i = 0; i < idealPoint.getDimension(); i++) {
                nadirPoint.update(points.get(i));
            }
        }

        return flag;
    }

    public IdealPoint getIdealPoint() {
        return idealPoint;
    }

    public NadirPoint getNadirPoint() {
        return nadirPoint;
    }

    @Override
    public String toString() {
        return "WisdomIdealNadirPoint{" +
                "idealPoint=" + idealPoint +
                ", nadirPoint=" + nadirPoint +
                '}';
    }
}
