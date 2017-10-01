/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.xml;

import org.xml.sax.Attributes;

public interface IXmlReadable {
    public IXmlReadable startXmlElement(String var1, Attributes var2);

    public boolean endXmlElement(String var1, String var2);
}

