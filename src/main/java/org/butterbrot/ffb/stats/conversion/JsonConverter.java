package org.butterbrot.ffb.stats.conversion;

import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.net.NetCommandFactory;
import com.fumbbl.ffb.net.NetCommandId;
import com.fumbbl.ffb.net.commands.ServerCommand;
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
    public StatsCollection convert(JsonObject root, String replayId) {
        EvaluationFactorySource factorySource = new EvaluationFactorySource();
        NetCommandFactory factory = new NetCommandFactory(factorySource);
        JsonObject gsonGame = root.getAsJsonObject(FIELD_GAME);
        Game game = new Game(factorySource, factorySource.getFactoryManager()).initFrom(factorySource, JsonValue.readFrom(gsonGame.toString()));
        Team away = game.getTeamAway();
        Team home = game.getTeamHome();

        JsonArray commands = root.getAsJsonObject(FIELD_GAME_LOG).getAsJsonArray(FIELD_COMMAND_ARRAY);
        Iterator<JsonElement> it = commands.iterator();

        List<ServerCommand> replayCommands = new ArrayList<>();
        while (it.hasNext()) {
            JsonElement element = it.next();
            String id = element.getAsJsonObject().get(FIELD_NET_COMMAND_ID).getAsString();
            if (NetCommandId.SERVER_MODEL_SYNC.getName().equals(id)) {
                replayCommands
                        .add((ServerCommand) factory.forJsonValue(factorySource, JsonValue.readFrom(element.toString())));
            }
        }

        StatsCollector collector = new StatsCollector(replayCommands);
        collector.setGame(game);
        return collector.evaluate(replayId);
    }

}
