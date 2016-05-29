/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportThrowTeamMateRoll
extends ReportSkillRoll {
    private String fThrownPlayerId;
    private PassingDistance fPassingDistance;

    public ReportThrowTeamMateRoll() {
        super(ReportId.THROW_TEAM_MATE_ROLL);
    }

    public ReportThrowTeamMateRoll(String pThrowerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, PassingDistance pPassingDistance, String pThrownPlayerId) {
        super(ReportId.THROW_TEAM_MATE_ROLL, pThrowerId, pSuccessful, pRoll, pMinimumRoll, pReRolled);
        this.fThrownPlayerId = pThrownPlayerId;
        this.fPassingDistance = pPassingDistance;
    }

    public String getThrownPlayerId() {
        return this.fThrownPlayerId;
    }

    public PassingDistance getPassingDistance() {
        return this.fPassingDistance;
    }

    @Override
    public IReport transform() {
        return new ReportThrowTeamMateRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getPassingDistance(), this.getThrownPlayerId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = UtilJson.toJsonObject(super.toJsonValue());
        IJsonOption.THROWN_PLAYER_ID.addTo(jsonObject, this.fThrownPlayerId);
        IJsonOption.PASSING_DISTANCE.addTo(jsonObject, this.fPassingDistance);
        return jsonObject;
    }

    @Override
    public ReportThrowTeamMateRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fThrownPlayerId = IJsonOption.THROWN_PLAYER_ID.getFrom(jsonObject);
        this.fPassingDistance = (PassingDistance)IJsonOption.PASSING_DISTANCE.getFrom(jsonObject);
        return this;
    }
}

