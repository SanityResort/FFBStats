/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.util;

import com.balancedbytes.games.ffb.client.FieldComponent;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.UserInterface;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public final class UtilClientCursor {
    public static void setCustomCursor(UserInterface pUserInterface, String pCursorIconProperty) {
        BufferedImage customCursorIcon = pUserInterface.getIconCache().getIconByProperty(pCursorIconProperty);
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(customCursorIcon, new Point(0, 0), "CustomCursor");
        pUserInterface.getFieldComponent().setCursor(customCursor);
    }

    public static void setDefaultCursor(UserInterface pUserInterface) {
        pUserInterface.getFieldComponent().setCursor(new Cursor(0));
    }
}

