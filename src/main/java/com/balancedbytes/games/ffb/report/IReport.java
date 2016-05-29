/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonSerializable;

public interface IReport
extends IJsonSerializable {
    public static final String XML_TAG = "report";
    public static final String XML_ATTRIBUTE_ID = "id";

    public ReportId getId();

    public IReport transform();
}

