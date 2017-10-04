package org.butterbrot.ffb.stats.validation;

import java.util.List;

public class ListValidator extends Validator<List<Object>> {

    private Iterable<Validator> validators;

    public void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    public void validate(List<Object> baseline, List<Object> toValidate) {
        validate("", baseline, toValidate);
    }

    @Override
    public void validate(String fieldPrefix, List<Object> baseline, List<Object> toValidate) {

        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
        }
        for (int index=0; index< baselineSize; index++) {
            delegate(fieldPrefix, baseline.get(index), toValidate.get(index), index);
        }

    }

    private void delegate(String fieldPrefix, Object baseline, Object toValidate, int index) {
        String compoundFieldName = getCompoundName(fieldPrefix, "[" + index + "]");

        for (Validator validator : validators) {
            if (validator.handles(baseline)) {
                validator.validate(compoundFieldName, baseline, toValidate);
                return;
            }
        }
        throw new IllegalArgumentException("Could not find a suitable validator for element " + compoundFieldName + "" +
                " of type " + baseline.getClass().getSimpleName());
    }
}
