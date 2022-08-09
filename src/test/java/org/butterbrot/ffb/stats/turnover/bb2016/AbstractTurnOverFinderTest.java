package org.butterbrot.ffb.stats.turnover.bb2016;

import com.fumbbl.ffb.mechanics.PassResult;
import com.fumbbl.ffb.report.bb2016.ReportPassRoll;
import org.butterbrot.ffb.stats.adapter.bb2016.TurnOverDescription;
import org.butterbrot.ffb.stats.evaluation.turnover.bb2016.TurnOverFinder;
import org.junit.Before;

import java.util.Set;

public class AbstractTurnOverFinderTest {

    protected String actingPlayer = "actingPlayer";
    protected String teamMember = "teamMember";
    protected String opponent = "opponent";
    protected String actingTeam = "actingTeam";
    protected String opponentTeam = "opponentTeam";

    protected TurnOverFinder turnOverFinder;

    protected TurnOverDescription turnOverDescription;

    public static ReportPassRoll regularPass(String pPlayerId, int pRoll, int pMinimumRoll, boolean pBomb, boolean reRolled, PassResult passResult) {
        return new ReportPassRoll(pPlayerId, pRoll,  pMinimumRoll, reRolled,  null, null, pBomb, passResult);
        }

    public static ReportPassRoll hailMaryPass(String pPlayerId, int pRoll, boolean pBomb, boolean reRolled, PassResult passResult) {
        return new ReportPassRoll(pPlayerId, pRoll, reRolled, pBomb, passResult);
    }

    @Before
    public void setUp() {
        turnOverFinder = new TurnOverFinder();
        Set<String> players = turnOverFinder.getHomePlayers();
        players.add(actingPlayer);
        players.add(teamMember);
        turnOverFinder.setHomeTeamActive(true);
        turnOverFinder.setHomeTeam(actingTeam);
        turnOverDescription = new TurnOverDescription();
    }


}
