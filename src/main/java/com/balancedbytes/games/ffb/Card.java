/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import java.util.Comparator;

public enum Card implements INamedObject
{
    BEGUILING_BRACERS("Beguiling Bracers", "Beguiling Bracers", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_GAME, "Player gets Hypnotic Gaze, Side Step & Bone-Head"),
    BELT_OF_INVULNERABILITY("Belt of Invulnerability", "Invulnerability Belt", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN, InducementPhase.AFTER_KICKOFF_TO_OPPONENT}, InducementDuration.UNTIL_END_OF_GAME, "No modifiers or re-rolls on armour rolls"),
    FAWNDOUGHS_HEADBAND("Fawndough's Headband", "Fawndough's Headband", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Player gets Pass & Accurate, opponents get +1 to intercept"),
    FORCE_SHIELD("Force Shield", "Force Shield", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN, InducementPhase.AFTER_KICKOFF_TO_OPPONENT}, InducementDuration.WHILE_HOLDING_THE_BALL, "Player gets Sure Hands & Fend"),
    GIKTAS_STRENGTH_OF_DA_BEAR("Gikta's Strength of da Bear", "Gikta's Strength", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, true, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_DRIVE, "Player gets +1 ST for this drive, then -1 ST for the remainder of the game"),
    GLOVES_OF_HOLDING("Gloves of Holding", "Gloves of Holding", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.BEFORE_KICKOFF_SCATTER}, InducementDuration.UNTIL_END_OF_GAME, "Player gets Catch & Sure Hands, but may not Pass or Hand-off"),
    INERTIA_DAMPER("Inertia Damper", "Inertia Damper", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN, InducementPhase.AFTER_KICKOFF_TO_OPPONENT}, InducementDuration.UNTIL_END_OF_DRIVE, "Opponents get -1 ST to Blitzing from 1 or more squares away"),
    LUCKY_CHARM("Lucky Charm", "Lucky Charm", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.AFTER_INDUCEMENTS_PURCHASED}, InducementDuration.UNTIL_USED, "Ignore first armour break roll"),
    MAGIC_GLOVES_OF_JARK_LONGARM("Magic Gloves of Jark Longarm", "Magic Gloves", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN, InducementPhase.AFTER_KICKOFF_TO_OPPONENT}, InducementDuration.UNTIL_END_OF_DRIVE, "Player gets Pass Block & +1 to interception"),
    GOOD_OLD_MAGIC_CODPIECE("Good Old Magic Codpiece", "Magic Codpiece", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.AFTER_INDUCEMENTS_PURCHASED}, InducementDuration.UNTIL_END_OF_GAME, "Player cannot be fouled and no modifiers to injury rolls"),
    RABBITS_FOOT("Rabbit's Foot", "Rabbit's Foot", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_GAME, "Player gets Pro (not playable on a Loner)"),
    WAND_OF_SMASHING("Wand of Smashing", "Wand of Smashing", CardType.MAGIC_ITEM, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Player gets +1 ST & Mighty Blow"),
    BLATANT_FOUL("Blatant Foul", "Blatant Foul", CardType.DIRTY_TRICK, CardTarget.TURN, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Next foul breaks armour automatically"),
    CHOP_BLOCK("Chop Block", "Chop Block", CardType.DIRTY_TRICK, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Unmoved player drops prone and stuns an adjacent player"),
    CUSTARD_PIE("Custard Pie", "Custard Pie", CardType.DIRTY_TRICK, CardTarget.OPPOSING_PLAYER, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Opponent distracted as per Hypnotic Gaze"),
    DISTRACT("Distract", "Distract", CardType.DIRTY_TRICK, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN, InducementPhase.AFTER_KICKOFF_TO_OPPONENT}, InducementDuration.UNTIL_END_OF_OPPONENTS_TURN, "Player gets Disturbing Presence & opponents in 3 squares get Bone-head"),
    GREASED_SHOES("Greased Shoes", "Greased Shoes", CardType.DIRTY_TRICK, CardTarget.TURN, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN, InducementPhase.AFTER_KICKOFF_TO_OPPONENT}, InducementDuration.UNTIL_END_OF_OPPONENTS_TURN, "Opposing players need to roll 5+ to Go For It"),
    GROMSKULLS_EXPLODING_RUNES("Gromskull's Exploding Runes", "Exploding Runes", CardType.DIRTY_TRICK, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.BEFORE_SETUP}, InducementDuration.UNTIL_END_OF_GAME, "Player gets Bombardier, No Hands, Secret Weapon & -1 to pass"),
    ILLEGAL_SUBSTITUTION("Illegal Substitution", "Illegal Substitution", CardType.DIRTY_TRICK, CardTarget.TURN, false, new InducementPhase[]{InducementPhase.START_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Place an extra player in your end zone"),
    KICKING_BOOTS("Kicking Boots", "Kicking Boots", CardType.DIRTY_TRICK, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.BEFORE_KICKOFF_SCATTER}, InducementDuration.UNTIL_END_OF_GAME, "Player gets Kick, Dirty Player & -1 MA"),
    PIT_TRAP("Pit Trap", "Pit Trap", CardType.DIRTY_TRICK, CardTarget.ANY_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_TURN, "Player is placed prone, no armour roll"),
    SPIKED_BALL("Spiked Ball", "Spiked Ball", CardType.DIRTY_TRICK, CardTarget.TURN, false, new InducementPhase[]{InducementPhase.BEFORE_KICKOFF_SCATTER}, InducementDuration.UNTIL_END_OF_DRIVE, "Any failed pick up or catch roll results in being stabbed"),
    STOLEN_PLAYBOOK("Stolen Playbook", "Stolen Playbook", CardType.DIRTY_TRICK, CardTarget.OWN_PLAYER, false, new InducementPhase[]{InducementPhase.END_OF_OWN_TURN}, InducementDuration.UNTIL_END_OF_DRIVE, "Player gets Pass Block and Shadowing"),
    WITCH_BREW("Witch's Brew", "Witch Brew", CardType.DIRTY_TRICK, CardTarget.OPPOSING_PLAYER, false, new InducementPhase[]{InducementPhase.BEFORE_KICKOFF_SCATTER}, InducementDuration.UNTIL_END_OF_DRIVE, "Poison an opponent (random effect)");
    
    private String fName;
    private String fShortName;
    private CardType fType;
    private CardTarget fTarget;
    private boolean fRemainsInPlay;
    private InducementPhase[] fPhases;
    private InducementDuration fDuration;
    private String fDescription;

    private Card(String pName, String pShortName, CardType pType, CardTarget pTarget, boolean pRemainsInPlay, InducementPhase[] pPhases, InducementDuration pDuration, String pDescription) {
        this.fName = pName;
        this.fShortName = pShortName;
        this.fType = pType;
        this.fTarget = pTarget;
        this.fRemainsInPlay = pRemainsInPlay;
        this.fPhases = pPhases;
        this.fDuration = pDuration;
        this.fDescription = pDescription;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getShortName() {
        return this.fShortName;
    }

    public CardType getType() {
        return this.fType;
    }

    public CardTarget getTarget() {
        return this.fTarget;
    }

    public boolean isRemainsInPlay() {
        return this.fRemainsInPlay;
    }

    public InducementPhase[] getPhases() {
        return this.fPhases;
    }

    public InducementDuration getDuration() {
        return this.fDuration;
    }

    public String getDescription() {
        return this.fDescription;
    }

    public String getHtmlDescription() {
        StringBuilder description = new StringBuilder();
        description.append(this.getDescription());
        description.append("<br>");
        description.append(this.getDuration().getDescription());
        return description.toString();
    }

    public String getHtmlDescriptionWithPhases() {
        StringBuilder description = new StringBuilder();
        description.append(this.getHtmlDescription());
        description.append("<br>");
        description.append(new InducementPhaseFactory().getDescription(this.getPhases()));
        return description.toString();
    }

    public static Comparator<Card> createComparator() {
        return new Comparator<Card>(){

            @Override
            public int compare(Card pCard1, Card pCard2) {
                return pCard1.getName().compareTo(pCard2.getName());
            }
        };
    }

}

