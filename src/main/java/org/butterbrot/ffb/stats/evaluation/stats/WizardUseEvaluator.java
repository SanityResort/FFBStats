package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportWizardUse;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class WizardUseEvaluator extends Evaluator<ReportWizardUse> {

    private StatsState state;
    private StatsCollection collection;

    public WizardUseEvaluator(StatsState state, StatsCollection collection) {
        this.state = state;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        collection.addWizardUse(state.isHomePlaying());
    }
}
