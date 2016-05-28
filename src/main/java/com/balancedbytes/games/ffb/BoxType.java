/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum BoxType {
    RESERVES(1, "reserves", "Rsv", "player is in reserve.", "players are in reserve."),
    OUT(2, "out", "Out", "player is out of the game.", "players are out of the game.");
    
    private int fId;
    private String fName;
    private String fShortcut;
    private String fToolTipSingle;
    private String fToolTipMultiple;

    private BoxType(int pValue, String pName, String pShortcut, String pToolTipSingle, String pToolTipMultiple) {
        this.fId = pValue;
        this.fName = pName;
        this.fShortcut = pShortcut;
        this.fToolTipSingle = pToolTipSingle;
        this.fToolTipMultiple = pToolTipMultiple;
    }

    public int getId() {
        return this.fId;
    }

    public String getName() {
        return this.fName;
    }

    public String getShortcut() {
        return this.fShortcut;
    }

    public String getToolTipSingle() {
        return this.fToolTipSingle;
    }

    public String getToolTipMultiple() {
        return this.fToolTipMultiple;
    }

    public static BoxType fromId(int pId) {
        for (BoxType type : BoxType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }

    public static BoxType fromName(String pName) {
        for (BoxType type : BoxType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }
}

