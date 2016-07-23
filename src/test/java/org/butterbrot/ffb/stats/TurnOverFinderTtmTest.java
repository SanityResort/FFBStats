package org.butterbrot.ffb.stats;

import org.junit.Test;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.ReRollSource;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportScatterBall;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTurnEnd;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderTtmTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedLandingWithBall() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithBallWithTeamReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithBallWithLeaderReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithBallWithProReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSource.PRO));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithoutBall() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Failed landing without ball is no turnover", turnOverOpt.isPresent());
    }

    @Test
    public void failedLandingWithBallCatchByTeam() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportSkillRoll(ReportId.CATCH_ROLL, actingPlayer, true, 3, 3));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithoutBallHitTeammate() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportInjury(actingPlayer, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Hitting teammate is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.RIGHT_STUFF_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("No minimum roll for hitting a player", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithoutBallHitOpponent() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.RIGHT_STUFF_ROLL, teamMember, false, 4, 1));
        turnOverFinder.add(new ReportInjury(teamMember, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportInjury(opponent, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Hitting opponent without ball is no turnover", turnOverOpt.isPresent());
    }

    @Test
    public void eatenWithBall(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CONFUSION_ROLL, actingPlayer, true, 4, 4));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ALWAYS_HUNGRY_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.ESCAPE_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBallWithTeamReRoll(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CONFUSION_ROLL, actingPlayer, true, 4, 4));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ALWAYS_HUNGRY_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.ESCAPE_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBallWithLeaderReRoll(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CONFUSION_ROLL, actingPlayer, true, 4, 4));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ALWAYS_HUNGRY_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.ESCAPE_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBallWithProReRoll(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CONFUSION_ROLL, actingPlayer, true, 4, 4));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ALWAYS_HUNGRY_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSource.PRO));
        turnOverFinder.add(new ReportSkillRoll(ReportId.ESCAPE_ROLL, teamMember, false, 2, 1));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.ESCAPE_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }
}
