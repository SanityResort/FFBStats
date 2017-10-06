package org.butterbrot.ffb.stats.validation;

public class IntegerValidator extends Validator<Integer> {
    @Override
    public boolean validate(String fieldPrefix, Integer baseline, Integer toValidate) {
        if (!baseline.equals(toValidate)) {
            logDifference(fieldPrefix, baseline.toString(), toValidate.toString());
            return false;
        }
        return true;
    }
}
