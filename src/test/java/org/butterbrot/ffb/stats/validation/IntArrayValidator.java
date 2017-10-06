package org.butterbrot.ffb.stats.validation;

public class IntArrayValidator extends DelegatingValidator<int[], Integer> {

    private Iterable<Validator> validators;

    @Override
    Iterable<Validator> getValidators() {
        return validators;
    }

    @Override
    public boolean validate(String fieldPrefix, int[] baseline, int[] toValidate) {
        boolean result = true;
        int baselineSize = baseline.length;
        int toValidateSize = toValidate.length;
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "length"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            result = false;
        }
        for (int index=0; index< baselineSize; index++) {
            String compoundFieldName = getCompoundName(fieldPrefix, "[" + index + "]");
            int baselineField = baseline[index];
            result = delegate(compoundFieldName, baselineField, toValidate[index]) && result;
        }
        return result;
    }

    @Override
    boolean handles(Object data) {
        return data instanceof int[];
    }

    void setValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }
}
