/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum InjuryType implements IEnumWithId,
IEnumWithName
{
    DROP_DODGE(1, "dropDodge", false),
    DROP_GFI(2, "dropGfi", false),
    DROP_LEAP(3, "dropLeap", false),
    BLOCK(4, "block", true),
    FOUL(5, "foul", false),
    CROWDPUSH(6, "crowdpush", false),
    THROW_A_ROCK(7, "throwARock", false),
    EAT_PLAYER(8, "eatPlayer", false),
    STAB(9, "stab", false),
    TTM_LANDING(10, "ttmLanding", false),
    TTM_HIT_PLAYER(11, "ttmHitPlayer", false),
    PILING_ON_INJURY(12, "pilingOnInjury", true),
    CHAINSAW(13, "chainsaw", false),
    BITTEN(14, "bitten", false),
    FIREBALL(15, "fireball", false),
    LIGHTNING(16, "lightning", false),
    PILING_ON_ARMOR(17, "pilingOnArmor", true),
    PILING_ON_KNOCKED_OUT(18, "pilingOnKnockedOut", false),
    BALL_AND_CHAIN(19, "ballAndChain", false),
    BLOCK_PRONE(20, "blockProne", false),
    BOMB(21, "bomb", false);
    
    private int fId;
    private String fName;
    private boolean fWorthSpps;

    private InjuryType(int pValue, String pName, boolean pWorthSpps) {
        this.fId = pValue;
        this.fName = pName;
        this.fWorthSpps = pWorthSpps;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isWorthSpps() {
        return this.fWorthSpps;
    }
}

