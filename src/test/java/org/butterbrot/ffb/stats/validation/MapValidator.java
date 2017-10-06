package org.butterbrot.ffb.stats.validation;

import java.util.Map;

public class MapValidator extends DelegatingValidator<Map<Object, Object>, Object> {

    private Iterable<Validator> validators;

    void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public boolean validate(String fieldPrefix, Map<Object, Object> baseline, Map<Object, Object> toValidate) {
        boolean result = true;
        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            result = false;
        }
        for (Object key: baseline.keySet()) {
            String compoundFieldName = getCompoundName(fieldPrefix, key.toString());

            result = delegate(compoundFieldName, baseline.get(key), toValidate.get(key)) && result;
        }

        return result;
    }

    @Override
    public Iterable<Validator> getValidators() {
        return validators;
    }
}
