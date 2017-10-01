/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface ClientEndpoint {
    public String[] subprotocols() default {};

    public Class<? extends Decoder>[] decoders() default {};

    public Class<? extends Encoder>[] encoders() default {};

    public Class<? extends ClientEndpointConfig.Configurator> configurator() default ClientEndpointConfig.Configurator.class;
}

