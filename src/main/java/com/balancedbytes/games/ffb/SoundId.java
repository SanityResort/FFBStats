/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum SoundId implements INamedObject
{
    BLOCK("block", false),
    BLUNDER("blunder", false),
    BOUNCE("bounce", false),
    CATCH("catch", false),
    CHAINSAW("chainsaw", false),
    CLICK("click", false),
    DING("ding", false),
    DODGE("dodge", false),
    DUH("duh", false),
    EW("ew", false),
    EXPLODE("explode", false),
    FALL("fall", false),
    FIREBALL("fireball", false),
    FOUL("foul", false),
    HYPNO("hypno", false),
    INJURY("injury", false),
    KICK("kick", false),
    KO("ko", false),
    LIGHTNING("lightning", false),
    METAL("metal", false),
    NOMNOM("nomnom", false),
    ORGAN("organ", false),
    PICKUP("pickup", false),
    QUESTION("question", false),
    RIP("rip", false),
    ROAR("roar", false),
    ROOT("root", false),
    SLURP("slurp", false),
    STAB("stab", false),
    STEP("step", false),
    THROW("throw", false),
    TOUCHDOWN("touchdown", false),
    WHISTLE("whistle", false),
    WOOOAAAH("woooaaah", false),
    SPEC_AAH("specAah", true),
    SPEC_BOO("specBoo", true),
    SPEC_CHEER("specCheer", true),
    SPEC_CLAP("specClap", true),
    SPEC_CRICKETS("specCrickets", true),
    SPEC_LAUGH("specLaugh", true),
    SPEC_OOH("specOoh", true),
    SPEC_SHOCK("specShock", true),
    SPEC_STOMP("specStomp", true);
    
    private String fName;
    private boolean fSpectatorSound;

    private SoundId(String pName, boolean pSpectatorSound) {
        this.fName = pName;
        this.fSpectatorSound = pSpectatorSound;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isSpectatorSound() {
        return this.fSpectatorSound;
    }
}

