package org.butterbrot.ffb.stats.turnover.bb2016;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.modifiers.CatchModifier;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportCatchRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportJumpRoll;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.bb2016.ReportInjury;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class TurnOverFinderLeapTest extends AbstractTurnOverFinderTest {

	@Test
	public void failedLeap() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedLeapCatchByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 3, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedLeapCatchByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, false, 3, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedLeapNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedLeapWithTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedLeapWithProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedLeapWithLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, false, 2, 3, true, new RollModifier[0]));
		turnOverFinder.add(new ReportInjury(actingPlayer, null, false, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Failed leap is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.JUMP_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successLeap() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
		turnOverFinder.add(new ReportJumpRoll(actingPlayer, true, 4, 3, false, new RollModifier[0]));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Successful leap is not a turnover", turnOverOpt.isPresent());
	}

}