package ticTacToe.Genetic;

import ticTacToe.Game.GameMove;

public class EvaluatedMove {
    private GameMove move;
    private Double value;

    public EvaluatedMove(GameMove aMove, Double aValue) {
        move = aMove;
        value = aValue;
    }

    public int orderByValue(EvaluatedMove anotherMove) {
        return this.getValue().compareTo(anotherMove.getValue());
    }

    public GameMove getMove() {
        return move;
    }

    public Double getValue() {
        return value;
    }
}
