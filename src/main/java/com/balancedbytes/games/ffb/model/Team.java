package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonReadable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class Team implements IJsonReadable {

    private String fId;
    private String fName;
    private String fRace;
    private String fCoach;
    private transient Map<String, Player> fPlayerById = new HashMap<String, Player>();

    public String getRace() {
        return this.fRace;
    }

    public String getId() {
        return this.fId;
    }

    private void addPlayer(Player pPlayer) {
            this.fPlayerById.put(pPlayer.getId(), pPlayer);
    }

    public Player[] getPlayers() {
        return this.fPlayerById.values().toArray(new Player[this.fPlayerById.size()]);
    }

    public String getName() {
        return this.fName;
    }

    public String getCoach() {
        return this.fCoach;
    }

    @Override
    public Team initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fName = IJsonOption.TEAM_NAME.getFrom(jsonObject);
        this.fCoach = IJsonOption.COACH.getFrom(jsonObject);
        this.fRace = IJsonOption.RACE.getFrom(jsonObject);
        this.fPlayerById.clear();
        JsonArray playerArray = IJsonOption.PLAYER_ARRAY.getFrom(jsonObject);
        for (int i = 0; i < playerArray.size(); ++i) {
            this.addPlayer(new Player().initFrom(playerArray.get(i)));
        }

        return this;
    }
}

