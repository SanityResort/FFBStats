/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.net;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.StatusReport;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.net.CommandSocket;
import com.fumbbl.rng.MouseEntropySource;
import java.util.TimerTask;

public class ClientPingTask
extends TimerTask {
    private static final boolean _TRACE = false;
    private FantasyFootballClient fClient;
    private int fMaxDelayPing;
    private long fLastPingReceived;

    public ClientPingTask(FantasyFootballClient pClient, int pMaxDelayPing) {
        this.fClient = pClient;
        this.fMaxDelayPing = pMaxDelayPing;
    }

    public int getMaxDelayPing() {
        return this.fMaxDelayPing;
    }

    public void setLastPingReceived(long pTimeMillis) {
        ClientPingTask clientPingTask = this;
        synchronized (clientPingTask) {
            this.fLastPingReceived = pTimeMillis;
        }
    }

    public long getLastPingReceived() {
        ClientPingTask clientPingTask = this;
        synchronized (clientPingTask) {
            return this.fLastPingReceived;
        }
    }

    @Override
    public void run() {
        if (this.getClient().getCommandSocket().isOpen()) {
            long currentTimeMillis = System.currentTimeMillis();
            long lastPingReceived = this.getLastPingReceived();
            if (this.fMaxDelayPing > 0 && lastPingReceived > 0 && currentTimeMillis - lastPingReceived > (long)this.fMaxDelayPing) {
                this.getClient().getUserInterface().getStatusReport().reportServerUnreachable();
                this.getClient().stopClient();
            } else {
                MouseEntropySource mouseEntropySource = this.getClient().getUserInterface().getMouseEntropySource();
                boolean hasEnoughEntropy = mouseEntropySource.hasEnoughEntropy();
                byte entropy = mouseEntropySource.getEntropy();
                this.getClient().getCommunication().sendPing(currentTimeMillis, hasEnoughEntropy, entropy);
            }
        }
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }
}

