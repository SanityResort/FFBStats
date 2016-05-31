package refactored.com.balancedbytes.games.ffb.json;

import refactored.com.balancedbytes.games.ffb.PlayerState;
import repackaged.com.eclipsesource.json.JsonObject;

public class JsonPlayerStateOption extends JsonAbstractOption {
    JsonPlayerStateOption(String pKey) {
        super(pKey);
    }

    public PlayerState getFrom(JsonObject pJsonObject) {
        return UtilJson.toPlayerState(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, PlayerState pValue) {
        this.addValueTo(pJsonObject, UtilJson.toJsonValue(pValue));
    }
}

