/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DialogTeamSetupParameter
implements IDialogParameter {
    private boolean fLoadDialog;
    private List<String> fSetupNames = new ArrayList<String>();

    public DialogTeamSetupParameter() {
    }

    public DialogTeamSetupParameter(boolean pLoadDialog, String[] pSetupNames) {
        this();
        this.fLoadDialog = pLoadDialog;
        this.add(pSetupNames);
    }

    @Override
    public DialogId getId() {
        return DialogId.TEAM_SETUP;
    }

    public boolean isLoadDialog() {
        return this.fLoadDialog;
    }

    public String[] getSetupNames() {
        return this.fSetupNames.toArray(new String[this.fSetupNames.size()]);
    }

    private void add(String pSetupName) {
        if (StringTool.isProvided(pSetupName)) {
            this.fSetupNames.add(pSetupName);
        }
    }

    private void add(String[] pSetupNames) {
        if (ArrayTool.isProvided(pSetupNames)) {
            for (String setupError : pSetupNames) {
                this.add(setupError);
            }
        }
    }

    @Override
    public IDialogParameter transform() {
        return new DialogTeamSetupParameter(this.isLoadDialog(), this.getSetupNames());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.LOAD_DIALOG.addTo(jsonObject, this.fLoadDialog);
        IJsonOption.SETUP_NAMES.addTo(jsonObject, this.fSetupNames);
        return jsonObject;
    }

    @Override
    public DialogTeamSetupParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fLoadDialog = IJsonOption.LOAD_DIALOG.getFrom(jsonObject);
        this.add(IJsonOption.SETUP_NAMES.getFrom(jsonObject));
        return this;
    }
}

