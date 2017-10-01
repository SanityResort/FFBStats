/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ServerCommandTeamSetupList
extends ServerCommand {
    private List<String> fSetupNames = new ArrayList<String>();

    public ServerCommandTeamSetupList() {
    }

    public ServerCommandTeamSetupList(String[] pSetupNames) {
        this();
        this.addSetupNames(pSetupNames);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_TEAM_SETUP_LIST;
    }

    public String[] getSetupNames() {
        return this.fSetupNames.toArray(new String[this.fSetupNames.size()]);
    }

    private void addSetupName(String pSetupName) {
        if (StringTool.isProvided(pSetupName)) {
            this.fSetupNames.add(pSetupName);
        }
    }

    private void addSetupNames(String[] pSetupNames) {
        if (ArrayTool.isProvided(pSetupNames)) {
            for (String setupName : pSetupNames) {
                this.addSetupName(setupName);
            }
        }
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
        IJsonOption.SETUP_NAMES.addTo(jsonObject, this.fSetupNames);
        return jsonObject;
    }

    @Override
    public ServerCommandTeamSetupList initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.addSetupNames(IJsonOption.SETUP_NAMES.getFrom(jsonObject));
        return this;
    }
}

