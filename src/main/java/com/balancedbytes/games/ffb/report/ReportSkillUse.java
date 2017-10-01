/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillUse;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportSkillUse
implements IReport {
    private String fPlayerId;
    private Skill fSkill;
    private boolean fUsed;
    private SkillUse fSkillUse;

    public ReportSkillUse() {
    }

    public ReportSkillUse(Skill pSkill, boolean pUsed, SkillUse pSkillUse) {
        this(null, pSkill, pUsed, pSkillUse);
    }

    public ReportSkillUse(String pPlayerId, Skill pSkill, boolean pUsed, SkillUse pSkillUse) {
        this.fPlayerId = pPlayerId;
        this.fSkill = pSkill;
        this.fUsed = pUsed;
        this.fSkillUse = pSkillUse;
    }

    @Override
    public ReportId getId() {
        return ReportId.SKILL_USE;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public Skill getSkill() {
        return this.fSkill;
    }

    public boolean isUsed() {
        return this.fUsed;
    }

    public SkillUse getSkillUse() {
        return this.fSkillUse;
    }

    @Override
    public IReport transform() {
        return new ReportSkillUse(this.getPlayerId(), this.getSkill(), this.isUsed(), this.getSkillUse());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.SKILL.addTo(jsonObject, this.fSkill);
        IJsonOption.USED.addTo(jsonObject, this.fUsed);
        IJsonOption.SKILL_USE.addTo(jsonObject, this.fSkillUse);
        return jsonObject;
    }

    @Override
    public ReportSkillUse initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSkill = (Skill)IJsonOption.SKILL.getFrom(jsonObject);
        this.fUsed = IJsonOption.USED.getFrom(jsonObject);
        this.fSkillUse = (SkillUse)IJsonOption.SKILL_USE.getFrom(jsonObject);
        return this;
    }
}

