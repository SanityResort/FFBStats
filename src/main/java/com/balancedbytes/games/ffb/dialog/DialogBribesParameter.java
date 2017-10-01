/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.UtilDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringArrayOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class DialogBribesParameter
implements IDialogParameter {
    private String fTeamId;
    private int fMaxNrOfBribes;
    private List<String> fPlayerIds = new ArrayList<String>();

    public DialogBribesParameter() {
    }

    public DialogBribesParameter(String pTeamId, int pMaxNrOfBribes) {
        this();
        this.fTeamId = pTeamId;
        this.fMaxNrOfBribes = pMaxNrOfBribes;
    }

    @Override
    public DialogId getId() {
        return DialogId.BRIBES;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getMaxNrOfBribes() {
        return this.fMaxNrOfBribes;
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
        DialogBribesParameter transformedParameter = new DialogBribesParameter(this.getTeamId(), this.getMaxNrOfBribes());
        transformedParameter.addPlayerIds(this.getPlayerIds());
        return transformedParameter;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.MAX_NR_OF_BRIBES.addTo(jsonObject, this.fMaxNrOfBribes);
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.getPlayerIds());
        return jsonObject;
    }

    @Override
    public DialogBribesParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fMaxNrOfBribes = IJsonOption.MAX_NR_OF_BRIBES.getFrom(jsonObject);
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        return this;
    }
}

