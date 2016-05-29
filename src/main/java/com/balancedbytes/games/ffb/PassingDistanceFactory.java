/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

public class PassingDistanceFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public PassingDistance forName(String pName) {
        for (PassingDistance distance : PassingDistance.values()) {
            if (!distance.getName().equalsIgnoreCase(pName)) continue;
            return distance;
        }
        return null;
    }

    @Override
    public PassingDistance forId(int pId) {
        for (PassingDistance distance : PassingDistance.values()) {
            if (distance.getId() != pId) continue;
            return distance;
        }
        return null;
    }

    public PassingDistance forShortcut(char pShortcut) {
        for (PassingDistance distance : PassingDistance.values()) {
            if (distance.getShortcut() != pShortcut) continue;
            return distance;
        }
        return null;
    }
}

