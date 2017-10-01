/*
 * Decompiled with CFR 0_122.
 */
package javax.websocket;

import java.util.List;

public interface Extension {
    public String getName();

    public List<Parameter> getParameters();

    public static interface Parameter {
        public String getName();

        public String getValue();
    }

}

