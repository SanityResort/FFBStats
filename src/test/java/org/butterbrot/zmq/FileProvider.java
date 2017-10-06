package org.butterbrot.zmq;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProvider {

    private static String filename = "src/test/resources/baseline/input/%s.gz";

    public static void main(String[] args) throws IOException {

        Ctx ctx = ZMQ.init(1);
        SocketBase sb = ZMQ.socket(ctx, ZMQ.ZMQ_REP);
        ZMQ.bind(sb, "tcp://localhost:44446");
        while (true) {
            Msg msg = sb.recv(0);
            String replayId = new String(msg.data());
            System.out.println("received");

            byte[] bytes = Files.readAllBytes(Paths.get(String.format(filename, replayId)));

            sb.send(new Msg(bytes), 0);
            System.out.println("sent");
        }
    }
}
