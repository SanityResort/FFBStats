/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.KickoffResult;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportKickoffExtraReRoll
implements IReport {
    private KickoffResult fKickoffResult;
    private int fRollHome;
    private boolean fHomeGainsReRoll;
    private int fRollAway;
    private boolean fAwayGainsReRoll;

    public ReportKickoffExtraReRoll() {
    }

    public ReportKickoffExtraReRoll(KickoffResult pKickoffResult, int pRollHome, boolean pHomeGainsReRoll, int pRollAway, boolean pAwayGainsReRoll) {
        this.fKickoffResult = pKickoffResult;
        this.fRollHome = pRollHome;
        this.fHomeGainsReRoll = pHomeGainsReRoll;
        this.fRollAway = pRollAway;
        this.fAwayGainsReRoll = pAwayGainsReRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_EXTRA_REROLL;
    }

    public KickoffResult getKickoffResult() {
        return this.fKickoffResult;
    }

    public int getRollHome() {
        return this.fRollHome;
    }

    public boolean isHomeGainsReRoll() {
        return this.fHomeGainsReRoll;
    }

    public int getRollAway() {
        return this.fRollAway;
    }

    public boolean isAwayGainsReRoll() {
        return this.fAwayGainsReRoll;
    }

    @Override
    public IReport transform() {
        return new ReportKickoffExtraReRoll(this.getKickoffResult(), this.getRollAway(), this.isAwayGainsReRoll(), this.getRollHome(), this.isHomeGainsReRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.KICKOFF_RESULT.addTo(jsonObject, this.fKickoffResult);
        IJsonOption.ROLL_HOME.addTo(jsonObject, this.fRollHome);
        IJsonOption.HOME_GAINS_RE_ROLL.addTo(jsonObject, this.fHomeGainsReRoll);
        IJsonOption.ROLL_AWAY.addTo(jsonObject, this.fRollAway);
        IJsonOption.AWAY_GAINS_RE_ROLL.addTo(jsonObject, this.fAwayGainsReRoll);
        return jsonObject;
    }

    @Override
    public ReportKickoffExtraReRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fKickoffResult = (KickoffResult)IJsonOption.KICKOFF_RESULT.getFrom(jsonObject);
        this.fRollHome = IJsonOption.ROLL_HOME.getFrom(jsonObject);
        this.fHomeGainsReRoll = IJsonOption.HOME_GAINS_RE_ROLL.getFrom(jsonObject);
        this.fRollAway = IJsonOption.ROLL_AWAY.getFrom(jsonObject);
        this.fAwayGainsReRoll = IJsonOption.AWAY_GAINS_RE_ROLL.getFrom(jsonObject);
        return this;
    }
}

