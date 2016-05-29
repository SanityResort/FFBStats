package org.butterbrot.ffb.stats.spring;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ReplayMessageHandler extends WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReplayMessageHandler.class);

    private boolean useCompression;

    public ReplayMessageHandler(boolean useCompression) {
        this.useCompression = useCompression;
    }

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
        return null;
    }
/*
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("afterConnectionEstablished");
        JsonValue command = new ClientCommandReplay().toJsonValue();

        final String textMessage;
        if (this.useCompression) {
            try {
                textMessage = UtilJson.deflateToBase64(command);
                webSocketSession.setBinaryMessageSizeLimit(65536);
                logger.info("session is open: " +         webSocketSession.isOpen());
                try {
                    webSocketSession.sendMessage(new BinaryMessage(binaryPayload(command.toString())));
                } catch (Exception ex) {
                    logger.error("Sending failed: ", ex);
                    throw ex;
                }
                logger.info("session is open: " +         webSocketSession.isOpen());
            }
            catch (IOException ex) {
                logger.error("Deflating json failed", ex);
                throw ex;
            }
        } else {
                textMessage = command.toString();
            webSocketSession.setTextMessageSizeLimit(65536);
            logger.info("session is open: " +         webSocketSession.isOpen());
            try {
                webSocketSession.sendMessage(new TextMessage(textMessage));
            } catch (Exception ex) {
                logger.error("Sending failed: ", ex);
                throw ex;
            }
            logger.info("session is open: " +         webSocketSession.isOpen());
        }
    }

    private byte[] binaryPayload(String message) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOut = new DeflaterOutputStream((OutputStream) byteOut, new Deflater(9));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter((OutputStream) deflaterOut,  Charset.forName("UTF-8")));
        out.write(message);
        out.close();
        return Base64.encodeToByte(byteOut.toByteArray(), false);
    }*/


  /*  @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("handleMessage");
        String message = (String) webSocketMessage.getPayload();
        logger.info("Message: " + message);
    }*/
/*
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        logger.info("handleBinaryMessage");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        logger.info("handleMessage");
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        logger.info("handlePongMessage");
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        logger.info("handleTextMessage");
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.info("handleTransportError");

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("afterConnectionClosed");

    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }*/
}
