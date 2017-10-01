/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class ServerCommandVersion
extends ServerCommand {
    private String fServerVersion;
    private String fClientVersion;
    private Map<String, String> fClientProperties = new HashMap<String, String>();

    public ServerCommandVersion() {
    }

    public ServerCommandVersion(String pServerVersion, String pClientVersion, String[] pClientProperties, String[] pClientPropertyValues) {
        this();
        this.fServerVersion = pServerVersion;
        this.fClientVersion = pClientVersion;
        if (ArrayTool.isProvided(pClientProperties) && ArrayTool.isProvided(pClientPropertyValues)) {
            for (int i = 0; i < pClientProperties.length; ++i) {
                this.fClientProperties.put(pClientProperties[i], pClientPropertyValues[i]);
            }
        }
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_VERSION;
    }

    public String getServerVersion() {
        return this.fServerVersion;
    }

    public String getClientVersion() {
        return this.fClientVersion;
    }

    public String[] getClientProperties() {
        return this.fClientProperties.keySet().toArray(new String[this.fClientProperties.size()]);
    }

    public String getClientPropertyValue(String pClientProperty) {
        return this.fClientProperties.get(pClientProperty);
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
        IJsonOption.SERVER_VERSION.addTo(jsonObject, this.fServerVersion);
        IJsonOption.CLIENT_VERSION.addTo(jsonObject, this.fClientVersion);
        String[] clientPropertyNames = this.getClientProperties();
        String[] clientPropertyValues = new String[clientPropertyNames.length];
        for (int i = 0; i < clientPropertyNames.length; ++i) {
            clientPropertyValues[i] = this.getClientPropertyValue(clientPropertyNames[i]);
        }
        IJsonOption.CLIENT_PROPERTY_NAMES.addTo(jsonObject, clientPropertyNames);
        IJsonOption.CLIENT_PROPERTY_VALUES.addTo(jsonObject, clientPropertyValues);
        return jsonObject;
    }

    @Override
    public ServerCommandVersion initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fServerVersion = IJsonOption.SERVER_VERSION.getFrom(jsonObject);
        this.fClientVersion = IJsonOption.CLIENT_VERSION.getFrom(jsonObject);
        Object[] clientPropertyNames = IJsonOption.CLIENT_PROPERTY_NAMES.getFrom(jsonObject);
        Object[] clientPropertyValues = IJsonOption.CLIENT_PROPERTY_VALUES.getFrom(jsonObject);
        this.fClientProperties.clear();
        if (ArrayTool.isProvided(clientPropertyNames) && ArrayTool.isProvided(clientPropertyValues)) {
            for (int i = 0; i < clientPropertyNames.length; ++i) {
                this.fClientProperties.put((String)clientPropertyNames[i], (String)clientPropertyValues[i]);
            }
        }
        return this;
    }
}

