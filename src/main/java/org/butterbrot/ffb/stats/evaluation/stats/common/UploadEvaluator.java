package org.butterbrot.ffb.stats.evaluation.stats.common;

import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportFumbblResultUpload;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.model.StatsCollection;

public class UploadEvaluator extends Evaluator<ReportFumbblResultUpload> {

    private final StatsCollection collection;

    public UploadEvaluator(StatsCollection collection) {
        this.collection = collection;
    }

    @Override
    public void evaluate(IReport report) {
        collection.setFinished(true);
    }
}
