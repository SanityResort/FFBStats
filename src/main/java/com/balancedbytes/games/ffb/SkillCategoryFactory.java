/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.util.StringTool;

import java.util.ArrayList;

public class SkillCategoryFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public SkillCategory forId(int pId) {
        if (pId > 0) {
            for (SkillCategory skillCategory : SkillCategory.values()) {
                if (pId != skillCategory.getId()) continue;
                return skillCategory;
            }
        }
        return null;
    }

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

