/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogPilingOn;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogPilingOnParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogPilingOnHandler
extends DialogHandler {
    public DialogPilingOnHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogPilingOnParameter dialogParameter = (DialogPilingOnParameter)game.getDialogParameter();
        if (dialogParameter != null) {
            Player player = game.getPlayerById(dialogParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().hasPlayer(player)) {
                this.setDialog(new DialogPilingOn(this.getClient(), dialogParameter));
                this.getDialog().showDialog(this);
            } else {
                this.showStatus("Piling On", "Waiting for coach to use Piling On.", StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.PILING_ON)) {
            DialogPilingOn pilingOnDialog = (DialogPilingOn)pDialog;
            this.getClient().getCommunication().sendUseSkill(Skill.PILING_ON, pilingOnDialog.isChoiceYes());
        }
    }
}

