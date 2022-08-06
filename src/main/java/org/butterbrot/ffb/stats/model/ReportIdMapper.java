package org.butterbrot.ffb.stats.model;

import com.fumbbl.ffb.report.ReportId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ReportIdMapper {
	private final Map<ReportId, StatKey> reportIdMapping =  new HashMap<ReportId, StatKey>() {{
		put(ReportId.BRIBES_ROLL, StatKey.BRIBE);
	}};
	private final Set<ReportId> alwaysMap = new HashSet<ReportId>() {{
		add(ReportId.BRIBES_ROLL);
	}};
	private final Set<ReportId> mapOnSuccess = new HashSet<ReportId>() {{}};
	private final Set<ReportId> mapOnFailure = new HashSet<ReportId>() {{}};
	private final Map<Boolean, Set<ReportId>> successMap = new HashMap<Boolean, Set<ReportId>>() {{
		put(true, mapOnSuccess);
		put(false, mapOnFailure);
	}};

	public Optional<StatKey> map(ReportId reportId, boolean success) {
		if (alwaysMap.contains(reportId) || successMap.get(success).contains(reportId)) {
			return Optional.ofNullable(reportIdMapping.get(reportId));
		}
		return Optional.empty();
	}
}
