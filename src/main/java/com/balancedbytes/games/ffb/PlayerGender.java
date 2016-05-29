/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public enum PlayerGender implements IEnumWithId,
IEnumWithName
{
    MALE(1, "male", "M", "he", "his", "him", "himself"),
    FEMALE(2, "female", "F", "she", "her", "her", "herself"),
    NEUTRAL(3, "neutral", "N", "it", "its", "it", "itself");
    
    private int fId;
    private String fName;
    private String fTypeString;
    private String fNominative;
    private String fGenitive;
    private String fDative;
    private String fSelf;

    private PlayerGender(int pId, String pName, String pTypeString, String pNominative, String pGenitive, String pDative, String pSelf) {
        this.fId = pId;
        this.fName = pName;
        this.fTypeString = pTypeString;
        this.fNominative = pNominative;
        this.fGenitive = pGenitive;
        this.fDative = pDative;
        this.fSelf = pSelf;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public String getTypeString() {
        return this.fTypeString;
    }

    public String getNominative() {
        return this.fNominative;
    }

    public String getGenitive() {
        return this.fGenitive;
    }

    public String getDative() {
        return this.fDative;
    }

    public String getSelf() {
        return this.fSelf;
    }
}

