package org.butterbrot.ffb.stats.evaluation.stats;

import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportWeather;
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
