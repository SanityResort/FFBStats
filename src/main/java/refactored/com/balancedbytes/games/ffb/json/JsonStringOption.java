package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class JsonStringOption extends JsonAbstractOption {
    JsonStringOption(String pKey) {
        super(pKey);
    }

    public String getFrom(JsonObject pJsonObject) {
        return this.asString(this.getValueFrom(pJsonObject));
    }

    public void addTo(JsonObject pJsonObject, String pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }

    private String asString(JsonValue pJsonValue) {
        if (pJsonValue == null || pJsonValue.isNull()) {
            return null;
        }
        return pJsonValue.asString();
    }
}

