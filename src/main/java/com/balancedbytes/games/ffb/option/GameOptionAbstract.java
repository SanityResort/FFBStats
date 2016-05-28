/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.option;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.helpers.AttributesImpl;

public abstract class GameOptionAbstract
implements IGameOption {
    private GameOptionId fId;

    public GameOptionAbstract(GameOptionId pId) {
        this.fId = pId;
    }

    @Override
    public GameOptionId getId() {
        return this.fId;
    }

    protected void setId(GameOptionId pId) {
        this.fId = pId;
    }

    @Override
    public boolean isChanged() {
        return !StringTool.print(this.getDefaultAsString()).equals(this.getValueAsString());
    }

    protected abstract String getDefaultAsString();

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.GAME_OPTION_ID.addTo(jsonObject, this.getId());
        IJsonOption.GAME_OPTION_VALUE.addTo(jsonObject, this.getValueAsString());
        return jsonObject;
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "name", this.getId() != null ? this.getId().getName() : null);
        UtilXml.addAttribute(attributes, "value", this.getValueAsString());
        UtilXml.startElement(pHandler, "option", attributes);
        UtilXml.endElement(pHandler, "option");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }
}

