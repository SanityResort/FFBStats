/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardFactory;
import com.balancedbytes.games.ffb.Inducement;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class InducementSet
implements IXmlSerializable,
IJsonSerializable {
    public static final String XML_TAG = "inducementSet";
    private static final String _XML_TAG_STAR_PLAYER_SET = "starPlayerSet";
    private static final String _XML_TAG_STAR_PLAYER = "starPlayer";
    private static final String _XML_TAG_CARD_SET = "cardSet";
    private static final String _XML_TAG_CARD = "card";
    private static final String _XML_ATTRIBUTE_POSITION_ID = "positionId";
    private static final String _XML_ATTRIBUTE_NAME = "name";
    private Map<InducementType, Inducement> fInducements = new HashMap<InducementType, Inducement>();
    private Set<Card> fCardsAvailable = new HashSet<Card>();
    private Set<Card> fCardsActive = new HashSet<Card>();
    private Set<Card> fCardsDeactivated = new HashSet<Card>();
    private Set<String> fStarPlayerPositionIds = new HashSet<String>();
    private transient TurnData fTurnData;

    public InducementSet() {
    }

    public InducementSet(TurnData pTurnData) {
        this();
        this.fTurnData = pTurnData;
    }

    public TurnData getTurnData() {
        return this.fTurnData;
    }

    public Inducement get(InducementType pType) {
        if (pType != null) {
            return this.fInducements.get(pType);
        }
        return null;
    }

    public Inducement[] getInducements() {
        return this.fInducements.values().toArray(new Inducement[this.fInducements.size()]);
    }

    public void addInducement(Inducement pInducement) {
        if (pInducement == null) {
            return;
        }
        this.fInducements.put(pInducement.getType(), pInducement);
        this.notifyObservers(ModelChangeId.INDUCEMENT_SET_ADD_INDUCEMENT, pInducement);
    }

    public void removeInducement(Inducement pInducement) {
        if (pInducement == null) {
            return;
        }
        this.fInducements.remove(pInducement.getType());
        this.notifyObservers(ModelChangeId.INDUCEMENT_SET_REMOVE_INDUCEMENT, pInducement);
    }

    public boolean hasUsesLeft(InducementType pType) {
        Inducement inducement = this.get(pType);
        return inducement != null && inducement.getUsesLeft() > 0;
    }

    public void addAvailableCard(Card pCard) {
        if (pCard == null) {
            return;
        }
        this.fCardsAvailable.add(pCard);
        this.notifyObservers(ModelChangeId.INDUCEMENT_SET_ADD_AVAILABLE_CARD, pCard);
    }

    public boolean removeAvailableCard(Card pCard) {
        if (pCard == null) {
            return false;
        }
        boolean removed = this.fCardsAvailable.remove(pCard);
        this.notifyObservers(ModelChangeId.INDUCEMENT_SET_REMOVE_AVAILABLE_CARD, pCard);
        return removed;
    }

    public Card[] getAvailableCards() {
        return this.fCardsAvailable.toArray(new Card[this.fCardsAvailable.size()]);
    }

    public boolean isAvailable(Card pCard) {
        return this.fCardsAvailable.contains(pCard);
    }

    public boolean activateCard(Card pCard) {
        if (pCard == null) {
            return false;
        }
        boolean removed = this.fCardsAvailable.remove(pCard);
        if (removed) {
            this.fCardsActive.add(pCard);
        }
        this.notifyObservers(ModelChangeId.INDUCEMENT_SET_ACTIVATE_CARD, pCard);
        return removed;
    }

    public boolean deactivateCard(Card pCard) {
        if (pCard == null) {
            return false;
        }
        boolean removed = this.fCardsActive.remove(pCard);
        if (removed) {
            this.fCardsDeactivated.add(pCard);
        }
        this.notifyObservers(ModelChangeId.INDUCEMENT_SET_DEACTIVATE_CARD, pCard);
        return removed;
    }

    public Card[] getActiveCards() {
        return this.fCardsActive.toArray(new Card[this.fCardsActive.size()]);
    }

    public Card[] getDeactivatedCards() {
        return this.fCardsDeactivated.toArray(new Card[this.fCardsDeactivated.size()]);
    }

    public boolean isDeactivated(Card pCard) {
        return this.fCardsDeactivated.contains(pCard);
    }

    public Card[] getAllCards() {
        ArrayList<Card> allCards = new ArrayList<Card>();
        for (Card card : this.getAvailableCards()) {
            allCards.add(card);
        }
        for (Card card : this.getActiveCards()) {
            allCards.add(card);
        }
        for (Card card : this.getDeactivatedCards()) {
            allCards.add(card);
        }
        return allCards.toArray(new Card[allCards.size()]);
    }

    public boolean isActive(Card pCard) {
        return this.fCardsActive.contains(pCard);
    }

    public void add(InducementSet pInducementSet) {
        if (pInducementSet != null) {
            for (Inducement inducement : pInducementSet.getInducements()) {
                Inducement initInducement = new Inducement(inducement.getType(), inducement.getValue());
                initInducement.setUses(inducement.getUses());
                this.addInducement(initInducement);
            }
        }
    }

    public void clear() {
        this.fInducements.clear();
        this.fCardsActive.clear();
        this.fCardsAvailable.clear();
        this.fCardsDeactivated.clear();
    }

    public int getNrOfInducements() {
        return this.fInducements.size();
    }

    public int totalInducements() {
        int total = 0;
        for (Inducement inducement : this.getInducements()) {
            total += inducement.getValue();
        }
        return total += this.getAllCards().length;
    }

    public String[] getStarPlayerPositionIds() {
        return this.fStarPlayerPositionIds.toArray(new String[this.fStarPlayerPositionIds.size()]);
    }

    public void addStarPlayerPositionId(String pStarPlayerPositionId) {
        this.fStarPlayerPositionIds.add(pStarPlayerPositionId);
    }

    private void notifyObservers(ModelChangeId pChangeId, Object pValue) {
        if (this.getTurnData() == null || pChangeId == null) {
            return;
        }
        String key = this.getTurnData().isHomeData() ? "home" : "away";
        ModelChange modelChange = new ModelChange(pChangeId, key, pValue);
        this.getTurnData().getGame().notifyObservers(modelChange);
    }

    @Override
    public void addToXml(TransformerHandler pHandler) {
        AttributesImpl attributes;
        UtilXml.startElement(pHandler, "inducementSet");
        for (Inducement inducement : this.fInducements.values()) {
            inducement.addToXml(pHandler);
        }
        UtilXml.startElement(pHandler, "starPlayerSet");
        for (String positionId : this.fStarPlayerPositionIds) {
            attributes = new AttributesImpl();
            UtilXml.addAttribute(attributes, "positionId", positionId);
            UtilXml.addEmptyElement(pHandler, "starPlayer", attributes);
        }
        UtilXml.endElement(pHandler, "starPlayerSet");
        UtilXml.startElement(pHandler, "cardSet");
        for (Card card : this.fCardsAvailable) {
            attributes = new AttributesImpl();
            UtilXml.addAttribute(attributes, "name", card.getName());
            UtilXml.addEmptyElement(pHandler, "card", attributes);
        }
        UtilXml.endElement(pHandler, "cardSet");
        UtilXml.endElement(pHandler, "inducementSet");
    }

    @Override
    public String toXml(boolean pIndent) {
        return UtilXml.toXml(this, pIndent);
    }

    @Override
    public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
        Card card;
        String cardName;
        IXmlSerializable xmlElement = this;
        if ("inducement".equals(pXmlTag)) {
            Inducement inducement = new Inducement();
            inducement.startXmlElement(pXmlTag, pXmlAttributes);
            this.addInducement(inducement);
            xmlElement = inducement;
        }
        if ("starPlayerSet".equals(pXmlTag)) {
            this.fStarPlayerPositionIds.clear();
        }
        if ("starPlayer".equals(pXmlTag)) {
            String positionId = pXmlAttributes.getValue("positionId").trim();
            this.addStarPlayerPositionId(positionId);
        }
        if ("cardSet".equals(pXmlTag)) {
            this.fCardsAvailable.clear();
            this.fCardsActive.clear();
            this.fCardsDeactivated.clear();
        }
        if ("card".equals(pXmlTag) && (card = new CardFactory().forName(cardName = pXmlAttributes.getValue("name").trim())) != null) {
            this.fCardsAvailable.add(card);
        }
        return xmlElement;
    }

    @Override
    public boolean endXmlElement(String pXmlTag, String pValue) {
        return "inducementSet".equals(pXmlTag);
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        JsonArray inducementsArray = new JsonArray();
        for (Inducement inducement : this.fInducements.values()) {
            inducementsArray.add(inducement.toJsonValue());
        }
        IJsonOption.INDUCEMENT_ARRAY.addTo(jsonObject, inducementsArray);
        ArrayList<String> cardsAvailable = new ArrayList<String>();
        for (Card card : this.getAvailableCards()) {
            cardsAvailable.add(card.getName());
        }
        IJsonOption.CARDS_AVAILABLE.addTo(jsonObject, cardsAvailable);
        ArrayList<String> cardsActive = new ArrayList<String>();
        for (Card card : this.getActiveCards()) {
            cardsActive.add(card.getName());
        }
        IJsonOption.CARDS_ACTIVE.addTo(jsonObject, cardsActive);
        ArrayList<String> cardsDeactivated = new ArrayList<String>();
        for (Card card : this.getDeactivatedCards()) {
            cardsDeactivated.add(card.getName());
        }
        IJsonOption.CARDS_DEACTIVATED.addTo(jsonObject, cardsDeactivated);
        Object[] starPlayerPositionIds = this.getStarPlayerPositionIds();
        if (ArrayTool.isProvided(starPlayerPositionIds)) {
            IJsonOption.STAR_PLAYER_POSTION_IDS.addTo(jsonObject, (String[])starPlayerPositionIds);
        }
        return jsonObject;
    }

    @Override
    public InducementSet initFrom(JsonValue pJsonValue) {
        Object[] starPlayerPositionIds;
        Object[] cardsActive;
        Object[] cardsDeactivated;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        JsonArray inducements = IJsonOption.INDUCEMENT_ARRAY.getFrom(jsonObject);
        if (inducements != null) {
            for (int i = 0; i < inducements.size(); ++i) {
                Inducement inducement = new Inducement();
                inducement.initFrom(inducements.get(i));
                this.addInducement(inducement);
            }
        }
        CardFactory cardFactory = new CardFactory();
        Object[] cardsAvailable = IJsonOption.CARDS_AVAILABLE.getFrom(jsonObject);
        if (ArrayTool.isProvided(cardsAvailable)) {
            for (Object cardName : cardsAvailable) {
                this.fCardsAvailable.add(cardFactory.forName((String)cardName));
            }
        }
        if (ArrayTool.isProvided(cardsActive = IJsonOption.CARDS_ACTIVE.getFrom(jsonObject))) {
            for (Object cardName : cardsActive) {
                this.fCardsActive.add(cardFactory.forName((String)cardName));
            }
        }
        if (ArrayTool.isProvided(cardsDeactivated = IJsonOption.CARDS_DEACTIVATED.getFrom(jsonObject))) {
            for (Object cardName : cardsDeactivated) {
                this.fCardsDeactivated.add(cardFactory.forName((String)cardName));
            }
        }
        if (ArrayTool.isProvided(starPlayerPositionIds = IJsonOption.STAR_PLAYER_POSTION_IDS.getFrom(jsonObject))) {
            for (Object starPlayerPositionId : starPlayerPositionIds) {
                this.fStarPlayerPositionIds.add((String)starPlayerPositionId);
            }
        }
        return this;
    }
}

