/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportTimeoutEnforced
implements IReport {
    private String fCoach;

    public ReportTimeoutEnforced() {
    }

    public ReportTimeoutEnforced(String pCoach) {
        this.fCoach = pCoach;
    }

    @Override
    public ReportId getId() {
        return ReportId.TIMEOUT_ENFORCED;
    }

    public String getCoach() {
        return this.fCoach;
    }

    @Override
    public IReport transform() {
        return new ReportTimeoutEnforced(this.getCoach());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        return jsonObject;
    }

    @Override
    public ReportTimeoutEnforced initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        return this;
    }
}

