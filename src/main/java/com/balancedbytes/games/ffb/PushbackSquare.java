/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public final class PushbackSquare
implements IJsonSerializable {
    private FieldCoordinate fCoordinate;
    private Direction fDirection;
    private boolean fSelected;
    private boolean fLocked;
    private boolean fHomeChoice;

    public PushbackSquare() {
    }

    public PushbackSquare(FieldCoordinate pCoordinate, Direction pDirection, boolean pHomeChoice) {
        if (pCoordinate == null) {
            throw new IllegalArgumentException("Parameter coordinate must not be null.");
        }
        this.fCoordinate = pCoordinate;
        this.fDirection = pDirection;
        this.fHomeChoice = pHomeChoice;
        this.fLocked = false;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public Direction getDirection() {
        return this.fDirection;
    }

    public boolean isSelected() {
        return this.fSelected;
    }

    public void setSelected(boolean pSelected) {
        this.fSelected = pSelected;
    }

    public void setLocked(boolean pLocked) {
        this.fLocked = pLocked;
    }

    public boolean isLocked() {
        return this.fLocked;
    }

    public void setHomeChoice(boolean pHomeChoice) {
        this.fHomeChoice = pHomeChoice;
    }

    public boolean isHomeChoice() {
        return this.fHomeChoice;
    }

    public PushbackSquare transform() {
        FieldCoordinate transformedCoordinate = this.getCoordinate().transform();
        Direction transformedDirection = new DirectionFactory().transform(this.getDirection());
        PushbackSquare transformedPushback = new PushbackSquare(transformedCoordinate, transformedDirection, !this.isHomeChoice());
        transformedPushback.setSelected(this.isSelected());
        transformedPushback.setLocked(this.isLocked());
        return transformedPushback;
    }

    public static PushbackSquare transform(PushbackSquare pPushbackSquare) {
        return pPushbackSquare != null ? pPushbackSquare.transform() : null;
    }

    public int hashCode() {
        return this.getCoordinate().hashCode();
    }

    public boolean equals(Object pObj) {
        return pObj instanceof PushbackSquare && this.getCoordinate().equals(((PushbackSquare)pObj).getCoordinate());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        IJsonOption.DIRECTION.addTo(jsonObject, this.fDirection);
        IJsonOption.SELECTED.addTo(jsonObject, this.fSelected);
        IJsonOption.LOCKED.addTo(jsonObject, this.fLocked);
        IJsonOption.HOME_CHOICE.addTo(jsonObject, this.fHomeChoice);
        return jsonObject;
    }

    @Override
    public PushbackSquare initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        this.fDirection = (Direction)IJsonOption.DIRECTION.getFrom(jsonObject);
        this.fSelected = IJsonOption.SELECTED.getFrom(jsonObject);
        this.fLocked = IJsonOption.LOCKED.getFrom(jsonObject);
        this.fHomeChoice = IJsonOption.HOME_CHOICE.getFrom(jsonObject);
        return this;
    }
}

