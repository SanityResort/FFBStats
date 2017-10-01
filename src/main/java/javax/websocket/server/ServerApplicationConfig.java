/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket.server;

import java.util.Set;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;

public interface ServerApplicationConfig {
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> var1);

    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> var1);
}

