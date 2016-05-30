package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonReadable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Player implements IJsonReadable {

    private String fId;
    public String getId() {
        return this.fId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Player other = (Player)obj;
        return this.getId().equals(other.getId());
    }

    @Override
    public Player initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        return this;
    }

}

