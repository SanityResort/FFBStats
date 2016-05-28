/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.InducementTypeFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class Inducement
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "inducement";
    private static final String _XML_ATTRIBUTE_TYPE = "type";
    private static final String _XML_ATTRIBUTE_VALUE = "value";
    private static final String _XML_ATTRIBUTE_USES = "uses";
    private InducementType fType;
    private int fValue;
    private int fUses;

    public Inducement() {
    }

    public Inducement(InducementType pType, int pValue) {
        this.fType = pType;
        this.setValue(pValue);
    }

    public InducementType getType() {
        return this.fType;
    }

    public int getValue() {
        return this.fValue;
    }

    public void setValue(int pValue) {
        this.fValue = pValue;
    }

    public int getUses() {
        return this.fUses;
    }

    public void setUses(int pCurrent) {
        this.fUses = pCurrent;
    }

    public int getUsesLeft() {
        return Math.max(0, this.getValue() - this.getUses());
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        String typeName = this.fType != null ? this.fType.getName() : null;
        UtilXml.addAttribute(attributes, "type", typeName);
        UtilXml.addAttribute(attributes, "value", this.fValue);
        UtilXml.addAttribute(attributes, "uses", this.fUses);
        UtilXml.addEmptyElement(pHandler, "inducement", attributes);
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        InducementTypeFactory typeFactory = new InducementTypeFactory();
        if ("inducement".equals(pXmlTag)) {
            String typeName = UtilXml.getStringAttribute(pXmlAttributes, "type");
            this.fType = typeFactory.forName(typeName);
            this.fValue = UtilXml.getIntAttribute(pXmlAttributes, "value");
            this.fUses = UtilXml.getIntAttribute(pXmlAttributes, "uses");
        }
        return this;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "inducement".equals(pXmlTag);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.INDUCEMENT_TYPE.addTo(jsonObject, this.fType);
        IJsonOption.VALUE.addTo(jsonObject, this.fValue);
        IJsonOption.USES.addTo(jsonObject, this.fUses);
        return jsonObject;
    }

    @Override
    public Inducement initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fType = (InducementType)IJsonOption.INDUCEMENT_TYPE.getFrom(jsonObject);
        this.fValue = IJsonOption.VALUE.getFrom(jsonObject);
        this.fUses = IJsonOption.USES.getFrom(jsonObject);
        return this;
    }
}

