/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.xml;

import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilXml {
    private static final DateFormat _TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public static TransformerHandler createTransformerHandler(Writer pWriter, boolean pIndent) {
        try {
            SAXTransformerFactory factory = (SAXTransformerFactory)TransformerFactory.newInstance();
            TransformerHandler handler = factory.newTransformerHandler();
            Transformer serializer = handler.getTransformer();
            serializer.setOutputProperty("encoding", "UTF-8");
            serializer.setOutputProperty("method", "xml");
            serializer.setOutputProperty("indent", pIndent ? "yes" : "no");
            handler.setResult(new StreamResult(pWriter));
            return handler;
        }
        catch (TransformerConfigurationException pTce) {
            throw new FantasyFootballException(pTce);
        }
    }

    public static String toXml(IXmlWriteable pXmlWriteable, boolean pIndent) {
        try {
            StringWriter writer = new StringWriter();
            TransformerHandler handler = UtilXml.createTransformerHandler(writer, pIndent);
            handler.startDocument();
            pXmlWriteable.addToXml(handler);
            handler.endDocument();
            return writer.toString();
        }
        catch (SAXException pSaxE) {
            throw new FantasyFootballException(pSaxE);
        }
    }

    public static void startElement(TransformerHandler pHandler, String pElement, Attributes pXmlAttributes) {
        if (pHandler == null || !StringTool.isProvided(pElement)) {
            return;
        }
        try {
            pHandler.startElement("", "", pElement, pXmlAttributes);
        }
        catch (SAXException pSaxE) {
            throw new FantasyFootballException(pSaxE);
        }
    }

    public static void startElement(TransformerHandler pHandler, String pElement) {
        UtilXml.startElement(pHandler, pElement, new AttributesImpl());
    }

    public static void endElement(TransformerHandler pHandler, String pElement) {
        if (pHandler == null || !StringTool.isProvided(pElement)) {
            return;
        }
        try {
            pHandler.endElement("", "", pElement);
        }
        catch (SAXException pSaxE) {
            throw new FantasyFootballException(pSaxE);
        }
    }

    public static void addEmptyElement(TransformerHandler pHandler, String pElement, Attributes pXmlAttributes) {
        UtilXml.startElement(pHandler, pElement, pXmlAttributes);
        UtilXml.endElement(pHandler, pElement);
    }

    public static void addEmptyElement(TransformerHandler pHandler, String pElement) {
        UtilXml.startElement(pHandler, pElement);
        UtilXml.endElement(pHandler, pElement);
    }

    public static void addCharacters(TransformerHandler pHandler, String pValue) {
        if (pHandler == null || !StringTool.isProvided(pValue)) {
            return;
        }
        try {
            pHandler.characters(pValue.toCharArray(), 0, pValue.length());
        }
        catch (SAXException pSaxE) {
            throw new FantasyFootballException(pSaxE);
        }
    }

    public static void addCharacters(TransformerHandler pHandler, int pValue) {
        UtilXml.addCharacters(pHandler, Integer.toString(pValue));
    }

    public static void addCharacters(TransformerHandler pHandler, long pValue) {
        UtilXml.addCharacters(pHandler, Long.toString(pValue));
    }

    public static void addCharacters(TransformerHandler pHandler, boolean pValue) {
        UtilXml.addCharacters(pHandler, Boolean.toString(pValue));
    }

    public static void addValueElement(TransformerHandler pHandler, String pElement, String pValue) {
        UtilXml.startElement(pHandler, pElement);
        UtilXml.addCharacters(pHandler, pValue);
        UtilXml.endElement(pHandler, pElement);
    }

    public static void addValueElement(TransformerHandler pHandler, String pElement, int pValue) {
        UtilXml.startElement(pHandler, pElement);
        UtilXml.addCharacters(pHandler, pValue);
        UtilXml.endElement(pHandler, pElement);
    }

    public static void addValueElement(TransformerHandler pHandler, String pElement, long pValue) {
        UtilXml.startElement(pHandler, pElement);
        UtilXml.addCharacters(pHandler, pValue);
        UtilXml.endElement(pHandler, pElement);
    }

    public static void addValueElement(TransformerHandler pHandler, String pElement, boolean pValue) {
        UtilXml.startElement(pHandler, pElement);
        UtilXml.addCharacters(pHandler, pValue);
        UtilXml.endElement(pHandler, pElement);
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, String pValue) {
        if (pXmlAttributes == null || !StringTool.isProvided(pAttribute)) {
            return;
        }
        pXmlAttributes.addAttribute("", "", pAttribute, "CDATA", StringTool.print(pValue));
    }

    public static String getStringAttribute(Attributes pXmlAttributes, String pAttribute) {
        String value = pXmlAttributes.getValue(pAttribute);
        return value != null ? value.trim() : null;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, int pValue) {
        UtilXml.addAttribute(pXmlAttributes, pAttribute, Integer.toString(pValue));
    }

    public static int getIntAttribute(Attributes pXmlAttributes, String pAttribute) {
        String value = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, long pValue) {
        UtilXml.addAttribute(pXmlAttributes, pAttribute, Long.toString(pValue));
    }

    public static long getLongAttribute(Attributes pXmlAttributes, String pAttribute) {
        String value = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        return value != null ? Long.parseLong(value) : -1;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, boolean pValue) {
        UtilXml.addAttribute(pXmlAttributes, pAttribute, Boolean.toString(pValue));
    }

    public static boolean getBooleanAttribute(Attributes pXmlAttributes, String pAttribute) {
        String value = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        return value != null ? Boolean.parseBoolean(value) : false;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, String[] pValue) {
        UtilXml.addAttribute(pXmlAttributes, pAttribute, ArrayTool.join(pValue, ","));
    }

    public static String[] getStringArrayAttribute(Attributes pXmlAttributes, String pAttribute) {
        String[] result = null;
        String value = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        if (value != null) {
            result = value.split(",");
        }
        return result;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, int[] pValue) {
        UtilXml.addAttribute(pXmlAttributes, pAttribute, ArrayTool.join(pValue, ","));
    }

    public static int[] getIntArrayAttribute(Attributes pXmlAttributes, String pAttribute) {
        return UtilXml.getIntArrayAttribute(pXmlAttributes, pAttribute, 0);
    }

    private static int[] getIntArrayAttribute(Attributes pXmlAttributes, String pAttribute, int pDefault) {
        int[] result = null;
        String value = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        if (value != null) {
            String[] intValues = value.split(",");
            result = new int[intValues.length];
            for (int i = 0; i < intValues.length; ++i) {
                result[i] = intValues[i] != null ? Integer.parseInt(intValues[i]) : pDefault;
            }
        }
        return result;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, boolean[] pValue) {
        UtilXml.addAttribute(pXmlAttributes, pAttribute, ArrayTool.join(pValue, ","));
    }

    public static boolean[] getBooleanArrayAttribute(Attributes pXmlAttributes, String pAttribute) {
        boolean[] result = null;
        String value = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        if (value != null) {
            String[] booleanValues = value.split(",");
            result = new boolean[booleanValues.length];
            for (int i = 0; i < booleanValues.length; ++i) {
                result[i] = Boolean.parseBoolean(booleanValues[i]);
            }
        }
        return result;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, FieldCoordinate pValue) {
        if (pValue == null) {
            return;
        }
        UtilXml.addAttribute(pXmlAttributes, pAttribute, new int[]{pValue.getX(), pValue.getY()});
    }

    public static FieldCoordinate getFieldCoordinateAttribute(Attributes pXmlAttributes, String pAttribute) {
        FieldCoordinate fieldCoordinate = null;
        int[] xyValues = UtilXml.getIntArrayAttribute(pXmlAttributes, pAttribute, -1);
        if (xyValues != null && xyValues.length == 2) {
            fieldCoordinate = new FieldCoordinate(xyValues[0], xyValues[1]);
        }
        return fieldCoordinate;
    }

    public static void addAttribute(AttributesImpl pXmlAttributes, String pAttribute, Date pTimestamp) {
        if (pTimestamp == null) {
            return;
        }
        UtilXml.addAttribute(pXmlAttributes, pAttribute, _TIMESTAMP_FORMAT.format(pTimestamp));
    }

    public static Date getTimestampAttribute(Attributes pXmlAttributes, String pAttribute) {
        String timestamp = UtilXml.getStringAttribute(pXmlAttributes, pAttribute);
        if (!StringTool.isProvided(timestamp)) {
            return null;
        }
        try {
            return _TIMESTAMP_FORMAT.parse(timestamp);
        }
        catch (ParseException pParseException) {
            return null;
        }
    }
}

