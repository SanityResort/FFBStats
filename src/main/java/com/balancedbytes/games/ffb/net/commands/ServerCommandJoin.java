/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerCommandJoin
extends ServerCommand {
    private String fCoach;
    private ClientMode fClientMode;
    private List<String> fPlayerNames = new ArrayList<String>();
    private int fSpectators;

    public ServerCommandJoin() {
    }

    public ServerCommandJoin(String pCoach, ClientMode pClientMode, String[] pPlayerNames, int pSpectators) {
        this();
        this.fCoach = pCoach;
        this.fClientMode = pClientMode;
        this.addPlayerNames(pPlayerNames);
        this.fSpectators = pSpectators;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_JOIN;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public ClientMode getClientMode() {
        return this.fClientMode;
    }

    public String[] getPlayerNames() {
        return this.fPlayerNames.toArray(new String[this.fPlayerNames.size()]);
    }

    private void addPlayerName(String pPlayerName) {
        if (StringTool.isProvided(pPlayerName)) {
            this.fPlayerNames.add(pPlayerName);
        }
    }

    private void addPlayerNames(String[] pPlayerNames) {
        if (ArrayTool.isProvided(pPlayerNames)) {
            for (String player : pPlayerNames) {
                this.addPlayerName(player);
            }
        }
    }

    public int getSpectators() {
        return this.fSpectators;
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
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        IJsonOption.CLIENT_MODE.addTo(jsonObject, this.fClientMode);
        IJsonOption.SPECTATORS.addTo(jsonObject, this.fSpectators);
        IJsonOption.PLAYER_NAMES.addTo(jsonObject, this.fPlayerNames);
        return jsonObject;
    }

    @Override
    public ServerCommandJoin initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.fClientMode = (ClientMode)IJsonOption.CLIENT_MODE.getFrom(jsonObject);
        this.fSpectators = IJsonOption.SPECTATORS.getFrom(jsonObject);
        this.addPlayerNames(IJsonOption.PLAYER_NAMES.getFrom(jsonObject));
        return this;
    }
}

