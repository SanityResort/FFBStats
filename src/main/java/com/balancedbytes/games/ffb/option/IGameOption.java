/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.json.IJsonWriteable;
import com.balancedbytes.games.ffb.xml.IXmlWriteable;

public interface IGameOption
extends IJsonWriteable,
IXmlWriteable {
    public static final String XML_TAG = "option";
    public static final String XML_ATTRIBUTE_NAME = "name";
    public static final String XML_ATTRIBUTE_VALUE = "value";

    public GameOptionId getId();

    public String getValueAsString();

    public IGameOption setValue(String var1);

    public boolean isChanged();

    public String getDisplayMessage();
}

