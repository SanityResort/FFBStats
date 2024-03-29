package org.butterbrot.ffb.stats.turnover.bb2016;

import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.report.ReportBribesRoll;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.ReportPlayerAction;
import com.fumbbl.ffb.report.bb2016.ReportReferee;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import org.butterbrot.ffb.stats.model.TurnOver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnOverFinderRefTest extends AbstractTurnOverFinderTest{

    @Test
    public void foulSentOff(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Spotted foul is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.FOUL), turnOver.getAction());
        assertEquals("Spotted foul does not have a minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolled());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void foulMoveSentOff(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL_MOVE));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Spotted foul is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.FOUL), turnOver.getAction());
        assertEquals("Spotted foul does not have a minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolled());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void foulSuccess(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(false));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Unspotted foul is not a turnover", turnOverOpt.isPresent());
    }

    @Test
    public void foulBribeFail(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportBribesRoll(actingPlayer, false, 1));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertTrue("Spotted foul is a turnover", turnOverOpt.isPresent());
        TurnOver turnOver = turnOverOpt.get();
        assertEquals("TurnOver must have the actingPlayer set as active player", actingPlayer, turnOver.getActivePlayer());
        assertEquals("TurnOver must reflect the failed action", turnOverDescription.get(ReportId.FOUL), turnOver.getAction());
        assertEquals("Spotted foul does not have a minimum roll", 0, turnOver.getMinRollOrDiceCount());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolled());
        assertFalse("Fouls cannot be rerolled", turnOver.isReRolledWithTeamReroll());
    }

    @Test
    public void foulBribeSuccess(){
        turnOverFinder.add(new ReportPlayerAction(actingPlayer, PlayerAction.FOUL));
        turnOverFinder.add(new ReportReferee(true));
        turnOverFinder.add(new ReportBribesRoll(actingPlayer, true, 2));
        turnOverFinder.add(new ReportTurnEnd(null, null, null, new ArrayList<>()));
        Optional<TurnOver> turnOverOpt = turnOverFinder.findTurnover();
        assertFalse("Successful bribe precents turnover", turnOverOpt.isPresent());
    }

}
