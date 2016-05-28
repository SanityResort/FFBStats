/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum InjuryAttribute {
    MA(1, "MA"),
    ST(2, "ST"),
    AG(3, "AG"),
    AV(4, "AV"),
    NI(5, "NI");
    
    private int fId;
    private String fName;

    private InjuryAttribute(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    public int getId() {
        return this.fId;
    }

    public String getName() {
        return this.fName;
    }

    public static InjuryAttribute fromId(int pId) {
        for (InjuryAttribute attribute : InjuryAttribute.values()) {
            if (attribute.getId() != pId) continue;
            return attribute;
        }
        return null;
    }

    public static InjuryAttribute fromName(String pName) {
        for (InjuryAttribute attribute : InjuryAttribute.values()) {
            if (!attribute.getName().equals(pName)) continue;
            return attribute;
        }
        return null;
    }
}

