/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportInducementsBought
implements IReport {
    private String fTeamId;
    private int fNrOfInducements;
    private int fNrOfStars;
    private int fNrOfMercenaries;
    private int fGold;

    public ReportInducementsBought() {
    }

    public ReportInducementsBought(String pTeamId, int pInducements, int pStars, int pMercenaries, int pGold) {
        this.fTeamId = pTeamId;
        this.fNrOfInducements = pInducements;
        this.fNrOfStars = pStars;
        this.fNrOfMercenaries = pMercenaries;
        this.fGold = pGold;
    }

    @Override
    public ReportId getId() {
        return ReportId.INDUCEMENTS_BOUGHT;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getNrOfInducements() {
        return this.fNrOfInducements;
    }

    public int getNrOfStars() {
        return this.fNrOfStars;
    }

    public int getNrOfMercenaries() {
        return this.fNrOfMercenaries;
    }

    public int getGold() {
        return this.fGold;
    }

    @Override
    public IReport transform() {
        return new ReportInducementsBought(this.getTeamId(), this.getNrOfInducements(), this.getNrOfStars(), this.getNrOfMercenaries(), this.getGold());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.NR_OF_INDUCEMENTS.addTo(jsonObject, this.fNrOfInducements);
        IJsonOption.NR_OF_STARS.addTo(jsonObject, this.fNrOfStars);
        IJsonOption.NR_OF_MERCENARIES.addTo(jsonObject, this.fNrOfMercenaries);
        IJsonOption.GOLD.addTo(jsonObject, this.fGold);
        return jsonObject;
    }

    @Override
    public ReportInducementsBought initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fNrOfInducements = IJsonOption.NR_OF_INDUCEMENTS.getFrom(jsonObject);
        this.fNrOfStars = IJsonOption.NR_OF_STARS.getFrom(jsonObject);
        this.fNrOfMercenaries = IJsonOption.NR_OF_MERCENARIES.getFrom(jsonObject);
        this.fGold = IJsonOption.GOLD.getFrom(jsonObject);
        return this;
    }
}

