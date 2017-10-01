/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardFactory;
import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.InducementTypeFactory;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;

public class DialogUseInducementParameter
implements IDialogParameter {
    private String fTeamId;
    private InducementType[] fInducementTypes;
    private Card[] fCards;

    public DialogUseInducementParameter() {
    }

    public DialogUseInducementParameter(String pTeamId, InducementType[] pInducementTypes, Card[] pCards) {
        this.fTeamId = pTeamId;
        this.fInducementTypes = pInducementTypes;
        this.fCards = pCards;
    }

    @Override
    public DialogId getId() {
        return DialogId.USE_INDUCEMENT;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public InducementType[] getInducementTypes() {
        return this.fInducementTypes;
    }

    public Card[] getCards() {
        return this.fCards;
    }

    @Override
    public IDialogParameter transform() {
        return new DialogUseInducementParameter(this.getTeamId(), this.getInducementTypes(), this.getCards());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        ArrayList<String> inducementTypeNames = new ArrayList<String>();
        for (InducementType inducementType : this.getInducementTypes()) {
            inducementTypeNames.add(inducementType.getName());
        }
        IJsonOption.INDUCEMENT_TYPE_ARRAY.addTo(jsonObject, inducementTypeNames);
        ArrayList<String> cardNames = new ArrayList<String>();
        for (Card card : this.getCards()) {
            cardNames.add(card.getName());
        }
        IJsonOption.CARDS.addTo(jsonObject, cardNames);
        return jsonObject;
    }

    @Override
    public DialogUseInducementParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        String[] inducementTypeNames = IJsonOption.INDUCEMENT_TYPE_ARRAY.getFrom(jsonObject);
        this.fInducementTypes = new InducementType[inducementTypeNames.length];
        InducementTypeFactory inducementTypeFactory = new InducementTypeFactory();
        for (int i = 0; i < this.fInducementTypes.length; ++i) {
            this.fInducementTypes[i] = inducementTypeFactory.forName(inducementTypeNames[i]);
        }
        String[] cardNames = IJsonOption.CARDS.getFrom(jsonObject);
        this.fCards = new Card[cardNames.length];
        CardFactory cardFactory = new CardFactory();
        for (int i = 0; i < this.fCards.length; ++i) {
            this.fCards[i] = cardFactory.forName(cardNames[i]);
        }
        return this;
    }
}

