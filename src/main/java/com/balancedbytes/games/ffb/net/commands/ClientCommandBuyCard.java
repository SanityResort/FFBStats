/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ClientCommandBuyCard
extends NetCommand {
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
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.CARD_TYPE.addTo(jsonObject, this.fCardType);
        return jsonObject;
    }

    @Override
    public ClientCommandBuyCard initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fCardType = (CardType)IJsonOption.CARD_TYPE.getFrom(jsonObject);
        return this;
    }
}

