package ticTacToe.Genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class FitnessEvaluator {

    private BiFunction<Genome,List<Genome>,Double> method;
    private List<Genome> championsList;

    public FitnessEvaluator(BiFunction<Genome, List<Genome>, Double> evaluationMethod) {
        this.championsList = new ArrayList<>();
        method = evaluationMethod;
    }

    public void useChampions(List<Genome> champions) {
        championsList = champions;
    }

    public Double calculateFitness(Genome genome) {
        return method.apply(genome, championsList);
    }


}