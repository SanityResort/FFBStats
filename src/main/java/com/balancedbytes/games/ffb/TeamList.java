/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.TeamListEntry;
import com.balancedbytes.games.ffb.TeamStatus;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class TeamList
implements IXmlSerializable,
IJsonSerializable {
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
    public void addToXml(TransformerHandler pHandler) {
        TeamListEntry[] teamListEntries;
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "coach", this.getCoach());
        UtilXml.startElement(pHandler, "teams", attributes);
        for (TeamListEntry teamListEntry : teamListEntries = this.getTeamListEntries()) {
            teamListEntry.addToXml(pHandler);
        }
        UtilXml.endElement(pHandler, "teams");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        IXmlSerializable xmlElement = this;
        if ("teams".equals(pXmlTag)) {
            this.fCoach = UtilXml.getStringAttribute(pXmlAttributes, "coach");
        }
        if ("team".equals(pXmlTag)) {
            TeamListEntry teamListEntry = new TeamListEntry();
            teamListEntry.startXmlElement(pXmlTag, pXmlAttributes);
            this.add(teamListEntry);
            xmlElement = teamListEntry;
        }
        return xmlElement;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "teams".equals(pXmlTag);
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

