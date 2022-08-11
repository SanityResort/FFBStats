package org.butterbrot.ffb.stats.adapter.bb2020;

import com.fumbbl.ffb.report.ReportId;

public class TurnOverDescription extends org.butterbrot.ffb.stats.adapter.bb2016.TurnOverDescription {

	public TurnOverDescription() {
		super();
		descriptions.put(ReportId.THROWN_KEG, "Beer Barrel Bash!");
	}

}
