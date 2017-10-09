package org.butterbrot.ffb.stats.turnover;

import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import com.balancedbytes.games.ffb.report.ReportScatterBall;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import org.butterbrot.ffb.stats.adapter.TurnOverDescription;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderLeapTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedLeap() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLeapCatchByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 3, 3, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLeapCatchByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, false, 3, 3, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLeapNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportInjury(actingPlayer, null,false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 2, 3, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLeapWithTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL, true, 6));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, true));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLeapWithProReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO, true, 6));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, true));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLeapWithLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, false));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER, true, 6));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, false, 2, 3, true));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.LEAP_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successLeap() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.LEAP_ROLL, actingPlayer, true, 4, 3, false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful leap is not a turnover", turnOverOpt.isPresent());
    }

}