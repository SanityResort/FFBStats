/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client;

import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ActionKeyAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;

public class ActionKeyMultiAction
extends AbstractAction {
    private ActionKey fActionKey;
    private List<ActionKeyAction> fActionKeyActions = new ArrayList<ActionKeyAction>();

    public ActionKeyMultiAction(ActionKey pActionKey) {
        this.fActionKey = pActionKey;
    }

    public ActionKey getActionKey() {
        return this.fActionKey;
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        for (ActionKeyAction actionKeyAction : this.fActionKeyActions) {
            actionKeyAction.actionPerformed(pE);
        }
    }

    public void add(ActionKeyAction pActionKeyAction) {
        this.fActionKeyActions.add(pActionKeyAction);
    }

    public ActionKeyAction[] getActions() {
        return this.fActionKeyActions.toArray(new ActionKeyAction[this.fActionKeyActions.size()]);
    }
}

