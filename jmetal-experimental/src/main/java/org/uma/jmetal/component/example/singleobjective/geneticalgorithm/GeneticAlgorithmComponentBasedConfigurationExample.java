package org.uma.jmetal.component.example.singleobjective.geneticalgorithm;


import org.uma.jmetal.component.algorithm.ComponentBasedEvolutionaryAlgorithm;
import org.uma.jmetal.component.catalogue.evaluation.Evaluation;
import org.uma.jmetal.component.catalogue.evaluation.SequentialEvaluation;
import org.uma.jmetal.component.catalogue.initialsolutioncreation.InitialSolutionsCreation;
import org.uma.jmetal.component.catalogue.initialsolutioncreation.impl.RandomSolutionsCreation;
import org.uma.jmetal.component.catalogue.replacement.Replacement;
import org.uma.jmetal.component.catalogue.replacement.impl.MuPlusLambdaReplacement;
import org.uma.jmetal.component.catalogue.selection.MatingPoolSelection;
import org.uma.jmetal.component.catalogue.selection.impl.NaryTournamentMatingPoolSelection;
import org.uma.jmetal.component.catalogue.termination.Termination;
import org.uma.jmetal.component.catalogue.termination.impl.TerminationByEvaluations;
import org.uma.jmetal.component.catalogue.variation.impl.CrossoverAndMutationVariation;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.io.FileNotFoundException;
import java.util.List;


/**
 * Class to configure and run the a genetic algorithm using the {@link ComponentBasedEvolutionaryAlgorithm} class.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class GeneticAlgorithmComponentBasedConfigurationExample extends AbstractAlgorithmRunner {
  public static void main(String[] args) throws JMetalException, FileNotFoundException {
    Problem<DoubleSolution> problem;
    ComponentBasedEvolutionaryAlgorithm<DoubleSolution> algorithm;

    problem = new Sphere(20);

    int populationSize = 100;
    int offspringPopulationSize = 100;
    int maxNumberOfEvaluations = 25000;

    InitialSolutionsCreation<DoubleSolution> initialSolutionsCreation =
        new RandomSolutionsCreation<>(problem, populationSize);

    double crossoverProbability = 0.95;
    double crossoverDistributionIndex = 20.0;
    CrossoverOperator<DoubleSolution> crossover =
        new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    double mutationDistributionIndex = 20.0;
    MutationOperator<DoubleSolution> mutation =
        new PolynomialMutation(mutationProbability, mutationDistributionIndex);

    CrossoverAndMutationVariation<DoubleSolution> variation =
        new CrossoverAndMutationVariation<>(offspringPopulationSize, crossover, mutation);

    MatingPoolSelection<DoubleSolution> selection =
        new NaryTournamentMatingPoolSelection<>(
            2, variation.getMatingPoolSize(), new ObjectiveComparator<>(0));

    Termination termination = new TerminationByEvaluations(maxNumberOfEvaluations);

    Evaluation<DoubleSolution> evaluation = new SequentialEvaluation<>(problem);

    Replacement<DoubleSolution> replacement =
            new MuPlusLambdaReplacement<>(new ObjectiveComparator<>(0));

    algorithm =
        new ComponentBasedEvolutionaryAlgorithm<>(
            "Generational genetic algorithm",
            evaluation,
            initialSolutionsCreation,
            termination,
            selection,
            variation,
            replacement);

    algorithm.run();

    List<DoubleSolution> population = algorithm.getResult();
    JMetalLogger.logger.info("Total execution time : " + algorithm.getTotalComputingTime() + "ms");
    JMetalLogger.logger.info("Number of evaluations: " + algorithm.getEvaluations());

    new SolutionListOutput(population)
        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.csv", ","))
        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.csv", ","))
        .print();

    JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
    JMetalLogger.logger.info("Objectives values have been written to file FUN.csv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.csv");

    JMetalLogger.logger.info("Best found solution: " + population.get(0).getObjective(0));
  }
}
