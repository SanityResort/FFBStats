/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DiceDecoration
implements IJsonSerializable {
    private FieldCoordinate fCoordinate;
    private int fNrOfDice;

    public DiceDecoration() {
    }

    public DiceDecoration(FieldCoordinate pCoordinate, int pNrOfDice) {
        this.fCoordinate = pCoordinate;
        this.fNrOfDice = pNrOfDice;
    }

    public FieldCoordinate getCoordinate() {
        return this.fCoordinate;
    }

    public int getNrOfDice() {
        return this.fNrOfDice;
    }

    public int hashCode() {
        return this.getCoordinate().hashCode();
    }

    public boolean equals(Object pObj) {
        return pObj instanceof DiceDecoration && this.getCoordinate().equals(((DiceDecoration)pObj).getCoordinate());
    }

    public DiceDecoration transform() {
        return new DiceDecoration(this.getCoordinate().transform(), this.getNrOfDice());
    }

    public static DiceDecoration transform(DiceDecoration pDiceDecoration) {
        return pDiceDecoration != null ? pDiceDecoration.transform() : null;
    }

    public static DiceDecoration[] transform(DiceDecoration[] pDiceDecorations) {
        DiceDecoration[] transformedDiceDecorations = new DiceDecoration[]{};
        if (ArrayTool.isProvided(pDiceDecorations)) {
            transformedDiceDecorations = new DiceDecoration[pDiceDecorations.length];
            for (int i = 0; i < transformedDiceDecorations.length; ++i) {
                transformedDiceDecorations[i] = DiceDecoration.transform(pDiceDecorations[i]);
            }
        }
        return transformedDiceDecorations;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.COORDINATE.addTo(jsonObject, this.fCoordinate);
        IJsonOption.NR_OF_DICE.addTo(jsonObject, this.fNrOfDice);
        return jsonObject;
    }

    @Override
    public DiceDecoration initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fCoordinate = IJsonOption.COORDINATE.getFrom(jsonObject);
        this.fNrOfDice = IJsonOption.NR_OF_DICE.getFrom(jsonObject);
        return this;
    }
}

