package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.HeatExhaustion;
import com.fumbbl.ffb.KnockoutRecovery;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2016.ReportTurnEnd;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.springframework.util.StringUtils;

public class TurnEndEvaluator extends Evaluator<ReportTurnEnd> {

    private final StatsCollection collection;
    private final StatsState state;

    public TurnEndEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        state.setBallScatters(false);
        ReportTurnEnd turn = (ReportTurnEnd) report;
        if (!StringUtils.isEmpty(turn.getPlayerIdTouchdown())) {
            collection.addTouchdown(turn.getPlayerIdTouchdown());
        }

        if (ArrayTool.isProvided(turn.getKnockoutRecoveries())) {
            for (KnockoutRecovery recovery : turn.getKnockoutRecoveries()) {
                collection.addSingleRoll(recovery.getRoll(), recovery.getPlayerId());
                collection.addKoRoll(recovery.getRoll(), recovery.getPlayerId());
                if (recovery.isRecovering()) {
                    collection.addSuccessRoll(recovery.getPlayerId(), report.getId(), 4 - recovery.getBloodweiserBabes());
                }
            }
        }

        if (ArrayTool.isProvided(turn.getHeatExhaustions())) {
            for (HeatExhaustion exhaustion : turn.getHeatExhaustions()) {
                collection.addSingleOpposingRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
                collection.addHeatRoll(exhaustion.getRoll(), exhaustion.getPlayerId());
                if (!exhaustion.isExhausted()) {
                    collection.addSuccessRoll(exhaustion.getPlayerId(), report.getId(), 2);
                }
            }
        }

        collection.addArmourAndInjuryStats(state.getInjuries());
        state.getInjuries().clear();

        if (state.isStartSecondHalf()) {
            collection.startSecondHalf();
            state.setStartSecondHalf(false);
        }
        if (state.isStartOvertime()) {
            collection.startOvertime();
            state.setStartOvertime(false);
        }
    }
}
