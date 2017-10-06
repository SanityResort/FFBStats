package org.butterbrot.ffb.stats.adapter;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.SpecialEffect;
import com.balancedbytes.games.ffb.report.ReportId;

import java.util.HashMap;
import java.util.Map;

public class TurnOverDescription {
    private static Map<INamedObject, String> DESCRIPTIONS = new HashMap<>();

   static {
        DESCRIPTIONS.put(ReportId.BLOCK_ROLL, "Block" );
        DESCRIPTIONS.put(ReportId.BLOOD_LUST_ROLL, "Bloodlust");
        DESCRIPTIONS.put(ReportId.CHAINSAW_ROLL, "Chainsaw");
        DESCRIPTIONS.put(ReportId.BRIBES_ROLL, "Bribe");
        DESCRIPTIONS.put(ReportId.BLOCK, "Block");
        DESCRIPTIONS.put(ReportId.HAND_OVER, "Hand Over");
        DESCRIPTIONS.put(ReportId.FOUL, "Foul");
        DESCRIPTIONS.put(ReportId.RIGHT_STUFF_ROLL, "Landing");
        DESCRIPTIONS.put(ReportId.PICK_UP_ROLL, "Pick up");
        DESCRIPTIONS.put(ReportId.PASS_ROLL, "Pass");
        DESCRIPTIONS.put(ReportId.LEAP_ROLL, "Leap");
        DESCRIPTIONS.put(ReportId.INTERCEPTION_ROLL, "Interception");
        DESCRIPTIONS.put(ReportId.GO_FOR_IT_ROLL, "Go for it");
        DESCRIPTIONS.put(ReportId.ESCAPE_ROLL, "Being eaten");
        DESCRIPTIONS.put(ReportId.DODGE_ROLL, "Dodge");
        DESCRIPTIONS.put(ReportId.CATCH_ROLL, "Catch");
        DESCRIPTIONS.put(SpecialEffect.BOMB, "Bomb");
        DESCRIPTIONS.put(SpecialEffect.FIREBALL, "Fireball");
        DESCRIPTIONS.put(SpecialEffect.LIGHTNING, "Lightning Bolt");
    }

    public static String get(INamedObject key) {
        String description = DESCRIPTIONS.get(key);
        return description != null ? description : key.getName();
    }
}
