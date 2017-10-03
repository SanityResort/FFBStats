package org.butterbrot.ffb.stats.validation;

public class StringValidator extends Validator<String> {
    @Override
    public void validate(String fieldPrefix, String baseline, String toValidate) {
        if (!baseline.equals(toValidate)) {
            logDifference(fieldPrefix,  baseline,  toValidate);
        }
    }
}
