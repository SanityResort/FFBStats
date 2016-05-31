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

public class ReportTurnEnd implements IReport {
    private List<KnockoutRecovery> fKnockoutRecoveries = new ArrayList<KnockoutRecovery>();
    private List<HeatExhaustion> fHeatExhaustions = new ArrayList<HeatExhaustion>();

    ReportTurnEnd() {
    }

    private ReportTurnEnd(KnockoutRecovery[] pKnockoutRecoveries, HeatExhaustion[] pHeatExhaustions) {
        this();
        this.add(pKnockoutRecoveries);
        this.add(pHeatExhaustions);
    }

    @Override
    public ReportId getId() {
        return ReportId.TURN_END;
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
        return new ReportTurnEnd(this.getKnockoutRecoveries(), this.getHeatExhaustions());
    }

    @Override
    public ReportTurnEnd initFrom(JsonValue pJsonValue) {
        JsonArray heatExhaustionArray;
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilReport.validateReportId(this, (ReportId)IJsonOption.REPORT_ID.getFrom(jsonObject));
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

