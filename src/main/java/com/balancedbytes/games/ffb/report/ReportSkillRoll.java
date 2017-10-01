/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.CatchModifierFactory;
import com.balancedbytes.games.ffb.DodgeModifierFactory;
import com.balancedbytes.games.ffb.GazeModifierFactory;
import com.balancedbytes.games.ffb.GoForItModifierFactory;
import com.balancedbytes.games.ffb.INamedObject;
import com.balancedbytes.games.ffb.IRollModifier;
import com.balancedbytes.games.ffb.IRollModifierFactory;
import com.balancedbytes.games.ffb.InterceptionModifierFactory;
import com.balancedbytes.games.ffb.LeapModifierFactory;
import com.balancedbytes.games.ffb.PassModifierFactory;
import com.balancedbytes.games.ffb.PickupModifierFactory;
import com.balancedbytes.games.ffb.RightStuffModifierFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.JsonArrayOption;
import com.balancedbytes.games.ffb.json.JsonBooleanOption;
import com.balancedbytes.games.ffb.json.JsonEnumWithNameOption;
import com.balancedbytes.games.ffb.json.JsonIntOption;
import com.balancedbytes.games.ffb.json.JsonStringOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class ReportSkillRoll
implements IReport {
    private ReportId fId;
    private String fPlayerId;
    private boolean fSuccessful;
    private int fRoll;
    private int fMinimumRoll;
    private boolean fReRolled;
    private List<IRollModifier> fRollModifierList;

    public ReportSkillRoll(ReportId pId) {
        this.fId = pId;
        this.initRollModifiers(null);
    }

    public ReportSkillRoll(ReportId pId, String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled) {
        this(pId, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled, null);
    }

    public ReportSkillRoll(ReportId pId, String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pReRolled, IRollModifier[] pRollModifiers) {
        this.fId = pId;
        this.fPlayerId = pPlayerId;
        this.fSuccessful = pSuccessful;
        this.fRoll = pRoll;
        this.fMinimumRoll = pMinimumRoll;
        this.fReRolled = pReRolled;
        this.initRollModifiers(pRollModifiers);
    }

    private void initRollModifiers(IRollModifier[] pRollModifiers) {
        this.fRollModifierList = new ArrayList<IRollModifier>();
        if (ArrayTool.isProvided(pRollModifiers)) {
            for (IRollModifier rollModifier : pRollModifiers) {
                this.addRollModifier(rollModifier);
            }
        }
    }

    public void addRollModifier(IRollModifier pRollModifier) {
        if (pRollModifier != null) {
            this.fRollModifierList.add(pRollModifier);
        }
    }

    public IRollModifier[] getRollModifiers() {
        return this.fRollModifierList.toArray(new IRollModifier[this.fRollModifierList.size()]);
    }

    public boolean hasRollModifier(IRollModifier pRollModifier) {
        return this.fRollModifierList.contains(pRollModifier);
    }

    protected List<IRollModifier> getRollModifierList() {
        return this.fRollModifierList;
    }

    @Override
    public ReportId getId() {
        return this.fId;
    }

    public String getPlayerId() {
        return this.fPlayerId;
    }

    public boolean isSuccessful() {
        return this.fSuccessful;
    }

    public int getRoll() {
        return this.fRoll;
    }

    public int getMinimumRoll() {
        return this.fMinimumRoll;
    }

    public boolean isReRolled() {
        return this.fReRolled;
    }

    @Override
    public IReport transform() {
        return new ReportSkillRoll(this.getId(), this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isReRolled(), this.getRollModifiers());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.fId);
        IJsonOption.PLAYER_ID.addTo(jsonObject, this.fPlayerId);
        IJsonOption.SUCCESSFUL.addTo(jsonObject, this.fSuccessful);
        IJsonOption.ROLL.addTo(jsonObject, this.fRoll);
        IJsonOption.MINIMUM_ROLL.addTo(jsonObject, this.fMinimumRoll);
        IJsonOption.RE_ROLLED.addTo(jsonObject, this.fReRolled);
        if (this.fRollModifierList.size() > 0) {
            JsonArray modifierArray = new JsonArray();
            for (IRollModifier modifier : this.fRollModifierList) {
                modifierArray.add(UtilJson.toJsonValue(modifier));
            }
            IJsonOption.ROLL_MODIFIERS.addTo(jsonObject, modifierArray);
        }
        return jsonObject;
    }

    @Override
    public ReportSkillRoll initFrom(JsonValue pJsonValue) {
        IRollModifierFactory modifierFactory;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerId = IJsonOption.PLAYER_ID.getFrom(jsonObject);
        this.fSuccessful = IJsonOption.SUCCESSFUL.getFrom(jsonObject);
        this.fRoll = IJsonOption.ROLL.getFrom(jsonObject);
        this.fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(jsonObject);
        this.fReRolled = IJsonOption.RE_ROLLED.getFrom(jsonObject);
        JsonArray modifierArray = IJsonOption.ROLL_MODIFIERS.getFrom(jsonObject);
        if (modifierArray != null && (modifierFactory = this.createRollModifierFactory()) != null) {
            for (int i = 0; i < modifierArray.size(); ++i) {
                this.fRollModifierList.add((IRollModifier)UtilJson.toEnumWithName(modifierFactory, modifierArray.get(i)));
            }
        }
        return this;
    }

    private IRollModifierFactory createRollModifierFactory() {
        switch (this.getId()) {
            case CATCH_ROLL: {
                return new CatchModifierFactory();
            }
            case DODGE_ROLL: {
                return new DodgeModifierFactory();
            }
            case GO_FOR_IT_ROLL: {
                return new GoForItModifierFactory();
            }
            case INTERCEPTION_ROLL: {
                return new InterceptionModifierFactory();
            }
            case LEAP_ROLL: {
                return new LeapModifierFactory();
            }
            case PASS_ROLL: 
            case THROW_TEAM_MATE_ROLL: {
                return new PassModifierFactory();
            }
            case PICK_UP_ROLL: {
                return new PickupModifierFactory();
            }
            case RIGHT_STUFF_ROLL: {
                return new RightStuffModifierFactory();
            }
            case HYPNOTIC_GAZE_ROLL: {
                return new GazeModifierFactory();
            }
        }
        return null;
    }

}

