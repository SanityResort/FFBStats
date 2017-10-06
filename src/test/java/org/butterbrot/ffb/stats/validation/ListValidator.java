package org.butterbrot.ffb.stats.validation;

import java.util.List;

public class ListValidator extends DelegatingValidator<List<Object>, Object> {

    private Iterable<Validator> validators;

    void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public boolean validate(String fieldPrefix, List<Object> baseline, List<Object> toValidate) {
        boolean result = true;
        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            result = false;
        }
        for (int index=0; index< baselineSize; index++) {
            String compoundFieldName = getCompoundName(fieldPrefix, "[" + index + "]");
            Object baselineField = baseline.get(index);
            result = delegate(compoundFieldName, baselineField, toValidate.get(index)) && result;
        }

        return result;
    }

    @Override
    public Iterable<Validator> getValidators() {
        return validators;
    }
}
