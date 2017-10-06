/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.HeatExhaustion;
import com.balancedbytes.games.ffb.KnockoutRecovery;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class ReportTurnEnd
implements IReport {
    private String fPlayerIdTouchdown;
    private List<KnockoutRecovery> fKnockoutRecoveries = new ArrayList<KnockoutRecovery>();
    private List<HeatExhaustion> fHeatExhaustions = new ArrayList<HeatExhaustion>();

    public ReportTurnEnd() {
    }

    public ReportTurnEnd(String pPlayerIdTouchdown, KnockoutRecovery[] pKnockoutRecoveries, HeatExhaustion[] pHeatExhaustions) {
        this();
        this.fPlayerIdTouchdown = pPlayerIdTouchdown;
        this.add(pKnockoutRecoveries);
        this.add(pHeatExhaustions);
    }

    @Override
    public ReportId getId() {
        return ReportId.TURN_END;
    }

    public String getPlayerIdTouchdown() {
        return this.fPlayerIdTouchdown;
    }

    public KnockoutRecovery[] getKnockoutRecoveries() {
        return this.fKnockoutRecoveries.toArray(new KnockoutRecovery[this.fKnockoutRecoveries.size()]);
    }

    private void add(KnockoutRecovery pKnockoutRecovery) {
        if (pKnockoutRecovery != null) {
            this.fKnockoutRecoveries.add(pKnockoutRecovery);
        }
    }

    private void add(KnockoutRecovery[] pKnockoutRecoveries) {
        if (ArrayTool.isProvided(pKnockoutRecoveries)) {
            for (KnockoutRecovery knockoutRecovery : pKnockoutRecoveries) {
                this.add(knockoutRecovery);
            }
        }
    }

    public HeatExhaustion[] getHeatExhaustions() {
        return this.fHeatExhaustions.toArray(new HeatExhaustion[this.fHeatExhaustions.size()]);
    }

    private void add(HeatExhaustion pHeatExhaustion) {
        if (pHeatExhaustion != null) {
            this.fHeatExhaustions.add(pHeatExhaustion);
        }
    }

    private void add(HeatExhaustion[] pHeatExhaustions) {
        if (ArrayTool.isProvided(pHeatExhaustions)) {
            for (HeatExhaustion heatExhaustion : pHeatExhaustions) {
                this.add(heatExhaustion);
            }
        }
    }

    @Override
    public IReport transform() {
        return new ReportTurnEnd(this.getPlayerIdTouchdown(), this.getKnockoutRecoveries(), this.getHeatExhaustions());
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject jsonObject = new JsonObject();
        IJsonOption.REPORT_ID.addTo(jsonObject, this.getId());
        IJsonOption.PLAYER_ID_TOUCHDOWN.addTo(jsonObject, this.fPlayerIdTouchdown);
        JsonArray knockoutRecoveryArray = new JsonArray();
        for (KnockoutRecovery knockoutRecovery : this.fKnockoutRecoveries) {
            knockoutRecoveryArray.add(knockoutRecovery.toJsonValue());
        }
        IJsonOption.KNOCKOUT_RECOVERY_ARRAY.addTo(jsonObject, knockoutRecoveryArray);
        JsonArray heatExhaustionArray = new JsonArray();
        for (HeatExhaustion heatExhaustion : this.fHeatExhaustions) {
            heatExhaustionArray.add(heatExhaustion.toJsonValue());
        }
        IJsonOption.HEAT_EXHAUSTION_ARRAY.addTo(jsonObject, heatExhaustionArray);
        return jsonObject;
    }

    @Override
    public ReportTurnEnd initFrom(JsonValue pJsonValue) {
        JsonArray heatExhaustionArray;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
        this.fPlayerIdTouchdown = IJsonOption.PLAYER_ID_TOUCHDOWN.getFrom(jsonObject);
        JsonArray knockoutRecoveryArray = IJsonOption.KNOCKOUT_RECOVERY_ARRAY.getFrom(jsonObject);
        if (knockoutRecoveryArray != null) {
            for (int i = 0; i < knockoutRecoveryArray.size(); ++i) {
                this.add(new KnockoutRecovery().initFrom(knockoutRecoveryArray.get(i)));
            }
        }
        if ((heatExhaustionArray = IJsonOption.HEAT_EXHAUSTION_ARRAY.getFrom(jsonObject)) != null) {
            for (int i = 0; i < heatExhaustionArray.size(); ++i) {
                this.add(new HeatExhaustion().initFrom(heatExhaustionArray.get(i)));
            }
        }
        return this;
    }
}
