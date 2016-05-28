/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.state.ClientState;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class ActionKeyAction
extends AbstractAction {
    private FantasyFootballClient fClient;
    private ActionKey fActionKey;
    private KeyStroke fKeyStroke;

    public ActionKeyAction(FantasyFootballClient pClient, KeyStroke pKeyStroke, ActionKey pActionKey) {
        this.fClient = pClient;
        this.fKeyStroke = pKeyStroke;
        this.fActionKey = pActionKey;
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        this.getClient().getClientState().actionKeyPressed(this.getActionKey());
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public ActionKey getActionKey() {
        return this.fActionKey;
    }

    public KeyStroke getKeyStroke() {
        return this.fKeyStroke;
    }
}

