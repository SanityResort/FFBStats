/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.net;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.net.CommandEndpoint;
import java.util.TimerTask;

public class ClientPingTask
extends TimerTask {
    private FantasyFootballClient fClient;

    public ClientPingTask(FantasyFootballClient pClient) {
        this.fClient = pClient;
    }

    @Override
    public void run() {
        if (this.getClient().getCommandEndpoint().isOpen()) {
            this.getClient().getCommunication().sendPing(System.currentTimeMillis());
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }
}

