package org.butterbrot.ffb.stats.adapter;

import refactored.com.balancedbytes.games.ffb.report.IReport;
import refactored.com.balancedbytes.games.ffb.report.ReportId;
import repackaged.com.eclipsesource.json.JsonValue;

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

}
