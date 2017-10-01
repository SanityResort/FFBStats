/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.Skill;

public enum ReRolledAction implements INamedObject
{
    GO_FOR_IT("Go For It"),
    DODGE("Dodge"),
    CATCH("Catch"),
    PICK_UP("Pick Up"),
    PASS(Skill.PASS),
    DAUNTLESS(Skill.DAUNTLESS),
    LEAP(Skill.LEAP),
    FOUL_APPEARANCE(Skill.FOUL_APPEARANCE),
    BLOCK("Block"),
    REALLY_STUPID(Skill.REALLY_STUPID),
    BONE_HEAD(Skill.BONE_HEAD),
    WILD_ANIMAL(Skill.WILD_ANIMAL),
    TAKE_ROOT(Skill.TAKE_ROOT),
    WINNINGS("Winnings"),
    ALWAYS_HUNGRY(Skill.ALWAYS_HUNGRY),
    THROW_TEAM_MATE(Skill.THROW_TEAM_MATE),
    RIGHT_STUFF(Skill.RIGHT_STUFF),
    SHADOWING_ESCAPE("Shadowing Escape"),
    TENTACLES_ESCAPE("Tentacles Escape"),
    ESCAPE("Escape"),
    SAFE_THROW(Skill.SAFE_THROW),
    INTERCEPTION("Interception"),
    JUMP_UP(Skill.JUMP_UP),
    STAND_UP("standUp"),
    CHAINSAW(Skill.CHAINSAW),
    BLOOD_LUST(Skill.BLOOD_LUST),
    HYPNOTIC_GAZE(Skill.HYPNOTIC_GAZE),
    ANIMOSITY(Skill.ANIMOSITY);
    
    private String fName;
    private Skill fSkill;

    private ReRolledAction(String pName) {
        this.fName = pName;
        this.fSkill = null;
    }

    private ReRolledAction(Skill pSkill) {
        this.fSkill = pSkill;
        this.fName = pSkill.getName();
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public Skill getSkill() {
        return this.fSkill;
    }
}

