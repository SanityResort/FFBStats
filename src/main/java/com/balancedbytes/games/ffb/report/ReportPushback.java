/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.PushbackMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPushback
implements IReport {
    private String fDefenderId;
    private PushbackMode fPushbackMode;

    public ReportPushback() {
    }

    public ReportPushback(String pDefenderId, PushbackMode pMode) {
        this();
        this.fDefenderId = pDefenderId;
        this.fPushbackMode = pMode;
    }

    @Override
    public ReportId getId() {
        return ReportId.PUSHBACK;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public PushbackMode getPushbackMode() {
        return this.fPushbackMode;
    }

    @Override
    public IReport transform() {
        return new ReportPushback(this.getDefenderId(), this.getPushbackMode());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.DEFENDER_ID.addTo(jsonObject, this.fDefenderId);
        IJsonOption.PUSHBACK_MODE.addTo(jsonObject, this.fPushbackMode);
        return jsonObject;
    }

    @Override
    public ReportPushback initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fPushbackMode = (PushbackMode)IJsonOption.PUSHBACK_MODE.getFrom(jsonObject);
        return this;
    }
}

