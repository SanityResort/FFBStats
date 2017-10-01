/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.PlayerChoiceMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class DialogPlayerChoiceParameter
implements IDialogParameter {
    private String fTeamId;
    private PlayerChoiceMode fPlayerChoiceMode;
    private List<String> fPlayerIds = new ArrayList<String>();
    private List<String> fDescriptions = new ArrayList<String>();
    private int fMaxSelects;

    public DialogPlayerChoiceParameter() {
    }

    public DialogPlayerChoiceParameter(String pTeamId, PlayerChoiceMode pPlayerChoiceMode, Player[] pPlayers, String[] pDescriptions, int pMaxSelects) {
        this(pTeamId, pPlayerChoiceMode, DialogPlayerChoiceParameter.findPlayerIds(pPlayers), pDescriptions, pMaxSelects);
    }

    public DialogPlayerChoiceParameter(String pTeamId, PlayerChoiceMode pPlayerChoiceMode, String[] pPlayerIds, String[] pDescriptions, int pMaxSelects) {
        this();
        this.fTeamId = pTeamId;
        this.fPlayerChoiceMode = pPlayerChoiceMode;
        this.fMaxSelects = pMaxSelects;
        this.addDescriptions(pDescriptions);
        this.addPlayerIds(pPlayerIds);
    }

    @Override
    public DialogId getId() {
        return DialogId.PLAYER_CHOICE;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getMaxSelects() {
        return this.fMaxSelects;
    }

    public PlayerChoiceMode getPlayerChoiceMode() {
        return this.fPlayerChoiceMode;
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

    public String[] getDescriptions() {
        return this.fDescriptions.toArray(new String[this.fDescriptions.size()]);
    }

    public void addDescription(String pDescription) {
        if (StringTool.isProvided(pDescription)) {
            this.fDescriptions.add(pDescription);
        }
    }

    private void addDescriptions(String[] pDescriptions) {
        if (ArrayTool.isProvided(pDescriptions)) {
            for (int i = 0; i < pDescriptions.length; ++i) {
                this.addDescription(pDescriptions[i]);
            }
        }
    }

    private static String[] findPlayerIds(Player[] pPlayers) {
        if (ArrayTool.isProvided(pPlayers)) {
            String[] playerIds = new String[pPlayers.length];
            for (int i = 0; i < playerIds.length; ++i) {
                playerIds[i] = pPlayers[i].getId();
            }
            return playerIds;
        }
        return new String[0];
    }

    @Override
    public IDialogParameter transform() {
        return new DialogPlayerChoiceParameter(this.getTeamId(), this.getPlayerChoiceMode(), this.getPlayerIds(), this.getDescriptions(), this.getMaxSelects());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.PLAYER_CHOICE_MODE.addTo(jsonObject, this.fPlayerChoiceMode);
        IJsonOption.MAX_SELECTS.addTo(jsonObject, this.fMaxSelects);
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.fPlayerIds);
        IJsonOption.DESCRIPTIONS.addTo(jsonObject, this.fDescriptions);
        return jsonObject;
    }

    @Override
    public DialogPlayerChoiceParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fPlayerChoiceMode = (PlayerChoiceMode)IJsonOption.PLAYER_CHOICE_MODE.getFrom(jsonObject);
        this.fMaxSelects = IJsonOption.MAX_SELECTS.getFrom(jsonObject);
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        this.addDescriptions(IJsonOption.DESCRIPTIONS.getFrom(jsonObject));
        return this;
    }
}

