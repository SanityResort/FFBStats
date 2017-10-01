/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.client.util.UtilClientCursor;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilCards;

public class ClientStateTouchback
extends ClientState {
    private boolean fTouchbackToAnyField;

    protected ClientStateTouchback(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public ClientStateId getId() {
        return ClientStateId.TOUCHBACK;
    }

    @Override
    public void enterState() {
        super.enterState();
        this.setSelectable(true);
        Game game = this.getClient().getGame();
        this.fTouchbackToAnyField = true;
        for (Player player : game.getTeamHome().getPlayers()) {
            if (!this.isPlayerSelectable(player)) continue;
            this.fTouchbackToAnyField = false;
            break;
        }
    }

    @Override
    protected boolean mouseOverPlayer(Player pPlayer) {
        super.mouseOverPlayer(pPlayer);
        if (this.isClickable() && (this.fTouchbackToAnyField || this.isPlayerSelectable(pPlayer))) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.pass");
        } else {
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        }
        return true;
    }

    @Override
    protected boolean mouseOverField(FieldCoordinate pCoordinate) {
        super.mouseOverField(pCoordinate);
        if (this.isClickable() && this.fTouchbackToAnyField) {
            UtilClientCursor.setCustomCursor(this.getClient().getUserInterface(), "cursor.pass");
        } else {
            UtilClientCursor.setDefaultCursor(this.getClient().getUserInterface());
        }
        return true;
    }

    @Override
    protected void clickOnPlayer(Player pPlayer) {
        Game game = this.getClient().getGame();
        if (this.isClickable() && (this.fTouchbackToAnyField || this.isPlayerSelectable(pPlayer))) {
            FieldCoordinate touchBackCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
            this.getClient().getCommunication().sendTouchback(touchBackCoordinate);
        }
    }

    @Override
    protected void clickOnField(FieldCoordinate pCoordinate) {
        if (this.isClickable() && this.fTouchbackToAnyField) {
            this.getClient().getCommunication().sendTouchback(pCoordinate);
        }
    }

    private boolean isPlayerSelectable(Player pPlayer) {
        boolean selectable = false;
        if (pPlayer != null) {
            Game game = this.getClient().getGame();
            PlayerState playerState = game.getFieldModel().getPlayerState(pPlayer);
            selectable = playerState != null && playerState.hasTacklezones() && game.getTeamHome().hasPlayer(pPlayer) && !UtilCards.hasSkill(game, pPlayer, Skill.NO_HANDS);
        }
        return selectable;
    }
}

