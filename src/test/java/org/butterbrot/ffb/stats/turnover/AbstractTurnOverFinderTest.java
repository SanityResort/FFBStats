package org.butterbrot.ffb.stats.turnover;

import com.balancedbytes.games.ffb.report.ReportPassRoll;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.junit.Before;

import java.lang.reflect.Field;
import java.util.Set;

public class AbstractTurnOverFinderTest {

    protected String actingPlayer = "actingPlayer";
    protected String teamMember = "teamMember";
    protected String opponent = "opponent";
    protected String actingTeam = "actingTeam";
    protected String opponentTeam = "opponentTeam";

    protected TurnOverFinder turnOverFinder;

    public static ReportPassRoll regularPass(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pFumble, boolean pSafeThrowHold, boolean pBomb, boolean reRolled) {
        return new ReportPassRoll(pPlayerId,  pSuccessful,  pRoll,  pMinimumRoll, reRolled,  null, null, pFumble,  pSafeThrowHold, pBomb);
    }

    public static ReportPassRoll hailMaryPass(String pPlayerId, boolean pFumble, int pRoll, boolean pBomb, boolean reRolled) {
        return new ReportPassRoll(pPlayerId, pFumble, pRoll, reRolled, pBomb);
    }

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception{
        turnOverFinder = new TurnOverFinder();
        Field playersField = turnOverFinder.getClass().getDeclaredField("homePlayers");
        playersField.setAccessible(true);
        Set<String> players = (Set<String>) playersField.get(turnOverFinder);
        players.add(actingPlayer);
        players.add(teamMember);
        turnOverFinder.setHomeTeamActive(true);
        Field homeTeam = TurnOverFinder.class.getDeclaredField("homeTeam");
        homeTeam.setAccessible(true);
        homeTeam.set(turnOverFinder, actingTeam);
    }


}
