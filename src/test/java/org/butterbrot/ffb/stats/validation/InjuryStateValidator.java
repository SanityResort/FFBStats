package org.butterbrot.ffb.stats.validation;

import org.butterbrot.ffb.stats.model.InjuryState;

public class InjuryStateValidator extends Validator<InjuryState> {
    @Override
    public boolean validate(String fieldPrefix, InjuryState baseline, InjuryState toValidate) {
        if (!baseline.equals(toValidate)) {
            logDifference(fieldPrefix,  baseline.name(),  toValidate.name());
            return false;
        }
        return true;
    }
}
