/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.model.change;


import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.IJsonReadable;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ModelChange implements IJsonReadable {
    public static final String HOME = "home";
    public static final String AWAY = "away";
    private ModelChangeId fChangeId;
    private String fKey;
    private Object fValue;

    public ModelChange() {
    }

    public ModelChange(ModelChangeId pChangeId, String pKey, Object pValue) {
        this.setChangeId(pChangeId);
        this.setKey(pKey);
        this.setValue(pValue);
    }

    public ModelChangeId getChangeId() {
        return this.fChangeId;
    }

    public void setChangeId(ModelChangeId pChangeId) {
        this.fChangeId = pChangeId;
    }

    public String getKey() {
        return this.fKey;
    }

    public void setKey(String pKey) {
        this.fKey = pKey;
    }

    public Object getValue() {
        return this.fValue;
    }

    public void setValue(Object pValue) {
        this.fValue = pValue;
    }

    @Override
    public ModelChange initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fChangeId = (ModelChangeId) IJsonOption.MODEL_CHANGE_ID.getFrom(jsonObject);
        this.fKey = IJsonOption.MODEL_CHANGE_KEY.getFrom(jsonObject);
        this.fValue = this.fChangeId.fromJsonValue(IJsonOption.MODEL_CHANGE_VALUE.getFrom(jsonObject));
        return this;
    }
}

