/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Team;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class TeamListEntry implements IJsonSerializable {
    public static final String XML_TAG = "team";
    private static final String _XML_TAG_ID = "id";
    private static final String _XML_TAG_STATUS = "status";
    private static final String _XML_TAG_DIVISION = "division";
    private static final String _XML_TAG_NAME = "name";
    private static final String _XML_TAG_TEAM_VALUE = "teamValue";
    private static final String _XML_TAG_RACE = "race";
    private static final String _XML_TAG_TREASURY = "treasury";
    private String fTeamId;
    private TeamStatus fTeamStatus;
    private String fDivision;
    private String fTeamName;
    private int fTeamValue;
    private String fRace;
    private int fTreasury;

    public void init(Team pTeam) {
        if (pTeam != null) {
            this.setTeamId(pTeam.getId());
            this.setTeamName(pTeam.getName());
            this.setDivision(pTeam.getDivision());
            this.setRace(pTeam.getRace());
            this.setTeamValue(pTeam.getTeamValue());
            this.setTreasury(pTeam.getTreasury());
        }
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public void setTeamId(String pTeamId) {
        this.fTeamId = pTeamId;
    }

    public TeamStatus getTeamStatus() {
        return this.fTeamStatus;
    }

    public void setTeamStatus(TeamStatus pTeamStatus) {
        this.fTeamStatus = pTeamStatus;
    }

    public String getDivision() {
        return this.fDivision;
    }

    public void setDivision(String pDivision) {
        this.fDivision = pDivision;
    }

    public String getTeamName() {
        return this.fTeamName;
    }

    public void setTeamName(String pTeamName) {
        this.fTeamName = pTeamName;
    }

    public int getTeamValue() {
        return this.fTeamValue;
    }

    public void setTeamValue(int pTeamRating) {
        this.fTeamValue = pTeamRating;
    }

    public String getRace() {
        return this.fRace;
    }

    public void setRace(String pRace) {
        this.fRace = pRace;
    }

    public int getTreasury() {
        return this.fTreasury;
    }

    public void setTreasury(int pTreasury) {
        this.fTreasury = pTreasury;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.TEAM_STATUS.addTo(jsonObject, this.fTeamStatus);
        IJsonOption.DIVISION.addTo(jsonObject, this.fDivision);
        IJsonOption.TEAM_NAME.addTo(jsonObject, this.fTeamName);
        IJsonOption.TEAM_VALUE.addTo(jsonObject, this.fTeamValue);
        IJsonOption.RACE.addTo(jsonObject, this.fRace);
        IJsonOption.TREASURY.addTo(jsonObject, this.fTreasury);
        return jsonObject;
    }

    @Override
    public TeamListEntry initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fTeamStatus = (TeamStatus)IJsonOption.TEAM_STATUS.getFrom(jsonObject);
        this.fDivision = IJsonOption.DIVISION.getFrom(jsonObject);
        this.fTeamName = IJsonOption.TEAM_NAME.getFrom(jsonObject);
        this.fTeamValue = IJsonOption.TEAM_VALUE.getFrom(jsonObject);
        this.fRace = IJsonOption.RACE.getFrom(jsonObject);
        this.fTreasury = IJsonOption.TREASURY.getFrom(jsonObject);
        return this;
    }
}

