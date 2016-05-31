package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonArray;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class JsonArrayOption extends JsonAbstractOption {
    JsonArrayOption(String pKey) {
        super(pKey);
    }

    public JsonArray getFrom(JsonObject pJsonObject) {
        JsonValue jsonValue = this.getValueFrom(pJsonObject);
        if (jsonValue == null || jsonValue.isNull()) {
            return null;
        }
        return jsonValue.asArray();
    }

    public void addTo(JsonObject pJsonObject, JsonArray pValue) {
        this.addValueTo(pJsonObject, pValue);
    }
}

