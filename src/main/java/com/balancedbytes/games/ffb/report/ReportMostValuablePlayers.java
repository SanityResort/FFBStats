/*
 * Decompiled with CFR 0_122.
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

public class ReportMostValuablePlayers
implements IReport {
    private List<String> fPlayerIdsHome = new ArrayList<String>();
    private List<String> fPlayerIdsAway = new ArrayList<String>();

    @Override
    public ReportId getId() {
        return ReportId.MOST_VALUABLE_PLAYERS;
    }

    public void addPlayerIdHome(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayerIdsHome.add(pPlayerId);
        }
    }

    private void addPlayerIdsHome(String[] pPlayerIds) {
        if (ArrayTool.isProvided(pPlayerIds)) {
            for (String playerId : pPlayerIds) {
                this.addPlayerIdHome(playerId);
            }
        }
    }

    public String[] getPlayerIdsHome() {
        return this.fPlayerIdsHome.toArray(new String[this.fPlayerIdsHome.size()]);
    }

    public void addPlayerIdAway(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayerIdsAway.add(pPlayerId);
        }
    }

    private void addPlayerIdsAway(String[] pPlayerIds) {
        if (ArrayTool.isProvided(pPlayerIds)) {
            for (String playerId : pPlayerIds) {
                this.addPlayerIdAway(playerId);
            }
        }
    }

    public String[] getPlayerIdsAway() {
        return this.fPlayerIdsAway.toArray(new String[this.fPlayerIdsAway.size()]);
    }

    @Override
    public IReport transform() {
        ReportMostValuablePlayers transformedReport = new ReportMostValuablePlayers();
        transformedReport.addPlayerIdsAway(this.getPlayerIdsHome());
        transformedReport.addPlayerIdsHome(this.getPlayerIdsAway());
        return transformedReport;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_IDS_HOME.addTo(jsonObject, this.getPlayerIdsHome());
        IJsonOption.PLAYER_IDS_AWAY.addTo(jsonObject, this.getPlayerIdsAway());
        return jsonObject;
    }

    @Override
    public ReportMostValuablePlayers initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.addPlayerIdsHome(IJsonOption.PLAYER_IDS_HOME.getFrom(jsonObject));
        this.addPlayerIdsAway(IJsonOption.PLAYER_IDS_AWAY.getFrom(jsonObject));
        return this;
    }
}

