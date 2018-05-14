package ticTacToe.Genetic;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Players.Player;
import ticTacToe.TicTacToe;

import java.util.List;
import java.util.stream.Collectors;

public class GeneticPlayer implements Player {

    private final Genome genome;
    private final String name;

    public GeneticPlayer(Genome playerGenome) {
        name = "Genetico";
        genome = playerGenome;
    }

    @Override
    public String playerName() {
        return name;
    }

    @Override
    public GameMove makeMoveFor(TicTacToe game, GameMarkings symbol) {
        List<GameMove> moves = game.freePositions();
        List<EvaluatedMove> evaluatedMoves = moves.stream()
                .map(move -> new EvaluatedMove(move, genome.geneticEvaluationOf(game, symbol, move)))
                .collect(Collectors.toList());
        evaluatedMoves.sort(EvaluatedMove::orderByValue);
        return evaluatedMoves.get(moves.size() - 1).getMove();
    }

}
