package ticTacToe.GeneticTests;

import org.junit.Test;
import ticTacToe.Genetic.EvaluationMethods;
import ticTacToe.Genetic.Evolver;
import ticTacToe.Genetic.FitnessEvaluator;
import ticTacToe.Genetic.Genome;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class EvolverTest {
    @Test
    public void aNewEvolverHasAPopulationOfTheSpecifiedSize(){
        assertThat(new Evolver(50, new FitnessEvaluator(new EvaluationMethods()::perfectOpponent)).currentPopulation().size(), is(50));
    }


    @Test
    public void anEvolverCanEvolveAPopulation(){
        Evolver evolver = new Evolver(50, new FitnessEvaluator(new EvaluationMethods()::manyOpponents));
        Genome genome = evolver.evolvePopulationFor(50, 20, 30, 0.05, 50);
        assertThat(genome.values(), is(1));
    }
}
