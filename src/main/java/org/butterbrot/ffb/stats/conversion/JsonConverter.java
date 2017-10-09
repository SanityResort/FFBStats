package org.butterbrot.ffb.stats.conversion;

import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.eclipsesource.json.JsonValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.butterbrot.ffb.stats.evaluation.stats.StatsCollector;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.butterbrot.ffb.stats.Constants.*;

@Service
public class JsonConverter {

    private NetCommandFactory factory = new NetCommandFactory();

    public StatsCollection convert(JsonObject root, String replayId) {
        com.google.gson.JsonObject game = root.getAsJsonObject(FIELD_GAME);
        Team away = new Team().initFrom(JsonValue.readFrom(game.getAsJsonObject(FIELD_TEAM_AWAY).toString()));
        Team home = new Team().initFrom(JsonValue.readFrom(game.getAsJsonObject(FIELD_TEAM_HOME).toString()));

        JsonArray commands = root.getAsJsonObject(FIELD_GAME_LOG).getAsJsonArray(FIELD_COMMAND_ARRAY);
        Iterator<JsonElement> it = commands.iterator();

        List<ServerCommand> replayCommands = new ArrayList<>();
        while (it.hasNext()) {
            JsonElement element = it.next();
            String id = element.getAsJsonObject().get(FIELD_NET_COMMAND_ID).getAsString();
            if (NetCommandId.SERVER_MODEL_SYNC.getName().equals(id)) {
                replayCommands
                        .add((ServerCommand) factory.forJsonValue(JsonValue.readFrom(element.toString())));
            }
        }

        StatsCollector collector = new StatsCollector(replayCommands);
        collector.setHomeTeam(home);
        collector.setAwayTeam(away);
        return collector.evaluate(replayId);
    }

}
