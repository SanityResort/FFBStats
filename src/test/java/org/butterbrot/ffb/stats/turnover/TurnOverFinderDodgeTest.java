package org.butterbrot.ffb.stats.turnover;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.modifiers.CatchModifier;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportCatchRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.bb2016.ReportDodgeRoll;
import com.fumbbl.ffb.report.bb2016.ReportInjury;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import org.butterbrot.ffb.stats.adapter.TurnOverDescription;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class TurnOverFinderDodgeTest extends AbstractTurnOverFinderTest {

	@Test
	public void failedDodge() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeCatchByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 3, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeCatchByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, false, 3, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeWithTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeWithLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeWithSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.DODGE, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeAgainstDT() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 4, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 0, 5, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 5, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeAgainstDTWithTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 4, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 0, 5, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 5, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeAgainstDTWithLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 4, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 0, 5, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 5, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}


	@Test
	public void failedDodgeAgainstDTWithProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 4, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 0, 5, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 5, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedDodgeAgainstDTWithSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.DODGE, true, 6));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 4, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, false, 0, 5, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed dodge is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.DODGE_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 5, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successDodge() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportDodgeRoll(actingPlayer, true, 4, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Successful dodge is not a turnover", turnOverOpt.isPresent());
	}

}