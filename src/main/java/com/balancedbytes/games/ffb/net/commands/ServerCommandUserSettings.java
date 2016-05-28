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
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServerCommandUserSettings
extends ServerCommand {
    private Map<String, String> fUserSettings = new HashMap<String, String>();

    public ServerCommandUserSettings() {
    }

    public ServerCommandUserSettings(String[] pUserSettingNames, String[] pUserSettingValues) {
        this();
        this.init(pUserSettingNames, pUserSettingValues);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_USER_SETTINGS;
    }

    public void addUserSetting(String pName, String pValue) {
        this.fUserSettings.put(pName, pValue);
    }

    public String[] getUserSettingNames() {
        String[] names = this.fUserSettings.keySet().toArray(new String[this.fUserSettings.size()]);
        Arrays.sort(names);
        return names;
    }

    public String getUserSettingValue(String pName) {
        return this.fUserSettings.get(pName);
    }

    private void init(String[] pSettingNames, String[] pSettingValues) {
        this.fUserSettings.clear();
        if (ArrayTool.isProvided(pSettingNames) && ArrayTool.isProvided(pSettingValues)) {
            for (int i = 0; i < pSettingNames.length; ++i) {
                this.addUserSetting(pSettingNames[i], pSettingValues[i]);
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
        String[] userSettingNames = this.getUserSettingNames();
        String[] userSettingValues = new String[userSettingNames.length];
        for (int i = 0; i < userSettingNames.length; ++i) {
            userSettingValues[i] = this.getUserSettingValue(userSettingNames[i]);
        }
        IJsonOption.USER_SETTING_NAMES.addTo(jsonObject, userSettingNames);
        IJsonOption.USER_SETTING_VALUES.addTo(jsonObject, userSettingValues);
        return jsonObject;
    }

    @Override
    public ServerCommandUserSettings initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        String[] userSettingNames = IJsonOption.USER_SETTING_NAMES.getFrom(jsonObject);
        String[] userSettingValues = IJsonOption.USER_SETTING_VALUES.getFrom(jsonObject);
        this.init(userSettingNames, userSettingValues);
        return this;
    }
}

