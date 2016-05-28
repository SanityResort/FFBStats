/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandPasswordChallenge
extends NetCommand {
    private String fCoach;

    public ClientCommandPasswordChallenge() {
    }

    public ClientCommandPasswordChallenge(String pChallenge) {
        this.fCoach = pChallenge;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_PASSWORD_CHALLENGE;
    }

    public String getCoach() {
        return this.fCoach;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        return jsonObject;
    }

    @Override
    public ClientCommandPasswordChallenge initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        return this;
    }
}

