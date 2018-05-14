package ticTacToe.Board;

import ticTacToe.Game.GameMarkings;

public abstract class BoardCell {


	public abstract Boolean isEmpty();

	public abstract GameMarkings content();

	public abstract BoardCell newCell(GameMarkings symbol);

}
