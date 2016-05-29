/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;

public abstract class GameOptionAbstract
implements IGameOption {
    private GameOptionId fId;

    public GameOptionAbstract(GameOptionId pId) {
        this.fId = pId;
    }

    @Override
    public GameOptionId getId() {
        return this.fId;
    }

    protected void setId(GameOptionId pId) {
        this.fId = pId;
    }

    @Override
    public boolean isChanged() {
        return !StringTool.print(this.getDefaultAsString()).equals(this.getValueAsString());
    }

    protected abstract String getDefaultAsString();

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.GAME_OPTION_ID.addTo(jsonObject, this.getId());
        IJsonOption.GAME_OPTION_VALUE.addTo(jsonObject, this.getValueAsString());
        return jsonObject;
    }
}

