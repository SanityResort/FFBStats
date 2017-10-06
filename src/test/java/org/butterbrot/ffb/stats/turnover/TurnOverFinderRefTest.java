package org.butterbrot.ffb.stats.turnover;

import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.report.ReportBribesRoll;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportPlayerAction;
import com.balancedbytes.games.ffb.report.ReportReferee;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import org.butterbrot.ffb.stats.adapter.TurnOverDescription;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderRefTest extends AbstractTurnOverFinderTest{

    @Test
    public void foulSentOff(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Spotted foul is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.FOUL), turnOver.getAction());
        assertEquals("Spotted foul does not have a minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolled());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void foulMoveSentOff(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL_MOVE));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Spotted foul is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.FOUL), turnOver.getAction());
        assertEquals("Spotted foul does not have a minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolled());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void foulSuccess(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Unspotted foul is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void foulBribeFail(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportBribesRoll(actingPlayer, false, 1));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Spotted foul is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", TurnOverDescription.get(ReportId.FOUL), turnOver.getAction());
        assertEquals("Spotted foul does not have a minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolled());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void foulBribeSuccess(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportBribesRoll(actingPlayer, true, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful bribe precents turnover", turnOverOpt.isPresent());
    }

}
