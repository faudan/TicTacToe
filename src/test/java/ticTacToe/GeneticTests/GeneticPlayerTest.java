package ticTacToe.GeneticTests;

import org.junit.Before;
import org.junit.Test;
import ticTacToe.Game.GameMarkings;
import ticTacToe.Game.GameMove;
import ticTacToe.Game.GameStrategy;
import ticTacToe.Genetic.GeneticPlayer;
import ticTacToe.Genetic.Genome;
import ticTacToe.Players.ComputerPlayer;
import ticTacToe.Players.HumanPlayer;
import ticTacToe.Players.Player;
import ticTacToe.TicTacToe;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static ticTacToe.Game.GameMove.moveOf;

public class GeneticPlayerTest {

    private HumanPlayer human1;
    private HumanPlayer human2;
    private TicTacToe game;
    private Player genetic;
    private GameStrategy strategies;

    @Before
    public void setUp() {
        human1 = new HumanPlayer("X");
        human2 = new HumanPlayer("O");
        strategies = new GameStrategy();
        game = new TicTacToe(human1, human2);
//        genetic = new ComputerPlayer("TicTacToeBot",strategies::playPerfectly);
//        genetic = new GeneticPlayer("",Genome.with(Arrays.asList(3.0, 1.6, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3)));
//        genetic = new ComputerPlayer("TicTacToeBot",strategies::randomPlay);
        genetic = new GeneticPlayer(Genome.with(Arrays.asList(0.037428982847471204, 2.971789877374368, 3.6394719454625517, 1.1984674741060726, 1.8702662890573558, 1.5144304960099717, 3.6081856293541064, 3.122576714380418)));
//        genetic = new GeneticPlayer(new Genome());
//        genetic = new GeneticPlayer(generations10());
//        genetic = new GeneticPlayer(generations100());
        genetic = new GeneticPlayer(generations500());
    }
//
//
    @Test
    public void theTrainedPlayerDoesNotLoseVersusTheRandomPlayerWhileFirst(){
        ComputerPlayer randomPlayer = new ComputerPlayer("cpu", strategies::randomPlay);
        assertThat(playFullGame(genetic, randomPlayer).winner(), not(randomPlayer));
    }

    @Test
    public void theTrainedPlayerDoesNotLoseVersusTheRandomPlayerWhileSecond(){
        ComputerPlayer randomPlayer = new ComputerPlayer("cpu", strategies::randomPlay);
        assertThat(playFullGame(randomPlayer,genetic).winner(), not(randomPlayer));
    }

    @Test
    public void theTrainedPlayerDoesNotLoseVersusTheGoodPlayerWhileFirst(){
        ComputerPlayer randomPlayer = new ComputerPlayer("cpu", strategies::playPerfectly);
        assertThat(playFullGame(genetic, randomPlayer).winner(), not(randomPlayer));
    }

    @Test
    public void theTrainedPlayerDoesNotLoseVersusTheGoodPlayerWhileSecond(){
        ComputerPlayer untrainedPlayer = new ComputerPlayer("cpu", strategies::playPerfectly);
        assertThat(playFullGame(untrainedPlayer,genetic).winner(), not(untrainedPlayer));
    }

    @Test
    public void theTrainedPlayerDoesNotLoseVersusAnUntrainedPlayerWhileFirst(){
        Player untrainedPlayer = new GeneticPlayer(new Genome());
        assertThat(playFullGame(genetic, untrainedPlayer).winner(), not(untrainedPlayer));
    }

    @Test
    public void theTrainedPlayerDoesNotLoseVersusAnUntrainedPlayerWhileSecond(){
        Player untrainedPlayer = new GeneticPlayer(new Genome());
        assertThat(playFullGame(untrainedPlayer,genetic).winner(), not(untrainedPlayer));
    }

    private TicTacToe playFullGame(Player firstPlayer, Player secondPlayer) {
        TicTacToe gameVsRandom = new TicTacToe(firstPlayer, secondPlayer);
        while(!gameVsRandom.isOver()){
            gameVsRandom.playTurn();
        }
        return gameVsRandom;
    }



    @Test
    public void theModifiedGeneticPlayerPlaysTheCenterWhenPossibleAndTheresNoBetterMove() {
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


    private List<GameMove> specificPositions() {
        return game.freePositions().stream().filter(position -> strategies.opponentIsInCornerAndOppositeIsAvailable(game, game.currentMark(), position)).collect(Collectors.toList());
    }

    private List<GameMove> forkMoves(GameMarkings gameMarkings) {
        return game.freePositions().stream().filter(position -> strategies.wouldMakeFork(game, gameMarkings, position)).collect(Collectors.toList());
    }
    /****************************************************************************************************************************/
    private void playerMove(HumanPlayer player, GameMove gm, TicTacToe currentGame) {
        player.nextMove(gm);
        currentGame.playTurn();
    }


    private Genome generations10(){
        return createGenome(3.520454041502946, 3.599735546820601, 1.6576362892612222, 1.224443763486955, 1.1090778235941396, 1.3428931452649926, 3.237186524348633, 1.5324333136798431);
    }

    private Genome generations100() {
        return createGenome(3.690358274617251, 3.6186006037662217, 1.0952632743042683, 0.009560753580474035, 0.1448427767259095, 0.7115261459149513, 1.5761811563748953, 0.6011079662110967);
    }
    private Genome generations20() {
        return createGenome(3.256015867462282, 1.6225803055852577, -1.2545911690779374, -1.2419749349277367, -0.22067533629546476, -0.8813734946201812, -1.2637718766010504, -1.876387191759756);
    }

    private Genome generations30() {
        return createGenome(2.350776939990322, 5.418654039397558, 0.6315663171872528, 0.7619781030804105, 1.6886851000505114, -0.027901831223596574, -0.1918496705407302, -0.4425251406378774);
    }

    private Genome generations40() {
        return createGenome(5.407946221415687, 2.93857664082393, 2.601885251256733, -1.2782438408711498, 1.6304920241504561, -1.7337220278971965, 0.7299922529749452, -0.35742481910398594);
    }

    private Genome generations50() {
        return createGenome(3.5053597148063655, 3.9320130133443767, 3.092169150046624, 1.4503454716315671, 1.988629720346498, 0.1916419091288506, 0.1351172941981087, 2.129681249759186);
    }

    private Genome generations60(){
        return createGenome(6.473846359102173, 2.7029660841404137, 1.8168906213771177, -1.0770340331455068, 7.028988043866567, -0.641143757251303, -0.19289433207820617, -0.9496858954424017);
    }

    private Genome createGenome(double v, double v1, double v2, double v3, double v4, double v5, double v6, double v7) {
        return Genome.with(Arrays.asList(v, v1, - v2, v3, v4, v5, v6, v7));
    }

    private Genome generations500(){
        return createGenome(3.2729828160656833, 2.5036513089931063, 0.15314072543185642, 0.5998856693863792, 2.0790133311397385, 0.11069821047049189, 1.1847651224019433, 0.8006519112952915);
    }
}

