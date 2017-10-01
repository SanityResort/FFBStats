/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public abstract class Endpoint {
    public abstract void onOpen(Session var1, EndpointConfig var2);

    public void onClose(Session session, CloseReason closeReason) {
    }

    public void onError(Session session, Throwable thr) {
    }
}

