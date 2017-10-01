/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonReadable;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.IJsonWriteable;
import com.balancedbytes.games.ffb.report.ReportId;

public interface IReport
extends IJsonReadable, IJsonWriteable {
    public static final String XML_TAG = "report";
    public static final String XML_ATTRIBUTE_ID = "id";

    public ReportId getId();

    public IReport transform();
}

