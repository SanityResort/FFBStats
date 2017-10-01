/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandTeamSetupDelete
extends ClientCommand {
    private String fSetupName;

    public ClientCommandTeamSetupDelete() {
    }

    public ClientCommandTeamSetupDelete(String pSetupName) {
        this.fSetupName = pSetupName;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_TEAM_SETUP_DELETE;
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
    public ClientCommandTeamSetupDelete initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fSetupName = IJsonOption.SETUP_NAME.getFrom(jsonObject);
        return this;
    }
}

