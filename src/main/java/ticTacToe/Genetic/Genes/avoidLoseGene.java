package ticTacToe.Genetic.Genes;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Genetic.Gene;
import ticTacToe.TicTacToe;

public class avoidLoseGene extends Gene {
    @Override
    public Integer checkForMove(TicTacToe game, GameMarkings symbol, GameMove move) {
        GameStrategy gameStrategy = new GameStrategy();
        return gameStrategy.makesLineOf3(game, symbol.opponentOf(), move) ? 1 : 0;
    }
}
