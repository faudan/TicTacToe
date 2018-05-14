package ticTacToe.Game;

import ticTacToe.Players.Player;
import ticTacToe.TicTacToe;

public class GameManager {

	private final TicTacToe game;
	private GamePlayer current;
	private GamePlayer next;

	public GameManager(GamePlayer gamePlayerOne, GamePlayer gamePlayerTwo, TicTacToe match) {
		current = gamePlayerOne;
		next = gamePlayerTwo;
		game = match;
	}

	public Player currentPlayer(){
		return current.player();
	}

	public Player nextPlayer(){
		return next.player();
	}

	public GameMarkings currentMark(){
		return current.symbol();
	}

	public GameMove nextPlay() {
		return current.moveFor(game);
	}

	public void advanceTurn() {
		GamePlayer justPlayed = current;
		current = next;
		next = justPlayed;
	}
}
