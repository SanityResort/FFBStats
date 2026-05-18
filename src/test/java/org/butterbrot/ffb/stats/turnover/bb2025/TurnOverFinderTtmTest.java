package org.butterbrot.ffb.stats.turnover.bb2025;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.Direction;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.PassingDistance;
import com.fumbbl.ffb.mechanics.PassResult;
import com.fumbbl.ffb.modifiers.CatchModifier;
import com.fumbbl.ffb.modifiers.PassModifier;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportAlwaysHungryRoll;
import com.fumbbl.ffb.report.ReportCatchRoll;
import com.fumbbl.ffb.report.ReportConfusionRoll;
import com.fumbbl.ffb.report.ReportEscapeRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.ReportRightStuffRoll;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.ReportScatterPlayer;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import com.fumbbl.ffb.report.mixed.ReportPlayerEvent;
import com.fumbbl.ffb.report.mixed.ReportThrowTeamMateRoll;
import com.fumbbl.ffb.report.mixed.ReportTurnEnd;
import com.fumbbl.ffb.skill.bb2025.ReallyStupid;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderTtmTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedLandingWithBall() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithBallWithTeamReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSources.TEAM_RE_ROLL, true, 6));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, true, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember,null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithBallWithLeaderReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSources.LEADER, true, 6));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, true, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithBallWithProReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSources.PRO, true, 6));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithoutBall() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Failed landing without ball is no turnover", turnOverOpt.isPresent());
    }

    @Test
    public void failedLandingWithBallCatchByTeam() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportCatchRoll(actingPlayer, true, 3, 3, false, new CatchModifier[0], false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failed landing with ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithoutBallHitTeammate() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 4, 1, false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportInjury(actingPlayer, null,false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Hitting teammate is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("No minimum roll for hitting a player", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedLandingWithoutBallHitOpponent() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 4, 1,false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember,null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportInjury(opponent,null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Hitting opponent is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.RIGHT_STUFF_ROLL), turnOver.getAction());
        assertEquals("No minimum roll for hitting a player", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBall(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportConfusionRoll(actingPlayer, true, 4, 4, false, new ReallyStupid()));
        turnOverFinder.add(new ReportAlwaysHungryRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.ESCAPE_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBallWithTeamReRoll(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportConfusionRoll(actingPlayer, true, 4, 4, false, new ReallyStupid()));
        turnOverFinder.add(new ReportAlwaysHungryRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSources.TEAM_RE_ROLL, true, 6));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.ESCAPE_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBallWithLeaderReRoll(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportConfusionRoll(actingPlayer, true, 4, 4, false, new ReallyStupid()));
        turnOverFinder.add(new ReportAlwaysHungryRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSources.LEADER, true, 6));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.ESCAPE_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void eatenWithBallWithProReRoll(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportConfusionRoll(actingPlayer, true, 4, 4, false, new ReallyStupid()));
        turnOverFinder.add(new ReportAlwaysHungryRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(teamMember, ReRollSources.PRO, true, 6));
        turnOverFinder.add(new ReportEscapeRoll(teamMember, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Eating the teammate with the ball is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the teamMember set as active player", teamMember, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.ESCAPE_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void landingOnTeamMate() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportThrowTeamMateRoll(actingPlayer, true, 6, 2, false, new PassModifier[0], PassingDistance.SHORT_PASS, teamMember, PassResult.ACCURATE, false));
        turnOverFinder.add(new ReportScatterPlayer(new FieldCoordinate(9, 2), new FieldCoordinate(12, 2), new Direction[]{Direction.EAST, Direction.EAST, Direction.EAST}, new int[]{3, 3, 3}, true));
        turnOverFinder.add(new ReportPlayerEvent(actingPlayer, "was hit"));
        turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterPlayer(new FieldCoordinate(12, 2), new FieldCoordinate(12, 1), new Direction[]{Direction.NORTH}, new int[]{1}, false));
        turnOverFinder.add(new ReportInjury(teamMember, actingPlayer, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Landing on a teammate is a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void failedLanding() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_TEAM_MATE));
        turnOverFinder.add(new ReportConfusionRoll(actingPlayer, true, 6, 2, false, new ReallyStupid()));
        turnOverFinder.add(new ReportAlwaysHungryRoll(actingPlayer, true, 6, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportThrowTeamMateRoll(actingPlayer, true, 6, 5, false, new PassModifier[0], PassingDistance.SHORT_PASS, teamMember, PassResult.ACCURATE, false));
        turnOverFinder.add(new ReportScatterPlayer(new FieldCoordinate(7, 6), new FieldCoordinate(7, 3), new Direction[]{Direction.NORTH, Direction.NORTH, Direction.NORTH}, new int[]{1, 1, 1}, true));
        turnOverFinder.add(new ReportRightStuffRoll(teamMember, false, 1, 3, false, new RollModifier[0]));
        turnOverFinder.add(new ReportInjury(teamMember, actingPlayer, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Failed landing without ball scatter is not a turnover", turnOverOpt.isPresent());
    }
}
