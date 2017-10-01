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

public class ClientCommandArgueTheCall
extends ClientCommand {
    private List<String> fPlayerIds = new ArrayList<String>();

    public ClientCommandArgueTheCall() {
    }

    public ClientCommandArgueTheCall(String playerId) {
        this();
        this.addPlayerId(playerId);
    }

    public ClientCommandArgueTheCall(String[] pPlayerIds) {
        this();
        this.addPlayerIds(pPlayerIds);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_ARGUE_THE_CALL;
    }

    public String[] getPlayerIds() {
        return this.fPlayerIds.toArray(new String[this.fPlayerIds.size()]);
    }

    public boolean hasPlayerId(String pPlayerId) {
        return this.fPlayerIds.contains(pPlayerId);
    }

    private void addPlayerId(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayerIds.add(pPlayerId);
        }
    }

    private void addPlayerIds(String[] pPlayerIds) {
        if (ArrayTool.isProvided(pPlayerIds)) {
            for (String playerId : pPlayerIds) {
                this.addPlayerId(playerId);
            }
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.fPlayerIds);
        return jsonObject;
    }

    @Override
    public ClientCommandArgueTheCall initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        return this;
    }
}

