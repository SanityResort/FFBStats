/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.dialog.DialogId;
import java.awt.Dimension;
import java.awt.Point;

public interface IDialog {
    public DialogId getId();

    public void showDialog(IDialogCloseListener var1);

    public void hideDialog();

    public void setLocation(Point var1);

    public Dimension getSize();

    public boolean isVisible();
}

