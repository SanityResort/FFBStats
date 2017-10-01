/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;

final class DefaultServerEndpointConfig
implements ServerEndpointConfig {
    private String path;
    private Class<?> endpointClass;
    private List<String> subprotocols;
    private List<Extension> extensions;
    private List<Class<? extends Encoder>> encoders;
    private List<Class<? extends Decoder>> decoders;
    private Map<String, Object> userProperties = new HashMap<String, Object>();
    private ServerEndpointConfig.Configurator serverEndpointConfigurator;

    DefaultServerEndpointConfig(Class<?> endpointClass, String path, List<String> subprotocols, List<Extension> extensions, List<Class<? extends Encoder>> encoders, List<Class<? extends Decoder>> decoders, ServerEndpointConfig.Configurator serverEndpointConfigurator) {
        this.path = path;
        this.endpointClass = endpointClass;
        this.subprotocols = Collections.unmodifiableList(subprotocols);
        this.extensions = Collections.unmodifiableList(extensions);
        this.encoders = Collections.unmodifiableList(encoders);
        this.decoders = Collections.unmodifiableList(decoders);
        this.serverEndpointConfigurator = serverEndpointConfigurator == null ? ServerEndpointConfig.Configurator.fetchContainerDefaultConfigurator() : serverEndpointConfigurator;
    }

    @Override
    public Class<?> getEndpointClass() {
        return this.endpointClass;
    }

    DefaultServerEndpointConfig(Class<? extends Endpoint> endpointClass, String path) {
        this.path = path;
        this.endpointClass = endpointClass;
    }

    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return this.encoders;
    }

    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return this.decoders;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public ServerEndpointConfig.Configurator getConfigurator() {
        return this.serverEndpointConfigurator;
    }

    @Override
    public final Map<String, Object> getUserProperties() {
        return this.userProperties;
    }

    @Override
    public final List<String> getSubprotocols() {
        return this.subprotocols;
    }

    @Override
    public final List<Extension> getExtensions() {
        return this.extensions;
    }
}

