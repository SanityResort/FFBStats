/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.StatusType;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogHandler;
import com.balancedbytes.games.ffb.client.dialog.DialogSkillUse;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.layer.FieldLayerRangeRuler;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogKickSkillParameter;
import com.balancedbytes.games.ffb.dialog.DialogSkillUseParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import java.awt.Color;

public class DialogKickSkillHandler
extends DialogHandler {
    private static final Color _MARKED_FIELDS_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.3f);

    public DialogKickSkillHandler(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public void showDialog() {
        Game game = this.getClient().getGame();
        UserInterface userInterface = this.getClient().getUserInterface();
        DialogKickSkillParameter dialogKickSkillParameter = (DialogKickSkillParameter)game.getDialogParameter();
        if (dialogKickSkillParameter != null) {
            Player player = game.getPlayerById(dialogKickSkillParameter.getPlayerId());
            if (ClientMode.PLAYER == this.getClient().getMode() && game.getTeamHome().hasPlayer(player)) {
                userInterface.getFieldComponent().getLayerRangeRuler().markCoordinates(new FieldCoordinate[]{dialogKickSkillParameter.getBallCoordinateWithKick(), dialogKickSkillParameter.getBallCoordinate()}, _MARKED_FIELDS_COLOR);
                userInterface.getFieldComponent().refresh();
                this.setDialog(new DialogSkillUse(this.getClient(), new DialogSkillUseParameter(player.getId(), Skill.KICK, 0)));
                this.getDialog().showDialog(this);
            } else {
                StringBuilder message = new StringBuilder();
                message.append("Waiting for coach to use ").append(Skill.KICK.getName()).append(".");
                this.showStatus("Skill Use", message.toString(), StatusType.WAITING);
            }
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        this.hideDialog();
        if (this.testDialogHasId(pDialog, DialogId.SKILL_USE)) {
            UserInterface userInterface = this.getClient().getUserInterface();
            userInterface.getFieldComponent().getLayerRangeRuler().clearMarkedCoordinates();
            userInterface.getFieldComponent().refresh();
            DialogSkillUse skillUseDialog = (DialogSkillUse)pDialog;
            this.getClient().getCommunication().sendUseSkill(skillUseDialog.getSkill(), skillUseDialog.isChoiceYes());
        }
    }
}

