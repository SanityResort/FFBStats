/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;

final class DefaultClientEndpointConfig
implements ClientEndpointConfig {
    private List<String> preferredSubprotocols;
    private List<Extension> extensions;
    private List<Class<? extends Encoder>> encoders;
    private List<Class<? extends Decoder>> decoders;
    private Map<String, Object> userProperties = new HashMap<String, Object>();
    private ClientEndpointConfig.Configurator clientEndpointConfigurator;

    DefaultClientEndpointConfig(List<String> preferredSubprotocols, List<Extension> extensions, List<Class<? extends Encoder>> encoders, List<Class<? extends Decoder>> decoders, ClientEndpointConfig.Configurator clientEndpointConfigurator) {
        this.preferredSubprotocols = Collections.unmodifiableList(preferredSubprotocols);
        this.extensions = Collections.unmodifiableList(extensions);
        this.encoders = Collections.unmodifiableList(encoders);
        this.decoders = Collections.unmodifiableList(decoders);
        this.clientEndpointConfigurator = clientEndpointConfigurator;
    }

    @Override
    public List<String> getPreferredSubprotocols() {
        return this.preferredSubprotocols;
    }

    @Override
    public List<Extension> getExtensions() {
        return this.extensions;
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
    public final Map<String, Object> getUserProperties() {
        return this.userProperties;
    }

    @Override
    public ClientEndpointConfig.Configurator getConfigurator() {
        return this.clientEndpointConfigurator;
    }
}

