package org.butterbrot.ffb.stats.adapter;

import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.report.ReportPilingOn;

public class ReportPoInjury extends ReportInjury {
    private ReportPilingOn poReport;

    public ReportPoInjury(ReportInjury reportInjury, ReportPilingOn poReport) {
        super(reportInjury.getDefenderId(), reportInjury.getInjuryType(), reportInjury.isArmorBroken(), reportInjury
                .getArmorModifiers(), reportInjury.getArmorRoll(), reportInjury.getInjuryModifiers(), reportInjury
                .getInjuryRoll(), reportInjury.getCasualtyRoll(), reportInjury.getSeriousInjury(), reportInjury
                .getCasualtyRollDecay(), reportInjury.getSeriousInjuryDecay(), reportInjury.getInjury(), reportInjury
                .getInjuryDecay(), reportInjury.getAttackerId());
        this.poReport = poReport;
    }

    public ReportPilingOn getPoReport() {
        return poReport;
    }

    public void setPoReport(ReportPilingOn poReport) {
        this.poReport = poReport;
    }
}
