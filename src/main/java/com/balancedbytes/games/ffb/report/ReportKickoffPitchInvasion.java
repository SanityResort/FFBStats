package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportKickoffPitchInvasion implements IReport {
    private List<Integer> fRollsHome = new ArrayList<Integer>();
    private List<Integer> fRollsAway = new ArrayList<Integer>();

    ReportKickoffPitchInvasion() {
    }

    private ReportKickoffPitchInvasion(int[] pRollsHome, int[] pRollsAway) {
        this();
        this.addRollsHome(pRollsHome);
        this.addRollsAway(pRollsAway);
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

    @Override
    public ReportKickoffPitchInvasion transform() {
        return new ReportKickoffPitchInvasion(this.getRollsAway(), this.getRollsHome());
    }
    @Override
    public ReportKickoffPitchInvasion initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fRollsHome.clear();
        this.addRollsHome(IJsonOption.ROLLS_HOME.getFrom(jsonObject));
        this.fRollsAway.clear();
        this.addRollsAway(IJsonOption.ROLLS_AWAY.getFrom(jsonObject));
        return this;
    }
}

