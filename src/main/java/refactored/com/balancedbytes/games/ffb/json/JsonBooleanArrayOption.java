package refactored.com.balancedbytes.games.ffb.json;

import repackaged.com.eclipsesource.json.JsonArray;
import repackaged.com.eclipsesource.json.JsonObject;

import java.util.Collection;

public class JsonBooleanArrayOption extends JsonAbstractOption {
    JsonBooleanArrayOption(String pKey) {
        super(pKey);
    }

    public boolean[] getFrom(JsonObject pJsonObject) {
        return this.toBooleanArray(this.getValueFrom(pJsonObject).asArray());
    }

    private boolean[] toBooleanArray(JsonArray pJsonArray) {
        if (pJsonArray == null) {
            return null;
        }
        boolean[] booleanArray = new boolean[pJsonArray.size()];
        for (int i = 0; i < booleanArray.length; ++i) {
            booleanArray[i] = pJsonArray.get(i).asBoolean();
        }
        return booleanArray;
    }

    private JsonArray toJsonArray(boolean[] pBooleanArray) {
        if (pBooleanArray == null) {
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        for (boolean aPBooleanArray : pBooleanArray) {
            jsonArray.add(aPBooleanArray);
        }
        return jsonArray;
    }

    public void addTo(JsonObject pJsonObject, boolean[] pValues) {
        this.addValueTo(pJsonObject, this.toJsonArray(pValues));
    }

    public void addTo(JsonObject pJsonObject, Collection<Boolean> pValues) {
        boolean[] booleanArray = null;
        if (pValues != null) {
            Boolean[] booleanObjectArray = pValues.toArray(new Boolean[pValues.size()]);
            booleanArray = new boolean[booleanObjectArray.length];
            for (int i = 0; i < booleanArray.length; ++i) {
                booleanArray[i] = booleanObjectArray[i];
            }
        }
        this.addTo(pJsonObject, booleanArray);
    }
}

