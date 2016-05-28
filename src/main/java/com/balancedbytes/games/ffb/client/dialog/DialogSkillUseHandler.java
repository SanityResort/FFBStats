/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SoundId;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogSkillUse;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogSkillUseParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;

public class DialogSkillUseHandler
extends DialogHandler {
    public DialogSkillUseHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        DialogSkillUseParameter dialogSkillUseParameter = (DialogSkillUseParameter)game.getDialogParameter();
        if (dialogSkillUseParameter != null) {
            Player player = game.getPlayerById(dialogSkillUseParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().hasPlayer(player)) {
                this.setDialog(new DialogSkillUse(this.getClient(), dialogSkillUseParameter));
                this.getDialog().showDialog(this);
                if (!game.isHomePlaying()) {
                    this.playSound(SoundId.QUESTION);
                }
            } else {
                StringBuilder message = new StringBuilder();
                String skillName = dialogSkillUseParameter.getSkill() != null ? dialogSkillUseParameter.getSkill().getName() : null;
                message.append("Waiting for coach to use ").append(skillName);
                if (dialogSkillUseParameter.getMinimumRoll() > 0) {
                    message.append(" (").append(dialogSkillUseParameter.getMinimumRoll()).append("+ to succeed)");
                }
                message.append(".");
                this.showStatus("Skill Use", message.toString(), StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.SKILL_USE)) {
            DialogSkillUse skillUseDialog = (DialogSkillUse)pDialog;
            this.getClient().getCommunication().sendUseSkill(skillUseDialog.getSkill(), skillUseDialog.isChoiceYes());
        }
    }
}

