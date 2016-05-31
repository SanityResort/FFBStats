package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class JsonIntOption extends JsonAbstractOption {
    JsonIntOption(String pKey) {
        super(pKey);
    }

    public int getFrom(JsonObject pJsonObject) {
        return this.getValueFrom(pJsonObject).asInt();
    }

    public void addTo(JsonObject pJsonObject, int pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }
}

