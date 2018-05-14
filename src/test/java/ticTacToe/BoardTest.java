package ticTacToe;

import org.junit.Before;
import org.junit.Test;
import ticTacToe.Board.Board;
import ticTacToe.Game.GameMarkings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class BoardTest {


	private Board board;
	private GameMarkings cross;
	private GameMarkings empty;
	private GameMarkings circle;


	@Before
	public void setUp(){
		board = new Board();
		cross = GameMarkings.CROSS;
		empty = GameMarkings.EMPTY;
		circle = GameMarkings.CIRCLE;
	}

	@Test
	public void aNewBoardIsEmpty(){

		assertThat(board.isEmpty()).isTrue();

	}

	@Test
	public void afterAddingSomethingToABoardIsNotEmpty(){
		board.addSymbolAt(cross, 0, 0);

		assertThat(board.isEmpty()).isFalse();

	}

	@Test
	public void afterAddingSomethingInAPositionOfTheBoardItCanBeFoundThere(){
		board.addSymbolAt(cross, 0, 0);

		assertThat(board.at(0, 0)).isEqualTo(cross);

	}

	@Test
	public void aPositionWithNothingOnItIsEmpty(){
		board.addSymbolAt(cross, 0, 0);

		assertThat(board.position(1, 0).isEmpty()).isTrue();

	}

	@Test
	public void aPositionOfAnEmptyBoardIsEmpty(){
		assertThat(board.position(0, 0).isEmpty()).isTrue();
	}

	@Test
	public void aPositionWhereSomethingWasAddedIsNotEmpty(){
		board.addSymbolAt(cross, 0, 0);
		assertThat(board.position(0, 0).isEmpty()).isFalse();
	}

	@Test
	public void emptyCellsHaveNoContent(){
		assertThat(board.at(0, 0)).isEqualTo(empty);

	}

	@Test
	public void canNotPutSomethingOnANonEmptyCell(){
		board.addSymbolAt(cross, 0, 0);
		try {
			board.addSymbolAt(circle, 0, 0);
			fail();
		} catch (Exception e){
			assertThat(e.getMessage()).isEqualTo(Board.ERROR_CELL_ALREADY_USED);
			assertThat(board.at(0, 0)).isEqualTo(cross);
		}
	}

	@Test
	public void canNotPutSomethingOutsideTheBoard(){

		try {
			board.addSymbolAt(circle, 5, 0);
			fail();
		} catch (Exception e){
			assertThat(e.getMessage()).isEqualTo(Board.ERROR_POSITION_NOT_IN_BOARD);
			assertThat(board.isEmpty()).isTrue();
		}
	}




}
