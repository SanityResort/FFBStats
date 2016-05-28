/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportConfusionRoll
extends ReportSkillRoll {
    private Skill fConfusionSkill;

    public ReportConfusionRoll() {
        super(ReportId.CONFUSION_ROLL);
    }

    public ReportConfusionRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, Skill pConfusionSkill) {
        super(ReportId.CONFUSION_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled);
        this.fConfusionSkill = pConfusionSkill;
    }

    @Override
    public ReportId getId() {
        return ReportId.CONFUSION_ROLL;
    }

    public Skill getConfusionSkill() {
        return this.fConfusionSkill;
    }

    @Override
    public IReport transform() {
        return new ReportConfusionRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getConfusionSkill());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = UtilJson.toJsonObject(super.toJsonValue());
        IJsonOption.CONFUSION_SKILL.addTo(jsonObject, this.fConfusionSkill);
        return jsonObject;
    }

    @Override
    public ReportConfusionRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fConfusionSkill = (Skill)IJsonOption.CONFUSION_SKILL.getFrom(jsonObject);
        return this;
    }
}

