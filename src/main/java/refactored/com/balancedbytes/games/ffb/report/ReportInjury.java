/*
 * Decompiled with CFR 0_114.
 */
package refactored.com.balancedbytes.games.ffb.report;

import refactored.com.balancedbytes.games.ffb.ArmorModifier;
import refactored.com.balancedbytes.games.ffb.ArmorModifierFactory;
import refactored.com.balancedbytes.games.ffb.InjuryModifier;
import refactored.com.balancedbytes.games.ffb.InjuryModifierFactory;
import refactored.com.balancedbytes.games.ffb.PlayerState;
import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import refactored.com.balancedbytes.games.ffb.util.ArrayTool;
import repackaged.com.eclipsesource.json.JsonArray;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportInjury
        implements IReport {
    private String fDefenderId;
    private boolean fArmorBroken;
    private int[] fArmorRoll;
    private int[] fInjuryRoll;
    private int[] fCasualtyRoll;
    private int[] fCasualtyRollDecay;
    private PlayerState fInjury;
    private List<ArmorModifier> fArmorModifiers = new ArrayList<ArmorModifier>();
    private List<InjuryModifier> fInjuryModifiers = new ArrayList<InjuryModifier>();
    private ReportPilingOn poReport;

    public ReportInjury() {
    }

    public ReportInjury(String pDefenderId, boolean pArmorBroken, int[] pArmorRoll, int[] pInjuryRoll, int[] pCasualtyRoll, InjuryModifier[] pInjuryModifiers, PlayerState pInjury, ArmorModifier[] pArmorModifiers, int[] pCasualtyRollDecay) {
        this();
        this.fDefenderId = pDefenderId;
        this.fArmorBroken = pArmorBroken;
        this.fArmorRoll = pArmorRoll;
        this.fInjuryRoll = pInjuryRoll;
        this.fCasualtyRoll = pCasualtyRoll;
        this.fCasualtyRollDecay = pCasualtyRollDecay;
        this.add(pArmorModifiers);
        this.add(pInjuryModifiers);
        this.fInjury = pInjury;
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

    public PlayerState getInjury() {
        return fInjury;
    }

    public List<ArmorModifier> getArmorModifiers() {
        return fArmorModifiers;
    }

    public List<InjuryModifier> getInjuryModifiers() {
        return fInjuryModifiers;
    }

    public ReportPilingOn getPoReport() {
        return poReport;
    }

    public void setPoReport(ReportPilingOn poReport) {
        this.poReport = poReport;
    }

    @Override
    public IReport transform() {
        return new ReportInjury(this.getDefenderId(), this.isArmorBroken(), this.getArmorRoll(), this.getInjuryRoll(), this.getCasualtyRoll(),
                this.fInjuryModifiers.toArray(new InjuryModifier[fInjuryModifiers.size()]), this.fInjury,
                this.fArmorModifiers.toArray(new ArmorModifier[fArmorModifiers.size()]), this.getCasualtyRollDecay());
    }

    @Override
    public ReportInjury initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fDefenderId = IJsonOption.DEFENDER_ID.getFrom(jsonObject);
        this.fArmorBroken = IJsonOption.ARMOR_BROKEN.getFrom(jsonObject);
        this.fArmorRoll = IJsonOption.ARMOR_ROLL.getFrom(jsonObject);
        this.fInjuryRoll = IJsonOption.INJURY_ROLL.getFrom(jsonObject);
        this.fCasualtyRoll = IJsonOption.CASUALTY_ROLL.getFrom(jsonObject);
        this.fCasualtyRollDecay = IJsonOption.CASUALTY_ROLL_DECAY.getFrom(jsonObject);
        this.fInjuryRoll = IJsonOption.INJURY_ROLL.getFrom(jsonObject);
        this.fArmorModifiers.clear();
        this.fInjury = IJsonOption.INJURY.getFrom(jsonObject);
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
}

