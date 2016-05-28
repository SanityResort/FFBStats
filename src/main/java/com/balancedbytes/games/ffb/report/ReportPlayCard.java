/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportPlayCard
implements IReport {
    private String fTeamId;
    private Card fCard;
    private String fPlayerId;

    public ReportPlayCard() {
    }

    public ReportPlayCard(String pTeamId, Card pCard) {
        this.fTeamId = pTeamId;
        this.fCard = pCard;
    }

    public ReportPlayCard(String pTeamId, Card pCard, String pCatcherId) {
        this(pTeamId, pCard);
        this.fPlayerId = pCatcherId;
    }

    @Override
    public ReportId getId() {
        return ReportId.PLAY_CARD;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public Card getCard() {
        return this.fCard;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    @Override
    public IReport transform() {
        return new ReportPlayCard(this.getTeamId(), this.getCard(), this.getPlayerId());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.CARD.addTo(jsonObject, this.fCard);
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        return jsonObject;
    }

    @Override
    public ReportPlayCard initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fCard = (Card)IJsonOption.CARD.getFrom(jsonObject);
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        return this;
    }
}

