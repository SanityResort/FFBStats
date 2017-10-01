/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandApothecaryChoice
extends ClientCommand {
    private String fPlayerId;
    private PlayerState fPlayerState;
    private SeriousInjury fSeriousInjury;

    public ClientCommandApothecaryChoice() {
    }

    public ClientCommandApothecaryChoice(String pPlayerId, PlayerState pPlayerState, SeriousInjury pSeriousInjury) {
        this.fPlayerId = pPlayerId;
        this.fPlayerState = pPlayerState;
        this.fSeriousInjury = pSeriousInjury;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_APOTHECARY_CHOICE;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public PlayerState getPlayerState() {
        return this.fPlayerState;
    }

    public SeriousInjury getSeriousInjury() {
        return this.fSeriousInjury;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.PLAYER_STATE.addTo(jsonObject, this.fPlayerState);
        IJsonOption.SERIOUS_INJURY.addTo(jsonObject, this.fSeriousInjury);
        return jsonObject;
    }

    @Override
    public ClientCommandApothecaryChoice initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fPlayerState = IJsonOption.PLAYER_STATE.getFrom(jsonObject);
        this.fSeriousInjury = (SeriousInjury)IJsonOption.SERIOUS_INJURY.getFrom(jsonObject);
        return this;
    }
}

