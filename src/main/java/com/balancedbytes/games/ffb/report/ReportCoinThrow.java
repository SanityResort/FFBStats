/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
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

public class ReportCoinThrow
implements IReport {
    private boolean fCoinThrowHeads;
    private String fCoach;
    private boolean fCoinChoiceHeads;

    public ReportCoinThrow() {
    }

    public ReportCoinThrow(boolean pCoinThrowHeads, String pCoach, boolean pCoinChoiceHeads) {
        this.fCoinThrowHeads = pCoinThrowHeads;
        this.fCoach = pCoach;
        this.fCoinChoiceHeads = pCoinChoiceHeads;
    }

    @Override
    public ReportId getId() {
        return ReportId.COIN_THROW;
    }

    public boolean isCoinThrowHeads() {
        return this.fCoinThrowHeads;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public boolean isCoinChoiceHeads() {
        return this.fCoinChoiceHeads;
    }

    @Override
    public IReport transform() {
        return new ReportCoinThrow(this.isCoinThrowHeads(), this.getCoach(), this.isCoinChoiceHeads());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        IJsonOption.COIN_THROW_HEADS.addTo(jsonObject, this.fCoinThrowHeads);
        IJsonOption.COIN_CHOICE_HEADS.addTo(jsonObject, this.fCoinChoiceHeads);
        return jsonObject;
    }

    @Override
    public ReportCoinThrow initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.fCoinThrowHeads = IJsonOption.COIN_THROW_HEADS.getFrom(jsonObject);
        this.fCoinChoiceHeads = IJsonOption.COIN_CHOICE_HEADS.getFrom(jsonObject);
        return this;
    }
}

