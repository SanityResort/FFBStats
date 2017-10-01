/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum InjuryType implements INamedObject
{
    DROP_DODGE("dropDodge", false),
    DROP_GFI("dropGfi", false),
    DROP_LEAP("dropLeap", false),
    BLOCK("block", true),
    FOUL("foul", false),
    CROWDPUSH("crowdpush", false),
    THROW_A_ROCK("throwARock", false),
    EAT_PLAYER("eatPlayer", false),
    STAB("stab", false),
    TTM_LANDING("ttmLanding", false),
    TTM_HIT_PLAYER("ttmHitPlayer", false),
    PILING_ON_INJURY("pilingOnInjury", true),
    CHAINSAW("chainsaw", false),
    BITTEN("bitten", false),
    FIREBALL("fireball", false),
    LIGHTNING("lightning", false),
    PILING_ON_ARMOR("pilingOnArmor", true),
    PILING_ON_KNOCKED_OUT("pilingOnKnockedOut", false),
    BALL_AND_CHAIN("ballAndChain", false),
    BLOCK_PRONE("blockProne", false),
    BOMB("bomb", false);
    
    private String fName;
    private boolean fWorthSpps;

    private InjuryType(String pName, boolean pWorthSpps) {
        this.fName = pName;
        this.fWorthSpps = pWorthSpps;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isWorthSpps() {
        return this.fWorthSpps;
    }
}

