package ticTacToe.Game;

import ticTacToe.TicTacToe;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static ticTacToe.Game.GameMove.moveOf;

public class GameStrategy {

	public GameMove firstOpenSlot(TicTacToe currentGame, GameMarkings playerMarking) {
		return checkBoardForFirst(boardPositions(),
								  gameMove -> gameBoardIsFreeAt(currentGame, gameMove))
				.orElse(new GameMove(-1, -1));
	}

    public GameMove randomPlay(TicTacToe currentGame, GameMarkings playerMarking) {
        List<GameMove> freeSlots = currentGame.freePositions();
        Random rand = new Random();
        return freeSlots.get(rand.nextInt(freeSlots.size()));
    }

	public GameMove playPerfectly(TicTacToe currentGame, GameMarkings playerMarking) {
		return winningMove(currentGame, playerMarking)
				.orElse(blockOpponentWin(currentGame, playerMarking)
                        .orElse(createFork(currentGame, playerMarking)
                                .orElse(blockOpponentFork(currentGame, playerMarking)
                                        .orElse(playCenter(currentGame)
                                                .orElse(playOppositeCornerFromOpponent(currentGame, playerMarking)
                                                        .orElse(firstFreeCorner(currentGame)
                                                                .orElse(firstFreeSide(currentGame)
                                                                        .orElse(moveOf(-1, -1)))))))))
				;
	}

	private Optional<GameMove> winningMove(TicTacToe currentGame, GameMarkings playerMarking) {
		return checkBoardForFirst(boardPositions(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove)
				&& makesLineOf3(currentGame, playerMarking, gameMove));
	}

	private Optional<GameMove> blockOpponentWin(TicTacToe currentGame, GameMarkings playerMarking) {
		return checkBoardForFirst(boardPositions(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove)
				&& makesLineOf3(currentGame, playerMarking.opponentOf(), gameMove));
	}

	private Optional<GameMove> createFork(TicTacToe currentGame, GameMarkings playerMarking) {
		return checkBoardForFirst(boardPositions(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove)
				&& wouldMakeFork(currentGame, playerMarking, gameMove));
	}


	private Optional<GameMove> blockOpponentFork(TicTacToe currentGame, GameMarkings playerMarking) {
		return checkBoardForFirst(boardPositions(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove)
				&& wouldMakeFork(currentGame, playerMarking.opponentOf(), gameMove));
	}


	public Optional<GameMove> playCenter(TicTacToe currentGame) {
		return checkBoardForFirst(center(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove));
	}

	public Optional<GameMove> playOppositeCornerFromOpponent(TicTacToe currentGame, GameMarkings playerMarking) {
		return checkBoardForFirst(boardCorners(), gameMove ->
                opponentIsInCornerAndOppositeIsAvailable(currentGame, playerMarking, gameMove
                ));
	}

	public Optional<GameMove> firstFreeCorner(TicTacToe currentGame) {
		return checkBoardForFirst(boardCorners(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove));
	}

	public Optional<GameMove> firstFreeSide(TicTacToe currentGame) {

		return checkBoardForFirst(boardSides(), gameMove -> gameBoardIsFreeAt(currentGame, gameMove));
	}

