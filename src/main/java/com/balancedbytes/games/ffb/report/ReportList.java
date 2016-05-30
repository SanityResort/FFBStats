/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonReadable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportList
implements IJsonReadable {
    private List<IReport> fReports;

    private ReportList(int pInitialCapacity) {
        this.fReports = new ArrayList<IReport>(pInitialCapacity);
    }

    public ReportList() {
        this(20);
    }

    public void add(IReport pReport) {
        this.fReports.add(pReport);
    }

    public boolean hasReport(ReportId pReportId) {
        boolean reportFound = false;
        for (IReport report : this.fReports) {
            if (report.getId() != pReportId) continue;
            reportFound = true;
        }
        return reportFound;
    }

    public void add(ReportList pReportList) {
        if (pReportList != null) {
            for (IReport report : pReportList.getReports()) {
                this.add(report);
            }
        }
    }

    public IReport[] getReports() {
        return this.fReports.toArray(new IReport[this.fReports.size()]);
    }

    public void clear() {
        this.fReports.clear();
    }

    public int size() {
        return this.fReports.size();
    }

    public ReportList copy() {
        ReportList copiedList = new ReportList(this.size());
        for (IReport report : this.fReports) {
            copiedList.add(report);
        }
        return copiedList;
    }

    public ReportList transform() {
        ReportList transformedList = new ReportList(this.size());
        for (IReport report : this.fReports) {
            transformedList.add(report.transform());
        }
        return transformedList;
    }

    @Override
    public ReportList initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        JsonArray reportArray = IJsonOption.REPORTS.getFrom(jsonObject);
        if (reportArray != null) {
            ReportFactory reportFactory = new ReportFactory();
            for (int i = 0; i < reportArray.size(); ++i) {
                this.add(reportFactory.forJsonValue(reportArray.get(i)));
            }
        }
        return this;
    }
}

