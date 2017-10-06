package org.butterbrot.ffb.stats.validation;

public class IntArrayValidator extends DelegatingValidator<int[], Integer> {

    private Iterable<Validator> validators;

    @Override
    Iterable<Validator> getValidators() {
        return validators;
    }

    @Override
    public void validate(String fieldPrefix, int[] baseline, int[] toValidate) {
        int baselineSize = baseline.length;
        int toValidateSize = toValidate.length;
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "length"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            return;
        }
        for (int index=0; index< baselineSize; index++) {
            String compoundFieldName = getCompoundName(fieldPrefix, "[" + index + "]");
            int baselineField = baseline[index];
            delegate(compoundFieldName, baselineField, toValidate[index]);
        }
    }

    @Override
    boolean handles(Object data) {
        return data instanceof int[];
    }

    void setValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }
}
