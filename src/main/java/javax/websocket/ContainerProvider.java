/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.util.ServiceLoader;
import javax.websocket.WebSocketContainer;

public abstract class ContainerProvider {
    public static WebSocketContainer getWebSocketContainer() {
        WebSocketContainer wsc = null;
        for (ContainerProvider impl : ServiceLoader.load(ContainerProvider.class)) {
            wsc = impl.getContainer();
            if (wsc == null) continue;
            return wsc;
        }
        if (wsc == null) {
            throw new RuntimeException("Could not find an implementation class.");
        }
        throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
    }

    protected abstract WebSocketContainer getContainer();
}

