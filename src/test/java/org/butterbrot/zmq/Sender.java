package org.butterbrot.zmq;

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
            publisher.send(new Msg("860124".getBytes(ZMQ.CHARSET)), 0);
        }
    }
}
