package org.uma.jmetal.component.catalogue.replacement.impl;

import org.uma.jmetal.component.catalogue.replacement.Replacement;
import org.uma.jmetal.component.catalogue.selection.impl.PopulationAndNeighborhoodMatingPoolSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.aggregativefunction.AggregativeFunction;
import org.uma.jmetal.util.neighborhood.Neighborhood;
import org.uma.jmetal.util.neighborhood.impl.WeightVectorNeighborhood;
import org.uma.jmetal.util.sequencegenerator.SequenceGenerator;
import org.uma.jmetal.util.sequencegenerator.impl.IntegerPermutationGenerator;

import java.util.Comparator;
import java.util.List;

/**
 * Given an offspring population composed of a single solution, this solution is compared against a particular solution
 * of the population given by a {@link SequenceGenerator} object. If this last solution is replaced by the former if
 * it worse according to a {@link Comparator}.
 *
 * One the replacement is carried out, the next sequence number is generated.
 *
 * @param <S>
 */
public class SingleSolutionReplacement<S extends Solution<?>> implements Replacement<S> {
  private final SequenceGenerator<Integer> sequenceGenerator;
  private final Comparator<S> comparator ;

  public SingleSolutionReplacement(SequenceGenerator<Integer> sequenceGenerator, Comparator<S>  comparator) {
    this.sequenceGenerator = sequenceGenerator;
    this.comparator = comparator ;
  }

  @Override
  public List<S> replace(
      List<S> population, List<S> offspringPopulation) {
    S newSolution = offspringPopulation.get(0);

    if (comparator.compare(population.get(sequenceGenerator.getValue()), newSolution) > 0) {
      population.set(sequenceGenerator.getValue(), newSolution) ;
    }

    sequenceGenerator.generateNext();
    
    return population;
  }
}
