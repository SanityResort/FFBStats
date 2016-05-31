package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class JsonObjectOption extends JsonAbstractOption {
    JsonObjectOption(String pKey) {
        super(pKey);
    }

    public JsonObject getFrom(JsonObject pJsonObject) {
        JsonValue jsonValue = this.getValueFrom(pJsonObject);
        if (jsonValue == null || jsonValue.isNull()) {
            return null;
        }
        return jsonValue.asObject();
    }

    public void addTo(JsonObject pJsonObject, JsonObject pValue) {
        if (pValue == null) {
            this.addValueTo(pJsonObject, JsonValue.NULL);
        } else {
            this.addValueTo(pJsonObject, pValue);
        }
    }
}
