/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import javax.websocket.DecodeException;
import javax.websocket.EndpointConfig;

public interface Decoder {
    public void init(EndpointConfig var1);

    public void destroy();

    public static interface TextStream<T>
    extends Decoder {
        public T decode(Reader var1) throws DecodeException, IOException;
    }

    public static interface Text<T>
    extends Decoder {
        public T decode(String var1) throws DecodeException;

        public boolean willDecode(String var1);
    }

    public static interface BinaryStream<T>
    extends Decoder {
        public T decode(InputStream var1) throws DecodeException, IOException;
    }

    public static interface Binary<T>
    extends Decoder {
        public T decode(ByteBuffer var1) throws DecodeException;

        public boolean willDecode(ByteBuffer var1);
    }

}

