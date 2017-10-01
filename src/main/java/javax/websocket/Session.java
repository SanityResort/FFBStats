/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Session
extends Closeable {
    public WebSocketContainer getContainer();

    public void addMessageHandler(MessageHandler var1) throws IllegalStateException;

    public <T> void addMessageHandler(Class<T> var1, MessageHandler.Whole<T> var2);

    public <T> void addMessageHandler(Class<T> var1, MessageHandler.Partial<T> var2);

    public Set<MessageHandler> getMessageHandlers();

    public void removeMessageHandler(MessageHandler var1);

    public String getProtocolVersion();

    public String getNegotiatedSubprotocol();

    public List<Extension> getNegotiatedExtensions();

    public boolean isSecure();

    public boolean isOpen();

    public long getMaxIdleTimeout();

    public void setMaxIdleTimeout(long var1);

    public void setMaxBinaryMessageBufferSize(int var1);

    public int getMaxBinaryMessageBufferSize();

    public void setMaxTextMessageBufferSize(int var1);

    public int getMaxTextMessageBufferSize();

    public RemoteEndpoint.Async getAsyncRemote();

    public RemoteEndpoint.Basic getBasicRemote();

    public String getId();

    @Override
    public void close() throws IOException;

    public void close(CloseReason var1) throws IOException;

    public URI getRequestURI();

    public Map<String, List<String>> getRequestParameterMap();

    public String getQueryString();

    public Map<String, String> getPathParameters();

    public Map<String, Object> getUserProperties();

    public Principal getUserPrincipal();

    public Set<Session> getOpenSessions();
}

