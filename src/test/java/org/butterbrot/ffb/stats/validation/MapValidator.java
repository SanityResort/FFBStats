package org.butterbrot.ffb.stats.validation;

import java.util.List;
import java.util.Map;

public class MapValidator extends Validator<Map<Object, Object>> {

    private Iterable<Validator> validators;

    public void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    public void validate(Map<Object, Object> baseline, Map<Object, Object> toValidate) {
        validate("", baseline, toValidate);
    }

    @Override
    public void validate(String fieldPrefix, Map<Object, Object> baseline, Map<Object, Object> toValidate) {

        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
        }
        for (Object key: baseline.keySet()) {
            String compoundFieldName = getCompoundName(fieldPrefix, key.toString());

            delegate(compoundFieldName, baseline.get(key), toValidate.get(key));
        }

    }

    private void delegate(String fieldPrefix, Object baseline, Object toValidate) {

        for (Validator validator : validators) {
            if (validator.handles(baseline)) {
                validator.validate(fieldPrefix, baseline, toValidate);
                return;
            }
        }
        throw new IllegalArgumentException("Could not find a suitable validator for element " + fieldPrefix + "" +
                " of type " + baseline.getClass().getSimpleName());
    }
}
