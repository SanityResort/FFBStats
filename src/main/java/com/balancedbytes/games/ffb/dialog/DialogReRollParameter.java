/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogReRollParameter
implements IDialogParameter {
    private String fPlayerId;
    private ReRolledAction fReRolledAction;
    private int fMinimumRoll;
    private boolean fTeamReRollOption;
    private boolean fProReRollOption;
    private boolean fFumble;

    public DialogReRollParameter() {
    }

    public DialogReRollParameter(String pPlayerId, ReRolledAction pReRolledAction, int pMinimumRoll, boolean pTeamReRollOption, boolean pProReRollOption, boolean pFumble) {
        this.fPlayerId = pPlayerId;
        this.fReRolledAction = pReRolledAction;
        this.fMinimumRoll = pMinimumRoll;
        this.fTeamReRollOption = pTeamReRollOption;
        this.fProReRollOption = pProReRollOption;
        this.fFumble = pFumble;
    }

    @Override
    public DialogId getId() {
        return DialogId.RE_ROLL;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public ReRolledAction getReRolledAction() {
        return this.fReRolledAction;
    }

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    public boolean isTeamReRollOption() {
        return this.fTeamReRollOption;
    }

    public boolean isProReRollOption() {
        return this.fProReRollOption;
    }

    public boolean isFumble() {
        return this.fFumble;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogReRollParameter(this.getPlayerId(), this.getReRolledAction(), this.getMinimumRoll(), this.isTeamReRollOption(), this.isProReRollOption(), this.isFumble());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.RE_ROLLED_ACTION.addTo(jsonObject, this.fReRolledAction);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        IJsonOption.TEAM_RE_ROLL_OPTION.addTo(jsonObject, this.fTeamReRollOption);
        IJsonOption.PRO_RE_ROLL_OPTION.addTo(jsonObject, this.fProReRollOption);
        IJsonOption.FUMBLE.addTo(jsonObject, this.fFumble);
        return jsonObject;
    }

    @Override
    public DialogReRollParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fReRolledAction = (ReRolledAction)IJsonOption.RE_ROLLED_ACTION.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fTeamReRollOption = IJsonOption.TEAM_RE_ROLL_OPTION.getFrom(jsonObject);
        this.fProReRollOption = IJsonOption.PRO_RE_ROLL_OPTION.getFrom(jsonObject);
        this.fFumble = IJsonOption.FUMBLE.getFrom(jsonObject);
        return this;
    }
}

