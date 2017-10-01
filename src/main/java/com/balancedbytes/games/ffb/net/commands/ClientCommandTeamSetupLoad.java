/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandTeamSetupLoad
extends ClientCommand {
    private String fSetupName;

    public ClientCommandTeamSetupLoad() {
    }

    public ClientCommandTeamSetupLoad(String pSetupName) {
        this.fSetupName = pSetupName;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_TEAM_SETUP_LOAD;
    }

    public String getSetupName() {
        return this.fSetupName;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.SETUP_NAME.addTo(jsonObject, this.fSetupName);
        return jsonObject;
    }

    @Override
    public ClientCommandTeamSetupLoad initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fSetupName = IJsonOption.SETUP_NAME.getFrom(jsonObject);
        return this;
    }
}

