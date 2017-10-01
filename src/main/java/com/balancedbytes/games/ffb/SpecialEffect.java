/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum SpecialEffect implements INamedObject
{
    LIGHTNING("lightning", true, "Lightning Bolt"),
    FIREBALL("fireball", true, "Fireball"),
    BOMB("bomb", false, "Bomb");

    private String fName;
    private boolean fWizardSpell;
    private String turnOverDesc;


    private SpecialEffect(String pName, boolean pWizardSpell, String turnOverDesc) {
        this.fName = pName;
        this.fWizardSpell = pWizardSpell;
        this.turnOverDesc = turnOverDesc;
    }

    public String getTurnOverDesc() {
        return turnOverDesc;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isWizardSpell() {
        return this.fWizardSpell;
    }
}
