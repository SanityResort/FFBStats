/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.CatchModifier;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportCatchRoll
extends ReportSkillRoll {
    private boolean fBomb;

    public ReportCatchRoll() {
        super(ReportId.CATCH_ROLL);
    }

    public ReportCatchRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, CatchModifier[] pRollModifiers, boolean pBomb) {
        super(ReportId.CATCH_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled, pRollModifiers);
        this.fBomb = pBomb;
    }

    public boolean isBomb() {
        return this.fBomb;
    }

    public CatchModifier[] getRollModifiers() {
        return this.getRollModifierList().toArray(new CatchModifier[this.getRollModifierList().size()]);
    }

    @Override
    public IReport transform() {
        return new ReportCatchRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getRollModifiers(), this.isBomb());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = UtilJson.toJsonObject(super.toJsonValue());
        IJsonOption.BOMB.addTo(jsonObject, this.fBomb);
        return jsonObject;
    }

    @Override
    public ReportCatchRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fBomb = IJsonOption.BOMB.getFrom(jsonObject);
        return this;
    }
}

