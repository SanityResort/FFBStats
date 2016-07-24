package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.Skill;
import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportConfusionRoll extends ReportSkillRoll {
    private Skill fConfusionSkill;

    public ReportConfusionRoll() {
        super(ReportId.CONFUSION_ROLL);
    }

    public ReportConfusionRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, Skill pConfusionSkill) {
        super(ReportId.CONFUSION_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll);
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
        return new ReportConfusionRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.getConfusionSkill());
    }

    @Override
    public ReportConfusionRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fConfusionSkill = (Skill)IJsonOption.CONFUSION_SKILL.getFrom(jsonObject);
        return this;
    }
}

