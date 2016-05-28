/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.JsonPlayerStateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandAddPlayer
extends ServerCommand {
    private String fTeamId;
    private Player fPlayer;
    private PlayerState fPlayerState;
    private SendToBoxReason fSendToBoxReason;
    private int fSendToBoxTurn;
    private int fSendToBoxHalf;

    public ServerCommandAddPlayer() {
    }

    public ServerCommandAddPlayer(String pTeamId, Player pPlayer, PlayerState pPlayerState, PlayerResult pPlayerResult) {
        this();
        if (pPlayer == null) {
            throw new IllegalArgumentException("Parameter player must not be null.");
        }
        this.fTeamId = pTeamId;
        this.fPlayer = pPlayer;
        this.fPlayerState = pPlayerState;
        if (pPlayerResult != null) {
            this.fSendToBoxReason = pPlayerResult.getSendToBoxReason();
            this.fSendToBoxTurn = pPlayerResult.getSendToBoxTurn();
            this.fSendToBoxHalf = pPlayerResult.getSendToBoxHalf();
        }
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_ADD_PLAYER;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public Player getPlayer() {
        return this.fPlayer;
    }

    public PlayerState getPlayerState() {
        return this.fPlayerState;
    }

    public SendToBoxReason getSendToBoxReason() {
        return this.fSendToBoxReason;
    }

    public int getSendToBoxHalf() {
        return this.fSendToBoxHalf;
    }

    public int getSendToBoxTurn() {
        return this.fSendToBoxTurn;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COMMAND_NR.addTo(jsonObject, this.getCommandNr());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        if (this.fPlayer != null) {
            IJsonOption.PLAYER.addTo(jsonObject, this.fPlayer.toJsonValue());
        }
        IJsonOption.PLAYER_STATE.addTo(jsonObject, this.fPlayerState);
        IJsonOption.SEND_TO_BOX_REASON.addTo(jsonObject, this.fSendToBoxReason);
        IJsonOption.SEND_TO_BOX_TURN.addTo(jsonObject, this.fSendToBoxTurn);
        IJsonOption.SEND_TO_BOX_HALF.addTo(jsonObject, this.fSendToBoxHalf);
        return jsonObject;
    }

    @Override
    public ServerCommandAddPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        JsonObject playerObject = IJsonOption.PLAYER.getFrom(jsonObject);
        if (playerObject != null) {
            this.fPlayer = new Player().initFrom(playerObject);
        }
        this.fPlayerState = IJsonOption.PLAYER_STATE.getFrom(jsonObject);
        this.fSendToBoxReason = (SendToBoxReason)IJsonOption.SEND_TO_BOX_REASON.getFrom(jsonObject);
        this.fSendToBoxTurn = IJsonOption.SEND_TO_BOX_TURN.getFrom(jsonObject);
        this.fSendToBoxHalf = IJsonOption.SEND_TO_BOX_HALF.getFrom(jsonObject);
        return this;
    }
}

