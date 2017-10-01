/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.DefaultServerEndpointConfig;
import javax.websocket.server.HandshakeRequest;

public interface ServerEndpointConfig
extends EndpointConfig {
    public Class<?> getEndpointClass();

    public String getPath();

    public List<String> getSubprotocols();

    public List<Extension> getExtensions();

    public Configurator getConfigurator();

    public static final class Builder {
        private String path;
        private Class<?> endpointClass;
        private List<String> subprotocols = Collections.emptyList();
        private List<Extension> extensions = Collections.emptyList();
        private List<Class<? extends Encoder>> encoders = Collections.emptyList();
        private List<Class<? extends Decoder>> decoders = Collections.emptyList();
        private Configurator serverEndpointConfigurator;

        public static Builder create(Class<?> endpointClass, String path) {
            return new Builder(endpointClass, path);
        }

        private Builder() {
        }

        public ServerEndpointConfig build() {
            return new DefaultServerEndpointConfig(this.endpointClass, this.path, Collections.unmodifiableList(this.subprotocols), Collections.unmodifiableList(this.extensions), Collections.unmodifiableList(this.encoders), Collections.unmodifiableList(this.decoders), this.serverEndpointConfigurator);
        }

        private Builder(Class endpointClass, String path) {
            if (endpointClass == null) {
                throw new IllegalArgumentException("endpointClass cannot be null");
            }
            this.endpointClass = endpointClass;
            if (path == null || !path.startsWith("/")) {
                throw new IllegalStateException("Path cannot be null and must begin with /");
            }
            this.path = path;
        }

        public Builder encoders(List<Class<? extends Encoder>> encoders) {
            this.encoders = encoders == null ? new ArrayList() : encoders;
            return this;
        }

        public Builder decoders(List<Class<? extends Decoder>> decoders) {
            this.decoders = decoders == null ? new ArrayList() : decoders;
            return this;
        }

        public Builder subprotocols(List<String> subprotocols) {
            this.subprotocols = subprotocols == null ? new ArrayList() : subprotocols;
            return this;
        }

        public Builder extensions(List<Extension> extensions) {
            this.extensions = extensions == null ? new ArrayList() : extensions;
            return this;
        }

        public Builder configurator(Configurator serverEndpointConfigurator) {
            this.serverEndpointConfigurator = serverEndpointConfigurator;
            return this;
        }
    }

    public static class Configurator {
        private Configurator containerDefaultConfigurator;

        static Configurator fetchContainerDefaultConfigurator() {
            Iterator<Configurator> iterator = ServiceLoader.load(Configurator.class).iterator();
            if (iterator.hasNext()) {
                Configurator impl = iterator.next();
                return impl;
            }
            throw new RuntimeException("Cannot load platform configurator");
        }

        Configurator getContainerDefaultConfigurator() {
            if (this.containerDefaultConfigurator == null) {
                this.containerDefaultConfigurator = Configurator.fetchContainerDefaultConfigurator();
            }
            return this.containerDefaultConfigurator;
        }

        public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
            return this.getContainerDefaultConfigurator().getNegotiatedSubprotocol(supported, requested);
        }

        public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
            return this.getContainerDefaultConfigurator().getNegotiatedExtensions(installed, requested);
        }

        public boolean checkOrigin(String originHeaderValue) {
            return this.getContainerDefaultConfigurator().checkOrigin(originHeaderValue);
        }

        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        }

        public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
            return this.getContainerDefaultConfigurator().getEndpointInstance(endpointClass);
        }
    }

}

