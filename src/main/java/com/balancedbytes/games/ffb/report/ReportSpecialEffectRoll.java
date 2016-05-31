package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportSpecialEffectRoll implements IReport {
    private String fPlayerId;
    private int fRoll;

    public ReportSpecialEffectRoll() {
    }

    public ReportSpecialEffectRoll(String pPlayerId, int pRoll) {
        this.fPlayerId = pPlayerId;
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.SPELL_EFFECT_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        return new ReportSpecialEffectRoll(this.getPlayerId(), this.getRoll());
    }

    @Override
    public ReportSpecialEffectRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

