package org.butterbrot.ffb.stats.validation;

public class StringValidator extends Validator<String> {
    @Override
    public boolean validate(String fieldPrefix, String baseline, String toValidate) {
        if (!baseline.equals(toValidate)) {
            logDifference(fieldPrefix,  baseline,  toValidate);
            return false;
        }
        return true;
    }
}
