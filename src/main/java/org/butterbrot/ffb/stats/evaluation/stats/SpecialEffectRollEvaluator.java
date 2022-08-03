package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportSpecialEffectRoll;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class SpecialEffectRollEvaluator extends Evaluator<ReportSpecialEffectRoll> {

    private StatsCollection collection;

    public SpecialEffectRollEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        ReportSpecialEffectRoll effect = (ReportSpecialEffectRoll) report;
        // bomb fumbles have no dice roll for the bomber (goes down automatically), also fireballs on lying
        // players prolly have that effect
        if (effect.getRoll() > 0) {
            collection.addSingleOpposingRoll(effect.getRoll(), effect.getPlayerId());
            if (effect.isSuccessful()) {
                collection.addOpposingSuccessRoll(effect.getPlayerId(), effect.getId(), effect.getSpecialEffect() == SpecialEffect.LIGHTNING ? 2 : 4);
            }
        }
    }
}
