/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.InterceptionModifier;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.List;

public class ReportInterceptionRoll
extends ReportSkillRoll {
    private boolean fBomb;

    public ReportInterceptionRoll() {
        super(ReportId.INTERCEPTION_ROLL);
    }

    public ReportInterceptionRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, InterceptionModifier[] pModifiers, boolean pBomb) {
        super(ReportId.INTERCEPTION_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled, pModifiers);
        this.fBomb = pBomb;
    }

    @Override
    public ReportId getId() {
        return ReportId.INTERCEPTION_ROLL;
    }

    public InterceptionModifier[] getRollModifiers() {
        return this.getRollModifierList().toArray(new InterceptionModifier[this.getRollModifierList().size()]);
    }

    public boolean isBomb() {
        return this.fBomb;
    }

    @Override
    public IReport transform() {
        return new ReportInterceptionRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getRollModifiers(), this.isBomb());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = UtilJson.toJsonObject(super.toJsonValue());
        IJsonOption.BOMB.addTo(jsonObject, this.fBomb);
        return jsonObject;
    }

    @Override
    public ReportInterceptionRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fBomb = IJsonOption.BOMB.getFrom(jsonObject);
        return this;
    }
}

