package org.butterbrot.ffb.stats.validation;

import org.butterbrot.ffb.stats.model.Data;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DataValidator extends DelegatingValidator<Data, Object> {

    private Iterable<Validator> validators;

    void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    public boolean validate(Data baseline, Data toValidate) {
        return validate("", baseline, toValidate);
    }

    @Override
    public boolean validate(String fieldPrefix, Data baseline, Data toValidate) {
        boolean result = true;

        for (Field field : baseline.getClass().getDeclaredFields()) {
            if (!Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                String fieldName = field.getName();
                Object baseField = ReflectionTestUtils.getField(baseline, fieldName);
                Object toValidateField = ReflectionTestUtils.getField(toValidate, fieldName);
                String compoundFieldName = getCompoundName(fieldPrefix, fieldName);
                result = delegate(compoundFieldName, baseField, toValidateField) && result;
            }
        }

        return result;
    }

    @Override
    Iterable<Validator> getValidators() {
        return validators;
    }
}
