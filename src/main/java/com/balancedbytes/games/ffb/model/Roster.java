/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class Roster implements IJsonSerializable {
    public static final String XML_TAG = "roster";
    private static final String _XML_ATTRIBUTE_ID = "id";
    private static final String _XML_ATTRIBUTE_TEAM = "team";
    private static final String _XML_TAG_NAME = "name";
    private static final String _XML_TAG_RE_ROLL_COST = "reRollCost";
    private static final String _XML_TAG_MAX_RE_ROLLS = "maxReRolls";
    private static final String _XML_TAG_BASE_ICON_PATH = "baseIconPath";
    private static final String _XML_TAG_LOGO_URL = "logo";
    private static final String _XML_TAG_RAISED_POSITION_ID = "raisedPositionId";
    private static final String _XML_TAG_APOTHECARY = "apothecary";
    private static final String _XML_TAG_NECROMANCER = "necromancer";
    private static final String _XML_TAG_UNDEAD = "undead";
    private String fId;
    private String fName;
    private int fReRollCost;
    private int fMaxReRolls;
    private String fBaseIconPath;
    private String fLogoUrl;
    private String fRaisedPositionId;
    private boolean fApothecary = true;
    private boolean fNecromancer;
    private boolean fUndead;
    private RosterPosition fCurrentlyParsedRosterPosition;
    private Map<String, RosterPosition> fRosterPositionById = new HashMap<String, RosterPosition>();
    private Map<String, RosterPosition> fRosterPositionByName = new HashMap<String, RosterPosition>();

    public String getName() {
        return this.fName;
    }

    public void setName(String name) {
        this.fName = name;
    }

    public int getReRollCost() {
        return this.fReRollCost;
    }

    public void setReRollCost(int reRollCost) {
        this.fReRollCost = reRollCost;
    }

    public RosterPosition[] getPositions() {
        return this.fRosterPositionById.values().toArray(new RosterPosition[this.fRosterPositionById.size()]);
    }

    public RosterPosition getPositionById(String pPositionId) {
        return this.fRosterPositionById.get(pPositionId);
    }

    public RosterPosition getPositionByName(String pPositionName) {
        return this.fRosterPositionByName.get(pPositionName);
    }

    public int getMaxReRolls() {
        return this.fMaxReRolls;
    }

    public void setMaxReRolls(int maxReRolls) {
        this.fMaxReRolls = maxReRolls;
    }

    public String getId() {
        return this.fId;
    }

    public void setId(String pId) {
        this.fId = pId;
    }

    public RosterPosition getRaisedRosterPosition() {
        return this.fRosterPositionById.get(this.fRaisedPositionId);
    }

    private void addPosition(RosterPosition pPosition) {
        if (pPosition != null) {
            this.fRosterPositionById.put(pPosition.getId(), pPosition);
            this.fRosterPositionByName.put(pPosition.getName(), pPosition);
            pPosition.setRoster(this);
        }
    }

    public String getBaseIconPath() {
        return this.fBaseIconPath;
    }

    public void setBaseIconPath(String pBaseIconPath) {
        this.fBaseIconPath = pBaseIconPath;
    }

    public void setLogoUrl(String pLogoUrl) {
        this.fLogoUrl = pLogoUrl;
    }

    public String getLogoUrl() {
        return this.fLogoUrl;
    }

    public boolean hasApothecary() {
        return this.fApothecary;
    }

    public void setApothecary(boolean pApothecary) {
        this.fApothecary = pApothecary;
    }

    public boolean hasNecromancer() {
        return this.fNecromancer;
    }

    public void setNecromancer(boolean pNecromancer) {
        this.fNecromancer = pNecromancer;
    }

    public boolean isUndead() {
        return this.fUndead;
    }

    public void setUndead(boolean pUndead) {
        this.fUndead = pUndead;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.ROSTER_ID.addTo(jsonObject, this.fId);
        IJsonOption.ROSTER_NAME.addTo(jsonObject, this.fName);
        IJsonOption.RE_ROLL_COST.addTo(jsonObject, this.fReRollCost);
        IJsonOption.MAX_RE_ROLLS.addTo(jsonObject, this.fMaxReRolls);
        IJsonOption.BASE_ICON_PATH.addTo(jsonObject, this.fBaseIconPath);
        IJsonOption.LOGO_URL.addTo(jsonObject, this.fLogoUrl);
        IJsonOption.RAISED_POSITION_ID.addTo(jsonObject, this.fRaisedPositionId);
        IJsonOption.APOTHECARY.addTo(jsonObject, this.fApothecary);
        IJsonOption.NECROMANCER.addTo(jsonObject, this.fNecromancer);
        IJsonOption.UNDEAD.addTo(jsonObject, this.fUndead);
        JsonArray positionArray = new JsonArray();
        for (RosterPosition position : this.getPositions()) {
            positionArray.add(position.toJsonValue());
        }
        IJsonOption.POSITION_ARRAY.addTo(jsonObject, positionArray);
        return jsonObject;
    }

    @Override
    public Roster initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.ROSTER_ID.getFrom(jsonObject);
        this.fName = IJsonOption.ROSTER_NAME.getFrom(jsonObject);
        this.fReRollCost = IJsonOption.RE_ROLL_COST.getFrom(jsonObject);
        this.fMaxReRolls = IJsonOption.MAX_RE_ROLLS.getFrom(jsonObject);
        this.fBaseIconPath = IJsonOption.BASE_ICON_PATH.getFrom(jsonObject);
        this.fLogoUrl = IJsonOption.LOGO_URL.getFrom(jsonObject);
        this.fRaisedPositionId = IJsonOption.RAISED_POSITION_ID.getFrom(jsonObject);
        this.fApothecary = IJsonOption.APOTHECARY.getFrom(jsonObject);
        this.fNecromancer = IJsonOption.NECROMANCER.getFrom(jsonObject);
        this.fUndead = IJsonOption.UNDEAD.getFrom(jsonObject);
        this.fRosterPositionById.clear();
        this.fRosterPositionByName.clear();
        JsonArray positionArray = IJsonOption.POSITION_ARRAY.getFrom(jsonObject);
        if (positionArray != null) {
            for (int i = 0; i < positionArray.size(); ++i) {
                this.addPosition(new RosterPosition().initFrom(positionArray.get(i)));
            }
        }
        return this;
    }
}

