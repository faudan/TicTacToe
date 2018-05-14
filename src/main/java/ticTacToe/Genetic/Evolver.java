package ticTacToe.Genetic;


import java.util.*;
import java.util.stream.Collectors;

public class Evolver {

    private final Set<Genome> champions;
    private final FitnessEvaluator fitnessCalculator;
    private Integer POPULATION_SIZE;

    public List<Genome> currentPopulation() {
        return currentPopulation;
    }

    private List<Genome> currentPopulation;

    public Evolver(Integer populationSize, FitnessEvaluator fitnessEvaluator) {
        POPULATION_SIZE = populationSize;
        currentPopulation = new ArrayList<>();
        champions = new HashSet<>();
        addNewGenomesToPool(POPULATION_SIZE, currentPopulation);
        fitnessCalculator = fitnessEvaluator;
    }

    private void addNewGenomesToPool(Integer amountToAdd, List<Genome> pool) {
        for (int population = 0; population < amountToAdd; population++) {
            pool.add(new Genome());
        }
    }

    public Genome evolvePopulationFor(Integer generations, Integer survivorsPercentage, Integer descendantsPercentage, Double mutationProbability, int extinctionGen){

        assertUsableParameters(generations, survivorsPercentage, descendantsPercentage, mutationProbability);
        Integer amountOfSurvivors = amountOfSubjects(survivorsPercentage);
        Integer amountOfDescendants = amountOfSubjects(descendantsPercentage);
        assertSurvivorsPlusDescendantsLessOrEqualTotalPopulation(amountOfSurvivors,amountOfDescendants);
        Integer amountOfNeededNewComers = POPULATION_SIZE - amountOfDescendants - amountOfSurvivors;

        for (int generationsPassed = 0; generationsPassed < generations; generationsPassed++) {
            assertCompletePopulation(currentPopulation.size());
            ReplaceAllPopulationOnMassExtinction(extinctionGen, generationsPassed);
            List<Genome> fittest = selectFittest(amountOfSurvivors);
            List<Genome> descendants = recombine(fittest, amountOfDescendants, mutationProbability);
            List<Genome> newOnes = newComers(amountOfNeededNewComers);
            currentPopulation = buildNewGeneration(fittest, descendants, newOnes);
        }
        return theBest();

    }


    private Genome theBest() {
        return selectFittest(1).get(0);
    }

    private int amountOfSubjects(Integer survivorsPercentage) {
        return Math.round(POPULATION_SIZE * survivorsPercentage / 100);
    }

    private void ReplaceAllPopulationOnMassExtinction(int extinctionGen, int generationsPassed) {
        if(generationsPassed % extinctionGen == 0 && generationsPassed != 0){
            currentPopulation= newComers(POPULATION_SIZE);
        }
    }

    private List<Genome> buildNewGeneration(List<Genome> fittest, List<Genome> descendants, List<Genome> newOnes) {
        List<Genome> newGeneration = new ArrayList<>();
        newGeneration.addAll(fittest);
        newGeneration.addAll(descendants);
        newGeneration.addAll(newOnes);
        return newGeneration;
    }

    private List<Genome> recombine(List<Genome> fittest, Integer amountOfDescendants, Double mutationProbability) {
        List<Genome> children = new ArrayList<>();
        for (int descendants = 0; descendants < amountOfDescendants; descendants++) {
            children.add(crossover(fittest,mutationProbability));
        }
        return children;
    }

    private List<Genome> selectFittest(Integer amountOfSurvivors) {
        fitnessCalculator.useChampions(new ArrayList(champions));
        return getBest(amountOfSurvivors, currentPopulation.stream()
                .map(genome -> new GenomeWithFitness(genome, fitnessCalculator.calculateFitness(genome)))
                .collect(Collectors.toList()));
    }

    private List<Genome> getBest(Integer amountOfSurvivors, List<GenomeWithFitness> fittest) {
        fittest.sort(GenomeWithFitness::orderByFitness);
        champions.add(fittest.get(0).genome());
        return fittest.subList(0, amountOfSurvivors).stream().map(GenomeWithFitness::genome).collect(Collectors.toList());
    }

    private Genome crossover(List<Genome> fittest, Double mutationProbability) {

        List<Double> fit1 = randomGenomeFrom(fittest).values();
        List<Double> fit2 = randomGenomeFrom(fittest).values();
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < fit1.size() ; i++) {
            Double geneValue = 0.0;
            double random1 = Math.round(Math.random()*11*17*13);
            if(random1 % 2 == 0){
                geneValue = fit1.get(i);
            }else{
                geneValue = fit2.get(i);
            }
            double randomMutation = Math.random();
            if(mutationProbability >= randomMutation){
                geneValue = (Math.random() * 4);
            }
            values.add(geneValue);
        }
        return Genome.with(values);
    }



    private Genome randomGenomeFrom(List<Genome> fittest) {
        Random rand = new Random();
        return fittest.get(rand.nextInt(fittest.size()));
    }

    private List<Genome> newComers(int amountOfNeededSubjects) {
        ArrayList<Genome> newOnes = new ArrayList<>();
        addNewGenomesToPool(amountOfNeededSubjects, newOnes);
        return newOnes;
    }

    private void assertUsableParameters(Integer generations, Integer survivorsPercentage, Integer descendantsPercentage, Double mutationProbability) {
        assertGenerationsPositive(generations);
        assertPercentageBetween0and100(survivorsPercentage);
        assertPercentageBetween0and100(descendantsPercentage);
        assertMutationRatePositiveBetween0and1(mutationProbability);
    }


    private void assertCompletePopulation(int size) {
        if(size != POPULATION_SIZE){
            throw new RuntimeException("La cantidad de individuos no es la correcta");
        }
    }

    private void assertMutationRatePositiveBetween0and1(Double mutationProbability) {
        if(0 > mutationProbability || mutationProbability > 1){
            throw new RuntimeException("La probabilidad de mutacion tiene que estar entre 0 y 1");
        }
    }

    private void assertPercentageBetween0and100(Integer survivorsPercentage) {
        if(0 > survivorsPercentage || survivorsPercentage > 100){
            throw new RuntimeException("El porcentaje de sobrevivientes de cada generacion tiene que ser un entero entre 1 y 100");
        }
    }

    private void assertGenerationsPositive(Integer generations) {
        if(generations < 0){
            throw new RuntimeException("La cantidad de generaciones a evolucionar tiene que ser positiva");
        }
    }

    private void assertSurvivorsPlusDescendantsLessOrEqualTotalPopulation(Integer amountOfSurvivors, Integer amountOfDescendants) {
        if(amountOfDescendants+amountOfSurvivors > POPULATION_SIZE){
            throw new RuntimeException("No se puede");
        }
    }
}
