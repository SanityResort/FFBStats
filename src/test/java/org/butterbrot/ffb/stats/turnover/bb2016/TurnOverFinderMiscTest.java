package org.butterbrot.ffb.stats.turnover.bb2016;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportChainsawRoll;
import com.fumbbl.ffb.report.ReportConfusionRoll;
import com.fumbbl.ffb.report.ReportFoulAppearanceRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.ReportSpecialEffectRoll;
import com.fumbbl.ffb.report.bb2016.ReportInjury;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import com.fumbbl.ffb.skill.bb2016.BoneHead;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class TurnOverFinderMiscTest extends AbstractTurnOverFinderTest {

	@Test
	public void empty() {
		assertFalse("Empty queue cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void actionOnly() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		assertFalse("Action without dice cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void confusion() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportConfusionRoll(actingPlayer, false, 1, 2, false, new BoneHead()));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		assertFalse("Confusion rolls cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void foulAppearance() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportFoulAppearanceRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		assertFalse("Foul Appearance rolls   cannot create a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void touchDown() {
		turnOverFinder.add(new ReportTurnEnd(actingPlayer, null, null, new ArrayList<>()));
		assertFalse("Touchdown is not a turn over", turnOverFinder.findTurnover().isPresent());
	}

	@Test
	public void kickBack() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CHAINSAW_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void kickBackNoAvBreak() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Kickback without armour break is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void kickBackWithLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CHAINSAW_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void kickBackWithTeamReroll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CHAINSAW_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void kickBackProReroll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportChainsawRoll(actingPlayer, false, 1, 2, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, true, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CHAINSAW_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void fireBallKnockDownOwnBallCarrier() {
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.FIREBALL, actingPlayer, 6, true));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
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
	public void lightningBoltKnockDownOwnBallCarrier() {
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.LIGHTNING, actingPlayer, 6, true));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Bolting your ball carrier is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertNull("For wizards there is no active player", turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(SpecialEffect.LIGHTNING), turnOver.getAction());
		assertEquals("Wizard does not use minimum roll", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void fireBallKnockDownOwnPlayer() {
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.FIREBALL, actingPlayer, 6, true));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Fireballing your own player without the ball is a not turnover", turnOverOpt.isPresent());
	}

	@Test
	public void lightningBoltKnockDownOwnPlayer() {
		turnOverFinder.setHomeTeamActive(true);
		turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.LIGHTNING, actingPlayer, 6, true));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Bolting your own player without the ball is a not turnover", turnOverOpt.isPresent());
	}
}
