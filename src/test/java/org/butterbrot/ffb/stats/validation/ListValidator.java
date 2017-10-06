package org.butterbrot.ffb.stats.validation;

import java.util.List;

public class ListValidator extends DelegatingValidator<List<Object>, Object> {

    private Iterable<Validator> validators;

    void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(String fieldPrefix, List<Object> baseline, List<Object> toValidate) {

        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            return;
        }
        for (int index=0; index< baselineSize; index++) {
            String compoundFieldName = getCompoundName(fieldPrefix, "[" + index + "]");
            Object baselineField = baseline.get(index);
            delegate(compoundFieldName, baselineField, toValidate.get(index));
        }

    }

    @Override
    public Iterable<Validator> getValidators() {
        return validators;
    }
}
