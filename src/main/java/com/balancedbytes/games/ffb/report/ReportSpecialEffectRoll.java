/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportSpecialEffectRoll
implements IReport {
    private SpecialEffect fSpecialEffect;
    private String fPlayerId;
    private int fRoll;
    private boolean fSuccessful;

    public ReportSpecialEffectRoll() {
    }

    public ReportSpecialEffectRoll(SpecialEffect pSpecialEffect, String pPlayerId, int pRoll, boolean pSuccessful) {
        this.fSpecialEffect = pSpecialEffect;
        this.fPlayerId = pPlayerId;
        this.fRoll = pRoll;
        this.fSuccessful = pSuccessful;
    }

    @Override
    public ReportId getId() {
        return ReportId.SPELL_EFFECT_ROLL;
    }

    public SpecialEffect getSpecialEffect() {
        return this.fSpecialEffect;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public int getRoll() {
        return this.fRoll;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    @Override
    public IReport transform() {
        return new ReportSpecialEffectRoll(this.getSpecialEffect(), this.getPlayerId(), this.getRoll(), this.isSuccessful());
    }

    @Override
    public ReportSpecialEffectRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSpecialEffect = (SpecialEffect)IJsonOption.SPECIAL_EFFECT.getFrom(jsonObject);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        return this;
    }
}

