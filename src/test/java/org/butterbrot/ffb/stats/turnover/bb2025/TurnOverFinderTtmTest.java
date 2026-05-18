package org.butterbrot.ffb.stats.turnover.bb2025;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.modifiers.CatchModifier;
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
import com.fumbbl.ffb.report.mixed.ReportInjury;
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
        /*
          [
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamHalflingBattleLore2",
      "playerAction": "throwTeamMateMove"
    }
  ],
  [
    {
      "reportId": "throwTeamMateRoll",
      "playerId": "teamHalflingBattleLore2",
      "successful": true,
      "roll": 6,
      "minimumRoll": 2,
      "reRolled": false,
      "rollModifiers": [
        "Strong Arm"
      ],
      "thrownPlayerId": "teamHalflingBattleLore9",
      "passingDistance": "Short Pass",
      "passResult": "ACCURATE",
      "kicked": false
    }
  ],
  [
    {
      "reportId": "scatterPlayer",
      "startCoordinate": [
        9,
        2
      ],
      "endCoordinate": [
        12,
        2
      ],
      "directionArray": [
        "East",
        "East",
        "East"
      ],
      "rolls": [
        3,
        3,
        3
      ],
      "isScatter": true
    }
  ],
  [
    {
      "reportId": "playerEvent",
      "playerId": "teamHalflingBattleLore4",
      "message": "was hit"
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore4",
      "injuryType": "ttmHitPlayer",
      "armorBroken": true,
      "armorRoll": [
        4,
        5
      ],
      "injuryRoll": [
        1,
        5
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 4,
      "injuryDecay": null,
      "attackerId": null,
      "armorModifiers": [],
      "injuryModifiers": [],
      "casualtyModifiers": [],
      "skipInjuryParts": "CAS"
    },
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore4",
      "injuryType": "ttmHitPlayer",
      "armorBroken": true,
      "armorRoll": [
        4,
        5
      ],
      "injuryRoll": [
        1,
        5
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 4,
      "injuryDecay": null,
      "attackerId": null,
      "armorModifiers": [],
      "injuryModifiers": [],
      "casualtyModifiers": [],
      "skipInjuryParts": "EVERYTHING_BUT_CAS"
    }
  ],
  [
    {
      "reportId": "scatterPlayer",
      "startCoordinate": [
        12,
        2
      ],
      "endCoordinate": [
        12,
        1
      ],
      "directionArray": [
        "North"
      ],
      "rolls": [
        1
      ],
      "isScatter": false
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore9",
      "injuryType": "ttmLanding",
      "armorBroken": true,
      "armorRoll": [
        5,
        4
      ],
      "injuryRoll": [
        2,
        5
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 4,
      "injuryDecay": null,
      "attackerId": "teamHalflingBattleLore2",
      "armorModifiers": [],
      "injuryModifiers": [],
      "casualtyModifiers": [],
      "skipInjuryParts": "CAS"
    },
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore9",
      "injuryType": "ttmLanding",
      "armorBroken": true,
      "armorRoll": [
        5,
        4
      ],
      "injuryRoll": [
        2,
        5
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 4,
      "injuryDecay": null,
      "attackerId": "teamHalflingBattleLore2",
      "armorModifiers": [],
      "injuryModifiers": [],
      "casualtyModifiers": [],
      "skipInjuryParts": "EVERYTHING_BUT_CAS"
    }
  ],
  [
    {
      "reportId": "turnEnd",
      "playerIdTouchdown": null,
      "knockoutRecoveryArray": [],
      "heatExhaustionArray": [],
      "unzapArray": [],
      "heatRoll": 0
    }
  ],
         */
    }

    @Test
    public void failedLanding() {
        /*
         [
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamGoblinKalimar2",
      "playerAction": "throwTeamMateMove"
    }
  ],
  [
    {
      "reportId": "confusionRoll",
      "playerId": "teamGoblinKalimar2",
      "successful": true,
      "roll": 6,
      "minimumRoll": 2,
      "reRolled": false,
      "confusionSkill": "Really Stupid"
    }
  ],
  [
    {
      "reportId": "alwaysHungryRoll",
      "playerId": "teamGoblinKalimar2",
      "successful": true,
      "roll": 6,
      "minimumRoll": 2,
      "reRolled": false
    }
  ],
  [
    {
      "reportId": "throwTeamMateRoll",
      "playerId": "teamGoblinKalimar2",
      "successful": true,
      "roll": 6,
      "minimumRoll": 5,
      "reRolled": false,
      "rollModifiers": [
        "2 Tacklezones"
      ],
      "thrownPlayerId": "teamGoblinKalimar9",
      "passingDistance": "Short Pass",
      "passResult": "ACCURATE",
      "kicked": false
    }
  ],
  [
    {
      "reportId": "scatterPlayer",
      "startCoordinate": [
        7,
        6
      ],
      "endCoordinate": [
        7,
        3
      ],
      "directionArray": [
        "North",
        "North",
        "North"
      ],
      "rolls": [
        1,
        1,
        1
      ],
      "isScatter": true
    }
  ],
  [
    {
      "reportId": "rightStuffRoll",
      "playerId": "teamGoblinKalimar9",
      "successful": false,
      "roll": 1,
      "minimumRoll": 3,
      "reRolled": false
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamGoblinKalimar9",
      "injuryType": "ttmLanding",
      "armorBroken": true,
      "armorRoll": [
        5,
        2
      ],
      "injuryRoll": [
        2,
        2
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 4,
      "injuryDecay": null,
      "attackerId": "teamGoblinKalimar2",
      "armorModifiers": [],
      "injuryModifiers": [
        "Stunty"
      ],
      "casualtyModifiers": [],
      "skipInjuryParts": "CAS"
    },
    {
      "reportId": "injury",
      "defenderId": "teamGoblinKalimar9",
      "injuryType": "ttmLanding",
      "armorBroken": true,
      "armorRoll": [
        5,
        2
      ],
      "injuryRoll": [
        2,
        2
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 4,
      "injuryDecay": null,
      "attackerId": "teamGoblinKalimar2",
      "armorModifiers": [],
      "injuryModifiers": [
        "Stunty"
      ],
      "casualtyModifiers": [],
      "skipInjuryParts": "EVERYTHING_BUT_CAS"
    }
  ],
  [
    {
      "reportId": "turnEnd",
      "playerIdTouchdown": null,
      "knockoutRecoveryArray": [],
      "heatExhaustionArray": [],
      "unzapArray": [],
      "heatRoll": 0
    }
  ],
         */
    }
}
