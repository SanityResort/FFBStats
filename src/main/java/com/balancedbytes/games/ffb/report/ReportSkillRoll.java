/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportSkillRoll
implements IReport {
    private ReportId fId;
    private String fPlayerId;
    private boolean fSuccessful;
    private int fRoll;
    private int fMinimumRoll;
    private boolean fReRolled;

    public ReportSkillRoll(ReportId pId) {
        this.fId = pId;
    }

    public ReportSkillRoll(ReportId pId, String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled) {
        this.fId = pId;
        this.fPlayerId = pPlayerId;
        this.fSuccessful = pSuccessful;
        this.fRoll = pRoll;
        this.fMinimumRoll = pMinimumRoll;
        this.fReRolled = pReRolled;
    }

    @Override
    public ReportId getId() {
        return this.fId;
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

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    public boolean isReRolled() {
        return this.fReRolled;
    }

    @Override
    public IReport transform() {
        return new ReportSkillRoll(this.getId(), this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.fId);
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.SUCCESSFUL.addTo(jsonObject, this.fSuccessful);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        IJsonOption.RE_ROLLED.addTo(jsonObject, this.fReRolled);
        return jsonObject;
    }

    @Override
    public ReportSkillRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fReRolled = IJsonOption.RE_ROLLED.getFrom(jsonObject);

        return this;
    }
}

