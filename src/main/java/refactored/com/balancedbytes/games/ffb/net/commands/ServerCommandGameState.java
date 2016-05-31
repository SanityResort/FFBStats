package refactored.com.balancedbytes.games.ffb.net.commands;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import refactored.com.balancedbytes.games.ffb.model.Game;
import refactored.com.balancedbytes.games.ffb.net.NetCommandId;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ServerCommandGameState extends ServerCommand {
    private Game fGame;

    public ServerCommandGameState() {
    }

    private ServerCommandGameState(Game pGame) {
        this();
        this.fGame = pGame;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_GAME_STATE;
    }

    public Game getGame() {
        return this.fGame;
    }

    public ServerCommandGameState transform() {
        return new ServerCommandGameState(this.getGame().transform());
    }

    @Override
    public ServerCommandGameState initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId) IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        JsonObject gameObject = IJsonOption.GAME.getFrom(jsonObject);
        if (gameObject != null) {
            this.fGame = new Game().initFrom(gameObject);
        }
        return this;
    }
}

