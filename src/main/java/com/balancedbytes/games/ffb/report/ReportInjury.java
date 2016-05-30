/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.ArmorModifier;
import com.balancedbytes.games.ffb.ArmorModifierFactory;
import com.balancedbytes.games.ffb.InjuryModifier;
import com.balancedbytes.games.ffb.InjuryModifierFactory;
import com.balancedbytes.games.ffb.InjuryType;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportInjury
implements IReport {
    private String fAttackerId;
    private String fDefenderId;
    private InjuryType fInjuryType;
    private boolean fArmorBroken;
    private List<ArmorModifier> fArmorModifiers = new ArrayList<ArmorModifier>();
    private int[] fArmorRoll;
    private List<InjuryModifier> fInjuryModifiers = new ArrayList<InjuryModifier>();
    private int[] fInjuryRoll;
    private int[] fCasualtyRoll;
    private SeriousInjury fSeriousInjury;
    private int[] fCasualtyRollDecay;
    private SeriousInjury fSeriousInjuryDecay;
    private PlayerState fInjury;
    private PlayerState fInjuryDecay;

    public ReportInjury() {
    }

    public ReportInjury(String pDefenderId, InjuryType pInjuryType, boolean pArmorBroken, ArmorModifier[] pArmorModifiers, int[] pArmorRoll, InjuryModifier[] pInjuryModifiers, int[] pInjuryRoll, int[] pCasualtyRoll, SeriousInjury pSeriousInjury, int[] pCasualtyRollDecay, SeriousInjury pSeriousInjuryDecay, PlayerState pInjury, PlayerState pInjuryDecay, String pAttackerId) {
        this();
        this.fDefenderId = pDefenderId;
        this.fInjuryType = pInjuryType;
        this.fArmorBroken = pArmorBroken;
        this.add(pArmorModifiers);
        this.fArmorRoll = pArmorRoll;
        this.add(pInjuryModifiers);
        this.fInjuryRoll = pInjuryRoll;
        this.fCasualtyRoll = pCasualtyRoll;
        this.fSeriousInjury = pSeriousInjury;
        this.fCasualtyRollDecay = pCasualtyRollDecay;
        this.fSeriousInjuryDecay = pSeriousInjuryDecay;
        this.fInjury = pInjury;
        this.fInjuryDecay = pInjuryDecay;
        this.fAttackerId = pAttackerId;
    }

    @Override
    public ReportId getId() {
        return ReportId.INJURY;
    }

    public String getDefenderId() {
        return this.fDefenderId;
    }

    public InjuryType getInjuryType() {
        return this.fInjuryType;
    }

    public boolean isArmorBroken() {
        return this.fArmorBroken;
    }

    public ArmorModifier[] getArmorModifiers() {
        return this.fArmorModifiers.toArray(new ArmorModifier[this.fArmorModifiers.size()]);
    }

    private void add(ArmorModifier pArmorModifier) {
        if (pArmorModifier != null) {
            this.fArmorModifiers.add(pArmorModifier);
        }
    }

    private void add(ArmorModifier[] pArmorModifiers) {
        if (ArrayTool.isProvided(pArmorModifiers)) {
            for (ArmorModifier armorModifier : pArmorModifiers) {
                this.add(armorModifier);
            }
        }
    }

    public int[] getArmorRoll() {
        return this.fArmorRoll;
    }

    public InjuryModifier[] getInjuryModifiers() {
        return this.fInjuryModifiers.toArray(new InjuryModifier[this.fInjuryModifiers.size()]);
    }

    private void add(InjuryModifier pInjuryModifier) {
        if (pInjuryModifier != null) {
            this.fInjuryModifiers.add(pInjuryModifier);
        }
    }

    private void add(InjuryModifier[] pInjuryModifiers) {
        if (ArrayTool.isProvided(pInjuryModifiers)) {
            for (InjuryModifier injuryModifier : pInjuryModifiers) {
                this.add(injuryModifier);
            }
        }
    }

    public int[] getInjuryRoll() {
        return this.fInjuryRoll;
    }

    public int[] getCasualtyRoll() {
        return this.fCasualtyRoll;
    }

    public PlayerState getInjury() {
        return this.fInjury;
    }

    public PlayerState getInjuryDecay() {
        return this.fInjuryDecay;
    }

    public SeriousInjury getSeriousInjury() {
        return this.fSeriousInjury;
    }

    public int[] getCasualtyRollDecay() {
        return this.fCasualtyRollDecay;
    }

    public SeriousInjury getSeriousInjuryDecay() {
        return this.fSeriousInjuryDecay;
    }

    public String getAttackerId() {
        return this.fAttackerId;
    }

    @Override
    public IReport transform() {
        return new ReportInjury(this.getDefenderId(), this.getInjuryType(), this.isArmorBroken(), this.getArmorModifiers(), this.getArmorRoll(), this.getInjuryModifiers(), this.getInjuryRoll(), this.getCasualtyRoll(), this.getSeriousInjury(), this.getCasualtyRollDecay(), this.getSeriousInjuryDecay(), this.getInjury(), this.getInjuryDecay(), this.getAttackerId());
    }

    @Override
    public ReportInjury initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fInjuryType = (InjuryType)IJsonOption.INJURY_TYPE.getFrom(jsonObject);
        this.fArmorBroken = IJsonOption.ARMOR_BROKEN.getFrom(jsonObject);
        this.fArmorRoll = IJsonOption.ARMOR_ROLL.getFrom(jsonObject);
        this.fInjuryRoll = IJsonOption.INJURY_ROLL.getFrom(jsonObject);
        this.fCasualtyRoll = IJsonOption.CASUALTY_ROLL.getFrom(jsonObject);
        this.fSeriousInjury = (SeriousInjury)IJsonOption.SERIOUS_INJURY.getFrom(jsonObject);
        this.fCasualtyRollDecay = IJsonOption.CASUALTY_ROLL_DECAY.getFrom(jsonObject);
        this.fSeriousInjuryDecay = (SeriousInjury)IJsonOption.SERIOUS_INJURY_DECAY.getFrom(jsonObject);
        this.fInjury = IJsonOption.INJURY.getFrom(jsonObject);
        this.fInjuryDecay = IJsonOption.INJURY_DECAY.getFrom(jsonObject);
        this.fAttackerId = IJsonOption.ATTACKER_ID.getFrom(jsonObject);
        this.fArmorModifiers.clear();
        ArmorModifierFactory armorModifierFactory = new ArmorModifierFactory();
        JsonArray armorModifiers = IJsonOption.ARMOR_MODIFIERS.getFrom(jsonObject);
        for (int i = 0; i < armorModifiers.size(); ++i) {
            this.fArmorModifiers.add((ArmorModifier)UtilJson.toEnumWithName(armorModifierFactory, armorModifiers.get(i)));
        }
        this.fInjuryModifiers.clear();
        InjuryModifierFactory injuryModifierFactory = new InjuryModifierFactory();
        JsonArray injuryModifiers = IJsonOption.INJURY_MODIFIERS.getFrom(jsonObject);
        for (int i2 = 0; i2 < injuryModifiers.size(); ++i2) {
            this.fInjuryModifiers.add((InjuryModifier)UtilJson.toEnumWithName(injuryModifierFactory, injuryModifiers.get(i2)));
        }
        return this;
    }
}

