/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public abstract class DialogWithoutParameter
implements IDialogParameter {
    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        return jsonObject;
    }
}

