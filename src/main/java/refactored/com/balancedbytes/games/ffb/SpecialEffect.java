/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb;

public enum SpecialEffect implements IEnumWithId,
IEnumWithName
{
    LIGHTNING(1, "lightning", true, "Lightning Bolt"),
    FIREBALL(2, "fireball", true, "Fire Ball"),
    BOMB(3, "bomb", false, "Bomb");
    
    private int fId;
    private String fName;
    private boolean fWizardSpell;
    private String turnOverDesc;

    private SpecialEffect(int pValue, String pName, boolean pWizardSpell, String turnOverDesc) {
        this.fId = pValue;
        this.fName = pName;
        this.fWizardSpell = pWizardSpell;
        this.turnOverDesc = turnOverDesc;
    }

    public String getTurnOverDesc() {
        return turnOverDesc;
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

