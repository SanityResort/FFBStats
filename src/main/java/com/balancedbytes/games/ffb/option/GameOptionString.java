/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.option.GameOptionAbstract;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.util.StringTool;

public class GameOptionString
extends GameOptionAbstract {
    private String fDefault;
    private String fValue;
    private String fMessage;

    public GameOptionString(GameOptionId pId) {
        super(pId);
    }

    protected String getDefault() {
        return this.fDefault;
    }

    @Override
    protected String getDefaultAsString() {
        return this.getDefault();
    }

    public GameOptionString setDefault(String pDefault) {
        this.fDefault = pDefault;
        return this.setValue(this.getDefault());
    }

    @Override
    public String getValueAsString() {
        return this.getValue();
    }

    public String getValue() {
        return this.fValue;
    }

    @Override
    public GameOptionString setValue(String pValue) {
        this.fValue = pValue;
        return this;
    }

    public GameOptionString setMessage(String pMessage) {
        this.fMessage = pMessage;
        return this;
    }

    @Override
    public String getDisplayMessage() {
        return StringTool.bind(this.fMessage, this.getValueAsString());
    }
}

