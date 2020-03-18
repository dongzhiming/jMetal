package org.uma.jmetal.lab.experiment.util;

import org.uma.jmetal.lab.experiment.Experiment;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 18-7-13 22:56
 * @Descriptiom: #
 */
public class PlotAttainments {
    public static void fire(String filePath, int numberOfRuns) {

    }

    public static void fireFromExperiment(Experiment<DoubleSolution, List<DoubleSolution>> experiment, int value) throws IOException {
        String outputDirectoryName = experiment.getExperimentBaseDirectory() + "/attainment_surfaces";
        prepare(outputDirectoryName);

        DefaultFileOutputContext context = new DefaultFileOutputContext(outputDirectoryName + "/run");
        context.setSeparator(" ");
        BufferedWriter bufferedWriter1 = context.getFileWriter();

        for (int i = 0; i < experiment.getAlgorithmList().size() / experiment.getIndependentRuns(); ++i) {
            ExperimentAlgorithm algorithm = experiment.getAlgorithmList().get(i);

            prepare(outputDirectoryName + "/" + algorithm.getAlgorithmTag());

            bufferedWriter1.write("plot-att " + "../data/" + algorithm.getAlgorithmTag() + "/" + algorithm.getProblemTag() + "/TotalFUN " +
                    "-k " + hello(experiment.getProblemList().get(i % experiment.getProblemList().size()).getProblem()) + "-r 60 -a " + value + " > " +
                    algorithm.getAlgorithmTag() + "/" + algorithm.getProblemTag() + value);

            bufferedWriter1.newLine();
        }

        bufferedWriter1.close();


        // plot
        context = new DefaultFileOutputContext(outputDirectoryName + "/" + "experiment.plt");
        context.setSeparator(" ");
        BufferedWriter bufferedWriter2 = context.getFileWriter();

        // head
        bufferedWriter2.write("set term post color 18");
        bufferedWriter2.newLine();
        bufferedWriter2.write("set view 45,50");
        bufferedWriter2.newLine();
        bufferedWriter2.write("set xlabel \"f1\"");
        bufferedWriter2.newLine();
        bufferedWriter2.write("set ylabel \"f2\"");
        bufferedWriter2.newLine();
        bufferedWriter2.write("set zlabel \"f3\"");
        bufferedWriter2.newLine();
        bufferedWriter2.write("set output \"experiment.ps\"");
        bufferedWriter2.newLine();
        bufferedWriter2.newLine();

        for (int i = 0; i < experiment.getProblemList().size(); ++i) {
            String problemName = experiment.getProblemList().get(i).getProblem().getName();
            bufferedWriter2.write("set title \"" + problemName + "\"");
            bufferedWriter2.newLine();

            if (experiment.getProblemList().get(i).getProblem().getNumberOfObjectives() > 2) {
                bufferedWriter2.write("s");
            }
            bufferedWriter2.write("plot ");

            for (int j = 0; j < experiment.getAlgorithmList().size() / experiment.getIndependentRuns() / experiment.getProblemList().size(); j++) {
                String algorithmName = experiment.getAlgorithmList().get(j * experiment.getProblemList().size()).getAlgorithmTag();

                bufferedWriter2.write("\"" + algorithmName + "/" +
                        problemName + value + "\" w p ls " + (j + 1) + " t \"" + algorithmName + "\"");

                if (j < experiment.getAlgorithmList().size() / experiment.getIndependentRuns() / experiment.getProblemList().size() - 1) {
                    bufferedWriter2.write(", ");
                }
            }


            bufferedWriter2.newLine();
            bufferedWriter2.newLine();
        }

        bufferedWriter2.close();

    }

    private static String hello(Problem<DoubleSolution> problem) {
        String ret = String.valueOf(problem.getNumberOfObjectives()) + " -mm ";
        for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
            ret += "-1 ";
        }

        return ret;
    }

    private static void prepare(String outputDirectoryName) {
        File experimentDirectory = new File(outputDirectoryName);
        if (!(experimentDirectory.exists() && experimentDirectory.isDirectory())) {
            if (experimentDirectory.exists()) {
                experimentDirectory.delete();
            }

            boolean result;
            result = new File(outputDirectoryName).mkdirs();
            if (!result) {
                JMetalLogger.logger.info("Error creating experiment directory: " + outputDirectoryName);
            }
        }
    }
}
