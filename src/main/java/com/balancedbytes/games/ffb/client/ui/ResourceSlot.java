/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import java.awt.Rectangle;

public class ResourceSlot {
    public static final int TYPE_NONE = 0;
    public static final int TYPE_RE_ROLL = 1;
    public static final int TYPE_APOTHECARY = 2;
    public static final int TYPE_BRIBE = 3;
    public static final int TYPE_BLOODWEISER_BABE = 4;
    public static final int TYPE_MASTER_CHEF = 5;
    public static final int TYPE_IGOR = 6;
    public static final int TYPE_WIZARD = 7;
    public static final int TYPE_CARD = 8;
    private int fType;
    private Rectangle fLocation;
    private int fValue;
    private boolean fEnabled;
    private String fIconProperty;

    public ResourceSlot(Rectangle pLocation) {
        this.fLocation = pLocation;
        this.fEnabled = true;
    }

    public int getType() {
        return this.fType;
    }

    public void setType(int pType) {
        this.fType = pType;
    }

    public Rectangle getLocation() {
        return this.fLocation;
    }

    public int getValue() {
        return this.fValue;
    }

    public void setValue(int pValue) {
        this.fValue = pValue;
    }

    public void setIconProperty(String pIconProperty) {
        this.fIconProperty = pIconProperty;
    }

    public String getIconProperty() {
        return this.fIconProperty;
    }

    public String getToolTip() {
        if (this.getType() > 0) {
            StringBuilder toolTip = new StringBuilder();
            if (this.getValue() > 0) {
                toolTip.append(this.getValue()).append(" ");
            } else {
                toolTip.append("No ");
            }
            switch (this.getType()) {
                case 1: {
                    toolTip.append(this.getValue() == 1 ? "Re-Roll" : "Re-Rolls");
                    break;
                }
                case 2: {
                    toolTip.append(this.getValue() == 1 ? "Apothecary" : "Apothecaries");
                    break;
                }
                case 3: {
                    toolTip.append(this.getValue() == 1 ? "Bribe" : "Bribes");
                    break;
                }
                case 4: {
                    toolTip.append(this.getValue() == 1 ? "Bloodweiser Babe" : "Bloodweiser Babes");
                    break;
                }
                case 5: {
                    toolTip.append(this.getValue() == 1 ? "Master Chef" : "Master Chefs");
                    break;
                }
                case 6: {
                    toolTip.append(this.getValue() == 1 ? "Igor" : "Igors");
                    break;
                }
                case 7: {
                    toolTip.append(this.getValue() == 1 ? "Wizard" : "Wizards");
                    break;
                }
                case 8: {
                    toolTip.append(this.getValue() == 1 ? "Card" : "Cards");
                }
            }
            return toolTip.toString();
        }
        return null;
    }

    public void setEnabled(boolean pEnabled) {
        this.fEnabled = pEnabled;
    }

    public boolean isEnabled() {
        return this.fEnabled;
    }
}

