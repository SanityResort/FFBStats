/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

public class ReRolledActionFactory
implements INamedObjectFactory {
    @Override
    public ReRolledAction forName(String pName) {
        for (ReRolledAction action : ReRolledAction.values()) {
            if (!action.getName().equalsIgnoreCase(pName)) continue;
            return action;
        }
        return null;
    }

    public ReRolledAction forSkill(Skill pSkill) {
        for (ReRolledAction action : ReRolledAction.values()) {
            if (pSkill != action.getSkill()) continue;
            return action;
        }
        return null;
    }
}

