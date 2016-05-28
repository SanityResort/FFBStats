/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.option.GameOptionAbstract;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.util.StringTool;

public class GameOptionInt
extends GameOptionAbstract {
    private int fDefault;
    private int fValue;
    private String fMessage;

    public GameOptionInt(GameOptionId pId) {
        super(pId);
    }

    protected int getDefault() {
        return this.fDefault;
    }

    @Override
    protected String getDefaultAsString() {
        return Integer.toString(this.getDefault());
    }

    public GameOptionInt setDefault(int pDefault) {
        this.fDefault = pDefault;
        return this.setValue(this.getDefault());
    }

    @Override
    public String getValueAsString() {
        return Integer.toString(this.getValue());
    }

    public int getValue() {
        return this.fValue;
    }

    @Override
    public GameOptionInt setValue(String pValue) {
        if (StringTool.isProvided(pValue)) {
            return this.setValue(Integer.parseInt(pValue));
        }
        return this.setValue(0);
    }

    public GameOptionInt setValue(int pValue) {
        this.fValue = pValue;
        return this;
    }

    public GameOptionInt setMessage(String pMessage) {
        this.fMessage = pMessage;
        return this;
    }

    @Override
    public String getDisplayMessage() {
        return StringTool.bind(this.fMessage, StringTool.formatThousands(this.getValue()));
    }
}

