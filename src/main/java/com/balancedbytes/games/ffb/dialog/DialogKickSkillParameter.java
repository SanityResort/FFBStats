/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogKickSkillParameter
implements IDialogParameter {
    private String fPlayerId;
    private FieldCoordinate fBallCoordinate;
    private FieldCoordinate fBallCoordinateWithKick;

    public DialogKickSkillParameter() {
    }

    public DialogKickSkillParameter(String pPlayerId, FieldCoordinate pBallCoordinate, FieldCoordinate pBallCoordinateWithKick) {
        this.fPlayerId = pPlayerId;
        this.fBallCoordinate = pBallCoordinate;
        this.fBallCoordinateWithKick = pBallCoordinateWithKick;
    }

    @Override
    public DialogId getId() {
        return DialogId.KICK_SKILL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public FieldCoordinate getBallCoordinate() {
        return this.fBallCoordinate;
    }

    public FieldCoordinate getBallCoordinateWithKick() {
        return this.fBallCoordinateWithKick;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogKickSkillParameter(this.getPlayerId(), FieldCoordinate.transform(this.getBallCoordinate()), FieldCoordinate.transform(this.getBallCoordinateWithKick()));
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.BALL_COORDINATE.addTo(jsonObject, this.fBallCoordinate);
        IJsonOption.BALL_COORDINATE_WITH_KICK.addTo(jsonObject, this.fBallCoordinateWithKick);
        return jsonObject;
    }

    @Override
    public DialogKickSkillParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fBallCoordinate = IJsonOption.BALL_COORDINATE.getFrom(jsonObject);
        this.fBallCoordinateWithKick = IJsonOption.BALL_COORDINATE_WITH_KICK.getFrom(jsonObject);
        return this;
    }
}

