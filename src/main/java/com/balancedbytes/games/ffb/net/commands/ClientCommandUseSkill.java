/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandUseSkill
extends ClientCommand {
    private Skill fSkill;
    private boolean fSkillUsed;

    public ClientCommandUseSkill() {
    }

    public ClientCommandUseSkill(Skill pSkill, boolean pSkillUsed) {
        this.fSkill = pSkill;
        this.fSkillUsed = pSkillUsed;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_USE_SKILL;
    }

    public boolean isSkillUsed() {
        return this.fSkillUsed;
    }

    public Skill getSkill() {
        return this.fSkill;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.SKILL.addTo(jsonObject, this.fSkill);
        IJsonOption.SKILL_USED.addTo(jsonObject, this.fSkillUsed);
        return jsonObject;
    }

    @Override
    public ClientCommandUseSkill initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fSkill = (Skill)IJsonOption.SKILL.getFrom(jsonObject);
        this.fSkillUsed = IJsonOption.SKILL_USED.getFrom(jsonObject);
        return this;
    }
}
