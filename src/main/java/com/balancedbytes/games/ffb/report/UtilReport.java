/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;

public class UtilReport {
    public static void validateReportId(IReport pReport, ReportId pReceivedId) {
        if (pReport == null) {
            throw new IllegalArgumentException("Parameter report must not be null.");
        }
        if (pReport.getId() != pReceivedId) {
            throw new IllegalStateException("Wrong report id. Expected " + pReport.getId().getName() + " received " + (pReceivedId != null ? pReceivedId.getName() : "null"));
        }
    }
}

