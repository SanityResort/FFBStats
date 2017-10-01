/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.LinkedList;

public class FieldCoordinateBounds
implements IJsonSerializable {
    public static final FieldCoordinateBounds FIELD = new FieldCoordinateBounds(new FieldCoordinate(0, 0), new FieldCoordinate(25, 14));
    public static final FieldCoordinateBounds HALF_HOME = new FieldCoordinateBounds(new FieldCoordinate(0, 0), new FieldCoordinate(12, 14));
    public static final FieldCoordinateBounds HALF_AWAY = new FieldCoordinateBounds(new FieldCoordinate(13, 0), new FieldCoordinate(25, 14));
    public static final FieldCoordinateBounds UPPER_HALF = new FieldCoordinateBounds(new FieldCoordinate(0, 0), new FieldCoordinate(25, 7));
    public static final FieldCoordinateBounds LOWER_HALF = new FieldCoordinateBounds(new FieldCoordinate(0, 7), new FieldCoordinate(25, 14));
    public static final FieldCoordinateBounds CENTER_FIELD_HOME = new FieldCoordinateBounds(new FieldCoordinate(0, 4), new FieldCoordinate(11, 10));
    public static final FieldCoordinateBounds CENTER_FIELD_AWAY = new FieldCoordinateBounds(new FieldCoordinate(14, 4), new FieldCoordinate(25, 10));
    public static final FieldCoordinateBounds LOS_HOME = new FieldCoordinateBounds(new FieldCoordinate(12, 4), new FieldCoordinate(12, 10));
    public static final FieldCoordinateBounds LOS_AWAY = new FieldCoordinateBounds(new FieldCoordinate(13, 4), new FieldCoordinate(13, 10));
    public static final FieldCoordinateBounds UPPER_WIDE_ZONE_HOME = new FieldCoordinateBounds(new FieldCoordinate(0, 0), new FieldCoordinate(12, 3));
    public static final FieldCoordinateBounds UPPER_WIDE_ZONE_AWAY = new FieldCoordinateBounds(new FieldCoordinate(13, 0), new FieldCoordinate(25, 3));
    public static final FieldCoordinateBounds LOWER_WIDE_ZONE_HOME = new FieldCoordinateBounds(new FieldCoordinate(0, 11), new FieldCoordinate(12, 14));
    public static final FieldCoordinateBounds LOWER_WIDE_ZONE_AWAY = new FieldCoordinateBounds(new FieldCoordinate(13, 11), new FieldCoordinate(25, 14));
    public static final FieldCoordinateBounds ENDZONE_HOME = new FieldCoordinateBounds(new FieldCoordinate(0, 0), new FieldCoordinate(0, 14));
    public static final FieldCoordinateBounds ENDZONE_AWAY = new FieldCoordinateBounds(new FieldCoordinate(25, 0), new FieldCoordinate(25, 14));
    public static final FieldCoordinateBounds SIDELINE_UPPER = new FieldCoordinateBounds(new FieldCoordinate(1, 0), new FieldCoordinate(24, 0));
    public static final FieldCoordinateBounds SIDELINE_LOWER = new FieldCoordinateBounds(new FieldCoordinate(1, 14), new FieldCoordinate(24, 14));
    private FieldCoordinate fTopLeftCorner;
    private FieldCoordinate fBottomRightCorner;

    public FieldCoordinateBounds() {
    }

    public FieldCoordinateBounds(FieldCoordinate pTopLeftCorner, FieldCoordinate pBottomRightCorner) {
        this.fTopLeftCorner = pTopLeftCorner;
        this.fBottomRightCorner = pBottomRightCorner;
    }

    public FieldCoordinate getTopLeftCorner() {
        return this.fTopLeftCorner;
    }

    public FieldCoordinate getBottomRightCorner() {
        return this.fBottomRightCorner;
    }

    public boolean isInBounds(FieldCoordinate pCoordinate) {
        boolean result = true;
        if (pCoordinate == null) {
            result = false;
        } else {
            if (pCoordinate.getX() < this.fTopLeftCorner.getX()) {
                result = false;
            }
            if (pCoordinate.getY() < this.fTopLeftCorner.getY()) {
                result = false;
            }
            if (pCoordinate.getX() > this.fBottomRightCorner.getX()) {
                result = false;
            }
            if (pCoordinate.getY() > this.fBottomRightCorner.getY()) {
                result = false;
            }
        }
        return result;
    }

    public FieldCoordinate[] fieldCoordinates() {
        LinkedList<FieldCoordinate> fieldCoordinates = new LinkedList<FieldCoordinate>();
        for (int i = this.getTopLeftCorner().getX(); i <= this.getBottomRightCorner().getX(); ++i) {
            for (int k = this.getTopLeftCorner().getY(); k <= this.getBottomRightCorner().getY(); ++k) {
                fieldCoordinates.add(new FieldCoordinate(i, k));
            }
        }
        return fieldCoordinates.toArray(new FieldCoordinate[fieldCoordinates.size()]);
    }

    public int width() {
        return this.getBottomRightCorner().getX() - this.getTopLeftCorner().getX() + 1;
    }

    public int height() {
        return this.getBottomRightCorner().getY() - this.getTopLeftCorner().getY() + 1;
    }

    public int size() {
        return this.width() * this.height();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.fBottomRightCorner == null ? 0 : this.fBottomRightCorner.hashCode());
        result = 31 * result + (this.fTopLeftCorner == null ? 0 : this.fTopLeftCorner.hashCode());
        return result;
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
        FieldCoordinateBounds other = (FieldCoordinateBounds)obj;
        if (this.fBottomRightCorner == null ? other.fBottomRightCorner != null : !this.fBottomRightCorner.equals(other.fBottomRightCorner)) {
            return false;
        }
        if (this.fTopLeftCorner == null ? other.fTopLeftCorner != null : !this.fTopLeftCorner.equals(other.fTopLeftCorner)) {
            return false;
        }
        return true;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.TOP_LEFT.addTo(jsonObject, this.fTopLeftCorner);
        IJsonOption.BOTTOM_RIGHT.addTo(jsonObject, this.fBottomRightCorner);
        return jsonObject;
    }

    @Override
    public FieldCoordinateBounds initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fTopLeftCorner = IJsonOption.TOP_LEFT.getFrom(jsonObject);
        this.fBottomRightCorner = IJsonOption.BOTTOM_RIGHT.getFrom(jsonObject);
        return this;
    }
}

