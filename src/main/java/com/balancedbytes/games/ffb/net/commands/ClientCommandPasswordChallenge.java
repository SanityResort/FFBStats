/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandPasswordChallenge
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        return jsonObject;
    }

    @Override
    public ClientCommandPasswordChallenge initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        return this;
    }
}

