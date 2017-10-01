/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportArgueTheCallRoll
implements IReport {
    private String fPlayerId;
    private boolean fSuccessful;
    private boolean fCoachBanned;
    private int fRoll;

    public ReportArgueTheCallRoll() {
    }

    public ReportArgueTheCallRoll(String playerId, boolean successful, boolean coachBanned, int roll) {
        this.fPlayerId = playerId;
        this.fSuccessful = successful;
        this.fCoachBanned = coachBanned;
        this.fRoll = roll;
    }

    @Override
    public ReportId getId() {
        return ReportId.ARGUE_THE_CALL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    public boolean isCoachBanned() {
        return this.fCoachBanned;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        return new ReportArgueTheCallRoll(this.getPlayerId(), this.isSuccessful(), this.isCoachBanned(), this.getRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.SUCCESSFUL.addTo(jsonObject, this.fSuccessful);
        IJsonOption.COACH_BANNED.addTo(jsonObject, this.fCoachBanned);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        return jsonObject;
    }

    @Override
    public ReportArgueTheCallRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fCoachBanned = IJsonOption.COACH_BANNED.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

