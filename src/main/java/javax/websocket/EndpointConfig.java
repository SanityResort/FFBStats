/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.util.List;
import java.util.Map;

public interface EndpointConfig {
    public List<Class<? extends Encoder>> getEncoders();

    public List<Class<? extends Decoder>> getDecoders();

    public Map<String, Object> getUserProperties();
}
