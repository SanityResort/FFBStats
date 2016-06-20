/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb;


import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import refactored.com.balancedbytes.games.ffb.report.UtilReport;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportStartHalf implements IReport {
    private int fHalf;

    public ReportStartHalf() {
    }

    public ReportStartHalf(int pHalf) {
        this.fHalf = pHalf;
    }

    @Override
    public ReportId getId() {
        return ReportId.START_HALF;
    }

    public int getHalf() {
        return this.fHalf;
    }

    @Override
    public IReport transform() {
        return new ReportStartHalf(this.getHalf());
    }

    @Override
    public ReportStartHalf initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fHalf = IJsonOption.HALF.getFrom(jsonObject);
        return this;
    }
}

