package ticTacToe.Genetic.Genes;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Genetic.Gene;
import ticTacToe.TicTacToe;

public class playOppositeCornerGene extends Gene {
    @Override
    public Integer checkForMove(TicTacToe game, GameMarkings symbol, GameMove move) {
        GameStrategy gameStrategy = new GameStrategy();
        return gameStrategy.opponentIsInCornerAndOppositeIsAvailable(game, symbol, move
        ) ? 1 : 0;
    }
}
