package org.butterbrot.ffb.stats.turnover.bb2025;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.mechanics.PassResult;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportChainsawRoll;
import com.fumbbl.ffb.report.ReportConfusionRoll;
import com.fumbbl.ffb.report.ReportFoulAppearanceRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportInterceptionRoll;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.ReportSpecialEffectRoll;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import com.fumbbl.ffb.report.mixed.ReportProjectileVomit;
import com.fumbbl.ffb.report.mixed.ReportThrowAtStallingPlayer;
import com.fumbbl.ffb.report.mixed.ReportThrownKeg;
import com.fumbbl.ffb.report.mixed.ReportTurnEnd;
import com.fumbbl.ffb.skill.bb2025.BoneHead;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderMiscTest extends AbstractTurnOverFinderTest {

	@Test
	public void empty() {
		assertFalse("Empty queue cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void actionOnly() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		assertFalse("Action without dice cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void confusion() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportConfusionRoll(actingPlayer, false, 1, 2, false, new BoneHead()));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		assertFalse("Confusion rolls cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void foulAppearance() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportFoulAppearanceRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		assertFalse("Foul Appearance rolls   cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void touchDown() {
		turnOverFinder.add(new ReportTurnEnd(actingPlayer, null, null, new ArrayList<>(), 0));
		assertFalse("Touchdown is not a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void kickBack() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Kickback is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void failedVomit() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportProjectileVomit(actingPlayer, false, 1, 2, false, null));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Kickback is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void fireBallKnockDownOwnBallCarrier() {
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.FIREBALL, actingPlayer, 6, true));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fireballing your ball carrier is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertNull("For wizards there is no active player", turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(SpecialEffect.FIREBALL), turnOver.getAction());
		assertEquals("Wizard does not use minimum roll", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void fireBallKnockDownOwnPlayer() {
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.FIREBALL, actingPlayer, 6, true));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Fireballing your own player without the ball is a not turnover", turnOverOpt.isPresent());
	}

	@Test
	public void kegSuccess() {
		turnOverFinder.add(new ReportThrownKeg(actingPlayer, opponent, 2, false, false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Missed keg is a not turnover", turnOverOpt.isPresent());
	}

	@Test
	public void kegFumble() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportThrownKeg(actingPlayer, opponent, 2, false, true));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Keg hitting the thrower is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.THROWN_KEG), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void bombOpponent() {
		/*
		 [
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamGoblinKalimar4",
      "playerAction": "throwBomb"
    }
  ],
  [
    {
      "reportId": "passRoll",
      "playerId": "teamGoblinKalimar4",
      "successful": true,
      "roll": 6,
      "minimumRoll": 5,
      "reRolled": false,
      "passingDistance": "Short Pass",
      "passResult": "ACCURATE",
      "hailMaryPass": false,
      "bomb": true
    }
  ],
  [
    {
      "reportId": "scatterBall",
      "directionArray": [
        "South"
      ],
      "rolls": [
        5
      ],
      "gustOfWind": false
    }
  ],
  [
    {
      "reportId": "spellEffectRoll",
      "specialEffect": "bomb",
      "playerId": "teamHalflingBattleLore9",
      "roll": 6,
      "successful": true
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore9",
      "injuryType": "bomb",
      "armorBroken": true,
      "armorRoll": [
        5,
        5
      ],
      "injuryRoll": [
        6,
        2
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 5,
      "injuryDecay": null,
      "attackerId": null,
      "armorModifiers": [],
      "injuryModifiers": [],
      "casualtyModifiers": [],
      "skipInjuryParts": "CAS"
    },
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore9",
      "injuryType": "bomb",
      "armorBroken": true,
      "armorRoll": [
        5,
        5
      ],
      "injuryRoll": [
        6,
        2
      ],
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 5,
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
      "reportId": "apothecaryRoll",
      "playerId": "teamHalflingBattleLore9",
      "casualtyRoll": null,
      "playerState": null,
      "seriousInjury": null,
      "seriousInjuryOld": null,
      "casualtyModifiers": []
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
  ]
		 */
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
		turnOverFinder.add(regularPass(actingPlayer, 6, 5, true, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.BOMB, opponent, 6, true));
		turnOverFinder.add(new ReportInjury(opponent, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Bomb only hitting opponents is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void bombTeamMate() {
		/*
		  [
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamGoblinKalimar4",
      "playerAction": "throwBomb"
    }
  ],
  [
    {
      "reportId": "passRoll",
      "playerId": "teamGoblinKalimar4",
      "successful": true,
      "roll": 6,
      "minimumRoll": 4,
      "reRolled": false,
      "passingDistance": "Quick Pass",
      "passResult": "ACCURATE",
      "hailMaryPass": false,
      "bomb": true
    }
  ],
  [
    {
      "reportId": "scatterBall",
      "directionArray": [
        "North"
      ],
      "rolls": [
        1
      ],
      "gustOfWind": false
    }
  ],
  [
    {
      "reportId": "spellEffectRoll",
      "specialEffect": "bomb",
      "playerId": "teamGoblinKalimar11",
      "roll": 6,
      "successful": true
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamGoblinKalimar11",
      "injuryType": "bomb",
      "armorBroken": true,
      "armorRoll": [
        5,
        2
      ],
      "injuryRoll": [
        6,
        6
      ],
      "casualtyRoll": [
        6,
        2
      ],
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 6,
      "injuryDecay": null,
      "attackerId": null,
      "armorModifiers": [],
      "injuryModifiers": [
        "Stunty"
      ],
      "casualtyModifiers": [],
      "skipInjuryParts": "CAS"
    },
    {
      "reportId": "regenerationRoll",
      "playerId": "teamGoblinKalimar11",
      "successful": false,
      "roll": 2,
      "minimumRoll": 4,
      "reRolled": false
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamGoblinKalimar11",
      "injuryType": "bomb",
      "armorBroken": true,
      "armorRoll": [
        5,
        2
      ],
      "injuryRoll": [
        6,
        6
      ],
      "casualtyRoll": [
        6,
        2
      ],
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 6,
      "injuryDecay": null,
      "attackerId": null,
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
      "reportId": "apothecaryRoll",
      "playerId": "teamGoblinKalimar11",
      "casualtyRoll": null,
      "playerState": null,
      "seriousInjury": null,
      "seriousInjuryOld": null,
      "casualtyModifiers": []
    }
  ],
  [
    {
      "reportId": "spellEffectRoll",
      "specialEffect": "bomb",
      "playerId": "teamGoblinKalimar12",
      "roll": 1,
      "successful": false
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
		String secondTeamMate = "secondTeamMate";
		turnOverFinder.getHomePlayers().add(secondTeamMate);
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
		turnOverFinder.add(regularPass(actingPlayer, 6, 4, true, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.BOMB, teamMember, 6, true));
		turnOverFinder.add(new ReportInjury(teamMember, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.BOMB, secondTeamMate, 1, false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Bomb injuring a teammate is a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void bombTeamMateAfterIntercept() {
		/*
		[
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamGoblinKalimar4",
      "playerAction": "throwBomb"
    }
  ],
  [
    {
      "reportId": "passRoll",
      "playerId": "teamGoblinKalimar4",
      "successful": true,
      "roll": 6,
      "minimumRoll": 5,
      "reRolled": false,
      "passingDistance": "Short Pass",
      "passResult": "ACCURATE",
      "hailMaryPass": false,
      "bomb": true
    }
  ],
  [
    {
      "reportId": "interceptionRoll",
      "playerId": "teamHalflingBattleLore6",
      "successful": true,
      "roll": 6,
      "minimumRoll": 7,
      "reRolled": false,
      "rollModifiers": [
        "1 Tacklezone",
        "Accurate Pass"
      ],
      "bomb": true,
      "ignoreAgility": false
    }
  ],
  [
    {
      "reportId": "passRoll",
      "playerId": "teamHalflingBattleLore6",
      "successful": true,
      "roll": 6,
      "minimumRoll": 6,
      "reRolled": false,
      "rollModifiers": [
        "1 Tacklezone"
      ],
      "passingDistance": "Short Pass",
      "passResult": "ACCURATE",
      "hailMaryPass": false,
      "bomb": true
    }
  ],
  [
    {
      "reportId": "scatterBall",
      "directionArray": [
        "North"
      ],
      "rolls": [
        1
      ],
      "gustOfWind": false
    }
  ],
  [
    {
      "reportId": "spellEffectRoll",
      "specialEffect": "bomb",
      "playerId": "teamGoblinKalimar7",
      "roll": 6,
      "successful": true
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamGoblinKalimar7",
      "injuryType": "bomb",
      "armorBroken": false,
      "armorRoll": [
        1,
        4
      ],
      "injuryRoll": null,
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 3,
      "injuryDecay": null,
      "attackerId": null,
      "armorModifiers": [],
      "injuryModifiers": [],
      "casualtyModifiers": [],
      "skipInjuryParts": "CAS"
    },
    {
      "reportId": "injury",
      "defenderId": "teamGoblinKalimar7",
      "injuryType": "bomb",
      "armorBroken": false,
      "armorRoll": [
        1,
        4
      ],
      "injuryRoll": null,
      "casualtyRoll": null,
      "seriousInjury": null,
      "casualtyRollDecay": null,
      "seriousInjuryDecay": null,
      "seriousInjuryOld": null,
      "injury": 3,
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
      "reportId": "turnEnd",
      "playerIdTouchdown": null,
      "knockoutRecoveryArray": [],
      "heatExhaustionArray": [],
      "unzapArray": [],
      "heatRoll": 0
    }
  ],
		 */
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
		turnOverFinder.add(regularPass(actingPlayer, 6, 5, true, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 7, false, null, true, false));
		turnOverFinder.add(regularPass(opponent, 6, 6, true, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.BOMB, teamMember, 6, true));
		turnOverFinder.add(new ReportInjury(teamMember, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Bombing a teammate after interception is a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void stalling() {
		/*
		[
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamHalflingBattleLore6",
      "playerAction": "forgo"
    }
  ],
  [
    {
      "reportId": "throwAtStallingPlayer",
      "playerId": "teamHalflingBattleLore6",
      "roll": 6,
      "successful": true
    }
  ],
  [
    {
      "reportId": "injury",
      "defenderId": "teamHalflingBattleLore6",
      "injuryType": "throwARock",
      "armorBroken": true,
      "armorRoll": [
        6,
        5
      ],
      "injuryRoll": [
        1,
        1
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
      "defenderId": "teamHalflingBattleLore6",
      "injuryType": "throwARock",
      "armorBroken": true,
      "armorRoll": [
        6,
        5
      ],
      "injuryRoll": [
        1,
        1
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
      "reportId": "scatterBall",
      "directionArray": [
        "East"
      ],
      "rolls": [
        3
      ],
      "gustOfWind": false
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
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FORGO));
		turnOverFinder.add(new ReportThrowAtStallingPlayer(actingPlayer, 6, true));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Stalling player hit by a rock is a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void stallingMisses() {
		/*
		[
    {
      "reportId": "playerAction",
      "actingPlayerId": "teamHalflingBattleLore6",
      "playerAction": "forgo"
    }
  ],
  [
    {
      "reportId": "throwAtStallingPlayer",
      "playerId": "teamHalflingBattleLore6",
      "roll": 1,
      "successful": false
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
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FORGO));
		turnOverFinder.add(new ReportThrowAtStallingPlayer(actingPlayer, 1, false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Missing the stalling player is not a turnover", turnOverOpt.isPresent());
	}
}
