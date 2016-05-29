/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.util.StringTool;

public class PlayerGenderFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public PlayerGender forName(String pName) {
        if (StringTool.isProvided(pName)) {
            for (PlayerGender gender : PlayerGender.values()) {
                if (!pName.equalsIgnoreCase(gender.getName())) continue;
                return gender;
            }
        }
        return null;
    }

    @Override
    public PlayerGender forId(int pId) {
        if (pId > 0) {
            for (PlayerGender gender : PlayerGender.values()) {
                if (gender.getId() != pId) continue;
                return gender;
            }
        }
        return null;
    }

    public PlayerGender forTypeString(String pTypeString) {
        if (StringTool.isProvided(pTypeString)) {
            for (PlayerGender gender : PlayerGender.values()) {
                if (!pTypeString.equalsIgnoreCase(gender.getTypeString())) continue;
                return gender;
            }
        }
        return null;
    }
}

