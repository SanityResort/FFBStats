/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class ReportInjury
implements IReport {
    private String fDefenderId;
    private boolean fArmorBroken;
    private int[] fArmorRoll;
    private int[] fInjuryRoll;
    private int[] fCasualtyRoll;
    private int[] fCasualtyRollDecay;

    public ReportInjury() {
    }

    public ReportInjury(String pDefenderId, boolean pArmorBroken, int[] pArmorRoll, int[] pInjuryRoll, int[] pCasualtyRoll, int[] pCasualtyRollDecay) {
        this();
        this.fDefenderId = pDefenderId;
        this.fArmorBroken = pArmorBroken;
        this.fArmorRoll = pArmorRoll;
        this.fInjuryRoll = pInjuryRoll;
        this.fCasualtyRoll = pCasualtyRoll;
        this.fCasualtyRollDecay = pCasualtyRollDecay;
    }

    @Override
    public ReportId getId() {
        return ReportId.INJURY;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public boolean isArmorBroken() {
        return this.fArmorBroken;
    }

    public int[] getArmorRoll() {
        return this.fArmorRoll;
    }

    public int[] getInjuryRoll() {
        return this.fInjuryRoll;
    }

    public int[] getCasualtyRoll() {
        return this.fCasualtyRoll;
    }

    public int[] getCasualtyRollDecay() {
        return this.fCasualtyRollDecay;
    }

    @Override
    public IReport transform() {
        return new ReportInjury(this.getDefenderId(), this.isArmorBroken(), this.getArmorRoll(), this.getInjuryRoll(), this.getCasualtyRoll(), this.getCasualtyRollDecay());
    }

    @Override
    public ReportInjury initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fArmorBroken = IJsonOption.ARMOR_BROKEN.getFrom(jsonObject);
        this.fArmorRoll = IJsonOption.ARMOR_ROLL.getFrom(jsonObject);
        this.fInjuryRoll = IJsonOption.INJURY_ROLL.getFrom(jsonObject);
        this.fCasualtyRoll = IJsonOption.CASUALTY_ROLL.getFrom(jsonObject);
        this.fCasualtyRollDecay = IJsonOption.CASUALTY_ROLL_DECAY.getFrom(jsonObject);
        return this;
    }
}

