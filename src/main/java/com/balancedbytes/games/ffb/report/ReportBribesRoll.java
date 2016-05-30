/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportBribesRoll
implements IReport {
    private String fPlayerId;
    private boolean fSuccessful;
    private int fRoll;

    public ReportBribesRoll() {
    }

    public ReportBribesRoll(String pPlayerId, boolean pSuccessful, int pRoll) {
        this.fPlayerId = pPlayerId;
        this.fSuccessful = pSuccessful;
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.BRIBES_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        return new ReportBribesRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll());
    }

    @Override
    public ReportBribesRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

