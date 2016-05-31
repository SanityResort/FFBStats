package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class JsonLongOption extends JsonAbstractOption {
    JsonLongOption(String pKey) {
        super(pKey);
    }

    public long getFrom(JsonObject pJsonObject) {
        return this.getValueFrom(pJsonObject).asLong();
    }

    public void addTo(JsonObject pJsonObject, long pValue) {
        this.addValueTo(pJsonObject, JsonValue.valueOf(pValue));
    }
}

