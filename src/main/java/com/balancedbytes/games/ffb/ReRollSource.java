/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.Skill;

public enum ReRollSource implements INamedObject
{
    TEAM_RE_ROLL("Team ReRoll"),
    DODGE(Skill.DODGE),
    PRO(Skill.PRO),
    SURE_FEET(Skill.SURE_FEET),
    SURE_HANDS(Skill.SURE_HANDS),
    CATCH(Skill.CATCH),
    PASS(Skill.PASS),
    WINNINGS("Winnings"),
    LONER(Skill.LONER),
    LEADER(Skill.LEADER),
    MONSTROUS_MOUTH(Skill.MOUNSTROUS_MOUTH);
    
    private int fId;
    private String fName;
    private Skill fSkill;

    private ReRollSource(String pName) {
        this.fName = pName;
    }

    private ReRollSource(Skill pSkill) {
        this(pSkill.getName());
        this.fSkill = pSkill;
    }

    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public Skill getSkill() {
        return this.fSkill;
    }
}

