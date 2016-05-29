/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class RangeRuler implements IJsonSerializable {
    public static final String XML_TAG = "rangeRuler";
    private static final String _XML_ATTRIBUTE_THROWER_ID = "throwerId";
    private static final String _XML_ATTRIBUTE_MINIMUM_ROLL = "minimumRoll";
    private static final String _XML_ATTRIBUTE_THROW_TEAM_MATE = "throwTeamMate";
    private static final String _XML_TAG_TARGET_COORDINATE = "targetCoordinate";
    private static final String _XML_ATTRIBUTE_X = "x";
    private static final String _XML_ATTRIBUTE_Y = "y";
    private String fThrowerId;
    private FieldCoordinate fTargetCoordinate;
    private int fMinimumRoll;
    private boolean fThrowTeamMate;

    public RangeRuler() {
    }

    public RangeRuler(String pThrowerId, FieldCoordinate pTargetCoordinate, int pMinimumRoll, boolean pThrowTeamMate) {
        this.fThrowerId = pThrowerId;
        this.fTargetCoordinate = pTargetCoordinate;
        this.fMinimumRoll = pMinimumRoll;
        this.fThrowTeamMate = pThrowTeamMate;
    }

    public String getThrowerId() {
        return this.fThrowerId;
    }

    public FieldCoordinate getTargetCoordinate() {
        return this.fTargetCoordinate;
    }

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    public boolean isThrowTeamMate() {
        return this.fThrowTeamMate;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        RangeRuler other = (RangeRuler)obj;
        if (this.fTargetCoordinate == null ? other.fTargetCoordinate != null : !this.fTargetCoordinate.equals(other.fTargetCoordinate)) {
            return false;
        }
        if (this.fMinimumRoll != other.fMinimumRoll) {
            return false;
        }
        if (this.fThrowTeamMate != other.fThrowTeamMate) {
            return false;
        }
        if (this.fThrowerId == null ? other.fThrowerId != null : !this.fThrowerId.equals(other.fThrowerId)) {
            return false;
        }
        return true;
    }

    public RangeRuler transform() {
        return new RangeRuler(this.getThrowerId(), FieldCoordinate.transform(this.getTargetCoordinate()), this.getMinimumRoll(), this.isThrowTeamMate());
    }

    public static RangeRuler transform(RangeRuler pTrackNumber) {
        return pTrackNumber != null ? pTrackNumber.transform() : null;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.THROWER_ID.addTo(jsonObject, this.fThrowerId);
        IJsonOption.TARGET_COORDINATE.addTo(jsonObject, this.fTargetCoordinate);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        IJsonOption.THROW_TEAM_MATE.addTo(jsonObject, this.fThrowTeamMate);
        return jsonObject;
    }

    @Override
    public RangeRuler initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fThrowerId = IJsonOption.THROWER_ID.getFrom(jsonObject);
        this.fTargetCoordinate = IJsonOption.TARGET_COORDINATE.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fThrowTeamMate = IJsonOption.THROW_TEAM_MATE.getFrom(jsonObject);
        return this;
    }
}

