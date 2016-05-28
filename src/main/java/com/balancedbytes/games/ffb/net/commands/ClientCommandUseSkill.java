/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandUseSkill
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.SKILL.addTo(jsonObject, this.fSkill);
        IJsonOption.SKILL_USED.addTo(jsonObject, this.fSkillUsed);
        return jsonObject;
    }

    @Override
    public ClientCommandUseSkill initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fSkill = (Skill)IJsonOption.SKILL.getFrom(jsonObject);
        this.fSkillUsed = IJsonOption.SKILL_USED.getFrom(jsonObject);
        return this;
    }
}

