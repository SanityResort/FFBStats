package org.butterbrot.ffb.stats.zmq;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import org.butterbrot.ffb.stats.StatsCollector;
import org.butterbrot.ffb.stats.collections.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.net.NetCommandFactory;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ServerCommand;
import com.eclipsesource.json.JsonValue;
import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
@ConfigurationProperties(prefix = "zmq")
public class MessageProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private SocketBase receiver;
    private Ctx context;
    private SocketBase sender;
    private SocketBase fileQueue;
    private String senderEndpoint;
    private String receiverEndpoint;
    private String fileQueueEndpoint;
    private NetCommandFactory factory = new NetCommandFactory();

    public MessageProcessor() {
        logger.info("Creating MessageProcessor");
        context = ZMQ.init(1);
        receiver = ZMQ.socket(context, ZMQ.ZMQ_PULL);
        sender = ZMQ.socket(context, ZMQ.ZMQ_PUSH);
        fileQueue = ZMQ.socket(context, ZMQ.ZMQ_REQ);

    }

    public void run() {
        receiver.connect(receiverEndpoint);
        logger.info("Receiver connects to " + receiverEndpoint);
        sender.bind(senderEndpoint);
        logger.info("Sender binds to " + senderEndpoint);
        fileQueue.connect(fileQueueEndpoint);
        logger.info("FileQueue binds to " + fileQueueEndpoint);
        try {
            while (true) {
                System.gc();
                Msg msg = receiver.recv(0);
                String replayId = new String(msg.data());
                logger.info("Received ReplayId: {}", replayId);
                fileQueue.send(new Msg(replayId.getBytes()), 0);
                Msg response = fileQueue.recv(0);
                logger.info("Received game data for replay {}", replayId);

                byte[] responseData = response.data();

                if ("ERROR".equalsIgnoreCase(new String(responseData))) {
                    logger.error("File Queue returned an error for " + replayId + ". Skipping");
                    sender.send(new Msg("ERROR".getBytes()), 0);
                    continue;
                }

                try (ByteArrayInputStream byteIn = new ByteArrayInputStream(responseData);
                        GZIPInputStream gzipInputStream = new GZIPInputStream(byteIn);
                        InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
                        BufferedReader buf = new BufferedReader(inputStreamReader)) {
                    Stream<String> stringStream = buf.lines();
                    String data = stringStream.reduce((s, s2) -> s + s2).get();
                    JsonObject root = new JsonParser().parse(data).getAsJsonObject();

                    JsonObject game = root.getAsJsonObject("game");
                    Team away = new Team().initFrom(JsonValue.readFrom(game.getAsJsonObject("teamAway").toString()));
                    Team home = new Team().initFrom(JsonValue.readFrom(game.getAsJsonObject("teamHome").toString()));

                    JsonArray commands = root.getAsJsonObject("gameLog").getAsJsonArray("commandArray");
                    Iterator<JsonElement> it = commands.iterator();

                    List<ServerCommand> replayCommands = new ArrayList<>();
                    while (it.hasNext()) {
                        JsonElement element = it.next();
                      //  logger.info("Element: " + );
                        String id = element.getAsJsonObject().get("netCommandId").getAsString();
                        if (NetCommandId.SERVER_MODEL_SYNC.getName().equals(id)) {
                            replayCommands
                                .add((ServerCommand) factory.forJsonValue(JsonValue.readFrom(element.toString())));
                        }
                    }

                    StatsCollector collector = new StatsCollector(replayCommands);
                    collector.setHomeTeam(home);
                    collector.setAwayTeam(away);
                    StatsCollection collection = collector.evaluate(replayId);
                    Gson gson = new Gson();
                    String gameJson = gson.toJson(collection);
                    sender.send(new Msg(gameJson.getBytes()), 0);
                    logger.info("Stats sent for replayId {}", replayId);

                } catch (Exception e) {
                    logger.error("Could not create stats for replayId " + replayId, e);
                    sender.send(new Msg("ERROR".getBytes()), 0);
                }
            }
        } finally {
            ZMQ.close(sender);
            ZMQ.term(context);
        }
    }

    public void setFileQueueEndpoint(String fileQueueEndpoint) {
        this.fileQueueEndpoint = fileQueueEndpoint;
    }

    public void setSenderEndpoint(String senderEndpoint) {
        this.senderEndpoint = senderEndpoint;
    }

    public void setReceiverEndpoint(String receiverEndpoint) {
        this.receiverEndpoint = receiverEndpoint;
    }
}
