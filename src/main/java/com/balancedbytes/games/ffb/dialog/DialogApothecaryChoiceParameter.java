/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
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

public class DialogApothecaryChoiceParameter
implements IDialogParameter {
    private String fPlayerId;
    private PlayerState fPlayerStateOld;
    private SeriousInjury fSeriousInjuryOld;
    private PlayerState fPlayerStateNew;
    private SeriousInjury fSeriousInjuryNew;

    public DialogApothecaryChoiceParameter() {
    }

    public DialogApothecaryChoiceParameter(String pPlayerId, PlayerState pPlayerStateOld, SeriousInjury pSeriousInjuryOld, PlayerState pPlayerStateNew, SeriousInjury pSeriousInjuryNew) {
        this.fPlayerId = pPlayerId;
        this.fPlayerStateOld = pPlayerStateOld;
        this.fSeriousInjuryOld = pSeriousInjuryOld;
        this.fPlayerStateNew = pPlayerStateNew;
        this.fSeriousInjuryNew = pSeriousInjuryNew;
    }

    @Override
    public DialogId getId() {
        return DialogId.APOTHECARY_CHOICE;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public PlayerState getPlayerStateOld() {
        return this.fPlayerStateOld;
    }

    public SeriousInjury getSeriousInjuryOld() {
        return this.fSeriousInjuryOld;
    }

    public PlayerState getPlayerStateNew() {
        return this.fPlayerStateNew;
    }

    public SeriousInjury getSeriousInjuryNew() {
        return this.fSeriousInjuryNew;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogApothecaryChoiceParameter(this.getPlayerId(), this.getPlayerStateOld(), this.getSeriousInjuryOld(), this.getPlayerStateNew(), this.getSeriousInjuryNew());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.PLAYER_STATE_OLD.addTo(jsonObject, this.fPlayerStateOld);
        IJsonOption.SERIOUS_INJURY_OLD.addTo(jsonObject, this.fSeriousInjuryOld);
        IJsonOption.PLAYER_STATE_NEW.addTo(jsonObject, this.fPlayerStateNew);
        IJsonOption.SERIOUS_INJURY_NEW.addTo(jsonObject, this.fSeriousInjuryNew);
        return jsonObject;
    }

    @Override
    public DialogApothecaryChoiceParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fPlayerStateOld = IJsonOption.PLAYER_STATE_OLD.getFrom(jsonObject);
        this.fSeriousInjuryOld = (SeriousInjury)IJsonOption.SERIOUS_INJURY_OLD.getFrom(jsonObject);
        this.fPlayerStateNew = IJsonOption.PLAYER_STATE_NEW.getFrom(jsonObject);
        this.fSeriousInjuryNew = (SeriousInjury)IJsonOption.SERIOUS_INJURY_NEW.getFrom(jsonObject);
        return this;
    }
}

