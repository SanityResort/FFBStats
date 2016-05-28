/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class ServerCommandReplay
extends ServerCommand {
    public static final int MAX_NR_OF_COMMANDS = 100;
    private List<ServerCommand> fReplayCommands = new ArrayList<ServerCommand>();
    private int fTotalNrOfCommands;

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_REPLAY;
    }

    public void add(ServerCommand pServerCommand) {
        if (pServerCommand != null) {
            this.fReplayCommands.add(pServerCommand);
        }
    }

    public void add(ServerCommand[] pServerCommands) {
        if (ArrayTool.isProvided(pServerCommands)) {
            for (ServerCommand serverCommand : pServerCommands) {
                this.add(serverCommand);
            }
        }
    }

    public int getNrOfCommands() {
        return this.fReplayCommands.size();
    }

    public void setTotalNrOfCommands(int pTotalNrOfCommands) {
        this.fTotalNrOfCommands = pTotalNrOfCommands;
    }

    public int getTotalNrOfCommands() {
        return this.fTotalNrOfCommands;
    }

    public ServerCommand[] getReplayCommands() {
        return this.fReplayCommands.toArray(new ServerCommand[this.fReplayCommands.size()]);
    }

    @Override
    public boolean isReplayable() {
        return false;
    }

    public int findHighestCommandNr() {
        int highestCommandNr = 0;
        for (ServerCommand serverCommand : this.fReplayCommands) {
            if (serverCommand.getCommandNr() <= highestCommandNr) continue;
            highestCommandNr = serverCommand.getCommandNr();
        }
        return highestCommandNr;
    }

    public int findLowestCommandNr() {
        int lowestCommandNr = Integer.MAX_VALUE;
        for (ServerCommand serverCommand : this.fReplayCommands) {
            if (serverCommand.getCommandNr() >= lowestCommandNr) continue;
            lowestCommandNr = serverCommand.getCommandNr();
        }
        return lowestCommandNr;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.COMMAND_NR.addTo(jsonObject, this.getCommandNr());
        IJsonOption.TOTAL_NR_OF_COMMANDS.addTo(jsonObject, this.fTotalNrOfCommands);
        JsonArray commandArray = new JsonArray();
        for (ServerCommand replayCommand : this.getReplayCommands()) {
            commandArray.add(replayCommand.toJsonValue());
        }
        IJsonOption.COMMAND_ARRAY.addTo(jsonObject, commandArray);
        return jsonObject;
    }

    @Override
    public ServerCommandReplay initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fTotalNrOfCommands = IJsonOption.TOTAL_NR_OF_COMMANDS.getFrom(jsonObject);
        JsonArray commandArray = IJsonOption.COMMAND_ARRAY.getFrom(jsonObject);
        this.fReplayCommands.clear();
        NetCommandFactory netCommandFactory = new NetCommandFactory();
        for (int i = 0; i < commandArray.size(); ++i) {
            ServerCommand replayCommand = (ServerCommand)netCommandFactory.forJsonValue(commandArray.get(i));
            this.add(replayCommand);
        }
        return this;
    }
}

