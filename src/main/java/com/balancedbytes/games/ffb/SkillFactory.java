/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.Skill;

public class SkillFactory
implements INamedObjectFactory {
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
}

