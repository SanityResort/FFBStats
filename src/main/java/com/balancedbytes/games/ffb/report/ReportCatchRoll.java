/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

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

    public ReportCatchRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled,boolean pBomb) {
        super(ReportId.CATCH_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled);
        this.fBomb = pBomb;
    }

    public boolean isBomb() {
        return this.fBomb;
    }


    @Override
    public IReport transform() {
        return new ReportCatchRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.isBomb());
    }

    @Override
    public ReportCatchRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fBomb = IJsonOption.BOMB.getFrom(jsonObject);
        return this;
    }
}

