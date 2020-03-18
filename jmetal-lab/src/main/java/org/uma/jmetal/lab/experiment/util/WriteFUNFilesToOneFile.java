package org.uma.jmetal.lab.experiment.util;

import org.uma.jmetal.lab.experiment.Experiment;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.impl.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Zhi-Ming Dong, dongzm@stumail.neu.edu.com
 * @Date: created in 18-7-13 16:25
 * @Descriptiom: #
 */
public class WriteFUNFilesToOneFile {

    private static void fire(String filePath, int numberOfRuns, FrontNormalizer frontNormalizer, boolean norm) throws IOException {
        DefaultFileOutputContext context = new DefaultFileOutputContext(filePath + "/TotalFUN");
        context.setSeparator(" ");
        BufferedWriter bufferedWriter = context.getFileWriter();

        for (int k = 0; k < numberOfRuns; k++) {
            Front front = new ArrayFront(filePath + "/FUN" + Integer.toString(k) + ".tsv");
            Front normalizedFront;
            if (norm) {
                normalizedFront = frontNormalizer.normalize(front);
            } else {
                normalizedFront = front;
            }

            for (int i = 0; i < normalizedFront.getNumberOfPoints(); i++) {
                for (int j = 0; j < normalizedFront.getPointDimensions(); j++) {
                    bufferedWriter.write(normalizedFront.getPoint(i).getValues()[j] + context.getSeparator());
                }
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        bufferedWriter.close();
    }

    public static void fireFromExperiment(Experiment<DoubleSolution, List<DoubleSolution>> experiment, boolean norm) throws IOException {
        for (int i = 0; i < experiment.getAlgorithmList().size() / experiment.getIndependentRuns(); ++i) {
            ExperimentAlgorithm algorithm = experiment.getAlgorithmList().get(i);
            String algorithmDirectory = experiment.getExperimentBaseDirectory() + "/data/" + algorithm.getAlgorithmTag();
            String problemDirectory = algorithmDirectory + "/" + algorithm.getProblemTag();


            String referenceFrontDirectory = experiment.getReferenceFrontDirectory();
//            String referenceFrontName = referenceFrontDirectory + "/" + algorithm.getReferenceParetoFront();
//            Front referenceFront = new ArrayFront(referenceFrontName);
            Front referenceFront = new ArrayFront("这个地方存在bug");
            JMetalLogger.logger.warning("这个地方存在bug");

            FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront);
            frontNormalizer.normalize(referenceFront);

            fire(problemDirectory, experiment.getIndependentRuns(), frontNormalizer, norm);
        }
    }
}
