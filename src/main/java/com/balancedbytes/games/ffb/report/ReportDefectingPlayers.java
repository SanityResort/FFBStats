/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportDefectingPlayers
implements IReport {
    private List<String> fPlayerIds = new ArrayList<String>();
    private List<Integer> fRolls = new ArrayList<Integer>();
    private List<Boolean> fDefectings = new ArrayList<Boolean>();

    public ReportDefectingPlayers() {
    }

    public ReportDefectingPlayers(String[] pPlayerIds, int[] pRolls, boolean[] pDefecting) {
        this();
        this.addPlayerIds(pPlayerIds);
        this.addRolls(pRolls);
        this.addDefectings(pDefecting);
    }

    @Override
    public ReportId getId() {
        return ReportId.DEFECTING_PLAYERS;
    }

    public int[] getRolls() {
        int[] rolls = new int[this.fRolls.size()];
        for (int i = 0; i < rolls.length; ++i) {
            rolls[i] = this.fRolls.get(i);
        }
        return rolls;
    }

    private void addRoll(int pRoll) {
        this.fRolls.add(pRoll);
    }

    private void addRolls(int[] pRolls) {
        if (ArrayTool.isProvided(pRolls)) {
            for (int roll : pRolls) {
                this.addRoll(roll);
            }
        }
    }

    public boolean[] getDefectings() {
        boolean[] defecting = new boolean[this.fDefectings.size()];
        for (int i = 0; i < defecting.length; ++i) {
            defecting[i] = this.fDefectings.get(i);
        }
        return defecting;
    }

    private void addDefecting(boolean pDefecting) {
        this.fDefectings.add(pDefecting);
    }

    private void addDefectings(boolean[] pDefectings) {
        if (ArrayTool.isProvided(pDefectings)) {
            for (boolean defecting : pDefectings) {
                this.addDefecting(defecting);
            }
        }
    }

    public String[] getPlayerIds() {
        return this.fPlayerIds.toArray(new String[this.fPlayerIds.size()]);
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
    public ReportDefectingPlayers transform() {
        return new ReportDefectingPlayers(this.getPlayerIds(), this.getRolls(), this.getDefectings());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.fPlayerIds);
        IJsonOption.ROLLS.addTo(jsonObject, this.fRolls);
        IJsonOption.DEFECTING_ARRAY.addTo(jsonObject, this.fDefectings);
        return jsonObject;
    }

    @Override
    public ReportDefectingPlayers initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerIds.clear();
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        this.fRolls.clear();
        this.addRolls(IJsonOption.ROLLS.getFrom(jsonObject));
        this.fDefectings.clear();
        this.addDefectings(IJsonOption.DEFECTING_ARRAY.getFrom(jsonObject));
        return this;
    }
}

