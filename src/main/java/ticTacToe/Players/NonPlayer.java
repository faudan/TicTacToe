package ticTacToe.Players;

import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.TicTacToe;

public class NonPlayer implements Player {
    @Override
    public String playerName() {
        return "draw";
    }

    @Override
    public GameMove makeMoveFor(TicTacToe game, GameMarkings selfSymbol) {
        return GameMove.moveOf(-1,-1);
    }
}
