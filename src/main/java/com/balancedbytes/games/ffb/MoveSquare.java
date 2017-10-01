/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public final class MoveSquare
implements IJsonSerializable {
    private FieldCoordinate fCoordinate;
    private int fMinimumRollDodge;
    private int fMinimumRollGoForIt;

    public MoveSquare() {
    }

    public MoveSquare(FieldCoordinate pCoordinate, int pMinimumRollDodge, int pMinimumRollGoForIt) {
        if (pCoordinate == null) {
            throw new IllegalArgumentException("Parameter coordinate must not be null.");
        }
        this.fCoordinate = pCoordinate;
        this.fMinimumRollDodge = pMinimumRollDodge;
        this.fMinimumRollGoForIt = pMinimumRollGoForIt;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public int getMinimumRollDodge() {
        return this.fMinimumRollDodge;
    }

    public boolean isDodging() {
        return this.getMinimumRollDodge() > 0;
    }

    public int getMinimumRollGoForIt() {
        return this.fMinimumRollGoForIt;
    }

    public boolean isGoingForIt() {
        return this.getMinimumRollGoForIt() > 0;
    }

    public int hashCode() {
        return this.getCoordinate().hashCode();
    }

    public boolean equals(Object pObj) {
        return pObj instanceof MoveSquare && this.getCoordinate().equals(((MoveSquare)pObj).getCoordinate());
    }

    public MoveSquare transform() {
        return new MoveSquare(this.getCoordinate().transform(), this.getMinimumRollDodge(), this.getMinimumRollGoForIt());
    }

    public static MoveSquare transform(MoveSquare pMoveSquare) {
        return pMoveSquare != null ? pMoveSquare.transform() : null;
    }

    public static MoveSquare[] transform(MoveSquare[] pMoveSquares) {
        MoveSquare[] transformedMoveSquares = new MoveSquare[]{};
        if (ArrayTool.isProvided(pMoveSquares)) {
            transformedMoveSquares = new MoveSquare[pMoveSquares.length];
            for (int i = 0; i < transformedMoveSquares.length; ++i) {
                transformedMoveSquares[i] = MoveSquare.transform(pMoveSquares[i]);
            }
        }
        return transformedMoveSquares;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        IJsonOption.MINIMUM_ROLL_DODGE.addTo(jsonObject, this.fMinimumRollDodge);
        IJsonOption.MINIMUM_ROLL_GFI.addTo(jsonObject, this.fMinimumRollGoForIt);
        return jsonObject;
    }

    @Override
    public MoveSquare initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        this.fMinimumRollDodge = IJsonOption.MINIMUM_ROLL_DODGE.getFrom(jsonObject);
        this.fMinimumRollGoForIt = IJsonOption.MINIMUM_ROLL_GFI.getFrom(jsonObject);
        return this;
    }
}

