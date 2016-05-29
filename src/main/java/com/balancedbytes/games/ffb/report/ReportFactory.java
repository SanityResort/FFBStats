/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportFactory {
    public IReport forJsonValue(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        ReportId reportId = (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject);
        return (IReport)reportId.createReport().initFrom(jsonObject);
    }
}

