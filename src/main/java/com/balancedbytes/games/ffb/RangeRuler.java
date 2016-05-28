/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonFieldCoordinateOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class RangeRuler
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "rangeRuler";
    private static final String _XML_ATTRIBUTE_THROWER_ID = "throwerId";
    private static final String _XML_ATTRIBUTE_MINIMUM_ROLL = "minimumRoll";
    private static final String _XML_ATTRIBUTE_THROW_TEAM_MATE = "throwTeamMate";
    private static final String _XML_TAG_TARGET_COORDINATE = "targetCoordinate";
    private static final String _XML_ATTRIBUTE_X = "x";
    private static final String _XML_ATTRIBUTE_Y = "y";
    private String fThrowerId;
    private FieldCoordinate fTargetCoordinate;
    private int fMinimumRoll;
    private boolean fThrowTeamMate;

    public RangeRuler() {
    }

    public RangeRuler(String pThrowerId, FieldCoordinate pTargetCoordinate, int pMinimumRoll, boolean pThrowTeamMate) {
        this.fThrowerId = pThrowerId;
        this.fTargetCoordinate = pTargetCoordinate;
        this.fMinimumRoll = pMinimumRoll;
        this.fThrowTeamMate = pThrowTeamMate;
    }

    public String getThrowerId() {
        return this.fThrowerId;
    }

    public FieldCoordinate getTargetCoordinate() {
        return this.fTargetCoordinate;
    }

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    public boolean isThrowTeamMate() {
        return this.fThrowTeamMate;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        RangeRuler other = (RangeRuler)obj;
        if (this.fTargetCoordinate == null ? other.fTargetCoordinate != null : !this.fTargetCoordinate.equals(other.fTargetCoordinate)) {
            return false;
        }
        if (this.fMinimumRoll != other.fMinimumRoll) {
            return false;
        }
        if (this.fThrowTeamMate != other.fThrowTeamMate) {
            return false;
        }
        if (this.fThrowerId == null ? other.fThrowerId != null : !this.fThrowerId.equals(other.fThrowerId)) {
            return false;
        }
        return true;
    }

    public RangeRuler transform() {
        return new RangeRuler(this.getThrowerId(), FieldCoordinate.transform(this.getTargetCoordinate()), this.getMinimumRoll(), this.isThrowTeamMate());
    }

    public static RangeRuler transform(RangeRuler pTrackNumber) {
        return pTrackNumber != null ? pTrackNumber.transform() : null;
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes = new AttributesImpl();
        UtilXml.addAttribute(attributes, "throwerId", this.getThrowerId());
        UtilXml.addAttribute(attributes, "minimumRoll", this.getMinimumRoll());
        UtilXml.addAttribute(attributes, "throwTeamMate", this.isThrowTeamMate());
        UtilXml.startElement(pHandler, "rangeRuler", attributes);
        if (this.getTargetCoordinate() != null) {
            attributes = new AttributesImpl();
            UtilXml.addAttribute(attributes, "x", this.getTargetCoordinate().getX());
            UtilXml.addAttribute(attributes, "y", this.getTargetCoordinate().getY());
            UtilXml.startElement(pHandler, "targetCoordinate", attributes);
            UtilXml.endElement(pHandler, "targetCoordinate");
        }
        UtilXml.endElement(pHandler, "rangeRuler");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        RangeRuler xmlElement = this;
        if ("rangeRuler".equals(pXmlTag)) {
            this.fThrowerId = UtilXml.getStringAttribute(pXmlAttributes, "throwerId");
            this.fMinimumRoll = UtilXml.getIntAttribute(pXmlAttributes, "minimumRoll");
            this.fThrowTeamMate = UtilXml.getBooleanAttribute(pXmlAttributes, "throwTeamMate");
        }
        if ("targetCoordinate".equals(pXmlTag)) {
            int x = UtilXml.getIntAttribute(pXmlAttributes, "x");
            int y = UtilXml.getIntAttribute(pXmlAttributes, "y");
            this.fTargetCoordinate = new FieldCoordinate(x, y);
        }
        return xmlElement;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "rangeRuler".equals(pXmlTag);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.THROWER_ID.addTo(jsonObject, this.fThrowerId);
        IJsonOption.TARGET_COORDINATE.addTo(jsonObject, this.fTargetCoordinate);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        IJsonOption.THROW_TEAM_MATE.addTo(jsonObject, this.fThrowTeamMate);
        return jsonObject;
    }

    @Override
    public RangeRuler initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fThrowerId = IJsonOption.THROWER_ID.getFrom(jsonObject);
        this.fTargetCoordinate = IJsonOption.TARGET_COORDINATE.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fThrowTeamMate = IJsonOption.THROW_TEAM_MATE.getFrom(jsonObject);
        return this;
    }
}

