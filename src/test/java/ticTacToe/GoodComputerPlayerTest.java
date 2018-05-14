package ticTacToe;

import org.junit.Before;
import org.junit.Test;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Players.ComputerPlayer;
import ticTacToe.Players.HumanPlayer;
import ticTacToe.Players.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static ticTacToe.Game.GameMove.moveOf;

public class GoodComputerPlayerTest {

	private GameMarkings cross;
	private GameMarkings circle;
	private GameMarkings empty;
	private HumanPlayer human1;
	private Player cpu;
	private HumanPlayer human2;
	private TicTacToe game;
	private GameStrategy strategies;


	@Before
	public void setUp() {
		human1 = new HumanPlayer("X");
		human2 = new HumanPlayer("O");
		strategies = new GameStrategy();
		cross = GameMarkings.CROSS;
		circle = GameMarkings.CIRCLE;
		empty = GameMarkings.EMPTY;
		game = new TicTacToe(human1, human2);
		cpu = new ComputerPlayer("TicTacToeBot",strategies::playPerfectly);
	}


	@Test
	public void theComputerPlaysTheCenterWhenPossible() {

		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(1, 1)).isEqualTo(cross);
	}

	@Test
	public void theComputerPlaysTheFirstAvailableCornerIfItHasNoBetterMove() {

		playerMove(human1, center(game), game);
		playerMove(human2, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(1, 1)).isEqualTo(cross);
		assertThat(game.at(0, 0)).isEqualTo(circle);
	}



	@Test
	public void theComputerPlaysTheFirstAvailableSideIfItHasNoBetterMove() {

		onlyCornersAndCenterPlayedWithNoWinners(game);
		playerMove(human2, cpu.makeMoveFor(game, game.currentMark()), game);


		assertThat(game.at(0, 1)).isEqualTo(circle);

	}

	@Test
	public void theComputerPlaysTheTheOppositeOfAnOpponentsCornerIfItHasNoBetterMove() {


		playerMove(human1, center(game), game);
		playerMove(human2, moveOf(0,0), game);
		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(2, 2)).isEqualTo(cross);

	}

	@Test
	public void theComputerWinsWhenGivenTheChanceToCompleteAColumn() {


		setUpColumnForWin();
		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(1, 0)).isEqualTo(cross);
		assertThat(game.winner()).isEqualTo(human1);

	}



	@Test
	public void theComputerWinsWhenGivenTheChanceToCompleteARow() {


		setUpRowForWin();
		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(0, 2)).isEqualTo(cross);
		assertThat(game.winner()).isEqualTo(human1);

	}


	@Test
	public void theComputerWinsWhenGivenTheChanceToCompleteTheUpRightDiagonal() {


		setUpURDiagonalForWin();
		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(2, 0)).isEqualTo(cross);
		assertThat(game.winner()).isEqualTo(human1);

	}



	@Test
	public void theComputerWinsWhenGivenTheChanceToCompleteTheUpLeftDiagonal() {


		setUpULDiagonalToWin();
		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(2, 2)).isEqualTo(cross);
		assertThat(game.winner()).isEqualTo(human1);

	}


	@Test
	public void theComputerBlocksTheOpponentsChanceToWin() {


		setUpOpponentForWinOnFirstColumn();
		playerMove(human2, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(1, 0)).isEqualTo(circle);


	}

	@Test
	public void theComputerSetsUpAForkWhenPossible() {

		playerMove(human1, center(game), game);
		playerMove(human2, moveOf(2, 0), game);
		playerMove(human1, moveOf(0, 2), game);
		playerMove(human2, moveOf(0, 1), game);
		playerMove(human1, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(1, 2)).isEqualTo(cross);

	}

	@Test
	public void theComputerBlocksAPossibleForkWhenPossible() {

		playerMove(human1, center(game), game);
		playerMove(human2, moveOf(2, 0), game);
		playerMove(human1, moveOf(0, 2), game);
		playerMove(human2, cpu.makeMoveFor(game, game.currentMark()), game);

		assertThat(game.at(0, 0)).isEqualTo(circle);

	}

	private void onlyCornersAndCenterPlayedWithNoWinners(TicTacToe currentGame) {
		playerMove(human1, center(currentGame), currentGame);
		playerMove(human2, freeCorner(currentGame), currentGame);
		playerMove(human1, freeCorner(currentGame), currentGame);
		playerMove(human2, freeCorner(currentGame), currentGame);
		playerMove(human1, freeCorner(currentGame), currentGame);
		playerMove(human2, moveOf(1, 2), currentGame);
		playerMove(human1, moveOf(1, 0), currentGame);
	}

	private void setUpOpponentForWinOnFirstColumn() {
		playerMove(human1, moveOf(0,0), game);
		playerMove(human2, moveOf(0,1), game);
		playerMove(human1, moveOf(2,0), game);
	}

	private void setUpColumnForWin() {
		playerMove(human1, moveOf(0,0), game);
		playerMove(human2, moveOf(0,1), game);
		playerMove(human1, moveOf(2,0), game);
		playerMove(human2, moveOf(1,2), game);
	}

	private void setUpRowForWin() {
		playerMove(human1, moveOf(0,0), game);
		playerMove(human2, moveOf(1,0), game);
		playerMove(human1, moveOf(0,1), game);
		playerMove(human2, moveOf(1,2), game);
	}

	private void setUpURDiagonalForWin() {
		playerMove(human1, moveOf(1,1), game);
		playerMove(human2, moveOf(1,2), game);
		playerMove(human1, moveOf(0,2), game);
		playerMove(human2, moveOf(2,2), game);
	}

	private void setUpULDiagonalToWin() {
		playerMove(human1, moveOf(1,1), game);
		playerMove(human2, moveOf(1,2), game);
		playerMove(human1, moveOf(0,0), game);
		playerMove(human2, moveOf(0,2), game);
	}

	private GameMove center(TicTacToe currentGame) {
		return strategies.playCenter(currentGame).get();
	}

	private GameMove freeCorner(TicTacToe currentGame) {
		return strategies.firstFreeCorner(currentGame).get();
	}


	/****************************************************************************************************************************/
	private void playerMove(HumanPlayer player, GameMove gm, TicTacToe currentGame) {
		player.nextMove(gm);
		currentGame.playTurn();
	}

}