	private List<GameMove> boardPositions() {

		List<GameMove> positions = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				positions.add(moveOf(i, j));
			}
		}
		return positions;
	}

    public List<GameMove> boardSides() {
		return Arrays.asList(moveOf(0, 1), moveOf(1, 0), moveOf(1, 2), moveOf(2, 1));
	}

    public List<GameMove> boardCorners() {
		return Arrays.asList(moveOf(0, 0), moveOf(0, 2), moveOf(2, 0), moveOf(2, 2));
	}

	private List<GameMove> center() {
		return Arrays.asList(moveOf(1, 1));
	}


    public boolean makesLineOf3(TicTacToe currentGame, GameMarkings playerMarking, GameMove gameMove) {
		Integer row = gameMove.row();
		Integer col = gameMove.column();
		return
				wouldMake3InLineIn(currentGame, playerMarking, boardColumn(col))
						|| wouldMake3InLineIn(currentGame, playerMarking, boardRow(row))
						|| isInDiagonalAndWouldMake3InLine(playerMarking, currentGame, gameMove, upLeftDiagonal())
						|| isInDiagonalAndWouldMake3InLine(playerMarking, currentGame, gameMove, upRightDiagonal())
				;

	}

	private boolean wouldMake3InLineIn(TicTacToe currentGame, GameMarkings playerMarking, List<GameMove> gameMoves) {
		return amountOfSameMarkingsIn(currentGame, playerMarking, gameMoves)== 2;
	}

	private boolean isInDiagonalAndWouldMake3InLine(GameMarkings playerMarking, TicTacToe currentGame, GameMove gameMove, List<GameMove> gameMoves) {
		return gameMoves.contains(gameMove) && wouldMake3InLineIn(currentGame, playerMarking, gameMoves);
	}

	private Long amountOfSameMarkingsIn(TicTacToe currentGame, GameMarkings playerMarking, List<GameMove> gameMoves) {
		return gameMoves.stream().filter(cell ->
												 currentGame.at(cell.row(), cell.column()).equals(playerMarking))
				.count();
	}

    public boolean wouldMakeFork(TicTacToe currentGame, GameMarkings playerMarking, GameMove hypoteticMove) {
		Integer column = hypoteticMove.column();
		Integer row = hypoteticMove.row();
		currentGame.board().addSymbolAt(playerMarking, row, column);
		long amountOfPossibleWinMovesAfterMove = checkBoardFor(boardPositions(),
															   gameMove -> gameBoardIsFreeAt(currentGame, gameMove)
																	   && makesLineOf3(currentGame,
																					   playerMarking,
																					   gameMove)).count();
		currentGame.board().remove(row, column);
		return amountOfPossibleWinMovesAfterMove >= 2;
	}

	private List<GameMove> boardColumn(Integer col) {
		return Arrays.asList(moveOf(0, col), moveOf(1, col), moveOf(2, col));
	}

	private List<GameMove> boardRow(Integer row) {
		return Arrays.asList(moveOf(row, 0), moveOf(row, 1), moveOf(row, 2));
	}

	private List<GameMove> upRightDiagonal() {
		return Arrays.asList(moveOf(2, 0), moveOf(1, 1), moveOf(0, 2));
	}

	private List<GameMove> upLeftDiagonal() {
		return Arrays.asList(moveOf(0, 0), moveOf(1, 1), moveOf(2, 2));
	}

	private Optional<GameMove> checkBoardForFirst(List<GameMove> positionsToSearch, Predicate<GameMove> typeOfMoveToFind) {

		return checkBoardFor(positionsToSearch, typeOfMoveToFind).findFirst();

	}

	private Stream<GameMove> checkBoardFor(List<GameMove> positionsToSearch, Predicate<GameMove> typeOfMoveToFind) {
		return positionsToSearch.stream().filter(typeOfMoveToFind);
	}

	private Boolean gameBoardIsFreeAt(TicTacToe currentGame, GameMove gameMove) {
		return currentGame.isFreeAt(gameMove.row(), gameMove.column());
	}

    public boolean opponentIsInCornerAndOppositeIsAvailable(TicTacToe currentGame, GameMarkings playerMarking, GameMove playerMove) {
        GameMove opponentCorner = oppositeFrom(playerMove);
        Integer row = opponentCorner.row();
		Integer col = opponentCorner.column();
		return !currentGame.isFreeAt(row, col)
				&& currentGame.at(row, col).isOpponentOf(playerMarking)
				&& gameBoardIsFreeAt(currentGame, playerMove);
	}

	public GameMove oppositeFrom(GameMove gameMove) {
		int r = 0;
		int c = 0;
		if (gameMove.row() == 0) r = 2;
		if (gameMove.column() == 0) c = 2;
		return moveOf(r, c);
	}


}

/*
Win: If the player has two in a row, they can place a third to get three in a row.

Block: If the opponent has two in a row, the player must play the third
	themselves to block the opponent.

Fork: Create an opportunity where the player has two threats to win (two non-blocked lines of 2).

Blocking an opponent's fork:

	Option 1: The player should create two in a row to force the opponent into defending,
	 as long as it doesn't result in them creating a fork.
	 For example, if "X" has two opposite corners and "O" has the center,
	  "O" must not play a corner in order to win.
	  (Playing a corner in this scenario creates a fork for "X" to win.)

	Option 2: If there is a configuration where the opponent can fork,
	 the player should block that fork.

Center: A player marks the center. (If it is the first move of the game,
 	playing on a corner gives the second player more opportunities to make a mistake
 	and may therefore be the better choice; however,
  	it makes no difference between perfect players.)

Opposite corner: If the opponent is in the corner, the player plays the opposite corner.

Empty corner: The player plays in a corner square.

Empty side: The player plays in a middle square on any of the 4 sides.
*/