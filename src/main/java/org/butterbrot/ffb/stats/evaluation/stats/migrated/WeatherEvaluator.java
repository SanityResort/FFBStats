package org.butterbrot.ffb.stats.evaluation.stats.migrated;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportWeather;
import org.butterbrot.ffb.stats.evaluation.stats.migrated.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class WeatherEvaluator extends Evaluator<ReportWeather> {

    private StatsCollection collection;

    public WeatherEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        collection.setWeather(((ReportWeather) report).getWeather().getName());
    }
}
