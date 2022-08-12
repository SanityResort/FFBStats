package org.butterbrot.ffb.stats.conversion;

import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.net.NetCommandFactory;
import com.fumbbl.ffb.net.NetCommandId;
import com.fumbbl.ffb.net.commands.ServerCommand;
import com.fumbbl.ffb.option.GameOptionId;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;
import org.butterbrot.ffb.stats.evaluation.stats.StatsCollector;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.butterbrot.ffb.stats.Constants.*;

@Service
public class JsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    public StatsCollection convert(JsonObject root, String replayId) {
        EvaluationFactorySource factorySource = new EvaluationFactorySource();
        NetCommandFactory factory = new NetCommandFactory(factorySource);
        JsonObject gsonGame = root.getAsJsonObject(FIELD_GAME);
        Game game = new Game(factorySource, factorySource.getFactoryManager()).initFrom(factorySource, JsonValue.readFrom(gsonGame.toString()));

        JsonArray commands = root.getAsJsonObject(FIELD_GAME_LOG).getAsJsonArray(FIELD_COMMAND_ARRAY);
        Iterator<JsonElement> it = commands.iterator();

        List<ServerCommand> replayCommands = new ArrayList<>();
        while (it.hasNext()) {
            JsonElement element = it.next();
            String id = element.getAsJsonObject().get(FIELD_NET_COMMAND_ID).getAsString();
            if (NetCommandId.SERVER_MODEL_SYNC.getName().equals(id)) {
                try {
                    replayCommands
                      .add((ServerCommand) factory.forJsonValue(game.getRules(), JsonValue.readFrom(element.toString())));
                } catch (Exception e) {
                    logger.error("Could not create replay command: " + element, e);
                }
            }
        }

        StatsCollector<? extends ExposingInjuryReport> collector;
        if (game.getOptions().getOptionWithDefault(GameOptionId.RULESVERSION).getValueAsString().equalsIgnoreCase("bb2016")) {
            collector = new org.butterbrot.ffb.stats.evaluation.stats.bb2016.StatsCollector(replayCommands);
        } else {
            collector = new org.butterbrot.ffb.stats.evaluation.stats.bb2020.StatsCollector(replayCommands);
        }
        collector.setGame(game);
        return collector.evaluate(replayId);
    }

}
