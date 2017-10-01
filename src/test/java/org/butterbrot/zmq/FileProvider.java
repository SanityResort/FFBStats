package org.butterbrot.zmq;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProvider {

    private static String filename = "src/test/resources/1005000.gz";

    public static void main(String[] args) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get(filename));

        Ctx ctx = ZMQ.init(1);
        SocketBase sb = ZMQ.socket(ctx, ZMQ.ZMQ_REP);
        ZMQ.bind(sb, "tcp://localhost:44446");
        while (true) {
            sb.recv(0);
            System.out.println("received");
            sb.send(new Msg(bytes), 0);
            System.out.println("sent");
        }
    }
}
