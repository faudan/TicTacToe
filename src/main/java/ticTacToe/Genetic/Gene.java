package ticTacToe.Genetic;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.TicTacToe;

public abstract class Gene {

    public abstract Integer checkForMove(TicTacToe game, GameMarkings symbol, GameMove move);
}
