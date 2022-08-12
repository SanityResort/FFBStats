package org.butterbrot.ffb.stats.zmq;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.butterbrot.ffb.stats.conversion.JsonConverter;
import org.butterbrot.ffb.stats.conversion.Unzipper;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

import javax.annotation.Resource;

import static org.butterbrot.ffb.stats.Constants.ZMG_ERROR;

@Component
@ConfigurationProperties(prefix = "zmq")
public class MessageProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private final SocketBase receiver;
    private final Ctx context;
    private final SocketBase sender;
    private final SocketBase fileQueue;
    private String senderEndpoint;
    private String receiverEndpoint;
    private String fileQueueEndpoint;
    private final Gson gson = new Gson();

    @Resource
    private Unzipper unzipper;

    @Resource
    private JsonConverter converter;

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
            //noinspection InfiniteLoopStatement
            while (true) {
                System.gc();
                Msg msg = receiver.recv(0);
                String replayId = new String(msg.data());
                logger.info("Received ReplayId: {}", replayId);
                fileQueue.send(new Msg(replayId.getBytes()), 0);
                Msg response = fileQueue.recv(0);
                logger.info("Received game data for replay {}", replayId);

                byte[] responseData = response.data();

                if (ZMG_ERROR.equalsIgnoreCase(new String(responseData))) {
                    logger.error("File Queue returned an error for " + replayId + ". Skipping");
                    sender.send(new Msg(ZMG_ERROR.getBytes()), 0);
                    continue;
                }

                try {
                    JsonObject root = unzipper.fromGZip(responseData);
                    StatsCollection collection = converter.convert(root, replayId);
                    String gameJson = gson.toJson(collection);
                    sender.send(new Msg(gameJson.getBytes()), 0);
                    logger.info("Stats sent for replayId {}", replayId);

                } catch (Exception e) {
                    logger.error("Could not create stats for replayId " + replayId, e);
                    sender.send(new Msg(ZMG_ERROR.getBytes()), 0);
                }
            }
        } finally {
            ZMQ.close(sender);
            ZMQ.term(context);
        }
    }

    @SuppressWarnings("unused")
    public void setFileQueueEndpoint(String fileQueueEndpoint) {
        this.fileQueueEndpoint = fileQueueEndpoint;
    }

    @SuppressWarnings("unused")
    public void setSenderEndpoint(String senderEndpoint) {
        this.senderEndpoint = senderEndpoint;
    }

    @SuppressWarnings("unused")
    public void setReceiverEndpoint(String receiverEndpoint) {
        this.receiverEndpoint = receiverEndpoint;
    }
}
