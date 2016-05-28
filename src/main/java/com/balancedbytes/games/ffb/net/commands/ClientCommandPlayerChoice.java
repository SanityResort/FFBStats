/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.PlayerChoiceMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.net.commands.UtilNetCommand;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientCommandPlayerChoice
extends NetCommand {
    private PlayerChoiceMode fPlayerChoiceMode;
    private List<String> fPlayerIds = new ArrayList<String>();

    public ClientCommandPlayerChoice() {
    }

    public ClientCommandPlayerChoice(PlayerChoiceMode pPlayerChoiceMode, Player[] pPlayers) {
        this();
        this.fPlayerChoiceMode = pPlayerChoiceMode;
        if (ArrayTool.isProvided(pPlayers)) {
            for (Player player : pPlayers) {
                this.addPlayerId(player.getId());
            }
        }
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_PLAYER_CHOICE;
    }

    public String getPlayerId() {
        return this.fPlayerIds.size() > 0 ? this.fPlayerIds.get(0) : null;
    }

    public String[] getPlayerIds() {
        return this.fPlayerIds.toArray(new String[this.fPlayerIds.size()]);
    }

    public void addPlayerId(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayerIds.add(pPlayerId);
        }
    }

    private void addPlayerIds(String[] pPlayerIds) {
        if (ArrayTool.isProvided(pPlayerIds)) {
            for (int i = 0; i < pPlayerIds.length; ++i) {
                this.addPlayerId(pPlayerIds[i]);
            }
        }
    }

    public PlayerChoiceMode getPlayerChoiceMode() {
        return this.fPlayerChoiceMode;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.NET_COMMAND_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_CHOICE_MODE.addTo(jsonObject, this.fPlayerChoiceMode);
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.fPlayerIds);
        return jsonObject;
    }

    @Override
    public ClientCommandPlayerChoice initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.fPlayerChoiceMode = (PlayerChoiceMode)IJsonOption.PLAYER_CHOICE_MODE.getFrom(jsonObject);
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        return this;
    }
}

