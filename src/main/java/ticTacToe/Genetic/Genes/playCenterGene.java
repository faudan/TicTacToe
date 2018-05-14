package ticTacToe.Genetic.Genes;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Genetic.Gene;
import ticTacToe.TicTacToe;

public class playCenterGene extends Gene {
    @Override
    public Integer checkForMove(TicTacToe game, GameMarkings symbol, GameMove move) {
        return move.equals(GameMove.moveOf(1, 1)) ? 1 : 0;
    }
}
