/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.INamedObjectFactory;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogIdFactory
implements INamedObjectFactory {
    @Override
    public DialogId forName(String pName) {
        for (DialogId dialogId : DialogId.values()) {
            if (!dialogId.getName().equalsIgnoreCase(pName)) continue;
            return dialogId;
        }
        return null;
    }
}

