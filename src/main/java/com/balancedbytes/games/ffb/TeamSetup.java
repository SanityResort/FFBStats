/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.util.UtilBox;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class TeamSetup
implements IXmlSerializable,
IJsonSerializable {
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

    public void applyTo(Game pGame) {
        boolean homeSetup = this.getTeamId().equals(pGame.getTeamHome().getId());
        Team team = homeSetup ? pGame.getTeamHome() : pGame.getTeamAway();
        for (Player player : team.getPlayers()) {
            FieldCoordinate playerCoordinate = this.getCoordinate(player.getNr());
            PlayerState playerState = pGame.getFieldModel().getPlayerState(player);
            if (!playerState.canBeSetUp()) continue;
            if (playerCoordinate != null) {
                pGame.getFieldModel().setPlayerState(player, playerState.changeBase(1).changeActive(true));
                if (homeSetup) {
                    pGame.getFieldModel().setPlayerCoordinate(player, playerCoordinate);
                    continue;
                }
                pGame.getFieldModel().setPlayerCoordinate(player, playerCoordinate.transform());
                continue;
            }
            pGame.getFieldModel().setPlayerState(player, playerState.changeBase(9));
            UtilBox.putPlayerIntoBox(pGame, player);
        }
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "name", this.getName());
        UtilXml.addAttribute(attributes, "teamId", this.getTeamId());
        UtilXml.startElement(pHandler, "teamSetup", attributes);
        int[] playerNumbers = this.getPlayerNumbers();
        FieldCoordinate[] coordinates = this.getCoordinates();
        for (int i = 0; i < playerNumbers.length; ++i) {
            attributes = new AttributesImpl();
            UtilXml.addAttribute(attributes, "nr", playerNumbers[i]);
            UtilXml.startElement(pHandler, "player", attributes);
            attributes = new AttributesImpl();
            UtilXml.addAttribute(attributes, "x", coordinates[i].getX());
            UtilXml.addAttribute(attributes, "y", coordinates[i].getY());
            UtilXml.startElement(pHandler, "coordinate", attributes);
            UtilXml.endElement(pHandler, "coordinate");
            UtilXml.endElement(pHandler, "player");
        }
        UtilXml.endElement(pHandler, "teamSetup");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        if ("teamSetup".equals(pXmlTag)) {
            this.setName(UtilXml.getStringAttribute(pXmlAttributes, "name"));
            this.setTeamId(UtilXml.getStringAttribute(pXmlAttributes, "teamId"));
        }
        if ("player".equals(pXmlTag)) {
            this.fCurrentPlayerNr = UtilXml.getIntAttribute(pXmlAttributes, "nr");
        }
        if ("coordinate".equals(pXmlTag)) {
            int x = UtilXml.getIntAttribute(pXmlAttributes, "x");
            int y = UtilXml.getIntAttribute(pXmlAttributes, "y");
            FieldCoordinate coordinate = new FieldCoordinate(x, y);
            this.addCoordinate(coordinate, this.fCurrentPlayerNr);
        }
        return this;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "teamSetup".equals(pXmlTag);
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

