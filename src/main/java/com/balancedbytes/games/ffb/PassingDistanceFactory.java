/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class PassingDistanceFactory
implements INamedObjectFactory {
    @Override
    public PassingDistance forName(String pName) {
        for (PassingDistance distance : PassingDistance.values()) {
            if (!distance.getName().equalsIgnoreCase(pName)) continue;
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

