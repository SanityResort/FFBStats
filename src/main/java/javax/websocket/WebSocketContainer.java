/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.Session;

public interface WebSocketContainer {
    public long getDefaultAsyncSendTimeout();

    public void setAsyncSendTimeout(long var1);

    public Session connectToServer(Object var1, URI var2) throws DeploymentException, IOException;

    public Session connectToServer(Class<?> var1, URI var2) throws DeploymentException, IOException;

    public Session connectToServer(Endpoint var1, ClientEndpointConfig var2, URI var3) throws DeploymentException, IOException;

    public Session connectToServer(Class<? extends Endpoint> var1, ClientEndpointConfig var2, URI var3) throws DeploymentException, IOException;

    public long getDefaultMaxSessionIdleTimeout();

    public void setDefaultMaxSessionIdleTimeout(long var1);

    public int getDefaultMaxBinaryMessageBufferSize();

    public void setDefaultMaxBinaryMessageBufferSize(int var1);

    public int getDefaultMaxTextMessageBufferSize();

    public void setDefaultMaxTextMessageBufferSize(int var1);

    public Set<Extension> getInstalledExtensions();
}

