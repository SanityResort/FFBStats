/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.model.change;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.IJsonReadable;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonArray;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ModelChangeList implements IJsonReadable {
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

    public ModelChangeList transform() {
        ModelChangeList transformedList = new ModelChangeList(this.size());
        ModelChangeProcessor processor = new ModelChangeProcessor();
        for (ModelChange change : this.getChanges()) {
            transformedList.add(processor.transform(change));
        }
        return transformedList;
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

