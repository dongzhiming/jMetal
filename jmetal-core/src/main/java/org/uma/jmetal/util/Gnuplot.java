package org.uma.jmetal.util;

import com.panayotis.gnuplot.JavaPlot;

import static com.panayotis.gnuplot.JavaPlot.Key.OFF;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 上午9:42 2020/3/18
 * @Descriptiom:
 */
public class Gnuplot {
    public static void plot(double[] data, String name) {
        double[][] convert = new double[data.length][2];
        for (int i = 0; i < data.length; i++) {
            convert[i][0] = i;
            convert[i][1] = data[i];
        }

        plot(convert, name.replace("_", "\\_"));
    }

    public static void plot(double[][] data, String name) {
        if (data[0].length > 3) {
            return;
        }

        JavaPlot p = new JavaPlot();
        if (data[0].length == 3) {
            p = new JavaPlot(true);
        }

        p.setKey(OFF);
        p.setTitle(name.replace("_", "\\_"));

        p.addPlot(data);
        p.plot();
    }

    public static void plots(double[][] data, String name) {
        JavaPlot p = new JavaPlot();
        if (data[0].length == 3) {
            p = new JavaPlot(true);
        }

        p.setKey(OFF);
        p.setTitle(name.replace("_", "\\_"));

        for (int i = 0; i < data[0].length; i++) {
            double[][] x = new double[data.length][2];
            for (int j = 0; j < data.length; j++) {
                x[j][0] = j;
                x[j][1] = data[j][i];
            }

            p.addPlot(x);
        }

        p.plot();
    }
}
