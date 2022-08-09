package org.butterbrot.ffb.stats.adapter.bb2016;

import com.fumbbl.ffb.report.bb2016.ReportInjury;
import com.fumbbl.ffb.report.ReportPilingOn;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;

public class ReportPoInjury extends ReportInjury implements ExposingInjuryReport {
    private final ReportPilingOn poReport;

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
}
