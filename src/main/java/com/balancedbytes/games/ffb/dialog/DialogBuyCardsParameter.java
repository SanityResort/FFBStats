/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DialogBuyCardsParameter
implements IDialogParameter {
    private String fTeamId;
    private int fAvailableGold;
    private int fAvailableCards;
    private Map<CardType, Integer> fNrOfCardsPerType = new HashMap<CardType, Integer>();

    public DialogBuyCardsParameter() {
    }

    public DialogBuyCardsParameter(String pTeamId, int pAvailableCards, int pAvailableGold) {
        this();
        this.fTeamId = pTeamId;
        this.fAvailableCards = pAvailableCards;
        this.fAvailableGold = pAvailableGold;
    }

    @Override
    public DialogId getId() {
        return DialogId.BUY_CARDS;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getAvailableCards() {
        return this.fAvailableCards;
    }

    public int getAvailableGold() {
        return this.fAvailableGold;
    }

    public void put(CardType pType, int pNrOfCards) {
        this.fNrOfCardsPerType.put(pType, pNrOfCards);
    }

    public int getNrOfCards(CardType pType) {
        Integer nrOfCards = this.fNrOfCardsPerType.get(pType);
        return nrOfCards != null ? nrOfCards : 0;
    }

    @Override
    public IDialogParameter transform() {
        DialogBuyCardsParameter dialogParameter = new DialogBuyCardsParameter(this.getTeamId(), this.getAvailableCards(), this.getAvailableGold());
        for (CardType type : CardType.values()) {
            dialogParameter.put(type, this.getNrOfCards(type));
        }
        return dialogParameter;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.AVAILABLE_CARDS.addTo(jsonObject, this.fAvailableCards);
        IJsonOption.AVAILABLE_GOLD.addTo(jsonObject, this.fAvailableGold);
        JsonArray nrOfCardsPerType = new JsonArray();
        for (CardType type : this.fNrOfCardsPerType.keySet()) {
            JsonObject nrOfCardsForThisType = new JsonObject();
            IJsonOption.CARD_TYPE.addTo(nrOfCardsForThisType, type);
            IJsonOption.NR_OF_CARDS.addTo(nrOfCardsForThisType, this.fNrOfCardsPerType.get(type));
            nrOfCardsPerType.add(nrOfCardsForThisType);
        }
        IJsonOption.NR_OF_CARDS_PER_TYPE.addTo(jsonObject, nrOfCardsPerType);
        return jsonObject;
    }

    @Override
    public DialogBuyCardsParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fAvailableCards = IJsonOption.AVAILABLE_CARDS.getFrom(jsonObject);
        this.fAvailableGold = IJsonOption.AVAILABLE_GOLD.getFrom(jsonObject);
        JsonArray nrOfCardsPerType = IJsonOption.NR_OF_CARDS_PER_TYPE.getFrom(jsonObject);
        for (int i = 0; i < nrOfCardsPerType.size(); ++i) {
            JsonObject nrOfCardsForThisType = nrOfCardsPerType.get(i).asObject();
            CardType cardType = (CardType)IJsonOption.CARD_TYPE.getFrom(nrOfCardsForThisType);
            int nrOfCards = IJsonOption.NR_OF_CARDS.getFrom(nrOfCardsForThisType);
            this.put(cardType, nrOfCards);
        }
        return this;
    }
}

