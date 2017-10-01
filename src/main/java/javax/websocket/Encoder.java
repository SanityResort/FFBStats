/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

public interface Encoder {
    public void init(EndpointConfig var1);

    public void destroy();

    public static interface BinaryStream<T>
    extends Encoder {
        public void encode(T var1, OutputStream var2) throws EncodeException, IOException;
    }

    public static interface Binary<T>
    extends Encoder {
        public ByteBuffer encode(T var1) throws EncodeException;
    }

    public static interface TextStream<T>
    extends Encoder {
        public void encode(T var1, Writer var2) throws EncodeException, IOException;
    }

    public static interface Text<T>
    extends Encoder {
        public String encode(T var1) throws EncodeException;
    }

}

