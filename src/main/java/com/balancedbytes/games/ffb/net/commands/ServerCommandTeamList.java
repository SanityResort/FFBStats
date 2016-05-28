/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.TeamList;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ServerCommandTeamList
extends ServerCommand {
    private TeamList fTeamList;

    public ServerCommandTeamList() {
    }

    public ServerCommandTeamList(TeamList pTeamList) {
        this();
        this.fTeamList = pTeamList;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_TEAM_LIST;
    }

    public TeamList getTeamList() {
        return this.fTeamList;
    }

    @Override
    public boolean isReplayable() {
        return false;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COMMAND_NR.addTo(jsonObject, this.getCommandNr());
        if (this.fTeamList != null) {
            IJsonOption.TEAM_LIST.addTo(jsonObject, this.fTeamList.toJsonValue());
        }
        return jsonObject;
    }

    @Override
    public ServerCommandTeamList initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        JsonObject teamListObject = IJsonOption.TEAM_LIST.getFrom(jsonObject);
        this.fTeamList = teamListObject != null ? new TeamList().initFrom(teamListObject) : null;
        return this;
    }
}

