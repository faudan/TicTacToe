package ticTacToe;

import org.junit.Before;
import org.junit.Test;
import ticTacToe.Board.Board;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Players.ComputerPlayer;
import ticTacToe.Players.HumanPlayer;
import ticTacToe.Players.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class TicTacToeTest {

	private TicTacToe game;
	private GameMarkings cross;
	private GameMarkings circle;
	private GameMarkings empty;
	private HumanPlayer playerOne;
	private HumanPlayer playerTwo;
	private GameStrategy strategies;

	private void playerMove(HumanPlayer player, int rowToPlay, int colToPlay, TicTacToe tictactoe) {
		player.nextMove(new GameMove(rowToPlay, colToPlay));
		tictactoe.playTurn();
	}

	@Before
	public void setUp(){
		playerOne = new HumanPlayer("X");
		playerTwo = new HumanPlayer("O");
		game = new TicTacToe(playerOne, playerTwo);
		cross = GameMarkings.CROSS;
		circle = GameMarkings.CIRCLE;
		empty = GameMarkings.EMPTY;
		strategies = new GameStrategy();
	}

	@Test
	public void aNewGameIsNotOver(){

		assertThat(game.isOver()).isFalse();

	}

	@Test
	public void canKnowWhoIsPlaying(){

		assertThat(game.currentPlayer()).isEqualTo(playerOne.playerName());
	}

	@Test
	public void afterAPlayerPlaysItIsTheOtherPlayersTurn(){
		playerMove(playerOne, 1, 1, game);
		assertThat(game.currentPlayer()).isEqualTo(playerTwo.playerName());
	}

	@Test
	public void afterAPlayerPlaysThePlayIsReflectedInTheBoard(){
		playerMove(playerOne, 1, 1, game);

		assertThat(game.at(1, 1)).isEqualTo(cross);
	}

	@Test
	public void afterTwoPlayersPlayThePlaysAreReflectedInTheBoard(){
		playerMove(playerOne, 1, 1, game);
		playerMove(playerTwo, 0, 1, game);

		assertThat(game.at(0, 1)).isEqualTo(circle);
	}

	@Test
	public void aPositionThatWasNotPlayedIsEmpty(){

		assertThat(game.at(0, 1)).isEqualTo(empty);
	}

	@Test
	public void aPositionCanNotBePlayedMoreThanOnce(){
		playerMove(playerOne, 1, 1, game);
		try {
			playerMove(playerTwo, 1, 1, game);
			fail();
		} catch (Exception e){
			assertThat(e.getMessage()).isEqualTo(Board.ERROR_CELL_ALREADY_USED);
			assertThat(game.at(1,1)).isEqualTo(cross);
		}
	}

	@Test
	public void whenAPlayerPlays3TimesInTheSameRowTheGameEnds(){
		endGameByRow();

		assertThat(game.isOver()).isTrue();
	}



	@Test
	public void whenAPlayerPlays3TimesInTheSameColumnTheGameEnds(){
		endGameByColumn();

		assertThat(game.isOver()).isTrue();
	}


	@Test
	public void whenAPlayerPlays3TimesInTheSameDiagonalTheGameEnds(){
		endGameByDiagonal();

		assertThat(game.isOver()).isTrue();
	}

	@Test
	public void aPlayerThatPlays3InLineIsTheWinner(){
		endGameByDiagonal();

		assertThat(game.winner()).isEqualTo(playerOne);
	}

	@Test
	public void aFinishedGameCanNotBePlayedAnyMore(){
		endGameByColumn();
		try {
			game.playAt(2,2);
			fail();
		} catch (Exception e){
			assertThat(e.getMessage()).isEqualTo(TicTacToe.ERROR_GAME_HAS_ENDED);
			assertThat(game.at(2,2)).isEqualTo(empty);
		}
	}


	@Test
	public void whenTheBoardIsFullTheGameEnds(){
		endGameInDraw();
		assertThat(game.isOver()).isTrue();
	}

	@Test
	public void whenTheBoardIsFullAndNoPlayerWonTheGameEndsInADraw(){
		endGameInDraw();
		try {
			game.winner();
			fail();
		} catch (Exception e){
			assertThat(e.getMessage()).isEqualTo(TicTacToe.ERROR_GAME_ENDED_IN_DRAW);
		}
	}

	@Test
	public void aPlayerCanBeNonHuman(){
		Player playerCPU = new ComputerPlayer("X", strategies::firstOpenSlot);
		TicTacToe gameWithCPU = new TicTacToe(playerCPU, playerTwo);

		gameWithCPU.playTurn();

		playerMove(playerTwo, 0, 1, gameWithCPU);

		gameWithCPU.playTurn();

		assertThat(gameWithCPU.at(0,0)).isEqualTo(cross);
		assertThat(gameWithCPU.at(0,1)).isEqualTo(circle);
		assertThat(gameWithCPU.at(0,2)).isEqualTo(cross);
	}

	private void endGameInDraw() {
		game.playAt(0,0);//X
		game.playAt(0,1);//O
		game.playAt(1,0);//X
		game.playAt(1,2);//O
		game.playAt(1,1);//X
		game.playAt(2,2);//O
		game.playAt(2,1);//X
		game.playAt(2,0);//O
		game.playAt(0,2);//X
	}

	private void endGameByRow() {
		game.playAt(0,0);//X
		game.playAt(1,0);//O
		game.playAt(0,1);//X
		game.playAt(1,1);//O
		game.playAt(0,2);//X
	}

	private void endGameByColumn() {
		game.playAt(0,0);//X
		game.playAt(2,1);//O
		game.playAt(1,0);//X
		game.playAt(1,1);//O
		game.playAt(2,0);//X
	}
	private void endGameByDiagonal() {
		game.playAt(0,0);//X
		game.playAt(2,1);//O
		game.playAt(1,1);//X
		game.playAt(0,1);//O
		game.playAt(2,2);//X
	}
}
