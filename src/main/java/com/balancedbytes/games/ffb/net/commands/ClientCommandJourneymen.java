/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ClientCommandJourneymen
extends ClientCommand {
    private List<Integer> fSlots = new ArrayList<Integer>();
    private List<String> fPositionIds = new ArrayList<String>();

    public ClientCommandJourneymen() {
    }

    public ClientCommandJourneymen(String[] pPositionsIds, int[] pSlots) {
        this();
        this.addPositionIds(pPositionsIds);
        this.addSlots(pSlots);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.CLIENT_JOURNEYMEN;
    }

    public String[] getPositionIds() {
        return this.fPositionIds.toArray(new String[this.fPositionIds.size()]);
    }

    public int[] getSlots() {
        int[] slots = new int[this.fSlots.size()];
        for (int i = 0; i < slots.length; ++i) {
            slots[i] = this.fSlots.get(i);
        }
        return slots;
    }

    public int getSlotsTotal() {
        int total = 0;
        int[] slots = this.getSlots();
        for (int i = 0; i < slots.length; ++i) {
            total += slots[i];
        }
        return total;
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

    private void addSlots(int[] pSlots) {
        if (ArrayTool.isProvided(pSlots)) {
            for (int slots : pSlots) {
                this.fSlots.add(slots);
            }
        }
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = super.toJsonValue();
        IJsonOption.POSITION_IDS.addTo(jsonObject, this.fPositionIds);
        IJsonOption.SLOTS.addTo(jsonObject, this.fSlots);
        return jsonObject;
    }

    @Override
    public ClientCommandJourneymen initFrom(JsonValue jsonValue) {
        super.initFrom(jsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
        this.addPositionIds(IJsonOption.POSITION_IDS.getFrom(jsonObject));
        this.addSlots(IJsonOption.SLOTS.getFrom(jsonObject));
        return this;
    }
}

