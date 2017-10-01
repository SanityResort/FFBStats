/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.xml;

import javax.xml.transform.sax.TransformerHandler;

public interface IXmlWriteable {
    public void addToXml(TransformerHandler var1);

    public String toXml(boolean var1);
}

