package org.butterbrot.ffb.stats;

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
