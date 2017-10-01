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

public class DialogJourneymenParameter
implements IDialogParameter {
    private String fTeamId;
    private int fNrOfSlots;
    private List<String> fPositionIds = new ArrayList<String>();

    public DialogJourneymenParameter() {
    }

    public DialogJourneymenParameter(String pTeamId, int pNrOfSlots, String[] pPositionIds) {
        this();
        this.fTeamId = pTeamId;
        this.fNrOfSlots = pNrOfSlots;
        this.addPositionIds(pPositionIds);
    }

    @Override
    public DialogId getId() {
        return DialogId.JOURNEYMEN;
    }

    public String getTeamId() {
        return this.fTeamId;
    }

    public int getNrOfSlots() {
        return this.fNrOfSlots;
    }

    private void addPositionId(String pPositionId) {
        if (StringTool.isProvided(pPositionId)) {
            this.fPositionIds.add(pPositionId);
        }
    }

    private void addPositionIds(String[] pPositionIds) {
        if (ArrayTool.isProvided(pPositionIds)) {
            for (String positionId : pPositionIds) {
                this.addPositionId(positionId);
            }
        }
    }

    public String[] getPositionIds() {
        return this.fPositionIds.toArray(new String[this.fPositionIds.size()]);
    }

    @Override
    public IDialogParameter transform() {
        return new DialogJourneymenParameter(this.getTeamId(), this.getNrOfSlots(), this.getPositionIds());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.DIALOG_ID.addTo(jsonObject, this.getId());
        IJsonOption.TEAM_ID.addTo(jsonObject, this.fTeamId);
        IJsonOption.NR_OF_SLOTS.addTo(jsonObject, this.fNrOfSlots);
        IJsonOption.POSITION_IDS.addTo(jsonObject, this.getPositionIds());
        return jsonObject;
    }

    @Override
    public DialogJourneymenParameter initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilDialogParameter.validateDialogId(this, (DialogId)IJsonOption.DIALOG_ID.getFrom(jsonObject));
        this.fTeamId = IJsonOption.TEAM_ID.getFrom(jsonObject);
        this.fNrOfSlots = IJsonOption.NR_OF_SLOTS.getFrom(jsonObject);
        this.addPositionIds(IJsonOption.POSITION_IDS.getFrom(jsonObject));
        return this;
    }
}

