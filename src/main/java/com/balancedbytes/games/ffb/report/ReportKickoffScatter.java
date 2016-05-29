/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.DirectionFactory;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportKickoffScatter
implements IReport {
    private FieldCoordinate fBallCoordinateEnd;
    private Direction fScatterDirection;
    private int fRollScatterDirection;
    private int fRollScatterDistance;

    public ReportKickoffScatter() {
    }

    public ReportKickoffScatter(FieldCoordinate pBallCoordinateEnd, Direction pScatterDirection, int pRollScatterDirection, int pRollScatterDistance) {
        this.fBallCoordinateEnd = pBallCoordinateEnd;
        this.fScatterDirection = pScatterDirection;
        this.fRollScatterDirection = pRollScatterDirection;
        this.fRollScatterDistance = pRollScatterDistance;
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_SCATTER;
    }

    public FieldCoordinate getBallCoordinateEnd() {
        return this.fBallCoordinateEnd;
    }

    public Direction getScatterDirection() {
        return this.fScatterDirection;
    }

    public int getRollScatterDirection() {
        return this.fRollScatterDirection;
    }

    public int getRollScatterDistance() {
        return this.fRollScatterDistance;
    }

    @Override
    public IReport transform() {
        return new ReportKickoffScatter(FieldCoordinate.transform(this.getBallCoordinateEnd()), new DirectionFactory().transform(this.getScatterDirection()), this.getRollScatterDirection(), this.getRollScatterDistance());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.BALL_COORDINATE_END.addTo(jsonObject, this.fBallCoordinateEnd);
        IJsonOption.SCATTER_DIRECTION.addTo(jsonObject, this.fScatterDirection);
        IJsonOption.ROLL_SCATTER_DIRECTION.addTo(jsonObject, this.fRollScatterDirection);
        IJsonOption.ROLL_SCATTER_DISTANCE.addTo(jsonObject, this.fRollScatterDistance);
        return jsonObject;
    }

    @Override
    public ReportKickoffScatter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fBallCoordinateEnd = IJsonOption.BALL_COORDINATE_END.getFrom(jsonObject);
        this.fScatterDirection = (Direction)IJsonOption.SCATTER_DIRECTION.getFrom(jsonObject);
        this.fRollScatterDirection = IJsonOption.ROLL_SCATTER_DIRECTION.getFrom(jsonObject);
        this.fRollScatterDistance = IJsonOption.ROLL_SCATTER_DISTANCE.getFrom(jsonObject);
        return this;
    }
}

