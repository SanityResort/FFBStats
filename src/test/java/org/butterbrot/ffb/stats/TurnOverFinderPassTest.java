package org.butterbrot.ffb.stats;

import org.junit.Test;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.ReRollSource;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
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

public class TurnOverFinderPassTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedPassFumble() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleCatchByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 4, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleCatchByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, true, 4, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassFumbleSafeThrow() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, true, true,false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, true, 3 , 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Safe throw prevents turnover", turnOverOpt.isPresent());
    }

    @Test
    public void failedPassFumbleSafeThrowFails() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, true, true,false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.SAFE_THROW_ROLL, actingPlayer, true, 3 , 4));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed Safe throw does not prevent turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrow() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowCatchByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, false, 4, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowCatchByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 4, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }
    
    @Test
    public void failedPassMissedThrowSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PASS));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedPassMissedThrowLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  1, 3, true,false,false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, false,  2, 3, false,false,false));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PASS_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successPass() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(ReportPassRoll.regularPass(actingPlayer, true, 4,3, false,false,false));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
    }

}