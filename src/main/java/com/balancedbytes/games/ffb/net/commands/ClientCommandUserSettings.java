/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientCommandUserSettings
extends ClientCommand {
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
        JsonObject jsonObject = super.toJsonValue();
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
    public ClientCommandUserSettings initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.init(IJsonOption.SETTING_NAMES.getFrom(jsonObject), IJsonOption.SETTING_VALUES.getFrom(jsonObject));
        return this;
    }
}

