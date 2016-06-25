package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportInterceptionRoll
extends ReportSkillRoll {
    private boolean fBomb;

    public ReportInterceptionRoll() {
        super(ReportId.INTERCEPTION_ROLL);
    }

    public ReportInterceptionRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pBomb) {
        super(ReportId.INTERCEPTION_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll);
        this.fBomb = pBomb;
    }

    @Override
    public ReportId getId() {
        return ReportId.INTERCEPTION_ROLL;
    }

    public boolean isBomb() {
        return this.fBomb;
    }

    @Override
    public IReport transform() {
        return new ReportInterceptionRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isBomb());
    }

    @Override
    public ReportInterceptionRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fBomb = IJsonOption.BOMB.getFrom(jsonObject);
        return this;
    }
}

