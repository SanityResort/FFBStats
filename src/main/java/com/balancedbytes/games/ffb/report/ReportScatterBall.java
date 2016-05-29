/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.DirectionFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportScatterBall
implements IReport {
    private List<Direction> fDirections = new ArrayList<Direction>();
    private List<Integer> fRolls = new ArrayList<Integer>();
    private boolean fGustOfWind;

    public ReportScatterBall() {
    }

    public ReportScatterBall(Direction[] pDirections, int[] pRolls, boolean pGustOfWind) {
        this();
        this.addDirections(pDirections);
        this.addRolls(pRolls);
        this.fGustOfWind = pGustOfWind;
    }

    @Override
    public ReportId getId() {
        return ReportId.SCATTER_BALL;
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

    public boolean isGustOfWind() {
        return this.fGustOfWind;
    }

    @Override
    public IReport transform() {
        return new ReportScatterBall(new DirectionFactory().transform(this.getDirections()), this.getRolls(), this.isGustOfWind());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        JsonArray directionArray = new JsonArray();
        for (Direction direction : this.getDirections()) {
            directionArray.add(UtilJson.toJsonValue(direction));
        }
        IJsonOption.DIRECTION_ARRAY.addTo(jsonObject, directionArray);
        IJsonOption.ROLLS.addTo(jsonObject, this.fRolls);
        IJsonOption.GUST_OF_WIND.addTo(jsonObject, this.fGustOfWind);
        return jsonObject;
    }

    @Override
    public ReportScatterBall initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        JsonArray directionArray = IJsonOption.DIRECTION_ARRAY.getFrom(jsonObject);
        if (directionArray != null) {
            for (int i = 0; i < directionArray.size(); ++i) {
                this.addDirection((Direction)UtilJson.toEnumWithName(new DirectionFactory(), directionArray.get(i)));
            }
        }
        this.addRolls(IJsonOption.ROLLS.getFrom(jsonObject));
        this.fGustOfWind = IJsonOption.GUST_OF_WIND.getFrom(jsonObject);
        return this;
    }
}

