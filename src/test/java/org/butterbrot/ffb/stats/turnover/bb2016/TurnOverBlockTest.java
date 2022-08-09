package org.butterbrot.ffb.stats.turnover.bb2016;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.report.ReportBlockRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.bb2016.ReportInjury;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TurnOverBlockTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedBlock1D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock2D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[2]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock3D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[3]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock2DAgainst() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(opponentTeam, new int[2]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", -2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock3DAgainst() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(opponentTeam, new int[3]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", -3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock1DProReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock1DTeamReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlock1DLeaderReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER,true, 6));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlitz1D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLITZ));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBlitzMove1D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLITZ_MOVE));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get( ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedStandupBlitz1D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.STAND_UP_BLITZ));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 1, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedFrenzyBlock() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[2]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedMultiBlock() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MULTIPLE_BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(opponent, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[2]));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed block is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOCK), turnOver.getAction());
        assertEquals("TurnOver must show the amount of block dice", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successBlock1D() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportBlockRoll(actingTeam, new int[1]));
        turnOverFinder.add(new ReportInjury(opponent, null, false, null, null, null, null, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful block is not a turnover", turnOverOpt.isPresent());
    }
}
