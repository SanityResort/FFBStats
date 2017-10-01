/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportSecretWeaponBan
implements IReport {
    private List<String> fPlayerIds = new ArrayList<String>();
    private List<Integer> fRolls = new ArrayList<Integer>();
    private List<Boolean> fBans = new ArrayList<Boolean>();

    @Override
    public ReportId getId() {
        return ReportId.SECRET_WEAPON_BAN;
    }

    public String[] getPlayerIds() {
        return this.fPlayerIds.toArray(new String[this.fPlayerIds.size()]);
    }

    public int[] getRolls() {
        int[] result = new int[this.fRolls.size()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.fRolls.get(i);
        }
        return result;
    }

    public boolean[] getBans() {
        boolean[] result = new boolean[this.fBans.size()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.fBans.get(i);
        }
        return result;
    }

    public void add(String pPlayerId, int pRoll, boolean pBanned) {
        this.fPlayerIds.add(pPlayerId);
        this.fRolls.add(pRoll);
        this.fBans.add(pBanned);
    }

    private void addPlayerIds(String[] pPlayerIds) {
        if (pPlayerIds != null) {
            for (String playerId : pPlayerIds) {
                this.fPlayerIds.add(playerId);
            }
        }
    }

    private void addRolls(int[] pRolls) {
        if (pRolls != null) {
            for (int roll : pRolls) {
                this.fRolls.add(roll);
            }
        }
    }

    private void addBans(boolean[] pBans) {
        if (pBans != null) {
            for (boolean ban : pBans) {
                this.fBans.add(ban);
            }
        }
    }

    @Override
    public ReportSecretWeaponBan transform() {
        ReportSecretWeaponBan transformed = new ReportSecretWeaponBan();
        String[] playerIds = this.getPlayerIds();
        int[] rolls = this.getRolls();
        boolean[] banned = this.getBans();
        for (int i = 0; i < playerIds.length; ++i) {
            transformed.add(playerIds[i], rolls[i], banned[i]);
        }
        return transformed;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_IDS.addTo(jsonObject, this.fPlayerIds);
        IJsonOption.ROLLS.addTo(jsonObject, this.fRolls);
        IJsonOption.BAN_ARRAY.addTo(jsonObject, this.fBans);
        return jsonObject;
    }

    @Override
    public ReportSecretWeaponBan initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerIds.clear();
        this.addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
        this.fRolls.clear();
        this.addRolls(IJsonOption.ROLLS.getFrom(jsonObject));
        this.fBans.clear();
        this.addBans(IJsonOption.BAN_ARRAY.getFrom(jsonObject));
        return this;
    }
}

