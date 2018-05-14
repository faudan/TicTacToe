package ticTacToe.Game;

import ticTacToe.Board.MarkedBoardCell;

public class GameMove {
	private Integer row;
	private Integer column;

	public GameMove(Integer rowToPlay, Integer colToPlay){
		row = rowToPlay;
		column = colToPlay;
	}

	public static GameMove moveOf(Integer r, Integer c){
		return new GameMove(r, c);
	}
	
	public Integer row(){
		return row;
	}
	public Integer column(){
		return column;
	}

	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof GameMove))return false;
		GameMove move = (GameMove) other;
		return row.equals(move.row()) && column.equals(move.column());
	}
}
