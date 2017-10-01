/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.DirectionFactory;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportScatterPlayer
implements IReport {
    private FieldCoordinate fStartCoordinate;
    private FieldCoordinate fEndCoordinate;
    private List<Direction> fDirections = new ArrayList<Direction>();
    private List<Integer> fRolls = new ArrayList<Integer>();

    public ReportScatterPlayer() {
    }

    public ReportScatterPlayer(FieldCoordinate pStartCoordinate, FieldCoordinate pEndCoordinate, Direction[] pDirections, int[] pRolls) {
        this();
        this.fStartCoordinate = pStartCoordinate;
        this.fEndCoordinate = pEndCoordinate;
        this.addDirections(pDirections);
        this.addRolls(pRolls);
    }

    @Override
    public ReportId getId() {
        return ReportId.SCATTER_PLAYER;
    }

    public FieldCoordinate getStartCoordinate() {
        return this.fStartCoordinate;
    }

    public FieldCoordinate getEndCoordinate() {
        return this.fEndCoordinate;
    }

    public Direction[] getDirections() {
        return this.fDirections.toArray(new Direction[this.fDirections.size()]);
    }

    private void addDirection(Direction pDirection) {
        if (pDirection != null) {
            this.fDirections.add(pDirection);
        }
    }

    private void addDirections(Direction[] pDirections) {
        if (ArrayTool.isProvided(pDirections)) {
            for (Direction direction : pDirections) {
                this.addDirection(direction);
            }
        }
    }

    public int[] getRolls() {
        int[] rolls = new int[this.fDirections.size()];
        for (int i = 0; i < rolls.length; ++i) {
            rolls[i] = this.fRolls.get(i);
        }
        return rolls;
    }

    private void addRoll(int pRoll) {
        this.fRolls.add(pRoll);
    }

    private void addRolls(int[] pRolls) {
        if (ArrayTool.isProvided(pRolls)) {
            for (int roll : pRolls) {
                this.addRoll(roll);
            }
        }
    }

    @Override
    public IReport transform() {
        return new ReportScatterPlayer(FieldCoordinate.transform(this.getStartCoordinate()), FieldCoordinate.transform(this.getEndCoordinate()), new DirectionFactory().transform(this.getDirections()), this.getRolls());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.START_COORDINATE.addTo(jsonObject, this.fStartCoordinate);
        IJsonOption.END_COORDINATE.addTo(jsonObject, this.fEndCoordinate);
        JsonArray directionArray = new JsonArray();
        for (Direction direction : this.getDirections()) {
            directionArray.add(UtilJson.toJsonValue(direction));
        }
        IJsonOption.DIRECTION_ARRAY.addTo(jsonObject, directionArray);
        IJsonOption.ROLLS.addTo(jsonObject, this.fRolls);
        return jsonObject;
    }

    @Override
    public ReportScatterPlayer initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fStartCoordinate = IJsonOption.START_COORDINATE.getFrom(jsonObject);
        this.fEndCoordinate = IJsonOption.END_COORDINATE.getFrom(jsonObject);
        JsonArray directionArray = IJsonOption.DIRECTION_ARRAY.getFrom(jsonObject);
        if (directionArray != null) {
            for (int i = 0; i < directionArray.size(); ++i) {
                this.addDirection((Direction)UtilJson.toEnumWithName(new DirectionFactory(), directionArray.get(i)));
            }
        }
        this.addRolls(IJsonOption.ROLLS.getFrom(jsonObject));
        return this;
    }
}

