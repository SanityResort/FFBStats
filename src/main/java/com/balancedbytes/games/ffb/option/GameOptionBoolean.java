/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.option.GameOptionAbstract;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.util.StringTool;

public class GameOptionBoolean
extends GameOptionAbstract {
    private boolean fDefault;
    private boolean fValue;
    private String fMessageTrue;
    private String fMessageFalse;

    public GameOptionBoolean(GameOptionId pId) {
        super(pId);
    }

    protected boolean getDefault() {
        return this.fDefault;
    }

    @Override
    protected String getDefaultAsString() {
        return Boolean.toString(this.getDefault());
    }

    public GameOptionBoolean setDefault(boolean pDefault) {
        this.fDefault = pDefault;
        return this.setValue(this.getDefault());
    }

    @Override
    public String getValueAsString() {
        return Boolean.toString(this.isEnabled());
    }

    public boolean isEnabled() {
        return this.fValue;
    }

    @Override
    public GameOptionBoolean setValue(String pValue) {
        if (!StringTool.isProvided(pValue) || "0".equals(pValue)) {
            return this.setValue(false);
        }
        if ("1".equals(pValue)) {
            return this.setValue(true);
        }
        return this.setValue(Boolean.parseBoolean(pValue));
    }

    public GameOptionBoolean setValue(boolean pValue) {
        this.fValue = pValue;
        return this;
    }

    public GameOptionBoolean setMessageTrue(String pMessage) {
        this.fMessageTrue = pMessage;
        return this;
    }

    public GameOptionBoolean setMessageFalse(String pMessage) {
        this.fMessageFalse = pMessage;
        return this;
    }

    @Override
    public String getDisplayMessage() {
        return this.isEnabled() ? this.fMessageTrue : this.fMessageFalse;
    }
}

