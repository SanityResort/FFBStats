/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportReceiveChoice
implements IReport {
    private String fTeamId;
    private boolean fReceiveChoice;

    public ReportReceiveChoice() {
    }

    public ReportReceiveChoice(String pTeamId, boolean pChoiceReceive) {
        this.fTeamId = pTeamId;
        this.fReceiveChoice = pChoiceReceive;
    }

    @Override
    public ReportId getId() {
        return ReportId.RECEIVE_CHOICE;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public boolean isReceiveChoice() {
        return this.fReceiveChoice;
    }

    @Override
    public IReport transform() {
        return new ReportReceiveChoice(this.getTeamId(), this.isReceiveChoice());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.RECEIVE_CHOICE.addTo(jsonObject, this.fReceiveChoice);
        return jsonObject;
    }

    @Override
    public ReportReceiveChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fReceiveChoice = IJsonOption.RECEIVE_CHOICE.getFrom(jsonObject);
        return this;
    }
}

