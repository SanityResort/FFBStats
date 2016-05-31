package refactored.com.balancedbytes.games.ffb.net.commands;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import refactored.com.balancedbytes.games.ffb.net.NetCommand;
import refactored.com.balancedbytes.games.ffb.net.NetCommandId;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ClientCommandReplay extends NetCommand {
    private long fGameId;
    private int fReplayToCommandNr;

    public ClientCommandReplay() {
    }

    public ClientCommandReplay(long pGameId, int pReplayToCommandNr) {
        this.fGameId = pGameId;
        this.fReplayToCommandNr = pReplayToCommandNr;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_REPLAY;
    }

    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.GAME_ID.addTo(jsonObject, this.fGameId);
        IJsonOption.REPLAY_TO_COMMAND_NR.addTo(jsonObject, this.fReplayToCommandNr);
        return jsonObject;
    }

    @Override
    public ClientCommandReplay initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fGameId = IJsonOption.GAME_ID.getFrom(jsonObject);
        this.fReplayToCommandNr = IJsonOption.REPLAY_TO_COMMAND_NR.getFrom(jsonObject);
        return this;
    }
}

