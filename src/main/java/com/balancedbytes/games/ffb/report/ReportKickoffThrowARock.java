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

public class ReportKickoffThrowARock
implements IReport {
    private int fRollHome;
    private int fRollAway;
    private List<String> fPlayersHit = new ArrayList<String>();

    public ReportKickoffThrowARock() {
    }

    public ReportKickoffThrowARock(int pRollHome, int pRollAway, String[] pPlayersHit) {
        this();
        this.fRollHome = pRollHome;
        this.fRollAway = pRollAway;
        this.addPlayerIds(pPlayersHit);
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_THROW_A_ROCK;
    }

    public int getRollHome() {
        return this.fRollHome;
    }

    public int getRollAway() {
        return this.fRollAway;
    }

    public String[] getPlayersHit() {
        return this.fPlayersHit.toArray(new String[this.fPlayersHit.size()]);
    }

    private void addPlayerId(String pPlayerId) {
        if (StringTool.isProvided(pPlayerId)) {
            this.fPlayersHit.add(pPlayerId);
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
    public IReport transform() {
        return new ReportKickoffThrowARock(this.getRollAway(), this.getRollHome(), this.getPlayersHit());
    }

    @Override
    public ReportKickoffThrowARock initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRollHome = IJsonOption.ROLL_HOME.getFrom(jsonObject);
        this.fRollAway = IJsonOption.ROLL_AWAY.getFrom(jsonObject);
        this.fPlayersHit.clear();
        this.addPlayerIds(IJsonOption.PLAYER_IDS_HIT.getFrom(jsonObject));
        return this;
    }
}

