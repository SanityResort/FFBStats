/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.SkillUse;
import com.balancedbytes.games.ffb.util.StringTool;

public class SkillUseFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public SkillUse forId(int pId) {
        if (pId > 0) {
            for (SkillUse skillUse : SkillUse.values()) {
                if (pId != skillUse.getId()) continue;
                return skillUse;
            }
        }
        return null;
    }

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

