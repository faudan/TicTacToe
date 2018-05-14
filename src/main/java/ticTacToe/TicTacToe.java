package ticTacToe;

import ticTacToe.Board.Board;
import ticTacToe.Board.BoardCell;
import ticTacToe.Game.GameManager;
import ticTacToe.Game.GamePlayer;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameMarkings;
import ticTacToe.Players.NonPlayer;
import ticTacToe.Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static ticTacToe.Game.GameMove.moveOf;

public class TicTacToe {

	public static final String ERROR_GAME_HAS_ENDED = "Game is over";
	public static final String ERROR_GAME_NOT_OVER = "Game not over";
	public static final String ERROR_GAME_ENDED_IN_DRAW = "Game ended in draw";
	private Board board;
	private GameManager turnManager;


	public TicTacToe(Player firstPlayer, Player secondPlayer) {

		turnManager = new GameManager(new GamePlayer(firstPlayer, GameMarkings.CROSS),
									  new GamePlayer(secondPlayer, GameMarkings.CIRCLE),
									  this);
		board = new Board();
	}

	public Player winner() {
			if (thereIsWinner()) return turnManager.nextPlayer();
			else if (boardIsFull()) return new NonPlayer();
			else throw new RuntimeException(ERROR_GAME_NOT_OVER);
	}

	public Boolean isOver(){
		return thereIsWinner() || boardIsFull();
	}

	public boolean boardIsFull() {
		return board.isFull();
	}

	public void playAt(Integer row, Integer column){
		assertCanPlay();
		board.addSymbolAt(turnManager.currentMark(), row, column);
		turnManager.advanceTurn();
	}

	public void playTurn(){

		GameMove gameMove = turnManager.nextPlay();
		playAt(gameMove.row(), gameMove.column());
	}

	public String currentPlayer(){
		return turnManager.currentPlayer().playerName();
	}

	public GameMarkings currentMark(){
		return turnManager.currentMark();
	}

	public Boolean isFreeAt(Integer row, Integer col){
		return board.position(row,col).isEmpty();
	}

	public GameMarkings at(Integer row, Integer col) {
		return board.at(row, col);
	}

	public boolean thereIsWinner() {
		return winAtAnyRow()
				|| winAnyColumn()
				|| winAnyDiagonal();
	}


	private Boolean winAtAnyRow() {
		return checkBoardFor(this::winAtRow);
	}

	private Boolean winAnyColumn() {
		return checkBoardFor(this::winAtColumn);
	}

	private Boolean checkBoardFor(Function<Integer, Boolean> winCondition) {
		return Stream.of(0, 1, 2).map(winCondition)
				.reduce(false, (acum, value) -> acum || value);
	}

	private Boolean winAnyDiagonal() {
		return winUpLeftDiagonal() || winUpRightDiagonal();
	}

	private Boolean winUpRightDiagonal() {
		int centralRow = 1;
		int centralColumn = 1;
		BoardCell middle = board.position(centralRow, centralColumn);
		return middle.equals(board.position(centralRow+1, centralColumn-1))
				&& middle.equals(board.position(centralRow-1, centralColumn+1));
	}

	private Boolean winUpLeftDiagonal() {
		int centralRow = 1;
		int centralColumn = 1;
		BoardCell middle = board.position(centralRow, centralColumn);
		return middle.equals(board.position(centralRow-1, centralColumn-1))
				&& middle.equals(board.position(centralRow+1, centralColumn+1));
	}

	private Boolean winAtColumn(Integer column) {

		int centralRow = 1;
		BoardCell middle = board.position(centralRow, column);
		return middle.equals(board.position(centralRow+1, column))
				&& middle.equals(board.position(centralRow-1, column));
	}

	private Boolean winAtRow(Integer row) {
		int centralColumn = 1;
		BoardCell middle = board.position(row, centralColumn);
		return middle.equals(board.position(row, centralColumn+1))
				&& middle.equals(board.position(row, centralColumn-1));
	}

	private void assertCanPlay() {
		if(isOver()) throw new RuntimeException(ERROR_GAME_HAS_ENDED);
	}


	public Board board() {
		return board;
	}

    public List<GameMove> freePositions() {

        List<GameMove> positions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isFreeAt(i, j)) {
                    positions.add(moveOf(i, j));
                }
            }
        }
        return positions;
    }

}
