/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.SkillCategory;
import java.util.Comparator;

public enum Skill implements INamedObject
{
    ACCURATE("Accurate", SkillCategory.PASSING),
    ALWAYS_HUNGRY("Always Hungry", SkillCategory.EXTRAORDINARY),
    ANIMOSITY("Animosity", SkillCategory.EXTRAORDINARY),
    BALL_AND_CHAIN("Ball and Chain", SkillCategory.EXTRAORDINARY),
    BIG_HAND("Big Hand", SkillCategory.MUTATION),
    BLOCK("Block", SkillCategory.GENERAL),
    BLOOD_LUST("Blood Lust", SkillCategory.EXTRAORDINARY),
    BOMBARDIER("Bombardier", SkillCategory.EXTRAORDINARY),
    BONE_HEAD("Bone-Head", SkillCategory.EXTRAORDINARY),
    BREAK_TACKLE("Break Tackle", SkillCategory.STRENGTH),
    CATCH("Catch", SkillCategory.AGILITY),
    CHAINSAW("Chainsaw", SkillCategory.EXTRAORDINARY),
    CLAW("Claw", SkillCategory.MUTATION),
    DAUNTLESS("Dauntless", SkillCategory.GENERAL),
    DECAY("Decay", SkillCategory.EXTRAORDINARY),
    DIRTY_PLAYER("Dirty Player", SkillCategory.GENERAL),
    DISTURBING_PRESENCE("Disturbing Presence", SkillCategory.MUTATION),
    DIVING_CATCH("Diving Catch", SkillCategory.AGILITY),
    DIVING_TACKLE("Diving Tackle", SkillCategory.AGILITY),
    DODGE("Dodge", SkillCategory.AGILITY),
    DUMP_OFF("Dump-Off", SkillCategory.PASSING),
    EXTRA_ARMS("Extra Arms", SkillCategory.MUTATION),
    FAN_FAVOURITE("Fan Favourite", SkillCategory.EXTRAORDINARY),
    FEND("Fend", SkillCategory.GENERAL),
    FOUL_APPEARANCE("Foul Appearance", SkillCategory.MUTATION),
    FRENZY("Frenzy", SkillCategory.GENERAL),
    GRAB("Grab", SkillCategory.STRENGTH),
    GUARD("Guard", SkillCategory.STRENGTH),
    HAIL_MARY_PASS("Hail Mary Pass", SkillCategory.PASSING),
    HORNS("Horns", SkillCategory.MUTATION),
    HYPNOTIC_GAZE("Hypnotic Gaze", SkillCategory.EXTRAORDINARY),
    JUGGERNAUT("Juggernaut", SkillCategory.STRENGTH),
    JUMP_UP("Jump Up", SkillCategory.AGILITY),
    KICK("Kick", SkillCategory.GENERAL),
    KICK_OFF_RETURN("Kick-Off Return", SkillCategory.GENERAL),
    LEADER("Leader", SkillCategory.PASSING),
    LEAP("Leap", SkillCategory.AGILITY),
    LONER("Loner", SkillCategory.EXTRAORDINARY),
    MIGHTY_BLOW("Mighty Blow", SkillCategory.STRENGTH),
    MOUNSTROUS_MOUTH("Monstrous Mouth", SkillCategory.EXTRAORDINARY),
    MULTIPLE_BLOCK("Multiple Block", SkillCategory.STRENGTH),
    NERVES_OF_STEEL("Nerves of Steel", SkillCategory.PASSING),
    NO_HANDS("No Hands", SkillCategory.EXTRAORDINARY),
    NURGLES_ROT("Nurgle's Rot", SkillCategory.EXTRAORDINARY),
    PASS("Pass", SkillCategory.PASSING),
    PASS_BLOCK("Pass Block", SkillCategory.GENERAL),
    PILING_ON("Piling On", SkillCategory.STRENGTH),
    PREHENSILE_TAIL("Prehensile Tail", SkillCategory.MUTATION),
    PRO("Pro", SkillCategory.GENERAL),
    REALLY_STUPID("Really Stupid", SkillCategory.EXTRAORDINARY),
    REGENERATION("Regeneration", SkillCategory.EXTRAORDINARY),
    RIGHT_STUFF("Right Stuff", SkillCategory.EXTRAORDINARY),
    SAFE_THROW("Safe Throw", SkillCategory.PASSING),
    SECRET_WEAPON("Secret Weapon", SkillCategory.EXTRAORDINARY),
    SHADOWING("Shadowing", SkillCategory.GENERAL),
    SIDE_STEP("Side Step", SkillCategory.AGILITY),
    SNEAKY_GIT("Sneaky Git", SkillCategory.AGILITY),
    SPRINT("Sprint", SkillCategory.AGILITY),
    STAB("Stab", SkillCategory.EXTRAORDINARY),
    STAKES("Stakes", SkillCategory.EXTRAORDINARY),
    STAND_FIRM("Stand Firm", SkillCategory.STRENGTH),
    STRIP_BALL("Strip Ball", SkillCategory.GENERAL),
    STRONG_ARM("Strong Arm", SkillCategory.STRENGTH),
    STUNTY("Stunty", SkillCategory.EXTRAORDINARY),
    SURE_FEET("Sure Feet", SkillCategory.AGILITY),
    SURE_HANDS("Sure Hands", SkillCategory.GENERAL),
    TACKLE("Tackle", SkillCategory.GENERAL),
    TAKE_ROOT("Take Root", SkillCategory.EXTRAORDINARY),
    TENTACLES("Tentacles", SkillCategory.MUTATION),
    THICK_SKULL("Thick Skull", SkillCategory.STRENGTH),
    THROW_TEAM_MATE("Throw Team-Mate", SkillCategory.EXTRAORDINARY),
    TIMMMBER("Timmm-ber!", SkillCategory.EXTRAORDINARY),
    TITCHY("Titchy", SkillCategory.EXTRAORDINARY),
    TWO_HEADS("Two Heads", SkillCategory.MUTATION),
    VERY_LONG_LEGS("Very Long Legs", SkillCategory.MUTATION),
    WEEPING_DAGGER("Weeping Dagger", SkillCategory.EXTRAORDINARY),
    WILD_ANIMAL("Wild Animal", SkillCategory.EXTRAORDINARY),
    WRESTLE("Wrestle", SkillCategory.GENERAL),
    MOVEMENT_INCREASE("+MA", SkillCategory.STAT_INCREASE),
    MOVEMENT_DECREASE("-MA", SkillCategory.STAT_DECREASE),
    STRENGTH_INCREASE("+ST", SkillCategory.STAT_INCREASE),
    STRENGTH_DECREASE("-ST", SkillCategory.STAT_DECREASE),
    AGILITY_INCREASE("+AG", SkillCategory.STAT_INCREASE),
    AGILITY_DECREASE("-AG", SkillCategory.STAT_DECREASE),
    ARMOUR_INCREASE("+AV", SkillCategory.STAT_INCREASE),
    ARMOUR_DECREASE("-AV", SkillCategory.STAT_DECREASE);
    
    private String fName;
    private SkillCategory fCategory;

    private Skill(String pName, SkillCategory pCategory) {
        this.fName = pName;
        this.fCategory = pCategory;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public SkillCategory getCategory() {
        return this.fCategory;
    }

    public static Comparator<Skill> getComparator() {
        return new Comparator<Skill>(){

            @Override
            public int compare(Skill pSkill1, Skill pSkill2) {
                return pSkill1.getName().compareTo(pSkill2.getName());
            }
        };
    }

}

