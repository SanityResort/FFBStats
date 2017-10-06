package org.butterbrot.ffb.stats.validation;

public class BooleanValidator extends Validator<Boolean> {
    @Override
    public void validate(String fieldPrefix, Boolean baseline, Boolean toValidate) {
        if (!baseline.equals(toValidate)) {
            logDifference(fieldPrefix, baseline.toString(), toValidate.toString());
        }
    }
}
