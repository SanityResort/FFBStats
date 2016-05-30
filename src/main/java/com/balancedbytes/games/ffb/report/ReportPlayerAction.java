/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPlayerAction
implements IReport {
    private String fActingPlayerId;
    private PlayerAction fPlayerAction;

    public ReportPlayerAction() {
    }

    public ReportPlayerAction(String pActingPlayerId, PlayerAction pPlayerAction) {
        this();
        this.fActingPlayerId = pActingPlayerId;
        this.fPlayerAction = pPlayerAction;
    }

    @Override
    public ReportId getId() {
        return ReportId.PLAYER_ACTION;
    }

    public String getActingPlayerId() {
        return this.fActingPlayerId;
    }

    public PlayerAction getPlayerAction() {
        return this.fPlayerAction;
    }

    @Override
    public IReport transform() {
        return new ReportPlayerAction(this.getActingPlayerId(), this.getPlayerAction());
    }

    @Override
    public ReportPlayerAction initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fActingPlayerId = IJsonOption.ACTING_PLAYER_ID.getFrom(jsonObject);
        this.fPlayerAction = (PlayerAction)IJsonOption.PLAYER_ACTION.getFrom(jsonObject);
        return this;
    }
}

