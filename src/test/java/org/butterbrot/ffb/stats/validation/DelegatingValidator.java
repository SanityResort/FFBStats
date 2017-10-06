package org.butterbrot.ffb.stats.validation;

abstract class DelegatingValidator<T, S> extends Validator<T> {

    abstract Iterable<Validator> getValidators();

    boolean delegate(String compoundFieldName, S baseline, S toValidate){
        if (baseline == null) {
            if (toValidate != null) {
                logDifference(compoundFieldName, null, toValidate.toString());
                return false;
            }
            return true;
        } else if ( toValidate == null) {
            logDifference(compoundFieldName, baseline.toString(), null);
            return false;
        }

        for (Validator validator : getValidators()) {
            if (validator.handles(baseline)) {
                return validator.validate(compoundFieldName, baseline, toValidate);
            }
        }
        throw new IllegalArgumentException("Could not find a suitable validator for element " + compoundFieldName + "" +
                " of type " + baseline.getClass().getSimpleName());
    }
}
