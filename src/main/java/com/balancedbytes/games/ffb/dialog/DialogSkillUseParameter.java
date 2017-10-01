/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogSkillUseParameter
implements IDialogParameter {
    private String fPlayerId;
    private Skill fSkill;
    private int fMinimumRoll;

    public DialogSkillUseParameter() {
    }

    public DialogSkillUseParameter(String pPlayerId, Skill pSkill, int pMinimumRoll) {
        this.fPlayerId = pPlayerId;
        this.fSkill = pSkill;
        this.fMinimumRoll = pMinimumRoll;
    }

    @Override
    public DialogId getId() {
        return DialogId.SKILL_USE;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public Skill getSkill() {
        return this.fSkill;
    }

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogSkillUseParameter(this.getPlayerId(), this.getSkill(), this.getMinimumRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.SKILL.addTo(jsonObject, this.fSkill);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        return jsonObject;
    }

    @Override
    public DialogSkillUseParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSkill = (Skill)IJsonOption.SKILL.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        return this;
    }
}

