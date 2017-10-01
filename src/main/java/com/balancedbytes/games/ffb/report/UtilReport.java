/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

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

