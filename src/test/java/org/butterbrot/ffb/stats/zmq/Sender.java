package org.butterbrot.ffb.stats.zmq;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

public class Sender {

    public static void main(String[] args) throws Exception {
        Ctx context = ZMQ.init(1);
        SocketBase publisher = ZMQ.socket(context, ZMQ.ZMQ_PUSH);
        publisher.bind("tcp://localhost:44444");
        while (true) {
            Thread.sleep(5000);
            publisher.send(new Msg("1005000".getBytes(ZMQ.CHARSET)), 0);
            System.out.println("sent");
        }
    }
}
