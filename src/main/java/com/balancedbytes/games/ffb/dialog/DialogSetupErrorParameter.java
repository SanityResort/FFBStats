/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DialogSetupErrorParameter
implements IDialogParameter {
    private String fTeamId;
    private List<String> fSetupErrors = new ArrayList<String>();

    public DialogSetupErrorParameter() {
    }

    public DialogSetupErrorParameter(String pTeamId, String[] pSetupErrors) {
        this();
        this.fTeamId = pTeamId;
        this.add(pSetupErrors);
    }

    @Override
    public DialogId getId() {
        return DialogId.SETUP_ERROR;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public String[] getSetupErrors() {
        return this.fSetupErrors.toArray(new String[this.fSetupErrors.size()]);
    }

    private void add(String pSetupError) {
        if (StringTool.isProvided(pSetupError)) {
            this.fSetupErrors.add(pSetupError);
        }
    }

    private void add(String[] pSetupErrors) {
        if (ArrayTool.isProvided(pSetupErrors)) {
            for (String setupError : pSetupErrors) {
                this.add(setupError);
            }
        }
    }

    @Override
    public IDialogParameter transform() {
        return new DialogSetupErrorParameter(this.getTeamId(), this.getSetupErrors());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.SETUP_ERRORS.addTo(jsonObject, this.fSetupErrors);
        return jsonObject;
    }

    @Override
    public DialogSetupErrorParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.add(IJsonOption.SETUP_ERRORS.getFrom(jsonObject));
        return this;
    }
}

