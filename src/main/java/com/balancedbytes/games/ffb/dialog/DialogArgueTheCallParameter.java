/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class DialogArgueTheCallParameter
implements IDialogParameter {
    private String fTeamId;
    private List<String> fPlayerIds = new ArrayList<String>();

    public DialogArgueTheCallParameter() {
    }

    public DialogArgueTheCallParameter(String teamId) {
        this();
        this.setTeamId(teamId);
    }

    @Override
    public DialogId getId() {
        return DialogId.ARGUE_THE_CALL;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public void setTeamId(String teamId) {
        this.fTeamId = teamId;
    }

    public void addPlayerId(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayerIds.add(pPlayerId);
        }
    }

    public void addPlayerIds(String[] pPlayerIds) {
        if (ArrayTool.isProvided(pPlayerIds)) {
            for (String playerId : pPlayerIds) {
                this.addPlayerId(playerId);
            }
        }
    }

    public String[] getPlayerIds() {
        return this.fPlayerIds.toArray(new String[this.fPlayerIds.size()]);
    }

    @Override
    public IDialogParameter transform() {
        DialogArgueTheCallParameter transformedParameter = new DialogArgueTheCallParameter(this.getTeamId());
        transformedParameter.addPlayerIds(this.getPlayerIds());
        return transformedParameter;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.getTeamId());
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.getPlayerIds());
        return jsonObject;
    }

    @Override
    public DialogArgueTheCallParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.setTeamId(IJsonOption.TEAM_ID.getFrom(jsonObject));
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        return this;
    }
}

