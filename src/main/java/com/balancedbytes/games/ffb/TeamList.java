/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class TeamList implements IJsonSerializable {
    public static final String XML_TAG = "teams";
    private static final String _XML_ATTRIBUTE_COACH = "coach";
    private String fCoach;
    private List<TeamListEntry> fTeamListEntries = new ArrayList<TeamListEntry>();

    public TeamList() {
    }

    public TeamList(String pCoach, TeamListEntry[] pTeamListEntries) {
        this();
        this.fCoach = pCoach;
        this.add(pTeamListEntries);
    }

    public String getCoach() {
        return this.fCoach;
    }

    public TeamListEntry[] getTeamListEntries() {
        return this.fTeamListEntries.toArray(new TeamListEntry[this.fTeamListEntries.size()]);
    }

    public void add(TeamListEntry pTeamListEntry) {
        if (pTeamListEntry != null) {
            this.fTeamListEntries.add(pTeamListEntry);
        }
    }

    private void add(TeamListEntry[] pTeamListEntries) {
        if (ArrayTool.isProvided(pTeamListEntries)) {
            for (TeamListEntry teamListEntry : pTeamListEntries) {
                this.add(teamListEntry);
            }
        }
    }

    public int size() {
        return this.fTeamListEntries.size();
    }

    public TeamList filterActiveTeams() {
        TeamList filteredTeamList = new TeamList();
        for (TeamListEntry teamListEntry : this.getTeamListEntries()) {
            if (TeamStatus.ACTIVE != teamListEntry.getTeamStatus()) continue;
            filteredTeamList.add(teamListEntry);
        }
        return filteredTeamList;
    }

    public TeamList filterDivisions(String[] pDivisions) {
        TeamList filteredTeamList = new TeamList();
        if (ArrayTool.isProvided(pDivisions)) {
            for (TeamListEntry teamListEntry : this.getTeamListEntries()) {
                for (String division : pDivisions) {
                    if (!StringTool.isProvided(teamListEntry.getDivision()) || !teamListEntry.getDivision().equals(division)) continue;
                    filteredTeamList.add(teamListEntry);
                }
            }
        }
        return filteredTeamList;
    }

    @Override
    public JsonObject toJsonValue() {
        TeamListEntry[] teamListEntries;
        JsonObject jsonObject = new JsonObject();
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        JsonArray teamList = new JsonArray();
        for (TeamListEntry teamListEntry : teamListEntries = this.getTeamListEntries()) {
            teamList.add(teamListEntry.toJsonValue());
        }
        IJsonOption.TEAM_LIST_ENTRIES.addTo(jsonObject, teamList);
        return jsonObject;
    }

    @Override
    public TeamList initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        JsonArray teamListEntries = IJsonOption.TEAM_LIST_ENTRIES.getFrom(jsonObject);
        for (int i = 0; i < teamListEntries.size(); ++i) {
            TeamListEntry teamListEntry = new TeamListEntry();
            teamListEntry.initFrom(teamListEntries.get(i));
            this.add(teamListEntry);
        }
        return this;
    }
}

