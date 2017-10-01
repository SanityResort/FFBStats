/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.List;

public class ReportPassRoll
extends ReportSkillRoll {
    private PassingDistance fPassingDistance;
    private boolean fFumble;
    private boolean fSafeThrowHold;
    private boolean fHailMaryPass;
    private boolean fBomb;

    public ReportPassRoll() {
        super(ReportId.PASS_ROLL);
    }

    public ReportPassRoll(String pPlayerId, boolean pFumble, int pRoll, boolean pReRolled, boolean pBomb) {
        this(pPlayerId, !pFumble, pRoll, 2, pReRolled, null, null, pFumble, false, pBomb);
        this.fHailMaryPass = true;
    }

    public ReportPassRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, PassModifier[] pRollModifiers, PassingDistance pPassingDistance, boolean pFumble, boolean pSafeThrowHold, boolean pBomb) {
        super(ReportId.PASS_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled, pRollModifiers);
        this.fFumble = pFumble;
        this.fPassingDistance = pPassingDistance;
        this.fFumble = pFumble;
        this.fSafeThrowHold = pSafeThrowHold;
        this.fBomb = pBomb;
        this.fHailMaryPass = false;
    }

    @Override
    public ReportId getId() {
        return ReportId.PASS_ROLL;
    }

    public PassModifier[] getRollModifiers() {
        return this.getRollModifierList().toArray(new PassModifier[this.getRollModifierList().size()]);
    }

    public PassingDistance getPassingDistance() {
        return this.fPassingDistance;
    }

    public boolean isFumble() {
        return this.fFumble;
    }

    public boolean isHeldBySafeThrow() {
        return this.fSafeThrowHold;
    }

    public boolean isHailMaryPass() {
        return this.fHailMaryPass;
    }

    public boolean isBomb() {
        return this.fBomb;
    }

    @Override
    public IReport transform() {
        if (this.isHailMaryPass()) {
            return new ReportPassRoll(this.getPlayerId(), this.isFumble(), this.getRoll(), this.isReRolled(), this.isBomb());
        }
        return new ReportPassRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getRollModifiers(), this.getPassingDistance(), this.isFumble(), this.isHeldBySafeThrow(), this.isBomb());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = UtilJson.toJsonObject(super.toJsonValue());
        IJsonOption.PASSING_DISTANCE.addTo(jsonObject, this.fPassingDistance);
        IJsonOption.FUMBLE.addTo(jsonObject, this.fFumble);
        IJsonOption.SAFE_THROW_HOLD.addTo(jsonObject, this.fSafeThrowHold);
        IJsonOption.HAIL_MARY_PASS.addTo(jsonObject, this.fHailMaryPass);
        IJsonOption.BOMB.addTo(jsonObject, this.fBomb);
        return jsonObject;
    }

    @Override
    public ReportPassRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fPassingDistance = (PassingDistance)IJsonOption.PASSING_DISTANCE.getFrom(jsonObject);
        this.fFumble = IJsonOption.FUMBLE.getFrom(jsonObject);
        this.fSafeThrowHold = IJsonOption.SAFE_THROW_HOLD.getFrom(jsonObject);
        this.fHailMaryPass = IJsonOption.HAIL_MARY_PASS.getFrom(jsonObject);
        this.fBomb = IJsonOption.BOMB.getFrom(jsonObject);
        return this;
    }
}

