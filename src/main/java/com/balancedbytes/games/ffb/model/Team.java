/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonReadable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Team implements IJsonReadable {

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
    private transient Map<String, Player> fPlayerById = new HashMap<String, Player>();
    private transient Map<Integer, Player> fPlayerByNr = new HashMap<Integer, Player>();

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



    public static Comparator<Team> comparatorByName() {
        return new Comparator<Team>(){

            @Override
            public int compare(Team pTeam1, Team pTeam2) {
                return pTeam1.getName().compareTo(pTeam2.getName());
            }
        };
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

