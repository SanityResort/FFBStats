/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.xml;

import com.balancedbytes.games.ffb.FantasyFootballException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.util.Stack;

public class XmlHandler
extends DefaultHandler {
    private StringBuilder fValue = new StringBuilder();
    private IXmlReadable fParsedElement;
    private Stack<IXmlReadable> fXmlElementStack;

    public XmlHandler(IXmlReadable pParsedElement) {
        this.fParsedElement = pParsedElement;
        this.fXmlElementStack = new Stack();
        this.fXmlElementStack.push(this.fParsedElement);
    }

    public IXmlReadable getParsedElement() {
        return this.fParsedElement;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        this.fValue.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String value = this.fValue.toString().trim();
        while (!this.fXmlElementStack.empty()) {
            IXmlReadable currentElement = this.fXmlElementStack.pop();
            if (currentElement.endXmlElement(qName, value)) continue;
            this.fXmlElementStack.push(currentElement);
            break;
        }
        this.fValue = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        IXmlReadable newElement;
        IXmlReadable currentElement;
        if (!this.fXmlElementStack.empty() && (currentElement = this.fXmlElementStack.peek()) != (newElement = currentElement.startXmlElement(qName, atts))) {
            this.fXmlElementStack.push(newElement);
        }
    }

    public static void parse(InputSource pXmlSource, IXmlReadable pParsedElement) {
        SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
        xmlParserFactory.setNamespaceAware(false);
        XmlHandler xmlHandler = new XmlHandler(pParsedElement);
        XMLReader xmlReader = null;
        try {
            xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
            xmlReader.setContentHandler(xmlHandler);
        }
        catch (Exception e) {
            throw new FantasyFootballException("Unable to initialize parser.", e);
        }
        try {
            xmlReader.parse(pXmlSource);
        }
        catch (Exception e) {
            throw new FantasyFootballException("Parsing error.", e);
        }
    }
}

