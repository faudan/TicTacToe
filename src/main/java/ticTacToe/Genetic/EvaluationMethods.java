package ticTacToe.Genetic;

import ticTacToe.Game.GameStrategy;
import ticTacToe.Players.ComputerPlayer;
import ticTacToe.Players.Player;
import ticTacToe.TicTacToe;

import java.util.List;
import java.util.Random;

public class EvaluationMethods {
    private final GameStrategy gameStrategy= new GameStrategy();

    public Double manyOpponents(Genome genome, List<Genome> champions) {
        Double fitness = 0.0;
        Player subject = new GeneticPlayer(genome);
        fitness = playVsRandomTimes(fitness, subject, 20);
        fitness = playVsPerfect(fitness, subject);
        fitness = playVsChampions(fitness, subject, champions);
        return fitness;
    }

    public Double perfectOpponent(Genome genome, List<Genome> champions) {
        Double fitness = 0.0;
        Player subject = new GeneticPlayer(genome);
        fitness = playVsPerfect(fitness, subject);
        return fitness;
    }

    private Double playVsChampions(Double fitness, Player subject,List<Genome> champions) {
        for (int i = 0; i < Math.min(champions.size(),20); i++) {
            GeneticPlayer oppChampion = new GeneticPlayer(champions.get(new Random().nextInt(champions.size())));
            fitness = trainAgainstOpponentTimes(1, fitness, oppChampion, subject, 1.0  , 1.5);
        }
        return fitness;
    }

    private Double playVsPerfect(Double fitness, Player subject) {
        Player opponent = new ComputerPlayer("cpu",gameStrategy::playPerfectly);
        fitness = trainAgainstOpponentTimes(1, fitness, opponent, subject, 20.0, 50.0);
        return fitness;
    }

    private Double playVsRandomTimes(Double fitness, Player subject, int gamesToPlay) {
        Player opponent = new ComputerPlayer("cpu",gameStrategy::randomPlay);
        fitness = trainAgainstOpponentTimes(gamesToPlay, fitness, opponent, subject, 0.5, 0.75);
        return fitness;
    }


    private Double trainAgainstOpponentTimes(Integer gamesToPlay, Double fitness, Player opponent, Player subject, Double fitnessIncrement, double fitnessIncrementWin) {
        for (int matchesVsOpp = 0; matchesVsOpp < gamesToPlay; matchesVsOpp++) {
            fitness = playGameAsPlayer1AndRecordResult(fitness, opponent, subject, fitnessIncrement, fitnessIncrementWin);
            fitness = playGameAsPlayer2AndRecordResult(fitness, opponent, subject, fitnessIncrement*2, fitnessIncrementWin*2);
        }
        return fitness;
    }

    private Double playGameAsPlayer1AndRecordResult(Double fitness, Player cpu, Player subject, Double fitnessIncrement, double fitnessIncrementWin) {
        TicTacToe game = new TicTacToe(subject, cpu);
        fitness = playAndRecordResult(fitness, cpu, subject, fitnessIncrement, game, fitnessIncrementWin);
        return fitness;
    }

    private Double playGameAsPlayer2AndRecordResult(Double fitness, Player cpu, Player subject, Double fitnessIncrement, double fitnessIncrementWin) {
        TicTacToe game = new TicTacToe(cpu, subject);
        fitness = playAndRecordResult(fitness, cpu, subject, fitnessIncrement, game, fitnessIncrementWin);
        return fitness;
    }


    private Double playAndRecordResult(Double fitness, Player cpu, Player subject, Double fitnessIncrementTie, TicTacToe game, double fitnessIncrementWin) {
        while (!game.isOver()) {
            game.playTurn();
        }
        if (game.winner() != cpu) {
            fitness += fitnessIncrementTie;
        }
        if (game.winner() == subject) {
            fitness += fitnessIncrementWin;
        }
        return fitness;
    }
}
