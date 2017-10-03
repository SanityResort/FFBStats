package org.butterbrot.ffb.stats.validation;

import org.butterbrot.ffb.stats.collections.Data;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ServiceLoader;

public class DataValidator extends Validator<Data> {

    private Iterable<Validator> validators;

    public void setDelegateValidators(Iterable<Validator> validators) {
        this.validators = validators;
    }

    public void validate(Data baseline, Data toValidate) {
        validate("", baseline, toValidate);
    }

    @Override
    public void validate(String fieldPrefix, Data baseline, Data toValidate) {

        for (Field field : baseline.getClass().getDeclaredFields()) {
            if (!Modifier.isTransient(field.getModifiers())) {
                delegate(fieldPrefix, baseline, toValidate, field);
            }
        }
    }

    private void delegate(String fieldPrefix, Object baseline, Object toValidate, Field field) {
        String fieldName = field.getName();
        String compoundFieldName = StringUtils.isEmpty(fieldPrefix) ? fieldName : String.join(fieldPrefix,
                PREFIX_DELIM, fieldName);
        for (Validator validator : validators) {
            Object baseField = ReflectionTestUtils.getField(baseline, fieldName);
            Object toValidateField = ReflectionTestUtils.getField(toValidate, fieldName);
            if (validator.handles(baseField)) {
                validator.validate(compoundFieldName, baseField, toValidateField);
                return;
            }
        }
        throw new IllegalArgumentException("Could not find find a suitable validator for field " + compoundFieldName + "" +
                " of type " + field.getType().getSimpleName());
    }
}
