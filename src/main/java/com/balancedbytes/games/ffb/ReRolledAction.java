/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum ReRolledAction implements IEnumWithId,
IEnumWithName
{
    GO_FOR_IT(1, "Go For It"),
    DODGE(2, "Dodge"),
    CATCH(3, "Catch"),
    PICK_UP(4, "Pick Up"),
    PASS(5, Skill.PASS),
    DAUNTLESS(6, Skill.DAUNTLESS),
    LEAP(7, Skill.LEAP),
    FOUL_APPEARANCE(8, Skill.FOUL_APPEARANCE),
    BLOCK(9, "Block"),
    REALLY_STUPID(10, Skill.REALLY_STUPID),
    BONE_HEAD(11, Skill.BONE_HEAD),
    WILD_ANIMAL(12, Skill.WILD_ANIMAL),
    TAKE_ROOT(13, Skill.TAKE_ROOT),
    WINNINGS(14, "Winnings"),
    ALWAYS_HUNGRY(15, Skill.ALWAYS_HUNGRY),
    THROW_TEAM_MATE(16, Skill.THROW_TEAM_MATE),
    RIGHT_STUFF(17, Skill.RIGHT_STUFF),
    SHADOWING_ESCAPE(18, "Shadowing Escape"),
    TENTACLES_ESCAPE(19, "Tentacles Escape"),
    ESCAPE(20, "Escape"),
    SAFE_THROW(21, Skill.SAFE_THROW),
    INTERCEPTION(22, "Interception"),
    JUMP_UP(23, Skill.JUMP_UP),
    STAND_UP(24, "standUp"),
    CHAINSAW(25, Skill.CHAINSAW),
    BLOOD_LUST(26, Skill.BLOOD_LUST),
    HYPNOTIC_GAZE(27, Skill.HYPNOTIC_GAZE),
    ANIMOSITY(28, Skill.ANIMOSITY);
    
    private int fId;
    private String fName;
    private Skill fSkill;

    private ReRolledAction(int pId, String pName) {
        this.fId = pId;
        this.fName = pName;
        this.fSkill = null;
    }

    private ReRolledAction(int pId, Skill pSkill) {
        this.fId = pId;
        this.fSkill = pSkill;
        this.fName = pSkill.getName();
    }

    @Override
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

