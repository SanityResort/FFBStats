package org.butterbrot.zmq;

import zmq.Ctx;
import zmq.Msg;
import zmq.SocketBase;
import zmq.ZMQ;

public class Receiver {

    public static void main(String[] args) {
        Ctx context = ZMQ.init(1);
        SocketBase subscriber = ZMQ.socket(context, ZMQ.ZMQ_PULL);
        subscriber.connect("tcp://localhost:44445");
        while (true) {
            Msg msg = subscriber.recv(0);
            System.out.println(new String(msg.data()));
        }
    }
}
