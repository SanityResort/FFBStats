/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.ReRollSource;
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

public class ReportReRoll
implements IReport {
    private String fPlayerId;
    private ReRollSource fReRollSource;
    private boolean fSuccessful;
    private int fRoll;

    public ReportReRoll() {
    }

    public ReportReRoll(String pPlayerId, ReRollSource pReRollSource, boolean pSuccessful, int pRoll) {
        this.fPlayerId = pPlayerId;
        this.fReRollSource = pReRollSource;
        this.fSuccessful = pSuccessful;
        this.fRoll = pRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.RE_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public ReRollSource getReRollSource() {
        return this.fReRollSource;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    public int getRoll() {
        return this.fRoll;
    }

    @Override
    public IReport transform() {
        return new ReportReRoll(this.getPlayerId(), this.getReRollSource(), this.isSuccessful(), this.getRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.RE_ROLL_SOURCE.addTo(jsonObject, this.fReRollSource);
        IJsonOption.SUCCESSFUL.addTo(jsonObject, this.fSuccessful);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        return jsonObject;
    }

    @Override
    public ReportReRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fReRollSource = (ReRollSource)IJsonOption.RE_ROLL_SOURCE.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        return this;
    }
}

