/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.GameTitle;
import com.balancedbytes.games.ffb.client.UserInterface;

public class GameTitleUpdateTask
implements Runnable {
    private FantasyFootballClient fClient;
    private GameTitle fGameTitle;

    public GameTitleUpdateTask(FantasyFootballClient pClient, GameTitle pGameTitle) {
        this.fClient = pClient;
        this.fGameTitle = pGameTitle;
    }

    @Override
    public void run() {
        UserInterface userInterface = this.fClient.getUserInterface();
        userInterface.setGameTitle(this.fGameTitle);
    }
}

