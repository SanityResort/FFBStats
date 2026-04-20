package org.butterbrot.ffb.stats.adapter.mixed;

import com.fumbbl.ffb.report.ReportPilingOn;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import org.butterbrot.ffb.stats.adapter.ExposingInjuryReport;

public class ReportPoInjury extends ReportInjury implements ExposingInjuryReport {
	private final ReportPilingOn poReport;

	public ReportPoInjury(ReportInjury reportInjury, ReportPilingOn poReport) {
		super(reportInjury.getDefenderId(), reportInjury.getInjuryType(), reportInjury.isArmorBroken(), reportInjury
			.getArmorModifiers(), reportInjury.getArmorRoll(), reportInjury.getInjuryModifiers(), reportInjury
			.getInjuryRoll(), reportInjury.getCasualtyRoll(), reportInjury.getSeriousInjury(), reportInjury
			.getCasualtyRollDecay(), reportInjury.getSeriousInjuryDecay(), reportInjury.getInjury(), reportInjury
			.getInjuryDecay(), reportInjury.getAttackerId(), reportInjury.getCasualtyModifiers(),
			reportInjury.getOriginalInjury(), reportInjury.getSkip());
		this.poReport = poReport;
	}

	public ReportPilingOn getPoReport() {
		return poReport;
	}
}
