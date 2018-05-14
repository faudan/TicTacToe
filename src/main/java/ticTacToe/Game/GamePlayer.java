package ticTacToe.Game;

import ticTacToe.Players.Player;
import ticTacToe.TicTacToe;

public class GamePlayer {

	private final Player player;
	private final GameMarkings symbol;

	public GamePlayer(Player playerName, GameMarkings mark) {
		player = playerName;
		symbol = mark;
	}

	public GameMove moveFor(TicTacToe game){
		return player.makeMoveFor(game, symbol);
	}

	public GameMarkings symbol(){
		return symbol;
	}

	public Player player() {
		return player;
	}
}
