/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilBox;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class TeamSetup implements IJsonSerializable {
    public static final String XML_TAG = "teamSetup";
    private static final String _XML_ATTRIBUTE_X = "x";
    private static final String _XML_ATTRIBUTE_Y = "y";
    private static final String _XML_ATTRIBUTE_NR = "nr";
    private static final String _XML_ATTRIBUTE_NAME = "name";
    private static final String _XML_ATTRIBUTE_TEAM_ID = "teamId";
    private static final String _XML_TAG_PLAYER = "player";
    private static final String _XML_TAG_COORDINATE = "coordinate";
    private String fName;
    private String fTeamId;
    private Map<Integer, FieldCoordinate> fCoordinateByPlayerNr = new HashMap<Integer, FieldCoordinate>();
    private transient int fCurrentPlayerNr;

    public int[] getPlayerNumbers() {
        Integer[] playerNumberIntegers = this.fCoordinateByPlayerNr.keySet().toArray(new Integer[this.fCoordinateByPlayerNr.size()]);
        int[] playerNumbers = new int[playerNumberIntegers.length];
        for (int i = 0; i < playerNumbers.length; ++i) {
            playerNumbers[i] = playerNumberIntegers[i];
        }
        return playerNumbers;
    }

    public FieldCoordinate[] getCoordinates() {
        int[] playerNumbers = this.getPlayerNumbers();
        FieldCoordinate[] coordinates = new FieldCoordinate[playerNumbers.length];
        for (int i = 0; i < coordinates.length; ++i) {
            coordinates[i] = this.getCoordinate(playerNumbers[i]);
        }
        return coordinates;
    }

    public void addCoordinate(FieldCoordinate pCoordinate, int pPlayerNr) {
        this.fCoordinateByPlayerNr.put(pPlayerNr, pCoordinate);
    }

    public FieldCoordinate getCoordinate(int pPlayerNr) {
        return this.fCoordinateByPlayerNr.get(pPlayerNr);
    }

    public void setName(String pName) {
        this.fName = pName;
    }

    public String getName() {
        return this.fName;
    }

    public void setTeamId(String pTeamId) {
        this.fTeamId = pTeamId;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public TeamSetup transform() {
        TeamSetup transformedSetup = new TeamSetup();
        transformedSetup.setName(this.getName());
        transformedSetup.setTeamId(this.getTeamId());
        int[] playerNumbers = this.getPlayerNumbers();
        FieldCoordinate[] coordinates = this.getCoordinates();
        for (int i = 0; i < playerNumbers.length; ++i) {
            transformedSetup.addCoordinate(coordinates[i], playerNumbers[i]);
        }
        return transformedSetup;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NAME.addTo(jsonObject, this.fName);
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        JsonArray playerPositions = new JsonArray();
        int[] playerNumbers = this.getPlayerNumbers();
        FieldCoordinate[] coordinates = this.getCoordinates();
        for (int i = 0; i < playerNumbers.length; ++i) {
            JsonObject playerPosition = new JsonObject();
            IJsonOption.PLAYER_NR.addTo(playerPosition, playerNumbers[i]);
            IJsonOption.COORDINATE.addTo(playerPosition, coordinates[i]);
            playerPositions.add(playerPosition);
        }
        IJsonOption.PLAYER_POSITIONS.addTo(jsonObject, playerPositions);
        return jsonObject;
    }

    @Override
    public TeamSetup initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fName = IJsonOption.NAME.getFrom(jsonObject);
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        JsonArray playerPositions = IJsonOption.PLAYER_POSITIONS.getFrom(jsonObject);
        for (int i = 0; i < playerPositions.size(); ++i) {
            JsonObject playerPosition = playerPositions.get(i).asObject();
            this.addCoordinate(IJsonOption.COORDINATE.getFrom(playerPosition), IJsonOption.PLAYER_NR.getFrom(playerPosition));
        }
        return this;
    }
}

