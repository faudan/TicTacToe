package ticTacToe.Players;

import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameMarkings;
import ticTacToe.TicTacToe;

public class HumanPlayer implements Player {


	private final String name;
	private GameMove nextGameMove;


	public void nextMove(GameMove next){
		nextGameMove = next;
	}

	@Override
	public GameMove makeMoveFor(TicTacToe game, GameMarkings s) {
		return nextGameMove;
	}

	@Override
	public String playerName(){
		return name;
	}

	public HumanPlayer(String playerName) {
		name = playerName;
		nextGameMove = new GameMove(0, 0);
	}

}
