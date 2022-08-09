package org.butterbrot.ffb.stats.adapter;

import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.modifiers.ArmorModifier;
import com.fumbbl.ffb.report.ReportPilingOn;

public interface ExposingInjuryReport {
	String getAttackerId();
	String getDefenderId();

	ArmorModifier[] getArmorModifiers();

	PlayerState getInjury();

	ReportPilingOn getPoReport();
}
