/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.util.StringTool;

import java.util.ArrayList;

public class SkillCategoryFactory
implements INamedObjectFactory {
    @Override
    public SkillCategory forName(String pName) {
        if (StringTool.isProvided(pName)) {
            for (SkillCategory skillCategory : SkillCategory.values()) {
                if (!pName.equalsIgnoreCase(skillCategory.getName())) continue;
                return skillCategory;
            }
        }
        return null;
    }

    public SkillCategory forTypeString(String pTypeString) {
        if (StringTool.isProvided(pTypeString)) {
            for (SkillCategory skillCategory : SkillCategory.values()) {
                if (!pTypeString.equalsIgnoreCase(skillCategory.getTypeString())) continue;
                return skillCategory;
            }
        }
        return null;
    }

    public SkillCategory[] forTypeStrings(String pTypeStrings) {
        ArrayList<SkillCategory> skillCategories = new ArrayList<SkillCategory>();
        if (StringTool.isProvided(pTypeStrings)) {
            for (int i = 0; i < pTypeStrings.length(); ++i) {
                SkillCategory skillCategory = this.forTypeString(pTypeStrings.substring(i, i + 1));
                if (skillCategory == null) continue;
                skillCategories.add(skillCategory);
            }
        }
        return skillCategories.toArray(new SkillCategory[skillCategories.size()]);
    }
}

