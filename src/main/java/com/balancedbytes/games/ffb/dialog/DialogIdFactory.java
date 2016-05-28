/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithIdFactory;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.IEnumWithNameFactory;
import com.balancedbytes.games.ffb.dialog.DialogId;

public class DialogIdFactory
implements IEnumWithIdFactory,
IEnumWithNameFactory {
    @Override
    public DialogId forId(int pId) {
        for (DialogId dialogId : DialogId.values()) {
            if (dialogId.getId() != pId) continue;
            return dialogId;
        }
        return null;
    }

    @Override
    public DialogId forName(String pName) {
        for (DialogId dialogId : DialogId.values()) {
            if (!dialogId.getName().equalsIgnoreCase(pName)) continue;
            return dialogId;
        }
        return null;
    }
}

