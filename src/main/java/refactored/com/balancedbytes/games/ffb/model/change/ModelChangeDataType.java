/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.model.change;

import refactored.com.balancedbytes.games.ffb.IEnumWithId;
import refactored.com.balancedbytes.games.ffb.IEnumWithName;
import refactored.com.balancedbytes.games.ffb.PlayerActionFactory;
import refactored.com.balancedbytes.games.ffb.TurnModeFactory;
import refactored.com.balancedbytes.games.ffb.WeatherFactory;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonValue;



public enum ModelChangeDataType implements IEnumWithId, IEnumWithName
{
    NULL(1, "null"),
    BOOLEAN(2, "boolean"),
    STRING(3, "string"),
    PLAYER_ACTION(4, "playerAction"),
    SKILL(5, "skill"),
    LONG(6, "long"),
    DATE(7, "date"),
    TURN_MODE(8, "turnMode"),
    FIELD_COORDINATE(9, "fieldCoordinate"),
    DIALOG_ID(10, "dialogId"),
    DIALOG_PARAMETER(11, "dialogParameter"),
    INTEGER(12, "integer"),
    PLAYER_STATE(13, "playerState"),
    SERIOUS_INJURY(14, "seriousInjury"),
    SEND_TO_BOX_REASON(15, "sendToBoxReason"),
    BLOOD_SPOT(16, "bloodSpot"),
    TRACK_NUMBER(17, "trackNumber"),
    PUSHBACK_SQUARE(18, "pushbackSquare"),
    MOVE_SQUARE(19, "moveSquare"),
    WEATHER(20, "weather"),
    RANGE_RULER(21, "rangeRuler"),
    DICE_DECORATION(22, "diceDecoration"),
    INDUCEMENT(23, "inducement"),
    FIELD_MARKER(24, "fieldMarker"),
    PLAYER_MARKER(25, "playerMarker"),
    GAME_OPTION(26, "gameOption"),
    CARD(27, "card"),
    LEADER_STATE(28, "leaderState"),
    CARD_EFFECT(29, "cardEffect");
    
    private int fId;
    private String fName;

    private ModelChangeDataType(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public Object fromJsonValue(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        switch (this) {
            case BOOLEAN: {
                return pJsonValue.asBoolean();
            }
            case INTEGER: {
                return pJsonValue.asInt();
            }
            case LONG: {
                return pJsonValue.asLong();
            }
            case NULL: {
                return null;
            }
            case PLAYER_ACTION: {
                return UtilJson.toEnumWithName(new PlayerActionFactory(), pJsonValue);
            }
            case STRING: {
                return pJsonValue.asString();
            }
            case TURN_MODE: {
                return UtilJson.toEnumWithName(new TurnModeFactory(), pJsonValue);
            }
            case WEATHER: {
                return UtilJson.toEnumWithName(new WeatherFactory(), pJsonValue);
            }
            default: return NULL;
        }
    }

}

