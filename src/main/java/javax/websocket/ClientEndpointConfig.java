/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ClientEndpointConfig
extends EndpointConfig {
    public List<String> getPreferredSubprotocols();

    public List<Extension> getExtensions();

    public Configurator getConfigurator();

    public static final class Builder {
        private List<String> preferredSubprotocols = Collections.emptyList();
        private List<Extension> extensions = Collections.emptyList();
        private List<Class<? extends Encoder>> encoders = Collections.emptyList();
        private List<Class<? extends Decoder>> decoders = Collections.emptyList();
        private Configurator clientEndpointConfigurator;

        private Builder() {
            this.clientEndpointConfigurator = new Configurator(){};
        }

        public static Builder create() {
            return new Builder();
        }

        public ClientEndpointConfig build() {
            return new DefaultClientEndpointConfig(Collections.unmodifiableList(this.preferredSubprotocols), Collections.unmodifiableList(this.extensions), Collections.unmodifiableList(this.encoders), Collections.unmodifiableList(this.decoders), this.clientEndpointConfigurator);
        }

        public Builder configurator(Configurator clientEndpointConfigurator) {
            this.clientEndpointConfigurator = clientEndpointConfigurator;
            return this;
        }

        public Builder preferredSubprotocols(List<String> preferredSubprotocols) {
            this.preferredSubprotocols = preferredSubprotocols == null ? new ArrayList() : preferredSubprotocols;
            return this;
        }

        public Builder extensions(List<Extension> extensions) {
            this.extensions = extensions == null ? new ArrayList() : extensions;
            return this;
        }

        public Builder encoders(List<Class<? extends Encoder>> encoders) {
            this.encoders = encoders == null ? new ArrayList() : encoders;
            return this;
        }

        public Builder decoders(List<Class<? extends Decoder>> decoders) {
            this.decoders = decoders == null ? new ArrayList() : decoders;
            return this;
        }

    }

    public static class Configurator {
        public void beforeRequest(Map<String, List<String>> headers) {
        }

        public void afterResponse(HandshakeResponse hr) {
        }
    }

}

