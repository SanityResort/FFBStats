/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
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

public class ServerCommandAdminMessage
extends ServerCommand {
    private List<String> fMessages = new ArrayList<String>();

    public ServerCommandAdminMessage() {
    }

    public ServerCommandAdminMessage(String[] pMessages) {
        this();
        this.addMessages(pMessages);
    }

    private void addMessage(String pMessage) {
        if (StringTool.isProvided(pMessage)) {
            this.fMessages.add(pMessage);
        }
    }

    private void addMessages(String[] pMessages) {
        if (ArrayTool.isProvided(pMessages)) {
            for (String message : pMessages) {
                this.addMessage(message);
            }
        }
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_ADMIN_MESSAGE;
    }

    public String[] getMessages() {
        return this.fMessages.toArray(new String[this.fMessages.size()]);
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
        IJsonOption.MESSAGE_ARRAY.addTo(jsonObject, this.fMessages);
        return jsonObject;
    }

    @Override
    public ServerCommandAdminMessage initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.addMessages(IJsonOption.MESSAGE_ARRAY.getFrom(jsonObject));
        return this;
    }
}

