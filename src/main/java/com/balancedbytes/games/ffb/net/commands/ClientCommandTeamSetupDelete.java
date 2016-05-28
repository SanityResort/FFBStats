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

public class ClientCommandTeamSetupDelete
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.SETUP_NAME.addTo(jsonObject, this.fSetupName);
        return jsonObject;
    }

    @Override
    public ClientCommandTeamSetupDelete initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fSetupName = IJsonOption.SETUP_NAME.getFrom(jsonObject);
        return this;
    }
}

