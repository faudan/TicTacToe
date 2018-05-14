package ticTacToe.Genetic;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Genetic.Genes.*;
import ticTacToe.TicTacToe;

import java.util.ArrayList;
import java.util.List;

public class Genome {

    private List<Gene> genes;
    private List<Double> alleles;

    public Genome() {
        genes = new ArrayList<>();
        alleles = new ArrayList<>();
        initializeGenes();
        randomizeAlleles();
    }

    private Genome(List<Double> values) {
        genes = new ArrayList<>();
        initializeGenes();
        alleles = values;
    }

    private void randomizeAlleles() {
        genes.forEach(gene ->
                alleles.add(Math.random() * 4)
        );
    }

    private void initializeGenes() {
        genes.add(new winGameGene());
        genes.add(new avoidLoseGene());
        genes.add(new setUpForkGene());
        genes.add(new blockOppForkGene());
        genes.add(new playCenterGene());
        genes.add(new playOppositeCornerGene());
        genes.add(new freeCornerGene());
        genes.add(new freeSideGene());
    }

    public Double geneticEvaluationOf(TicTacToe game, GameMarkings symbol, GameMove move) {
        Double totalValue = 0.0;
        for (int i = 0; i < genes.size(); i++) {
            Integer evaluatedGene = genes.get(i).checkForMove(game, symbol, move);
            Double allele = alleles.get(i);
            totalValue += evaluatedGene * allele;
        }
        return totalValue;
    }

    public static Genome with(List<Double> alleles) {
        return new Genome(alleles);
    }

    public List<Gene> genes() {
        return genes;
    }

    public List<Double> values() {
        return alleles;
    }
}
