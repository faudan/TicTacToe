package ticTacToe.Board;

import ticTacToe.Game.GameMarkings;

public class MarkedBoardCell extends BoardCell {

	private GameMarkings content;


	public MarkedBoardCell(GameMarkings c) {
		content = c;
	}

	@Override
	public BoardCell newCell(GameMarkings symbol) {
		throw new RuntimeException(Board.ERROR_CELL_ALREADY_USED);
	}

	@Override
	public Boolean isEmpty() {
		return false;
	}

	@Override
	public GameMarkings content() {
		return content;
	}


	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof MarkedBoardCell))return false;
		MarkedBoardCell c = (MarkedBoardCell) other;
		return content.equals(c.content());
	}

}
