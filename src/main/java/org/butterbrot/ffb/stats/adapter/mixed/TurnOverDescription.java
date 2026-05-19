package org.butterbrot.ffb.stats.adapter.mixed;

import com.fumbbl.ffb.report.ReportId;

public class TurnOverDescription extends org.butterbrot.ffb.stats.adapter.bb2016.TurnOverDescription {

	public TurnOverDescription() {
		super();
		descriptions.put(ReportId.THROWN_KEG, "Beer Barrel Bash!");
		descriptions.put(ReportId.THROW_AT_STALLING_PLAYER, "Stalling");
		descriptions.put(ReportId.THROW_TEAM_MATE_ROLL, "Hit by TTM");
	}

}
