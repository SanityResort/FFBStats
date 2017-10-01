/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.AnimationType;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Animation
implements IJsonSerializable {
    private AnimationType fAnimationType;
    private String fThrownPlayerId;
    private boolean fWithBall;
    private FieldCoordinate fStartCoordinate;
    private FieldCoordinate fEndCoordinate;
    private FieldCoordinate fInterceptorCoordinate;
    private Card fCard;

    public Animation() {
    }

    public Animation(AnimationType pAnimationType) {
        this(pAnimationType, null, null, null, null, false, null);
    }

    public Animation(Card pCard) {
        this(AnimationType.CARD, pCard, null, null, null, false, null);
    }

    public Animation(AnimationType pAnimationType, FieldCoordinate pCoordinate) {
        this(pAnimationType, null, pCoordinate, null, null, false, null);
    }

    public Animation(FieldCoordinate pStartCoordinate, FieldCoordinate pEndCoordinate, String pThrownPlayerId, boolean pWithBall) {
        this(AnimationType.THROW_TEAM_MATE, null, pStartCoordinate, pEndCoordinate, pThrownPlayerId, pWithBall, null);
    }

    public Animation(AnimationType pAnimationType, FieldCoordinate pStartCoordinate, FieldCoordinate pEndCoordinate, FieldCoordinate pInterceptorCoordinate) {
        this(pAnimationType, null, pStartCoordinate, pEndCoordinate, null, false, pInterceptorCoordinate);
    }

    private Animation(AnimationType pAnimationType, Card pCard, FieldCoordinate pStartCoordinate, FieldCoordinate pEndCoordinate, String pThrownPlayerId, boolean pWithBall, FieldCoordinate pInterceptorCoordinate) {
        this.fAnimationType = pAnimationType;
        this.fCard = pCard;
        this.fThrownPlayerId = pThrownPlayerId;
        this.fWithBall = pWithBall;
        this.fStartCoordinate = pStartCoordinate;
        this.fEndCoordinate = pEndCoordinate;
        this.fInterceptorCoordinate = pInterceptorCoordinate;
    }

    public AnimationType getAnimationType() {
        return this.fAnimationType;
    }

    public String getThrownPlayerId() {
        return this.fThrownPlayerId;
    }

    public boolean isWithBall() {
        return this.fWithBall;
    }

    public FieldCoordinate getStartCoordinate() {
        return this.fStartCoordinate;
    }

    public FieldCoordinate getEndCoordinate() {
        return this.fEndCoordinate;
    }

    public FieldCoordinate getInterceptorCoordinate() {
        return this.fInterceptorCoordinate;
    }

    public Card getCard() {
        return this.fCard;
    }

    public Animation transform() {
        return new Animation(this.getAnimationType(), this.getCard(), FieldCoordinate.transform(this.getStartCoordinate()), FieldCoordinate.transform(this.getEndCoordinate()), this.getThrownPlayerId(), this.isWithBall(), FieldCoordinate.transform(this.getInterceptorCoordinate()));
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.THROWN_PLAYER_ID.addTo(jsonObject, this.fThrownPlayerId);
        IJsonOption.WITH_BALL.addTo(jsonObject, this.fWithBall);
        IJsonOption.START_COORDINATE.addTo(jsonObject, this.fStartCoordinate);
        IJsonOption.END_COORDINATE.addTo(jsonObject, this.fEndCoordinate);
        IJsonOption.INTERCEPTOR_COORDINATE.addTo(jsonObject, this.fInterceptorCoordinate);
        IJsonOption.ANIMATION_TYPE.addTo(jsonObject, this.fAnimationType);
        IJsonOption.CARD.addTo(jsonObject, this.fCard);
        return jsonObject;
    }

    @Override
    public Animation initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fThrownPlayerId = IJsonOption.THROWN_PLAYER_ID.getFrom(jsonObject);
        this.fWithBall = IJsonOption.WITH_BALL.getFrom(jsonObject);
        this.fStartCoordinate = IJsonOption.START_COORDINATE.getFrom(jsonObject);
        this.fEndCoordinate = IJsonOption.END_COORDINATE.getFrom(jsonObject);
        this.fInterceptorCoordinate = IJsonOption.INTERCEPTOR_COORDINATE.getFrom(jsonObject);
        this.fAnimationType = (AnimationType)IJsonOption.ANIMATION_TYPE.getFrom(jsonObject);
        this.fCard = (Card)IJsonOption.CARD.getFrom(jsonObject);
        return this;
    }
}

