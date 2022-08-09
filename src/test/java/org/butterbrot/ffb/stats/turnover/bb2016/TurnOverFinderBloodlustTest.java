package org.butterbrot.ffb.stats.turnover.bb2016;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.ReRollSources;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportBiteSpectator;
import com.fumbbl.ffb.report.ReportBloodLustRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.ReportReRoll;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderBloodlustTest extends AbstractTurnOverFinderTest {

    @Test
    public void failedBloodlust() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportBloodLustRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportBiteSpectator(actingPlayer));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failing to feed is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOOD_LUST_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBloodlustWithTeamReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportBloodLustRoll(actingPlayer, false, 1, 2,false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.TEAM_RE_ROLL, true, 6));
        turnOverFinder.add(new ReportBloodLustRoll(actingPlayer, false, 1, 2,true, new RollModifier[0]));
        turnOverFinder.add(new ReportBiteSpectator(actingPlayer));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failing to feed is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOOD_LUST_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void failedBloodlustWithLeaderReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportBloodLustRoll(actingPlayer, false, 1, 2, false, new RollModifier[0]));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSources.LEADER, true, 6));
        turnOverFinder.add(new ReportBloodLustRoll(actingPlayer, false, 1, 2, true, new RollModifier[0]));
        turnOverFinder.add(new ReportBiteSpectator(actingPlayer));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Failing to feed is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.BLOOD_LUST_ROLL), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void successBloodlust() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportBloodLustRoll(actingPlayer, true, 2, 4, false, new RollModifier[0]));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful dodge is not a turnover", turnOverOpt.isPresent());
    }

}