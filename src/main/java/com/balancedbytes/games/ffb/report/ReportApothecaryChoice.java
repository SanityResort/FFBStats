/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportApothecaryChoice
implements IReport {
    private String fPlayerId;
    private PlayerState fPlayerState;
    private SeriousInjury fSeriousInjury;

    public ReportApothecaryChoice() {
    }

    public ReportApothecaryChoice(String pPlayerId, PlayerState pPlayerState, SeriousInjury pSeriousInjury) {
        this.fPlayerId = pPlayerId;
        this.fPlayerState = pPlayerState;
        this.fSeriousInjury = pSeriousInjury;
    }

    @Override
    public ReportId getId() {
        return ReportId.APOTHECARY_CHOICE;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public PlayerState getPlayerState() {
        return this.fPlayerState;
    }

    public SeriousInjury getSeriousInjury() {
        return this.fSeriousInjury;
    }

    @Override
    public IReport transform() {
        return new ReportApothecaryChoice(this.getPlayerId(), this.getPlayerState(), this.getSeriousInjury());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.PLAYER_STATE.addTo(jsonObject, this.fPlayerState);
        IJsonOption.SERIOUS_INJURY.addTo(jsonObject, this.fSeriousInjury);
        return jsonObject;
    }

    @Override
    public ReportApothecaryChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fPlayerState = IJsonOption.PLAYER_STATE.getFrom(jsonObject);
        this.fSeriousInjury = (SeriousInjury)IJsonOption.SERIOUS_INJURY.getFrom(jsonObject);
        return this;
    }
}

