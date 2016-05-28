/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientCommandUserSettings
extends NetCommand {
    private Map<String, String> fSettings = new HashMap<String, String>();

    public ClientCommandUserSettings() {
    }

    public ClientCommandUserSettings(String[] pSettingNames, String[] pSettingValues) {
        this();
        this.init(pSettingNames, pSettingValues);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_USER_SETTINGS;
    }

    public void addSetting(String pName, String pValue) {
        this.fSettings.put(pName, pValue);
    }

    public String[] getSettingNames() {
        String[] names = this.fSettings.keySet().toArray(new String[this.fSettings.size()]);
        Arrays.sort(names);
        return names;
    }

    public String getSettingValue(String pName) {
        return this.fSettings.get(pName);
    }

    private void init(String[] pSettingNames, String[] pSettingValues) {
        if (ArrayTool.isProvided(pSettingNames) && ArrayTool.isProvided(pSettingValues)) {
            for (int i = 0; i < pSettingNames.length; ++i) {
                this.addSetting(pSettingNames[i], pSettingValues[i]);
            }
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        String[] settingNames = this.getSettingNames();
        IJsonOption.SETTING_NAMES.addTo(jsonObject, settingNames);
        String[] settingValues = new String[settingNames.length];
        for (int i = 0; i < settingNames.length; ++i) {
            settingValues[i] = this.getSettingValue(settingNames[i]);
        }
        IJsonOption.SETTING_VALUES.addTo(jsonObject, settingValues);
        return jsonObject;
    }

    @Override
    public ClientCommandUserSettings initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.init(IJsonOption.SETTING_NAMES.getFrom(jsonObject), IJsonOption.SETTING_VALUES.getFrom(jsonObject));
        return this;
    }
}

