/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import java.util.Comparator;

public enum Skill implements IEnumWithId,
IEnumWithName
{
    ACCURATE(1, "Accurate", SkillCategory.PASSING),
    ALWAYS_HUNGRY(2, "Always Hungry", SkillCategory.EXTRAORDINARY),
    BALL_AND_CHAIN(3, "Ball and Chain", SkillCategory.EXTRAORDINARY),
    BIG_HAND(4, "Big Hand", SkillCategory.MUTATION),
    BLOCK(5, "Block", SkillCategory.GENERAL),
    BLOOD_LUST(6, "Blood Lust", SkillCategory.EXTRAORDINARY),
    BOMBARDIER(7, "Bombardier", SkillCategory.EXTRAORDINARY),
    BONE_HEAD(8, "Bone-Head", SkillCategory.EXTRAORDINARY),
    BREAK_TACKLE(9, "Break Tackle", SkillCategory.STRENGTH),
    CATCH(10, "Catch", SkillCategory.AGILITY),
    CHAINSAW(11, "Chainsaw", SkillCategory.EXTRAORDINARY),
    CLAW(12, "Claw", SkillCategory.MUTATION),
    DAUNTLESS(13, "Dauntless", SkillCategory.GENERAL),
    DECAY(14, "Decay", SkillCategory.EXTRAORDINARY),
    DIRTY_PLAYER(15, "Dirty Player", SkillCategory.GENERAL),
    DISTURBING_PRESENCE(16, "Disturbing Presence", SkillCategory.MUTATION),
    DIVING_CATCH(17, "Diving Catch", SkillCategory.AGILITY),
    DIVING_TACKLE(18, "Diving Tackle", SkillCategory.AGILITY),
    DODGE(19, "Dodge", SkillCategory.AGILITY),
    DUMP_OFF(20, "Dump-Off", SkillCategory.PASSING),
    EXTRA_ARMS(21, "Extra Arms", SkillCategory.MUTATION),
    FAN_FAVOURITE(22, "Fan Favourite", SkillCategory.EXTRAORDINARY),
    FEND(23, "Fend", SkillCategory.GENERAL),
    FOUL_APPEARANCE(24, "Foul Appearance", SkillCategory.MUTATION),
    FRENZY(25, "Frenzy", SkillCategory.GENERAL),
    GRAB(26, "Grab", SkillCategory.STRENGTH),
    GUARD(27, "Guard", SkillCategory.STRENGTH),
    HAIL_MARY_PASS(28, "Hail Mary Pass", SkillCategory.PASSING),
    HORNS(29, "Horns", SkillCategory.MUTATION),
    HYPNOTIC_GAZE(30, "Hypnotic Gaze", SkillCategory.EXTRAORDINARY),
    JUGGERNAUT(31, "Juggernaut", SkillCategory.STRENGTH),
    JUMP_UP(32, "Jump Up", SkillCategory.AGILITY),
    KICK(33, "Kick", SkillCategory.GENERAL),
    KICK_OFF_RETURN(34, "Kick-Off Return", SkillCategory.GENERAL),
    LEADER(35, "Leader", SkillCategory.PASSING),
    LEAP(36, "Leap", SkillCategory.AGILITY),
    LONER(37, "Loner", SkillCategory.EXTRAORDINARY),
    MIGHTY_BLOW(38, "Mighty Blow", SkillCategory.STRENGTH),
    MULTIPLE_BLOCK(39, "Multiple Block", SkillCategory.STRENGTH),
    NERVES_OF_STEEL(40, "Nerves of Steel", SkillCategory.PASSING),
    NO_HANDS(41, "No Hands", SkillCategory.EXTRAORDINARY),
    NURGLES_ROT(42, "Nurgle's Rot", SkillCategory.EXTRAORDINARY),
    PASS(43, "Pass", SkillCategory.PASSING),
    PASS_BLOCK(44, "Pass Block", SkillCategory.GENERAL),
    PILING_ON(45, "Piling On", SkillCategory.STRENGTH),
    PREHENSILE_TAIL(46, "Prehensile Tail", SkillCategory.MUTATION),
    PRO(47, "Pro", SkillCategory.GENERAL),
    REALLY_STUPID(48, "Really Stupid", SkillCategory.EXTRAORDINARY),
    REGENERATION(49, "Regeneration", SkillCategory.EXTRAORDINARY),
    RIGHT_STUFF(50, "Right Stuff", SkillCategory.EXTRAORDINARY),
    SAFE_THROW(51, "Safe Throw", SkillCategory.PASSING),
    SECRET_WEAPON(52, "Secret Weapon", SkillCategory.EXTRAORDINARY),
    SHADOWING(53, "Shadowing", SkillCategory.GENERAL),
    SIDE_STEP(54, "Side Step", SkillCategory.AGILITY),
    SNEAKY_GIT(55, "Sneaky Git", SkillCategory.AGILITY),
    SPRINT(56, "Sprint", SkillCategory.AGILITY),
    STAB(57, "Stab", SkillCategory.EXTRAORDINARY),
    STAKES(58, "Stakes", SkillCategory.EXTRAORDINARY),
    STAND_FIRM(59, "Stand Firm", SkillCategory.STRENGTH),
    STRIP_BALL(60, "Strip Ball", SkillCategory.GENERAL),
    STRONG_ARM(61, "Strong Arm", SkillCategory.STRENGTH),
    STUNTY(62, "Stunty", SkillCategory.EXTRAORDINARY),
    SURE_FEET(63, "Sure Feet", SkillCategory.AGILITY),
    SURE_HANDS(64, "Sure Hands", SkillCategory.GENERAL),
    TACKLE(65, "Tackle", SkillCategory.GENERAL),
    TAKE_ROOT(66, "Take Root", SkillCategory.EXTRAORDINARY),
    TENTACLES(67, "Tentacles", SkillCategory.MUTATION),
    THICK_SKULL(68, "Thick Skull", SkillCategory.STRENGTH),
    THROW_TEAM_MATE(69, "Throw Team-Mate", SkillCategory.EXTRAORDINARY),
    TITCHY(70, "Titchy", SkillCategory.EXTRAORDINARY),
    TWO_HEADS(71, "Two Heads", SkillCategory.MUTATION),
    VERY_LONG_LEGS(72, "Very Long Legs", SkillCategory.MUTATION),
    WILD_ANIMAL(73, "Wild Animal", SkillCategory.EXTRAORDINARY),
    WRESTLE(74, "Wrestle", SkillCategory.GENERAL),
    MOVEMENT_INCREASE(75, "+MA", SkillCategory.STAT_INCREASE),
    MOVEMENT_DECREASE(80, "-MA", SkillCategory.STAT_DECREASE),
    STRENGTH_INCREASE(76, "+ST", SkillCategory.STAT_INCREASE),
    STRENGTH_DECREASE(81, "-ST", SkillCategory.STAT_DECREASE),
    AGILITY_INCREASE(77, "+AG", SkillCategory.STAT_INCREASE),
    AGILITY_DECREASE(82, "-AG", SkillCategory.STAT_DECREASE),
    ARMOUR_INCREASE(78, "+AV", SkillCategory.STAT_INCREASE),
    ARMOUR_DECREASE(83, "-AV", SkillCategory.STAT_DECREASE),
    ANIMOSITY(79, "Animosity", SkillCategory.EXTRAORDINARY);
    
    private int fId;
    private String fName;
    private SkillCategory fCategory;

    private Skill(int pValue, String pName, SkillCategory pCategory) {
        this.fId = pValue;
        this.fName = pName;
        this.fCategory = pCategory;
    }

    @Override
    public int getId() {
        return this.fId;
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

