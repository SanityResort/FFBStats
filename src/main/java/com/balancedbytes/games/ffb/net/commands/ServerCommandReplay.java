package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ServerCommandReplay extends ServerCommand {
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

   public int getTotalNrOfCommands() {
        return this.fTotalNrOfCommands;
    }

    public ServerCommand[] getReplayCommands() {
        return this.fReplayCommands.toArray(new ServerCommand[this.fReplayCommands.size()]);
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

