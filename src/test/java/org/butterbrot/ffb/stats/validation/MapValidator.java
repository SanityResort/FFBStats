package org.butterbrot.ffb.stats.validation;

import java.util.Map;

public class MapValidator extends DelegatingValidator<Map<Object, Object>, Object> {

    private Iterable<Validator> validators;

    void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(String fieldPrefix, Map<Object, Object> baseline, Map<Object, Object> toValidate) {

        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            return;
        }
        for (Object key: baseline.keySet()) {
            String compoundFieldName = getCompoundName(fieldPrefix, key.toString());

            delegate(compoundFieldName, baseline.get(key), toValidate.get(key));
        }
    }

    @Override
    public Iterable<Validator> getValidators() {
        return validators;
    }
}
