/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonObjectOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Roster;
import com.balancedbytes.games.ffb.model.RosterPosition;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class Team
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "team";
    private static final String _XML_ATTRIBUTE_ID = "id";
    private static final String _XML_TAG_NAME = "name";
    private static final String _XML_TAG_RACE = "race";
    private static final String _XML_TAG_ROSTER_ID = "rosterId";
    private static final String _XML_TAG_RE_ROLLS = "reRolls";
    private static final String _XML_TAG_APOTHECARIES = "apothecaries";
    private static final String _XML_TAG_CHEERLEADERS = "cheerleaders";
    private static final String _XML_TAG_ASSISTANT_COACHES = "assistantCoaches";
    private static final String _XML_TAG_COACH = "coach";
    private static final String _XML_TAG_FAN_FACTOR = "fanFactor";
    private static final String _XML_TAG_TEAM_VALUE = "teamValue";
    private static final String _XML_TAG_DIVISION = "division";
    private static final String _XML_TAG_TREASURY = "treasury";
    private static final String _XML_TAG_BASE_ICON_PATH = "baseIconPath";
    private static final String _XML_TAG_LOGO_URL = "logo";
    private String fId;
    private String fName;
    private String fRace;
    private int fReRolls;
    private int fApothecaries;
    private int fCheerleaders;
    private int fAssistantCoaches;
    private String fCoach;
    private int fFanFactor;
    private int fTeamValue;
    private String fDivision;
    private int fTreasury;
    private String fBaseIconPath;
    private String fLogoUrl;
    private String fRosterId;
    private Roster fRoster;
    private InducementSet fInducementSet;
    private transient Map<String, Player> fPlayerById = new HashMap<String, Player>();
    private transient Map<Integer, Player> fPlayerByNr = new HashMap<Integer, Player>();

    public Team() {
        this.updateRoster(new Roster());
    }

    public void setId(String pId) {
        this.fId = pId;
    }

    public String getRosterId() {
        return this.fRosterId;
    }

    public void setRosterId(String pRosterId) {
        this.fRosterId = pRosterId;
    }

    public void setRace(String pRace) {
        this.fRace = pRace;
    }

    public String getRace() {
        return this.fRace;
    }

    public int getFanFactor() {
        return this.fFanFactor;
    }

    public void setFanFactor(int fanFactor) {
        this.fFanFactor = fanFactor;
    }

    public String getId() {
        return this.fId;
    }

    public void addPlayer(Player pPlayer) {
        this.fPlayerByNr.put(pPlayer.getNr(), pPlayer);
        if (pPlayer.getId() != null) {
            this.fPlayerById.put(pPlayer.getId(), pPlayer);
        }
        pPlayer.setTeam(this);
    }

    public void removePlayer(Player pPlayer) {
        this.fPlayerByNr.remove(pPlayer.getNr());
        this.fPlayerById.remove(pPlayer.getId());
        pPlayer.setTeam(null);
    }

    public Player getPlayerById(String pId) {
        return this.fPlayerById.get(pId);
    }

    public Player getPlayerByNr(int pNr) {
        return this.fPlayerByNr.get(pNr);
    }

    public Player[] getPlayers() {
        Player[] players = this.fPlayerByNr.values().toArray(new Player[this.fPlayerByNr.size()]);
        Arrays.sort(players, new PlayerComparatorByNr());
        return players;
    }

    public int getMaxPlayerNr() {
        int maxPlayerNr = 0;
        for (Player player : this.getPlayers()) {
            if (player.getNr() <= maxPlayerNr) continue;
            maxPlayerNr = player.getNr();
        }
        return maxPlayerNr;
    }

    public int getNrOfAvailablePlayersInPosition(RosterPosition pos) {
        int nrOfPlayersInPosition = 0;
        for (Player player : this.getPlayers()) {
            if (player.getPosition() != pos || player.getRecoveringInjury() != null) continue;
            ++nrOfPlayersInPosition;
        }
        return nrOfPlayersInPosition;
    }

    public int getNrOfRegularPlayers() {
        int nrOfRegularPlayers = 0;
        for (Player player : this.getPlayers()) {
            if (player.getPlayerType() == PlayerType.JOURNEYMAN || player.getPlayerType() == PlayerType.STAR) continue;
            ++nrOfRegularPlayers;
        }
        return nrOfRegularPlayers;
    }

    public int getNrOfAvailablePlayers() {
        int nrOfAvailablePlayers = 0;
        for (Player player : this.getPlayers()) {
            if (player.getRecoveringInjury() != null) continue;
            ++nrOfAvailablePlayers;
        }
        return nrOfAvailablePlayers;
    }

    public boolean hasPlayer(Player pPlayer) {
        return pPlayer != null && this.getPlayerById(pPlayer.getId()) != null;
    }

    public String getName() {
        return this.fName;
    }

    public void setName(String pName) {
        this.fName = pName;
    }

    public int getReRolls() {
        return this.fReRolls;
    }

    public int getApothecaries() {
        return this.fApothecaries;
    }

    public void setApothecaries(int pApothecaries) {
        this.fApothecaries = pApothecaries;
    }

    public void setReRolls(int pReRolls) {
        this.fReRolls = pReRolls;
    }

    public Roster getRoster() {
        return this.fRoster;
    }

    public void updateRoster(Roster pRoster) {
        this.fRoster = pRoster;
        if (this.fRoster != null) {
            this.setRosterId(this.fRoster.getId());
            this.setRace(this.fRoster.getName());
            for (Player player : this.getPlayers()) {
                String positionId = player.getPositionId();
                player.updatePosition(this.fRoster.getPositionById(positionId));
            }
        }
    }

    public int getCheerleaders() {
        return this.fCheerleaders;
    }

    public void setCheerleaders(int cheerleaders) {
        this.fCheerleaders = cheerleaders;
    }

    public int getAssistantCoaches() {
        return this.fAssistantCoaches;
    }

    public void setAssistantCoaches(int assistantCoaches) {
        this.fAssistantCoaches = assistantCoaches;
    }

    public String getCoach() {
        return this.fCoach;
    }

    public int getTeamValue() {
        return this.fTeamValue;
    }

    public void setTeamValue(int pTeamValue) {
        this.fTeamValue = pTeamValue;
    }

    public String getDivision() {
        return this.fDivision;
    }

    public int getTreasury() {
        return this.fTreasury;
    }

    public void setTreasury(int pTreasury) {
        this.fTreasury = pTreasury;
    }

    public String getBaseIconPath() {
        return this.fBaseIconPath;
    }

    public void setBaseIconPath(String pBaseIconPath) {
        this.fBaseIconPath = pBaseIconPath;
    }

    public void setLogoUrl(String pTeamLogoUrl) {
        this.fLogoUrl = pTeamLogoUrl;
    }

    public String getLogoUrl() {
        return this.fLogoUrl;
    }

    public void setDivision(String division) {
        this.fDivision = division;
    }

    public void setCoach(String coach) {
        this.fCoach = coach;
    }

    public InducementSet getInducementSet() {
        return this.fInducementSet;
    }

    public void setInducementSet(InducementSet pInducementSet) {
        this.fInducementSet = pInducementSet;
    }

    public static Comparator<Team> comparatorByName() {
        return new Comparator<Team>(){

            @Override
            public int compare(Team pTeam1, Team pTeam2) {
                return pTeam1.getName().compareTo(pTeam2.getName());
            }
        };
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "id", this.getId());
        UtilXml.startElement(pHandler, "team", attributes);
        UtilXml.addValueElement(pHandler, "coach", this.getCoach());
        UtilXml.addValueElement(pHandler, "name", this.getName());
        UtilXml.addValueElement(pHandler, "race", this.getRace());
        UtilXml.addValueElement(pHandler, "rosterId", this.getRosterId());
        UtilXml.addValueElement(pHandler, "reRolls", this.getReRolls());
        UtilXml.addValueElement(pHandler, "fanFactor", this.getFanFactor());
        UtilXml.addValueElement(pHandler, "teamValue", this.getTeamValue());
        UtilXml.addValueElement(pHandler, "apothecaries", this.getApothecaries());
        UtilXml.addValueElement(pHandler, "cheerleaders", this.getCheerleaders());
        UtilXml.addValueElement(pHandler, "assistantCoaches", this.getAssistantCoaches());
        UtilXml.addValueElement(pHandler, "division", this.getDivision());
        UtilXml.addValueElement(pHandler, "treasury", this.getTreasury());
        UtilXml.addValueElement(pHandler, "baseIconPath", this.getBaseIconPath());
        UtilXml.addValueElement(pHandler, "logo", this.getLogoUrl());
        for (Player player : this.getPlayers()) {
            player.addToXml(pHandler);
        }
        UtilXml.endElement(pHandler, "team");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        IXmlSerializable xmlElement = this;
        if ("team".equals(pXmlTag)) {
            this.setId(UtilXml.getStringAttribute(pXmlAttributes, "id"));
        }
        if ("player".equals(pXmlTag)) {
            Player player = new Player();
            player.startXmlElement(pXmlTag, pXmlAttributes);
            this.addPlayer(player);
            xmlElement = player;
        }
        if ("inducementSet".equals(pXmlTag)) {
            this.setInducementSet(new InducementSet());
            this.getInducementSet().startXmlElement(pXmlTag, pXmlAttributes);
            xmlElement = this.getInducementSet();
        }
        return xmlElement;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        boolean complete = "team".equals(pXmlTag);
        if (!complete) {
            if ("name".equals(pXmlTag)) {
                this.fName = pValue;
            }
            if ("coach".equals(pXmlTag)) {
                this.setCoach(pValue);
            }
            if ("race".equals(pXmlTag)) {
                this.setRace(pValue);
            }
            if ("rosterId".equals(pXmlTag)) {
                this.setRosterId(pValue);
            }
            if ("reRolls".equals(pXmlTag)) {
                this.setReRolls(Integer.parseInt(pValue));
            }
            if ("fanFactor".equals(pXmlTag)) {
                this.setFanFactor(Integer.parseInt(pValue));
            }
            if ("apothecaries".equals(pXmlTag)) {
                this.setApothecaries(Integer.parseInt(pValue));
            }
            if ("cheerleaders".equals(pXmlTag)) {
                this.setCheerleaders(Integer.parseInt(pValue));
            }
            if ("assistantCoaches".equals(pXmlTag)) {
                this.setAssistantCoaches(Integer.parseInt(pValue));
            }
            if ("teamValue".equals(pXmlTag)) {
                this.setTeamValue(Integer.parseInt(pValue));
            }
            if ("division".equals(pXmlTag)) {
                this.setDivision(pValue);
            }
            if ("treasury".equals(pXmlTag)) {
                this.setTreasury(Integer.parseInt(pValue));
            }
            if ("baseIconPath".equals(pXmlTag)) {
                this.setBaseIconPath(pValue);
            }
            if ("logo".equals(pXmlTag)) {
                this.setLogoUrl(pValue);
            }
        }
        return complete;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fId);
        IJsonOption.TEAM_NAME.addTo(jsonObject, this.fName);
        IJsonOption.COACH.addTo(jsonObject, this.fCoach);
        IJsonOption.RACE.addTo(jsonObject, this.fRace);
        IJsonOption.RE_ROLLS.addTo(jsonObject, this.fReRolls);
        IJsonOption.APOTHECARIES.addTo(jsonObject, this.fApothecaries);
        IJsonOption.CHEERLEADERS.addTo(jsonObject, this.fCheerleaders);
        IJsonOption.ASSISTANT_COACHES.addTo(jsonObject, this.fAssistantCoaches);
        IJsonOption.FAN_FACTOR.addTo(jsonObject, this.fFanFactor);
        IJsonOption.TEAM_VALUE.addTo(jsonObject, this.fTeamValue);
        IJsonOption.TREASURY.addTo(jsonObject, this.fTreasury);
        IJsonOption.BASE_ICON_PATH.addTo(jsonObject, this.fBaseIconPath);
        IJsonOption.LOGO_URL.addTo(jsonObject, this.fLogoUrl);
        JsonArray playerArray = new JsonArray();
        for (Player player : this.getPlayers()) {
            playerArray.add(player.toJsonValue());
        }
        IJsonOption.PLAYER_ARRAY.addTo(jsonObject, playerArray);
        if (this.fRoster != null) {
            IJsonOption.ROSTER.addTo(jsonObject, this.fRoster.toJsonValue());
        }
        return jsonObject;
    }

    @Override
    public Team initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fName = IJsonOption.TEAM_NAME.getFrom(jsonObject);
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.fRace = IJsonOption.RACE.getFrom(jsonObject);
        this.fReRolls = IJsonOption.RE_ROLLS.getFrom(jsonObject);
        this.fApothecaries = IJsonOption.APOTHECARIES.getFrom(jsonObject);
        this.fCheerleaders = IJsonOption.CHEERLEADERS.getFrom(jsonObject);
        this.fAssistantCoaches = IJsonOption.ASSISTANT_COACHES.getFrom(jsonObject);
        this.fFanFactor = IJsonOption.FAN_FACTOR.getFrom(jsonObject);
        this.fTeamValue = IJsonOption.TEAM_VALUE.getFrom(jsonObject);
        this.fTreasury = IJsonOption.TREASURY.getFrom(jsonObject);
        this.fBaseIconPath = IJsonOption.BASE_ICON_PATH.getFrom(jsonObject);
        this.fLogoUrl = IJsonOption.LOGO_URL.getFrom(jsonObject);
        this.fPlayerById.clear();
        this.fPlayerByNr.clear();
        JsonArray playerArray = IJsonOption.PLAYER_ARRAY.getFrom(jsonObject);
        for (int i = 0; i < playerArray.size(); ++i) {
            this.addPlayer(new Player().initFrom(playerArray.get(i)));
        }
        Roster roster = null;
        JsonObject rosterObject = IJsonOption.ROSTER.getFrom(jsonObject);
        if (rosterObject != null) {
            roster = new Roster().initFrom(rosterObject);
        }
        this.updateRoster(roster);
        return this;
    }

    private class PlayerComparatorByNr
    implements Comparator<Player> {
        private PlayerComparatorByNr() {
        }

        @Override
        public int compare(Player pPlayer1, Player pPlayer2) {
            return pPlayer1.getNr() - pPlayer2.getNr();
        }
    }

}

