package org.butterbrot.ffb.stats.validation;

import java.util.Set;

public class SetValidator extends Validator<Set<String>> {

    @Override
    public boolean validate(String fieldPrefix, Set<String> baseline, Set<String> toValidate) {
        boolean result = true;
        int baselineSize = baseline.size();
        int toValidateSize = toValidate.size();
        if (baselineSize != toValidateSize) {
            logDifference(getCompoundName(fieldPrefix, "size"), String.valueOf(baselineSize), String
                    .valueOf(toValidateSize));
            result = false;
        }
        return result;
    }

}
