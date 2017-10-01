/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ModelChange
implements IJsonSerializable {
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
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.MODEL_CHANGE_ID.addTo(jsonObject, this.fChangeId);
        IJsonOption.MODEL_CHANGE_KEY.addTo(jsonObject, this.fKey);
        IJsonOption.MODEL_CHANGE_VALUE.addTo(jsonObject, this.fChangeId.toJsonValue(this.fValue));
        return jsonObject;
    }

    @Override
    public ModelChange initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fChangeId = (ModelChangeId)IJsonOption.MODEL_CHANGE_ID.getFrom(jsonObject);
        this.fKey = IJsonOption.MODEL_CHANGE_KEY.getFrom(jsonObject);
        this.fValue = this.fChangeId.fromJsonValue(IJsonOption.MODEL_CHANGE_VALUE.getFrom(jsonObject));
        return this;
    }
}

