package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.HeatExhaustion;
import com.balancedbytes.games.ffb.KnockoutRecovery;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportTurnEnd;
import com.balancedbytes.games.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.evaluation.turnover.TurnOverFinder;
import org.butterbrot.ffb.stats.model.StatsCollection;
import org.springframework.util.StringUtils;

public class TurnEndEvaluator extends Evaluator<ReportTurnEnd> {

    private StatsCollection collection;
    private StatsState state;

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
