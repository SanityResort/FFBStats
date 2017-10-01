/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.ClientCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientCommandUseInducement
extends ClientCommand {
    private InducementType fInducementType;
    private Card fCard;
    private List<String> fPlayerIds = new ArrayList<String>();

    public ClientCommandUseInducement() {
    }

    public ClientCommandUseInducement(InducementType pInducementType) {
        this();
        this.fInducementType = pInducementType;
    }

    public ClientCommandUseInducement(InducementType pInducement, String pPlayerId) {
        this(pInducement);
        this.addPlayerId(pPlayerId);
    }

    public ClientCommandUseInducement(Card pCard) {
        this();
        this.fCard = pCard;
    }

    public ClientCommandUseInducement(Card pCard, String pPlayerId) {
        this(pCard);
        this.addPlayerId(pPlayerId);
    }

    public ClientCommandUseInducement(InducementType pInducement, String[] pPlayerIds) {
        this(pInducement);
        this.addPlayerIds(pPlayerIds);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_USE_INDUCEMENT;
    }

    public InducementType getInducementType() {
        return this.fInducementType;
    }

    public Card getCard() {
        return this.fCard;
    }

    public String[] getPlayerIds() {
        return this.fPlayerIds.toArray(new String[this.fPlayerIds.size()]);
    }

    public boolean hasPlayerId(String pPlayerId) {
        return this.fPlayerIds.contains(pPlayerId);
    }

    private void addPlayerId(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayerIds.add(pPlayerId);
        }
    }

    private void addPlayerIds(String[] pPlayerIds) {
        if (ArrayTool.isProvided(pPlayerIds)) {
            for (String playerId : pPlayerIds) {
                this.addPlayerId(playerId);
            }
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.INDUCEMENT_TYPE.addTo(jsonObject, this.fInducementType);
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.fPlayerIds);
        IJsonOption.CARD.addTo(jsonObject, this.fCard);
        return jsonObject;
    }

    @Override
    public ClientCommandUseInducement initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.fInducementType = (InducementType)IJsonOption.INDUCEMENT_TYPE.getFrom(jsonObject);
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        this.fCard = (Card)IJsonOption.CARD.getFrom(jsonObject);
        return this;
    }
}

