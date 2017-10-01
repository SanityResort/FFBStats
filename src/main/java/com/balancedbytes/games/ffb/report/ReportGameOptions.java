/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportGameOptions
implements IReport {
    private boolean fOvertime;
    private int fTurntime;
    private boolean fSneakyGitAsFoulGuard;
    private boolean fFoulBonusOutsideTacklezone;
    private boolean fRightStuffCancelsTackle;
    private boolean fPilingOnWithoutModifier;

    public void init(ReportGameOptions pReportGameOptions) {
        if (pReportGameOptions != null) {
            this.fOvertime = pReportGameOptions.isOvertime();
            this.fTurntime = pReportGameOptions.getTurntime();
            this.fSneakyGitAsFoulGuard = pReportGameOptions.isSneakyGitAsFoulGuard();
            this.fFoulBonusOutsideTacklezone = pReportGameOptions.isFoulBonusOutsideTacklezone();
            this.fRightStuffCancelsTackle = pReportGameOptions.isRightStuffCancelsTackle();
            this.fPilingOnWithoutModifier = pReportGameOptions.isPilingOnWithoutModifier();
        }
    }

    @Override
    public ReportId getId() {
        return ReportId.GAME_OPTIONS;
    }

    public boolean isOvertime() {
        return this.fOvertime;
    }

    public int getTurntime() {
        return this.fTurntime;
    }

    public boolean isSneakyGitAsFoulGuard() {
        return this.fSneakyGitAsFoulGuard;
    }

    public boolean isFoulBonusOutsideTacklezone() {
        return this.fFoulBonusOutsideTacklezone;
    }

    public boolean isRightStuffCancelsTackle() {
        return this.fRightStuffCancelsTackle;
    }

    public boolean isPilingOnWithoutModifier() {
        return this.fPilingOnWithoutModifier;
    }

    @Override
    public IReport transform() {
        ReportGameOptions transformedReport = new ReportGameOptions();
        transformedReport.init(this);
        return transformedReport;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        return jsonObject;
    }

    @Override
    public ReportGameOptions initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        return this;
    }
}

