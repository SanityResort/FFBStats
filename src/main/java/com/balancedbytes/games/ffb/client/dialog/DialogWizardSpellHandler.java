/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogWizardSpell;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.Game;

public class DialogWizardSpellHandler
extends DialogHandler {
    public DialogWizardSpellHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        if (ClientMode.PLAYER == this.getClient().getMode() && game.isHomePlaying()) {
            this.setDialog(new DialogWizardSpell(this.getClient()));
            this.getDialog().showDialog(this);
        } else {
            this.showStatus("Wizard Spell", "Waiting for coach to select a spell.", StatusType.WAITING);
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (pDialog != null && pDialog.getId() == DialogId.WIZARD_SPELL) {
            SpecialEffect wizardSpell = ((DialogWizardSpell)pDialog).getWizardSpell();
            if (wizardSpell != null) {
                this.getClient().getClientData().setWizardSpell(wizardSpell);
            } else {
                this.getClient().getCommunication().sendWizardSpell(null, null);
            }
        }
    }
}

