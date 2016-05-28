/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.handler;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandler;
import com.balancedbytes.games.ffb.client.handler.ClientCommandHandlerMode;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommandUserSettings;

public class ClientCommandHandlerUserSettings
extends ClientCommandHandler {
    protected ClientCommandHandlerUserSettings(FantasyFootballClient pClient) {
        super(pClient);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_USER_SETTINGS;
    }

    @Override
    public boolean handleNetCommand(NetCommand pNetCommand, ClientCommandHandlerMode pMode) {
        String[] settingNames;
        if (pMode == ClientCommandHandlerMode.QUEUING) {
            return true;
        }
        ServerCommandUserSettings userSettingsCommand = (ServerCommandUserSettings)pNetCommand;
        for (String settingName : settingNames = userSettingsCommand.getUserSettingNames()) {
            this.getClient().setProperty(settingName, userSettingsCommand.getUserSettingValue(settingName));
        }
        if (pMode == ClientCommandHandlerMode.PLAYING) {
            this.refreshGameMenuBar();
        }
        return true;
    }
}

