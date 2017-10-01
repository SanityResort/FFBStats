/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import javax.websocket.Session;

public class SessionException
extends Exception {
    private final Session session;
    private static final long serialVersionUID = 12;

    public SessionException(String message, Throwable cause, Session session) {
        super(message, cause);
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }
}

