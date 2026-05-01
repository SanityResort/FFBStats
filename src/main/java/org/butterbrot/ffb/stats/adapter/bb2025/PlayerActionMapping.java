package org.butterbrot.ffb.stats.adapter.bb2025;

import com.fumbbl.ffb.PlayerAction;
import com.google.common.collect.Sets;

public class PlayerActionMapping extends org.butterbrot.ffb.stats.adapter.mixed.PlayerActionMapping {

	public PlayerActionMapping() {
		super();
		actionAliasMap.put(PlayerAction.SECURE_THE_BALL, Sets.newHashSet(PlayerAction.SECURE_THE_BALL));
	}
}
