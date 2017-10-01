/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandFollowupChoice
extends ClientCommand {
    private boolean fChoiceFollowup;

    public ClientCommandFollowupChoice() {
    }

    public ClientCommandFollowupChoice(boolean pChoiceReceive) {
        this.fChoiceFollowup = pChoiceReceive;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_FOLLOWUP_CHOICE;
    }

    public boolean isChoiceFollowup() {
        return this.fChoiceFollowup;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.CHOICE_FOLLOWUP.addTo(jsonObject, this.fChoiceFollowup);
        return jsonObject;
    }

    @Override
    public ClientCommandFollowupChoice initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fChoiceFollowup = IJsonOption.CHOICE_FOLLOWUP.getFrom(jsonObject);
        return this;
    }
}

