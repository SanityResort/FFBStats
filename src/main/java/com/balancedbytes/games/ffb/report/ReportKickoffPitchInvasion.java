/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportKickoffPitchInvasion
implements IReport {
    private List<Integer> fRollsHome = new ArrayList<Integer>();
    private List<Boolean> fPlayersAffectedHome = new ArrayList<Boolean>();
    private List<Integer> fRollsAway = new ArrayList<Integer>();
    private List<Boolean> fPlayersAffectedAway = new ArrayList<Boolean>();

    public ReportKickoffPitchInvasion() {
    }

    public ReportKickoffPitchInvasion(int[] pRollsHome, boolean[] pPlayersAffectedHome, int[] pRollsAway, boolean[] pPlayersAffectedAway) {
        this();
        this.addRollsHome(pRollsHome);
        this.addPlayersAffectedHome(pPlayersAffectedHome);
        this.addRollsAway(pRollsAway);
        this.addPlayersAffectedAway(pPlayersAffectedAway);
    }

    @Override
    public ReportId getId() {
        return ReportId.KICKOFF_PITCH_INVASION;
    }

    public int[] getRollsHome() {
        int[] rolls = new int[this.fRollsHome.size()];
        for (int i = 0; i < rolls.length; ++i) {
            rolls[i] = this.fRollsHome.get(i);
        }
        return rolls;
    }

    private void addRollHome(int pRoll) {
        this.fRollsHome.add(pRoll);
    }

    private void addRollsHome(int[] pRolls) {
        if (ArrayTool.isProvided(pRolls)) {
            for (int roll : pRolls) {
                this.addRollHome(roll);
            }
        }
    }

    public boolean[] getPlayersAffectedHome() {
        boolean[] playersAffected = new boolean[this.fPlayersAffectedHome.size()];
        for (int i = 0; i < playersAffected.length; ++i) {
            playersAffected[i] = this.fPlayersAffectedHome.get(i);
        }
        return playersAffected;
    }

    private void addPlayerAffectedHome(boolean pPlayerAffected) {
        this.fPlayersAffectedHome.add(pPlayerAffected);
    }

    private void addPlayersAffectedHome(boolean[] pPlayersAffected) {
        if (ArrayTool.isProvided(pPlayersAffected)) {
            for (boolean playerAffected : pPlayersAffected) {
                this.addPlayerAffectedHome(playerAffected);
            }
        }
    }

    public int[] getRollsAway() {
        int[] rolls = new int[this.fRollsAway.size()];
        for (int i = 0; i < rolls.length; ++i) {
            rolls[i] = this.fRollsAway.get(i);
        }
        return rolls;
    }

    private void addRollAway(int pRoll) {
        this.fRollsAway.add(pRoll);
    }

    private void addRollsAway(int[] pRolls) {
        if (ArrayTool.isProvided(pRolls)) {
            for (int roll : pRolls) {
                this.addRollAway(roll);
            }
        }
    }

    public boolean[] getPlayersAffectedAway() {
        boolean[] playersAffected = new boolean[this.fPlayersAffectedAway.size()];
        for (int i = 0; i < playersAffected.length; ++i) {
            playersAffected[i] = this.fPlayersAffectedAway.get(i);
        }
        return playersAffected;
    }

    private void addPlayerAffectedAway(boolean pPlayerAffected) {
        this.fPlayersAffectedAway.add(pPlayerAffected);
    }

    private void addPlayersAffectedAway(boolean[] pPlayersAffected) {
        if (ArrayTool.isProvided(pPlayersAffected)) {
            for (boolean playerAffected : pPlayersAffected) {
                this.addPlayerAffectedAway(playerAffected);
            }
        }
    }

    @Override
    public ReportKickoffPitchInvasion transform() {
        return new ReportKickoffPitchInvasion(this.getRollsAway(), this.getPlayersAffectedAway(), this.getRollsHome(), this.getPlayersAffectedHome());
    }

    @Override
    public ReportKickoffPitchInvasion initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRollsHome.clear();
        this.addRollsHome(IJsonOption.ROLLS_HOME.getFrom(jsonObject));
        this.fPlayersAffectedHome.clear();
        this.addPlayersAffectedHome(IJsonOption.PLAYERS_AFFECTED_HOME.getFrom(jsonObject));
        this.fRollsAway.clear();
        this.addRollsAway(IJsonOption.ROLLS_AWAY.getFrom(jsonObject));
        this.fPlayersAffectedAway.clear();
        this.addPlayersAffectedAway(IJsonOption.PLAYERS_AFFECTED_AWAY.getFrom(jsonObject));
        return this;
    }
}

