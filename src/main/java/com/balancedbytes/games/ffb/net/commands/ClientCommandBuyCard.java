/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandBuyCard
extends ClientCommand {
    private CardType fCardType;

    public ClientCommandBuyCard() {
    }

    public ClientCommandBuyCard(CardType pCardType) {
        this.fCardType = pCardType;
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_BUY_CARD;
    }

    public CardType getCardType() {
        return this.fCardType;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.CARD_TYPE.addTo(jsonObject, this.fCardType);
        return jsonObject;
    }

    @Override
    public ClientCommandBuyCard initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fCardType = (CardType)IJsonOption.CARD_TYPE.getFrom(jsonObject);
        return this;
    }
}

