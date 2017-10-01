/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.INamedObject;

public enum PlayerGender implements INamedObject
{
    MALE("male", "M", "he", "his", "him", "himself"),
    FEMALE("female", "F", "she", "her", "her", "herself"),
    NEUTRAL("neutral", "N", "it", "its", "it", "itself");
    
    private String fName;
    private String fTypeString;
    private String fNominative;
    private String fGenitive;
    private String fDative;
    private String fSelf;

    private PlayerGender(String pName, String pTypeString, String pNominative, String pGenitive, String pDative, String pSelf) {
        this.fName = pName;
        this.fTypeString = pTypeString;
        this.fNominative = pNominative;
        this.fGenitive = pGenitive;
        this.fDative = pDative;
        this.fSelf = pSelf;
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

