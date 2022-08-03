package org.butterbrot.ffb.stats.evaluation.stats;

import com.fumbbl.ffb.report.IReport;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Evaluator<T extends IReport> {

    public boolean handles(IReport report) {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        Class<T> genericClass = (Class<T>) type;
        return genericClass.isAssignableFrom(report.getClass());
    }

    public abstract void evaluate(IReport report);

}
