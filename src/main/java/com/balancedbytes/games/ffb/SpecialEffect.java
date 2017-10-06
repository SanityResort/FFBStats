/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public enum SpecialEffect implements INamedObject
{
    LIGHTNING("lightning", true),
    FIREBALL("fireball", true),
    BOMB("bomb", false);

    private String fName;
    private boolean fWizardSpell;


    private SpecialEffect(String pName, boolean pWizardSpell) {
        this.fName = pName;
        this.fWizardSpell = pWizardSpell;
    }


    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isWizardSpell() {
        return this.fWizardSpell;
    }
}
