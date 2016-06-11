package org.butterbrot.ffb.stats.zmq;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.butterbrot.ffb.stats.NoSuchReplayException;
import org.butterbrot.ffb.stats.StatsProvider;
import org.butterbrot.ffb.stats.collections.StatsCollection;
import org.butterbrot.ffb.stats.model.GameDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

import com.google.gson.Gson;

@Component
@ConfigurationProperties(prefix = "zmq")
public class MessageProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private SocketBase receiver;
    private Ctx context;
    private SocketBase sender;
    private String senderEndpoint;
    private String receiverEndpoint;

    @Resource
    private StatsProvider provider;

    public MessageProcessor() {
        logger.info("Creating MessageProcessor");
        context = ZMQ.init(1);
        receiver = ZMQ.socket(context, ZMQ.ZMQ_PULL);
        sender = ZMQ.socket(context, ZMQ.ZMQ_PUSH);
    }

    public void run() {
        receiver.connect(receiverEndpoint);
        logger.info("Receiver connects to " + receiverEndpoint);
        sender.bind(senderEndpoint);
        logger.info("Sender binds to " + senderEndpoint);
        try {
            while (true) {
                logger.info("listening");
                Msg msg = receiver.recv(0);
                String replayId = new String(msg.data());
                logger.info("Received Message: {}", replayId);

                try {
                    StatsCollection collection = provider.stats(replayId);
                    Gson gson = new Gson();
                    String gameJson = gson.toJson(collection);
                    sender.send(new Msg(gameJson.getBytes()), 0);
                    logger.info("Message sent");
                } catch (NoSuchReplayException e) {
                    logger.warn("Could not find replay for id {}", replayId);
                } catch (Exception e) {
                    logger.error("Failed to load replay for id " + replayId, e);
                }
            }
        } finally {
            ZMQ.close(sender);
            ZMQ.term(context);
        }
    }

    public void setSenderEndpoint(String senderEndpoint) {
        this.senderEndpoint = senderEndpoint;
    }

    public void setReceiverEndpoint(String receiverEndpoint) {
        this.receiverEndpoint = receiverEndpoint;
    }
}
