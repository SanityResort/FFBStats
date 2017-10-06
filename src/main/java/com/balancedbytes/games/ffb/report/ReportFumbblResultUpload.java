/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportFumbblResultUpload
implements IReport {
    private boolean fSuccessful;
    private String fUploadStatus;

    public ReportFumbblResultUpload() {
    }

    public ReportFumbblResultUpload(boolean pSuccessful, String pStatus) {
        this.fSuccessful = pSuccessful;
        this.fUploadStatus = pStatus;
    }

    @Override
    public ReportId getId() {
        return ReportId.FUMBBL_RESULT_UPLOAD;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    public String getUploadStatus() {
        return this.fUploadStatus;
    }

    @Override
    public IReport transform() {
        return new ReportFumbblResultUpload(this.isSuccessful(), this.getUploadStatus());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.SUCCESSFUL.addTo(jsonObject, this.fSuccessful);
        IJsonOption.UPLOAD_STATUS.addTo(jsonObject, this.fUploadStatus);
        return jsonObject;
    }

    @Override
    public ReportFumbblResultUpload initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fUploadStatus = IJsonOption.UPLOAD_STATUS.getFrom(jsonObject);
        return this;
    }
}
