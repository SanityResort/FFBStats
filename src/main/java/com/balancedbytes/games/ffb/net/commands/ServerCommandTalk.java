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

public class ServerCommandTalk
extends ServerCommand {
    private String fCoach;
    private List<String> fTalks = new ArrayList<String>();

    public ServerCommandTalk() {
    }

    public ServerCommandTalk(String pCoach, String pTalk) {
        this();
        this.fCoach = pCoach;
        this.addTalk(pTalk);
    }

    public ServerCommandTalk(String pCoach, String[] pTalk) {
        this();
        this.fCoach = pCoach;
        this.addTalks(pTalk);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_TALK;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public void addTalk(String pTalk) {
        if (StringTool.isProvided(pTalk)) {
            this.fTalks.add(pTalk);
        }
    }

    public void addTalks(String[] pTalk) {
        if (ArrayTool.isProvided(pTalk)) {
            for (String talk : pTalk) {
                this.addTalk(talk);
            }
        }
    }

    public String[] getTalks() {
        return this.fTalks.toArray(new String[this.fTalks.size()]);
    }

    @Override
    public boolean isReplayable() {
        return false;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        IJsonOption.TALKS.addTo(jsonObject, this.fTalks);
        return jsonObject;
    }

    @Override
    public ServerCommandTalk initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.addTalks(IJsonOption.TALKS.getFrom(jsonObject));
        return this;
    }
}

