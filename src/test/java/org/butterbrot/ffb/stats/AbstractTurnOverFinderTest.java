package org.butterbrot.ffb.stats;

import org.junit.Before;

public class AbstractTurnOverFinderTest {

    protected String actingPlayer = "actingPlayer";
    protected String teamMember = "teamMember";
    protected String opponent = "opponent";

    protected TurnOverFinder turnOverFinder;

    @Before
    public void setUp() {
        turnOverFinder = new TurnOverFinder();
    }
}
