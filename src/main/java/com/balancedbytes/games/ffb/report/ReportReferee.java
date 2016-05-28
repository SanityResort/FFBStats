/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportReferee
implements IReport {
    private boolean fFoulingPlayerBanned;

    public ReportReferee() {
    }

    public ReportReferee(boolean pFoulingPlayerBanned) {
        this.fFoulingPlayerBanned = pFoulingPlayerBanned;
    }

    @Override
    public ReportId getId() {
        return ReportId.REFEREE;
    }

    public boolean isFoulingPlayerBanned() {
        return this.fFoulingPlayerBanned;
    }

    @Override
    public IReport transform() {
        return new ReportReferee(this.isFoulingPlayerBanned());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.FOULING_PLAYER_BANNED.addTo(jsonObject, this.fFoulingPlayerBanned);
        return jsonObject;
    }

    @Override
    public ReportReferee initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fFoulingPlayerBanned = IJsonOption.FOULING_PLAYER_BANNED.getFrom(jsonObject);
        return this;
    }
}

