package org.butterbrot.ffb.stats;

import org.junit.Test;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.ReRollSource;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportScatterBall;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTurnEnd;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderPickUpTest extends AbstractTurnOverFinderTest {

    @Test
    public void findTurnoverFailedPickUp() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverFailedPickUpCatchByTeam() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, true, 3, 3));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverFailedPickUpCatchByOpponent() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, opponent, false, 3, 3));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverFailedPickUpNoCatch() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, teamMember, false, 3, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverFailedPickUpWithTeamReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverFailedPickUpWithLeaderReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverFailedPickUpWithSkillReRoll() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.SURE_FEET ));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, false, 3, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed pick up is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.PICK_UP_ROLL, turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void findTurnoverSuccessPickUp() throws Exception {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.PICK_UP_ROLL, actingPlayer, true, 3, 4));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful pick up is not a turnover", turnOverOpt.isPresent());
    }

}