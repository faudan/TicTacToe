package ticTacToe.Board;

import ticTacToe.Game.GameMarkings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {


	public static final String ERROR_POSITION_NOT_IN_BOARD = "That position is not in the board";
	public static final String ERROR_CELL_ALREADY_USED = "Position already in use";
	private final List<List<BoardCell>> cells;

	public Board() {
		this.cells = new ArrayList<>();
		Arrays.asList(0, 1, 2).forEach(numRow -> {
			cells.add(new ArrayList<>());
			Arrays.asList(0, 1, 2).forEach(
					numCol -> cells.get(numRow).add(new EmptyBoardCell())
			);
		});
	}

	public BoardCell position(Integer row, Integer col) {
		return cells.get(row).get(col);
	}


	public GameMarkings at(int row, int col) {
		return position(row, col).content();
	}

	public Boolean isEmpty() {

		return cells.stream().flatMap(row -> row.stream().map(cell -> {
			return cell.isEmpty();
		})).reduce(true, (acum, value) -> acum && value);
	}

	public boolean isFull() {
		return cells.stream().flatMap(row -> {
			return row.stream().map(cell -> {
				return !cell.isEmpty();
			});
		}).reduce(true, (acum, value) -> acum && value);
	}

	public void addSymbolAt(GameMarkings symbol, Integer row, Integer column) {
		assertInBoard(row);
		assertInBoard(column);
		BoardCell cell = cells.get(row).get(column).newCell(symbol);
		cells.get(row).set(column, cell);
	}

	private void assertInBoard(Integer i) {
		if (i < 0 || 2 < i) throw new RuntimeException(ERROR_POSITION_NOT_IN_BOARD);
	}

	public void remove(Integer row, Integer column) {
		cells.get(row).set(column, new EmptyBoardCell());
	}
}
