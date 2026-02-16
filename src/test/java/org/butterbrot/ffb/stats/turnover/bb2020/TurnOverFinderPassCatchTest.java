package org.butterbrot.ffb.stats.turnover.bb2020;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.mechanics.PassResult;
import com.fumbbl.ffb.modifiers.CatchModifier;
import com.fumbbl.ffb.report.ReportCatchRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportInterceptionRoll;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.report.mixed.ReportCloudBurster;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import com.fumbbl.ffb.report.mixed.ReportTurnEnd;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class TurnOverFinderPassCatchTest extends AbstractTurnOverFinderTest {

	@Test
	public void failedPassFumble() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3, true, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleCatchByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3, true, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleCatchByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 4, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3,  false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PASS, true, 6));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3, false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3, false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleSafePass() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3, false, false, PassResult.SAVED_FUMBLE));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Safe Pass prevents turnover", turnOverOpt.isPresent());
	}

	@Test
	public void failedPassMissedThrow() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassEmpty() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 3, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Uncaught accurate pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("No minimum roll for pass to empty square", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassToOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  3, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 3, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Pass to opponent is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("No minimum roll for pass to empty square", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successHandoffToOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 3, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Handoff to opponent is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.HAND_OVER), turnOver.getAction());
		assertEquals("No minimum roll for pass to empty square", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassMissedThrowCatchByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 2, 3, false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Caught missed pass by opponent is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassMissedThrowCatchByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Caught missed pass is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void failedPassMissedThrowNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassMissedThrowSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3, false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PASS, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, true, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassMissedThrowProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3, false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(regularPass(actingPlayer, 2, 3,  false, true, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassMissedThrowTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3,  false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, true, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassMissedThrowLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, true, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassWAMissedThrowCatchByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 2, 3, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Caught missed pass by opponent is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassWAMissedThrowCatchByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Caught missed pass is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void failedPassWAMissedThrowNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Uncaught missed pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassWAMissedThrowSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PASS, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassWAMissedThrowProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(regularPass(actingPlayer, 2, 3,  false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassWAMissedThrowTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 1, 3,  false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassWAMissedThrowLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  1, 3,  false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(regularPass(actingPlayer,  2, 3,  false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}
	@Test
	public void successPass() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void successPassMissedCatchCaughtByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  4, 3, false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 5, 5, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassMissedCatchCaughtByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 5, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Dropped catch caught by team is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void successPassMissedCatchNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 3, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassMissedCatchTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassMissedCatchLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer,  4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassMissedCatchProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successPassMissedCatchSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(regularPass(actingPlayer, 4, 3,  false, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.CATCH, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successHandoff() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void missedHandoffCaughtByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 5, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffCaughtByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 5, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Dropped catch caught by team is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void missedHandoffNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.CATCH, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void successHandoffMove() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 3, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Successful pass is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void missedHandoffMoveCaughtByOpponent() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 5, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffMoveCaughtByTeam() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 5, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Dropped catch caught by team is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void missedHandoffMoveNoCatch() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffMoveTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffMoveLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffMoveProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void missedHandoffMoveSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.HAND_OVER_MOVE));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, false, new CatchModifier[0], false));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.CATCH, true, 6));
		turnOverFinder.add(new ReportCatchRoll(teamMember, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Dropped catch is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", teamMember, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.CATCH_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 4, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void failedPassFumbleFromDumpOff() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLITZ));
		turnOverFinder.add(regularPass(opponent, 1, 3,  false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Fumble from opponent player is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void deflectionCaughtByTeamMate() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false, null, false, false));
		turnOverFinder.add(new ReportCatchRoll(opponent, false, 1, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportCatchRoll(teamMember, true, 4, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Deflected pass caught by team-mate is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void interceptedSuccessfully() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false, null, false, false));
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 4, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the turnover action", turnOverDescription.get(ReportId.INTERCEPTION_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void deflectedOnly() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false, null, false, false));
		turnOverFinder.add(new ReportCatchRoll(opponent, false, 2, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the turnover action", turnOverDescription.get(ReportId.INTERCEPTION_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void interceptedSuccessfullyWithCatchReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, false, 5, 6, false, null, false, false));
		turnOverFinder.add(new ReportReRoll(opponent, ReRollSources.CATCH, true, 6));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, true, null, false, false));
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 4, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the turnover action", turnOverDescription.get(ReportId.INTERCEPTION_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void interceptedSuccessfullyWithProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, false, 5, 6, false, null, false, false));
		turnOverFinder.add(new ReportReRoll(opponent, ReRollSources.PRO, true, 6));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, true, null, false, false));
		turnOverFinder.add(new ReportCatchRoll(opponent, true, 4, 4, true, new CatchModifier[0], false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the turnover action", turnOverDescription.get(ReportId.INTERCEPTION_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void interceptionNotCancelledSuccessfully() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false, null, false, false));
		turnOverFinder.add(new ReportCloudBurster(actingPlayer, opponent, actingTeam));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false, null, false, false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Intercepted pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the team member set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the turnover action", turnOverDescription.get(ReportId.INTERCEPTION_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 6, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void interceptionCancelled() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, true, 6, 6, false, null, false, false));
		turnOverFinder.add(new ReportCloudBurster(actingPlayer, opponent, actingTeam));
		turnOverFinder.add(new ReportInterceptionRoll(opponent, false, 2, 6, false, null, false, false));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Cancelled interception is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void hailMary() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 2, false, false, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Uncaught hail mary pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("Hail Mary was successful but still a turnover so we use 0", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryReRolled() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll());
		turnOverFinder.add(hailMaryPass(actingPlayer, 2, false, true, PassResult.INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Uncaught hail mary pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("Hail Mary was successful but still a turnover so we use 0", 0, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryFumble() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryFumbleSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PASS, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryFumbleProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryFumbleTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryFumbleLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.FUMBLE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.FUMBLE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryWildlyInaccurate() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertFalse("Was not rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryWildlyInaccurateSkillReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PASS, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryWildlyInaccurateProReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.PRO, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryWildlyInaccurateTeamReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void hailMaryWildlyInaccurateLeaderReRoll() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.PASS));
		turnOverFinder.add(hailMaryPass(actingPlayer, 1, false, false, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
		turnOverFinder.add(hailMaryPass(actingPlayer,  1, false, true, PassResult.WILDLY_INACCURATE));
		turnOverFinder.add(new ReportScatterBall());
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertTrue("Fumbled pass is a turnover", turnOverOpt.isPresent());
		TurnOver turnOver = turnOverOpt.get();
		assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
		assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.PASS_ROLL), turnOver.getAction());
		assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
		assertTrue("Was rerolled", turnOver.isReRolled());
		assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
	}

	@Test
	public void bombOpponentOnlyDown() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
		turnOverFinder.add(regularPass(actingPlayer, 4, 3,  true, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportInjury(opponent, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Bomb only hitting opponents is not a turnover", turnOverOpt.isPresent());
	}

	@Test
	public void bombTeamDown() {
		turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.THROW_BOMB));
		turnOverFinder.add(regularPass(actingPlayer,  4, 3,  true, false, PassResult.ACCURATE));
		turnOverFinder.add(new ReportInjury(teamMember, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
		turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>(), 0));
		Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
		assertFalse("Bomb hitting teammates is not a turnover", turnOverOpt.isPresent());
	}
}