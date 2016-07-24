package org.butterbrot.ffb.stats;

import org.junit.Test;
import refactored.com.balancedbytes.games.ffb.PlayerAction;
import refactored.com.balancedbytes.games.ffb.ReRollSource;
import refactored.com.balancedbytes.games.ffb.SpecialEffect;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.ReportInjury;
import refactored.com.balancedbytes.games.ffb.report.ReportPlayerAction;
import refactored.com.balancedbytes.games.ffb.report.ReportReRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportScatterBall;
import refactored.com.balancedbytes.games.ffb.report.ReportSkillRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportSpecialEffectRoll;
import refactored.com.balancedbytes.games.ffb.report.ReportTurnEnd;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderMiscTest extends AbstractTurnOverFinderTest {

    @Test
    public void empty() {
        assertFalse("Empty queue cannot create a turn over", turnOverFinder.findTurnover().isPresent());
    }

    @Test
    public void actionOnly() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.MOVE));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        assertFalse("Action without dice cannot create a turn over", turnOverFinder.findTurnover().isPresent());
    }

    @Test
    public void touchDown() {
        turnOverFinder.add(new ReportTurnEnd(actingPlayer, null, null));
        assertFalse("Touchdown is not a turn over", turnOverFinder.findTurnover().isPresent());
    }

    @Test
    public void kickBack() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportInjury(actingPlayer, true, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CHAINSAW_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void kickBackNoAvBreak() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportInjury(actingPlayer, false, null, null, null, null, null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Kickback without armour break is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void kickBackWithLeaderReRoll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.LEADER));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportInjury(actingPlayer, true, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CHAINSAW_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void kickBackWithTeamReroll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.TEAM_RE_ROLL));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportInjury(actingPlayer, true, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CHAINSAW_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertTrue("Was rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void kickBackProReroll() {
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.BLOCK));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportReRoll(actingPlayer, ReRollSource.PRO));
        turnOverFinder.add(new ReportSkillRoll(ReportId.CHAINSAW_ROLL, actingPlayer, false, 2, 1));
        turnOverFinder.add(new ReportInjury(actingPlayer, true, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Kickback with armour break is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", ReportId.CHAINSAW_ROLL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("TurnOver must show the minimum roll", 2, turnOver.getMinRollOrDiceCount());
        assertTrue("Was rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void fireBallKnockDownOwnBallCarrier() {
        turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.FIREBALL, actingPlayer, true, 6));
        turnOverFinder.add(new ReportInjury(actingPlayer, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Fireballing your ball carrier is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertTrue("For wizards there is no active player", turnOver.getActivePlayer() == null);
        assertEquals("TurnOver must reflect the failed action", SpecialEffect.FIREBALL.getTurnOverDesc(), turnOver.getAction());
        assertEquals("Wizard does not use minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void lightningBoltKnockDownOwnBallCarrier() {
        turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.LIGHTNING, actingPlayer, true, 6));
        turnOverFinder.add(new ReportInjury(actingPlayer, false, null, null, null, null, null, null, null));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Bolting your ball carrier is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertTrue("For wizards there is no active player", turnOver.getActivePlayer() == null);
        assertEquals("TurnOver must reflect the failed action", SpecialEffect.LIGHTNING.getTurnOverDesc(), turnOver.getAction());
        assertEquals("Wizard does not use minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Was not rerolled", turnOver.isReRolled());
        assertFalse("Was not rerolled with a team reroll", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void fireBallKnockDownOwnPlayer() {
        turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.FIREBALL, actingPlayer, true, 6));
        turnOverFinder.add(new ReportScatterBall());
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Fireballing your own player without the ball is a not turnover", turnOverOpt.isPresent());
    }

    @Test
    public void lightningBoltKnockDownOwnPlayer() {
        turnOverFinder.setHomeTeamActive(true);
        turnOverFinder.add(new ReportSpecialEffectRoll(SpecialEffect.LIGHTNING, actingPlayer, true, 6));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Bolting your own player without the ball is a not turnover", turnOverOpt.isPresent());
    }
}