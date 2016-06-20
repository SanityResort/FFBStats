/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.model.change;


import refactored.com.balancedbytes.games.ffb.IEnumWithIdFactory;
import refactored.com.balancedbytes.games.ffb.IEnumWithNameFactory;

public class ModelChangeDataTypeFactory implements IEnumWithIdFactory, IEnumWithNameFactory {
    @Override
    public ModelChangeDataType forName(String pName) {
        for (ModelChangeDataType type : ModelChangeDataType.values()) {
            if (!type.getName().equalsIgnoreCase(pName)) continue;
            return type;
        }
        return null;
    }

    @Override
    public ModelChangeDataType forId(int pId) {
        for (ModelChangeDataType type : ModelChangeDataType.values()) {
            if (type.getId() != pId) continue;
            return type;
        }
        return null;
    }
}

