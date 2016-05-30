/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportStandUpRoll
implements IReport {
    private String fPlayerId;
    private boolean fSuccessful;
    private int fRoll;
    private boolean fReRolled;

    public ReportStandUpRoll() {
    }

    public ReportStandUpRoll(String pPlayerId, boolean pSuccessful, int pRoll, boolean pReRolled) {
        this.fPlayerId = pPlayerId;
        this.fSuccessful = pSuccessful;
        this.fRoll = pRoll;
        this.fReRolled = pReRolled;
    }

    @Override
    public ReportId getId() {
        return ReportId.STAND_UP_ROLL;
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

    public boolean isReRolled() {
        return this.fReRolled;
    }

    @Override
    public IReport transform() {
        return new ReportStandUpRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.isReRolled());
    }

    @Override
    public ReportStandUpRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fReRolled = IJsonOption.RE_ROLLED.getFrom(jsonObject);
        return this;
    }
}

