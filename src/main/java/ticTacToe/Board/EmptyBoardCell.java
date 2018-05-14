package ticTacToe.Board;

import ticTacToe.Game.GameMarkings;

public class EmptyBoardCell extends BoardCell {

	private final GameMarkings content;

	public EmptyBoardCell() {
		content = GameMarkings.EMPTY;
	}

	@Override
	public BoardCell newCell(GameMarkings symbol) {
		return new MarkedBoardCell(symbol);
	}

	@Override
	public Boolean isEmpty() {
		return true;
	}

	@Override
	public GameMarkings content() {
		return content;
	}


	@Override
	public boolean equals(Object other) {
		return false;
	}
}
