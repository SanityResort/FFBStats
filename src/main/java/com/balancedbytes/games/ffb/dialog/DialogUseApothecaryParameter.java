/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonPlayerStateOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogUseApothecaryParameter
implements IDialogParameter {
    private String fPlayerId;
    private PlayerState fPlayerState;
    private SeriousInjury fSeriousInjury;

    public DialogUseApothecaryParameter() {
    }

    public DialogUseApothecaryParameter(String pPlayerId, PlayerState pPlayerState, SeriousInjury pSeriousInjury) {
        this.fPlayerId = pPlayerId;
        this.fPlayerState = pPlayerState;
        this.fSeriousInjury = pSeriousInjury;
    }

    @Override
    public DialogId getId() {
        return DialogId.USE_APOTHECARY;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public PlayerState getPlayerState() {
        return this.fPlayerState;
    }

    public SeriousInjury getSeriousInjury() {
        return this.fSeriousInjury;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogUseApothecaryParameter(this.getPlayerId(), this.getPlayerState(), this.getSeriousInjury());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.PLAYER_STATE.addTo(jsonObject, this.fPlayerState);
        IJsonOption.SERIOUS_INJURY.addTo(jsonObject, this.fSeriousInjury);
        return jsonObject;
    }

    @Override
    public DialogUseApothecaryParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fPlayerState = IJsonOption.PLAYER_STATE.getFrom(jsonObject);
        this.fSeriousInjury = (SeriousInjury)IJsonOption.SERIOUS_INJURY.getFrom(jsonObject);
        return this;
    }
}

