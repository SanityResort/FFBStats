/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket.server;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface HandshakeRequest {
    public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
    public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
    public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
    public static final String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";

    public Map<String, List<String>> getHeaders();

    public Principal getUserPrincipal();

    public URI getRequestURI();

    public boolean isUserInRole(String var1);

    public Object getHttpSession();

    public Map<String, List<String>> getParameterMap();

    public String getQueryString();
}

