package org.butterbrot.ffb.stats.turnover.bb2020;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportChainsawRoll;
import com.fumbbl.ffb.report.ReportConfusionRoll;
import com.fumbbl.ffb.report.ReportFoulAppearanceRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.ReportSpecialEffectRoll;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import com.fumbbl.ffb.report.mixed.ReportProjectileVomit;
import com.fumbbl.ffb.report.mixed.ReportThrownKeg;
import com.fumbbl.ffb.report.mixed.ReportTurnEnd;
import com.fumbbl.ffb.skill.bb2020.BoneHead;
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
}
