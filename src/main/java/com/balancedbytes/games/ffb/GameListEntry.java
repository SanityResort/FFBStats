/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonDateOption;
import com.balancedbytes.games.ffb.json.JsonLongOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Date;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class GameListEntry
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "game";
    private static final String _XML_ATTRIBUTE_ID = "id";
    private static final String _XML_ATTRIBUTE_STARTED = "started";
    private static final String _XML_ATTRIBUTE_NAME = "name";
    private static final String _XML_ATTRIBUTE_COACH = "coach";
    private static final String _XML_TAG_HOME_TEAM = "homeTeam";
    private static final String _XML_TAG_AWAY_TEAM = "awayTeam";
    private long fGameId;
    private Date fStarted;
    private String fTeamHomeId;
    private String fTeamHomeName;
    private String fTeamHomeCoach;
    private String fTeamAwayId;
    private String fTeamAwayName;
    private String fTeamAwayCoach;

    public void init(Game pGame) {
        if (pGame != null) {
            this.setGameId(pGame.getId());
            this.setStarted(pGame.getStarted());
            this.setTeamHomeId(pGame.getTeamHome().getId());
            this.setTeamHomeName(pGame.getTeamHome().getName());
            this.setTeamHomeCoach(pGame.getTeamHome().getCoach());
            this.setTeamAwayId(pGame.getTeamAway().getId());
            this.setTeamAwayName(pGame.getTeamAway().getName());
            this.setTeamAwayCoach(pGame.getTeamAway().getCoach());
        }
    }

    public long getGameId() {
        return this.fGameId;
    }

    public void setGameId(long pGameId) {
        this.fGameId = pGameId;
    }

    public Date getStarted() {
        return this.fStarted;
    }

    public void setStarted(Date pStarted) {
        this.fStarted = pStarted;
    }

    public String getTeamHomeId() {
        return this.fTeamHomeId;
    }

    public void setTeamHomeId(String pTeamHomeId) {
        this.fTeamHomeId = pTeamHomeId;
    }

    public String getTeamHomeName() {
        return this.fTeamHomeName;
    }

    public void setTeamHomeName(String pTeamHomeName) {
        this.fTeamHomeName = pTeamHomeName;
    }

    public String getTeamHomeCoach() {
        return this.fTeamHomeCoach;
    }

    public void setTeamHomeCoach(String pTeamHomeCoach) {
        this.fTeamHomeCoach = pTeamHomeCoach;
    }

    public String getTeamAwayId() {
        return this.fTeamAwayId;
    }

    public void setTeamAwayId(String pTeamAwayId) {
        this.fTeamAwayId = pTeamAwayId;
    }

    public String getTeamAwayName() {
        return this.fTeamAwayName;
    }

    public void setTeamAwayName(String pTeamAwayName) {
        this.fTeamAwayName = pTeamAwayName;
    }

    public String getTeamAwayCoach() {
        return this.fTeamAwayCoach;
    }

    public void setTeamAwayCoach(String pTeamAwayCoach) {
        this.fTeamAwayCoach = pTeamAwayCoach;
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "id", this.getGameId());
        UtilXml.addAttribute(attributes, "started", this.getStarted());
        UtilXml.startElement(pHandler, "game", attributes);
        attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "id", this.getTeamHomeId());
        UtilXml.addAttribute(attributes, "name", this.getTeamHomeName());
        UtilXml.addAttribute(attributes, "coach", this.getTeamHomeCoach());
        UtilXml.startElement(pHandler, "homeTeam", attributes);
        UtilXml.endElement(pHandler, "homeTeam");
        attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "id", this.getTeamAwayId());
        UtilXml.addAttribute(attributes, "name", this.getTeamAwayName());
        UtilXml.addAttribute(attributes, "coach", this.getTeamAwayCoach());
        UtilXml.startElement(pHandler, "awayTeam", attributes);
        UtilXml.endElement(pHandler, "awayTeam");
        UtilXml.endElement(pHandler, "game");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        if ("game".equals(pXmlTag)) {
            this.fGameId = UtilXml.getLongAttribute(pXmlAttributes, "id");
            this.fStarted = UtilXml.getTimestampAttribute(pXmlAttributes, "started");
        }
        if ("homeTeam".equals(pXmlTag)) {
            this.fTeamHomeId = UtilXml.getStringAttribute(pXmlAttributes, "id");
            this.fTeamHomeName = UtilXml.getStringAttribute(pXmlAttributes, "name");
            this.fTeamHomeCoach = UtilXml.getStringAttribute(pXmlAttributes, "coach");
        }
        if ("awayTeam".equals(pXmlTag)) {
            this.fTeamAwayId = UtilXml.getStringAttribute(pXmlAttributes, "id");
            this.fTeamAwayName = UtilXml.getStringAttribute(pXmlAttributes, "name");
            this.fTeamAwayCoach = UtilXml.getStringAttribute(pXmlAttributes, "coach");
        }
        return this;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "game".equals(pXmlTag);
    }

    @Override
    public JsonValue toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.GAME_ID.addTo(jsonObject, this.fGameId);
        IJsonOption.STARTED.addTo(jsonObject, this.fStarted);
        IJsonOption.TEAM_HOME_ID.addTo(jsonObject, this.fTeamHomeId);
        IJsonOption.TEAM_HOME_NAME.addTo(jsonObject, this.fTeamHomeName);
        IJsonOption.TEAM_HOME_COACH.addTo(jsonObject, this.fTeamHomeCoach);
        IJsonOption.TEAM_AWAY_ID.addTo(jsonObject, this.fTeamAwayId);
        IJsonOption.TEAM_AWAY_NAME.addTo(jsonObject, this.fTeamAwayName);
        IJsonOption.TEAM_AWAY_COACH.addTo(jsonObject, this.fTeamAwayCoach);
        return jsonObject;
    }

    @Override
    public GameListEntry initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fGameId = IJsonOption.GAME_ID.getFrom(jsonObject);
        this.fStarted = IJsonOption.STARTED.getFrom(jsonObject);
        this.fTeamHomeId = IJsonOption.TEAM_HOME_ID.getFrom(jsonObject);
        this.fTeamHomeName = IJsonOption.TEAM_HOME_NAME.getFrom(jsonObject);
        this.fTeamHomeCoach = IJsonOption.TEAM_HOME_COACH.getFrom(jsonObject);
        this.fTeamAwayId = IJsonOption.TEAM_AWAY_ID.getFrom(jsonObject);
        this.fTeamAwayName = IJsonOption.TEAM_AWAY_NAME.getFrom(jsonObject);
        this.fTeamAwayCoach = IJsonOption.TEAM_AWAY_COACH.getFrom(jsonObject);
        return this;
    }
}

