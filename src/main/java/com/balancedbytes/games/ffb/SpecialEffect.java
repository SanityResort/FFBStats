/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum SpecialEffect implements IEnumWithId,
IEnumWithName
{
    LIGHTNING(1, "lightning", true),
    FIREBALL(2, "fireball", true),
    BOMB(3, "bomb", false);
    
    private int fId;
    private String fName;
    private boolean fWizardSpell;

    private SpecialEffect(int pValue, String pName, boolean pWizardSpell) {
        this.fId = pValue;
        this.fName = pName;
        this.fWizardSpell = pWizardSpell;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public boolean isWizardSpell() {
        return this.fWizardSpell;
    }
}

