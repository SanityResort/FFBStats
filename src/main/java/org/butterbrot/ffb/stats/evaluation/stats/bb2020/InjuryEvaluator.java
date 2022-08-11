package org.butterbrot.ffb.stats.evaluation.stats.bb2020;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.bb2020.ReportInjury;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.adapter.bb2020.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class InjuryEvaluator extends Evaluator<ReportInjury> {

    private final StatsCollection collection;
    private final StatsState<ReportPoInjury> state;

    public InjuryEvaluator(StatsCollection collection, StatsState<ReportPoInjury> state) {
        this.collection = collection;
        this.state = state;
    }

    @Override
    public void evaluate(IReport report) {
        ReportInjury injury = (ReportInjury) report;
        if (ArrayTool.isProvided(injury.getArmorRoll())) {
            if (injury.getSkip() == null || !injury.getSkip().isArmour() ) {
                collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
            }
        }
        if (injury.isArmorBroken()) {
            state.getInjuries().addLast(new ReportPoInjury(injury, state.getPoReport()));
            state.setPoReport(null);
            // if the armour is broken report the injury roll, but only if both injury dice are not 0. this
            // should prevent errors when fanatic armour is broken, as this might be reported with weird data.
            if ((injury.getSkip() == null || !injury.getSkip().isInjury()) && ArrayTool.isProvided(injury.getInjuryRoll()) && injury.getInjuryRoll()[0] * injury.getInjuryRoll()[1] > 0) {
                collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
            }
        }
        if (state.getActivePlayer() != null && state.getCurrentBlockRoll() != null) {
            collection.addBlockKnockDown(state.getCurrentBlockRoll().getBlockRoll().length, injury.getDefenderId(),
                    state.getCurrentBlockRoll().getChoosingTeamId(), state.getActivePlayer());
        }
    }
}
