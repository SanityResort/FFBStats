package org.butterbrot.ffb.stats;

import org.junit.Test;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.ReRollSource;
import refactored.com.balancedbytes.games.ffb.SpecialEffect;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;
import refactored.com.balancedbytes.games.ffb.report.ReportInterceptionRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportPassRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportScatterBall;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTurnEnd;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderPassCatchTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedPassFumble() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleCatchByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleCatchByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, true, 4, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleSafeThrow() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, true, true, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Safe throw prevents turnover", turnOverOpt.isPresent());
    }

    @Test
    public void failedPassFumbleSafeThrowFails() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, true, true, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, false, 3, 4));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed Safe throw does not prevent turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrow() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPassEmpty() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 3, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught accurate pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("No minimum roll for pass to empty square", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowCatchByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, false, 4, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Caught missed pass by opponent is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowCatchByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Caught missed pass is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void failedPassMissedThrowNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 2, 3, false, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPass() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void successPassMissedCatchCaughtByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, true, 4, 5));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPassMissedCatchCaughtByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 5));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Dropped catch caught by team is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void successPassMissedCatchNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPassMissedCatchTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPassMissedCatchLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPassMissedCatchProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPassMissedCatchSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.CATCH));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successHandoff() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void missedHandoffCaughtByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, true, 4, 5));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffCaughtByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 5));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Dropped catch caught by team is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void missedHandoffNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.CATCH));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successHandoffMove() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void missedHandoffMoveCaughtByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, true, 4, 5));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffMoveCaughtByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 5));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Dropped catch caught by team is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void missedHandoffMoveNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffMoveTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffMoveLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHandoffMoveProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void missedHamdoffMoveSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.CATCH));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CATCH_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleFromDumpOff() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLITZ));
        turnOverFinder.add(ReportPassRoll.regularPass(opponent, false, 1, 3, true, false, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Fumble from opponent player is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void interceptedSuccessfully() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the turnover action", ReportId.INTERCEPTION_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void interceptedSuccessfullyWithCatchReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, false, 5, 6, false));
        turnOverFinder.add(new ReportReRoll(opponent, ReRollSource.CATCH));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the turnover action", ReportId.INTERCEPTION_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void interceptedSuccessfullyWithProReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, false, 5, 6, false));
        turnOverFinder.add(new ReportReRoll(opponent, ReRollSource.PRO));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the turnover action", ReportId.INTERCEPTION_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void interceptionCancelledWithReroll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportReRoll(opponent, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Cancelled interception is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void interceptionCancelled() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Cancelled interception is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void hailMary() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, false, 2, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught hail mary pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("Hail Mary was successful but still a turnover so we use 0", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void hailMaryReRolled() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportReRoll());
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, false, 2, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught hail mary pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("Hail Mary was successful but still a turnover so we use 0", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void hailMaryFumble() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void hailMaryFumbleSkillReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void hailMaryFumbleProReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void hailMaryFumbleTeamReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void hailMaryFumbleLeaderReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(ReportPassRoll.hailMaryPass(actingPlayer, true, 1, false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void bombOpponentOnlyDown() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, true));
        turnOverFinder.add(new ReportInjury(opponent, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", SpecialEffect.BOMB.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void bombTeamDown() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4, 3, false, false, true));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", SpecialEffect.BOMB.getTurnOverDesc(), turnOver.getAction());
        assertEquals("No minimum roll for failed bomb", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void bombFumbleReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, true));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false, 1, 3, true, false, true));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", SpecialEffect.BOMB.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }
}