/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.SkillUse;
import com.balancedbytes.games.ffb.util.StringTool;

public class SkillUseFactory
implements INamedObjectFactory {
    @Override
    public SkillUse forName(String pName) {
        if (StringTool.isProvided(pName)) {
            for (SkillUse skillUse : SkillUse.values()) {
                if (!pName.equalsIgnoreCase(skillUse.getName())) continue;
                return skillUse;
            }
        }
        return null;
    }
}

