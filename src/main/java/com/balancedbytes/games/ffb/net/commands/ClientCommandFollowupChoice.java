/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandFollowupChoice
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.CHOICE_FOLLOWUP.addTo(jsonObject, this.fChoiceFollowup);
        return jsonObject;
    }

    @Override
    public ClientCommandFollowupChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fChoiceFollowup = IJsonOption.CHOICE_FOLLOWUP.getFrom(jsonObject);
        return this;
    }
}

