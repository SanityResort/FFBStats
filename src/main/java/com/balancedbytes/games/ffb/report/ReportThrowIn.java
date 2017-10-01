/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.DirectionFactory;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportThrowIn
implements IReport {
    private Direction fDirection;
    private int fDirectionRoll;
    private int[] fDistanceRoll;

    public ReportThrowIn() {
    }

    public ReportThrowIn(Direction pDirection, int pDirectionRoll, int[] pDistanceRoll) {
        this.fDirection = pDirection;
        this.fDirectionRoll = pDirectionRoll;
        this.fDistanceRoll = pDistanceRoll;
    }

    @Override
    public ReportId getId() {
        return ReportId.THROW_IN;
    }

    public Direction getDirection() {
        return this.fDirection;
    }

    public int getDirectionRoll() {
        return this.fDirectionRoll;
    }

    public int[] getDistanceRoll() {
        return this.fDistanceRoll;
    }

    @Override
    public IReport transform() {
        return new ReportThrowIn(new DirectionFactory().transform(this.getDirection()), this.getDirectionRoll(), this.getDistanceRoll());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.DIRECTION.addTo(jsonObject, this.fDirection);
        IJsonOption.DIRECTION_ROLL.addTo(jsonObject, this.fDirectionRoll);
        IJsonOption.DISTANCE_ROLL.addTo(jsonObject, this.fDistanceRoll);
        return jsonObject;
    }

    @Override
    public ReportThrowIn initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDirection = (Direction)IJsonOption.DIRECTION.getFrom(jsonObject);
        this.fDirectionRoll = IJsonOption.DIRECTION_ROLL.getFrom(jsonObject);
        this.fDistanceRoll = IJsonOption.DISTANCE_ROLL.getFrom(jsonObject);
        return this;
    }
}

