/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum DefenderAction {
    DUMP_OFF(1, "dumpOff", "Dump Off", "dump off the ball");
    
    private int fId;
    private String fName;
    private String fTitle;
    private String fDescription;

    private DefenderAction(int pId, String pName, String pTitle, String pDescription) {
        this.fId = pId;
        this.fName = pName;
        this.fTitle = pTitle;
        this.fDescription = pDescription;
    }

    public int getId() {
        return this.fId;
    }

    public String getName() {
        return this.fName;
    }

    public String getTitle() {
        return this.fTitle;
    }

    public String getDescription() {
        return this.fDescription;
    }

    public static DefenderAction fromId(int pValue) {
        for (DefenderAction action : DefenderAction.values()) {
            if (action.getId() != pValue) continue;
            return action;
        }
        return null;
    }

    public static DefenderAction fromName(String pName) {
        for (DefenderAction action : DefenderAction.values()) {
            if (!action.getName().equalsIgnoreCase(pName)) continue;
            return action;
        }
        return null;
    }
}

