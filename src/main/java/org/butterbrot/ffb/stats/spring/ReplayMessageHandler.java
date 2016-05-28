package org.butterbrot.ffb.stats.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.Type;

public class ReplayMessageHandler  implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReplayMessageHandler.class);
/*
    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        logger.info("afterConnected");
        super.afterConnected(stompSession, stompHeaders);
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        logger.info("handleException");
        super.handleException(stompSession,stompCommand,stompHeaders,bytes,throwable);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        logger.info("handleTransportError");
        throwable.printStackTrace();
        super.handleTransportError(stompSession,throwable);
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        logger.info("getPayloadType");
        return super.getPayloadType(stompHeaders);
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        logger.info("handleFrame");
        super.handleFrame(stompHeaders,o);
    }*/

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("afterConnectionEstablished");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("handleMessage");

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
    }
}
