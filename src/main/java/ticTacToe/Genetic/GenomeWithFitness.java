package ticTacToe.Genetic;

public class GenomeWithFitness {
    private final Genome genome;

    private final Double fitness;

    public GenomeWithFitness(Genome aGenome, Double aFitnessValue) {
        genome = aGenome;
        fitness = aFitnessValue;
    }

    public int orderByFitness(GenomeWithFitness gwf){
        return fitness.compareTo(gwf.fitness())*-1;
    }

    public Genome genome() {
        return genome;
    }

    public Double fitness() {
        return fitness;
    }
}
