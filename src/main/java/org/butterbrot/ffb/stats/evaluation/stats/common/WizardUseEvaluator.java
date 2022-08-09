package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportWizardUse;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class WizardUseEvaluator extends Evaluator<ReportWizardUse> {

    private final StatsState state;
    private final StatsCollection collection;

    public WizardUseEvaluator(StatsState state, StatsCollection collection) {
        this.state = state;
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        collection.addWizardUse(state.isHomePlaying());
    }
}
