package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportInjury;
import com.balancedbytes.games.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.adapter.ReportPoInjury;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class InjuryEvaluator extends Evaluator<ReportInjury> {

    private StatsCollection collection;
    private StatsState state;

    public InjuryEvaluator(StatsCollection collection, StatsState state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportInjury injury = (ReportInjury) report;
        if (ArrayTool.isProvided(injury.getArmorRoll())) {
            if (state.getPoReport() == null || (state.getPoReport().isUsed() && !state.getPoReport().isReRollInjury())) {
                collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
            }
        }
        if (injury.isArmorBroken()) {
            state.getInjuries().addLast(new ReportPoInjury(injury, state.getPoReport()));
            state.setPoReport(null);
            // if the armour is broken report the injury roll, but only if both injury dice are not 0. this
            // should prevent errors when fanatic armour is broken, as this might be reported with weird data.
            if (ArrayTool.isProvided(injury.getInjuryRoll()) && injury.getInjuryRoll()[0] * injury.getInjuryRoll()[1] > 0) {
                collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
                if (ArrayTool.isProvided(injury.getCasualtyRoll())) {
                    collection.addSingleRoll(injury.getCasualtyRoll()[0], injury.getDefenderId());
                }
                if (ArrayTool.isProvided(injury.getCasualtyRollDecay())) {
                    collection.addSingleRoll(injury.getCasualtyRollDecay()[0], injury.getDefenderId());
                }
            }
        }
        if (state.getActivePlayer() != null && state.getCurrentBlockRoll() != null) {
            collection.addBlockKnockDown(state.getCurrentBlockRoll().getBlockRoll().length, injury.getDefenderId(),
                    state.getCurrentBlockRoll().getChoosingTeamId(), state.getActivePlayer());
        }
    }
}
