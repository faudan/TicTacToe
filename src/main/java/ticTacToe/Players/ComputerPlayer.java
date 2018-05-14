package ticTacToe.Players;

import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameMarkings;
import ticTacToe.TicTacToe;

import java.util.function.BiFunction;

public class ComputerPlayer implements Player {
	private final String name;
	private BiFunction<TicTacToe, GameMarkings, GameMove> strategy;

	public ComputerPlayer(String playerName, BiFunction<TicTacToe,GameMarkings,GameMove> playStrategy){
		name = playerName;
		strategy = playStrategy;
	}

	@Override
	public String playerName() {
		return name;
	}

	@Override
	public GameMove makeMoveFor(TicTacToe game, GameMarkings mark) {
		return strategy.apply(game,mark);
	}

}
