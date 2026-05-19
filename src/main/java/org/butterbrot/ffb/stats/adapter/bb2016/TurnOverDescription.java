package org.butterbrot.ffb.stats.adapter.bb2016;

import com.fumbbl.ffb.INamedObject;
import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.report.ReportId;

import java.util.HashMap;
import java.util.Map;

public class TurnOverDescription {
	protected final Map<INamedObject, String> descriptions = new HashMap<>();

	public TurnOverDescription() {
		descriptions.put(ReportId.BLOCK_ROLL, "Block");
		descriptions.put(ReportId.BLOOD_LUST_ROLL, "Bloodlust");
		descriptions.put(ReportId.CHAINSAW_ROLL, "Chainsaw");
		descriptions.put(ReportId.BRIBES_ROLL, "Bribe");
		descriptions.put(ReportId.BLOCK, "Block");
		descriptions.put(ReportId.HAND_OVER, "Hand Over");
		descriptions.put(ReportId.FOUL, "Foul");
		descriptions.put(ReportId.RIGHT_STUFF_ROLL, "Landing");
		descriptions.put(ReportId.PICK_UP_ROLL, "Pick up");
		descriptions.put(ReportId.PASS_ROLL, "Pass");
		descriptions.put(ReportId.JUMP_ROLL, "Jump");
		descriptions.put(ReportId.INTERCEPTION_ROLL, "Interception");
		descriptions.put(ReportId.GO_FOR_IT_ROLL, "Go for it");
		descriptions.put(ReportId.ESCAPE_ROLL, "Being eaten");
		descriptions.put(ReportId.DODGE_ROLL, "Dodge");
		descriptions.put(ReportId.CATCH_ROLL, "Catch");
		descriptions.put(SpecialEffect.BOMB, "Bomb");
		descriptions.put(SpecialEffect.FIREBALL, "Fireball");
		descriptions.put(SpecialEffect.LIGHTNING, "Lightning Bolt");
	}

	public String get(INamedObject key) {
		String description = descriptions.get(key);
		return description != null ? description : key.getName();
	}
}
