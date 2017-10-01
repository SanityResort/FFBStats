/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket.server;

import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

public interface ServerContainer
extends WebSocketContainer {
    public void addEndpoint(Class<?> var1) throws DeploymentException;

    public void addEndpoint(ServerEndpointConfig var1) throws DeploymentException;
}

