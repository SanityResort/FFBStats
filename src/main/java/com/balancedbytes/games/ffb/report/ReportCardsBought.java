/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportCardsBought
implements IReport {
    private String fTeamId;
    private int fNrOfCards;
    private int fGold;

    public ReportCardsBought() {
    }

    public ReportCardsBought(String pTeamId, int pNrOfCards, int pGold) {
        this.fTeamId = pTeamId;
        this.fNrOfCards = pNrOfCards;
        this.fGold = pGold;
    }

    @Override
    public ReportId getId() {
        return ReportId.CARDS_BOUGHT;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getNrOfCards() {
        return this.fNrOfCards;
    }

    public int getGold() {
        return this.fGold;
    }

    @Override
    public IReport transform() {
        return new ReportCardsBought(this.getTeamId(), this.getNrOfCards(), this.getGold());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.NR_OF_CARDS.addTo(jsonObject, this.fNrOfCards);
        IJsonOption.GOLD.addTo(jsonObject, this.fGold);
        return jsonObject;
    }

    @Override
    public ReportCardsBought initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fNrOfCards = IJsonOption.NR_OF_CARDS.getFrom(jsonObject);
        this.fGold = IJsonOption.GOLD.getFrom(jsonObject);
        return this;
    }
}

