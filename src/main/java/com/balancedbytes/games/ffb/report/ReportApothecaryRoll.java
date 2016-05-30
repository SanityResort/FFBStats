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

public class ReportApothecaryRoll
implements IReport {
    private String fPlayerId;
    private int[] fCasualtyRoll;
    private PlayerState fPlayerState;
    private SeriousInjury fSeriousInjury;

    public ReportApothecaryRoll() {
    }

    public ReportApothecaryRoll(String pPlayerId, int[] pCasualtyRoll, PlayerState pPlayerState, SeriousInjury pSeriousInjury) {
        this.fPlayerId = pPlayerId;
        this.fCasualtyRoll = pCasualtyRoll;
        this.fPlayerState = pPlayerState;
        this.fSeriousInjury = pSeriousInjury;
    }

    @Override
    public ReportId getId() {
        return ReportId.APOTHECARY_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public int[] getCasualtyRoll() {
        return this.fCasualtyRoll;
    }

    public PlayerState getPlayerState() {
        return this.fPlayerState;
    }

    public SeriousInjury getSeriousInjury() {
        return this.fSeriousInjury;
    }

    @Override
    public IReport transform() {
        return new ReportApothecaryRoll(this.getPlayerId(), this.getCasualtyRoll(), this.getPlayerState(), this.getSeriousInjury());
    }

    @Override
    public ReportApothecaryRoll initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fCasualtyRoll = IJsonOption.CASUALTY_ROLL.getFrom(jsonObject);
        this.fPlayerState = IJsonOption.PLAYER_STATE.getFrom(jsonObject);
        this.fSeriousInjury = (SeriousInjury)IJsonOption.SERIOUS_INJURY.getFrom(jsonObject);
        return this;
    }
}

