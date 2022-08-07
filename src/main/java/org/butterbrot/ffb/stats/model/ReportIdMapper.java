package org.butterbrot.ffb.stats.model;

import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.property.NamedProperties;
import com.fumbbl.ffb.report.ReportId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ReportIdMapper {
	private final Map<ReportId, StatKey> reportIdMapping =  new HashMap<ReportId, StatKey>() {{
		put(ReportId.BRIBES_ROLL, StatKey.BRIBE);
		put(ReportId.BLOOD_LUST_ROLL, StatKey.BLOOD_LUST);
		put(ReportId.CONFUSION_ROLL, StatKey.CONFUSION);
		put(ReportId.HYPNOTIC_GAZE_ROLL, StatKey.HYPNOTIC_GAZE);
	}};
	private final Set<ReportId> alwaysMap = new HashSet<ReportId>() {{
		add(ReportId.BRIBES_ROLL);
	}};
	private final Set<ReportId> mapOnSuccess = new HashSet<ReportId>() {{
		add(ReportId.BLOOD_LUST_ROLL);
		add(ReportId.CONFUSION_ROLL);
	}};
	private final Set<ReportId> mapOnFailure = new HashSet<ReportId>() {{
		add(ReportId.HYPNOTIC_GAZE_ROLL);
	}};
	private final Map<Boolean, Set<ReportId>> successMap = new HashMap<Boolean, Set<ReportId>>() {{
		put(true, mapOnSuccess);
		put(false, mapOnFailure);
	}};

	public Optional<StatKey> map(ReportId reportId, boolean success, Optional<Player<?>> player) {
		if (alwaysMap.contains(reportId) || successMap.get(success).contains(reportId)) {
			return Optional.ofNullable(subMap(reportIdMapping.get(reportId), player));
		}
		return Optional.empty();
	}

	private StatKey subMap(StatKey statKey, Optional<Player<?>> player) {
		if (player.isPresent() && statKey == StatKey.CONFUSION) {
			if (player.get().hasSkillProperty(NamedProperties.becomesImmovable)) {
				return StatKey.TAKE_ROOT;
			}
			if (player.get().hasSkillProperty(NamedProperties.needsToRollForActionButKeepsTacklezone)) {
				return StatKey.WILD_ANIMAL;
			}
		}

		return statKey;
	}
}
