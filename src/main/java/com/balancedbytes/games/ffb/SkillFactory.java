/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.Skill;

public class SkillFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public Skill forName(String pName) {
        for (Skill skill : Skill.values()) {
            if (!skill.getName().equalsIgnoreCase(pName)) continue;
            return skill;
        }
        if ("Ball & Chain".equalsIgnoreCase(pName) || "Ball &amp; Chain".equalsIgnoreCase(pName)) {
            return Skill.BALL_AND_CHAIN;
        }
        return null;
    }

    @Override
    public Skill forId(int pId) {
        if (pId > 0) {
            for (Skill skill : Skill.values()) {
                if (pId != skill.getId()) continue;
                return skill;
            }
        }
        return null;
    }
}

