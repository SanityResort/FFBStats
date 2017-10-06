package org.butterbrot.ffb.stats.adapter;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DummyReport implements IReport {
    @Override
    public ReportId getId() {
        return ReportId.DUMMY;
    }

    @Override
    public IReport transform() {
        return new DummyReport();
    }

    @Override
    public DummyReport initFrom(JsonValue var1) {
        return new DummyReport();
    }

    @Override
    public JsonValue toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, getId());
        return jsonObject;
    }
}
