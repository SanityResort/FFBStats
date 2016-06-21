package refactored.com.balancedbytes.games.ffb.model;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.IJsonReadable;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class Player implements IJsonReadable {

    private String fId;
    public String getId() {
        return this.fId;
    }
    private int fArmour;

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

    public int getArmour() {
        return this.fArmour;
    }

    @Override
    public Player initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fArmour = IJsonOption.ARMOUR.getFrom(jsonObject);
        return this;
    }
}

