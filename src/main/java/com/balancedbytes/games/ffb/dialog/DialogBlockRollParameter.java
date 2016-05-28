/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntArrayOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogBlockRollParameter
implements IDialogParameter {
    private String fChoosingTeamId;
    private int fNrOfDice;
    private int[] fBlockRoll;
    private boolean fTeamReRollOption;
    private boolean fProReRollOption;

    public DialogBlockRollParameter() {
    }

    public DialogBlockRollParameter(String pChoosingTeamId, int pNrOfDice, int[] pBlockRoll, boolean pTeamReRollOption, boolean pProReRollOption) {
        this.fChoosingTeamId = pChoosingTeamId;
        this.fNrOfDice = pNrOfDice;
        this.fBlockRoll = pBlockRoll;
        this.fTeamReRollOption = pTeamReRollOption;
        this.fProReRollOption = pProReRollOption;
    }

    @Override
    public DialogId getId() {
        return DialogId.BLOCK_ROLL;
    }

    public String getChoosingTeamId() {
        return this.fChoosingTeamId;
    }

    public int getNrOfDice() {
        return this.fNrOfDice;
    }

    public int[] getBlockRoll() {
        return this.fBlockRoll;
    }

    public boolean hasTeamReRollOption() {
        return this.fTeamReRollOption;
    }

    public boolean hasProReRollOption() {
        return this.fProReRollOption;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogBlockRollParameter(this.getChoosingTeamId(), this.getNrOfDice(), this.getBlockRoll(), this.hasTeamReRollOption(), this.hasProReRollOption());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.CHOOSING_TEAM_ID.addTo(jsonObject, this.fChoosingTeamId);
        IJsonOption.NR_OF_DICE.addTo(jsonObject, this.fNrOfDice);
        IJsonOption.BLOCK_ROLL.addTo(jsonObject, this.fBlockRoll);
        IJsonOption.TEAM_RE_ROLL_OPTION.addTo(jsonObject, this.fTeamReRollOption);
        IJsonOption.PRO_RE_ROLL_OPTION.addTo(jsonObject, this.fProReRollOption);
        return jsonObject;
    }

    @Override
    public DialogBlockRollParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fChoosingTeamId = IJsonOption.CHOOSING_TEAM_ID.getFrom(jsonObject);
        this.fNrOfDice = IJsonOption.NR_OF_DICE.getFrom(jsonObject);
        this.fBlockRoll = IJsonOption.BLOCK_ROLL.getFrom(jsonObject);
        this.fTeamReRollOption = IJsonOption.TEAM_RE_ROLL_OPTION.getFrom(jsonObject);
        this.fProReRollOption = IJsonOption.PRO_RE_ROLL_OPTION.getFrom(jsonObject);
        return this;
    }
}

