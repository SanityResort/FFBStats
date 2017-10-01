/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket.server;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.server.ServerEndpointConfig;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface ServerEndpoint {
    public String value();

    public String[] subprotocols() default {};

    public Class<? extends Decoder>[] decoders() default {};

    public Class<? extends Encoder>[] encoders() default {};

    public Class<? extends ServerEndpointConfig.Configurator> configurator() default ServerEndpointConfig.Configurator.class;
}

