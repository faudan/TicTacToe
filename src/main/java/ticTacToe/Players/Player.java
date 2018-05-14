package ticTacToe.Players;

import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameMarkings;
import ticTacToe.TicTacToe;

public interface Player {

	String playerName();

	GameMove makeMoveFor(TicTacToe game, GameMarkings selfSymbol);

}
