package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportReferee implements IReport {
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
    public ReportReferee initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fFoulingPlayerBanned = IJsonOption.FOULING_PLAYER_BANNED.getFrom(jsonObject);
        return this;
    }
}

