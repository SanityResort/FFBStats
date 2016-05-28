/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.model.change;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeProcessor;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class ModelChangeList
implements IJsonSerializable {
    private List<ModelChange> fChanges;

    public ModelChangeList() {
        this(16);
    }

    public ModelChangeList(int pInitialCapacity) {
        this.fChanges = new ArrayList<ModelChange>(pInitialCapacity);
    }

    public void add(ModelChange pChange) {
        this.fChanges.add(pChange);
    }

    public void add(ModelChangeList pChangeList) {
        if (pChangeList != null) {
            for (ModelChange change : pChangeList.getChanges()) {
                this.add(change);
            }
        }
    }

    public ModelChange[] getChanges() {
        return this.fChanges.toArray(new ModelChange[this.fChanges.size()]);
    }

    public void clear() {
        this.fChanges.clear();
    }

    public int size() {
        return this.fChanges.size();
    }

    public void applyTo(Game pGame) {
        ModelChangeProcessor processor = new ModelChangeProcessor();
        for (ModelChange change : this.getChanges()) {
            processor.apply(pGame, change);
        }
    }

    public ModelChangeList transform() {
        ModelChangeList transformedList = new ModelChangeList(this.size());
        ModelChangeProcessor processor = new ModelChangeProcessor();
        for (ModelChange change : this.getChanges()) {
            transformedList.add(processor.transform(change));
        }
        return transformedList;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        JsonArray modelChanges = new JsonArray();
        for (ModelChange change : this.fChanges) {
            modelChanges.add(change.toJsonValue());
        }
        IJsonOption.MODEL_CHANGE_ARRAY.addTo(jsonObject, modelChanges);
        return jsonObject;
    }

    @Override
    public ModelChangeList initFrom(JsonValue pJsonValue) {
        this.clear();
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        JsonArray modelChanges = IJsonOption.MODEL_CHANGE_ARRAY.getFrom(jsonObject);
        for (int i = 0; i < modelChanges.size(); ++i) {
            this.add(new ModelChange().initFrom(modelChanges.get(i)));
        }
        return this;
    }
}

