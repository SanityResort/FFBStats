/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import javax.websocket.EncodeException;
import javax.websocket.SendHandler;

public interface RemoteEndpoint {
    public void setBatchingAllowed(boolean var1) throws IOException;

    public boolean getBatchingAllowed();

    public void flushBatch() throws IOException;

    public void sendPing(ByteBuffer var1) throws IOException, IllegalArgumentException;

    public void sendPong(ByteBuffer var1) throws IOException, IllegalArgumentException;

    public static interface Basic
    extends RemoteEndpoint {
        public void sendText(String var1) throws IOException;

        public void sendBinary(ByteBuffer var1) throws IOException;

        public void sendText(String var1, boolean var2) throws IOException;

        public void sendBinary(ByteBuffer var1, boolean var2) throws IOException;

        public OutputStream getSendStream() throws IOException;

        public Writer getSendWriter() throws IOException;

        public void sendObject(Object var1) throws IOException, EncodeException;
    }

    public static interface Async
    extends RemoteEndpoint {
        public long getSendTimeout();

        public void setSendTimeout(long var1);

        public void sendText(String var1, SendHandler var2);

        public Future<Void> sendText(String var1);

        public Future<Void> sendBinary(ByteBuffer var1);

        public void sendBinary(ByteBuffer var1, SendHandler var2);

        public Future<Void> sendObject(Object var1);

        public void sendObject(Object var1, SendHandler var2);
    }

}

