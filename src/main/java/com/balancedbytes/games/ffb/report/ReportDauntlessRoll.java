/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportDauntlessRoll
extends ReportSkillRoll {
    private int fStrength;

    public ReportDauntlessRoll() {
        super(ReportId.DAUNTLESS_ROLL);
    }

    public ReportDauntlessRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, int pStrength) {
        super(ReportId.DAUNTLESS_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled);
        this.fStrength = pStrength;
    }

    @Override
    public ReportId getId() {
        return ReportId.DAUNTLESS_ROLL;
    }

    public int getStrength() {
        return this.fStrength;
    }

    @Override
    public IReport transform() {
        return new ReportDauntlessRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getStrength());
    }

    @Override
    public ReportDauntlessRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fStrength = IJsonOption.STRENGTH.getFrom(jsonObject);
        return this;
    }
}

