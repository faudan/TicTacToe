package ticTacToe;

import org.junit.Before;
import org.junit.Test;
import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Genetic.GeneticPlayer;
import ticTacToe.Genetic.Genome;
import ticTacToe.Players.HumanPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static ticTacToe.Game.GameMove.moveOf;

public class GeneticPlayerTest {

    private GameMarkings cross;
    private GameMarkings circle;
    private HumanPlayer human1;
    private HumanPlayer human2;
    private TicTacToe game;
    private GeneticPlayer genetic;
    private GameStrategy strategies;

    @Before
    public void setUp() {
        human1 = new HumanPlayer("X");
        human2 = new HumanPlayer("O");
        strategies = new GameStrategy();
        cross = GameMarkings.CROSS;
        circle = GameMarkings.CIRCLE;
        game = new TicTacToe(human1, human2);
        genetic = new GeneticPlayer(
                Genome.with(Arrays.asList(3.0, 1.6, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3)));
    }

    @Test
    public void theModifiedGeneticPlayerPlaysTheCenterWhenPossible() {
        assertThat(center(game), is(genetic.makeMoveFor(game, game.currentMark())));
    }

    @Test
    public void theModifiedGeneticPlayerPlaysAnAvailableCornerIfItHasNoBetterMove() {

        playerMove(human1, center(game), game);

        assertThat(strategies.boardCorners(), hasItem(genetic.makeMoveFor(game, game.currentMark())));
    }


    @Test
    public void theModifiedGeneticPlayerPlaysAnAvailableSideIfItHasNoBetterMove() {

        onlyCornersAndCenterPlayedWithNoWinners(game);

        assertThat(strategies.boardSides(), hasItem(genetic.makeMoveFor(game, game.currentMark())));

    }

    @Test
    public void theModifiedGeneticPlayerPlaysTheTheOppositeOfAnOpponentsCornerIfItHasNoBetterMove() {


        playerMove(human1, center(game), game);
        playerMove(human2, moveOf(0, 0), game);
        List<GameMove> possibleMoves = specificPositions();
        assertThat(possibleMoves, hasItem(genetic.makeMoveFor(game, game.currentMark())));
    }


    @Test
    public void theModifiedGeneticPlayerWinsWhenGivenTheChanceToCompleteAColumn() {


        setUpColumnForWin();
        playerMove(human1, genetic.makeMoveFor(game, game.currentMark()), game);

        assertThat(game.winner(), is(human1));

    }


    @Test
    public void theModifiedGeneticPlayerWinsWhenGivenTheChanceToCompleteARow() {


        setUpRowForWin();
        playerMove(human1, genetic.makeMoveFor(game, game.currentMark()), game);

        assertThat(game.winner(), is(human1));

    }


    @Test
    public void theModifiedGeneticPlayerWinsWhenGivenTheChanceToCompleteTheUpRightDiagonal() {


        setUpURDiagonalForWin();
        playerMove(human1, genetic.makeMoveFor(game, game.currentMark()), game);

        assertThat(game.winner(), is(human1));


    }


    @Test
    public void theModifiedGeneticPlayerWinsWhenGivenTheChanceToCompleteTheUpLeftDiagonal() {


        setUpULDiagonalToWin();
        playerMove(human1, genetic.makeMoveFor(game, game.currentMark()), game);

        assertThat(game.winner(), is(human1));

    }


    @Test
    public void theModifiedGeneticPlayerBlocksTheOpponentsChanceToWin() {


        setUpOpponentForWinOnFirstColumn();

        assertThat(GameMove.moveOf(1, 0), is(genetic.makeMoveFor(game, game.currentMark())));


    }

    @Test
    public void theModifiedGeneticPlayerSetsUpAForkWhenPossible() {

        playerMove(human1, center(game), game);
        playerMove(human2, moveOf(2, 0), game);
        playerMove(human1, moveOf(0, 2), game);
        playerMove(human2, moveOf(0, 1), game);

        List<GameMove> possibleForkMoves = forkMoves(game.currentMark());
        assertThat(possibleForkMoves, hasItem(genetic.makeMoveFor(game, game.currentMark())));

    }

    private List<GameMove> specificPositions() {
        return game.freePositions().stream().filter(position -> strategies.opponentIsInCornerAndOppositeIsAvailable(game, game.currentMark(), position)).collect(Collectors.toList());
    }

    private List<GameMove> forkMoves(GameMarkings gameMarkings) {
        return game.freePositions().stream().filter(position -> strategies.wouldMakeFork(game, gameMarkings, position)).collect(Collectors.toList());
    }

    @Test
    public void theModifiedGeneticPlayerBlocksAPossibleForkWhenPossible() {

        playerMove(human1, center(game), game);
        playerMove(human2, moveOf(2, 0), game);
        playerMove(human1, moveOf(0, 2), game);

        List<GameMove> possibleOppForkMoves = forkMoves(game.currentMark().opponentOf());
        assertThat(possibleOppForkMoves, hasItem(genetic.makeMoveFor(game, game.currentMark())));


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
        playerMove(human1, moveOf(0, 0), game);
        playerMove(human2, moveOf(0, 1), game);
        playerMove(human1, moveOf(2, 0), game);
    }

    private void setUpColumnForWin() {
        playerMove(human1, moveOf(0, 0), game);
        playerMove(human2, moveOf(0, 1), game);
        playerMove(human1, moveOf(2, 0), game);
        playerMove(human2, moveOf(1, 2), game);
    }

    private void setUpRowForWin() {
        playerMove(human1, moveOf(0, 0), game);
        playerMove(human2, moveOf(1, 0), game);
        playerMove(human1, moveOf(0, 1), game);
        playerMove(human2, moveOf(1, 2), game);
    }

    private void setUpURDiagonalForWin() {
        playerMove(human1, moveOf(1, 1), game);
        playerMove(human2, moveOf(1, 2), game);
        playerMove(human1, moveOf(0, 2), game);
        playerMove(human2, moveOf(2, 2), game);
    }

    private void setUpULDiagonalToWin() {
        playerMove(human1, moveOf(1, 1), game);
        playerMove(human2, moveOf(1, 2), game);
        playerMove(human1, moveOf(0, 0), game);
        playerMove(human2, moveOf(0, 2), game);
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
