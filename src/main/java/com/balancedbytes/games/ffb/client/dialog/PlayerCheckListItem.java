/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.model.Player;
import javax.swing.Icon;

public final class PlayerCheckListItem {
    private Player fPlayer;
    private Icon fIcon;
    private String fText;
    private boolean fSelected = false;

    public PlayerCheckListItem(Player pPlayer, Icon pIcon, String pText) {
        this.fPlayer = pPlayer;
        this.fIcon = pIcon;
        this.fText = pText;
        this.setSelected(false);
    }

    public boolean isSelected() {
        return this.fSelected;
    }

    public void setSelected(boolean pSelected) {
        this.fSelected = pSelected;
    }

    public Player getPlayer() {
        return this.fPlayer;
    }

    public Icon getIcon() {
        return this.fIcon;
    }

    public String getText() {
        return this.fText;
    }
}

