package com.balancedbytes.games.ffb;

public enum SpecialEffect implements IEnumWithId, IEnumWithName
{
    LIGHTNING(1, "lightning"),
    FIREBALL(2, "fireball"),
    BOMB(3, "bomb");
    
    private int fId;
    private String fName;

    SpecialEffect(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }
}

