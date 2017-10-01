/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogWithoutParameter;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DialogWizardSpellParameter
extends DialogWithoutParameter {
    @Override
    public DialogId getId() {
        return DialogId.WIZARD_SPELL;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogWizardSpellParameter();
    }

    @Override
    public DialogWizardSpellParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        return this;
    }
}

